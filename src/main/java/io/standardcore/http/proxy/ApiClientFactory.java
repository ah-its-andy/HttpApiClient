package io.standardcore.http.proxy;

import io.standardcore.http.MarshallWare;
import io.standardcore.http.Marshaller;
import io.standardcore.http.UnmarshallWare;
import io.standardcore.http.Unmarshaller;
import net.sf.cglib.proxy.Enhancer;

import java.util.List;

public class ApiClientFactory {
    private List<MarshallWare> marshallWares;
    private List<UnmarshallWare> unmarshallWares;
    private Marshaller defaultMarshaller;
    private Unmarshaller defaultUnmarshaller;

    public <T> T createClient(Class<T> classOfService){
        Enhancer enhancer = new Enhancer(); //创建加强器，用来创建动态代理类
        enhancer.setSuperclass(AbstractClient.class);
        enhancer.setInterfaces(new Class[]{classOfService});
        ApiClientProxy apiClientProxy = new ApiClientProxy(classOfService);
        enhancer.setCallback(apiClientProxy);
        return (T) enhancer.create();
    }
}
