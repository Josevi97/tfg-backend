package forum.exceptions;

import org.springframework.http.HttpStatus;

import forum.helpers.ApiResponse;

public class AccountFollowAlreadyExistsException extends ApiException {
    public AccountFollowAlreadyExistsException() {
        super(new ApiResponse("account follow already exists", HttpStatus.METHOD_NOT_ALLOWED));
    }
}
