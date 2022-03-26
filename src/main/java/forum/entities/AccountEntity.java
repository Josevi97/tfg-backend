package forum.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

import forum.constants.AccountConstants;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.CascadeType;
import javax.persistence.Column;

@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", unique = true, length = AccountConstants.MAX_LOGIN_SIZE, nullable = false)
    private String login;

    @Column(name = "email", unique = true, length = AccountConstants.MAX_EMAIL_SIZE, nullable = false)
    private String email;

    @Column(name = "password", length = AccountConstants.MAX_PASSWORD_SIZE, nullable = false)
    private String password;

    @Column(name = "username", length = AccountConstants.MAX_USERNAME_SIZE, nullable = false)
    private String username;

    @Column(name = "description", length = AccountConstants.MAX_DESCRIPTION_SIZE, nullable = true)
    private String description;

    @Column(name = "avatar", length = 255, nullable = true)
    private String avatar;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin;

    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    @Column(name = "last_session_at", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime lastSessionAt;

    @OneToMany(mappedBy = "communityListId.accountEntity", cascade = CascadeType.REMOVE)
    private List<CommunityListEntity> communityList = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
    private List<EntranceEntity> entrances = new ArrayList<>();

    @OneToMany(mappedBy = "account", cascade = CascadeType.REMOVE)
    private List<CommentEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "accountFollowId.from", cascade = CascadeType.REMOVE)
    private List<AccountFollowEntity> following = new ArrayList<>();

    @OneToMany(mappedBy = "accountFollowId.to", cascade = CascadeType.REMOVE)
    private List<AccountFollowEntity> followers = new ArrayList<>();

    @OneToMany(mappedBy = "entranceVoteId.account", cascade = CascadeType.REMOVE)
    private List<EntranceVoteEntity> entranceVotes = new ArrayList<>();

    @OneToMany(mappedBy = "commentVoteId.account", cascade = CascadeType.REMOVE)
    private List<CommentVoteEntity> commentVotes = new ArrayList<>();

    @Transient
    private int sessionFollow;

    public AccountEntity() {
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastSessionAt(LocalDateTime lastSessionAt) {
        this.lastSessionAt = lastSessionAt;
    }

    public void setSessionFollow(int sessionFollow) {
        this.sessionFollow = sessionFollow;
    }

    public Long getId() {
        return this.id;
    }

    public String getLogin() {
        return this.login;
    }

    public String getEmail() {
        return this.email;
    }

    public String getUsername() {
        return this.username;
    }

    public String getDescription() {
        return this.description;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public Boolean isAdmin() {
        return this.isAdmin;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public LocalDateTime getLastSessionAt() {
        return this.lastSessionAt;
    }

    public int getCommunityList() {
        return this.communityList.size();
    }

    public int getEntrances() {
        return this.entrances.size();
    }

    public int getComments() {
        return this.comments.size();
    }

    public int getFollowing() {
        return this.following.size();
    }

    public int getFollowers() {
        return this.followers.size();
    }

    public int getEntranceVotes() {
        return this.entranceVotes.size();
    }

    public int getCommentVotes() {
        return this.commentVotes.size();
    }

    public int getSessionFollow() {
        return this.sessionFollow;
    }
}
