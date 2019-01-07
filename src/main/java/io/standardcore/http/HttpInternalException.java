package io.standardcore.http;

public class HttpInternalException extends RuntimeException {
    private final ApiResponse response;

    public HttpInternalException(ApiResponse response){
        this.response = response;
    }

    public ApiResponse getResponse() {
        return response;
    }
}
