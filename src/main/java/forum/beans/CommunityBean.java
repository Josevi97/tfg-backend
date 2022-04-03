package forum.beans;

import forum.entities.CommunityEntity;
import forum.helpers.CommunityHelper;

public class CommunityBean {
    private String name;
    private String description;
    private boolean changeImage;

    public CommunityBean() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setChangeImage(boolean changeImage) {
        this.changeImage = changeImage;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean getChangeImage() {
        return this.changeImage;
    }

    public boolean isValid() {
        return CommunityHelper.isNameValid(this.name) && CommunityHelper.isDescriptionValid(this.description);
    }

    public CommunityEntity toEntity() {
        CommunityEntity communityEntity = new CommunityEntity();
        communityEntity.setName(this.name);
        communityEntity.setDescription(this.description);

        return communityEntity;
    }
}
