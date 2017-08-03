package com.xxy.stock.web.bo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StockCache {
	
	public static Map<String, StockTencent> stockMap = new ConcurrentHashMap<String, StockTencent>();
	public static Map<String, BuySellRate> rateMap = new ConcurrentHashMap<String, BuySellRate>();
	public static Map<String, String> blockMap = new ConcurrentHashMap<String, String>();
	public static Map<String, String> selectMap = new ConcurrentHashMap<String, String>();

	public static Map<String, StockTencent> getStockMap() {
		return stockMap;
	}
	public static void setStockMap(Map<String, StockTencent> stockMap) {
		StockCache.stockMap = stockMap;
	}
	public static void putStock(String stockCode, StockTencent stock) {
		StockCache.stockMap.put(stockCode, stock);
	}
	public static void removeStock(String stockCode) {
		StockCache.stockMap.remove(stockCode);
	}
	public static StockTencent getStock(String stockCode) {
		return StockCache.stockMap.get(stockCode);
	}
	public static void clearStockMap() {
		StockCache.stockMap.clear();
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
