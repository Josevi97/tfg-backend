package forum.helpers;

public class CommentHelper {
    public static final int MIN_BODY_SIZE = 4;

    public static boolean isBodyValid(String body) {
        return body != null && body.length() > MIN_BODY_SIZE;
    }
}
