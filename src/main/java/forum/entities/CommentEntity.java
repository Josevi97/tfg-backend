package forum.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "body", length = 255, nullable = false, updatable = false)
    private String body;

    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    @ManyToOne
    @JoinColumn(name = "entrance_id", nullable = true)
    private EntranceEntity entrance;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = true)
    private CommentEntity comment;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    private List<CommentEntity> responses = new ArrayList<>();

    @OneToMany(mappedBy = "commentVoteId.comment", cascade = CascadeType.REMOVE)
    private List<CommentVoteEntity> votes = new ArrayList<>();

    public CommentEntity() {
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

    public void setEntrance(EntranceEntity entrance) {
        this.entrance = entrance;
    }

    public void setComment(CommentEntity comment) {
        this.comment = comment;
    }

    public Long getId() {
        return this.id;
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

    public EntranceEntity getEntrance() {
        return this.entrance;
    }

    public CommentEntity getComment() {
        return this.comment;
    }

    public int getResponses() {
        return this.responses.size();
    }

    public int getVotes() {
        return this.votes.size();
    }

    public Long getCalculatedVotes() {
        Long plus = this.votes.stream().filter(vote -> vote.getVote()).count();
        return plus - (this.votes.size() - plus);
    }
}
