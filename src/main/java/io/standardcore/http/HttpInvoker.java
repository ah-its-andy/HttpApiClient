package io.standardcore.http;

import java.io.IOException;

public interface HttpInvoker {
    HttpWebResponse invoke(ApiRequest request) throws IOException;
}
