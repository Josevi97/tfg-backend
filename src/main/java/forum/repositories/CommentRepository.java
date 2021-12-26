package forum.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import forum.entities.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Page<CommentEntity> findByAccountId(Long account_id, Pageable pageable);

    Page<CommentEntity> findByEntranceId(Long entrance_id, Pageable pageable);

    Page<CommentEntity> findByCommentId(Long comment_id, Pageable pageable);
}
