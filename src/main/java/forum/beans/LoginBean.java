package forum.beans;

import forum.helpers.AccountHelper;

public class LoginBean {
    private String login;
    private String password;

    public LoginBean() {
    }

    public LoginBean(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isValid() {
        return AccountHelper.isLoginValid(this.login) && AccountHelper.isPasswordValid(password);
    }
}
