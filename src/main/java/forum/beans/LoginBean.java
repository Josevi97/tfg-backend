package forum.beans;

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
        return this.login != null && this.login.length() > 4 &&
                this.password != null && this.password.length() > 4;
    }
}
