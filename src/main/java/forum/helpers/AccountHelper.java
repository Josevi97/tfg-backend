package forum.helpers;

import forum.entities.AccountEntity;

public class AccountHelper {
    public static final String IMAGES_ROUTE = "/assets/accounts/images";
    public static final String DEFAULT_IMAGE_ROUTE = IMAGES_ROUTE + "/default_000.png";
    public static final int MIN_LOGIN_SIZE = 4;
    public static final int MIN_USERNAME_SIZE = 4;
    public static final int MIN_PASSWORD_SIZE = 4;

    public static boolean isLoginValid(String login) {
        return login != null && login.length() > MIN_LOGIN_SIZE;
    }

    public static boolean isEmailValid(String email) {
        return true;
    }

    public static boolean isPasswordValid(String password) {
        return password != null && password.length() > MIN_PASSWORD_SIZE;
    }

    public static boolean isPasswordValid(String originalPassword, String repeatedPassword) {
        return originalPassword.equals(repeatedPassword) && originalPassword != null
                && originalPassword.length() > MIN_PASSWORD_SIZE;
    }

    public static AccountEntity getFixedAccountEntity(AccountEntity accountEntity) {
        if (accountEntity.getAvatar() == null) {
            accountEntity.setAvatar(DEFAULT_IMAGE_ROUTE);
        }

        return accountEntity;
    }
}
