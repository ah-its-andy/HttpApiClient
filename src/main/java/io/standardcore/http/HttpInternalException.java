package io.standardcore.http;

public class HttpInternalException extends RuntimeException {
    public HttpInternalException(String message){
        super(message);
    }

    public HttpInternalException(String message, Throwable innerException){
        super(message, innerException);
    }
}
