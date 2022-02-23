package forum.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import forum.entities.AccountEntity;
import forum.entities.CommunityEntity;
import forum.entities.EntranceEntity;

public interface EntranceRepository extends JpaRepository<EntranceEntity, Long> {
    Page<EntranceEntity> findByCommunityIn(List<CommunityEntity> communities, Pageable pageable);

    Page<EntranceEntity> findByAccountIn(List<AccountEntity> accounts, Pageable pageable);

    Page<EntranceEntity> findByCommunityId(Long community_id, Pageable pageable);

    Page<EntranceEntity> findByAccountId(Long account_id, Pageable pageable);
}
