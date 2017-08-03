<%@include file="/jsp/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<HTML >
	<HEAD>
		<title>Stock Server</title>
		<%@include file="/jsp/common/meta.jsp"%>
	</HEAD>
	
	<BODY leftmargin="0" topmargin="0" >
		<table width="100%" height="30%" valign="middle">
			<tr valign="middle">
				<td align="center" valign="middle">
					<font size="+3" color="#137A4F">${myAccountId},欢迎使用星宇股票行情系统</font>
				</td>
			</tr>
		</table>
		<c:if test="${king}">
		<table width="60%" height="30%" valign="middle" align="center">
			<tr valign="middle">
				<td align="center" valign="middle">
					<fieldset>
        				<legend class="legendnormal">后台管理</legend>
						<table width="100%" height="20%" valign="middle" align="center">
							<form name="form" id="form" method="post" action="${ctxPath}/refresh.do?action=uploadBlock" enctype="multipart/form-data">
							<tr valign="middle">
								<td align="center" valign="middle">
									<input type="file" name="block" size="60" style="height=18;" onkeydown="return false" onpaste="return false" contenteditable="false">
									<input type="submit" style="height:18;background-color:#137A4F;color:#ffffff;" onClick="javascript:return submitForm();" value="上传板块" class="icon_button">
								</td>
							</tr>
							</form>
							<tr valign="middle">
								<td align="center" valign="middle">
									<input type="button" style="height:18;width:112;margin:10;background-color:#137A4F;color:#ffffff;" onClick="javascript:window.location='${ctxPath}/refresh.do?action=downloadBlock'" value="下载板块" class="icon_button">
									<input type="button" style="height:18;width:111;margin:10;background-color:#137A4F;color:#ffffff;" onClick="javascript:window.location='${ctxPath}/refresh.do?action=initial'" value="初始化数据" class="icon_button">
									<input type="button" style="height:18;width:111;margin:10;background-color:#137A4F;color:#ffffff;" onClick="javascript:window.location='${ctxPath}/refresh.do?action=refresh'" value="刷新数据" class="icon_button">
									<input type="button" style="height:18;width:112;margin:10;background-color:#137A4F;color:#ffffff;" onClick="javascript:window.location='${ctxPath}/refresh.do?action=initialBlock'" value="刷新板块" class="icon_button">
									<input type="button" style="height:18;width:112;margin:10;background-color:#137A4F;color:#ffffff;" onClick="javascript:window.location='${ctxPath}/refresh.do?action=initialCapital'" value="刷新资金" class="icon_button">
									<input type="button" style="height:18;width:112;margin:10;background-color:#137A4F;color:#ffffff;" onClick="javascript:window.location='${ctxPath}/refresh.do?action=refreshRate'" value="刷新比例" class="icon_button">
								</td>
							</tr>
						</table>
					</fieldset>
				</td>
			</tr>
		</table>
		</c:if>
	</BODY>
</HTML>
