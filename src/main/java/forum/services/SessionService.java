package forum.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import forum.beans.LoginBean;
import forum.entities.AccountEntity;
import forum.exceptions.IlegalLoginArgumentsException;
import forum.exceptions.InvalidSessionException;
import forum.repositories.AccountRepository;

@Service
public class SessionService {
    private static final String USER_ATTR = "USER_ATTR";

    @Autowired
    HttpSession http;

    @Autowired
    AccountRepository accountRepository;

    public AccountEntity getUser() throws InvalidSessionException {
        if (this.http.getAttribute(USER_ATTR) == null) {
            throw new InvalidSessionException();
        }

        return this.accountRepository.findById(
                (Long) this.http.getAttribute(USER_ATTR)).get();
    }

    public void connect(LoginBean loginBean) throws IlegalLoginArgumentsException {
        if (!loginBean.isValid()) {
            throw new IlegalLoginArgumentsException();
        }

        this.http.setAttribute(
                USER_ATTR,
                this.accountRepository.findByLoginAndPassword(
                        loginBean.getLogin(),
                        loginBean.getPassword()).getId());
    }

    public void disconnect() throws InvalidSessionException {
        if (this.http.getAttribute(USER_ATTR) == null) {
            throw new InvalidSessionException();
        }

        this.http.invalidate();
    }

    public boolean itsMe(Long id) throws InvalidSessionException {
        return this.getUser().getId().equals(id);
    }

    public boolean isAdmin() throws InvalidSessionException {
        return this.getUser().isAdmin();
    }
}
