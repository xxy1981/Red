package com.xxy.stock.web.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.xxy.stock.web.constants.StockWebsiteConstants;
import com.xxy.stock.web.util.StockHelper;

/**
 * @author	<a href="mailto:jimmy.xu@cetelem.com.cn">JimmyXu</a>
 * @version	1.0
 * @Creationdate:Apr 2, 2009 5:24:48 PM
 * 盘中每2分钟刷新本地通达信目录自选股板块文件
 */

public class RefreshZxgFileJob implements Job, StockWebsiteConstants {

	public void execute(JobExecutionContext jctx) throws JobExecutionException {
		
		StockHelper.doLocalWriteZxgFile(LOCAL_ZXG_NUMBER);
		
	}
	
}
