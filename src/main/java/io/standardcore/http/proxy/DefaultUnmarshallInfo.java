package io.standardcore.http.proxy;

import io.standardcore.http.UnmarshallInfo;
import io.standardcore.http.UnmarshallWare;
import io.standardcore.http.Unmarshaller;
import io.standardcore.http.collection.NameValueCollection;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

public class DefaultUnmarshallInfo implements UnmarshallInfo {
    private int statusCode;
    private byte[] payload;
    private NameValueCollection<String, String> headers;
    private List<UnmarshallWare> unmarshallWares;
    private Unmarshaller unmarshaller;
    private Class<?> httpServiceType;
    private Method invokingMethod;
    private Class<?> returningType;

    protected void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    protected void setPayload(byte[] payload) {
        this.payload = payload;
    }

    protected void setHeaders(NameValueCollection<String, String> headers) {
        this.headers = headers;
    }

    protected void setUnmarshallWares(List<UnmarshallWare> unmarshallWares) {
        this.unmarshallWares = unmarshallWares;
    }

    protected void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    protected void setHttpServiceType(Class<?> httpServiceType) {
        this.httpServiceType = httpServiceType;
    }

    protected void setInvokingMethod(Method invokingMethod) {
        this.invokingMethod = invokingMethod;
    }

    protected void setReturningType(Class<?> returningType) {
        this.returningType = returningType;
    }

    @Override
    public int statusCode() {
        return statusCode;
    }

    @Override
    public byte[] payload() {
        return payload;
    }

    @Override
    public NameValueCollection<String, String> headers() {
        return headers;
    }

    @Override
    public List<UnmarshallWare> unmarshallWares() {
        return Collections.unmodifiableList(unmarshallWares);
    }

    @Override
    public Unmarshaller unmarshaller() {
        return unmarshaller;
    }

    @Override
    public Class<?> httpServiceType() {
        return httpServiceType;
    }

    @Override
    public Method invokingMethod() {
        return invokingMethod;
    }

    @Override
    public Class<?> returningType() {
        return returningType;
    }
}
