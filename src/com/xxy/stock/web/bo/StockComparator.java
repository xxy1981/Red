package com.xxy.stock.web.bo;

import java.util.Comparator;

/**
 * @author <a href="mailto:jimmy.xu@cetelem.com.cn">JimmyXu</a>
 * @version 1.0
 * @Creationdate:Apr 3, 2009 3:37:20 PM
 */

public class StockComparator implements Comparator<StockTencent> {

	public int compare(StockTencent s1, StockTencent s2) {
		if (s2.getZf() > s1.getZf()) {
			return 1;
		} else if (s2.getZf() == s1.getZf()) {
			return 0;
		} else {
			return -1;
		}
	}

}
