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
    @Query(value = "SELECT * FROM entrance ORDER BY (SELECT COUNT(*) FROM comment WHERE entrance.id = comment.entrance_id) DESC", nativeQuery = true)
    Page<EntranceEntity> orderByComments(Pageable pageable);

    @Query(value = "SELECT * FROM entrance ORDER BY (SELECT COUNT(*) FROM entrancevote WHERE entrance.id = entrancevote.entrance_id) DESC", nativeQuery = true)
    Page<EntranceEntity> orderByVotes(Pageable pageable);

    @Query(value = "SELECT * FROM entrance WHERE community_id IN ?1 ORDER BY (SELECT COUNT(*) FROM comment WHERE entrance.id = comment.entrance_id) DESC", nativeQuery = true)
    Page<EntranceEntity> findByCommunitiesOrderByComments(List<CommunityEntity> communities, Pageable pageable);

    @Query(value = "SELECT * FROM entrance WHERE community_id in ?1 ORDER BY (SELECT COUNT(*) FROM entrancevote WHERE entrance.id = entrancevote.entrance_id) DESC", nativeQuery = true)
    Page<EntranceEntity> findByCommunitiesOrderByVotes(List<CommunityEntity> communities, Pageable pageable);

    @Query(value = "SELECT * FROM entrance WHERE account_id IN ?1 ORDER BY (SELECT COUNT(*) FROM comment WHERE entrance.id = comment.entrance_id) DESC", nativeQuery = true)
    Page<EntranceEntity> findByFollowingOrderByComments(List<AccountEntity> accounts, Pageable pageable);

    @Query(value = "SELECT * FROM entrance WHERE account_id in ?1 ORDER BY (SELECT COUNT(*) FROM entrancevote WHERE entrance.id = entrancevote.entrance_id) DESC", nativeQuery = true)
    Page<EntranceEntity> findByFollowingOrderByVotes(List<AccountEntity> accounts, Pageable pageable);

    @Query(value = "SELECT * FROM entrance WHERE community_id = ?1 ORDER BY (SELECT COUNT(*) FROM comment WHERE entrance.id = comment.entrance_id) DESC", nativeQuery = true)
    Page<EntranceEntity> findByCommunityOrderByComments(Long id, Pageable pageable);

    @Query(value = "SELECT * FROM entrance WHERE community_id = ?1 ORDER BY (SELECT COUNT(*) FROM entrancevote WHERE entrance.id = entrancevote.entrance_id) DESC", nativeQuery = true)
    Page<EntranceEntity> findByCommunityOrderByVotes(Long id, Pageable pageable);

    @Query(value = "SELECT * FROM entrance WHERE account_id = ?1 ORDER BY (SELECT COUNT(*) FROM comment WHERE entrance.id = comment.entrance_id) DESC", nativeQuery = true)
    Page<EntranceEntity> findByAccountOrderByComments(Long id, Pageable pageable);

    @Query(value = "SELECT * FROM entrance WHERE account_id = ?1 ORDER BY (SELECT COUNT(*) FROM entrancevote WHERE entrance.id = entrancevote.entrance_id) DESC", nativeQuery = true)
    Page<EntranceEntity> findByAccountOrderByVotes(Long id, Pageable pageable);

    Page<EntranceEntity> findByCommunityIn(List<CommunityEntity> communities, Pageable pageable);

    Page<EntranceEntity> findByAccountIn(List<AccountEntity> accounts, Pageable pageable);

    Page<EntranceEntity> findByCommunityId(Long community_id, Pageable pageable);

    Page<EntranceEntity> findByAccountId(Long account_id, Pageable pageable);
}
