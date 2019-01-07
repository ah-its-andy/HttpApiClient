package io.standardcore.http;

import io.standardcore.http.collection.NameValueCollection;

import java.lang.reflect.Method;
import java.util.List;

public interface UnmarshallInfo {
    int statusCode();
    byte[] payload();
    NameValueCollection<String, String> headers();
    List<UnmarshallWare> unmarshallWares();
    Unmarshaller unmarshaller();
    Class<?> httpServiceType();
    Method invokingMethod();
    Class<?> returningType();

    default ApiResponse unmarshall(){
        return unmarshaller().unmarshall(this);
    }
}
