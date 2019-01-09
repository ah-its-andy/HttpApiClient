package io.standardcore.http;

import io.standardcore.http.collection.NameValueCollection;

import java.lang.reflect.Method;
import java.util.List;

public interface MarshallInfo {
    String getHttpSchema();
    String getServiceHost();
    int getPort();
    String getServiceUrl();
    String getMethod();
    String getContentType();
    Object getPayload();
    NameValueCollection<String, Object> getHeaders();
    NameValueCollection<String, Object> getQueryStrings();
    List<MarshallWare> getMarshallWares();
    Marshaller getMarshaller();
    Class<?> getHttpServiceType();
    Method getInvokingMethod();

    default ApiRequest marshall(){
        return getMarshaller().marshall(this);
    }
}
