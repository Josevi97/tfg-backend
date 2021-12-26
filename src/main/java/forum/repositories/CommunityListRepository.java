package forum.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import forum.combinedIds.CommunityListId;
import forum.entities.AccountEntity;
import forum.entities.CommunityListEntity;

public interface CommunityListRepository extends JpaRepository<CommunityListEntity, CommunityListId> {
    Page<CommunityListEntity> findByCommunityListIdAccountEntity(AccountEntity accountEntity, Pageable pegeable);
}
