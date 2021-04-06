package com.xxy.stock.web.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.comparator.CompoundComparator;

import com.xxy.stock.web.bo.BuySellRate;
import com.xxy.stock.web.bo.StockCache;
import com.xxy.stock.web.bo.StockComparator;
import com.xxy.stock.web.bo.StockStrongComparator;
import com.xxy.stock.web.bo.StockTencent;
import com.xxy.stock.web.constants.StockWebsiteConstants;

public class StockHelper implements StockWebsiteConstants {
	
	public final static ExecutorService pool = Executors.newCachedThreadPool();
	
	/**
	 * 初始化所有股票实时数据
	 * @throws InterruptedException 
	 */
	public static void doInitialAllStock() throws InterruptedException {
		//long startTime = System.currentTimeMillis();
		
		StockCache.clearStockMap();
		StockCache.clearRateMap();
		
		if("tencent".equalsIgnoreCase(STCOK_CHANNEL)){
			ClientHelper.retriveAllStockDataTencent();
		}else{
			ClientHelper.retriveAllStockDataSina();
		}
		
		Thread.sleep(60 * 1000);
		Map<String, StockTencent> map = StockCache.getStockMap();
		StockTencent stock = null;
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, StockTencent> entry : map.entrySet()) {
			stock = entry.getValue();
			//System.out.println(stock.getCode() + "=" + stock.getInfo());
			sb.append(stock.getCode() + "=" + stock.getInfo()).append("\n");
		}
		
		Resource res = new ClassPathResource("data.txt");
		try {
			FileUtil.writeFile(sb.toString(), "UTF-8", res.getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//long endTime = System.currentTimeMillis();
		//System.out.println("initial all stock cost " + (endTime - startTime) + " ms");
		
	}
	
	/**
	 * 刷新所有股票实时数据
	 */
	public static void doRefreshAllStock() {
		//long startTime = System.currentTimeMillis();
		
		if("tencent".equalsIgnoreCase(STCOK_CHANNEL)){
			ClientHelper.retriveAllStockDataTencent();
		}else{
			ClientHelper.retriveAllStockDataSina();
		}
		
		//long endTime = System.currentTimeMillis();
		//System.out.println("refresh all stock cost " + (endTime - startTime) + " ms");
		
	}

	/**
	 * 更新股票实时数据（新浪接口）
	 * @param sb
	 * 
	 * var hq_str_sz300329="海伦钢琴,11.260,11.420,12.210,12.510,11.240,12.210,12.220,13611157,160392599.630,25359,12.210,29491,12.200,5600,12.190,19900,12.180,1500,12.170,302700,12.220,45000,12.230,2900,12.240,8000,12.250,2500,12.260,2017-06-09,16:35:03,00";
	 * 
	 * 0：”大秦铁路”，股票名字；
	 * 1：”27.55″，今日开盘价；
	 * 2：”27.25″，昨日收盘价；
	 * 3：”26.91″，当前价格；
	 * 4：”27.55″，今日最高价；
	 * 5：”26.20″，今日最低价；
	 * 6：”26.91″，竞买价，即“买一”报价；
	 * 7：”26.92″，竞卖价，即“卖一”报价；
	 * 8：”22114263″，成交的股票数，由于股票交易以一百股为基本单位，所以在使用时，通常把该值除以一百；
	 * 9：”589824680″，成交金额，单位为“元”，为了一目了然，通常以“万元”为成交金额的单位，所以通常把该值除以一万；
	 * 10：”4695″，“买一”申请4695股，即47手；
	 * 11：”26.91″，“买一”报价；
	 * 12：”57590″，“买二”
	 * 13：”26.90″，“买二”
	 * 14：”14700″，“买三”
	 * 15：”26.89″，“买三”
	 * 16：”14300″，“买四”
	 * 17：”26.88″，“买四”
	 * 18：”15100″，“买五”
	 * 19：”26.87″，“买五”
	 * 20：”3100″，“卖一”申报3100股，即31手；
	 * 21：”26.92″，“卖一”报价
	 * (22, 23), (24, 25), (26,27), (28, 29)分别为“卖二”至“卖四的情况”
	 * 30：”2008-01-11″，日期；
	 * 31：”15:05:32″，时间；
	 * 
	 */
	public static void refreshStockSina(String stockInfo) {
		SimpleDateFormat sdf = new SimpleDateFormat("Hmm");
        int now = Integer.parseInt(sdf.format(new Date()));
		
		String[] sparatorList = stockInfo.split(";");	//多个股票数据以分号分隔
		StringTokenizer st = null;
		String left = null;
		String right = null;
		String[] data = null;
		String code = null;
		String id = null;
		double zs = 0;	//昨日收盘价
		double jk = 0;	//今日开盘价
		double xj = 0;	//现价
		double zg = 0;	//最高价
		double zd = 0;	//最低价
		double jj = 0;	//均价
		double zf = 0;	//涨幅
		double zfC = 0;	//涨幅跟均价涨幅差
		StockTencent stockSina = null;
		
		try {
			for (String str : sparatorList) {
				if (str.length() > 30) {
					st = new StringTokenizer(str, "=");
					left = st.nextToken();
					right = st.nextToken();
					data = right.split(",");
					
					if(data == null || data.length < 32 || "0".equals(data[8]) || "0".equals(data[9])){//未开市或错误
						continue;
					}
					
					code = left.substring(left.length()-8);
					id = code.substring(2);
					
					BigDecimal doneAmount = new BigDecimal(data[9]);
					BigDecimal doneQuantity = new BigDecimal(data[8]);
					try {
						jk = new BigDecimal(data[1]).doubleValue();	//今日开盘价
						zs = new BigDecimal(data[2]).doubleValue();	//昨日收盘价
						xj = new BigDecimal(data[3]).doubleValue();	//现价
						zg = new BigDecimal(data[4]).doubleValue();	//最高价
						zd = new BigDecimal(data[5]).doubleValue();	//最低价
						jj = doneAmount.divide(doneQuantity, 2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
						zf = (new BigDecimal(data[3]).subtract(new BigDecimal(data[2])))
							.divide(new BigDecimal(data[2]), 4, BigDecimal.ROUND_HALF_UP)
							.multiply(new BigDecimal(100)).doubleValue();
						zfC = (new BigDecimal(data[3]).subtract(new BigDecimal(jj)))
							.divide(new BigDecimal(data[2]), 4, BigDecimal.ROUND_HALF_UP)
							.multiply(new BigDecimal(100)).doubleValue();
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
					
					stockSina = StockCache.getStock(code);
					if (stockSina == null) {
						//stockSina = new StockSina(code,right,"black",0);
						stockSina = new StockTencent(code,right,"black",0);
					}

					//判断均线是否持强（即均线一直上升）
					if(jj >= stockSina.getJjHigh()){//均价大于等于最大均价
						stockSina.setJjHigh(jj);//均价攀升，保存最大值
						if(xj >= zs && xj >= jj){//涨幅大于等于0且现价大于等于均价
							stockSina.setJjStrongFlag(0);
						}else{//涨幅小于0或现价小于均价
							stockSina.setJjStrongFlag(-1);
						}
					}else{//均价小于最大均价，均价线下降
						stockSina.setJjStrongFlag(-1);
					}
					
					//判断价格是否一直处于均价线上运行
					if (xj < jj) {//价格跌破均价
						stockSina.setAlwaysStrongFlag(-1);//价格一旦跌破均价，则不符合一直处于均价线上原则
						if(doneQuantity.intValue() != stockSina.getDoneQuantity()){//中途休市排除
							stockSina.setUpJjTimes(stockSina.getUpJjTimes() - 1);
						}
					} else {//价格处于均价线上
						stockSina.setAlwaysStrongFlag(0);//价格一直处于均价线上
						if(doneQuantity.intValue() != stockSina.getDoneQuantity()){//中途休市排除
							stockSina.setUpJjTimes(stockSina.getUpJjTimes() + 1);
						}
					}
					
					try {
						stockSina.setId(id);
						stockSina.setInfo(right);
						stockSina.setJj(jj);
						stockSina.setZf(zf);
						stockSina.setName(data[0].substring(1));
						stockSina.setOpenToday(jk);
						stockSina.setCloseYesterday(zs);
						stockSina.setCloseToday(xj);
						stockSina.setHighToday(zg);
						stockSina.setLowToday(zd);
						stockSina.setJinBuy(toDouble(data[6]));
						stockSina.setJinSell(toDouble(data[7]));
						stockSina.setDoneQuantity(doneQuantity.intValue());
						if(now < 933) {
							stockSina.setDoneQuantity933(doneQuantity.intValue());
						}
						stockSina.setDoneAmount(doneAmount.doubleValue());
						stockSina.setBuy1Quantity(toInt(data[10]));
						stockSina.setBuy1Price(toDouble(data[11]));
						stockSina.setBuy2Quantity(toInt(data[12]));
						stockSina.setBuy2Price(toDouble(data[13]));
						stockSina.setBuy3Quantity(toInt(data[14]));
						stockSina.setBuy3Price(toDouble(data[15]));
						stockSina.setBuy4Quantity(toInt(data[16]));
						stockSina.setBuy4Price(toDouble(data[17]));
						stockSina.setBuy5Quantity(toInt(data[18]));
						stockSina.setBuy5Price(toDouble(data[19]));
						stockSina.setSell1Quantity(toInt(data[20]));
						stockSina.setSell1Price(toDouble(data[21]));
						stockSina.setSell2Quantity(toInt(data[22]));
						stockSina.setSell2Price(toDouble(data[23]));
						stockSina.setSell3Quantity(toInt(data[24]));
						stockSina.setSell3Price(toDouble(data[25]));
						stockSina.setSell4Quantity(toInt(data[26]));
						stockSina.setSell4Price(toDouble(data[27]));
						stockSina.setSell5Quantity(toInt(data[28]));
						stockSina.setSell5Price(toDouble(data[29]));
						stockSina.setUpdateDate(data[30]);
						stockSina.setUpdateTime(data[31].substring(0,data[31].length()-1));
						
						stockSina.setBuySumQuantity(stockSina.getBuy1Quantity() + stockSina.getBuy2Quantity() 
							+ stockSina.getBuy3Quantity() + stockSina.getBuy4Quantity() + stockSina.getBuy5Quantity());
						stockSina.setSellSumQuantity(stockSina.getSell1Quantity() + stockSina.getSell2Quantity() 
							+ stockSina.getSell3Quantity() + stockSina.getSell4Quantity() + stockSina.getSell5Quantity());
						
						if(zf > 0){
							stockSina.setZfColor("red");
						}else if(zf == 0){
							stockSina.setZfColor("black");
						}else{
							stockSina.setZfColor("green");
						}
						if(jk > zs){
							stockSina.setOpenStatus("高开");
							stockSina.setOpenColor("red");
						}else if(zf == zs){
							stockSina.setOpenStatus("平开");
							stockSina.setOpenColor("black");
						}else{
							stockSina.setOpenStatus("低开");
							stockSina.setOpenColor("green");
						}
						stockSina.setWindow("无缺");
						stockSina.setWindowColor("black");
						if(zg < zs){
							stockSina.setWindow("低缺");
							stockSina.setWindowColor("green");
						}
						if(zd > zs){
							stockSina.setWindow("高缺");
							stockSina.setWindowColor("red");
						}
						stockSina.setUpdateSum(stockSina.getUpdateSum() + 1 );
						stockSina.setBlock(StockCache.getBlock(id));
						stockSina.setSelectMark(StockCache.getSelect(id));
						stockSina.setZfC(zfC);
						if(zfC <= 1){
							stockSina.setZfCColor("red");
						}else if(zfC <= 2){
							stockSina.setZfCColor("black");
						}else{
							stockSina.setZfCColor("green");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					StockCache.putStock(code, stockSina);
					//System.out.println(str);
					
					new RefreshExtDataThread(now, stockSina).start();
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新股票实时数据（腾讯接口）
	 * @param sb
	 * 
	 * v_sz300329="51~海伦钢琴~300329~12.21~11.42~11.26~136111~67046~69066~12.21~253~12.20~294~12.19~56~12.18~199~12.17~15~12.22~3027~12.23~450~12.24~29~12.25~80~12.26~25~15:00:04/12.21/3595/S/4389996/9584|14:57:03/12.21/182/M/222188/9495|14:57:00/12.22/146/B/178396/9493|14:56:57/12.22/157/B/191737/9491|14:56:55/12.21/86/S/105114/9489|14:56:52/12.22/288/B/351866/9487~20170609150133~0.79~6.92~12.51~11.24~12.21/132516/156002604~136111~16039~5.43~89.38~~12.51~11.24~11.12~30.63~30.68~3.79~12.56~10.28~1.82";
	 * 
	 * 0: 未知  
 	 * 1: 名字  
 	 * 2: 代码  
 	 * 3: 当前价格  
 	 * 4: 昨收  
 	 * 5: 今开  
 	 * 6: 成交量（手）  
 	 * 7: 外盘  
 	 * 8: 内盘  
 	 * 9: 买一  
	 * 10: 买一量（手）  
	 * 11-18: 买二 买五  
	 * 19: 卖一  
	 * 20: 卖一量  
	 * 21-28: 卖二 卖五  
	 * 29: 最近逐笔成交  
	 * 30: 时间  
	 * 31: 涨跌  
	 * 32: 涨跌%  
	 * 33: 最高  
	 * 34: 最低  
	 * 35: 价格/成交量（手）/成交额  
	 * 36: 成交量（手）  
	 * 37: 成交额（万）  
	 * 38: 换手率  
	 * 39: 市盈率  
	 * 40:   
	 * 41: 最高  
	 * 42: 最低  
	 * 43: 振幅  
	 * 44: 流通市值  
	 * 45: 总市值  
	 * 46: 市净率  
	 * 47: 涨停价  
	 * 48: 跌停价  
	 * 49: 量比
	 */
	public static void refreshStockTencent(String stockInfo) {
		String[] sparatorList = stockInfo.split("v_");	//多个股票数据以分号分隔
		StringTokenizer st = null;
		String left = null;
		String right = null;
		String[] data = null;
		String code = null;
		String id = null;
		double zs = 0;	//昨日收盘价
		double jk = 0;	//今日开盘价
		double xj = 0;	//现价
		double zg = 0;	//最高价
		double zd = 0;	//最低价
		double jj = 0;	//均价
		double zf = 0;	//涨幅
		double zfC = 0;	//涨幅跟均价涨幅差
		StockTencent stockTencent = null;
		
		try {
			for (String str : sparatorList) {
				if (str.length() > 30) {
					str  = str.replaceAll("\"", "").replaceAll(";", "").replaceAll("\n", "");
					st = new StringTokenizer(str, "=");
					left = st.nextToken();
					right = st.nextToken();
					data = right.split("~");
					
					if(data == null || data.length < 50 || "0".equals(data[6]) || "0".equals(data[36]) || "0".equals(data[37])){//未开市或错误
						continue;
					}
					
					code = left.substring(left.length()-8);
					id = code.substring(2);
					
					BigDecimal doneAmount = new BigDecimal(data[37]).multiply(new BigDecimal(10000));
					BigDecimal doneQuantity = new BigDecimal(data[36]).multiply(new BigDecimal(100));
					try {
						jk = new BigDecimal(data[5]).doubleValue();		//今日开盘价
						zs = new BigDecimal(data[4]).doubleValue();		//昨日收盘价
						xj = new BigDecimal(data[3]).doubleValue();		//现价
						zg = new BigDecimal(data[41]).doubleValue();	//最高价
						zd = new BigDecimal(data[42]).doubleValue();	//最低价
						jj = doneAmount.divide(doneQuantity, 2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
						zf = (new BigDecimal(data[3]).subtract(new BigDecimal(data[4])))
							.divide(new BigDecimal(data[4]), 4, BigDecimal.ROUND_HALF_UP)
							.multiply(new BigDecimal(100)).doubleValue();
						zfC = (new BigDecimal(data[3]).subtract(new BigDecimal(jj)))
							.divide(new BigDecimal(data[4]), 4, BigDecimal.ROUND_HALF_UP)
							.multiply(new BigDecimal(100)).doubleValue();
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
					
					stockTencent = StockCache.getStock(code);
					if (stockTencent == null) {
						stockTencent = new StockTencent(code,right,"black",0);
					}

					//判断均线是否持强（即均线一直上升）
					if(jj >= stockTencent.getJjHigh()){//均价大于等于最大均价
						stockTencent.setJjHigh(jj);//均价攀升，保存最大值
						if(xj >= zs && xj >= jj){//涨幅大于等于0且现价大于等于均价
							stockTencent.setJjStrongFlag(0);
						}else{//涨幅小于0或现价小于均价
							stockTencent.setJjStrongFlag(-1);
						}
					}else{//均价小于最大均价，均价线下降
						stockTencent.setJjStrongFlag(-1);
					}
					
					//判断价格是否一直处于均价线上运行
					if (xj < jj) {//价格跌破均价
						stockTencent.setAlwaysStrongFlag(-1);//价格一旦跌破均价，则不符合一直处于均价线上原则
						if(doneQuantity.intValue() != stockTencent.getDoneQuantity()){//中途休市排除
							stockTencent.setUpJjTimes(stockTencent.getUpJjTimes() - 1);
						}
					} else {//价格处于均价线上
						stockTencent.setAlwaysStrongFlag(0);//价格一直处于均价线上
						if(doneQuantity.intValue() != stockTencent.getDoneQuantity()){//中途休市排除
							stockTencent.setUpJjTimes(stockTencent.getUpJjTimes() + 1);
						}
					}
					
					try {
						stockTencent.setId(id);
						stockTencent.setInfo(right);
						stockTencent.setJj(jj);
						stockTencent.setZf(zf);
						stockTencent.setName(data[1]);
						stockTencent.setOpenToday(jk);
						stockTencent.setCloseYesterday(zs);
						stockTencent.setCloseToday(xj);
						stockTencent.setHighToday(zg);
						stockTencent.setLowToday(zd);
						//stockTencent.setJinBuy();
						//stockTencent.setJinSell();
						stockTencent.setDoneQuantity(doneQuantity.intValue());
						stockTencent.setDoneAmount(doneAmount.doubleValue());
						stockTencent.setBuy1Quantity(toInt(data[10])*100);
						stockTencent.setBuy1Price(toDouble(data[9]));
						stockTencent.setBuy2Quantity(toInt(data[12])*100);
						stockTencent.setBuy2Price(toDouble(data[11]));
						stockTencent.setBuy3Quantity(toInt(data[14])*100);
						stockTencent.setBuy3Price(toDouble(data[13]));
						stockTencent.setBuy4Quantity(toInt(data[16])*100);
						stockTencent.setBuy4Price(toDouble(data[15]));
						stockTencent.setBuy5Quantity(toInt(data[18])*100);
						stockTencent.setBuy5Price(toDouble(data[17]));
						stockTencent.setSell1Quantity(toInt(data[20])*100);
						stockTencent.setSell1Price(toDouble(data[19]));
						stockTencent.setSell2Quantity(toInt(data[22])*100);
						stockTencent.setSell2Price(toDouble(data[21]));
						stockTencent.setSell3Quantity(toInt(data[24])*100);
						stockTencent.setSell3Price(toDouble(data[23]));
						stockTencent.setSell4Quantity(toInt(data[26])*100);
						stockTencent.setSell4Price(toDouble(data[25]));
						stockTencent.setSell5Quantity(toInt(data[28])*100);
						stockTencent.setSell5Price(toDouble(data[27]));
						stockTencent.setUpdateDate(data[30]);
						//stockTencent.setUpdateTime();
						stockTencent.setZd(toDouble(data[31]));
						stockTencent.setHsl(toDouble(data[38]));
						stockTencent.setSyl(toDouble(data[39]));
						stockTencent.setIndex40(data[40]);
						stockTencent.setSjl(toDouble(data[46]));
						stockTencent.setLtsz(toDouble(data[44]));
						stockTencent.setZsz(toDouble(data[45]));
						stockTencent.setZtj(toDouble(data[47]));
						stockTencent.setDtj(toDouble(data[48]));
						stockTencent.setLb(toDouble(data[49]));
						
						stockTencent.setBuySumQuantity(stockTencent.getBuy1Quantity() + stockTencent.getBuy2Quantity() 
							+ stockTencent.getBuy3Quantity() + stockTencent.getBuy4Quantity() + stockTencent.getBuy5Quantity());
						stockTencent.setSellSumQuantity(stockTencent.getSell1Quantity() + stockTencent.getSell2Quantity() 
							+ stockTencent.getSell3Quantity() + stockTencent.getSell4Quantity() + stockTencent.getSell5Quantity());
						
						if(zf > 0){
							stockTencent.setZfColor("red");
						}else if(zf == 0){
							stockTencent.setZfColor("black");
						}else{
							stockTencent.setZfColor("green");
						}
						if(jk > zs){
							stockTencent.setOpenStatus("高开");
							stockTencent.setOpenColor("red");
						}else if(zf == zs){
							stockTencent.setOpenStatus("平开");
							stockTencent.setOpenColor("black");
						}else{
							stockTencent.setOpenStatus("低开");
							stockTencent.setOpenColor("green");
						}
						stockTencent.setWindow("无缺");
						stockTencent.setWindowColor("black");
						if(zg < zs){
							stockTencent.setWindow("低缺");
							stockTencent.setWindowColor("green");
						}
						if(zd > zs){
							stockTencent.setWindow("高缺");
							stockTencent.setWindowColor("red");
						}
						stockTencent.setUpdateSum(stockTencent.getUpdateSum() + 1 );
						stockTencent.setBlock(StockCache.getBlock(id));
						stockTencent.setSelectMark(StockCache.getSelect(id));
						stockTencent.setZfC(zfC);
						if(zfC <= 1){
							stockTencent.setZfCColor("red");
						}else if(zfC <= 2){
							stockTencent.setZfCColor("black");
						}else{
							stockTencent.setZfCColor("green");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					StockCache.putStock(code, stockTencent);
					//System.out.println(str);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新股票实时买卖比率（腾讯接口）
	 * @param sb
	 * 
	 * v_s_pksz300329="0.046~0.446~0.052~0.455";
	 * 
	 * 0: 买盘大单
	 * 1: 买盘小单
	 * 2: 卖盘大单
	 * 3: 卖盘小单
	 */
	public static void refreshStockBuySellRate(String stockInfo) {
		String[] sparatorList = stockInfo.split(";");	//多个股票数据以分号分隔
		StringTokenizer st = null;
		String left = null;
		String right = null;
		String[] data = null;
		String code = null;
		String id = null;
		BuySellRate rate = null;
		
		try {
			for (String str : sparatorList) {
				if (str.length() > 30) {
					str  = str.replaceAll("\"", "").replaceAll(";", "").replaceAll("v_s_pk", "").replaceAll("\n", "");
					st = new StringTokenizer(str, "=");
					left = st.nextToken();
					right = st.nextToken();
					data = right.split("~");
					
					if(data == null || data.length < 4 || "0.000".equals(data[1]) || "0.00".equals(data[1]) || "0".equals(data[1])){//未开市或错误
						continue;
					}
					
					code = left;
					id = code.substring(2);
					
					rate = StockCache.getRate(code);
					if (rate == null) {
						rate = new BuySellRate(code, right, "black", 0);
					}
					
					try {
						rate.setId(id);
						rate.setInfo(right);
						StockTencent stock = StockCache.getStock(code);
						if(stock != null){
							rate.setName(stock.getName());
							rate.setCloseToday(stock.getCloseToday());
							rate.setJj(stock.getJj());
							rate.setZf(stock.getZf());
							rate.setJjZf(stock.getJjZf());
							rate.setZfC(stock.getZfC());
							rate.setZfColor(stock.getZfColor());
							rate.setZfCColor(stock.getZfCColor());
							rate.setUpJjTimes(stock.getUpJjTimes());
							rate.setLtsz(stock.getLtsz());
							rate.setZsz(stock.getZsz());
							rate.setLb(stock.getLb());
						}else{
							rate.setName("");
							rate.setCloseToday(0);
							rate.setJj(0);
							rate.setZf(0);
							rate.setJjZf(0);
							rate.setZfC(0);
							rate.setZfColor("");
							rate.setZfCColor("");
							rate.setUpJjTimes(0);
							rate.setLtsz(0);
							rate.setZsz(0);
							rate.setLb(0);
						}
						
						rate.setBuyBigRate(toDouble(data[0])*100);
						rate.setBuySamllRate(toDouble(data[1])*100);
						rate.setSellBigRate(toDouble(data[2])*100);
						rate.setSellSamllRate(toDouble(data[3])*100);
						rate.setBuyRate((new BigDecimal(data[0]).add(new BigDecimal(data[1]))).doubleValue()*100);
						rate.setSellRate((new BigDecimal(data[2]).add(new BigDecimal(data[3]))).doubleValue()*100);
						if(rate.getSellBigRate() >0){
						    rate.setBigTime(rate.getBuyBigRate()/rate.getSellBigRate());
						}else{
						    rate.setBigTime(rate.getBuyBigRate());
						}
						
						if(rate.getBuyRate() > 50){
							rate.setRateColor("red");
						}else if(rate.getBuyRate() == 0){
							rate.setRateColor("black");
						}else{
							rate.setRateColor("green");
						}
						rate.setUpdateSum(rate.getUpdateSum() + 1 );
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					StockCache.putRate(code, rate);
					//System.out.println(str);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 刷新所有股票实时买卖比例
	 */
	public static void doRefreshAllStockBuySellRate() {
		//long startTime = System.currentTimeMillis();
		
		ClientHelper.retriveAllStockBuySellRate();
		
		//long endTime = System.currentTimeMillis();
		//System.out.println("refresh all stock cost " + (endTime - startTime) + " ms");
		
	}
	
	/**
	 * 初始化所有版块
	 */
	public static void doInitialAllBlock() {
		//long startTime = System.currentTimeMillis();
		Resource res = new ClassPathResource("block.txt");
		try {
			FileInputStream in = new FileInputStream(res.getFile());
	        
	        Reader reader = new InputStreamReader(in, "utf8");
	        
			BufferedReader br = new BufferedReader(reader);
			String str = null;
			StringTokenizer st = null;
			while ((str = br.readLine()) != null) {
				st = new StringTokenizer(str, "=");
				StockCache.putBlock(st.nextToken(), st.nextToken());
			}
			br.close();
			reader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		Map<String, StockTencent> map = StockCache.getStockMap();
		StockTencent stock = null;
		for (Map.Entry<String, StockTencent> entry : map.entrySet()) {
			stock = entry.getValue();
			stock.setBlock(StockCache.getBlock(stock.getId()));
			StockCache.putStock(entry.getKey(), stock);
		}
		
		//long endTime = System.currentTimeMillis();
		//System.out.println("initial all block cost " + (endTime - startTime) + " ms");
		//System.out.println("initial all block success at " + new Date());
		
	}

	/**
	 * 上传版块文件
	 * @param request
	 */
	@SuppressWarnings("rawtypes")
	public static void doUploadAllBlock(HttpServletRequest request) {
		Resource res = new ClassPathResource("block.txt");
		FileItemFactory factory = new DiskFileItemFactory();   
		ServletFileUpload upload = new ServletFileUpload(factory);
		List fileItems = null;
		try {
			fileItems = upload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		Iterator i = fileItems.iterator();
		
		while (i.hasNext()){
			FileItem fi = (FileItem) i.next();
			//忽略其他不是文件域的所有表单信息
			if (!fi.isFormField()) {
				if((fi.getName() == null || fi.getName().equals("")) && fi.getSize()==0){
					continue; 
				}
				try {
					fi.write(res.getFile());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 下载版本文件
	 * @param response
	 */
	public static void doDownloadAllBlock(HttpServletResponse response) {
		Resource res = new ClassPathResource("block.txt");
		FileInputStream in = null; //输入流
        ServletOutputStream out = null; //输出流
		response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Content-disposition", "attachment; filename=block.txt");
        
        try {
			in = new FileInputStream(res.getFile());
			out = response.getOutputStream();
	        out.flush();
	        int aRead = 0;
	        while((aRead = in.read()) != -1 & in != null)
	        {
	        	out.write(aRead);
	        }
	      	out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            try{
            	in.close();
                out.close();
            }
            catch(Throwable e){
            	e.printStackTrace();
            }
        }
	}
	
	/**
	 * 初始化自选股
	 */
	public static void doInitialSelectStock() {
		//long startTime = System.currentTimeMillis();
		Resource res = new ClassPathResource("select.blk");
		StockCache.clearSelectMap();
		try {
			FileInputStream in = new FileInputStream(res.getFile());
	        
	        Reader reader = new InputStreamReader(in, "utf8");
	        
			BufferedReader br = new BufferedReader(reader);
			String str = null;
			String id = null;
			while ((str = br.readLine()) != null) {
				if(str.length() >= 6){
					id = str.substring(str.length() - 6);
					StockCache.putSelect(id, "优");
				}
			}
			br.close();
			reader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		Map<String, StockTencent> map = StockCache.getStockMap();
		StockTencent stock = null;
		for (Map.Entry<String, StockTencent> entry : map.entrySet()) {
			stock = entry.getValue();
			stock.setSelectMark(StockCache.getSelect(stock.getId()));
			StockCache.putStock(entry.getKey(), stock);
		}
		
		//long endTime = System.currentTimeMillis();
		//System.out.println("initial all select cost " + (endTime - startTime) + " ms");
		//System.out.println("initial all select success at " + new Date());
		
	}

	/**
	 * 上传自选股
	 * @param request
	 */
	@SuppressWarnings("rawtypes")
	public static void doUploadSelectStock(HttpServletRequest request) {
		Resource res = new ClassPathResource("select.blk");
		FileItemFactory factory = new DiskFileItemFactory();   
		ServletFileUpload upload = new ServletFileUpload(factory);
		List fileItems = null;
		try {
			fileItems = upload.parseRequest(request);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		Iterator i = fileItems.iterator();
		
		while (i.hasNext()){
			FileItem fi = (FileItem) i.next();
			//忽略其他不是文件域的所有表单信息
			if (!fi.isFormField()) {
				if((fi.getName() == null || fi.getName().equals("")) && fi.getSize()==0){
					continue; 
				}
				try {
					fi.write(res.getFile());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 本地写自选股
	 * @param number
	 */
	@SuppressWarnings("unchecked")
	public static void doLocalWriteZxgFile(int number) {
		Map<String, StockTencent> map = StockCache.getStockMap();

		List<StockTencent> list = new ArrayList<StockTencent>();
		StockTencent stock = null;
		for (Map.Entry<String, StockTencent> entry : map.entrySet()) {
			stock = entry.getValue();
//			if (stock.getDoneQuantity() == 0 || stock.getZf() < 0
//					|| stock.getUpJjTimes() < stock.getUpdateSum()/3
//					|| stock.getCloseToday() < stock.getJj()
//					|| stock.getCloseYesterday() <= 0
//					|| stock.getName().contains("S")
//					|| stock.getLtsz() > ZXG_LTSZ
//					|| stock.getLb() < ZXG_LB) {
//				continue;
//			}
			
			if (stock.getDoneQuantity() == 0
					|| stock.getUpJjTimes() < stock.getUpdateSum()/4
					|| stock.getCloseYesterday() <= 0
					|| stock.getName().contains("S")
					|| stock.getLtsz() > ZXG_LTSZ) {
				continue;
			}
			
			list.add(stock);
		}

		CompoundComparator cc = new CompoundComparator();
		cc.addComparator(new StockStrongComparator(),true);
		cc.addComparator(new StockComparator(),true);
		
		Collections.sort(list, cc);

		if(list.size() > number){
			list = list.subList(0, number);
		}else{
			list = list.subList(0, list.size());
		}
		
		StringBuffer sb = new StringBuffer();
		for(StockTencent s : list){
			sb.append(s.getCode().replaceAll("sh", "1").replaceAll("sz", "0")).append("\r\n");
		}
		FileUtil.writeFile(sb.toString(), "UTF-8", LOCAL_ZXG_FILE);
		//FileUtil.writeFile(sb.toString(), "UTF-8", LOCAL_ZXG_FILE2);
		
		new DownloadExtDataThread().start();
	}
	
	/**
	 * 下载自选股
	 * @param response
	 * @param number
	 */
	@SuppressWarnings("unchecked")
	public static void doDownloadZxg(HttpServletResponse response, int number) {
		Map<String, StockTencent> map = StockCache.getStockMap();

		List<StockTencent> list = new ArrayList<StockTencent>();
		StockTencent stock = null;
		for (Map.Entry<String, StockTencent> entry : map.entrySet()) {
			stock = entry.getValue();
			//if (stock.getDoneQuantity() == 0 || stock.getZf() < 0
			if (stock.getDoneQuantity() == 0 || stock.getZf() < 1.8
					|| stock.getUpJjTimes() < stock.getUpdateSum()/3
					|| stock.getCloseToday() < stock.getJj()
					|| stock.getCloseYesterday() <= 0
					|| stock.getName().contains("S")
					|| stock.getLtsz() > ZXG_LTSZ
					|| stock.getLb() < ZXG_LB) {
				continue;
			}
			list.add(stock);
		}

		CompoundComparator cc = new CompoundComparator();
		cc.addComparator(new StockStrongComparator(),true);
		cc.addComparator(new StockComparator(),true);
		
		Collections.sort(list, cc);

		if(list.size() > number){
			list = list.subList(0, number);
		}else{
			list = list.subList(0, list.size());
		}
		
		StringBuffer sb = new StringBuffer();
		for(StockTencent s : list){
			sb.append(s.getCode().replaceAll("sh", "1").replaceAll("sz", "0")).append("\r\n");
		}
		
		DataInputStream in = null; //输入流
        ServletOutputStream out = null; //输出流
		response.setContentType("text/plain; charset=UTF-8");
        //response.setHeader("Content-disposition", "attachment; filename=ZXG.txt");
        response.setHeader("Content-disposition", "attachment; filename=ZXG.EBK");
        
        try {
			in =  new DataInputStream(new ByteArrayInputStream(sb.toString().getBytes())); 
			out = response.getOutputStream();
	        out.flush();
	        int aRead = 0;
	        while((aRead = in.read()) != -1 & in != null)
	        {
	        	out.write(aRead);
	        }
	      	out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            try{
            	in.close();
                out.close();
            }
            catch(Throwable e){
            	e.printStackTrace();
            }
        }
	}
	

	
	/**
	 * 下载自选股(靠近均价)
	 * @param response
	 * @param number
	 */
	@SuppressWarnings("unchecked")
	public static void doDownloadZxgJj(HttpServletResponse response, int number) {
		Map<String, StockTencent> map = StockCache.getStockMap();

		List<StockTencent> list = new ArrayList<StockTencent>();
		StockTencent stock = null;
		for (Map.Entry<String, StockTencent> entry : map.entrySet()) {
			stock = entry.getValue();
			if (stock.getDoneQuantity() == 0 || stock.getZf() < 0
					|| stock.getZfC() >= 1
					|| stock.getUpJjTimes() < stock.getUpdateSum()/3
					|| stock.getCloseToday() < stock.getJj()
					|| stock.getCloseYesterday() <= 0
					|| stock.getName().contains("S")
					|| stock.getLtsz() > ZXG_LTSZ
					|| stock.getLb() < ZXG_LB) {
				continue;
			}
			list.add(stock);
		}

		CompoundComparator cc = new CompoundComparator();
		cc.addComparator(new StockStrongComparator(),true);
		cc.addComparator(new StockComparator(),true);
		
		Collections.sort(list, cc);

		if(list.size() > number){
			list = list.subList(0, number);
		}else{
			list = list.subList(0, list.size());
		}
		
		StringBuffer sb = new StringBuffer();
		for(StockTencent s : list){
			sb.append(s.getCode().replaceAll("sh", "1").replaceAll("sz", "0")).append("\r\n");
		}
		
		DataInputStream in = null; //输入流
        ServletOutputStream out = null; //输出流
		response.setContentType("text/plain; charset=UTF-8");
        //response.setHeader("Content-disposition", "attachment; filename=ZXG.txt");
        response.setHeader("Content-disposition", "attachment; filename=ZXG.EBK");
        
        try {
			in =  new DataInputStream(new ByteArrayInputStream(sb.toString().getBytes())); 
			out = response.getOutputStream();
	        out.flush();
	        int aRead = 0;
	        while((aRead = in.read()) != -1 & in != null)
	        {
	        	out.write(aRead);
	        }
	      	out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            try{
            	in.close();
                out.close();
            }
            catch(Throwable e){
            	e.printStackTrace();
            }
        }
	}
	
	/**
     * 本地写盯盘股
     * @param number
     */
    public static void doAddViewFile(String stockCode) {
        Path path = Paths.get(LOCAL_VIEW_FILE);
        stockCode = stockCode.replaceAll("sh", "1").replaceAll("sz", "0");
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        try {
            if(Files.exists(path)){
                reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
                String str = null;
                while ((str = reader.readLine()) != null) {
                    sb.append(str).append("\r\n");
                    if(str.contains(stockCode)){
                        return;
                    }
                }
            }else{
                System.out.println("[" + path.toString() + "] file not exist...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        sb.append(stockCode).append("\r\n");
        
        //FileUtil.writeFile(sb.toString(), "UTF-8", LOCAL_VIEW_FILE);
        
        try {
            BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
            writer.write(sb.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	private static double toDouble(String str) {
		if(str == null || "".equals(str.trim())){
			return 0;
		}
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	private static int toInt(String str) {
		if(str == null || "".equals(str.trim())){
			return 0;
		}
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 本地写自定义数据
	 * @param number
	 */
	public static void doLocalWriteExternalFile() {
		Map<String, StockTencent> map = StockCache.getStockMap();

		StringBuffer sb = new StringBuffer();
		StockTencent stock = null;
		for (Map.Entry<String, StockTencent> entry : map.entrySet()) {
			stock = entry.getValue();
			sb.append(stock.getCode().startsWith("sh")?"1":"0").append("|").append(stock.getId()).append("|").append("1").append("|").append("JJCQ").append("|").append(stock.getUpJjTimes()).append("\r\n");
			sb.append(stock.getCode().startsWith("sh")?"1":"0").append("|").append(stock.getId()).append("|").append("2").append("|").append("JC").append("|").append(stock.getZfC()).append("\r\n");
		}

		FileUtil.writeFile(sb.toString(), "UTF-8", EXTERNAL_FILE_DATA);
		new File(EXTERNAL_FILE_CONF).setLastModified(System.currentTimeMillis());
	}
	
	/**
	 * 下载所有均价持强数据
	 * @param response
	 * @param number
	 */
	public static void doDownloadExternal(HttpServletResponse response) {
		Map<String, StockTencent> map = StockCache.getStockMap();

		StringBuffer sb = new StringBuffer();
		StockTencent stock = null;
		for (Map.Entry<String, StockTencent> entry : map.entrySet()) {
			stock = entry.getValue();
			sb.append(stock.getCode().startsWith("sh")?"1":"0").append("|").append(stock.getId()).append("|").append("JJCQ").append("|").append(stock.getUpJjTimes()).append("\r\n");
		}
		
		DataInputStream in = null; //输入流
        ServletOutputStream out = null; //输出流
		response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Content-disposition", "attachment; filename=extern.txt");
        
        try {
			in =  new DataInputStream(new ByteArrayInputStream(sb.toString().getBytes())); 
			out = response.getOutputStream();
	        out.flush();
	        int aRead = 0;
	        while((aRead = in.read()) != -1 & in != null)
	        {
	        	out.write(aRead);
	        }
	      	out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            try{
            	in.close();
                out.close();
            }
            catch(Throwable e){
            	e.printStackTrace();
            }
        }
	}
	

	
	/**
	 * 下载所有均价持强数据
	 * @param response
	 * @param number
	 */
	public static void doDownloadExternalJc(HttpServletResponse response) {
		Map<String, StockTencent> map = StockCache.getStockMap();

		StringBuffer sb = new StringBuffer();
		StockTencent stock = null;
		for (Map.Entry<String, StockTencent> entry : map.entrySet()) {
			stock = entry.getValue();
			sb.append(stock.getCode().startsWith("sh")?"1":"0").append("|").append(stock.getId()).append("|").append("JC").append("|").append(stock.getZfC()).append("\r\n");
		}
		
		DataInputStream in = null; //输入流
        ServletOutputStream out = null; //输出流
		response.setContentType("text/plain; charset=UTF-8");
        response.setHeader("Content-disposition", "attachment; filename=extern_jc.txt");
        
        try {
			in =  new DataInputStream(new ByteArrayInputStream(sb.toString().getBytes())); 
			out = response.getOutputStream();
	        out.flush();
	        int aRead = 0;
	        while((aRead = in.read()) != -1 & in != null)
	        {
	        	out.write(aRead);
	        }
	      	out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            try{
            	in.close();
                out.close();
            }
            catch(Throwable e){
            	e.printStackTrace();
            }
        }
	}
	
	

}

class RefreshExtDataThread extends Thread {
	
	private StockTencent stockSina;
	private int now;
	
	public RefreshExtDataThread(int now, StockTencent stockTencent){
		this.stockSina = stockTencent;
		this.now = now;
	}
	
	public void run() {
		try {
			StockTencent stockSina0945;
			StockTencent stockSina1000;
			StockTencent stockSina1030;
			StockTencent stockSina1100;
			StockTencent stockSina1130;
			
			StockCache.putStock0930(stockSina.getCode(), stockSina);
			
			if(now >= 945) {
				stockSina0945 = StockCache.getStock0945(stockSina.getCode());
				if (stockSina0945 == null) {
					stockSina0945 = new StockTencent(stockSina.getCode(), stockSina.getInfo(), "black", 0);
				}
				
				//判断价格是否一直处于均价线上运行
				if (stockSina.getCloseToday() < stockSina.getJj()) {//价格跌破均价
					stockSina0945.setAlwaysStrongFlag(-1);//价格一旦跌破均价，则不符合一直处于均价线上原则
					stockSina0945.setUpJjTimes(stockSina0945.getUpJjTimes() - 1);
				} else {//价格处于均价线上
					stockSina0945.setAlwaysStrongFlag(0);//价格一直处于均价线上
					stockSina0945.setUpJjTimes(stockSina0945.getUpJjTimes() + 1);
				}
				int upJjTimes = stockSina0945.getUpJjTimes();
				BeanUtils.copyProperties(stockSina0945, stockSina);
				stockSina0945.setUpJjTimes(upJjTimes);
				StockCache.putStock0945(stockSina.getCode(), stockSina0945);
			}
			
			if(now >= 1000) {
				stockSina1000 = StockCache.getStock1000(stockSina.getCode());
				if (stockSina1000 == null) {
					stockSina1000 = new StockTencent(stockSina.getCode(), stockSina.getInfo(), "black", 0);
				}
				
				//判断价格是否一直处于均价线上运行
				if (stockSina.getCloseToday() < stockSina.getJj()) {//价格跌破均价
					stockSina1000.setAlwaysStrongFlag(-1);//价格一旦跌破均价，则不符合一直处于均价线上原则
					stockSina1000.setUpJjTimes(stockSina1000.getUpJjTimes() - 1);
				} else {//价格处于均价线上
					stockSina1000.setAlwaysStrongFlag(0);//价格一直处于均价线上
					stockSina1000.setUpJjTimes(stockSina1000.getUpJjTimes() + 1);
				}
				int upJjTimes = stockSina1000.getUpJjTimes();
				BeanUtils.copyProperties(stockSina1000, stockSina);
				stockSina1000.setUpJjTimes(upJjTimes);
				StockCache.putStock1000(stockSina.getCode(), stockSina1000);
			}
			
			if(now >= 1030) {
				stockSina1030 = StockCache.getStock1030(stockSina.getCode());
				if (stockSina1030 == null) {
					stockSina1030 = new StockTencent(stockSina.getCode(), stockSina.getInfo(), "black", 0);
				}
				
				//判断价格是否一直处于均价线上运行
				if (stockSina.getCloseToday() < stockSina.getJj()) {//价格跌破均价
					stockSina1030.setAlwaysStrongFlag(-1);//价格一旦跌破均价，则不符合一直处于均价线上原则
					stockSina1030.setUpJjTimes(stockSina1030.getUpJjTimes() - 1);
				} else {//价格处于均价线上
					stockSina1030.setAlwaysStrongFlag(0);//价格一直处于均价线上
					stockSina1030.setUpJjTimes(stockSina1030.getUpJjTimes() + 1);
				}
				int upJjTimes = stockSina1030.getUpJjTimes();
				BeanUtils.copyProperties(stockSina1030, stockSina);
				stockSina1030.setUpJjTimes(upJjTimes);
				StockCache.putStock1030(stockSina.getCode(), stockSina1030);
			}
			
			if(now >= 1100) {
				stockSina1100 = StockCache.getStock1100(stockSina.getCode());
				if (stockSina1100 == null) {
					stockSina1100 = new StockTencent(stockSina.getCode(), stockSina.getInfo(), "black", 0);
				}
				
				//判断价格是否一直处于均价线上运行
				if (stockSina.getCloseToday() < stockSina.getJj()) {//价格跌破均价
					stockSina1100.setAlwaysStrongFlag(-1);//价格一旦跌破均价，则不符合一直处于均价线上原则
					stockSina1100.setUpJjTimes(stockSina1100.getUpJjTimes() - 1);
				} else {//价格处于均价线上
					stockSina1100.setAlwaysStrongFlag(0);//价格一直处于均价线上
					stockSina1100.setUpJjTimes(stockSina1100.getUpJjTimes() + 1);
				}
				int upJjTimes = stockSina1100.getUpJjTimes();
				BeanUtils.copyProperties(stockSina1100, stockSina);
				stockSina1100.setUpJjTimes(upJjTimes);
				StockCache.putStock1100(stockSina.getCode(), stockSina1100);
			}
			
			if(now >= 1130) {
				stockSina1130 = StockCache.getStock1130(stockSina.getCode());
				if (stockSina1130 == null) {
					stockSina1130 = new StockTencent(stockSina.getCode(), stockSina.getInfo(), "black", 0);
				}
				
				//判断价格是否一直处于均价线上运行
				if (stockSina.getCloseToday() < stockSina.getJj()) {//价格跌破均价
					stockSina1130.setAlwaysStrongFlag(-1);//价格一旦跌破均价，则不符合一直处于均价线上原则
					stockSina1130.setUpJjTimes(stockSina1130.getUpJjTimes() - 1);
				} else {//价格处于均价线上
					stockSina1130.setAlwaysStrongFlag(0);//价格一直处于均价线上
					stockSina1130.setUpJjTimes(stockSina1130.getUpJjTimes() + 1);
				}
				int upJjTimes = stockSina1130.getUpJjTimes();
				BeanUtils.copyProperties(stockSina1130, stockSina);
				stockSina1130.setUpJjTimes(upJjTimes);
				StockCache.putStock1130(stockSina.getCode(), stockSina1130);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class DownloadExtDataThread extends Thread implements StockWebsiteConstants {
	
	public void run() {
		try {
			doLocalWriteZxgFile0945();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void doLocalWriteZxgFile0945() {
		doLocalWriteZxgFile0945AndVol(StockCache.getStockMap0930(), LOCAL_ZXG_FILE0930, 100);
		doLocalWriteZxgFile0945(StockCache.getStockMap0945(), LOCAL_ZXG_FILE0945, 100);
		doLocalWriteZxgFile0945(StockCache.getStockMap1000(), LOCAL_ZXG_FILE1000, 100);
		doLocalWriteZxgFile0945(StockCache.getStockMap1030(), LOCAL_ZXG_FILE1030, 100);
		doLocalWriteZxgFile0945(StockCache.getStockMap1100(), LOCAL_ZXG_FILE1100, 100);
		doLocalWriteZxgFile0945(StockCache.getStockMap1130(), LOCAL_ZXG_FILE1130, 100);
	}
	
	public static void doLocalWriteZxgFile0945(Map<String, StockTencent> map, String filePath, int number) {
		List<StockTencent> list = new ArrayList<StockTencent>();
		StockTencent stock = null;
		for (Map.Entry<String, StockTencent> entry : map.entrySet()) {
			stock = entry.getValue();			
			if (stock.getDoneQuantity() == 0
//					|| stock.getUpJjTimes() < stock.getUpdateSum()/4
					|| stock.getCloseYesterday() <= 0
					|| stock.getName().contains("S")
					|| stock.getLtsz() > ZXG_LTSZ
					|| stock.getZf() > 7) {
				continue;
			}
			
			list.add(stock);
		}

		CompoundComparator cc = new CompoundComparator();
		cc.addComparator(new StockStrongComparator(),true);
		cc.addComparator(new StockComparator(),true);
		
		Collections.sort(list, cc);

		if(list.size() > number){
			list = list.subList(0, number);
		}else{
			list = list.subList(0, list.size());
		}
		
		StringBuffer sb = new StringBuffer();
		for(StockTencent s : list){
			sb.append(s.getCode().replaceAll("sh", "1").replaceAll("sz", "0")).append("\r\n");
		}
		FileUtil.writeFile(sb.toString(), "UTF-8", filePath);
	}
	
	public static void doLocalWriteZxgFile0945AndVol(Map<String, StockTencent> map, String filePath, int number) {
		List<StockTencent> list = new ArrayList<StockTencent>();
		StockTencent stock = null;
		for (Map.Entry<String, StockTencent> entry : map.entrySet()) {
			stock = entry.getValue();			
			if (stock.getDoneQuantity() == 0
//					|| stock.getUpJjTimes() < stock.getUpdateSum()/4
					|| stock.getCloseYesterday() <= 0
					|| stock.getName().contains("S")
					|| stock.getLtsz() > ZXG_LTSZ
					|| stock.getZf() > 7
					|| stock.getDoneQuantity933() > 500000) {
				continue;
			}
			
			list.add(stock);
		}

		CompoundComparator cc = new CompoundComparator();
		cc.addComparator(new StockStrongComparator(),true);
		cc.addComparator(new StockComparator(),true);
		
		Collections.sort(list, cc);

		if(list.size() > number){
			list = list.subList(0, number);
		}else{
			list = list.subList(0, list.size());
		}
		
		StringBuffer sb = new StringBuffer();
		for(StockTencent s : list){
			sb.append(s.getCode().replaceAll("sh", "1").replaceAll("sz", "0")).append("\r\n");
		}
		FileUtil.writeFile(sb.toString(), "UTF-8", filePath);
	}
}
