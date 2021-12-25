package forum.helpers;

public class CommunityHelper {
    public static final int MIN_NAME_SIZE = 4;
    public static final String COLOR_FORMAT = "^#([0-9]|[a-f]|[A-F]){6}$";

    public static boolean isNameValid(String name) {
        return name != null && name.length() > MIN_NAME_SIZE;
    }

    public static boolean isDescriptionValid(String description) {
        return description != null;
    }

    public static boolean isColorValid(String color) {
        return color != null && color.matches(COLOR_FORMAT);
    }
}
