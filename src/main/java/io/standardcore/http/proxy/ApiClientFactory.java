package io.standardcore.http.proxy;

import net.sf.cglib.proxy.Enhancer;


public class ApiClientFactory {
    private final ApiClientOptions apiClientOptions;

    public ApiClientFactory(ApiClientOptions apiClientOptions) {
        this.apiClientOptions = apiClientOptions;
    }

    public <T> T createClient(Class<T> classOfService){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(AbstractClient.class);
        enhancer.setInterfaces(new Class[]{classOfService});
        ApiClientProxy apiClientProxy = new ApiClientProxy(apiClientOptions, classOfService);
        enhancer.setCallback(apiClientProxy);
        return (T) enhancer.create();
    }
}
