package com.xxy.stock.web.bo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StockCache {
	
	public static Map<String, StockTencent> stockMap = new ConcurrentHashMap<String, StockTencent>();
	public static Map<String, StockTencent> stockMap0930 = new ConcurrentHashMap<String, StockTencent>();
	public static Map<String, StockTencent> stockMap0945 = new ConcurrentHashMap<String, StockTencent>();
	public static Map<String, StockTencent> stockMap1000 = new ConcurrentHashMap<String, StockTencent>();
	public static Map<String, StockTencent> stockMap1030 = new ConcurrentHashMap<String, StockTencent>();
	public static Map<String, StockTencent> stockMap1100 = new ConcurrentHashMap<String, StockTencent>();
	public static Map<String, StockTencent> stockMap1130 = new ConcurrentHashMap<String, StockTencent>();
	
	public static Map<String, BuySellRate> rateMap = new ConcurrentHashMap<String, BuySellRate>();
	public static Map<String, String> blockMap = new ConcurrentHashMap<String, String>();
	public static Map<String, String> selectMap = new ConcurrentHashMap<String, String>();

	public static Map<String, StockTencent> getStockMap() {
		return stockMap;
	}
	public static Map<String, StockTencent> getStockMap0930() {
		return stockMap0930;
	}
	public static Map<String, StockTencent> getStockMap0945() {
		return stockMap0945;
	}
	public static Map<String, StockTencent> getStockMap1000() {
		return stockMap1000;
	}
	public static Map<String, StockTencent> getStockMap1030() {
		return stockMap1030;
	}
	public static Map<String, StockTencent> getStockMap1100() {
		return stockMap1100;
	}
	public static Map<String, StockTencent> getStockMap1130() {
		return stockMap1130;
	}
	public static void setStockMap(Map<String, StockTencent> stockMap) {
		StockCache.stockMap = stockMap;
	}
	public static void setStockMap0930(Map<String, StockTencent> stockMap) {
		StockCache.stockMap0930 = stockMap;
	}
	public static void setStockMap0945(Map<String, StockTencent> stockMap) {
		StockCache.stockMap0945 = stockMap;
	}
	public static void setStockMap1000(Map<String, StockTencent> stockMap) {
		StockCache.stockMap1000 = stockMap;
	}
	public static void setStockMap1030(Map<String, StockTencent> stockMap) {
		StockCache.stockMap1030 = stockMap;
	}
	public static void setStockMap1100(Map<String, StockTencent> stockMap) {
		StockCache.stockMap1100 = stockMap;
	}
	public static void setStockMap1130(Map<String, StockTencent> stockMap) {
		StockCache.stockMap1130 = stockMap;
	}
	public static void putStock(String stockCode, StockTencent stock) {
		StockCache.stockMap.put(stockCode, stock);
	}
	public static void putStock0930(String stockCode, StockTencent stock) {
		StockCache.stockMap0930.put(stockCode, stock);
	}
	public static void putStock0945(String stockCode, StockTencent stock) {
		StockCache.stockMap0945.put(stockCode, stock);
	}
	public static void putStock1000(String stockCode, StockTencent stock) {
		StockCache.stockMap1000.put(stockCode, stock);
	}
	public static void putStock1030(String stockCode, StockTencent stock) {
		StockCache.stockMap1030.put(stockCode, stock);
	}
	public static void putStock1100(String stockCode, StockTencent stock) {
		StockCache.stockMap1100.put(stockCode, stock);
	}
	public static void putStock1130(String stockCode, StockTencent stock) {
		StockCache.stockMap1130.put(stockCode, stock);
	}
	public static void removeStock(String stockCode) {
		StockCache.stockMap.remove(stockCode);
	}
	public static void removeStock0930(String stockCode) {
		StockCache.stockMap0930.remove(stockCode);
	}
	public static void removeStock0945(String stockCode) {
		StockCache.stockMap0945.remove(stockCode);
	}
	public static void removeStock1000(String stockCode) {
		StockCache.stockMap1000.remove(stockCode);
	}
	public static void removeStock1030(String stockCode) {
		StockCache.stockMap1030.remove(stockCode);
	}
	public static void removeStock1100(String stockCode) {
		StockCache.stockMap1100.remove(stockCode);
	}
	public static void removeStock1130(String stockCode) {
		StockCache.stockMap1130.remove(stockCode);
	}
	public static StockTencent getStock(String stockCode) {
		return StockCache.stockMap.get(stockCode);
	}
	public static StockTencent getStock0930(String stockCode) {
		return StockCache.stockMap0930.get(stockCode);
	}
	public static StockTencent getStock0945(String stockCode) {
		return StockCache.stockMap0945.get(stockCode);
	}
	public static StockTencent getStock1000(String stockCode) {
		return StockCache.stockMap1000.get(stockCode);
	}
	public static StockTencent getStock1030(String stockCode) {
		return StockCache.stockMap1030.get(stockCode);
	}
	public static StockTencent getStock1100(String stockCode) {
		return StockCache.stockMap1100.get(stockCode);
	}
	public static StockTencent getStock1130(String stockCode) {
		return StockCache.stockMap1130.get(stockCode);
	}
	public static void clearStockMap() {
		StockCache.stockMap.clear();
		StockCache.stockMap0930.clear();
		StockCache.stockMap0945.clear();
		StockCache.stockMap1000.clear();
		StockCache.stockMap1030.clear();
		StockCache.stockMap1100.clear();
		StockCache.stockMap1130.clear();
	}
	
	
	public static Map<String, BuySellRate> getRateMap() {
		return rateMap;
	}
	public static void setRatekMap(Map<String, BuySellRate> rateMap) {
		StockCache.rateMap = rateMap;
	}
	public static void putRate(String stockCode, BuySellRate rate) {
		StockCache.rateMap.put(stockCode, rate);
	}
	public static void removeRate(String stockCode) {
		StockCache.rateMap.remove(stockCode);
	}
	public static BuySellRate getRate(String stockCode) {
		return StockCache.rateMap.get(stockCode);
	}
	public static void clearRateMap() {
		StockCache.rateMap.clear();
	}
	
	
	public static Map<String, String> getBlockMap() {
		return blockMap;
	}
	public static void setBlockMap(Map<String, String> blockMap) {
		StockCache.blockMap = blockMap;
	}
	public static void putBlock(String stockCode, String block) {
		StockCache.blockMap.put(stockCode, block);
	}
	public static void removeBlock(String stockCode) {
		StockCache.blockMap.remove(stockCode);
	}
	public static String getBlock(String stockCode) {
		return StockCache.blockMap.get(stockCode);
	}
	public static void clearBlockMap() {
		StockCache.blockMap.clear();
	}
	
	
	public static Map<String, String> getSelectMap() {
		return selectMap;
	}
	public static void setSelectMap(Map<String, String> selectMap) {
		StockCache.selectMap = selectMap;
	}
	public static void putSelect(String stockCode, String select) {
		StockCache.selectMap.put(stockCode, select);
	}
	public static void removeSelect(String stockCode) {
		StockCache.selectMap.remove(stockCode);
	}
	public static String getSelect(String stockCode) {
		return StockCache.selectMap.get(stockCode);
	}
	public static void clearSelectMap() {
		StockCache.selectMap.clear();
	}
	
}
