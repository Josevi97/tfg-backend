package forum.beans;

import forum.entities.AccountEntity;

public class RegisterAccountBean {
    private String login;
    private String email;
    private String originalPassword;
    private String repeatedPassword;

    public RegisterAccountBean() {
    }

    public RegisterAccountBean(String login, String email, String originalPassword, String repeatedPassword) {
        this.login = login;
        this.email = email;
        this.originalPassword = originalPassword;
        this.repeatedPassword = repeatedPassword;
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

    public String getLogin() {
        return this.login;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.originalPassword;
    }

    public boolean isValid() {
        return this.login != null && this.login.length() > 4 &&
                this.originalPassword != null && this.originalPassword.length() > 4 &&
                this.originalPassword.equals(this.repeatedPassword);
    }

    public AccountEntity toEntity() {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setLogin(this.login);
        accountEntity.setEmail(this.email);
        accountEntity.setPassword(this.originalPassword);

        return accountEntity;
    }
}
