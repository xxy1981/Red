package com.xxy.stock.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import com.xxy.stock.web.bo.StockCache;
import com.xxy.stock.web.util.StockHelper;

public class BlockListener implements ServletContextListener {

	public void contextInitialized(ServletContextEvent e) {
		
		StockHelper.doInitialAllBlock();
		
		e.getServletContext().setAttribute("blockMaps", StockCache.getBlockMap());
	}

	public void contextDestroyed(ServletContextEvent e) {
		StockCache.clearBlockMap();
	}
	
}
