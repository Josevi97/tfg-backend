package forum.services;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import forum.beans.LoginBean;
import forum.entities.AccountEntity;
import forum.exceptions.AccountNotFoundException;
import forum.exceptions.IlegalLoginArgumentsException;
import forum.exceptions.InvalidSessionException;
import forum.repositories.AccountRepository;

@Service
public class SessionService {
    public static final String USER_ATTR = "USER_ATTR";

    @Autowired
    HttpSession http;

    @Autowired
    AccountRepository accountRepository;

    public AccountEntity getUser() throws InvalidSessionException, AccountNotFoundException {
        if (this.http.getAttribute(USER_ATTR) == null) {
            throw new InvalidSessionException();
        }

        if (!this.accountRepository.existsById((Long) this.http.getAttribute(USER_ATTR))) {
            this.http.invalidate();
            throw new AccountNotFoundException();
        }

        return this.accountRepository.findById(
                (Long) this.http.getAttribute(USER_ATTR)).get();
    }

    public void connect(LoginBean loginBean) throws IlegalLoginArgumentsException, AccountNotFoundException {
        if (!loginBean.isValid()) {
            throw new IlegalLoginArgumentsException();
        }

        if (!this.accountRepository.existsByLoginAndPassword(loginBean.getLogin(), loginBean.getPassword())) {
            throw new AccountNotFoundException();
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

    public boolean itsMe(Long id) throws InvalidSessionException, AccountNotFoundException {
        return this.getUser().getId().equals(id);
    }

    public boolean isAdmin() throws InvalidSessionException, AccountNotFoundException {
        return this.getUser().isAdmin();
    }
}
