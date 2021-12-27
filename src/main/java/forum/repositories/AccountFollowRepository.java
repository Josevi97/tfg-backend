package forum.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import forum.combinedIds.AccountFollowId;
import forum.entities.AccountEntity;
import forum.entities.AccountFollowEntity;

public interface AccountFollowRepository extends JpaRepository<AccountFollowEntity, AccountFollowId> {
    Page<AccountFollowEntity> findByAccountFollowIdFrom(AccountEntity from, Pageable pageable);

    Page<AccountFollowEntity> findByAccountFollowIdTo(AccountEntity to, Pageable pageable);
}
