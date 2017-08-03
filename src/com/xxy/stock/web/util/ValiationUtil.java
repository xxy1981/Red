package com.xxy.stock.web.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author	<a href="mailto:jimmy.xu@cetelem.com.cn">JimmyXu</a>
 * @version	1.0
 * @Creationdate:Dec 10, 2008 4:31:05 PM
 */

public class ValiationUtil {
	private static Pattern pattern;
    private static Matcher matcher;

    private static String REG_EXP_NUM = "[0-9]*";

    //private static String REG_EXP_EMAIL = "(\\w[-._\\w]*\\w@\\w[-._\\w]*\\w\\.\\w{2,3})";
    private static String REG_EXP_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@\\w+((\\.|-)\\w+)*\\.\\w+$";

    private static String REG_EXP_NUM_AND_CHAR = "\\w*";

    private static String REG_EXP_LOWER_CHAR = "[a-z]*";

    private static String REG_EXP_UPER_CHAR = "[A-Z]*";

    private static String REG_EXP_CHINESE_OR_CHAR = "^[A-Za-z\u4e00-\u9fa5\uf900-\ufa2d\uFF21-\uFF3A\uFF41-\uFF5A]*$";

    private static String REG_EXP_BLANK_OR_CHAR = "^[ A-Za-z]*$"; // or ^[\sA-Za-z]*$


    public ValiationUtil() {
    }

    public static boolean isNum(String str){
        pattern = Pattern.compile(REG_EXP_NUM);
        matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    public static boolean isNull(String str){
        if (str == null || "".equals(str.trim())) {
            return true;
        }
        return false;
    }

    public static boolean isEmail(String str){
        pattern = Pattern.compile(REG_EXP_EMAIL);
        matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        }
        return false;

    }

    public static boolean isRightLength(String str, int length){
        if(str != null && str.length() == length){
            return true;
        }
        return false;
    }

    public static boolean isNumAndChar(String str){
        pattern = Pattern.compile(REG_EXP_NUM_AND_CHAR);
        matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        }
        return false;

    }

    public static boolean isAmount(String str){
        try {
            new BigDecimal(str);
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }

    public static boolean isLowerChar(String str){
        pattern = Pattern.compile(REG_EXP_LOWER_CHAR);
        matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        }
        return false;

    }

    public static boolean isUperChar(String str){
        pattern = Pattern.compile(REG_EXP_UPER_CHAR);
        matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        }
        return false;

    }

    public static boolean isChineseOrChar(String str){
        pattern = Pattern.compile(REG_EXP_CHINESE_OR_CHAR);
        matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    public static boolean isBlankOrChar(String str){
        pattern = Pattern.compile(REG_EXP_BLANK_OR_CHAR);
        matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    public static String popDigit(String s) {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < s.length(); i++){
			if(Character.isDigit(s.charAt(i))){
				sb.append(s.charAt(i));
			}
		}
		return sb.toString();
	}






    public static void main(String[] args) {
        System.out.println(isNumAndChar("f"));
        System.out.println(isNumAndChar("F"));
        System.out.println(isNumAndChar("f "));
        System.out.println(isNumAndChar("2f"));
    }
}
