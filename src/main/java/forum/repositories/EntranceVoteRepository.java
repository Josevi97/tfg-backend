package forum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import forum.combinedIds.EntranceVoteId;
import forum.entities.EntranceVoteEntity;

public interface EntranceVoteRepository extends JpaRepository<EntranceVoteEntity, EntranceVoteId> {
}
