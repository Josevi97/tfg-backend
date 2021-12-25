package forum.exceptions;

import org.springframework.http.HttpStatus;

import forum.helpers.ApiResponse;

public class CommunityNotFoundException extends ApiException {
    public CommunityNotFoundException() {
        super(new ApiResponse("community does not exists", HttpStatus.NOT_FOUND));
    }
}
