<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#name").focus();
			$("#inputForm").validate({
				rules: {
					name: {remote: "${ctx}/sys/role/checkName?oldName=" + encodeURIComponent("${role.name}")}
				},
				messages: {
					name: {remote: "角色名已存在"}
				},
				submitHandler: function(form){
					var ids = [], nodes = tree.getCheckedNodes(true);
					for(var i=0; i<nodes.length; i++) {
						ids.push(nodes[i].id);
					}
					$("#menuIds").val(ids);
					var ids2 = [], nodes2 = tree2.getCheckedNodes(true);
					for(var i=0; i<nodes2.length; i++) {
						ids2.push(nodes2[i].id);
					}
					$("#officeIds").val(ids2);
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});

			var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
					data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
						tree.checkNode(node, !node.checked, true, true);
						return false;
					}}};
			
			// 用户-菜单
			var zNodes=[
					<c:forEach items="${menuList}" var="menu">{id:'${menu.id}', pId:'${not empty menu.parent.id?menu.parent.id:0}', name:"${not empty menu.parent.id?menu.name:'权限列表'}"},
		            </c:forEach>];
			// 初始化树结构
			var tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
			// 默认选择节点
			var ids = "${role.menuIds}".split(",");
			for(var i=0; i<ids.length; i++) {
				var node = tree.getNodeByParam("id", ids[i]);
				try{tree.checkNode(node, true, false);}catch(e){}
			}
			// 默认展开全部节点
			tree.expandAll(true);
			
			// 用户-机构
			var zNodes2=[
					<c:forEach items="${officeList}" var="office">{id:'${office.id}', pId:'${not empty office.parent?office.parent.id:0}', name:"${office.name}"},
		            </c:forEach>];
			// 初始化树结构
			var tree2 = $.fn.zTree.init($("#officeTree"), setting, zNodes2);
			// 不选择父节点
			tree2.setting.check.chkboxType = { "Y" : "s", "N" : "s" };
			// 默认选择节点
			var ids2 = "${role.officeIds}".split(",");
			for(var i=0; i<ids2.length; i++) {
				var node = tree2.getNodeByParam("id", ids2[i]);
				try{tree2.checkNode(node, true, false);}catch(e){}
			}
			// 默认展开全部节点
			tree2.expandAll(true);
			// 刷新（显示/隐藏）机构
			refreshOfficeTree();
			$("#dataScope").change(function(){
				refreshOfficeTree();
			});
		});
		function refreshOfficeTree(){
			if($("#dataScope").val()==9){
				$("#officeTree").show();
			}else{
				$("#officeTree").hide();
			}
		}
		
		/**
         * 提交表单
         */
        function sub() {
            $("#inputForm").submit();
        }
	</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="role" action="${ctx}/sys/role/save" method="post" >
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="System_addInfo">
			 <ul class="pt10">
			<li class="w100">
	                <span class="name"><em class="red">*</em>归属机构：</span>
	                    <tags:treeselect id="office" name="office.id" value="${role.office.id}" labelName="office.name"
	                                     labelValue="${role.office.name}"
	                                     title="机构" url="/party/common/orgUtils/treeData" cssClass="required"/>
	          </li>
	          <li class="w100">
	                <span class="name"><em class="red">*</em>角色名称：</span>
	                <input id="oldName" name="oldName" type="hidden" value="${role.name}">
                    <form:input path="name" htmlEscape="false" maxlength="50" type="text" class="text required"/>
	            </li>
	            <li>
	                <span class="name">数据范围：</span>
	                <form:select path="dataScope" class="select selectpicker" data-width="251px">
	                    <form:option value="" label="请选择"/>
	                    <form:options items="${fns:getDictList('sys_data_scope')}" itemLabel="label" itemValue="value"
	                                  htmlEscape="false"/>
	                </form:select>
	                <em class="help-inline">特殊情况下，设置为“按明细设置”，可进行跨机构授权</em>
	            </li>
	            
	            <li class="w100">
	                <span class="name">角色授权：</span>
	                <div class="fl Role_list">
	                    <div id="menuTree" class="ztree" style="margin-top:3px;"></div>
	                    <form:hidden path="menuIds"/>
	                    <div id="officeTree" class="ztree" style="margin-left:100px;margin-top:3px;"></div>
	                    <form:hidden path="officeIds"/>
	                </div>
	                <div class="clear"></div>
	            </li>
	             <li class="btnPad pb20">
	                <shiro:hasPermission name="sys:role:edit">
	                    <a id="btnSubmit" class="btn01 dib rdu3 f14 tc mr10" href="javascript:void(0);"
	                       onclick="sub();">保 存</a>
	                </shiro:hasPermission>
	                <a id="btnCancel" class="btn02 dib rdu3 f14 tc" href="javascript:void(0);" onclick="history.go(-1)">返回</a>
	            </li>
	            </ul>
		</div>
	</form:form>
</body>
</html>