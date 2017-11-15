<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>菜单管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<style type="text/css">.table td i{margin:0 2px;}</style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 1});
		});
    	function updateSort() {
			loading('正在提交，请稍等...');
	    	$("#listForm").attr("action", "${ctx}/sys/menu/updateSort");
	    	$("#listForm").submit();
    	}
	</script>
</head>
<body>
	  <!--表格列表-->
	<div class="Mytable_box ml10 mr10 rdu3 mt10 jepor">
	    <!--功能按钮-->
	    <div class="titleDiv p10">
	        <p class="btnDiv fr ml10">
	            <!--添加菜单-->
	            <shiro:hasPermission name="sys:menu:edit">
	                <a class="first" href="${ctx}/sys/menu/form"><i class="iconfont f14 mr5">&#xe681;</i>添加菜单</a>
	            </shiro:hasPermission>
	            <!--保存排序-->
	            <shiro:hasPermission name="sys:menu:edit">
	                <a id="btnSubmit" class="last" href="javascript:void(0);" onclick="updateSort();">
	                    <i class="iconfont f14 mr5">&#xe601;</i>保存排序</a>
	            </shiro:hasPermission>
	        </p>
	        <p class="f16 name">菜单管理</p>
	    </div>
	    <!--功能按钮 End-->
	    <!--提示信息-->
	    <tags:message content="${message}"/>
	    <!--提示信息 End-->
	    <form id="listForm" method="post" onsubmit="loading('正在提交，请稍等...');">
	        <div class="listHead">
	            <table width="100%" border="0" cellspacing="0" cellpadding="0">
	                <thead>
	                <tr>
	                    <td width="23%" class="noLine">名称</td>
	                    <td width="17%">链接</td>
	                    <td width="8%">排序</td>
	                    <td width="6%">可见</td>
	                    <td width="20%">权限标识</td>
	                    <shiro:hasPermission name="sys:menu:edit">
	                        <td width="22%">操作</td>
	                    </shiro:hasPermission>
	                </tr>
	                </thead>
	            </table>
	        </div>
	            <table id="treeTable" width="100%" border="0" cellspacing="0" cellpadding="0">
	                <tbody>
	                <c:forEach items="${list}" var="menu">
	                    <tr id="${menu.id}" pId="${menu.parent.id ne '1' ? menu.parent.id : '0'}">
	                        <td width="23%" class="noLine">
	                            <i class="icon iconfont">${ not empty menu.icon ? ('&#'.concat(menu.icon).concat(";")) : ''}</i>
	                            <a href="${ctx}/sys/menu/form?id=${menu.id}">${menu.name}</a>
	                        </td>
	
	                        <td width="17%">${menu.href}</td>
	                        <td width="8%" class="tc">
	                            <shiro:hasPermission name="sys:menu:edit">
	                                <input type="hidden" name="ids" value="${menu.id}"/>
	                                <input name="sorts" style="height:25px;width: 45px;margin-bottom: 0px" class="text rdu3"
	                                       type="text"
	                                       value="${menu.sort}">
	                            </shiro:hasPermission>
	                            <shiro:lacksPermission name="sys:menu:edit">
	                                ${menu.sort}
	                            </shiro:lacksPermission>
	                        </td>
	
	                        <td width="6%" class="tc">${menu.isShow eq '1'?'显示':'隐藏'}</td>
	
	                        <td width="20%">${menu.permission}</td>
	
	                        <td width="22%">
	                            <shiro:hasPermission name="sys:menu:edit">
	                                <a class="mr10" href="${ctx}/sys/menu/form?id=${menu.id}">
	                                    <i class="iconfont f16 mr5">&#xe61b;</i>修改
	                                </a>
	                                <c:if test="${menu.id != '111' && menu.id != '112'}">
	                                    <a class="mr10" href="${ctx}/sys/menu/delete?id=${menu.id}"
	                                       onclick="return confirmx('要删除该菜单及所有子菜单项吗？', this.href)">
	                                        <i class="iconfont f16 mr5">&#xe618;</i>删除
	                                    </a>
	                                </c:if>
	                                <a class="mr10" href="${ctx}/sys/menu/form?parent.id=${menu.id}">
	                                    <i class="iconfont f16 mr5">&#xe681;</i>添加下级菜单
	                                </a>
	                            </shiro:hasPermission>
	                        </td>
	                    </tr>
	                </c:forEach>
	                </tbody>
	            </table>
	    </form>
	</div>
	<!--表格列表 End-->
</body>
</html>
