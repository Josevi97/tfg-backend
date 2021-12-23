package forum.exceptions;

import org.springframework.http.HttpStatus;

import forum.helpers.ApiResponse;

public class AccountNotFoundException extends ApiException {
    public AccountNotFoundException() {
        super(new ApiResponse("account does not exists", HttpStatus.NOT_FOUND));
    }
}
