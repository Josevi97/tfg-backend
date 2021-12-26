package forum.beans;

import forum.entities.CommentEntity;
import forum.helpers.CommentHelper;

public class CommentBean {
    private String body;
    private Long commentId;

    public CommentBean() {
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getBody() {
        return this.body;
    }

    public Long getCommentId() {
        return this.commentId;
    }

    public boolean isValid() {
        return CommentHelper.isBodyValid(this.body);
    }

    public CommentEntity toEntity() {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setBody(this.body);

        return commentEntity;
    }
}
