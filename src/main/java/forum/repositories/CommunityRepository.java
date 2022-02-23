package forum.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import forum.entities.CommunityEntity;

public interface CommunityRepository extends JpaRepository<CommunityEntity, Long> {
    @Query(value = "SELECT * FROM community WHERE id not in ?1 ORDER BY RAND(), RAND()", nativeQuery = true)
    Page<CommunityEntity> random(List<Long> blackList, Pageable pageable);

    public CommunityEntity findByName(String name);

    public boolean existsByName(String name);

    public Page<CommunityEntity> findByNameContaining(String name, Pageable pageable);
}
