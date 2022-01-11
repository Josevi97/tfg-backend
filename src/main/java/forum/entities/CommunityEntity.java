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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "community")
public class CommunityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, length = 255, nullable = false)
    private String name;

    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @Column(name = "color", length = 7, nullable = false)
    private String color;

    @Column(name = "image", length = 255, nullable = true)
    private String image;

    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "communityListId.communityEntity", cascade = CascadeType.REMOVE)
    private List<CommunityListEntity> communityList = new ArrayList<>();

    @OneToMany(mappedBy = "community", cascade = CascadeType.REMOVE)
    private List<EntranceEntity> entrances = new ArrayList<>();

    public CommunityEntity() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getColor() {
        return this.color;
    }

    public String getImage() {
        return this.image;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public int getCommunityList() {
        return this.communityList.size();
    }

    public int getEntrances() {
        return this.entrances.size();
    }
}
