package com.xxy.stock.web.http;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;
import com.xxy.stock.web.constants.OkHttpConstants;
import com.xxy.stock.web.exception.StockException;
import com.xxy.stock.web.util.StringUtils;

import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * @author  <a href="mailto:jimmy.xu@cetelem.com.cn">JimmyXu</a>
 * @version 1.0
 * @Creationdate:Dec 23, 2008 5:07:37 PM
 */
public class OkHttpFactory {
    private static OkHttpClient okHttpClient;
    public RequestBody emptyRequestBody = RequestBody.create(OkHttpConstants.MEDIA_TYPE_JSON, "");

    static {
    	Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(OkHttpConstants.DEFAULT_MAX_REQUESTS);
        dispatcher.setMaxRequestsPerHost(OkHttpConstants.DEFAULT_MAX_REQUESTS_PER_HOST);
        ConnectionPool connectionPool = new ConnectionPool(OkHttpConstants.DEFAULT_MAX_IDLE_CONNECTIONS,
                                                           OkHttpConstants.DEFAULT_KEEP_ALIVE_DURATION,
                                                           TimeUnit.SECONDS);
        okHttpClient = new OkHttpClient().newBuilder()
                .dispatcher(dispatcher)
                .connectionPool(connectionPool)
                .connectTimeout(OkHttpConstants.DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(OkHttpConstants.DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(OkHttpConstants.DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();
    }
    
    public static OkHttpClient getOkHttpClient() {
		return okHttpClient;
    }
    
    public static OkHttpClient getNewOkHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(OkHttpConstants.DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(OkHttpConstants.DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(OkHttpConstants.DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }

    public static String doGet(String url) {
    	String result = "";
    	try {
    		Request request = new Request.Builder().url(url).build();
    		Response response = okHttpClient.newCall(request).execute();
    		result = response.body().string().replaceAll("pv_none_match=1", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
		return result;
    }
    
    public static String doPost(String url, String body) {
    	String result = "";
    	try {
    		Request request = new Request.Builder().post(RequestBody.create(MediaType.parse("text/plain"), body)).url(url).build();
    		Response response = okHttpClient.newCall(request).execute();
    		result = response.body().string().replaceAll("pv_none_match=1", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
		return result;
    }
    
    public Response get(String url) throws StockException {
        return get(url, new HttpHeaders());
    }

    public Response get(String url, HttpHeaders headers) throws StockException {
        Request.Builder requestBuilder = new Request.Builder().get().url(url);
        return send(requestBuilder, Headers.of(headers.getHeaders()));
    }

    public Response post(String url, byte[] body, HttpHeaders headers) throws StockException {
        return post(url, body, headers, OkHttpConstants.STREAM_MIME);
    }

    public Response post(String url, String body, HttpHeaders headers) throws StockException {
        return post(url, StringUtils.utf8Bytes(body), headers, OkHttpConstants.STREAM_MIME);
    }

    public Response post(String url, HttpFormEncoding params, HttpHeaders headers) throws StockException {
        FormBody.Builder f = new FormBody.Builder();
        for (String key : params.getParams().keySet()) {
            f.add(key, params.getParams().get(key));
        }
        return post(url, f.build(), headers);
    }

    public Response post(String url, byte[] body, HttpHeaders headers, String contentType) throws StockException {
        RequestBody rbody;
        if (body != null && body.length > 0) {
            MediaType t = MediaType.parse(contentType);
            rbody = RequestBody.create(t, body);
        } else {
            rbody = RequestBody.create(null, new byte[0]);
        }
        return post(url, rbody, headers);
    }

    public Response post(String url, byte[] body, int offset, int size, HttpHeaders headers,
                         String contentType) throws StockException {
        RequestBody rbody;
        if (body != null && body.length > 0) {
            MediaType t = MediaType.parse(contentType);
            rbody = requestIO(t, body, offset, size);
        } else {
            rbody = RequestBody.create(null, new byte[0]);
        }
        return post(url, rbody, headers);
    }

    public Response post(String url, InputStream inputStream, Long contentLength,
                         HttpHeaders headers) throws StockException {
        return post(url, requestIO(inputStream, contentLength), headers);
    }

    public Response post(String url, RequestBody body, HttpHeaders headers) throws StockException {
        Request.Builder requestBuilder = new Request.Builder().url(url).post(body);
        return send(requestBuilder, Headers.of(headers.getHeaders()));
    }

    public Response put(String url, HttpHeaders headers) throws StockException {
        return put(url, emptyRequestBody, headers);
    }

    public Response put(String url, Object body, HttpHeaders headers) throws StockException {
        return put(url, JSONObject.toJSONString(body), headers);
    }

    public Response put(String url, String body, HttpHeaders headers) throws StockException {
        return put(url, RequestBody.create(OkHttpConstants.MEDIA_TYPE_JSON, body), headers);
    }

    public Response put(String url, InputStream inputStream, Long contentLength,
                        HttpHeaders headers) throws StockException {
        return put(url, requestIO(inputStream, contentLength), headers);

    }

    private Response put(String url, RequestBody body, HttpHeaders headers) throws StockException {
        Request.Builder requestBuilder = new Request.Builder().url(url).put(body);
        return send(requestBuilder, Headers.of(headers.getHeaders()));
    }

    public Response delete(String url) throws StockException {
        return delete(url, new HttpHeaders());
    }

    public Response delete(String url, HttpHeaders headers) throws StockException {
        Request.Builder requestBuilder = new Request.Builder().url(url).delete();
        return send(requestBuilder, Headers.of(headers.getHeaders()));
    }

    public Response send(final Request.Builder requestBuilder, Headers headers) throws StockException {
        if (headers != null) {
            requestBuilder.headers(headers);
        }

        try {
            Response response = okHttpClient.newCall(requestBuilder.build()).execute();
            if (response.code() >= 300) {
                throw new StockException(response.code() + "::" + response.message());
            }
            return response;
        } catch (IOException e) {
            throw new StockException(e);
        }
    }

    private static RequestBody requestIO(final MediaType contentType, final byte[] content, final int offset,
                                         final int size) {
        if (content == null) throw new NullPointerException("content == null");

        return new RequestBody() {

            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return size;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                sink.write(content, offset, size);
            }
        };
    }

    private static RequestBody requestIO(final InputStream inputStream, final Long contentLength) {
        if (inputStream == null) throw new NullPointerException("content == null");

        return new RequestBody() {

            @Override
            public MediaType contentType() {
                return MediaType.parse(OkHttpConstants.STREAM_MIME);
            }

            @Override
            public long contentLength() {
                return contentLength;
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                try {
                    final byte[] buffer = new byte[OkHttpConstants.PUT_THRESHOLD];
                    int l;
                    if (contentLength < OkHttpConstants.PUT_THRESHOLD) {
                        // consume until EOF
                        while ((l = inputStream.read(buffer)) != -1) {
                            sink.outputStream().write(buffer, 0, l);
                        }
                    } else {
                        // consume no more than length
                        long remaining = contentLength;
                        while (remaining > 0) {
                            l = inputStream.read(buffer, 0, (int) Math.min(OkHttpConstants.PUT_THRESHOLD, remaining));
                            if (l == -1) {
                                break;
                            }
                            sink.outputStream().write(buffer, 0, l);
                            remaining -= l;
                        }
                    }
                } finally {
                    sink.close();
                }
            }
        };
    }
}
