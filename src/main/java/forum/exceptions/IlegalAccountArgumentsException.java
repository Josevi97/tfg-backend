package forum.exceptions;

import org.springframework.http.HttpStatus;

import forum.helpers.ApiResponse;

public class IlegalAccountArgumentsException extends ApiException {
    public IlegalAccountArgumentsException() {
        super(new ApiResponse("invalid data", HttpStatus.NOT_ACCEPTABLE));
    }
}
