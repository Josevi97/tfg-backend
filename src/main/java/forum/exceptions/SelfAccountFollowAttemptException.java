package forum.exceptions;

import org.springframework.http.HttpStatus;

import forum.helpers.ApiResponse;

public class SelfAccountFollowAttemptException extends ApiException {
    public SelfAccountFollowAttemptException() {
        super(new ApiResponse("invalid operation", HttpStatus.NOT_ACCEPTABLE));
    }
}
