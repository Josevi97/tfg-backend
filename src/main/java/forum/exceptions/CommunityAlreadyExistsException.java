package forum.exceptions;

import org.springframework.http.HttpStatus;

import forum.helpers.ApiResponse;

public class CommunityAlreadyExistsException extends ApiException {
    public CommunityAlreadyExistsException() {
        super(new ApiResponse("community already exists", HttpStatus.METHOD_NOT_ALLOWED));
    }
}
