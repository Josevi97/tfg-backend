package forum.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import forum.beans.CommentBean;
import forum.entities.CommentEntity;
import forum.exceptions.AccountNotFoundException;
import forum.exceptions.CommentNotFoundException;
import forum.exceptions.EntranceNotFoundException;
import forum.exceptions.IlegalCommentArguments;
import forum.exceptions.InsufficientPrivilegesException;
import forum.exceptions.InvalidSessionException;
import forum.repositories.AccountRepository;
import forum.repositories.CommentRepository;
import forum.repositories.EntranceRepository;

@Service
public class CommentService {

    @Autowired
    SessionService sessionService;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    EntranceRepository entranceRepository;

    public Page<CommentEntity> getCommentsByAccountId(Long id, Pageable pageable) throws AccountNotFoundException {
        if (!this.accountRepository.existsById(id)) {
            throw new AccountNotFoundException();
        }

        return this.commentRepository.findByAccountId(this.accountRepository.findById(id).get().getId(), pageable);
    }

    public Page<CommentEntity> getCommentsByEntranceId(Long id, Pageable pageable) throws EntranceNotFoundException {
        if (!this.entranceRepository.existsById(id)) {
            throw new EntranceNotFoundException();
        }

        return this.commentRepository.findByEntranceId(this.entranceRepository.findById(id).get().getId(), pageable);
    }

    public Page<CommentEntity> getCommentsByCommentId(Long id, Pageable pageable) throws CommentNotFoundException {
        if (!this.commentRepository.existsById(id)) {
            throw new CommentNotFoundException();
        }

        return this.commentRepository.findByCommentId(this.commentRepository.findById(id).get().getId(), pageable);
    }

    public CommentEntity getComment(Long id) throws CommentNotFoundException {
        if (!this.commentRepository.existsById(id)) {
            throw new CommentNotFoundException();
        }

        return this.commentRepository.findById(id).get();
    }

    public void createCommentInEntrance(Long id, CommentBean commentBean)
            throws IlegalCommentArguments, EntranceNotFoundException, InvalidSessionException,
            AccountNotFoundException {
        if (commentBean == null || !commentBean.isValid()) {
            throw new IlegalCommentArguments();
        }

        if (!this.entranceRepository.existsById(id)) {
            throw new EntranceNotFoundException();
        }

        CommentEntity commentEntity = commentBean.toEntity();
        commentEntity.setAccount(this.accountRepository.findById(this.sessionService.getUser().getId()).get());
        commentEntity.setEntrance(this.entranceRepository.findById(id).get());
        commentEntity.setCreatedAt(LocalDateTime.now());

        this.commentRepository.save(commentEntity);
    }

    public void createCommentInComment(Long id, CommentBean commentBean)
            throws IlegalCommentArguments, CommentNotFoundException, InvalidSessionException,
            AccountNotFoundException {
        if (commentBean == null || !commentBean.isValid()) {
            throw new IlegalCommentArguments();
        }

        if (!this.commentRepository.existsById(id)) {
            throw new CommentNotFoundException();
        }

        CommentEntity commentEntity = commentBean.toEntity();
        commentEntity.setAccount(this.accountRepository.findById(this.sessionService.getUser().getId()).get());
        commentEntity.setComment(this.commentRepository.findById(id).get());
        commentEntity.setCreatedAt(LocalDateTime.now());

        this.commentRepository.save(commentEntity);
    }

    public void deleteComment(Long id) throws CommentNotFoundException, InvalidSessionException,
            AccountNotFoundException, InsufficientPrivilegesException {
        if (!this.commentRepository.existsById(id)) {
            throw new CommentNotFoundException();
        }

        if (!this.sessionService.isAdmin()
                && !this.sessionService.itsMe(this.commentRepository.findById(id).get().getAccount().getId())) {
            throw new InsufficientPrivilegesException();
        }

        this.commentRepository.deleteById(id);
    }
}
