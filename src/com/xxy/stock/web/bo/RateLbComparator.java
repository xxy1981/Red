package com.xxy.stock.web.bo;

import java.util.Comparator;

public class RateLbComparator implements Comparator<BuySellRate> {
	
	public int compare(BuySellRate s1, BuySellRate s2) {
		if (s2.getLb() > s1.getLb()) {
			return 1;
		} else if (s2.getLb() == s1.getLb()) {
			return 0;
		} else {
			return -1;
		}
	}
	
}
