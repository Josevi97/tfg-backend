package forum.helpers;

import forum.constants.EntranceConstants;

public class EntranceHelper {
    public static boolean isTitleValid(String title) {
        return title != null && title.length() >= EntranceConstants.MIN_TITLE_SIZE
                && title.length() <= EntranceConstants.MAX_TITLE_SIZE;
    }

    public static boolean isBodyValid(String body) {
        return body != null && body.length() >= EntranceConstants.MIN_BODY_SIZE
                && body.length() <= EntranceConstants.MAX_BODY_SIZE;
    }
}
