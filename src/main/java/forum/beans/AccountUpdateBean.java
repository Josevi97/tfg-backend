package forum.beans;

import forum.entities.AccountEntity;
import forum.helpers.AccountHelper;

public class AccountUpdateBean {
    private String login;
    private String email;
    private String username;
    private String description;
    // Falta la imagen
    private boolean isAdmin;

    public AccountUpdateBean() {
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getUsername() {
        return this.username;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isAdmin() {
        return this.isAdmin;
    }

    public boolean isValid() {
        return AccountHelper.isLoginValid(this.login) && AccountHelper.isEmailValid(this.email) &&
                AccountHelper.isUsernameValid(this.username);
    }

    public AccountEntity toEntity() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setLogin(this.login);
        accountEntity.setEmail(this.email);
        accountEntity.setUsername(this.username);
        accountEntity.setDescription(this.description);
        // Falta la imagen que deberia ser un MultiCast o bien que sea el controlador el
        // que dirija esta interaccion
        accountEntity.setAdmin(this.isAdmin);

        return accountEntity;
    }
}
