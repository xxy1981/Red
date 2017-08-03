package com.xxy.stock.web.constants;

import java.nio.charset.Charset;

import okhttp3.MediaType;

/**
 * OkHttp常量
 * @author Think
 *
 */
public interface OkHttpConstants {

    // 默认字符集编码
    public static final Charset   UTF_8                          = Charset.forName("UTF-8");

    public static final MediaType MEDIA_TYPE_JSON                = MediaType.parse("application/json; charset=utf-8");

    public static final String    STREAM_MIME                    = "application/octet-stream";
    public static final String    JSON_MIME                      = "application/json";
    public static final String    FORM_MIME                      = "application/x-www-form-urlencoded";

    // 断点上传时的分块大小(默认的分块大小, 不允许改变)
    public static final int       BLOCK_SIZE                     = 4 * 1024 * 1024;

    // 如果文件大小大于此值则使用断点上传, 否则使用Form上传
    public static int             PUT_THRESHOLD                  = BLOCK_SIZE; 
    
    // 最大空闲连接数
    public static final int       DEFAULT_MAX_IDLE_CONNECTIONS   = 100;

    // 保持活动周期时长
    public static final long      DEFAULT_KEEP_ALIVE_DURATION    = 5 * 60;

    // 最大请求数
    public static final int       DEFAULT_MAX_REQUESTS           = 300;

    // 每台主机最大的请求数
    public static final int       DEFAULT_MAX_REQUESTS_PER_HOST  = 150;

    // 连接超时时间 单位秒(默认10s)
    public static final int       DEFAULT_CONNECT_TIMEOUT        = 60;

    // 写超时时间 单位秒(默认 0 , 不超时)
    public static final int       DEFAULT_WRITE_TIMEOUT          = 60;

    // 读超时时间 单位秒(默认30s)
    public static final int       DEFAULT_READ_TIMEOUT           = 60;
}
