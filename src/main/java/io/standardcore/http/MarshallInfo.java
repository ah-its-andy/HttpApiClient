package io.standardcore.http;

import io.standardcore.http.collection.NameValueCollection;

import java.lang.reflect.Method;
import java.util.List;

public interface MarshallInfo {
    String serviceHost();
    String serviceUrl();
    String method();
    String contentType();
    Object payload();
    NameValueCollection<String, Object> headers();
    NameValueCollection<String, Object> queryStrings();
    List<MarshallWare> marshallWares();
    Marshaller marshaller();
    Class<?> httpServiceType();
    Method invokingMethod();

    default ApiRequest marshall(){
        return marshaller().marshall(this);
    }
}
