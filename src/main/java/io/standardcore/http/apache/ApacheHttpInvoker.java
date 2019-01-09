package io.standardcore.http.apache;

import io.standardcore.http.ApiRequest;
import io.standardcore.http.HttpInvoker;
import io.standardcore.http.HttpWebResponse;
import io.standardcore.http.io.ByteInputStreamReader;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ApacheHttpInvoker implements HttpInvoker {
    private final HttpClientFactory httpClientFactory;

    public ApacheHttpInvoker(HttpClientFactory httpClientFactory) {
        this.httpClientFactory = httpClientFactory;
    }

    public ApacheHttpInvoker(){
        this(new HttpClientFactory());
    }

    @Override
    public HttpWebResponse invoke(ApiRequest request) throws IOException {
        HttpHost httpHost = new HttpHost(request.serviceHost(), request.port(), request.httpSchema());
        CloseableHttpClient httpClient = httpClientFactory.getHttpClient(httpHost);
        HttpClientContext context = HttpClientContext.create();
        HttpUriRequest httpRequest = buildHttpRequest(request);
        for(String headerName : request.headers().getNames()){
            List<String> values = request.headers().getValues(headerName);
            httpRequest.addHeader(headerName, String.join(",", values));
        }
        CloseableHttpResponse httpResponse = httpClient.execute(httpRequest, context);
        return buildHttpWebResponse(httpResponse);
    }

    private HttpUriRequest buildHttpRequest(ApiRequest apiRequest){
        switch (apiRequest.method().toLowerCase()){
            case "get":
                return new HttpGet(apiRequest.buildUrlWithQueryStrings());

            case "post":
                HttpPost httpPost = new HttpPost(apiRequest.buildUrlWithQueryStrings());
                if(apiRequest.payload().length > 0) {
                    httpPost.setEntity(buildHttpEntity(apiRequest));
                }
                return httpPost;

            case "put":
                HttpPut httpPut = new HttpPut(apiRequest.buildUrlWithQueryStrings());
                if(apiRequest.payload().length > 0) {
                    httpPut.setEntity(buildHttpEntity(apiRequest));
                }
                return httpPut;

            case "delete":
                return new HttpDelete(apiRequest.buildUrlWithQueryStrings());
        }
        return null;
    }

    private HttpEntity buildHttpEntity(ApiRequest apiRequest){
        ByteArrayEntity httpEntity = new ByteArrayEntity(apiRequest.payload());
        return httpEntity;
    }

    private HttpWebResponse buildHttpWebResponse(CloseableHttpResponse httpResponse) throws IOException {
        HttpEntity httpEntity = httpResponse.getEntity();
        InputStream bodyStream = httpEntity.getContent();
        ByteInputStreamReader reader = new ByteInputStreamReader(bodyStream);
        try{
            byte[] bodyContent = reader.readToEnd();
            HttpWebResponse webResponse = new HttpWebResponse(httpResponse.getStatusLine().getStatusCode(), bodyContent);
            for(Header header : httpResponse.getAllHeaders()){
                webResponse.getHeaders().add(header.getName(), header.getValue());
            }
            return webResponse;
        } finally {
            reader.close();
        }

    }
}
