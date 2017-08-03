<%@include file="/jsp/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<HTML >
	<HEAD>
		<title>Stock Server</title>
		<%@include file="/jsp/common/meta.jsp"%>
		<meta http-equiv="refresh" content="60">
	</HEAD>
	
	<BODY leftmargin="0" topmargin="0" >
	<table width="100%">
			<tr valign="bottom">
				<td align="left">
					<font color="red"><b>上证指数</b></font>
				</td>
				<td align="right">
					<input type="button" value="刷新" style="width: 55px;height:18;" onclick="window.location.reload();">
				</td>
			</tr>
			<tr>
				<td align="left" colspan="2">
					<table width="100%" bgcolor="#765"  border="0" cellspacing="1" cellpadding="0">
						<tr align="center" style="background-color:#548B54;color:#fff;font-weight:bold">
							<td width="50%" align="center">
								实时
							</td>
							<td width="50%" align="center">
								日线
							</td>
						</tr>
						<tr align="center" style="background-color:#DEFFAC;color:#137A4F;">
							<td align="center" style="color:#28004D;" onmouseover="this.style.backgroundColor='#FFFFFF'" onmouseout="this.style.backgroundColor='#DEFFAC'">
								<IMG src="http://image.sinajs.cn/newchart/min/n/sh000001.gif" border=0>
							</td>
							<td align="center" style="color:#548B54;" onmouseover="this.style.backgroundColor='#FFFFFF'" onmouseout="this.style.backgroundColor='#DEFFAC'">
								<IMG src="http://image.sinajs.cn/newchart/daily/n/sh000001.gif" border=0>
							</td>
						</tr>
						<tr align="center" style="background-color:#548B54;color:#fff;font-weight:bold">
							<td width="50%" align="center">
								周线
							</td>
							<td width="50%" align="center">
								月线
							</td>
						</tr>
						<tr align="center" style="background-color:#DEFFAC;color:#137A4F;">
							<td align="center" style="color:#28004D;" onmouseover="this.style.backgroundColor='#FFFFFF'" onmouseout="this.style.backgroundColor='#DEFFAC'">
								<IMG src="http://image.sinajs.cn/newchart/weekly/n/sh000001.gif" border=0>
							</td>
							<td align="center" style="color:#548B54;" onmouseover="this.style.backgroundColor='#FFFFFF'" onmouseout="this.style.backgroundColor='#DEFFAC'">
								<IMG src="http://image.sinajs.cn/newchart/monthly/n/sh000001.gif" border=0>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<br>
		<table width="100%">
			<tr valign="bottom">
				<td align="left">
					<font color="red"><b>深证成指</b></font>
				</td>
				<td align="right">
					<input type="button" value="刷新" style="width: 55px;" onclick="window.location.reload();">
				</td>
			</tr>
			<tr>
				<td align="left" colspan="2">
					<table width="100%" bgcolor="#765"  border="0" cellspacing="1" cellpadding="0">
						<tr align="center" style="background-color:#548B54;color:#fff;font-weight:bold">
							<td width="50%" align="center">
								实时
							</td>
							<td width="50%" align="center">
								日线
							</td>
						</tr>
						<tr align="center" style="background-color:#DEFFAC;color:#137A4F;">
							<td align="center" style="color:#28004D;" onmouseover="this.style.backgroundColor='#FFFFFF'" onmouseout="this.style.backgroundColor='#DEFFAC'">
								<IMG src="http://image.sinajs.cn/newchart/min/n/sz399001.gif" border=0>
							</td>
							<td align="center" style="color:#548B54;" onmouseover="this.style.backgroundColor='#FFFFFF'" onmouseout="this.style.backgroundColor='#DEFFAC'">
								<IMG src="http://image.sinajs.cn/newchart/daily/n/sz399001.gif" border=0>
							</td>
						</tr>
						<tr align="center" style="background-color:#548B54;color:#fff;font-weight:bold">
							<td width="50%" align="center">
								周线
							</td>
							<td width="50%" align="center">
								月线
							</td>
						</tr>
						<tr align="center" style="background-color:#DEFFAC;color:#137A4F;">
							<td align="center" style="color:#28004D;" onmouseover="this.style.backgroundColor='#FFFFFF'" onmouseout="this.style.backgroundColor='#DEFFAC'">
								<IMG src="http://image.sinajs.cn/newchart/weekly/n/sz399001.gif" border=0>
							</td>
							<td align="center" style="color:#548B54;" onmouseover="this.style.backgroundColor='#FFFFFF'" onmouseout="this.style.backgroundColor='#DEFFAC'">
								<IMG src="http://image.sinajs.cn/newchart/monthly/n/sz399001.gif" border=0>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>	
	</BODY>
</HTML>
