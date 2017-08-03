package com.xxy.stock.web.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.xxy.stock.web.util.StockHelper;

/**
 * @author	<a href="mailto:jimmy.xu@cetelem.com.cn">JimmyXu</a>
 * @version	1.0
 * @Creationdate:Apr 2, 2009 5:24:48 PM
 * 盘中每分钟刷新和统计所有股票信息
 */

public class RefreshStockJob implements Job {

	public void execute(JobExecutionContext jctx) throws JobExecutionException {
		
		StockHelper.doRefreshAllStock();
		
	}
	
}
