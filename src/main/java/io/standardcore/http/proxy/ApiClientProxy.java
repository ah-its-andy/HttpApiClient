package io.standardcore.http.proxy;

import io.standardcore.http.*;
import io.standardcore.http.collection.KeyValuePair;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.annotation.AnnotationTypeMismatchException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ApiClientProxy implements MethodInterceptor {
    private final ApiClientOptions apiClientOptions;
    private final Class<?> serviceInterfaceType;

    public ApiClientProxy(ApiClientOptions apiClientOptions, Class<?> serviceInterfaceType) {
        this.apiClientOptions = apiClientOptions;
        this.serviceInterfaceType = serviceInterfaceType;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        DefaultMarshallInfo marshallInfo = new DefaultMarshallInfo();
        marshallInfo.setHttpServiceType(serviceInterfaceType);
        marshallInfo.setInvokingMethod(method);
        marshallInfo.setMarshallWares(apiClientOptions.getMarshallWares());

        HttpService httpService =  serviceInterfaceType.getAnnotation(HttpService.class);
        marshallInfo.setServiceHost(apiClientOptions.getServiceNameResolver().resolve(httpService.value()));
        Annotation annotation = getServiceAnnotation(method);
        if(annotation instanceof HttpGet){
            marshallInfo.setServiceUrl(((HttpGet) annotation).value());
            marshallInfo.setMethod("GET");
        } else if(annotation instanceof HttpPost){
            marshallInfo.setServiceUrl(((HttpPost) annotation).value());
            marshallInfo.setMethod("POST");
            marshallInfo.setContentType(((HttpPost) annotation).contentType());
        } else if(annotation instanceof HttpPut){
            marshallInfo.setServiceUrl(((HttpPut) annotation).value());
            marshallInfo.setMethod("PUT");
            marshallInfo.setContentType(((HttpPut) annotation).contentType());
        } else {
            marshallInfo.setServiceUrl(((HttpDelete) annotation).value());
            marshallInfo.setMethod("DELETE");
        }
        Marshall marshall = method.getDeclaredAnnotation(Marshall.class);
        if(marshall != null) marshallInfo.setMarshaller((Marshaller) createInstance(marshall.value()));
        else marshallInfo.setMarshaller(apiClientOptions.getMarshaller());

        BeforeMarshall beforeMarshall = method.getDeclaredAnnotation(BeforeMarshall.class);
        if(beforeMarshall != null) {
            List<MarshallWare> marshallWares =
                    Arrays.stream(beforeMarshall.value())
                    .map(x-> (MarshallWare)createInstance(x)).collect(Collectors.toList());
            marshallInfo.setMarshallWares(marshallWares);
        }

        Unmarshall unmarshall = method.getDeclaredAnnotation(Unmarshall.class);
        Unmarshaller unmarshaller = apiClientOptions.getUnmarshaller();
        if(unmarshall != null){
            unmarshaller = (Unmarshaller)createInstance(unmarshall.value());
        }

        BeforeUnmarshall beforeUnmarshall = method.getDeclaredAnnotation(BeforeUnmarshall.class);
        List<UnmarshallWare> unmarshallWares = new LinkedList<>();
        if(beforeMarshall != null){
             unmarshallWares.addAll(
                    Arrays.stream(beforeUnmarshall.value())
                    .map(x-> (UnmarshallWare)createInstance(x))
                    .collect(Collectors.toList()));
        }

        for(int i=0;i<method.getParameters().length;i++){
            Parameter parameter = method.getParameters()[i];

            FromHeader fromHeader = parameter.getDeclaredAnnotation(FromHeader.class);
            if(fromHeader != null){
                KeyValuePair<String, Object> header
                        = new KeyValuePair<>(StringUtils.isEmpty(fromHeader.value()) ? parameter.getName() : fromHeader.value(), args[i]);
                marshallInfo.getHeaders().add(header);
                continue;
            }
            FromQuery fromQuery = parameter.getDeclaredAnnotation(FromQuery.class);
            if(fromQuery != null){
                KeyValuePair<String, Object> querystring
                        = new KeyValuePair<>(StringUtils.isEmpty(fromQuery.value()) ? parameter.getName() : fromQuery.value(),args[i]);
                marshallInfo.getQueryStrings().add(querystring);
                continue;
            }
            FromBody fromBody = parameter.getDeclaredAnnotation(FromBody.class);
            if(fromBody != null){
                marshallInfo.setPayload(args[i]);
                continue;
            }
        }
        ApiRequest apiRequest = MarshallerInvoker.invoke(marshallInfo);
        HttpWebResponse webResponse = apiClientOptions.getHttpInvoker().invoke(apiRequest);
        DefaultUnmarshallInfo unmarshallInfo = new DefaultUnmarshallInfo();
        unmarshallInfo.setStatusCode(webResponse.getStatusCode());
        unmarshallInfo.setPayload(webResponse.getBody());
        unmarshallInfo.setHeaders(webResponse.getHeaders());
        unmarshallInfo.setUnmarshaller(unmarshaller);
        unmarshallInfo.setUnmarshallWares(unmarshallWares);
        unmarshallInfo.setHttpServiceType(serviceInterfaceType);
        unmarshallInfo.setInvokingMethod(method);
        unmarshallInfo.setReturningType(method.getReturnType());

        ApiResponse apiResponse = UnmarshallInvoker.invoke(unmarshallInfo);
        if(ApiResponse.class.isAssignableFrom(method.getReturnType())){
            return apiResponse;
        } else if(method.getReturnType().equals(Void.TYPE)) {
            return null;
        } else if (apiResponse.statusCode() >= 200 && apiResponse.statusCode() <= 299) {
                return apiResponse.payload();
        } else{
            throw new HttpInternalException(apiResponse);
        }
    }

    private Object createInstance(Class<?> type){
        try {
            Constructor<?> ctor = type.getConstructor();
            return ctor.newInstance();
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    private Annotation getServiceAnnotation(Method method){
        HttpGet httpGet = method.getDeclaredAnnotation(HttpGet.class);
        if(httpGet != null) return httpGet;
        HttpPost httpPost = method.getDeclaredAnnotation(HttpPost.class);
        if(httpPost != null) return httpPost;
        HttpPut httpPut = method.getDeclaredAnnotation(HttpPut.class);
        if(httpPut != null) return httpPut;
        HttpDelete httpDelete = method.getDeclaredAnnotation(HttpDelete.class);
        if(httpDelete != null) return httpDelete;
        throw new AnnotationTypeMismatchException(method, "");
    }
}
