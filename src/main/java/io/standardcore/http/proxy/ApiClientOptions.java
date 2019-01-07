package io.standardcore.http.proxy;

import io.standardcore.http.*;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class ApiClientOptions {
    public ApiClientOptions() {
        this.marshallWares = new LinkedList<>();
        this.unmarshallWares = new LinkedList<>();
    }

    private List<MarshallWare> marshallWares;
    private List<UnmarshallWare> unmarshallWares;
    private Marshaller marshaller;
    private Unmarshaller unmarshaller;
    private ServiceNameResolver serviceNameResolver;
    private HttpInvoker httpInvoker;

    public List<MarshallWare> getMarshallWares() {
        return Collections.unmodifiableList(marshallWares);
    }

    public void setMarshallWares(List<MarshallWare> marshallWares) {
        this.marshallWares.addAll(marshallWares);
    }

    public List<UnmarshallWare> getUnmarshallWares() {
        return Collections.unmodifiableList(unmarshallWares);
    }

    public void setUnmarshallWares(List<UnmarshallWare> unmarshallWares) {
        this.unmarshallWares.addAll(unmarshallWares);
    }

    public Marshaller getMarshaller() {
        return marshaller;
    }

    public void setMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    public Unmarshaller getUnmarshaller() {
        return unmarshaller;
    }

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    public ServiceNameResolver getServiceNameResolver() {
        return serviceNameResolver;
    }

    public void setServiceNameResolver(ServiceNameResolver serviceNameResolver) {
        this.serviceNameResolver = serviceNameResolver;
    }

    public HttpInvoker getHttpInvoker() {
        return httpInvoker;
    }

    public void setHttpInvoker(HttpInvoker httpInvoker) {
        this.httpInvoker = httpInvoker;
    }
}
