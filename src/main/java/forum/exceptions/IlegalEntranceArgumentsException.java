package forum.exceptions;

import org.springframework.http.HttpStatus;

import forum.helpers.ApiResponse;

public class IlegalEntranceArgumentsException extends ApiException {
    public IlegalEntranceArgumentsException() {
        super(new ApiResponse("ilegal entrance arguments", HttpStatus.NOT_ACCEPTABLE));
    }
}
