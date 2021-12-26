package forum.combinedIds;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import forum.entities.AccountEntity;
import forum.entities.CommunityEntity;

@Embeddable
public class CommunityListId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "account_id")
    private AccountEntity accountEntity;

    @ManyToOne
    @JoinColumn(name = "community_id")
    private CommunityEntity communityEntity;

    public CommunityListId() {
    }

    public CommunityListId(AccountEntity accountEntity, CommunityEntity communityEntity) {
        this.accountEntity = accountEntity;
        this.communityEntity = communityEntity;
    }

    public AccountEntity getAccountEntity() {
        return this.accountEntity;
    }

    public CommunityEntity getCommunityEntity() {
        return this.communityEntity;
    }
}
