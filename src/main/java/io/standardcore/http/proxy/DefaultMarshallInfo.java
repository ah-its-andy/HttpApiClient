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
    private String httpSchema;
    private String serviceHost;
    private int port;
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

    protected void setHttpSchema(String httpSchema) {
        this.httpSchema = httpSchema;
    }

    protected void setPort(int port) {
        this.port = port;
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
    public String getHttpSchema() {
        return httpSchema;
    }

    @Override
    public String getServiceHost() {
        return serviceHost;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getServiceUrl() {
        return serviceUrl;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public Object getPayload() {
        return payload;
    }

    @Override
    public NameValueCollection<String, Object> getHeaders() {
        return headers;
    }

    @Override
    public NameValueCollection<String, Object> getQueryStrings() {
        return queryStrings;
    }

    @Override
    public List<MarshallWare> getMarshallWares() {
        return Collections.unmodifiableList(marshallWares);
    }

    @Override
    public Marshaller getMarshaller() {
        return marshaller;
    }

    @Override
    public Class<?> getHttpServiceType() {
        return httpServiceType;
    }

    @Override
    public Method getInvokingMethod() {
        return invokingMethod;
    }
}
