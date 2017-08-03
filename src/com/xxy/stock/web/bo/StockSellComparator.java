package com.xxy.stock.web.bo;

import java.util.Comparator;

public class StockSellComparator implements Comparator<StockTencent> {
	
	public int compare(StockTencent s1, StockTencent s2) {
		if (s2.getSellSumQuantity() > s1.getSellSumQuantity()) {
			return 1;
		} else if (s2.getSellSumQuantity() == s1.getSellSumQuantity()) {
			return 0;
		} else {
			return -1;
		}
	}
	
}
