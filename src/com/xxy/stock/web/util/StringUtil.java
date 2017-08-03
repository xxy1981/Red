package com.xxy.stock.web.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author	<a href="mailto:jimmy.xu@cetelem.com.cn">JimmyXu</a>
 * @version	1.0
 * @Creationdate:Dec 10, 2008 4:32:37 PM
 */

public class StringUtil {

    public static String trim(String s) {
        if (s != null) {
        	Pattern p = Pattern.compile("\\s*|\t|\r|\n");
      	  	Matcher m = p.matcher(s);
      	  	String after = m.replaceAll(""); 
      	  	after = after.replaceAll("\u3000", ""); //remove chinese blank
      	  	return after.trim();
        }
        return s;
    }

    public static String popDigit(String s) {
    	if(s == null){
    		return "";
    	}
    	s = trim(s);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }

    public static Long getPbocJobTimeBegin(String s) {
        if(s == null){
            return Long.valueOf(0);
        }
        try {
        	s = trim(s);
            s = s.split("--")[0].trim();
            return new Long(popDigit(s));
        }
        catch (Exception ex) {
            return new Long(0);
        }
    }

    public static Long getPbocJobTimeEnd(String s) {
        if(s == null){
            return Long.valueOf(0);
        }
        try {
        	s = trim(s);
            s = s.split("--")[1].trim();
            return new Long(popDigit(s));
        }
        catch (Exception ex) {
            return new Long(0);
        }
    }

    public static Long string2Long(String str) {
        if (str == null) {
            return new Long(0);
        }
        try {
        	str = trim(str);
            str = str.trim().replaceAll(",","");
            return new Long(str);
        }
        catch (Exception ex) {
            try {
                return new Long(str.split("\\.")[0]);
            }
            catch (Exception ex1) {
                return new Long(0);
            }
        }
    }

    public static Long s2Long100(String str) {
        if (str == null) {
            return new Long(0);
        }
        try {
        	str = trim(str);
            str = str.trim().replaceAll(",","");
            BigDecimal money = new BigDecimal(str);
            money = money.multiply(new BigDecimal(100));
            return money.longValue();
        }
        catch (Exception ex) {
            return new Long(0);
        }
    }
    
    public static String[] split(String str, String s) {
    	if (str == null || s == null){
			throw new IllegalArgumentException((new StringBuilder(
					"\u6240\u6709\u53C2\u6570\u4E0D\u80FD\u4E3Anull !   str="))
					.append(str).append("  :  token=").append(s).toString());
		}
		//int len = str.length();
		int tLen = s.length();
		if (tLen == 0){
			throw new IllegalArgumentException(
					"token\u957F\u5EA6\u4E0D\u80FD\u4E3A0 !");
		}
    	StringTokenizer st = new StringTokenizer(str, s);
		int count = st.countTokens();
		String[] list = new String[count];
		for (int i = 0; i < count; i++) {
			list[i] = st.nextToken();
		}
		return list;
	}

	public static List<String> cut(String str, String token) {
		if (str == null || token == null){
			throw new IllegalArgumentException((new StringBuilder(
					"\u6240\u6709\u53C2\u6570\u4E0D\u80FD\u4E3Anull !   str="))
					.append(str).append("  :  token=").append(token).toString());
		}
		int len = str.length();
		int tLen = token.length();
		if (tLen == 0){
			throw new IllegalArgumentException(
					"token\u957F\u5EA6\u4E0D\u80FD\u4E3A0 !");
		}
		if (len < tLen || len == tLen && !str.equals(token)) {
			List<String> list = new ArrayList<String>();
			list.add(str);
			return list;
		}
		if (str.equals(token)){
			return new ArrayList<String>();
		}
		List<String> cuts = new ArrayList<String>();
		int bg = 0;
		int end = 0;
		for (int i = 0; i <= len - tLen; i++)
			if (str.charAt(i) == token.charAt(0)) {
				boolean isToken = true;
				for (int k = 1; k < token.length(); k++) {
					if (str.charAt(i + k) == token.charAt(k))
						continue;
					isToken = false;
					break;
				}

				if (isToken) {
					end = i;
					String tem = str.substring(bg, end);
					if (tem.length() > 0){
						cuts.add(tem);
					}
					bg = end + tLen;
					end = bg;
					i += tLen - 1;
				}
			}

		if (bg < len){
			cuts.add(str.substring(bg, len));
		}
		return cuts;
	}

    public static void main(String[] args) {

    }
}
