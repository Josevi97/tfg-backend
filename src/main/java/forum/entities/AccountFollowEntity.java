package forum.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import forum.combinedIds.AccountFollowId;

@Entity
@Table(name = "accountfollow")
public class AccountFollowEntity {

    @EmbeddedId
    AccountFollowId accountFollowId;

    public AccountFollowEntity() {
    }

    public void setAccountFollowId(AccountFollowId accountFollowId) {
        this.accountFollowId = accountFollowId;
    }

    public AccountEntity getFrom() {
        return this.accountFollowId.getFrom();
    }

    public AccountEntity getTo() {
        return this.accountFollowId.getTo();
    }
}
