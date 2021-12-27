package forum.combinedIds;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import forum.entities.AccountEntity;

@Embeddable
public class AccountFollowId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "from_id", nullable = false)
    private AccountEntity from;

    @ManyToOne
    @JoinColumn(name = "to_id", nullable = false)
    private AccountEntity to;

    public AccountFollowId() {
    }

    public AccountFollowId(AccountEntity from, AccountEntity to) {
        this.from = from;
        this.to = to;
    }

    public AccountEntity getFrom() {
        return this.from;
    }

    public AccountEntity getTo() {
        return this.to;
    }
}
