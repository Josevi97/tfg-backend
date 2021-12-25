package forum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import forum.entities.CommunityEntity;

public interface CommunityRepository extends JpaRepository<CommunityEntity, Long> {
    public boolean existsByName(String name);
}
