package forum.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import forum.combinedIds.CommentVoteId;
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

    public void createVote(Long id, boolean vote)
            throws CommentNotFoundException, AccountNotFoundException, InvalidSessionException,
            CommentVoteAlreadyExistsException {
        if (!this.commentRepository.existsById(id)) {
            throw new CommentNotFoundException();
        }

        CommentVoteId commentVoteId = new CommentVoteId(
                this.sessionService.getUser(),
                this.commentRepository.findById(id).get());

        if (this.commentVoteRepository.existsById(commentVoteId)) {
            throw new CommentVoteAlreadyExistsException();
        }

        CommentVoteEntity commentVoteEntity = new CommentVoteEntity();
        commentVoteEntity.setCommentVoteId(commentVoteId);
        commentVoteEntity.setVote(vote);

        this.commentVoteRepository.save(commentVoteEntity);
    }

    public void deleteVote(Long id)
            throws CommentNotFoundException, AccountNotFoundException, InvalidSessionException,
            CommentVoteNotFoundException {
        if (!this.commentRepository.existsById(id)) {
            throw new CommentNotFoundException();
        }

        CommentVoteId commentVoteId = new CommentVoteId(
                this.sessionService.getUser(),
                this.commentRepository.findById(id).get());

        if (!this.commentVoteRepository.existsById(commentVoteId)) {
            throw new CommentVoteNotFoundException();
        }

        this.commentVoteRepository.deleteById(commentVoteId);
    }
}
