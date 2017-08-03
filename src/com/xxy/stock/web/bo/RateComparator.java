package com.xxy.stock.web.bo;

import java.util.Comparator;

public class RateComparator implements Comparator<BuySellRate> {
	
	public int compare(BuySellRate s1, BuySellRate s2) {
		if (s2.getBuyRate() > s1.getBuyRate()) {
			return 1;
		} else if (s2.getBuyRate() == s1.getBuyRate()) {
			return 0;
		} else {
			return -1;
		}
	}
	
}
