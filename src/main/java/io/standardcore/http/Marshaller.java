package io.standardcore.http;

public interface Marshaller {
    ApiRequest marshall(MarshallInfo marshallInfo);
}
