]<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<!--表格列表-->
	<div class="Mytable_box ml10 mr10 rdu3 mt10 jepor">
	    <!--功能按钮-->
	    <div class="titleDiv p10">
	        <p class="btnDiv fr ml10">
	            <!--添加角色-->
	            <shiro:hasPermission name="sys:role:edit">
	                <a class="rdu3" href="${ctx}/sys/role/form"><i class="iconfont f14 mr5">&#xe60a;</i>添加角色</a>
	            </shiro:hasPermission>
	
	        </p>
	        <p class="f16 name">角色管理</p>
	    </div>
	    <!--功能按钮 End-->
	
	    <div class="listHead">
	        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <thead>
	            <tr>
	                <!-- <td width="6%" class="noLine"><input type="checkbox" id="tableHead"></td> -->
	                <td class="noLine" width="20%">角色名称</td>
	                <td width="20%">归属机构</td>
	                <td width="18%">数据范围</td>
	                <shiro:hasPermission name="sys:role:edit">
	                    <td width="25%">操作</td>
	                </shiro:hasPermission>
	            </tr>
	            </thead>
	        </table>
	    </div>
	    <table id="treeTable" width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tbody>
	        <c:forEach items="${list}" var="role">
	            <tr>
	                <%-- <td width="6%" class="noLine"><input id="${role.id}" type="checkbox" name="rowSelect"
	                                                     value="${role.id}"/> --%>
	                <td class="noLine" width="20%"><a href="form?id=${role.id}">${role.name}</a></td>
	                <td width="20%">${role.office.name}</td>
	                <td width="18%">${fns:getDictLabel(role.dataScope, 'sys_data_scope', '无')}</td>
	                <!--操作-->
	                <td width="25%">
	                    <shiro:hasPermission name="sys:role:edit">
	                        <a class="mr10" href="${ctx}/sys/role/assign?id=${role.id}">
	                            <i class="iconfont f16 mr5">&#xe605;</i>分配
	                        </a>
	                        <a class="mr10" href="${ctx}/sys/role/form?id=${role.id}">
	                            <i class="iconfont f16 mr5">&#xe61b;</i>修改
	                        </a>
	                        <a class="mr10" href="${ctx}/sys/role/delete?id=${role.id}"
	                           onclick="return confirmx('确认要删除该角色吗？', this.href)">
	                            <i class="iconfont f16 mr5">&#xe618;</i>删除
	                        </a>
	                    </shiro:hasPermission>
	                </td>
	            </tr>
	        </c:forEach>
	        </tbody>
	    </table>
	</div>
	<!--表格列表 End-->
</body>
</html>