package forum.exceptions;

import org.springframework.http.HttpStatus;

import forum.helpers.ApiResponse;

public class IlegalCommentArguments extends ApiException {
    public IlegalCommentArguments() {
        super(new ApiResponse("invalid data", HttpStatus.NOT_ACCEPTABLE));
    }
}
