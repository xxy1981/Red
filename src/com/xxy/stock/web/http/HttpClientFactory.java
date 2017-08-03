package com.xxy.stock.web.http;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
//import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

import com.xxy.stock.web.constants.StockWebsiteConstants;


/**
 * @author	<a href="mailto:jimmy.xu@cetelem.com.cn">JimmyXu</a>
 * @version	1.0
 * @Creationdate:Dec 23, 2008 5:07:37 PM
 */
public class HttpClientFactory implements StockWebsiteConstants{
    private static Object LOCAL_LOCK = new Object();

    private static PoolingHttpClientConnectionManager cm = null; 
    
    private HttpClientFactory() {  
    }
    
    private static PoolingHttpClientConnectionManager getPoolManager() {  
        if (null == cm) {  
            synchronized (LOCAL_LOCK) {  
                if (null == cm) {  
                    SSLContextBuilder sslContextBuilder = new SSLContextBuilder();  
                    try {  
                        sslContextBuilder.loadTrustMaterial(null, new TrustSelfSignedStrategy());  
                        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(  
                                sslContextBuilder.build());  
                        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory> create()  
                                .register("https", socketFactory)  
                                .register("http", new PlainConnectionSocketFactory())  
                                .build();  
                        cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);  
                        cm.setMaxTotal(HTTPCLIENT_CONNECTION_MAX);  
                        cm.setDefaultMaxPerRoute(HTTPCLIENT_CONNECTION_MAX/2);  
                    } catch (Exception e) {  
                       e.printStackTrace();  
                    }
                }  
            }  
        }  
        return cm;  
    }  
  
  
    /** 
     * 创建线程安全的HttpClient 
     *  
     * @param config 客户端超时设置 
     *  
     * @return 
     */  
    public static CloseableHttpClient getHttpClient(RequestConfig config) {
        HttpHost proxy = null;
        if(PROXY_MODE){
            proxy = new HttpHost(PROXY_IP, PROXY_PORT);
        }
        if(config == null){
            config = RequestConfig.custom().setProxy(proxy)  
                        .setSocketTimeout(HTTPCLIENT_CONNECTION_TIMEOUT)
                        .setConnectTimeout(HTTPCLIENT_CONNECTION_TIMEOUT)
                        .setConnectionRequestTimeout(HTTPCLIENT_CONNECTION_TIMEOUT)
                        .setStaleConnectionCheckEnabled(true)
                        .build();   
        }
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(config)  
                .setConnectionManager(getPoolManager())  
                .build();  
        return httpClient; 
    }
    
    public static CloseableHttpClient getNewHttpClient(RequestConfig config) {
        HttpHost proxy = null;
        if(PROXY_MODE){
            proxy = new HttpHost(PROXY_IP, PROXY_PORT);
        }
        if(config == null){
            config = RequestConfig.custom().setProxy(proxy)
                        .setSocketTimeout(HTTPCLIENT_CONNECTION_TIMEOUT)
                        .setConnectTimeout(HTTPCLIENT_CONNECTION_TIMEOUT)
                        .setConnectionRequestTimeout(HTTPCLIENT_CONNECTION_TIMEOUT)
                        .setStaleConnectionCheckEnabled(true).build();   
        }
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(config)
                .build(); 
        return httpClient;
    }  
  
  
    /** 
     * Https post请求 
     *  
     * @param url 请求地址 
     * @param json 请求参数(如果为null,则表示不请求参数) return 返回结果 
     */  
    public String poolHttpsPost(String url, JSONObject json) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;  
        HttpPost post = null;  
        try {
            httpClient = getHttpClient(null);
            post = new HttpPost(url);
  
            if (json != null) {
                StringEntity se = new StringEntity(json.toString(), "UTF-8");  
                se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json;"));  
                post.setEntity(se);  
            }
  
            response = httpClient.execute(post);  
            int status = response.getStatusLine().getStatusCode();  
            String result = null;  
            if (status == 200) {  
                result = EntityUtils.toString(response.getEntity(), "UTF-8");  
            }  
            EntityUtils.consume(response.getEntity());  
            response.close();  
            return result;  
        } catch (Exception e) {  
            e.printStackTrace(); 
        } finally {
            post.releaseConnection();  
            if (response != null) {  
                try {  
                    EntityUtils.consume(response.getEntity());  
                    response.close();  
                } catch (IOException e) {  
                    e.printStackTrace();
                }  
            } 
            if(httpClient != null){
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();;
                } 
            } 
        }  
        return null;  
    }
	
}
