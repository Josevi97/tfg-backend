package forum.combinedIds;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import forum.entities.AccountEntity;
import forum.entities.EntranceEntity;

@Embeddable
public class EntranceVoteId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    @ManyToOne
    @JoinColumn(name = "entrance_id", nullable = false)
    private EntranceEntity entrance;

    public EntranceVoteId() {
    }

    public EntranceVoteId(AccountEntity account, EntranceEntity entrance) {
        this.account = account;
        this.entrance = entrance;
    }

    public AccountEntity getAccount() {
        return this.account;
    }

    public EntranceEntity getEntrance() {
        return this.entrance;
    }
}
