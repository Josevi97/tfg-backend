package forum.exceptions;

import org.springframework.http.HttpStatus;

import forum.helpers.ApiResponse;

public class EntranceVoteAlreadyExistsException extends ApiException {
    public EntranceVoteAlreadyExistsException() {
        super(new ApiResponse("entrance vote already exists", HttpStatus.METHOD_NOT_ALLOWED));
    }
}
