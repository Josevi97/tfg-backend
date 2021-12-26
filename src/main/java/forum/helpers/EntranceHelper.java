package forum.helpers;

public class EntranceHelper {
    public static final int MIN_TITLE_SIZE = 4;
    public static final int MIN_BODY_SIZE = 4;

    public static boolean isTitleValid(String title) {
        return title != null && title.length() > MIN_TITLE_SIZE;
    }

    public static boolean isBodyValid(String body) {
        return body != null && body.length() > MIN_BODY_SIZE;
    }
}
