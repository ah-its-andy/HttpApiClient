package io.standardcore.http;

import io.standardcore.http.collection.NameValueCollection;


public interface ApiRequest {
    String httpSchema();
    String serviceHost();
    int port();
    String serviceUrl();
    String method();
    NameValueCollection<String, String> headers();
    NameValueCollection<String, Object> queryStrings();
    byte[] payload();

    String buildUrlWithQueryStrings();
}
