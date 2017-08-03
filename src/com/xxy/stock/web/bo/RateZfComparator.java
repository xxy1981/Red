package com.xxy.stock.web.bo;

import java.util.Comparator;

public class RateZfComparator implements Comparator<BuySellRate> {
	
	public int compare(BuySellRate s1, BuySellRate s2) {
		if (s2.getZf() > s1.getZf()) {
			return 1;
		} else if (s2.getZf() == s1.getZf()) {
			return 0;
		} else {
			return -1;
		}
	}
	
}
