package forum.exceptions;

import org.springframework.http.HttpStatus;

import forum.helpers.ApiResponse;

public class IlegalCommunityArgumentsException extends ApiException {
    public IlegalCommunityArgumentsException() {
        super(new ApiResponse("invalid data", HttpStatus.NOT_ACCEPTABLE));
    }
}
