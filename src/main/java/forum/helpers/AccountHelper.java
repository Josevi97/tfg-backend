package forum.helpers;

import forum.constants.AccountConstants;

public class AccountHelper {
    public static boolean isLoginValid(String login) {
        return login != null && login.length() >= AccountConstants.MIN_LOGIN_SIZE
                && login.length() <= AccountConstants.MAX_LOGIN_SIZE;
    }

    public static boolean isEmailValid(String email) {
        return email.matches("^[a-zA-Z0-9\\-_.]{3,}@[a-zA-Z]{3,}\\.[a-zA-Z]{2,}$")
                && email.length() >= AccountConstants.MIN_EMAIL_SIZE
                && email.length() <= AccountConstants.MAX_EMAIL_SIZE;
    }

    public static boolean isPasswordValid(String password) {
        return password != null && password.length() >= AccountConstants.MIN_PASSWORD_SIZE
                && password.length() <= AccountConstants.MAX_PASSWORD_SIZE;
    }

    public static boolean isUsernameValid(String username) {
        return username != null && username.length() >= AccountConstants.MIN_USERNAME_SIZE
                && username.length() <= AccountConstants.MAX_USERNAME_SIZE;
    }

    public static boolean isPasswordValid(String originalPassword, String repeatedPassword) {
        return originalPassword != null && originalPassword.equals(repeatedPassword)
                && originalPassword.length() >= AccountConstants.MIN_PASSWORD_SIZE
                && originalPassword.length() <= AccountConstants.MAX_PASSWORD_SIZE;
    }

    public static boolean isDescriptionValid(String description) {
        return description == null || description.length() <= AccountConstants.MAX_DESCRIPTION_SIZE;
    }
}
