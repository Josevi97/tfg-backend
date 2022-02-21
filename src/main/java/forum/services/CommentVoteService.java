package forum.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import forum.combinedIds.CommentVoteId;
import forum.entities.CommentEntity;
import forum.entities.CommentVoteEntity;
import forum.exceptions.AccountNotFoundException;
import forum.exceptions.CommentNotFoundException;
import forum.exceptions.CommentVoteAlreadyExistsException;
import forum.exceptions.CommentVoteNotFoundException;
import forum.exceptions.InvalidSessionException;
import forum.repositories.CommentRepository;
import forum.repositories.CommentVoteRepository;

@Service
public class CommentVoteService {

    @Autowired
    SessionService sessionService;

    @Autowired
    private CommentVoteRepository commentVoteRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Transactional
    public void createVote(Long id, boolean vote)
            throws CommentNotFoundException, AccountNotFoundException, InvalidSessionException,
            CommentVoteAlreadyExistsException {

        CommentVoteId commentVoteId = new CommentVoteId(
                this.sessionService.getUser(),
                this.commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException()));

        if (this.commentVoteRepository.existsById(commentVoteId)) {
            CommentVoteEntity commentVoteEntity = this.commentVoteRepository.findById(commentVoteId).get();

            if (commentVoteEntity.getVote() == vote) {
                this.commentVoteRepository.deleteById(commentVoteId);
            } else {
                commentVoteEntity.setVote(vote);
                this.commentVoteRepository.save(commentVoteEntity);
            }
        } else {
            CommentVoteEntity commentVoteEntity = new CommentVoteEntity();
            commentVoteEntity.setCommentVoteId(commentVoteId);
            commentVoteEntity.setVote(vote);

            this.commentVoteRepository.save(commentVoteEntity);
        }
    }

    public CommentEntity checkVoteOfSession(CommentEntity commentEntity) {
        int value = -1;

        if (this.commentRepository.existsById(commentEntity.getId())) {
            try {
                CommentVoteId commentVoteId = new CommentVoteId(
                        this.sessionService.getUser(),
                        commentEntity);

                if (this.commentVoteRepository.existsById(commentVoteId)) {
                    value = this.commentVoteRepository.findById(commentVoteId).get().getVote() ? 1 : 0;
                }
            } catch (InvalidSessionException | AccountNotFoundException e) {
            }
        }

        commentEntity.setSessionVoted(value);
        return commentEntity;
    }

    public Page<CommentEntity> checkVoteOfSession(Page<CommentEntity> comments) {
        comments.forEach(comment -> this.checkVoteOfSession(comment));
        return comments;
    }
}
