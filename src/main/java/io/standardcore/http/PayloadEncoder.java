package io.standardcore.http;

public interface PayloadEncoder {
    boolean support(String contentType, Object payload);

    byte[] encode(String contentType, Object payload);
}
