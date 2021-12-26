package forum.exceptions;

import org.springframework.http.HttpStatus;

import forum.helpers.ApiResponse;

public class EntranceNotFoundException extends ApiException {
    public EntranceNotFoundException() {
        super(new ApiResponse("entrance does not exists", HttpStatus.NOT_FOUND));
    }
}
