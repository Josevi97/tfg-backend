package forum.combinedIds;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import forum.entities.CommunityEntity;

@Embeddable
public class CommunityListId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "community_id")
    CommunityEntity communityEntity;

    public CommunityListId() {
    }

    public CommunityListId(Long userId, CommunityEntity communityEntity) {
        this.userId = userId;
        this.communityEntity = communityEntity;
    }

    public Long getUserId() {
        return this.userId;
    }

    public CommunityEntity getCommunityEntity() {
        return this.communityEntity;
    }
}
