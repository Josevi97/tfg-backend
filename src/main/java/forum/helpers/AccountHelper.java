package forum.helpers;

import forum.constants.AccountConstants;

public class AccountHelper {
    public static boolean isLoginValid(String login) {
        return login != null && login.length() > AccountConstants.MIN_LOGIN_SIZE;
    }

    public static boolean isEmailValid(String email) {
        return true;
    }

    public static boolean isPasswordValid(String password) {
        return password != null && password.length() > AccountConstants.MIN_PASSWORD_SIZE;
    }

    public static boolean isUsernameValid(String username) {
        return username != null && username.length() > AccountConstants.MIN_USERNAME_SIZE;
    }

    public static boolean isPasswordValid(String originalPassword, String repeatedPassword) {
        return originalPassword != null && originalPassword.equals(repeatedPassword)
                && originalPassword.length() > AccountConstants.MIN_PASSWORD_SIZE;
    }
}
