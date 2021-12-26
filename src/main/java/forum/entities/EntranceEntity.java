package forum.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "entrance")
public class EntranceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "body", length = 255, nullable = false)
    private String body;

    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    AccountEntity account;

    @ManyToOne
    @JoinColumn(name = "community_id", nullable = false)
    CommunityEntity community;

    public EntranceEntity() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public void setCommunity(CommunityEntity community) {
        this.community = community;
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getBody() {
        return this.body;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public AccountEntity getAccount() {
        return this.account;
    }

    public CommunityEntity getCommunity() {
        return this.community;
    }
}
