package io.standardcore.http.apache;

import org.apache.http.HttpHost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import javax.net.ssl.SSLContext;
import java.security.NoSuchAlgorithmException;

public class HttpClientFactory {
    private final PoolingHttpClientConnectionManager httpClientConnectionManager;
    private final Registry<ConnectionSocketFactory> socketFactoryRegistry;

    public HttpClientFactory() {
        this.maxActiveConnection = 100;
        this.maxConnectionPerRoute = 50;
        this.defaultConnectionPerRoute = 20;

        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.<ConnectionSocketFactory>create();

        try {
            LayeredConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
            registryBuilder.register("https", sslsf);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        registryBuilder.register("http", new PlainConnectionSocketFactory());
        socketFactoryRegistry = registryBuilder.build();
        httpClientConnectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        httpClientConnectionManager.setMaxTotal(getMaxActiveConnection());
        httpClientConnectionManager.setDefaultMaxPerRoute(getDefaultConnectionPerRoute());
    }

    public CloseableHttpClient getHttpClient(HttpHost httpHost){
        HttpRoute route = new HttpRoute(httpHost);
        httpClientConnectionManager.setMaxPerRoute(route, getMaxConnectionPerRoute());
        return HttpClients.custom().setConnectionManager(httpClientConnectionManager).build();
    }

    private Integer maxActiveConnection;
    private Integer maxConnectionPerRoute;
    private Integer defaultConnectionPerRoute;

    public Integer getMaxActiveConnection() {
        return maxActiveConnection;
    }

    public void setMaxActiveConnection(Integer maxActiveConnection) {
        this.maxActiveConnection = maxActiveConnection;
    }

    public Integer getMaxConnectionPerRoute() {
        return maxConnectionPerRoute;
    }

    public void setMaxConnectionPerRoute(Integer maxConnectionPerRoute) {
        this.maxConnectionPerRoute = maxConnectionPerRoute;
    }

    public Integer getDefaultConnectionPerRoute() {
        return defaultConnectionPerRoute;
    }

    public void setDefaultConnectionPerRoute(Integer defaultConnectionPerRoute) {
        this.defaultConnectionPerRoute = defaultConnectionPerRoute;
    }
}
