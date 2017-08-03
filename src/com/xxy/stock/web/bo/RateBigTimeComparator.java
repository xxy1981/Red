package com.xxy.stock.web.bo;

import java.util.Comparator;

public class RateBigTimeComparator implements Comparator<BuySellRate> {
	
	public int compare(BuySellRate s1, BuySellRate s2) {
		if (s2.getBigTime() > s1.getBigTime()) {
			return 1;
		} else if (s2.getBigTime() == s1.getBigTime()) {
			return 0;
		} else {
			return -1;
		}
	}
	
}
