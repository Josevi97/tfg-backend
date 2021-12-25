package forum.beans;

import forum.entities.CommunityEntity;
import forum.helpers.CommunityHelper;

public class CommunityBean {
    private String name;
    private String description;
    private String color;

    public CommunityBean() {
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

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getColor() {
        return this.color;
    }

    public boolean isValid() {
        return CommunityHelper.isNameValid(this.name) && CommunityHelper.isDescriptionValid(this.description)
                && CommunityHelper.isColorValid(this.color);
    }

    public CommunityEntity toEntity() {
        CommunityEntity communityEntity = new CommunityEntity();
        communityEntity.setName(this.name);
        communityEntity.setDescription(this.description);
        communityEntity.setColor(this.color);

        return communityEntity;
    }
}
