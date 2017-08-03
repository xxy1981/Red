<%@include file="/jsp/common/taglibs.jsp"%>
<%@include file="/jsp/common/login_check.jsp" %>
<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>

<html>
<head>
	<title>Stock Server</title>
	<%@include file="/jsp/common/meta.jsp"%>
	<script language="javascript">
		
		var saml;
		//Test function with post method
		//function RequestByPost()
		//{
			debugger;
			
			var userName = "201613";
			var password = "201613";
			
			var samlString = "<SOAP:Envelope xmlns:SOAP=\"http://schemas.xmlsoap.org/soap/envelope/\">"
				+ "	 	<SOAP:Header>"
				+ "      	<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
				+ "          	<wsse:UsernameToken xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">"
				+ "              	<wsse:Username>"+userName+"</wsse:Username>"
				+ "              	<wsse:Password>"+password+"</wsse:Password>"
				+ "          	</wsse:UsernameToken>"
				+ "      	</wsse:Security>"
				+ "  	</SOAP:Header>"
				+ "  	<SOAP:Body>"
				+ "      	<samlp:Request xmlns:samlp=\"urn:oasis:names:tc:SAML:1.0:protocol\" MajorVersion=\"1\" MinorVersion=\"1\">"
				+ "          	<samlp:AuthenticationQuery>"
				+ "              	<saml:Subject xmlns:saml=\"urn:oasis:names:tc:SAML:1.0:assertion\">"
				+ "                  	<saml:NameIdentifier Format=\"urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified\">"+userName+"</saml:NameIdentifier>"
				+ "              	</saml:Subject>"
				+ "          	</samlp:AuthenticationQuery>"
				+ "      	</samlp:Request>"
				+ "		</SOAP:Body>"
				+ "	</SOAP:Envelope>";
			
			
			var xmlhttp = getConnection();

			var urlCordysGateway = "http://xxy:4303/cordys//com.eibus.web.soap.Gateway.wcp";
			var urlCordysGateway2 = "http://xxy:4303/cordys/com.eibus.web.soap.Gateway.wcp?organization=o=myorg,cn=cordys,cn=defaultInst,o=xxytech&SAMLart=";
			
			debugger;
			xmlhttp.open("POST", urlCordysGateway, false);
			xmlhttp.send(samlString);
			smal = xmlhttp.responseXML.selectSingleNode(".//samlp:AssertionArtifact").text;
			urlCordysGateway2 = urlCordysGateway2 + xmlhttp.responseXML.selectSingleNode(".//samlp:AssertionArtifact").text;

		//}
		
		/***************************************************************
		 * function getConnection :: Cross browser implementation to get a xmlHttp object.
		 */
		function getConnection() {
			var xmlHttp = null;
			try {
				xmlHttp = new XMLHttpRequest();	// Firefox, Opera 8.0+, Safari, IE7+
			} catch (e) {
				// Internet Explorer
				try {
					xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
				} catch (e) {
					xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
				}
			}
			return xmlHttp;
		}
		
		function cordysbtn() {
			window.location="http://xxy:4303/home/comac/cc/comac/collaboration/hr/common/CustomTaskList.caf?organization=o%3Dcomac%2Ccn%3Dcordys%2Ccn%3DdefaultInst%2Co%3Dxxytech&SAMLart="+smal;
		}
		function openWindow(){
			 
			//alert("此处将跳转审批系统中该合同的审批结果页面！");
			//alert(url);
			//window.showModalDialog("http://xxy:4303/home/comac/cc/comac/collaboration/hr/common/CustomTaskList.caf?organization=o%3Dcomac%2Ccn%3Dcordys%2Ccn%3DdefaultInst%2Co%3Dxxytech&SAMLart=MDHyLkUzvqdcVml1sFOcS6SkLgjV3CJzIlZwss/BUuM9nNvBvihQ+aj/&time=" + new Date(),"dialogWidth=100%;dialogHeight=100%");
			//window.open("http://xxy:4303/home/comac/cc/comac/collaboration/hr/common/CustomTaskList.caf?organization=o%3Dcomac%2Ccn%3Dcordys%2Ccn%3DdefaultInst%2Co%3Dxxytech&SAMLart=MDHyLkUzvqdcVml1sFOcS6SkLgjV3CJzIlZwss/BUuM9nNvBvihQ+aj/&time=" + new Date(),"_blank");
			window.open("/home/comac/cc/comac/collaboration/hr/common/CustomTaskList.caf?organization=o%3Dcomac%2Ccn%3Dcordys%2Ccn%3DdefaultInst%2Co%3Dxxytech","Cordys","menubar=0,scrollbars=1, resizable=1,status=1,titlebar=0,toolbar=0,location=1");
			
			
		}
		function openWindow1(){
			debugger;
			if(top.document.getElementById("mainFrame")){ 
				
				top.window.frames["mainFrame"]._webform_IsApplicationJSLoaded = false;
				//.getElementById("formid")
				top.window.frames["mainFrame"]._webform_loadApplicationJS();
				
				//top.document.getElementById("mainFrame").src = null; 
				//top.document.getElementById("mainFrame").src = "http://xxy:4303/home/comac/cc/comac/collaboration/hr/common/CustomTaskList.caf?organization=o%3Dcomac%2Ccn%3Dcordys%2Ccn%3DdefaultInst%2Co%3Dxxytech&SAMLart=MDHyLkUzvqdcVml1sFOcS6SkLgjV3CJzIlZwss/BUuM9nNvBvihQ+aj/&time=" + new Date(); 
				//top.document.getElementById("mainFrame").src = "http://xxy/home/comac/cc/comac/collaboration/hr/common/CustomTaskList.caf?organization=o%3Dcomac%2Ccn%3Dcordys%2Ccn%3DdefaultInst%2Co%3Dxxytech"; 
				//top.window.frames["mainFrame"].src = "http://xxy/home/comac/cc/comac/collaboration/hr/common/CustomTaskList.caf?organization=o%3Dcomac%2Ccn%3Dcordys%2Ccn%3DdefaultInst%2Co%3Dxxytech";
				top.window.frames["mainFrame"].document.location.href="/home/comac/cc/comac/collaboration/hr/common/CustomTaskList.caf?organization=o%3Dcomac%2Ccn%3Dcordys%2Ccn%3DdefaultInst%2Co%3Dxxytech";
				//top.window.frames["mainFrame"].document.location.href="http://xxy/home/comac/defaulttest.htm";
				//setTimeout('doRefreshTamCookie()',20000);
			}//兼容IE、Chrome和Firefox，此写法正确，推荐使用
			//window.showModalDialog("http://xxy:4303/home/comac/cc/comac/collaboration/hr/common/CustomTaskList.caf?organization=o%3Dcomac%2Ccn%3Dcordys%2Ccn%3DdefaultInst%2Co%3Dxxytech&SAMLart=MDHyLkUzvqdcVml1sFOcS6SkLgjV3CJzIlZwss/BUuM9nNvBvihQ+aj/&time=" + new Date(),"dialogWidth=100%;dialogHeight=100%");
			//window.open("http://xxy:4303/home/comac/cc/comac/collaboration/hr/common/CustomTaskList.caf?organization=o%3Dcomac%2Ccn%3Dcordys%2Ccn%3DdefaultInst%2Co%3Dxxytech&SAMLart=MDHyLkUzvqdcVml1sFOcS6SkLgjV3CJzIlZwss/BUuM9nNvBvihQ+aj/&time=" + new Date(),"_blank");
			//window.open("http://localhost:8080/Red/jsp/test/open.jsp");
			
		}
		
		function doRefreshTamCookie()
		{
			top.window.frames["mainFrame"]._webform_IsApplicationJSLoaded = false;
			//.getElementById("formid")
			top.window.frames["mainFrame"]._webform_loadApplicationJS();
		}
		</Script>
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
			<a href='/cordys/cc/comac/collaboration/hr/common/CustomTaskList.caf?organization=o%3Dcomac%2Ccn%3Dcordys%2Ccn%3DdefaultInst%2Co%3Dxxytech'  title="Cordys" class="standalone" target="mainFrame">Cordys</a>
		</li>
	</ul>
	
	<table width="100%">
		<tr>
			<td align="left" valign="bottom">
				<a href="${ctxPath}" target="_top"><img src="${ctxPath}/images/visuel_bandeau_gauche.gif" height="239" width="94" border="0"></a>
			</td>
		</tr>
			<input type="button" value="Open1" style="width:55px;height:18;" onclick="openWindow1()">
			<input type="button" value="Open" style="width:55px;height:18;" onclick="openWindow()">
	</table>
</body>
</html>
