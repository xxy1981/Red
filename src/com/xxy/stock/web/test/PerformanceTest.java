package com.xxy.stock.web.test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.core.io.ClassPathResource;

import com.xxy.stock.web.bo.StockCache;
import com.xxy.stock.web.bo.StockTencent;
import com.xxy.stock.web.util.FileUtil;
import com.xxy.stock.web.util.StringUtil;

public class PerformanceTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		TestSplit();
		TestCalculation();
		
	}

	private static void TestCalculation() throws IOException {
		long t = System.currentTimeMillis();
		String all = FileUtil.file2String(new ClassPathResource("data.txt").getFile(),"utf8");
		System.out.println(System.currentTimeMillis() - t);
		
		t = System.currentTimeMillis();
		for (int i = 0; i < 100; ++i) {
			String[] sparatorList = StringUtil.split(all, ";");
			StringTokenizer st = null;
			String left = null;
			String right = null;
			String[] data = null;
			String code = null;
			String id = null;
			double zs = 0;
			double jk = 0;
			double xj = 0;
			double zg = 0;
			double zd = 0;
			double jj = 0;
			double zf = 0;
			double zfC = 0;
			StockTencent stockSina = null;
			
			try {
				for (String str : sparatorList) {
					if (str.length() > 30) {
						st = new StringTokenizer(str, "=");
						left = st.nextToken();
						right = st.nextToken();
						data = StringUtil.split(right,",");
						
						if(data == null || data.length < 32 || "0".equals(data[8]) || "0".equals(data[9])){//未开市或错误
							continue;
						}
						
						code = left.substring(left.length()-8);
						id = code.substring(2);
						
						BigDecimal doneAmount = new BigDecimal(data[9]);
						BigDecimal doneQuantity = new BigDecimal(data[8]);
						try {
							jk = new BigDecimal(data[1]).doubleValue();
							zs = new BigDecimal(data[2]).doubleValue();
							xj = new BigDecimal(data[3]).doubleValue();
							zg = new BigDecimal(data[4]).doubleValue();
							zd = new BigDecimal(data[5]).doubleValue();
							jj = doneAmount.divide(doneQuantity, 2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
							zf = (new BigDecimal(data[3]).subtract(new BigDecimal(data[2])))
								.divide(new BigDecimal(data[2]), 4,BigDecimal.ROUND_HALF_UP)
								.multiply(new BigDecimal(100)).doubleValue();
							zfC = (new BigDecimal(data[3]).subtract(new BigDecimal(jj)))
								.divide(new BigDecimal(data[2]), 4,BigDecimal.ROUND_HALF_UP)
								.multiply(new BigDecimal(100)).doubleValue();
						} catch (Exception e) {
							continue;
						}
						
						stockSina = StockCache.getStock(code);
						if (stockSina == null) {
							continue;
						}

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
						
						if (xj < jj) {//价格跌破均价
							stockSina.setAlwaysStrongFlag(-1);//价格一旦跌破均价，则不符合一直处于均价线上原则
							if(doneQuantity.intValue() != stockSina.getDoneQuantity()){//中途休市排除
								stockSina.setUpJjTimes(stockSina.getUpJjTimes() - 1);
							}
						} else {//价格处于均价线上
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
							stockSina.setJinBuy(Double.parseDouble(data[6]));
							stockSina.setJinSell(Double.parseDouble(data[7]));
							stockSina.setDoneQuantity(doneQuantity.intValue());
							stockSina.setDoneAmount(doneAmount.doubleValue());
							stockSina.setBuy1Quantity(Integer.parseInt(data[10]));
							stockSina.setBuy1Price(Double.parseDouble(data[11]));
							stockSina.setBuy2Quantity(Integer.parseInt(data[12]));
							stockSina.setBuy2Price(Double.parseDouble(data[13]));
							stockSina.setBuy3Quantity(Integer.parseInt(data[14]));
							stockSina.setBuy3Price(Double.parseDouble(data[15]));
							stockSina.setBuy4Quantity(Integer.parseInt(data[16]));
							stockSina.setBuy4Price(Double.parseDouble(data[17]));
							stockSina.setBuy5Quantity(Integer.parseInt(data[18]));
							stockSina.setBuy5Price(Double.parseDouble(data[19]));
							stockSina.setSell1Quantity(Integer.parseInt(data[20]));
							stockSina.setSell1Price(Double.parseDouble(data[21]));
							stockSina.setSell2Quantity(Integer.parseInt(data[22]));
							stockSina.setSell2Price(Double.parseDouble(data[23]));
							stockSina.setSell3Quantity(Integer.parseInt(data[24]));
							stockSina.setSell3Price(Double.parseDouble(data[25]));
							stockSina.setSell4Quantity(Integer.parseInt(data[26]));
							stockSina.setSell4Price(Double.parseDouble(data[27]));
							stockSina.setSell5Quantity(Integer.parseInt(data[28]));
							stockSina.setSell5Price(Double.parseDouble(data[29]));
							stockSina.setUpdateDate(data[30]);
							stockSina.setUpdateTime(data[31].substring(0,data[31].length()-1));
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
						} catch (Exception e) {
						}
						
						StockCache.putStock(code, stockSina);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(System.currentTimeMillis() - t);
		
		
		t = System.currentTimeMillis();
		for (int i = 0; i < 100; ++i) {
			String[] sparatorList = all.split(";");
			StringTokenizer st = null;
			String left = null;
			String right = null;
			String[] data = null;
			String code = null;
			String id = null;
			double zs = 0;
			double jk = 0;
			double xj = 0;
			double zg = 0;
			double zd = 0;
			double jj = 0;
			double zf = 0;
			double zfC = 0;
			StockTencent stockSina = null;
			
			try {
				for (String str : sparatorList) {
					if (str.length() > 30) {
						st = new StringTokenizer(str, "=");
						left = st.nextToken();
						right = st.nextToken();
						data = StringUtil.split(right,",");
						
						if(data == null || data.length < 32 || "0".equals(data[8]) || "0".equals(data[9])){//未开市或错误
							continue;
						}
						
						code = left.substring(left.length()-8);
						id = code.substring(2);
						
						BigDecimal doneAmount = new BigDecimal(data[9]);
						BigDecimal doneQuantity = new BigDecimal(data[8]);
						try {
							jk = new BigDecimal(data[1]).doubleValue();
							zs = new BigDecimal(data[2]).doubleValue();
							xj = new BigDecimal(data[3]).doubleValue();
							zg = new BigDecimal(data[4]).doubleValue();
							zd = new BigDecimal(data[5]).doubleValue();
							jj = doneAmount.divide(doneQuantity, 2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
							zf = (new BigDecimal(data[3]).subtract(new BigDecimal(data[2])))
								.divide(new BigDecimal(data[2]), 4,BigDecimal.ROUND_HALF_UP)
								.multiply(new BigDecimal(100)).doubleValue();
							zfC = (new BigDecimal(data[3]).subtract(new BigDecimal(jj)))
								.divide(new BigDecimal(data[2]), 4,BigDecimal.ROUND_HALF_UP)
								.multiply(new BigDecimal(100)).doubleValue();
						} catch (Exception e) {
							continue;
						}
						
						stockSina = StockCache.getStock(code);
						if (stockSina == null) {
							continue;
						}

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
						
						if (xj < jj) {//价格跌破均价
							stockSina.setAlwaysStrongFlag(-1);//价格一旦跌破均价，则不符合一直处于均价线上原则
							if(doneQuantity.intValue() != stockSina.getDoneQuantity()){//中途休市排除
								stockSina.setUpJjTimes(stockSina.getUpJjTimes() - 1);
							}
						} else {//价格处于均价线上
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
							stockSina.setJinBuy(Double.parseDouble(data[6]));
							stockSina.setJinSell(Double.parseDouble(data[7]));
							stockSina.setDoneQuantity(doneQuantity.intValue());
							stockSina.setDoneAmount(doneAmount.doubleValue());
							stockSina.setBuy1Quantity(Integer.parseInt(data[10]));
							stockSina.setBuy1Price(Double.parseDouble(data[11]));
							stockSina.setBuy2Quantity(Integer.parseInt(data[12]));
							stockSina.setBuy2Price(Double.parseDouble(data[13]));
							stockSina.setBuy3Quantity(Integer.parseInt(data[14]));
							stockSina.setBuy3Price(Double.parseDouble(data[15]));
							stockSina.setBuy4Quantity(Integer.parseInt(data[16]));
							stockSina.setBuy4Price(Double.parseDouble(data[17]));
							stockSina.setBuy5Quantity(Integer.parseInt(data[18]));
							stockSina.setBuy5Price(Double.parseDouble(data[19]));
							stockSina.setSell1Quantity(Integer.parseInt(data[20]));
							stockSina.setSell1Price(Double.parseDouble(data[21]));
							stockSina.setSell2Quantity(Integer.parseInt(data[22]));
							stockSina.setSell2Price(Double.parseDouble(data[23]));
							stockSina.setSell3Quantity(Integer.parseInt(data[24]));
							stockSina.setSell3Price(Double.parseDouble(data[25]));
							stockSina.setSell4Quantity(Integer.parseInt(data[26]));
							stockSina.setSell4Price(Double.parseDouble(data[27]));
							stockSina.setSell5Quantity(Integer.parseInt(data[28]));
							stockSina.setSell5Price(Double.parseDouble(data[29]));
							stockSina.setUpdateDate(data[30]);
							stockSina.setUpdateTime(data[31].substring(0,data[31].length()-1));
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
						} catch (Exception e) {
						}
						
						StockCache.putStock(code, stockSina);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(System.currentTimeMillis() - t);
		
		
		t = System.currentTimeMillis();
		for (int i = 0; i < 100; ++i) {
			List<String> sparatorList = StringUtil.cut(all,";");
			StringTokenizer st = null;
			String left = null;
			String right = null;
			List<String> data = null;
			String code = null;
			String id = null;
			double zs = 0;
			double jk = 0;
			double xj = 0;
			double zg = 0;
			double zd = 0;
			double jj = 0;
			double zf = 0;
			double zfC = 0;
			StockTencent stockSina = null;
			
			try {
				for (String str : sparatorList) {
					if (str.length() > 30) {
						st = new StringTokenizer(str, "=");
						left = st.nextToken();
						right = st.nextToken();
						data = StringUtil.cut(right,",");
						
						
						if(data == null || data.size() < 32 || "0".equals(data.get(8)) || "0".equals(data.get(9))){//未开市或错误
							continue;
						}
						
						code = left.substring(left.length()-8);
						id = code.substring(2);
						
						BigDecimal doneAmount = new BigDecimal(data.get(9));
						BigDecimal doneQuantity = new BigDecimal(data.get(8));
						try {
							jk = Double.parseDouble(data.get(1));
							zs = Double.parseDouble(data.get(2));
							xj = Double.parseDouble(data.get(3));
							zg = Double.parseDouble(data.get(4));
							zd = Double.parseDouble(data.get(5));
							jj = Double.parseDouble(data.get(9))/Double.parseDouble(data.get(8));
							zf = (new BigDecimal(data.get(3)).subtract(new BigDecimal(data.get(2))))
								.divide(new BigDecimal(data.get(2)), 4,BigDecimal.ROUND_HALF_UP)
								.multiply(new BigDecimal(100)).doubleValue();
							zfC = (new BigDecimal(data.get(3)).subtract(new BigDecimal(jj)))
								.divide(new BigDecimal(data.get(2)), 4,BigDecimal.ROUND_HALF_UP)
								.multiply(new BigDecimal(100)).doubleValue();
						} catch (Exception e) {
							continue;
						}
						
						stockSina = StockCache.getStock(code);
						if (stockSina == null) {
							continue;
						}

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
						
						if (xj < jj) {//价格跌破均价
							stockSina.setAlwaysStrongFlag(-1);//价格一旦跌破均价，则不符合一直处于均价线上原则
							if(doneQuantity.intValue() != stockSina.getDoneQuantity()){//中途休市排除
								stockSina.setUpJjTimes(stockSina.getUpJjTimes() - 1);
							}
						} else {//价格处于均价线上
							if(doneQuantity.intValue() != stockSina.getDoneQuantity()){//中途休市排除
								stockSina.setUpJjTimes(stockSina.getUpJjTimes() + 1);
							}
						}
						
						try {
							stockSina.setId(id);
							stockSina.setInfo(right);
							stockSina.setJj(jj);
							stockSina.setZf(zf);
							stockSina.setName(data.get(0).substring(1));
							stockSina.setOpenToday(jk);
							stockSina.setCloseYesterday(zs);
							stockSina.setCloseToday(xj);
							stockSina.setHighToday(zg);
							stockSina.setLowToday(zd);
							stockSina.setJinBuy(Double.parseDouble(data.get(6)));
							stockSina.setJinSell(Double.parseDouble(data.get(7)));
							stockSina.setDoneQuantity(doneQuantity.intValue());
							stockSina.setDoneAmount(doneAmount.doubleValue());
							stockSina.setBuy1Quantity(Integer.parseInt(data.get(10)));
							stockSina.setBuy1Price(Double.parseDouble(data.get(11)));
							stockSina.setBuy2Quantity(Integer.parseInt(data.get(12)));
							stockSina.setBuy2Price(Double.parseDouble(data.get(13)));
							stockSina.setBuy3Quantity(Integer.parseInt(data.get(14)));
							stockSina.setBuy3Price(Double.parseDouble(data.get(15)));
							stockSina.setBuy4Quantity(Integer.parseInt(data.get(16)));
							stockSina.setBuy4Price(Double.parseDouble(data.get(17)));
							stockSina.setBuy5Quantity(Integer.parseInt(data.get(18)));
							stockSina.setBuy5Price(Double.parseDouble(data.get(19)));
							stockSina.setSell1Quantity(Integer.parseInt(data.get(20)));
							stockSina.setSell1Price(Double.parseDouble(data.get(21)));
							stockSina.setSell2Quantity(Integer.parseInt(data.get(22)));
							stockSina.setSell2Price(Double.parseDouble(data.get(23)));
							stockSina.setSell3Quantity(Integer.parseInt(data.get(24)));
							stockSina.setSell3Price(Double.parseDouble(data.get(25)));
							stockSina.setSell4Quantity(Integer.parseInt(data.get(26)));
							stockSina.setSell4Price(Double.parseDouble(data.get(27)));
							stockSina.setSell5Quantity(Integer.parseInt(data.get(28)));
							stockSina.setSell5Price(Double.parseDouble(data.get(29)));
							stockSina.setUpdateDate(data.get(30));
							stockSina.setUpdateTime(data.get(31).substring(0,data.get(31).length()-1));
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
						} catch (Exception e) {
						}
						
						StockCache.putStock(code, stockSina);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(System.currentTimeMillis() - t);
	}

	private static void TestSplit() throws IOException {
		long t = System.currentTimeMillis();
		String all = FileUtil.file2String(new ClassPathResource("data.txt").getFile(),"utf8");
		System.out.println(System.currentTimeMillis() - t);
		
		t = System.currentTimeMillis();
		for (int i = 0; i < 100; ++i) {
		String[] sparatorList = StringUtil.split(all, ";");
		StringTokenizer st = null;
		String left = null;
		String right = null;
		String code = null;
		for(String str: sparatorList){
			if(str.length() > 30){
				st = new StringTokenizer(str, "=");
				left = st.nextToken();
				right = st.nextToken();
				code = left.substring(left.length()-8);
				StockCache.putStock(code, new StockTencent(code,right,"black",0));
			}
		}}
		System.out.println(System.currentTimeMillis() - t);
		
		
		t = System.currentTimeMillis();
		for (int i = 0; i < 100; ++i) {
		String[] sparatorList = all.split(";");
		StringTokenizer st = null;
		String left = null;
		String right = null;
		String code = null;
		for(String str: sparatorList){
			if(str.length() > 30){
				st = new StringTokenizer(str, "=");
				left = st.nextToken();
				right = st.nextToken();
				code = left.substring(left.length()-8);
				StockCache.putStock(code, new StockTencent(code,right,"black",0));
			}
		}}
		System.out.println(System.currentTimeMillis() - t);
		
		t = System.currentTimeMillis();
		for (int i = 0; i < 100; ++i) {
		List<String> sparatorLists = StringUtil.cut(all, ";");
		StringTokenizer st = null;
		String left = null;
		String right = null;
		String code = null;
		for(String str: sparatorLists){
			if(str.length() > 30){
				st = new StringTokenizer(str, "=");
				left = st.nextToken();
				right = st.nextToken();
				code = left.substring(left.length()-8);
				StockCache.putStock(code, new StockTencent(code,right,"black",0));
			}
		}}
		System.out.println(System.currentTimeMillis() - t);
		
		System.out.println("======================================");
	}

}
