package forum.exceptions;

import org.springframework.http.HttpStatus;

import forum.helpers.ApiResponse;

public class IlegalFileExtensionException extends ApiException {
    public IlegalFileExtensionException() {
        super(new ApiResponse("ilegal file extension", HttpStatus.NOT_ACCEPTABLE));
    }
}
