package forum.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import forum.combinedIds.AccountFollowId;
import forum.entities.AccountEntity;
import forum.entities.AccountFollowEntity;
import forum.exceptions.AccountFollowAlreadyExistsException;
import forum.exceptions.AccountFollowNotFoundException;
import forum.exceptions.AccountNotFoundException;
import forum.exceptions.InvalidSessionException;
import forum.exceptions.SelfAccountFollowAttemptException;
import forum.repositories.AccountFollowRepository;
import forum.repositories.AccountRepository;

@Service
public class AccountFollowService {

    @Autowired
    SessionService sessionService;

    @Autowired
    AccountFollowRepository accountFollowRepository;

    @Autowired
    AccountRepository accountRepository;

    public Page<AccountFollowEntity> getFollowingByAccountId(Long id, Pageable pageable)
            throws AccountNotFoundException {
        if (!this.accountRepository.existsById(id)) {
            throw new AccountNotFoundException();
        }

        return this.accountFollowRepository.findByAccountFollowIdFrom(
                this.accountRepository.findById(id).get(),
                pageable);
    }

    public Page<AccountFollowEntity> getFollowersByAccountId(Long id, Pageable pageable)
            throws AccountNotFoundException {
        if (!this.accountRepository.existsById(id)) {
            throw new AccountNotFoundException();
        }

        return this.accountFollowRepository.findByAccountFollowIdTo(
                this.accountRepository.findById(id).get(),
                pageable);
    }

    public void createFollow(Long id)
            throws AccountNotFoundException, InvalidSessionException, AccountFollowAlreadyExistsException,
            SelfAccountFollowAttemptException {
        if (!this.accountRepository.existsById(id)) {
            throw new AccountNotFoundException();
        }

        if (this.sessionService.itsMe(id)) {
            throw new SelfAccountFollowAttemptException();
        }

        AccountFollowId accountFollowId = new AccountFollowId(
                this.sessionService.getUser(),
                this.accountRepository.findById(id).get());

        if (this.accountFollowRepository.existsById(accountFollowId)) {
            throw new AccountFollowAlreadyExistsException();
        }

        AccountFollowEntity accountFollowEntity = new AccountFollowEntity();
        accountFollowEntity.setAccountFollowId(accountFollowId);
        accountFollowEntity.setCreatedAt(LocalDateTime.now());

        this.accountFollowRepository.save(accountFollowEntity);
    }

    public void deleteFollow(Long id)
            throws AccountNotFoundException, InvalidSessionException, AccountFollowNotFoundException {
        if (!this.accountRepository.existsById(id)) {
            throw new AccountNotFoundException();
        }

        AccountFollowId accountFollowId = new AccountFollowId(
                this.sessionService.getUser(),
                this.accountRepository.findById(id).get());

        if (!this.accountFollowRepository.existsById(accountFollowId)) {
            throw new AccountFollowNotFoundException();
        }

        this.accountFollowRepository.deleteById(accountFollowId);
    }
}
