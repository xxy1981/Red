package com.xxy.stock.web.constants;

import com.xxy.stock.web.util.PropertiesUtil;

/**
 * @author	<a href="mailto:jimmy.xu@cetelem.com.cn">JimmyXu</a>
 * @version	1.0
 * @Creationdate:Apr 2, 2009 2:55:35 PM
 */

public interface StockWebsiteConstants {

	public final static String PROXY_IP = PropertiesUtil.getPropertyString("conf-param.properties", "proxy.ip.address");
	public final static int PROXY_PORT = PropertiesUtil.getPropertyInt("conf-param.properties", "proxy.ip.port");
	public final static boolean PROXY_MODE = PropertiesUtil.getPropertyBoolean("conf-param.properties", "proxy.mode");
	public final static int HTTPCLIENT_CONNECTION_MAX = PropertiesUtil.getPropertyInt("conf-param.properties", "httpclinet.connect.max");
	public final static int HTTPCLIENT_CONNECTION_TIMEOUT = PropertiesUtil.getPropertyInt("conf-param.properties", "httpclinet.connect.timeout");
	public final static boolean HTTPCLIENT_POOL_ENABLED = PropertiesUtil.getPropertyBoolean("conf-param.properties", "httpclinet.pool.enabled");
	public final static String HTTPCLIENT_OR_OKHTTP = PropertiesUtil.getPropertyString("conf-param.properties", "httpclinet.or.okhttp");
	public final static String STCOK_URL_SINA = PropertiesUtil.getPropertyString("conf-param.properties", "stock.data.url.sina");
	public final static String STCOK_URL_TENCENT = PropertiesUtil.getPropertyString("conf-param.properties", "stock.data.url.tencent");
	public final static String STCOK_CHANNEL = PropertiesUtil.getPropertyString("conf-param.properties", "stock.channel");
	public final static String STCOK_DB_PATH = PropertiesUtil.getPropertyString("conf-param.properties", "stock.db.path");
	public final static String LOCAL_ZXG_FILE = PropertiesUtil.getPropertyString("conf-param.properties", "local.zxg.file");
	public final static String LOCAL_ZXG_FILE2 = PropertiesUtil.getPropertyString("conf-param.properties", "local.zxg.file2");
    public final static String LOCAL_VIEW_FILE = PropertiesUtil.getPropertyString("conf-param.properties", "local.view.file");
	public final static int LOCAL_ZXG_NUMBER = PropertiesUtil.getPropertyInt("conf-param.properties", "local.zxg.number");
	public final static double ZXG_LB = PropertiesUtil.getPropertyDouble("conf-param.properties", "zxg.lb");
	public final static double ZXG_LTSZ = PropertiesUtil.getPropertyDouble("conf-param.properties", "zxg.ltsz");

}
