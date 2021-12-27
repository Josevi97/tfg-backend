package forum.combinedIds;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import forum.entities.AccountEntity;
import forum.entities.CommentEntity;

@Embeddable
public class CommentVoteId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private CommentEntity comment;

    public CommentVoteId() {
    }

    public CommentVoteId(AccountEntity account, CommentEntity comment) {
        this.account = account;
        this.comment = comment;
    }

    public AccountEntity getAccount() {
        return this.account;
    }

    public CommentEntity getComment() {
        return this.comment;
    }
}
