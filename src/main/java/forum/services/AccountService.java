package forum.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import forum.beans.AccountBean;
import forum.beans.AccountUpdateBean;
import forum.beans.ResetPasswordBean;
import forum.constants.FileConstants;
import forum.entities.AccountEntity;
import forum.exceptions.AccountAlreadyExistsException;
import forum.exceptions.AccountNotFoundException;
import forum.exceptions.IlegalAccountArgumentsException;
import forum.exceptions.IlegalFileExtensionException;
import forum.exceptions.InsufficientPrivilegesException;
import forum.exceptions.InvalidSessionException;
import forum.repositories.AccountRepository;

@Service
public class AccountService {

    @Autowired
    SessionService sessionService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    FileService fileService;

    public Page<AccountEntity> getAllAccounts(Pageable pageable, String filter) {
        if (filter == null) {
            return this.accountRepository.findAll(pageable);
        }

        return this.accountRepository.filtered(filter, pageable);
    }

    public Page<AccountEntity> getAccountsLikeLogin(String login, Pageable pageable) {
        return this.accountRepository.findByLoginContaining(login, pageable);
    }

    public Page<AccountEntity> getRandomAccounts(List<Long> blackList, Pageable pageable) {
        if (blackList == null) {
            blackList = new ArrayList<Long>();
            blackList.add(0L);
        }

        return this.accountRepository.random(blackList, pageable);
    }

    public AccountEntity getAccount(Long id) throws AccountNotFoundException {
        if (!this.accountRepository.existsById(id)) {
            throw new AccountNotFoundException();
        }

        return this.accountRepository.findById(id).get();
    }

    public void createAccount(AccountBean accountBean)
            throws IlegalAccountArgumentsException, AccountAlreadyExistsException, InvalidSessionException,
            AccountNotFoundException, InsufficientPrivilegesException {

        if (accountBean == null || !accountBean.isValid()) {
            throw new IlegalAccountArgumentsException();
        }

        if (this.accountRepository.existsByLogin(accountBean.getLogin())) {
            throw new AccountAlreadyExistsException("login is already in use");
        }

        if (this.accountRepository.existsByEmail(accountBean.getEmail())) {
            throw new AccountAlreadyExistsException("email is already in use");
        }

        if (accountBean.isAdmin() && !this.sessionService.isAdmin()) {
            throw new InsufficientPrivilegesException();
        }

        AccountEntity accountEntity = accountBean.toEntity();
        accountEntity.setAvatar(null);
        accountEntity.setCreatedAt(LocalDateTime.now());
        accountEntity.setLastSessionAt(accountEntity.getCreatedAt());

        this.accountRepository.save(accountEntity);
    }

    public AccountEntity updateAccount(Long id, AccountUpdateBean accountBean, MultipartFile file)
            throws IlegalAccountArgumentsException, InvalidSessionException, AccountNotFoundException,
            AccountAlreadyExistsException, InsufficientPrivilegesException, IlegalFileExtensionException {

        if (accountBean == null || !accountBean.isValid()) {
            throw new IlegalAccountArgumentsException();
        }

        if (this.accountRepository.existsByLogin(accountBean.getLogin())
                && this.accountRepository.findByLogin(accountBean.getLogin()).getId() != id) {
            throw new AccountAlreadyExistsException("login is already in use");
        }

        if (this.accountRepository.existsByEmail(accountBean.getEmail())
                && this.accountRepository.findByEmail(accountBean.getEmail()).getId() != id) {
            throw new AccountAlreadyExistsException("email is already in use");
        }

        if (!this.sessionService.isAdmin() && !this.sessionService.itsMe(id)) {
            throw new InsufficientPrivilegesException();
        }

        if (accountBean.isAdmin() && !this.sessionService.isAdmin()) {
            throw new InsufficientPrivilegesException();
        }

        String avatar = this.fileService.toImage(file, String.format(FileConstants.IMAGE_ACCOUNT_FILE_FORMAT, id),
                accountBean.getChangeImage());

        AccountEntity accountEntity = this.accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException());
        accountEntity.setLogin(accountBean.getLogin());
        accountEntity.setEmail(accountBean.getEmail());
        accountEntity.setDescription(accountBean.getDescription());
        accountEntity.setUsername(accountBean.getUsername());
        accountEntity.setAvatar(avatar);
        accountEntity.setAdmin(accountBean.isAdmin());

        return this.accountRepository.save(accountEntity);
    }

    public void resetPassword(Long id, ResetPasswordBean resetPasswordBean) throws IlegalAccountArgumentsException,
            InvalidSessionException, InsufficientPrivilegesException, AccountNotFoundException {

        if (resetPasswordBean == null || !resetPasswordBean.isValid()) {
            throw new IlegalAccountArgumentsException();
        }

        if (!this.sessionService.isAdmin() && !this.sessionService.itsMe(id)) {
            throw new InsufficientPrivilegesException();
        }

        AccountEntity accountEntity = this.accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException());
        accountEntity.setPassword(DigestUtils.sha256Hex(resetPasswordBean.getPassword()));

        this.accountRepository.save(accountEntity);
    }

    public void deleteAccount(Long id)
            throws InvalidSessionException, AccountNotFoundException, InsufficientPrivilegesException {

        if (!this.accountRepository.existsById(id)) {
            throw new AccountNotFoundException();
        }

        if (!this.sessionService.isAdmin() && !this.sessionService.itsMe(id)) {
            throw new InsufficientPrivilegesException();
        }

        this.accountRepository.deleteById(id);
        this.fileService.removeFile(String.format(FileConstants.IMAGE_ACCOUNT_FILE_FORMAT, id));
    }
}
