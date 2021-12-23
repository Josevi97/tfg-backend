package forum.exceptions;

import org.springframework.http.HttpStatus;

import forum.helpers.ApiResponse;

public class AccountAlreadyExistsException extends ApiException {
    public AccountAlreadyExistsException(String message) {
        super(new ApiResponse("account already exists: " + message, HttpStatus.METHOD_NOT_ALLOWED));
    }
}
