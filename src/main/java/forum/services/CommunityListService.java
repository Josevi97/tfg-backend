package forum.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import forum.combinedIds.CommunityListId;
import forum.entities.CommunityListEntity;
import forum.exceptions.AccountNotFoundException;
import forum.exceptions.CommunityAlreadyFollowedException;
import forum.exceptions.CommunityNotFollowedException;
import forum.exceptions.CommunityNotFoundException;
import forum.exceptions.InvalidSessionException;
import forum.repositories.AccountRepository;
import forum.repositories.CommunityListRepository;
import forum.repositories.CommunityRepository;

@Service
public class CommunityListService {

    @Autowired
    SessionService sessionService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CommunityRepository communityRepository;

    @Autowired
    CommunityListRepository communityListRepository;

    public Page<CommunityListEntity> getCommunitiesByUserId(Long id, Pageable pageable)
            throws AccountNotFoundException {

        if (!this.accountRepository.existsById(id)) {
            throw new AccountNotFoundException();
        }

        return this.communityListRepository
                .findByCommunityListIdAccountEntity(this.accountRepository.findById(id).get(), pageable);
    }

    public void createFollow(Long id)
            throws CommunityNotFoundException, InvalidSessionException, AccountNotFoundException,
            CommunityAlreadyFollowedException {
        if (!this.communityRepository.existsById(id)) {
            throw new CommunityNotFoundException();
        }

        CommunityListId communityListId = new CommunityListId(
                this.sessionService.getUser(),
                this.communityRepository.findById(id).get());

        if (this.communityListRepository.existsById(communityListId)) {
            throw new CommunityAlreadyFollowedException();
        }

        this.communityListRepository.save(new CommunityListEntity(communityListId));
    }

    public void deleteFollow(Long id)
            throws CommunityNotFoundException, InvalidSessionException, AccountNotFoundException,
            CommunityNotFollowedException {
        if (!this.communityRepository.existsById(id)) {
            throw new CommunityNotFoundException();
        }

        CommunityListId communityListId = new CommunityListId(
                this.sessionService.getUser(),
                this.communityRepository.findById(id).get());

        if (!this.communityListRepository.existsById(communityListId)) {
            throw new CommunityNotFollowedException();
        }

        this.communityListRepository.deleteById(communityListId);
    }
}
