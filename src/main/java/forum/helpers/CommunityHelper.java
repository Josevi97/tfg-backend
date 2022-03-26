package forum.helpers;

import forum.constants.CommunityConstants;

public class CommunityHelper {
    public static boolean isNameValid(String name) {
        return name != null && name.length() >= CommunityConstants.MIN_NAME_SIZE
                && name.length() <= CommunityConstants.MAX_NAME_SIZE;
    }

    public static boolean isDescriptionValid(String description) {
        return description == null || description.length() <= CommunityConstants.MAX_DESCRIPTION_SIZE;
    }
}
