package com.xxy.stock.web.bo;

import java.util.Comparator;

public class RateBigComparator implements Comparator<BuySellRate> {
	
	public int compare(BuySellRate s1, BuySellRate s2) {
		if (s2.getBuyBigRate() > s1.getBuyBigRate()) {
			return 1;
		} else if (s2.getBuyBigRate() == s1.getBuyBigRate()) {
			return 0;
		} else {
			return -1;
		}
	}
	
}
