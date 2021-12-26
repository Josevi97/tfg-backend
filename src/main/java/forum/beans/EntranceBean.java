package forum.beans;

import forum.entities.EntranceEntity;
import forum.helpers.EntranceHelper;

public class EntranceBean {
    private String title;
    private String body;

    public EntranceBean() {
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return this.title;
    }

    public String getBody() {
        return this.body;
    }

    public boolean isValid() {
        return EntranceHelper.isTitleValid(this.title) && EntranceHelper.isBodyValid(this.body);
    }

    public EntranceEntity toEntity() {
        EntranceEntity entranceEntity = new EntranceEntity();
        entranceEntity.setTitle(this.title);
        entranceEntity.setBody(this.body);

        return entranceEntity;
    }
}
