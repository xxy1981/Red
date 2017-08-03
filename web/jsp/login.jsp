<%@include file="/jsp/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<HTML >
	<HEAD>
		<title>Stock Server</title>
		<%@include file="/jsp/common/meta.jsp"%>
		<script language="JavaScript">
		<!--
		function submitForm()
		    {
		        document.form.submit();
			}
		
		function onReady(eventObject)
		{
		    if (!eventObject) {
		        eventObject = window.event;
		    }
		    document.getElementById("name").attachEvent("onkeyup", handleOnKeyUp);
		    document.getElementById("password").attachEvent("onkeyup", handleOnKeyUp);
		}
		
		function handleOnKeyUp(eventObject)
		{
		    if ( ! eventObject ) {
		        eventObject = window.event;
		    }
		    var keyCode = eventObject.keyCode ? eventObject.keyCode : eventObject.which ? eventObject.which : eventObject.charCode;
		    if (keyCode == 13) {
		    	submitForm();
		    }
		}
		//-->
		</script>
	</HEAD>
	
	<BODY leftmargin="0" topmargin="0" cellspacing="0" cellpadding="0" onload="onReady()">
	<form name="form" id="form" method="post" action="${ctxPath}/login.do?action=login">
	<table width="100%" align="center" valign="middle" height="80%">
			<tr valign="middle">
				<td align="center" height="25" valign="middle">
					<table width="100%" bgcolor="#765"  border="0" cellspacing="1" cellpadding="0">
						<tr align="center" style="background-color:#548B54;color:#fff;font-weight:bold" valign="middle">
							<td width="100%" height="25" valign="middle">
								用户名：<input type="text" name="name" size="20" style="height=18;" value="" />
								&nbsp;&nbsp;密码：<input type="password" name="password" size="20" style="height=18;" value=""/>
								&nbsp;&nbsp;<input type="button" value="登录" style="width:55px;height=18;" onClick="submitForm();">
								<br><html:errors property="error"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		</form>
	</BODY>
</HTML>
