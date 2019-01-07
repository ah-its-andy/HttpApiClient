package io.standardcore.http;

import io.standardcore.http.proxy.ApiClientOptions;

import java.lang.reflect.Method;

public interface MarshallInfoResolver {
    MarshallInfo resolve(Class<?> serviceInterfaceType, Method methodInfo, ApiClientOptions options);
}
