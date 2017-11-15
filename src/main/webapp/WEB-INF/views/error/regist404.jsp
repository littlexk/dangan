<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%response.setStatus(200);%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>404 - 页面不存在</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
		<meta http-equiv="refresh" content="5;url=${ctx}/login"/>
<script type="text/javascript">
		var id = setInterval(function(){
			if(navigator.userAgent.indexOf("MSIE")>0) {
		      var obj = document.getElementById('endtime');
		        var num = parseInt(obj.innerHTML);
					obj.innerHTML = num-1;
			}else if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){
				     var obj = document.getElementById('endtime');
		     var num = parseInt(obj.textContent);
			       obj.textContent = num-1;
			} else{
				var obj = document.getElementById('endtime');
		        var num = parseInt(obj.innerHTML);
				 obj.innerHTML = num-1;
			}
			},1000);
			setTimeout(function(){
				clearInterval(id);
			},10000);
</script>
</head>
<body>
	<div class="container-fluid">
		<div class="page-header"><h1>页面不存在.</h1></br>
		<div style="margin-left: 60px;"><b><span id=endtime>5</span> 秒钟后页面将自动返回<a href="${ctx}/login" title="点击返回登录页"> 登录</a>页面...</b></br></br>
		</div>
		</div>
		
		<div><a href="javascript:" onclick="history.go(-1);" class="btn">返回上一页</a></div>
		<script>try{top.$.jBox.closeTip();}catch(e){}</script>
	</div>
</body>
</html>