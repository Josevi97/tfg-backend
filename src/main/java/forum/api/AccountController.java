package forum.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import forum.beans.AccountBean;
import forum.entities.AccountEntity;
import forum.exceptions.AccountAlreadyExistsException;
import forum.exceptions.AccountNotFoundException;
import forum.exceptions.InsufficientPrivilegesException;
import forum.exceptions.InvalidAccountException;
import forum.exceptions.InvalidSessionException;
import forum.helpers.ApiResponse;
import forum.services.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping
    public ResponseEntity<?> getAccounts(@PageableDefault(page = 0, size = 5) Pageable pageable) {
        try {
            return new ResponseEntity<Page<AccountEntity>>(
                    this.accountService.getAllAccounts(pageable),
                    HttpStatus.OK);
        } catch (InvalidSessionException | InsufficientPrivilegesException | AccountNotFoundException e) {
            return new ResponseEntity<ApiResponse>(
                    e.getApiResponse(),
                    e.getApiResponse().getStatus());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAccount(@PathVariable Long id) {
        try {
            return new ResponseEntity<AccountEntity>(
                    this.accountService.getAccount(id),
                    HttpStatus.OK);
        } catch (AccountNotFoundException e) {
            return new ResponseEntity<ApiResponse>(
                    e.getApiResponse(),
                    e.getApiResponse().getStatus());
        }
    }

    @PostMapping
    public ResponseEntity<?> createAccount(@RequestBody AccountBean accountBean) {
        ApiResponse response;

        try {
            this.accountService.createAccount(accountBean);
            response = new ApiResponse("account has been created", HttpStatus.OK);
        } catch (InvalidAccountException | AccountAlreadyExistsException | InvalidSessionException
                | InsufficientPrivilegesException | AccountNotFoundException e) {
            response = e.getApiResponse();
        }

        return new ResponseEntity<ApiResponse>(response, response.getStatus());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable Long id, @RequestBody AccountBean accountBean) {
        // Should update account but with its own bean, or maybe change
        // RegisterAccountBean utiliy to adapt it to be
        // well used as an update action or else.

        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        ApiResponse response;

        try {
            this.accountService.deleteAccount(id);
            response = new ApiResponse("account has been deleted", HttpStatus.OK);
        } catch (InvalidSessionException | InsufficientPrivilegesException | AccountNotFoundException e) {
            response = e.getApiResponse();
        }

        return new ResponseEntity<ApiResponse>(response, response.getStatus());
    }
}
