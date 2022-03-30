package forum.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import forum.beans.EntranceBean;
import forum.entities.AccountEntity;
import forum.entities.CommunityEntity;
import forum.entities.EntranceEntity;
import forum.exceptions.AccountNotFoundException;
import forum.exceptions.CommunityNotFoundException;
import forum.exceptions.EntranceNotFoundException;
import forum.exceptions.IlegalEntranceArgumentsException;
import forum.exceptions.InsufficientPrivilegesException;
import forum.exceptions.InvalidSessionException;
import forum.repositories.AccountRepository;
import forum.repositories.CommunityRepository;
import forum.repositories.EntranceRepository;

@Service
public class EntranceService {

    @Autowired
    SessionService sessionService;

    @Autowired
    EntranceRepository entranceRepository;

    @Autowired
    CommunityRepository communityRepository;

    @Autowired
    AccountRepository accountRepository;

    public Page<EntranceEntity> getAllEntrances(Pageable pageable) {
        String sortBy = pageable.getSort().toString().split(": ")[0];

        if (sortBy.equals("comments")) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Direction.DESC, "id");
            return this.entranceRepository.orderByComments(pageable);
        } else if (sortBy.equals("votes")) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Direction.DESC, "id");
            return this.entranceRepository.orderByVotes(pageable);
        }

        return this.entranceRepository.findAll(pageable);
    }

    public Page<EntranceEntity> getEntrancesByCommunityId(Long id, Pageable pageable)
            throws CommunityNotFoundException {
        if (!this.communityRepository.existsById(id)) {
            throw new CommunityNotFoundException();
        }

        String sortBy = pageable.getSort().toString().split(": ")[0];

        if (sortBy.equals("comments")) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Direction.DESC, "id");
            return this.entranceRepository.findByCommunityOrderByComments(id, pageable);
        } else if (sortBy.equals("votes")) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Direction.DESC, "id");
            return this.entranceRepository.findByCommunityOrderByVotes(id, pageable);
        }

        return this.entranceRepository.findByCommunityId(id, pageable);
    }

    public Page<EntranceEntity> getEntrancesByAccountId(Long id, Pageable pageable) throws AccountNotFoundException {
        if (!this.accountRepository.existsById(id)) {
            throw new AccountNotFoundException();
        }

        String sortBy = pageable.getSort().toString().split(": ")[0];

        if (sortBy.equals("comments")) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Direction.DESC, "id");
            return this.entranceRepository.findByAccountOrderByComments(id, pageable);
        } else if (sortBy.equals("votes")) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Direction.DESC, "id");
            return this.entranceRepository.findByAccountOrderByVotes(id, pageable);
        }

        return this.entranceRepository.findByAccountId(id, pageable);
    }

    public EntranceEntity getEntrance(Long id) throws EntranceNotFoundException {
        if (!this.entranceRepository.existsById(id)) {
            throw new EntranceNotFoundException();
        }

        return this.entranceRepository.findById(id).get();
    }

    public Page<EntranceEntity> getEntrancesByCommunities(List<CommunityEntity> communities, Pageable pageable) {
        String sortBy = pageable.getSort().toString().split(": ")[0];

        if (sortBy.equals("comments")) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Direction.DESC, "id");
            return this.entranceRepository.findByCommunitiesOrderByComments(communities, pageable);
        } else if (sortBy.equals("votes")) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Direction.DESC, "id");
            return this.entranceRepository.findByCommunitiesOrderByVotes(communities, pageable);
        }

        return this.entranceRepository.findByCommunityIn(communities, pageable);
    }

    public Page<EntranceEntity> getEntrancesByAccounts(List<AccountEntity> accounts, Pageable pageable) {
        String sortBy = pageable.getSort().toString().split(": ")[0];

        if (sortBy.equals("comments")) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Direction.DESC, "id");
            return this.entranceRepository.findByFollowingOrderByComments(accounts, pageable);
        } else if (sortBy.equals("votes")) {
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Direction.DESC, "id");
            return this.entranceRepository.findByFollowingOrderByVotes(accounts, pageable);
        }

        return this.entranceRepository.findByAccountIn(accounts, pageable);
    }

    public void createEntrance(Long id, EntranceBean entranceBean) throws IlegalEntranceArgumentsException,
            CommunityNotFoundException, InvalidSessionException, AccountNotFoundException {
        if (entranceBean == null || !entranceBean.isValid()) {
            throw new IlegalEntranceArgumentsException();
        }

        if (!this.communityRepository.existsById(id)) {
            throw new CommunityNotFoundException();
        }

        EntranceEntity entranceEntity = entranceBean.toEntity();
        entranceEntity.setAccount(this.accountRepository.findById(this.sessionService.getUser().getId()).get());
        entranceEntity.setCommunity(this.communityRepository.findById(id).get());
        entranceEntity.setCreatedAt(LocalDateTime.now());

        this.entranceRepository.save(entranceEntity);
    }

    public void updateEntrance(Long id, EntranceBean entranceBean)
            throws IlegalEntranceArgumentsException, EntranceNotFoundException, InvalidSessionException,
            AccountNotFoundException, InsufficientPrivilegesException {
        if (entranceBean == null || !entranceBean.isValid()) {
            throw new IlegalEntranceArgumentsException();
        }

        if (!this.entranceRepository.existsById(id)) {
            throw new EntranceNotFoundException();
        }

        EntranceEntity entranceEntity = this.entranceRepository.findById(id).get();

        if (!this.sessionService.itsMe(entranceEntity.getAccount().getId())) {
            throw new InsufficientPrivilegesException();
        }

        entranceEntity.setTitle(entranceBean.getTitle());
        entranceEntity.setBody(entranceBean.getBody());

        this.entranceRepository.save(entranceEntity);
    }

    public void deleteEntrance(Long id) throws EntranceNotFoundException, InvalidSessionException,
            AccountNotFoundException, InsufficientPrivilegesException {
        if (!this.entranceRepository.existsById(id)) {
            throw new EntranceNotFoundException();
        }

        if (!this.sessionService.getUser().isAdmin()
                && !this.sessionService.itsMe(this.entranceRepository.findById(id).get().getAccount().getId())) {
            throw new InsufficientPrivilegesException();
        }

        this.entranceRepository.deleteById(id);
    }
}
