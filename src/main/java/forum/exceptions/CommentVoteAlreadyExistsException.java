package forum.exceptions;

import org.springframework.http.HttpStatus;

import forum.helpers.ApiResponse;

public class CommentVoteAlreadyExistsException extends ApiException {
    public CommentVoteAlreadyExistsException() {
        super(new ApiResponse("comment vote already exists", HttpStatus.METHOD_NOT_ALLOWED));
    }
}
