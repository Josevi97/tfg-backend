package forum.beans;

import forum.helpers.AccountHelper;

public class ResetPasswordBean {
    private String originalPassword;
    private String repeatedPassword;

    public ResetPasswordBean() {
    }

    public String getPassword() {
        return this.originalPassword;
    }

    public void setOriginalPassword(String originalPassword) {
        this.originalPassword = originalPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }

    public boolean isValid() {
        return AccountHelper.isPasswordValid(this.originalPassword, this.repeatedPassword);
    }
}
