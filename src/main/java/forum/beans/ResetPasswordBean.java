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
        boolean bOpass = this.originalPassword != null
                && AccountHelper.isPasswordValid(this.originalPassword);
        boolean bEpass = this.originalPassword.equals(this.repeatedPassword);

        return bOpass && bEpass;
    }
}
