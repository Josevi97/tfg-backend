package forum.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import forum.entities.CommunityEntity;

public interface CommunityRepository extends JpaRepository<CommunityEntity, Long> {
    public CommunityEntity findByName(String name);

    public boolean existsByName(String name);

    public Page<CommunityEntity> findByNameContaining(String name, Pageable pageable);
}
