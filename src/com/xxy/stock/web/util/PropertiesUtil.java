package com.xxy.stock.web.util;

import org.springframework.core.io.support.PropertiesLoaderUtils;

public class PropertiesUtil {

	public static String getPropertyString(String file, String key){
		try {
			return PropertiesLoaderUtils.loadAllProperties(file).getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static int getPropertyInt(String file, String key){
		try {
			return Integer.parseInt(PropertiesLoaderUtils.loadAllProperties(file).getProperty(key));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static double getPropertyDouble(String file, String key){
		try {
			return Double.parseDouble(PropertiesLoaderUtils.loadAllProperties(file).getProperty(key));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static boolean getPropertyBoolean(String file, String key){
		try {
			return Boolean.parseBoolean(PropertiesLoaderUtils.loadAllProperties(file).getProperty(key));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
