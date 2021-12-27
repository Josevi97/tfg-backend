package forum.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import forum.combinedIds.CommentVoteId;

@Entity
@Table(name = "commentvote")
public class CommentVoteEntity {

    @EmbeddedId
    private CommentVoteId commentVoteId;

    @Column(name = "vote", nullable = false)
    private boolean vote;

    public CommentVoteEntity() {
    }

    public void setCommentVoteId(CommentVoteId commentVoteId) {
        this.commentVoteId = commentVoteId;
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }

    public AccountEntity getAccount() {
        return this.commentVoteId.getAccount();
    }

    public CommentEntity getComment() {
        return this.commentVoteId.getComment();
    }

    public boolean getVote() {
        return this.vote;
    }
}
