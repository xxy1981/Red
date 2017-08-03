<%@include file="/jsp/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<HTML >
	<HEAD>
		<title>Stock Server</title>
		<%@include file="/jsp/common/meta.jsp"%>
		<meta http-equiv="refresh" content="60;url=${ctxPath}/refresh.do?action=all">
	</HEAD>
	
	<BODY leftmargin="0" topmargin="0" >
	<table width="100%">
			<tr>
				<td align="left">
					总记录数：${fn:length(stockList)}
				</td>
				<td align="right">
					<input type="button" value="刷新" style="width: 55px;height:18;" onclick="javascript:window.location='${ctxPath}/refresh.do?action=all'">
				</td>
			</tr>
			<tr>
				<td align="left" colspan="2">
					<table width="100%" bgcolor="#765"  border="0" cellspacing="1" cellpadding="0">
						<tr align="center" style="background-color:#548B54;color:#fff;font-weight:bold">
							<td width="7%">
								代码
							</td>
							<td width="7%">
								名称
							</td>
							<td width="7%">
								板块          
							</td>
							<td width="4%">
								强度 
							</td>
							<td width="7%">
								涨幅 
							</td>
							<td width="5%">
								开态
							</td>
							<td width="5%">
								缺口
							</td>
							<td width="7%">
								昨收
							</td>
							<td width="7%">
								现价
							</td>
							<td width="7%">
								均价
							</td>
							<td width="7%">
								最高价
							</td>
							<td width="7%">
								最低价    
							</td>
							<td width="7%">
								时间          
							</td>
							<td width="4%">
								刷 
							</td>
							<td width="12%" colspan="4">
								查看          
							</td>
						</tr>
						<c:forEach items="${stockList}" var="stock" > 
						<tr align="right" style="background-color:#DEFFAC;color:#137A4F;" onmouseover="this.style.backgroundColor='#FFD306'" onmouseout="this.style.backgroundColor='#DEFFAC'">
							<td align="center" style="color:#28004D;font-weight:bold">
								${stock.id}
							</td>
							<td align="center" style="color:#006400;">
								${stock.name}
							</td>
							<td align="center" style="color:#2F0000;">
								${stock.block}   
							</td>
							<td align="center" style="color:${stock.zfColor};">
								${stock.upJjTimes}     
							</td>
							<td style="color:${stock.zfColor};">
								<b>${stock.zf}</b>%         
							</td>
							<td align="center" style="color:${stock.openColor};">
								${stock.openStatus}
							</td>
							<td align="center" style="color:${stock.windowColor};">
								${stock.window}
							</td>
							<td style="color:#2F0000;">
								${stock.closeYesterday}
							</td>
							<td style="color:${stock.zfColor};">
								${stock.closeToday}
							</td>
							<td style="color:#2F0000;">
								${stock.jj}
							</td>
							<td style="color:#2F0000;">
								${stock.highToday}
							</td>
							<td style="color:#2F0000;">
								${stock.lowToday}
							</td>
							<td align="center" style="color:#2F0000;">
								${stock.updateDate}&nbsp;${stock.updateTime}     
							</td>
							<td style="color:#2F0000;">
								${stock.updateSum}
							</td>
							<td align="center" width="3%">
								<a target="_blank" href="${ctxPath}/jsp/picture.jsp?url=http://image.sinajs.cn/newchart/min/n/${stock.code}.gif"><span style="color:#EE30A7;">实</span></a>     
							</td>
							<td align="center" width="3%">
								<a target="_blank" href="${ctxPath}/jsp/picture.jsp?url=http://image.sinajs.cn/newchart/daily/n/${stock.code}.gif"><span style="color:#EE30A7;">日</span></a>    
							</td>
							<td align="center" width="3%">
								<a target="_blank" href="${ctxPath}/jsp/picture.jsp?url=http://image.sinajs.cn/newchart/weekly/n/${stock.code}.gif"><span style="color:#EE30A7;">周</span></a>     
							</td>
							<td align="center" width="3%">
								<a target="_blank" href="${ctxPath}/jsp/picture.jsp?url=http://image.sinajs.cn/newchart/monthly/n/${stock.code}.gif"><span style="color:#EE30A7;">月</span></a>     
							</td>
						</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</table>
			
	</BODY>
</HTML>
