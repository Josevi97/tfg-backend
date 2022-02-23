package forum.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import forum.entities.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    // This one does not work because I cannot name tables
    @Query(value = "SELECT * FROM comment WHERE account_id = ?1 ORDER BY (SELECT COUNT(*) FROM comment WHERE comment.id = comment_id) DESC", nativeQuery = true)
    Page<CommentEntity> findByAccountOrderByComments(Long id, Pageable pageable);

    @Query(value = "SELECT * FROM comment WHERE account_id = ?1 ORDER BY (SELECT COUNT(*) FROM commentvote WHERE comment.id = commentvote.comment_id) DESC", nativeQuery = true)
    Page<CommentEntity> findByAccountOrderByVotes(Long id, Pageable pageable);

    Page<CommentEntity> findByAccountId(Long account_id, Pageable pageable);

    Page<CommentEntity> findByEntranceId(Long entrance_id, Pageable pageable);

    Page<CommentEntity> findByCommentId(Long comment_id, Pageable pageable);
}
