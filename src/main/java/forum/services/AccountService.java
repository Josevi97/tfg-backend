package forum.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import forum.beans.AccountBean;
import forum.entities.AccountEntity;
import forum.entities.CommunityEntity;
import forum.entities.CommunityListEntity;
import forum.exceptions.AccountAlreadyExistsException;
import forum.exceptions.AccountNotFoundException;
import forum.exceptions.IlegalAccountArgumentsException;
import forum.exceptions.InsufficientPrivilegesException;
import forum.exceptions.InvalidSessionException;
import forum.helpers.AccountHelper;
import forum.repositories.AccountRepository;
import forum.repositories.CommunityListRepository;

@Service
public class AccountService {

    @Autowired
    SessionService sessionService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CommunityListRepository communityListRepository;

    public Page<AccountEntity> getAllAccounts(Pageable pageable)
            throws InvalidSessionException, AccountNotFoundException, InsufficientPrivilegesException {
        if (!sessionService.isAdmin()) {
            throw new InsufficientPrivilegesException();
        }

        return this.accountRepository.findAll(pageable);
    }

    public AccountEntity getAccount(Long id) throws AccountNotFoundException {
        if (!this.accountRepository.existsById(id)) {
            throw new AccountNotFoundException();
        }

        return AccountHelper.getFixedAccountEntity(this.accountRepository.findById(id).get());
    }

    public Page<CommunityListEntity> getCommunitiesByUserId(Long id, Pageable pageable)
            throws AccountNotFoundException {

        if (!this.accountRepository.existsById(id)) {
            throw new AccountNotFoundException();
        }

        return this.communityListRepository.findByCommunityListIdUserId(id, pageable);
    }

    public void createAccount(AccountBean accountBean)
            throws IlegalAccountArgumentsException, AccountAlreadyExistsException, InvalidSessionException,
            AccountNotFoundException, InsufficientPrivilegesException {
        if (accountBean == null || !accountBean.isValid()) {
            throw new IlegalAccountArgumentsException();
        }

        if (this.accountRepository.findByLogin(accountBean.getLogin()) != null) {
            throw new AccountAlreadyExistsException("login is already in use");
        }

        if (this.accountRepository.findByEmail(accountBean.getEmail()) != null) {
            throw new AccountAlreadyExistsException("email is already in use");
        }

        if (accountBean.isAdmin() && !this.sessionService.isAdmin()) {
            throw new InsufficientPrivilegesException();
        }

        AccountEntity accountEntity = accountBean.toEntity();
        accountEntity.setUsername(this.generateRandomName());
        accountEntity.setCreatedAt(LocalDateTime.now());
        accountEntity.setLastSessionAt(accountEntity.getCreatedAt());

        this.accountRepository.save(accountEntity);
    }

    public void updateAccount(Long id, AccountBean accountBean)
            throws IlegalAccountArgumentsException, InvalidSessionException, AccountNotFoundException,
            InsufficientPrivilegesException {
        if (accountBean == null || !accountBean.isValid()) {
            throw new IlegalAccountArgumentsException();
        }

        if (!this.accountRepository.existsById(id)) {
            throw new AccountNotFoundException();
        }

        if (!this.sessionService.isAdmin() && !this.sessionService.itsMe(id)) {
            throw new InsufficientPrivilegesException();
        }

        if (accountBean.isAdmin() && !this.sessionService.isAdmin()) {
            throw new InsufficientPrivilegesException();
        }

        AccountEntity accountEntity = this.accountRepository.findById(id).get();
        accountEntity.setLogin(accountBean.getLogin());
        accountEntity.setEmail(accountBean.getEmail());
        accountEntity.setPassword(accountBean.getPassword());
        accountEntity
                .setUsername(
                        accountBean.getUsername() == null ? this.generateRandomName() : accountBean.getUsername());
        accountEntity.setAdmin(accountBean.isAdmin());

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
    }

    public String generateRandomName() {
        return "Jolly_Cucumber";
    }
}
