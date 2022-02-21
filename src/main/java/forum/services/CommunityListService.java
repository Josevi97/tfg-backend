package forum.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import forum.combinedIds.CommunityListId;
import forum.entities.CommunityEntity;
import forum.entities.CommunityListEntity;
import forum.exceptions.AccountNotFoundException;
import forum.exceptions.CommunityAlreadyFollowedException;
import forum.exceptions.CommunityNotFollowedException;
import forum.exceptions.CommunityNotFoundException;
import forum.exceptions.InvalidSessionException;
import forum.repositories.AccountRepository;
import forum.repositories.CommunityListRepository;
import forum.repositories.CommunityRepository;
import forum.repositories.EntranceRepository;

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

    @Autowired
    EntranceRepository entranceRepository;

    public Page<CommunityListEntity> getCommunitiesByUserId(Long id, Pageable pageable)
            throws AccountNotFoundException {

        return this.communityListRepository
                .findByCommunityListIdAccountEntity(
                        this.accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException()),
                        pageable);
    }

    public List<CommunityEntity> getCommunitiesBySession()
            throws InvalidSessionException, AccountNotFoundException {

        return this.communityListRepository.findByCommunityListIdAccountEntity(this.sessionService.getUser())
                .stream()
                .map(CommunityListEntity::getCommunity)
                .collect(Collectors.toList());
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

    public CommunityEntity checkFollowOfSession(CommunityEntity communityEntity) {
        int value = -1;

        if (this.communityRepository.existsById(communityEntity.getId())) {
            try {
                CommunityListId communityListId = new CommunityListId(
                        this.sessionService.getUser(),
                        communityEntity);

                if (this.communityListRepository.existsById(communityListId)) {
                    value = 0;
                }
            } catch (InvalidSessionException | AccountNotFoundException e) {
            }
        }

        communityEntity.setSessionFollow(value);
        return communityEntity;
    }

    public Page<CommunityEntity> checkVoteOfSession(Page<CommunityEntity> communities) {
        communities.forEach(community -> this.checkFollowOfSession(community));
        return communities;
    }
}
