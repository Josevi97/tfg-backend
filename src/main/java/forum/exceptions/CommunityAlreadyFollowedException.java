package forum.exceptions;

import org.springframework.http.HttpStatus;

import forum.helpers.ApiResponse;

public class CommunityAlreadyFollowedException extends ApiException {
    public CommunityAlreadyFollowedException() {
        super(new ApiResponse("community is already followed", HttpStatus.METHOD_NOT_ALLOWED));
    }
}
