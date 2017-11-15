<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<style type="text/css">.sort{color:#0663A2;cursor:pointer;}</style>
	<script type="text/javascript">
		$(document).ready(function() {
			// 表格排序
			var orderBy = $("#orderBy").val().split(" ");
			$("#contentTable th.sort").each(function(){
				if ($(this).hasClass(orderBy[0])){
					orderBy[1] = orderBy[1]&&orderBy[1].toUpperCase()=="DESC"?"down":"up";
					$(this).html($(this).html()+" <i class=\"icon icon-arrow-"+orderBy[1]+"\"></i>");
				}
			});
			$("#contentTable th.sort").click(function(){
				var order = $(this).attr("class").split(" ");
				var sort = $("#orderBy").val().split(" ");
				for(var i=0; i<order.length; i++){
					if (order[i] == "sort"){order = order[i+1]; break;}
				}
				if (order == sort[0]){
					sort = (sort[1]&&sort[1].toUpperCase()=="DESC"?"ASC":"DESC");
					$("#orderBy").val(order+" DESC"!=order+" "+sort?"":order+" "+sort);
				}else{
					$("#orderBy").val(order+" ASC");
				}
				page();
			});
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sys/user/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			$("#btnImport").click(function(){
				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true}, 
					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
		});
		function page(n,s){
			var roleId = $("#roleId").val();
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/user/?roleId="+roleId);
			$("#searchForm").submit();  
	    	return false;
	    }
	</script>
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/sys/user/import" method="post" enctype="multipart/form-data"
			class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
			<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
			<a href="${ctx}/sys/user/import/template">下载模板</a>
		</form>
	</div>
	<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/" method="post">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	    <input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
	
	    <!--搜索-->
	    <div class="Search_box pl5 pt5 jepor BoderAndBg m10">
	        <ul>
	            <li class="fl pl5 pr5">
	                <span class="show">上级部门</span>
	                <tags:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}" 
							title="机构" url="/party/common/orgUtils/treeData?type=1" cssClass="input-small" allowClear="true"/>
	            </li>
	            <li class="fl pl5 pr5">
	                <span class="show">登录名</span>
	                <form:input allowReset="true" path="loginName" htmlEscape="false" maxlength="50" class="text"
	                            type="text"
	                            placeholder="登录名"/>
	            </li>
	            <li class="fl pl5 pr5">
	                <span class="show">所在单位</span>
	               <tags:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}" 
				title="单位" url="/party/common/orgUtils/treeData?type=2" cssClass="input-small" allowClear="true"/>
	            </li>
	            <li class="fl pl5 pr5">
	                <span class="show">姓名</span>
	                <form:input allowReset="true" path="name" htmlEscape="false" maxlength="50" class="text"
	                            type="text" placeholder="姓名"/>
	            </li>
	             <li class="fl pl5 pr5">
	           		 <span class="show">角色：</span>
					<select id="roleId" class="selectpicker">
						<option  value="" label="请选择"></option>
						<c:forEach items="${allRoles}" var="role">
						<option value="${role.id}" ${role.id eq roleId?'selected':''}>${role.name}</option>
						</c:forEach>
					</select>
				</li>
	        </ul>
	        <div class="frBtn jepoa">
	            <span class="fl"><a href="javascript:void(0);" class="btnSearch"
	                                onclick="return page('${page.pageNo}','${page.pageSize}');"><i class="iconfont f16 mr5">
	                &#xe686;</i>查询</a></span>
	                    <span class="btnOther fl ml5">
	                         <a href="javascript:void(0);" title="刷新" class="rdu3"
	                            onclick="resetSearchForm($('#searchForm'))"><i class="iconfont f16">&#xe68f;</i></a>
	                    </span>
	        </div>
	        <div style="clear:both;"></div>
	    </div>
	    <!--搜索 End-->
		<tags:message content="${message}"/>
		<div class="MainContent BoderAndBg p10 m10">
	        <div class="Title_right mb10 jepor">
	            <span class="btnDiv fr">
	                <shiro:hasPermission name="sys:user:edit"><a href="${ctx}/sys/user/form" class="rdu3 mr10 acur"><i
	                        class="iconfont f14 mr5">&#xe60a;</i>添加用户</a></shiro:hasPermission>
	                <!-- <a href="javascript:void(0);" id="btnImport"><i class="iconfont f16 mr5">&#xe66b;</i>导入</a> -->
	                <a href="javascript:void(0);" class="last" id="btnExport"><i class="iconfont f14 mr5">&#xe66c;</i>导出</a>
	            </span>
	            <span class="f16">用户管理列表</span>
	        </div>
	        <div class="Table_box rdu3 mb15 jepor" style="overflow-x:auto;">
	            <table border="0" cellspacing="0" cellpadding="0" class="jew100" >
	                <tr class="title">
	                    <td width="13%" class="noLine">上级部门</td>
	                    <td width="13%">归属部门</td>
	                    <td width="11%">登录名</td>
	                    <td width="13%">姓名</td>
	                    <td width="11%">电话</td>
	                    <td width="11%">手机</td>
	                    <td width="11%">角色</td>
	                    <shiro:hasPermission name="sys:user:edit">
	                        <td width="13%">操作</td>
	                    </shiro:hasPermission>
	                </tr>
	                <c:forEach items="${page.list}" var="user">
		                <tr>
		                    <td width="13%" class="noLine">${user.company.name}</td>
		                    <td width="13%">${user.office.name}</td>
		                    <td width="11%"><a class="blue" href="${ctx}/sys/user/form?id=${user.id}">${user.loginName}</a></td>
		                    <td width="13%">${user.name}</td>
		                    <td width="11%">${user.phone}</td>
		                    <td width="11%">${user.mobile}</td>
		                    <td width="11%">${user.roleNames}</td>
		                    <shiro:hasPermission name="sys:user:edit">
		                        <td width="13%">
		                            <a class="mr10" href="${ctx}/sys/user/form?id=${user.id}"><i class="iconfont f16 mr5">
		                                &#xe61b;</i>修改</a>
		                            <a class="mr10" href="${ctx}/sys/user/delete?id=${user.id}"
		                               onclick="return confirmx('确认要删除该用户吗？', this.href)"><i class="iconfont f16 mr5">
		                                &#xe618;</i>删除</a>
		                        </td>
		                    </shiro:hasPermission>
		                </tr>
	            	</c:forEach>
	            </table>
	        </div>
	        <!--分页-->
	        <div class="Pages">${page}</div>
	        <!--分页 结束-->
	    </div>
	</form:form>
</body>
</html>