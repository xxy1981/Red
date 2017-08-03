<%@include file="/jsp/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<HTML >
	<HEAD>
		<title>Stock Server</title>
		<%@include file="/jsp/common/meta.jsp"%>
		<meta http-equiv="refresh" content="180;url=${ctxPath}/refresh.do?action=rate">
		<script language="javascript" type="text/javascript"> 
			function hidden_mp() 
			{ 
				real = document.getElementById('realDiv');
				real.innerHTML=""
				real.style.display="none"; 
			}
			function show_mp(stockCode){
				real = document.getElementById('realDiv');
				real.style.display = "block";
				real.style.left=document.body.scrollLeft+event.clientX+10
				real.style.top=document.body.scrollTop+event.clientY+10
				real.innerHTML="<img src='http://image.sinajs.cn/newchart/min/n/"+stockCode+".gif'></img>"
			}
	    </script>
	</HEAD>
	
	<BODY leftmargin="0" topmargin="0" >
	<div style="width:545px;height:300px;background:#ffffff;display:none;position:absolute;z-index:5;" id="realDiv"></div>
	<table width="100%">
			<tr>
				<td align="left">
					总记录数：${fn:length(stockList)}
				</td>
				<td align="right" width="5%">
					<input type="button" value="大买↓" style="width:55px;height:18;" onclick="javascript:window.location='${ctxPath}/refresh.do?action=rate&type=big'">
				</td>
				<td align="right" width="5%">
					<input type="button" value="大倍↓" style="width:55px;height:18;" onclick="javascript:window.location='${ctxPath}/refresh.do?action=rate&type=time'">
				</td>
				<td align="right" width="5%">
					<input type="button" value="量比↓" style="width:55px;height:18;" onclick="javascript:window.location='${ctxPath}/refresh.do?action=rate&type=lb'">
				</td>
				<td align="right" width="5%">
					<input type="button" value="刷新" style="width:55px;height:18;" onclick="javascript:window.location='${ctxPath}/refresh.do?action=rate'">
				</td>
			</tr>
			<tr>
				<td align="left" colspan="5">
					<table width="100%" bgcolor="#765"  border="0" cellspacing="1" cellpadding="0">
						<tr align="center" style="background-color:#548B54;color:#fff;font-weight:bold">
							<td width="10%">
								代码
							</td>
							<td width="8%">
								名称
							</td>
							<td width="5%">
								强度 
							</td>
							<td width="5%">
								涨幅 
							</td>
							<td width="5%">
								幅差
							</td>
							<td width="5%">
								量比          
							</td>
							<td width="6%">
								市值          
							</td>
							<td width="5%">
								买比 
							</td>
							<td width="5%">
								大买 
							</td>
							<td width="5%">
								大卖          
							</td>
							<td width="5%">
								大倍          
							</td>
							<td width="5%">
								小买
							</td>
							<td width="5%">
								小卖          
							</td>
							<td width="5%">
								卖比          
							</td>
							<td width="5%">
								刷 
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
							<td align="center" style="color:${stock.zfColor};" onmouseover="show_mp('${stock.code}');" onmouseout="hidden_mp();">
								${stock.upJjTimes}
							</td>
							<td style="color:${stock.zfColor};">
								<b><fmt:formatNumber value="${stock.zf}" pattern="0.00" type="number"/></b>%
							</td>
							<td style="color:${stock.zfCColor};">
								<b><fmt:formatNumber value="${stock.zfC}" pattern="0.00" type="number"/></b>%  
							</td>
							<td align="center">
								<fmt:formatNumber value="${stock.lb}" pattern="0.00" type="number"/>
							</td>
							<td style="color:#2F0000;">
								<fmt:formatNumber value="${stock.ltsz}" pattern="0.00" type="number"/>      
							</td>
							<td align="center" style="color:${stock.rateColor};">
								<fmt:formatNumber value="${stock.buyRate}" pattern="0.00" type="number"/>% 
							</td>
							<td style="color:#2F0000;">
								<fmt:formatNumber value="${stock.buyBigRate}" pattern="0.00" type="number"/>%   
							</td>
							<td style="color:#2F0000;">
								<fmt:formatNumber value="${stock.sellBigRate}" pattern="0.00" type="number"/>%
							</td>
							<td style="color:red;">
								<fmt:formatNumber value="${stock.bigTime}" pattern="0.00" type="number"/>%
							</td>
							<td style="color:#2F0000;">
								<fmt:formatNumber value="${stock.buySamllRate}" pattern="0.00" type="number"/>%
							</td>
							<td style="color:#2F0000;">
								<fmt:formatNumber value="${stock.sellSamllRate}" pattern="0.00" type="number"/>%
							</td>
							<td align="center" style="color:#2F0000;">
								<fmt:formatNumber value="${stock.sellRate}" pattern="0.00" type="number"/>%
							</td>
							<td style="color:#2F0000;">
								${stock.updateSum}
							</td>
						</tr>
						</c:forEach>
					</table>
				</td>
			</tr>
		</table>
			
	</BODY>
</HTML>
