package forum.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import forum.combinedIds.CommunityListId;

@Entity
@Table(name = "communitylist")
public class CommunityListEntity {

    @EmbeddedId
    private CommunityListId communityListId;

    public CommunityListEntity() {
    }

    public CommunityListEntity(CommunityListId communityListId) {
        this.communityListId = communityListId;
    }

    public CommunityEntity getCommunity() {
        return this.communityListId.getCommunityEntity();
    }
}
