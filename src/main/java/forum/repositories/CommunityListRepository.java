package forum.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import forum.combinedIds.CommunityListId;
import forum.entities.AccountEntity;
import forum.entities.CommunityEntity;
import forum.entities.CommunityListEntity;

public interface CommunityListRepository extends JpaRepository<CommunityListEntity, CommunityListId> {
    Page<CommunityListEntity> findByCommunityListIdCommunityEntity(CommunityEntity communityEntity, Pageable pageable);

    Page<CommunityListEntity> findByCommunityListIdAccountEntity(AccountEntity accountEntity, Pageable pageable);

    List<CommunityListEntity> findByCommunityListIdAccountEntity(AccountEntity accountEntity);
}
