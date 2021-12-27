package forum.exceptions;

import org.springframework.http.HttpStatus;

import forum.helpers.ApiResponse;

public class EntranceVoteNotFoundException extends ApiException {
    public EntranceVoteNotFoundException() {
        super(new ApiResponse("entrance vote does not exists", HttpStatus.NOT_FOUND));
    }
}
