<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>菜单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			if(!$("#bgColor").val()){
				$("#bgColor").val("#2ecc71")
			}
			$("body").css({"background-color": "#fff", "min-width": "inherit"});
			$("#name").focus();
			if ($("input:radio[name='isShow']:checked").length == 0) {
                $("input:radio[name='isShow']")[0].checked = true;
            }
			$("#inputForm").validate({
				rules: {
					target: true,
					href:{maxlength:100}
					
				},
				messages: {
					target: "【目标】长度需要在0到20之间",
					href:{ maxlength:"字符长度不能超过100"}
				},
				submitHandler: function(form){
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
		});
		/**
         * 提交表单
         */
        function sub() {
            $("#inputForm").submit();
        }
    </script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="menu" action="${ctx}/sys/menu/save" method="post">
	    <form:hidden path="id"/>
	    <tags:message content="${message}"/>
	    <div class="System_addInfo">
	        <ul class="pt10">
	            <li>
	                <span class="name"><em class="red">*</em>上级菜单：</span>
	                <tags:treeselect id="menu" name="parent.id" value="${menu.parent.id}" labelName="parent.name"
	                                 labelValue="${menu.parent.name}"
	                                 title="菜单" url="/sys/menu/treeData" extId="${menu.id}" cssClass="required"/>
	            </li>
	            <li>
	                <span class="name"><em class="red">*</em>名称：</span>
	                <form:input path="name" htmlEscape="false" maxlength="50" type="text" class="text required"/>
	            </li>
	            <li>
	                <span class="name"><em class="red">*</em>排序：</span>
	                <form:input path="sort" htmlEscape="false" maxlength="50" type="text" class="required text digits"/>
	            </li>
	            <li>
	                <span class="name">权限标识：</span>
	                <form:input path="permission" htmlEscape="false" type="text" class="text" maxlength="100"/>
	                <!-- <span class="help-inline">控制器中定义的权限标识，如：@RequiresPermissions("权限标识")</span> -->
	            </li>
	            
                <li>
                    <span class="name">链接：</span>
                    <form:input path="href" htmlEscape="false" maxlength="200" type="text" class="text"/>
                    <span class="help-inline">点击菜单跳转的页面</span>
                </li>
                <%-- <li>
                    <span class="name">目标：</span>
                    <form:input path="target" htmlEscape="false" type="text" class="text" maxlength="20"/>
                    <!-- <span class="help-inline">链接地址打开的目标窗口，默认：mainFrame</span> -->
                    <form:select path="target" class="input-medium" id="target">
		                <form:option value="" label="请选择"/>
		                <form:option value="tab">标签页</form:option>
		                <form:option value="new">新窗口</form:option>
                	</form:select>
                </li> --%>
                <li>
                    <span class="name">显示类型：</span>
                    <form:select path="showType" class="selectpicker select">
		                <form:option value="" label="请选择"/>
		                <form:options items="${fns:getDictList('show_type')}" itemLabel="label" itemValue="value"
		                              htmlEscape="false"/>
	                </form:select>
					<span class="help-inline">菜单显示类型</span>
                </li>
                <li>
	                <span class="name">菜单背景色：</span>
	                <form:input id="bgColor" path="bgColor" htmlEscape="false" type="text" class="text" maxlength="10"/>
	            </li>
                <li>
	                <span class="name">区域名称：</span>
	                <form:input id="areaName" path="areaName" htmlEscape="false" type="text" class="text" maxlength="10"/>
	            </li>
	            <li class="w100">
	                <span class="name">图标：</span>
	                <tags:iconselect id="icon" name="icon" value="${menu.icon}"></tags:iconselect>
	            </li>
	            <li class="w100">
	                <span class="name"><em class="red">*</em>可见：</span>
	                <dl class="RadioBox">
	                <form:radiobuttons path="isShow" items="${fns:getDictList('show_hide')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
	                <span class="help-inline">该菜单或操作是否显示到系统菜单中</span>
	               </dl>
	            </li>
				<li class="w100">
	                <span class="name">备注：</span>
	                <form:textarea id="remark" path="remarks" htmlEscape="false" maxlength="200"
	                               class="textarea rdu3" onkeyup="remarkCount($(this),200)"/>
	                <span class="pl10"><font class="red">0</font>/200</span>
	            </li>
	            <li class="btnPad">
	                <shiro:hasPermission name="sys:menu:edit">
	                    <a class="btn01 dib rdu3 f14 tc mr10" href="javascript:void(0);" onclick="sub();">保存</a>
	                </shiro:hasPermission>
	                <a class="btn02 dib rdu3 f14 tc" href="javascript:void(0);" onclick="history.go(-1)">返回</a>
	            </li>
	        </ul>
	    </div>
	</form:form>
</body>
</html>