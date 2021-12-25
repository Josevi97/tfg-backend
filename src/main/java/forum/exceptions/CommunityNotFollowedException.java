package forum.exceptions;

import org.springframework.http.HttpStatus;

import forum.helpers.ApiResponse;

public class CommunityNotFollowedException extends ApiException {
    public CommunityNotFollowedException() {
        super(new ApiResponse("community is not followed", HttpStatus.METHOD_NOT_ALLOWED));
    }
}
