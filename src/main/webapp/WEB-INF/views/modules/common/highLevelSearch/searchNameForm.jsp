<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>高级查询名称</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/bns/js/highLevelSearch.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body>
   <!--基本信息-->
   <div class="searchInfo">
       <ul class="pt10">
           <li>
               <span class="name" style="width:150px;">查询方案名称：</span>
               <input id="searchName" type="text" value="${map.bean.searchName}" class="text mt5 searBox" style="margin-left:40px;width:300px;"/>
           </li>
         
       </ul>
       <div class="clear"></div>
    </div>
</body>
</html>