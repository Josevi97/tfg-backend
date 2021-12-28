package forum.helpers;

import forum.constants.CommentConstants;

public class CommentHelper {
    public static boolean isBodyValid(String body) {
        return body != null && body.length() > CommentConstants.MIN_BODY_SIZE;
    }
}
