<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@include file="/jsp/common/taglibs.jsp"%>
<%@include file="/jsp/common/login_check.jsp" %>
 
<%
String pic = request.getParameter("url");
%>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
	<HEAD>
		<title>Stock Server</title>
		<%@include file="/jsp/common/meta.jsp"%>
	</HEAD>
	<BODY text=#000000 bgColor=#d61e1f leftMargin=0 topMargin=0 marginheight="0" marginwidth="0">
		<TABLE cellSpacing=0 cellPadding=0 width="70%" border=0 align=center bgColor=#ffffff height="100%">
			<TBODY>
				<TR height="50%">
					<TD align=center valign=top>
					    <IMG src="<%=pic %>" border=0>
					</TD>
				</TR>
				<TR vAlign=top align=left height="50%">
					<TD width="100%" align=center colspan="2" height="50%">
						<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
							<TBODY>
								<TR valign=top>
									<TD width="50%" align=center>
										<input type="button" name="print22" value="打印本页"
											onClick="javascript:window.print()" style="HEIGHT: 20px" />
									</TD>
									<TD width="50%" align=center>
										<input type="button" name="close22" value="关闭窗口"
											onClick="window.close()" style="HEIGHT: 20px" />
									</TD>
								</TR>
							</TBODY>
						</TABLE>
					</TD>
				</TR>
			</TBODY>
		</TABLE>
	</BODY>
</HTML>
