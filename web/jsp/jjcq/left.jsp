<%@include file="/jsp/common/taglibs.jsp"%>
<%@include file="/jsp/common/login_check.jsp" %>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>

<html>
<head>
	<title>Stock Server</title>
	<%@include file="/jsp/common/meta.jsp"%>
</head>
<body>

	<ul class="menuList">
		<li class="menubar">
			<a href="${ctxPath}/jsp/index/index.jsp" title="大盘指数" class="standalone" target="_top">大盘指数</a>
		</li>
		<li class="menubar">
			<a href="${ctxPath}/jsp/all/index.jsp" title="沪深股票" class="standalone" target="_top">沪深股票</a>
		</li>
		<li class="menubar">
			<a href="${ctxPath}/jsp/always/index.jsp" title="今日持强" class="standalone" target="_top">今日持强</a>
		</li>
		<li class="menubar">
			<a href="${ctxPath}/jsp/jjcq/index.jsp" title="均价持强" class="standalone" target="_top">均价持强</a>
		</li>
		<li class="menubar">
			<a href="${ctxPath}/jsp/xdqs/index.jsp" title="相对强势" class="standalone" target="_top">相对强势</a>
		</li>
		<li class="menubar">
			<a href="${ctxPath}/jsp/xydp/index.jsp" title="星宇盯盘" class="standalone" target="_top">星宇盯盘</a>
		</li>
		<li class="menubar">
			<a href="${ctxPath}/jsp/rate/index.jsp" title="买卖比例" class="standalone" target="_top">买卖比例</a>
		</li>
		<li class="menubar">
			<a href="${ctxPath}/jsp/mmgd/index.jsp" title="买卖挂单" class="standalone" target="_top">买卖挂单</a>
		</li>
	</ul>
	
	<table width="100%">
		<tr>
			<td align="left" valign="bottom">
				<a href="${ctxPath}" target="_top"><img src="${ctxPath}/images/visuel_bandeau_gauche.gif" height="239" width="94" border="0"></a>
			</td>
		</tr>
			
	</table>
	
</body>
</html>
