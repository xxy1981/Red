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
					<iframe style="Z-INDEX: 1; VISIBILITY: inherit; WIDTH:100%; HEIGHT: 100%"  
			         name="menuFrame" src="http://xxy:4303/home/comac/cc/comac/collaboration/hr/common/CustomTaskList.caf?organization=o%3Dcomac%2Ccn%3Dcordys%2Ccn%3DdefaultInst%2Co%3Dxxytech&SAMLart=MDHyLkUzvqdcVml1sFOcS6SkLgjV3CJzIlZwss/BUuM9nNvBvihQ+aj/"  frameBorder=0 scrolling="yes"> 
			     </iframe>
				</td>
			</tr>
		</table>
			
	</BODY>
</HTML>
