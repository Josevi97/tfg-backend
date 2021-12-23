package forum.exceptions;

import org.springframework.http.HttpStatus;

import forum.helpers.ApiResponse;

public class InvalidAccountException extends ApiException {
    public InvalidAccountException() {
        super(new ApiResponse("invalid data", HttpStatus.NOT_ACCEPTABLE));
    }
}
