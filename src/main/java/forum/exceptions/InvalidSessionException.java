package forum.exceptions;

import org.springframework.http.HttpStatus;

import forum.helpers.ApiResponse;

public class InvalidSessionException extends ApiException {
    public InvalidSessionException() {
        super(new ApiResponse("there is not any session stored", HttpStatus.UNAUTHORIZED));
    }
}
