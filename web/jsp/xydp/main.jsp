<%@include file="/jsp/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<HTML >
	<HEAD>
		<title>Stock Server</title>
		<%@include file="/jsp/common/meta.jsp"%>
		<meta http-equiv="refresh" content="60;url=${ctxPath}/refresh.do?action=xydp">
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
				<c:if test="${king}">
				<form name="form" id="form" method="post" action="${ctxPath}/refresh.do?action=uploadSelect" enctype="multipart/form-data">
				<td align="right">
					<input type="file" name="select" size="30" style="height=18;" onkeydown="return false" onpaste="return false" contenteditable="false">
					<input type="submit" style="width:70px;height:18;" onClick="javascript:return submitForm();" value="上传自选股" class="icon_button">	
				</td>
				</form>
				<td align="right" width="5%">
					<input type="button" value="本地50" style="width:55px;height:18;" onclick="javascript:window.location='${ctxPath}/refresh.do?action=local50'">
				</td>
				<td align="right" width="5%">
					<input type="button" value="本地100" style="width:55px;height:18;" onclick="javascript:window.location='${ctxPath}/refresh.do?action=local100'">
				</td>
				<td align="right" width="5%">
					<input type="button" value="下载均价" style="width:55px;height:18;" onclick="javascript:window.location='${ctxPath}/refresh.do?action=downloadJj'">
				</td>
				<td align="right" width="5%">
					<input type="button" value="下载持强"  style="width:55px;height:18;" onclick="javascript:window.location='${ctxPath}/refresh.do?action=downloadCq'">
				</td>
				<td align="right" width="5%">
					<input type="button" value="下载扩展"  style="width:55px;height:18;" onclick="javascript:window.location='${ctxPath}/refresh.do?action=downloadExternal'">
				</td>
				<td align="right" width="5%">
					<input type="button" value="下载均差"  style="width:55px;height:18;" onclick="javascript:window.location='${ctxPath}/refresh.do?action=downloadExternalJc'">
				</td>
				</c:if>
				<td align="right" width="5%">
					<input type="button" value="刷新" style="width:55px;height:18;" onclick="javascript:window.location='${ctxPath}/refresh.do?action=xydp'">
				</td>
			</tr>
			<tr>
				<td align="left" colspan="10">
					<table width="100%" bgcolor="#765"  border="0" cellspacing="1" cellpadding="0">
						<tr align="center" style="background-color:#548B54;color:#fff;font-weight:bold">
							<td width="10%">
								代码
							</td>
							<td width="10%">
								名称
							</td>
							<td width="10%">
								板块          
							</td>
							<td width="5%">
								强度 
							</td>
							<td width="8%">
								涨幅 
							</td>
							<td width="8%">
								幅差
							</td>
							<td width="7%">
								量比
							</td>
							<td width="7%">
								市值
							</td>
							<td width="7%">
								开态
							</td>
							<td width="7%">
								缺口
							</td>
							<td width="6%">
								操作
							</td>
							<td width="10%">
								时间          
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
							<td align="center" style="color:#2F0000;">
								${stock.block}   
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
							<td align="center" style="color:red;">
								<fmt:formatNumber value="${stock.lb}" pattern="0.00" type="number"/>
							</td>
							<td style="color:#2F0000;">
								<fmt:formatNumber value="${stock.ltsz}" pattern="0.00" type="number"/>
							</td>
							<td align="center" style="color:${stock.openColor};">
								${stock.openStatus}
							</td>
							<td align="center" style="color:${stock.windowColor};">
								${stock.window}
							</td>
							<td align="center">
								<a href="#" onclick="javascript:window.location='${ctxPath}/refresh.do?action=add&code=${stock.code}'"><span style="color:#EE30A7;">关注</span></a> 
							</td>
							<td align="center" style="color:#2F0000;">
								${stock.updateDate}&nbsp;${stock.updateTime}     
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
