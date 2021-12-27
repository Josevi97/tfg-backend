package forum.exceptions;

import org.springframework.http.HttpStatus;

import forum.helpers.ApiResponse;

public class AccountFollowNotFoundException extends ApiException {
    public AccountFollowNotFoundException() {
        super(new ApiResponse("follow does not exists", HttpStatus.NOT_FOUND));
    }
}
