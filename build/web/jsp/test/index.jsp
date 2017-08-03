<%@include file="/jsp/common/taglibs.jsp"%>
<%@include file="/jsp/common/login_check.jsp" %>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>

<html>
	<head>
		<title>Stock Server</title>
		<%@include file="/jsp/common/meta.jsp"%>
		<!-- script type="text/javascript" src="http://xxy/cordys/wcp/applicationtest.js"></script-->
	</head>

	<frameset rows="51,*" cols="*" frameborder="YES" border="1" framespacing="0" bordercolor="#765">
		<frame src="${ctxPath}/jsp/test/top.jsp" name="topFrame" scrolling="NO"
			noresize>
		<frameset cols="150,*" border="1" frameborder="YES" framespacing="1" bordercolor="#765">
			<frame src="${ctxPath}/jsp/test/left.jsp" name="leftFrame">
			<frame src="${ctxPath}/jsp/test/main.jsp" name="mainFrame" id="mainFrame">
		</frameset>
		<noframes>
			<body>
			</body>
		</noframes>
	</frameset>
</html>
