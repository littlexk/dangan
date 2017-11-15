<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>字典管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#value").focus();
			$("#inputForm").validate({
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
         * 保存
         */
        function save() {
            $("#inputForm").submit();
        }
	</script>
</head>
<body>
	
	<!--表单-->
	<form:form id="inputForm" modelAttribute="dict" action="${ctx}/sys/dict/save" method="post">
	    <form:hidden path="id"/>
	    <!--提示信息-->
	    <tags:message content="${message}"/>
	    <!--提示信息 结束-->
	        <!--基本信息-->
	        <div class="System_addInfo">
	            <ul class="pt10">
	                <li>
	                    <span class="name"><em class="red">*</em>键值:</span>
	                    <form:input path="value" htmlEscape="false" maxlength="50" type="text" class="text required"/>
	                </li>
	                <li>
	                    <span class="name"><em class="red">*</em>标签:</span>
	                    <form:input path="label" htmlEscape="false" maxlength="50" type="text" class="text required"/>
	                </li>
	                <li>
	                    <span class="name"><em class="red">*</em>类型:</span>
	                    <form:input path="type" htmlEscape="false" maxlength="50" class="text required"/>
	                </li>
	                <li>
	                    <span class="name"><em class="red">*</em>描述:</span>
	                    <form:input path="description" htmlEscape="false" maxlength="50" type="text" class="text required"/>
	                </li>
	                <li>
	                    <span class="name"><em class="red">*</em>排序:</span>
	                    <form:input path="sort" htmlEscape="false" maxlength="11" type="text" class="required text"/>
	                </li>
					<%-- <li class="w100">
		                <span class="name">备注：</span>
		                <form:textarea id="remark" path="remarks" htmlEscape="false" maxlength="200"
		                               class="textarea rdu3" onkeyup="remarkCount($(this),200)"/>
		                <span class="pl10"><font class="red">0</font>/200</span>
	            	</li> --%>
	                <li class="btnPad">
	                    <shiro:hasPermission name="sys:dict:edit">
	                        <a id="btnSubmit" class="btn01 dib rdu3 f14 tc mr10" href="javascript:void(0);"
	                           onclick="save();">保 存</a>
	                    </shiro:hasPermission>
	                    <a id="btnCancel" class="btn02 dib rdu3 f14 tc" href="javascript:void(0);" onclick="history.go(-1)">关 闭</a>
	                </li>
	            </ul>
	        </div>
	        <!--基本信息 End-->
	</form:form>
	<!--表单 结束-->
</body>
</html>