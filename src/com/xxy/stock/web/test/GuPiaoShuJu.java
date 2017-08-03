package com.xxy.stock.web.test;

import java.io.*; 
import java.net.*; 
import java.util.regex.*; 

public class GuPiaoShuJu { 

    private static String getDocumnetAt(String urlString) { 
        StringBuffer html_text = new StringBuffer(); 
        try { 
            // 创建指向股票网址的链接 
            URL url = new URL(urlString); 
            // 创建链接 
            URLConnection uc = url.openConnection(); 
            // 创建输入流 
            BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream())); 
            // 将网页内容放到缓冲区 
            String line = null; 
            while ((line = reader.readLine()) != null) { 
                html_text.append(line + "\n"); 
            } 
            // 关闭输入流 
            reader.close(); 
        } catch (MalformedURLException e) { 
            System.out.print("invalid url:" + urlString); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
        return html_text.toString(); 
    } 

    public void extractor(String urlString) throws IOException { 
        // 文件输出流 
        FileOutputStream fos = new FileOutputStream("GuPiao.txt"); 
        OutputStreamWriter ows = new OutputStreamWriter(fos); 
        try { 
            // 获得网页文本内容 
            String str = GuPiaoShuJu.getDocumnetAt(urlString); 
            // 创建提取股票数据来源的正则表达式 
            Pattern gp_source = Pattern.compile("(?<=<th colspan=\"7\">|\"blue\">|</FONT>).*?(?=<|FONT|</th>)"); 
            Matcher mc = gp_source.matcher(str); 
            String s1; 
            while (mc.find()) { 
                // 提取股票数据来源 
                s1 = String.valueOf(mc.group()); 
                ows.write(s1); 
                System.out.printf("%s", mc.group()); 
            } 
            System.out.println(); 
            ows.write("\r\n"); 

            // 输出股票数据条目 
            String s2; 
            Pattern gp_item = Pattern 
                    .compile("(?<=<strong>).*?(?=(</strong>))"); 
            Matcher n = gp_item.matcher(str); 
            while (n.find()) { 
                s2 = String.valueOf(n.group()); 
                ows.write(s2 + "          "); 
                System.out.printf("%-40s", n.group()); 
            } 

            // 提取股票数据详细情况 
            Pattern gp_data = Pattern.compile("((?<=date=)(\\w*?)).*?(?=('>))|((?<=center\">)(\\d{1,7}?)).*?(?=(</div>))"); 
            Matcher m = gp_data.matcher(str); 
            String s3; 
            int i = 0; 
            while (m.find()) { 
                if (i == 0) 
                    System.out.println(); 
                i++; 
                s3 = String.valueOf(m.group()); 
                ows.write(s3 + "          "); 

                System.out.printf("%-20s", m.group()); 
                if (i % 7 == 0) { 
                    System.out.println(); 
                    ows.write("\r\n"); 
                } 
            } 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } catch (PatternSyntaxException e) { 
            System.out.println("Regular expression syntax error"); 
        } catch (IllegalStateException e) { 
            System.out.println("Do not find the pattern"); 
        } finally { 
            if (ows != null) { 
                ows.close(); 
                fos.close(); 
            } 
        } 
    } 

    @SuppressWarnings("static-access")
	public static void main(String[] args) { 
        GuPiaoShuJu test = new GuPiaoShuJu(); 
//        try { 
//            test.extractor("http://money.finance.sina.com.cn/corp/go.php/vMS_MarketHistory/stockid/600004.phtml"); 
//        } catch (IOException e) { 
//            e.printStackTrace(); 
//        } 
        System.out.println(test.getDocumnetAt(retriveStockData("sh6").toString()));
    } 
    
    public static StringBuffer retriveStockData(String preCode) {
		
		String url = "http://hq.sinajs.cn/list=";
		//String url = STCOK_URL;
		
		@SuppressWarnings("unused")
		StringBuffer result = new StringBuffer("");
		
		StringBuffer sb = new StringBuffer(url);
		for(int i = 0; i < 10; i++){
			sb.append(preCode).append("0000").append(i).append(",");
		}
		for(int i = 10; i <= 99; i++){
			sb.append(preCode).append("000").append(i).append(",");
		}
		for(int i = 100; i < 149; i++){
			sb.append(preCode).append("00").append(i).append(",");
		}
		sb.append(preCode).append("00149");
		//result.append(retriveRealData(sb.toString()));
		
		
		//sb = new StringBuffer(url);
		for(int i = 150; i < 299; i++){
			sb.append(preCode).append("00").append(i).append(",");
		}
		sb.append(preCode).append("00299");
		//result.append(retriveRealData(sb.toString()));
		
		
		//sb = new StringBuffer(url);
		for(int i = 300; i < 449; i++){
			sb.append(preCode).append("00").append(i).append(",");
		}
		sb.append(preCode).append("00449");
		//result.append(retriveRealData(sb.toString()));
		
		
		//sb = new StringBuffer(url);
		for(int i = 450; i < 599; i++){
			sb.append(preCode).append("00").append(i).append(",");
		}
		sb.append(preCode).append("00599");
		//result.append(retriveRealData(sb.toString()));
		
		
		//sb = new StringBuffer(url);
		for(int i = 600; i < 749; i++){
			sb.append(preCode).append("00").append(i).append(",");
		}
		sb.append(preCode).append("00749");
		//result.append(retriveRealData(sb.toString()));
		
		
		//sb = new StringBuffer(url);
		for(int i = 750; i < 899; i++){
			sb.append(preCode).append("00").append(i).append(",");
		}
		sb.append(preCode).append("00899");
		//result.append(retriveRealData(sb.toString()));
		
		
		//sb = new StringBuffer(url);
		for(int i = 900; i < 999; i++){
			sb.append(preCode).append("00").append(i).append(",");
		}
		sb.append(preCode).append("00999").append(",");
		for(int i = 1000; i < 1049; i++){
			sb.append(preCode).append("0").append(i).append(",");
		}
		sb.append(preCode).append("01049");
		//result.append(retriveRealData(sb.toString()));
		
		
		//sb = new StringBuffer(url);
		for(int i = 1050; i < 1199; i++){
			sb.append(preCode).append("0").append(i).append(",");
		}
		sb.append(preCode).append("01199");
		//result.append(retriveRealData(sb.toString()));
		
		
		//sb = new StringBuffer(url);
		for(int i = 1200; i < 1349; i++){
			sb.append(preCode).append("0").append(i).append(",");
		}
		sb.append(preCode).append("01349");
		//result.append(retriveRealData(sb.toString()));
		
		
		//sb = new StringBuffer(url);
		for(int i = 1350; i < 1499; i++){
			sb.append(preCode).append("0").append(i).append(",");
		}
		sb.append(preCode).append("01499");
		//result.append(retriveRealData(sb.toString()));
		
		
		//sb = new StringBuffer(url);
		for(int i = 1500; i < 1649; i++){
			sb.append(preCode).append("0").append(i).append(",");
		}
		sb.append(preCode).append("01649");
		//result.append(retriveRealData(sb.toString()));
		
		
		//sb = new StringBuffer(url);
		for(int i = 1650; i < 1799; i++){
			sb.append(preCode).append("0").append(i).append(",");
		}
		sb.append(preCode).append("01799");
		//result.append(retriveRealData(sb.toString()));
		
		
		//sb = new StringBuffer(url);
		for(int i = 1800; i < 1949; i++){
			sb.append(preCode).append("0").append(i).append(",");
		}
		sb.append(preCode).append("01949");
		//result.append(retriveRealData(sb.toString()));
		
		
		//sb = new StringBuffer(url);
		for(int i = 1950; i < 2099; i++){
			sb.append(preCode).append("0").append(i).append(",");
		}
		sb.append(preCode).append("02099");
		//result.append(retriveRealData(sb.toString()));
		
		
		//sb = new StringBuffer(url);
		for(int i = 2100; i < 2249; i++){
			sb.append(preCode).append("0").append(i).append(",");
		}
		sb.append(preCode).append("02249");
		//result.append(retriveRealData(sb.toString()));

		
		//sb = new StringBuffer(url);
		for(int i = 2250; i < 2399; i++){
			sb.append(preCode).append("0").append(i).append(",");
		}
		sb.append(preCode).append("02399");
		//result.append(retriveRealData(sb.toString()));
		
		
		//sb = new StringBuffer(url);
		for(int i = 2400; i < 2549; i++){
			sb.append(preCode).append("0").append(i).append(",");
		}
		sb.append(preCode).append("02549");
		System.out.println(sb.toString());
		//result.append(retriveRealData(sb.toString()));
		
		
		//System.out.println(result.toString());
		return sb;
	}

}