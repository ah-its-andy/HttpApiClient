package io.standardcore.http;

public interface Unmarshaller {
    ApiResponse unmarshall(UnmarshallInfo unmarshallInfo);
}
