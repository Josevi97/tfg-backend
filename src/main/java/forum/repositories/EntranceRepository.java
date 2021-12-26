package forum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import forum.entities.EntranceEntity;

public interface EntranceRepository extends JpaRepository<EntranceEntity, Long> {
}
