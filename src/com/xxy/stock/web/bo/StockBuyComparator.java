package com.xxy.stock.web.bo;

import java.util.Comparator;

public class StockBuyComparator implements Comparator<StockTencent> {
	
	public int compare(StockTencent s1, StockTencent s2) {
		if (s2.getBuySumQuantity() > s1.getBuySumQuantity()) {
			return 1;
		} else if (s2.getBuySumQuantity() == s1.getBuySumQuantity()) {
			return 0;
		} else {
			return -1;
		}
	}
	
}
