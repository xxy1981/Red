package com.xxy.stock.web.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.xxy.stock.web.util.StockHelper;

/**
 * @author	<a href="mailto:jimmy.xu@cetelem.com.cn">JimmyXu</a>
 * @version	1.0
 * @Creationdate:Apr 9, 2009 8:58:29 AM
 * 开始初始化所有股票信息
 */
public class InitialStockJob implements Job {
	
	public void execute(JobExecutionContext jctx) throws JobExecutionException {
		
		try {
			StockHelper.doInitialAllStock();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}
