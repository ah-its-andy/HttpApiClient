package io.standardcore.http;

public interface HttpInvoker {
    HttpWebResponse invoke(ApiRequest request);
}
