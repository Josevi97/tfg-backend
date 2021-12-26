package forum.exceptions;

import org.springframework.http.HttpStatus;

import forum.helpers.ApiResponse;

public class CommentNotFoundException extends ApiException {
    public CommentNotFoundException() {
        super(new ApiResponse("comment does not exists", HttpStatus.NOT_FOUND));
    }
}
