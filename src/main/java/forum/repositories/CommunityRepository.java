package forum.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import forum.entities.CommunityEntity;

public interface CommunityRepository extends JpaRepository<CommunityEntity, Long> {
    @Query(value = "SELECT * FROM community ORDER BY RAND(), RAND()", nativeQuery = true)
    Page<CommunityEntity> random(Pageable pageable);

    public CommunityEntity findByName(String name);

    public boolean existsByName(String name);

    public Page<CommunityEntity> findByNameContaining(String name, Pageable pageable);
}
