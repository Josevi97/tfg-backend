package forum.exceptions;

import org.springframework.http.HttpStatus;

import forum.helpers.ApiResponse;

public class IlegalLoginArgumentsException extends ApiException {
    public IlegalLoginArgumentsException() {
        super(new ApiResponse("ilegal login arguments", HttpStatus.NOT_ACCEPTABLE));
    }
}
