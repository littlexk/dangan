<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 5});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<!--表格列表-->
	<div class="Mytable_box ml10 mr10 rdu3 mt10 jepor">
	    <!--功能按钮-->
	    <div class="titleDiv p10">
	        <p class="btnDiv fr ml10">
	        	<shiro:hasPermission name="sys:office:edit">
	            	<a class="rdu3" href="${ctx}/sys/office/form?parent.id=${office.id}"><i class="iconfont f14 mr5">&#xe60a;</i>机构添加</a>
	        	</shiro:hasPermission>
	        </p>
	        <p class="f16 name">机构管理</p>
	    </div>
	    <!--功能按钮 End-->
	
	    <div class="listHead">
	        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <thead>
	            <tr>
	                <td width="20%" class="noLine">机构名称</td>
	                <td width="10%">归属区域</td>
	                <td width="10%">机构编码</td>
	                <td width="10%">机构类型</td>
	                <td width="10%">负责人编号</td>
	                <td width="10%">备注</td>
	                <shiro:hasPermission name="sys:office:edit">
	                    <td width="25%">操作</td>
	                </shiro:hasPermission>
	            </tr>
	            </thead>
	        </table>
	    </div>
	    <table id="treeTable" width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tbody>
	        <c:forEach items="${list}" var="office">
	            <tr id="${office.id}" pId="${office.parent.id ne requestScope.office.id?office.parent.id:'0'}">
	                <td width="20%" class="noLine"><a class="blue" href="${ctx}/sys/office/form?id=${office.id}">${office.name}</a></td>
	                <td width="10%">${office.area.name}</td>
	                <td width="10%">${office.code}</td>
	                <td width="10%">${fns:getDictLabel(office.type, 'sys_office_type', '无')}</td>
	                <td width="10%">${office.master}</td>
	                <td width="10%">${office.remarks}</td>
	                <shiro:hasPermission name="sys:office:edit">
	                    <td width="25%">
	                        <a class="mr10" href="${ctx}/sys/office/form?id=${office.id}"><i class="iconfont f16 mr5">
	                            &#xe61b;</i>修改</a>
	                        <a class="mr10" href="${ctx}/sys/office/delete?id=${office.id}"
	                           onclick="return confirmx('要删除该机构及所有子机构项吗？', this.href)"><i class="iconfont f16 mr5">
	                            &#xe618;</i>删除</a>
	                        <a class="mr10" href="${ctx}/sys/office/form?parent.id=${office.id}"><i
	                                class="iconfont f16 mr5">&#xe60a;</i>添加下级机构</a>
	                    </td>
	                </shiro:hasPermission>
	            </tr>
	        </c:forEach>
	        </tbody>
	    </table>
	</div>
	<!--表格列表 End-->
</body>
</html>