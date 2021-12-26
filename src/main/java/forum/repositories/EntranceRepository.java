package forum.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import forum.entities.EntranceEntity;

public interface EntranceRepository extends JpaRepository<EntranceEntity, Long> {
    Page<EntranceEntity> findByCommunityId(Long community_id, Pageable pageable);

    Page<EntranceEntity> findByAccountId(Long account_id, Pageable pageable);
}
