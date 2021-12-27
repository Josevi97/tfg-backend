package forum.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import forum.combinedIds.AccountFollowId;

@Entity
@Table(name = "accountfollow")
public class AccountFollowEntity {

    @EmbeddedId
    AccountFollowId accountFollowId;

    @Column(name = "created_at", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    public AccountFollowEntity() {
    }

    public void setAccountFollowId(AccountFollowId accountFollowId) {
        this.accountFollowId = accountFollowId;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public AccountEntity getFrom() {
        return this.accountFollowId.getFrom();
    }

    public AccountEntity getTo() {
        return this.accountFollowId.getTo();
    }
}
