package com.xxy.stock.web.bo;

import java.util.Comparator;

public class StockStrongComparator implements Comparator<StockTencent> {
	
	public int compare(StockTencent s1, StockTencent s2) {
		if (s2.getUpJjTimes() > s1.getUpJjTimes()) {
			return 1;
		} else if (s2.getUpJjTimes() == s1.getUpJjTimes()) {
			return 0;
		} else {
			return -1;
		}
	}
	
}
