package io.standardcore.http.proxy;

import io.standardcore.http.MarshallInfo;
import io.standardcore.http.MarshallWare;
import io.standardcore.http.Marshaller;
import io.standardcore.http.collection.NameValueCollection;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DefaultMarshallInfo implements MarshallInfo {
    private String serviceHost;
    private String serviceUrl;
    private String method;
    private String contentType;
    private Object payload;
    private NameValueCollection<String, Object> headers;
    private NameValueCollection<String, Object> queryStrings;
    private Marshaller marshaller;
    private List<MarshallWare> marshallWares;
    private Class<?> httpServiceType;
    private Method invokingMethod;

    public DefaultMarshallInfo(){
        headers = new NameValueCollection<>();
        queryStrings = new NameValueCollection<>();
        marshallWares = new LinkedList<>();
    }

    protected void setServiceHost(String serviceHost) {
        this.serviceHost = serviceHost;
    }

    protected void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }

    protected void setMethod(String method) {
        this.method = method;
    }

    protected void setPayload(Object payload) {
        this.payload = payload;
    }

    protected void setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    protected void setMarshallWares(List<MarshallWare> marshallWares) {
        this.marshallWares.addAll(marshallWares);
    }

    protected void setHttpServiceType(Class<?> httpServiceType) {
        this.httpServiceType = httpServiceType;
    }

    protected void setInvokingMethod(Method invokingMethod) {
        this.invokingMethod = invokingMethod;
    }

    protected void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String serviceHost() {
        return serviceHost;
    }

    @Override
    public String serviceUrl() {
        return serviceUrl;
    }

    @Override
    public String method() {
        return method;
    }

    @Override
    public String contentType() {
        return contentType;
    }

    @Override
    public Object payload() {
        return payload;
    }

    @Override
    public NameValueCollection<String, Object> headers() {
        return headers;
    }

    @Override
    public NameValueCollection<String, Object> queryStrings() {
        return queryStrings;
    }

    @Override
    public List<MarshallWare> marshallWares() {
        return Collections.unmodifiableList(marshallWares);
    }

    @Override
    public Marshaller marshaller() {
        return marshaller;
    }

    @Override
    public Class<?> httpServiceType() {
        return httpServiceType;
    }

    @Override
    public Method invokingMethod() {
        return invokingMethod;
    }
}
