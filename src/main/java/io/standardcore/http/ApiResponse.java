package io.standardcore.http;

import io.standardcore.http.collection.NameValueCollection;

public interface ApiResponse {
    int statusCode();
    Object payload();
    NameValueCollection<String, String> headers();
}
