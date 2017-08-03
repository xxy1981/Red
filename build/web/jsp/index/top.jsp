<%@page import="java.util.*,java.text.*"%>

<%@include file="/jsp/common/taglibs.jsp"%>
<%@include file="/jsp/common/login_check.jsp" %>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>

<html>
<head>
  	<title>Stock Server</title>
	<%@include file="/jsp/common/meta.jsp"%>
</head>
<body>
	<table border="0" cellpadding="0" cellspacing="0" width="100%">
	<tbody>
    	<tr>
			<td align="left">
      			<div id="glowingtabs">
      				<div class="logo"><font color="#137A4F"><b>星宇</b></font><font color="#333333"><b>股票行情系统</b></font></div>
      				<ul>
						<li>
							<a title="首页" href="${ctxPath}/jsp/index.jsp" target="_top"><span>首页</span></a>
						</li>
						<li id="current">
							<a title="大盘指数" href="${ctxPath}/jsp/index/index.jsp" target="_top"><span>大盘指数</span></a>
						</li>
						<li>
							<a title="沪深股票" href="${ctxPath}/jsp/all/index.jsp" target="_top"><span>沪深股票</span></a>
						</li>
						<li>
							<a title="今日持强" href="${ctxPath}/jsp/always/index.jsp" target="_top"><span>今日持强</span> </a>
						</li>
						<li>
							<a title="均价持强" href="${ctxPath}/jsp/jjcq/index.jsp" target="_top"><span>均价持强</span> </a>
						</li>
						<li>
							<a title="相对强势" href="${ctxPath}/jsp/xdqs/index.jsp" target="_top"><span>相对强势</span> </a>
						</li>
						<li>
							<a title="星宇盯盘" href="${ctxPath}/jsp/xydp/index.jsp" target="_top"><span>星宇盯盘</span> </a>
						</li>
						<li>
							<a title="买卖比例" href="${ctxPath}/jsp/rate/index.jsp" target="_top"><span>买卖比例</span> </a>
						</li>
						<li>
							<a title="买卖挂单" href="${ctxPath}/jsp/mmgd/index.jsp" target="_top"><span>买卖挂单</span> </a>
						</li>
						<li>
							<a title="退出" href="${ctxPath}/logout.do?action=logout" target="_top"><span>退出</span> </a>
						</li>
      				</ul>
				</div>
			</td>
		</tr>
  	</tbody>
	</table>
</body>
</html>
