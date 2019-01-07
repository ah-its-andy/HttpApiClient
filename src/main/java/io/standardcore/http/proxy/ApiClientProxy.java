package io.standardcore.http.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class ApiClientProxy implements MethodInterceptor {
    private final Class<?> serviceInterfaceType;

    public ApiClientProxy(Class<?> serviceInterfaceType) {
        this.serviceInterfaceType = serviceInterfaceType;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        return null;
    }
}
