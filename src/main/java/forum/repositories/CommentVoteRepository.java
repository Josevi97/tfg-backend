package forum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import forum.combinedIds.CommentVoteId;
import forum.entities.CommentVoteEntity;

public interface CommentVoteRepository extends JpaRepository<CommentVoteEntity, CommentVoteId> {
}
