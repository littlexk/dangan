<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字典管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
		//跳转到修改页面
	    function addChange() {
	        window.location.href = "${ctx}/sys/dict/form?sort=10";
	    }
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="dict" action="${ctx}/sys/dict/" method="post">
	    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	
	    <!--搜索-->
	    <div class="Search_box pt10 pl5 m10 rdu3 jepor">
	        <ul>
	            <li style="width:200px;" class="fl pl5 pr10">
	                <span class="show">类型</span>
	                <form:select cssStyle="input-medium" allowReset="true" id="type" path="type" class="select selectpicker" data-live-search="true">
	                    <form:option value="" label="请选择"/>
	                    <form:options items="${typeList}" htmlEscape="false"/>
	                </form:select>
	            </li>
	            <li class="fl pl5 pr5">
	                <span class="show">描述</span>
	                <form:input allowReset="true" path="description" htmlEscape="false" maxlength="50" class="text"/>
	            </li>
	        </ul>
	        <div class="frBtn jepoa">
	            <span class="fl">
	                <a id="btnSubmit" href="javascript:void(0);" class="btnSearch"
	                   onclick="return page('${page.pageNo}','${page.pageSize}');">
	                    <i class="iconfont f16 mr5">&#xe686;</i>查询
	                </a>
	            </span>
	            <span class="btnOther fl ml5">
	                 <a href="javascript:void(0);" title="刷新" class="rdu3" onclick="resetSearchForm($('#searchForm'))">
	                     <i class="iconfont f16">&#xe68f;</i>
	                 </a>
	            </span>
	        </div>
	        <div class="clear"></div>
	    </div>
	    <!--搜索 End-->
	
	</form:form>
	<!--表单 结束-->
	<tags:message content="${message}"/>
	<!--表格列表-->
	<div class="Mytable_box ml10 mr10 rdu3 jepor">
	    <!--功能按钮-->
	    <div class="titleDiv p10">
	        <p class="btnDiv fr ml10">
	            <!--添加按钮-->
	            <a class="first last" href="javascript:void(0);" onclick="addChange();"><i class="iconfont f14 mr5">&#xe60a;</i>添加</a>
	        </p>
	        <p class="f16 name">字典列表</p>
	    </div>
	    <!--功能按钮 End-->
	
	    <div class="listHead">
	        <table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <thead>
	            <tr>
	                <td width="11%" class="noLine">键值</td>
	                <td width="15%">标签</td>
	                <td width="23%">类型</td>
	                <td width="15%">描述</td>
	                <td width="6%">排序</td>
	                <shiro:hasPermission name="sys:dict:edit">
	                    <td width="22%">操作</td>
	                </shiro:hasPermission></tr>
	            </tr>
	            </thead>
	        </table>
	    </div>
	    <table id="contentTable" width="100%" border="0" cellspacing="0" cellpadding="0">
	        <tbody>
	        <c:forEach items="${page.list}" var="dict">
	            <tr>
	                <td width="11%" class="noLine">${dict.value}</td>
	                <td width="15%"><a href="${ctx}/sys/dict/form?id=${dict.id}">${dict.label}</a></td>
	                <td width="23%"><a href="javascript:"
	                                   onclick="$('#type').val('${dict.type}');$('#searchForm').submit();return false;">${dict.type}</a>
	                </td>
	                <td width="15%">${dict.description}</td>
	                <td width="6%">${dict.sort}</td>
	                <shiro:hasPermission name="sys:dict:edit">
	                    <td width="22%">
	                        <a href="${ctx}/sys/dict/form?id=${dict.id}" class="mr10"><i class="iconfont f16 mr5">
	                            &#xe61b;</i>修改</a>
	                        <a href="${ctx}/sys/dict/delete?id=${dict.id}" class="mr10"
	                           onclick="return confirmx('确认要删除该字典吗？', this.href)"><i class="iconfont f16 mr5">
	                            &#xe618;</i>删除</a>
	                        <a href="<c:url value='${fns:getAdminPath()}/sys/dict/form?type=${dict.type}&sort=${dict.sort+10}'><c:param name='description' value='${dict.description}'/></c:url>"><i
	                                class="iconfont f16 mr5">&#xe60a;</i>添加键值</a>
	                    </td>
	                </shiro:hasPermission>
	            </tr>
	        </c:forEach>
	        </tbody>
	    </table>
	    <!--分页-->
	    <div class="pagination ml10">${page}</div>
	    <!--分页 End-->
	</div>
	<!--表格列表 End-->
</body>
</html>