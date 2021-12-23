package forum.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import forum.beans.RegisterAccountBean;
import forum.entities.AccountEntity;
import forum.exceptions.AccountAlreadyExistsException;
import forum.exceptions.AccountNotFoundException;
import forum.exceptions.InsufficientPrivilegesException;
import forum.exceptions.InvalidAccountException;
import forum.exceptions.InvalidSessionException;
import forum.repositories.AccountRepository;

@Service
public class AccountService {

    @Autowired
    SessionService sessionService;

    @Autowired
    AccountRepository accountRepository;

    public Page<AccountEntity> getAllAccounts(Pageable pageable)
            throws InvalidSessionException, InsufficientPrivilegesException {
        if (!sessionService.isAdmin()) {
            throw new InsufficientPrivilegesException();
        }

        return this.accountRepository.findAll(pageable);
    }

    public AccountEntity getAccount(Long id) throws AccountNotFoundException {
        if (!this.accountRepository.existsById(id)) {
            throw new AccountNotFoundException();
        }

        return this.accountRepository.findById(id).get();
    }

    public void createAccount(RegisterAccountBean registerAccountBean)
            throws InvalidAccountException, AccountAlreadyExistsException {
        if (registerAccountBean == null || !registerAccountBean.isValid()) {
            throw new InvalidAccountException();
        }

        if (this.accountRepository.findByLogin(registerAccountBean.getLogin()) != null) {
            throw new AccountAlreadyExistsException("login is already in use");
        }

        if (this.accountRepository.findByEmail(registerAccountBean.getEmail()) != null) {
            throw new AccountAlreadyExistsException("email is already in use");
        }

        AccountEntity accountEntity = registerAccountBean.toEntity();
        accountEntity.setAdmin(false);
        accountEntity.setCreatedAt(LocalDateTime.now());
        accountEntity.setLastSessionAt(accountEntity.getCreatedAt());

        this.accountRepository.save(accountEntity);
    }

    public void updateAccount(Long id)
            throws InvalidSessionException, InsufficientPrivilegesException, AccountNotFoundException {
        if (!this.sessionService.isAdmin() && !this.sessionService.itsMe(id)) {
            throw new InsufficientPrivilegesException();
        }

        if (!this.accountRepository.existsById(id)) {
            throw new AccountNotFoundException();
        }

        // Should update account
    }

    public void deleteAccount(Long id)
            throws InvalidSessionException, InsufficientPrivilegesException, AccountNotFoundException {
        if (!this.sessionService.isAdmin() && !this.sessionService.itsMe(id)) {
            throw new InsufficientPrivilegesException();
        }

        if (!this.accountRepository.existsById(id)) {
            throw new AccountNotFoundException();
        }

        this.accountRepository.deleteById(id);
    }
}
