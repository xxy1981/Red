package com.xxy.stock.web.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.context.support.ServletContextResource;

import com.xxy.stock.web.constants.StockWebsiteConstants;


/**
 * @author	<a href="mailto:jimmy.xu@cetelem.com.cn">JimmyXu</a>
 * @version	1.0
 * @Creationdate:Dec 5, 2008 3:19:19 PM
 */

public class FileUtil implements StockWebsiteConstants {

	private static final Logger log = Logger.getLogger(FileUtil.class);
	
	private final static SimpleDateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat YYMM = new SimpleDateFormat("yyMM");
	
	@SuppressWarnings("resource")
	public static String file2String(File file, String charset) {
		FileInputStream in = null;
        try {
            in = new FileInputStream(file);
        }
        catch (FileNotFoundException e1) {
        	log.error(e1.getMessage());
            return null;
        }
        Reader reader = null;
        try {
            reader = new InputStreamReader(in, charset);
        }
        catch (UnsupportedEncodingException e) {
        	log.error(e.getMessage());
            return null;
        }
        try {
			return FileCopyUtils.copyToString(reader);
		} catch (IOException e) {
			log.error(e.getMessage());
            return null;
		}
    }
	
	public static String file2String(File file) {
        try {
			return FileCopyUtils.copyToString(new FileReader(file));
		} catch (IOException e) {
			log.error(e.getMessage());
            return null;
		}
    }
	
	public static void writeFile(String source, String fileName) {
    	writeFile(source, new File(fileName));
    }
    
	public static void writeFile(String source, String inCharset, String fileName) {
	    writeFile(source, inCharset, new File(fileName));
    }
    
    public static void writeFile(String source, File out) {
        try {
        	FileCopyUtils.copy(source.getBytes(), out);
        }
        catch (Exception e) {
        	log.error(e.getMessage());
        }
    }
    
    public static void writeFile(String source, String inCharset, File out) {
        try {
        	FileCopyUtils.copy(source.getBytes(inCharset), out);
        }
        catch (Exception e) {
        	log.error(e.getMessage());
        }
    }
    
    public static void copyFile(String inName, String outName) {
        try {
        	FileCopyUtils.copy(new File(inName), new File(outName));
        }
        catch (Exception e) {
        	log.error(e.getMessage());
        }
    }
    
    
    /**
     * Get real path according current path.
     * 
     * @param clz
     * @param cutPath
     * @param makePath
     * @return RealPath
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
	public static String getRealPath(Class clz, String cutPath, String makePath) {
       try {
           Class clzObj = clz.newInstance().getClass();
           String strClassName = clzObj.getName();
           String strClassFileName = strClassName.substring(strClassName.lastIndexOf(".") + 1, strClassName.length());
           URL url = null;
           url = clzObj.getResource(strClassFileName + ".class");
           String strURL = url.toString();
           if (getOSName().indexOf("Windows") > -1) {
              strURL = strURL.substring(strURL.indexOf("/") + 1);
           } else {
              strURL = strURL.substring(strURL.indexOf(java.io.File.separatorChar));
           }
           strURL = strURL.substring(0, strURL.lastIndexOf(cutPath));
           strURL = strURL + makePath;
           File file = new File(strURL);
           if (!file.exists()) {
              file.mkdirs();
           }
           return strURL;
       } catch (Exception e) {
           e.printStackTrace();
           return "";
       }
    }
    
    public static String getOSName() {
        return (String) System.getProperties().get("os.name");
    }
    
    @SuppressWarnings("rawtypes")
	public static String getTopOfWebInfPath(){
    	try {
			Class theClass = FileUtil.class;
			URL u = theClass.getResource("");
			String str = u.toString();
			if (getOSName().indexOf("Windows") > -1) {
				str = str.substring(str.indexOf("/") + 1);
			} else {
				str = str.substring(str.indexOf(java.io.File.separatorChar));
			}
			//if blank in path
			str = str.replaceAll("%20", " ");
			int num = str.indexOf("WEB-INF");
			str = str.substring(0, num-1);
			return str;
		} catch (Exception e) {
			return "";
		}
    }
    
    public static File getRealPathOfWebFile(ServletContext servletContext, String path){
    	Resource res = new ServletContextResource(servletContext,path);
    	try {
			return res.getFile();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
    }
    
    public synchronized static void writePasswordFile() {
    	
    	Resource res = new ClassPathResource("password.properties");
		
		StringBuffer sb = new StringBuffer("");
		try {
			FileReader reader = new FileReader(res.getFile());
			BufferedReader br = new BufferedReader(reader);
			String s = null;
			while ((s = br.readLine()) != null) {
				if(!s.startsWith("#") && s.startsWith("password")){
					s = "#" + s;
				}
				sb.append(s + '\n');
			}
			br.close();
			reader.close();
		} catch (Exception e) {
			log.error("Read password file 'password.properties' fail.");
		}
		
		Date date = new Date();
		
		
		sb.append("#change password at " + YYYYMMDDHHMMSS.format(date) + "\n");
		sb.append("password=password" + YYMM.format(date) + "\n\n");
		
		FileWriter fos = null;
		try {
			fos = new FileWriter(res.getFile());
			fos.write(sb.toString());
		} catch (IOException e) {
			log.error("Write password file 'password.properties' fail.");
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
    
    public synchronized static String getPassword() {
    	
    	Resource res = new ClassPathResource("password.properties");
    	String password = "";
		try {
			FileReader reader = new FileReader(res.getFile());
			BufferedReader br = new BufferedReader(reader);
			String s = null;
			while ((s = br.readLine()) != null) {
				if(s.startsWith("password")){
					password = s.split("=")[1];
				}
			}
			br.close();
			reader.close();
		} catch (Exception e) {
			log.error("Read password file 'password.properties' fail.");
		}
		return password;
    }

    public static void main(String[] args) {

    	System.out.println(getRealPath(FileUtil.class, "/WEB-INF", ""));
    	System.out.println(getTopOfWebInfPath());
    	writePasswordFile();
    }

}
