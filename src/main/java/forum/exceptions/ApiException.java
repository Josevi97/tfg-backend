package forum.exceptions;

import forum.helpers.ApiResponse;

public class ApiException extends Exception {
    private ApiResponse apiResponse;

    public ApiException(ApiResponse apiResponse) {
        this.apiResponse = apiResponse;
    }

    public ApiResponse getApiResponse() {
        return this.apiResponse;
    }
}
