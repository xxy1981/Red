<%@include file="/jsp/common/taglibs.jsp"%>
<%@include file="/jsp/common/login_check.jsp" %>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>

<html>
	<head>
		<title>Stock Server</title>
		<%@include file="/jsp/common/meta.jsp"%>
	</head>

	<frameset rows="51,*" cols="*" frameborder="YES" border="1" framespacing="0" bordercolor="#765">
		<frame src="${ctxPath}/jsp/mmgd/top.jsp" name="topFrame" scrolling="NO"
			noresize>
		<frameset cols="150,*" border="1" frameborder="YES" framespacing="1" bordercolor="#765">
			<frame src="${ctxPath}/jsp/mmgd/left.jsp" name="leftFrame">
			<frame src="${ctxPath}/refresh.do?action=mmgd" name="mainFrame">
		</frameset>
		<noframes>
			<body>
			</body>
		</noframes>
	</frameset>
</html>
