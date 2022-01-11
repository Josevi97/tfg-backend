package forum.beans;

import forum.entities.AccountEntity;
import forum.helpers.AccountHelper;

public class AccountBean {
    private String login;
    private String email;
    private String originalPassword;
    private String repeatedPassword;
    private String username;
    // Falta la imagen
    private boolean isAdmin;

    public AccountBean() {
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOriginalPassword(String originalPassword) {
        this.originalPassword = originalPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getLogin() {
        return this.login;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.originalPassword;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isAdmin() {
        return this.isAdmin;
    }

    public boolean isValid() {
        return AccountHelper.isLoginValid(this.login) && AccountHelper.isEmailValid(this.email) &&
                AccountHelper.isPasswordValid(this.originalPassword, this.repeatedPassword)
                && AccountHelper.isUsernameValid(this.username);
    }

    public AccountEntity toEntity() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setLogin(this.login);
        accountEntity.setEmail(this.email);
        accountEntity.setPassword(this.originalPassword);
        accountEntity.setUsername(this.username);
        // Falta la imagen que deberia ser un MultiCast o bien que sea el controlador el
        // que dirija esta interaccion
        accountEntity.setAdmin(this.isAdmin);

        return accountEntity;
    }
}
