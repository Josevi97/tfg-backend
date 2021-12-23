package forum.exceptions;

import org.springframework.http.HttpStatus;

import forum.helpers.ApiResponse;

public class InsufficientPrivilegesException extends ApiException {
    public InsufficientPrivilegesException() {
        super(new ApiResponse("you have not enough privileges", HttpStatus.NON_AUTHORITATIVE_INFORMATION));
    }
}
