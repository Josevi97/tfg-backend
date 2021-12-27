package forum.entities;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import forum.combinedIds.EntranceVoteId;

@Entity
@Table(name = "entrancevote")
public class EntranceVoteEntity {

    @EmbeddedId
    private EntranceVoteId entranceVoteId;

    @Column(name = "vote", nullable = false)
    private boolean vote;

    public EntranceVoteEntity() {
    }

    public void setEntranceVoteId(EntranceVoteId entranceVoteId) {
        this.entranceVoteId = entranceVoteId;
    }

    public void setVote(boolean vote) {
        this.vote = vote;
    }

    public AccountEntity getAccount() {
        return this.entranceVoteId.getAccount();
    }

    public EntranceEntity getEntrance() {
        return this.entranceVoteId.getEntrance();
    }

    public boolean getVote() {
        return this.vote;
    }
}
