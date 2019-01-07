package io.standardcore.http;

import io.standardcore.http.collection.NameValueCollection;


public interface ApiRequest {
    String serviceHost();
    String serviceUrl();
    String method();
    NameValueCollection<String, String> headers();
    NameValueCollection<String, Object> queryStrings();
    byte[] payload();
}
