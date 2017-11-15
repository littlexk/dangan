<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("body").css({"background-color": "#fff", "min-width": "inherit"});
			$("#no").focus();
			$("#inputForm").validate({
				rules: {
					loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
				},
				messages: {
					loginName: {remote: "用户登录名已存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
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
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/save" method="post">
	    <form:hidden path="id"/>
	    <tags:message content="${message}"/>
	
	    <!--基本信息-->
	    <div class="System_addInfo">
	        <ul class="pt10">
	            <li>
	                <span class="name"><em class="red">*</em>上级部门：</span>
	                <input id="companyId" name="company.id" class="text required" type="hidden" value="${user.company.id}" />
					<input id="companyName" name="company.name" readonly="readonly" type="text" value="${user.company.name}" class="text required"/>
	                <%-- <sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name"
	                                 labelValue="${user.company.name}"
	                                 title="公司" url="/party/common/orgUtils/treeData?type=2" cssClass="required"/> --%>
	            </li>
	            <li>
	                <span class="name"><em class="red">*</em>所在单位：</span>
	                <input id="officeId" name="office.id" class="required" type="hidden" value="${user.office.id}" />
					<input id="officeName" name="office.name" readonly="readonly" type="text" value="${user.office.name}" class="text required"/>
	                <%-- <sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name"
	                                 labelValue="${user.office.name}"
	                                 title="部门" url="/party/common/orgUtils/treeData?type=3" cssClass="required"/> --%>
	            </li>
	            <li>
	                <span class="name"><em class="red">*</em>登录名：</span>
	                <input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
	                <form:input path="loginName" htmlEscape="false" maxlength="50" type="text"
	                            class="text required userName"/>
	            </li>
	           
	            <li>
                    <span class="name">
                        <c:if test="${empty user.id}"><em class="red">*</em></c:if>密码：
                    </span>
	                <input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3"
	                       class="text ${empty user.id?'required':''}"/>
	                <c:if test="${not empty user.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
	            </li>
	            <li>
	                <span class="name">
	                    <c:if test="${empty user.id}"><em class="red">*</em></c:if>确认密码：
	                </span>
	                <input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50"
	                       minlength="3" class="text ${empty user.id?'required':''}" equalTo="#newPassword"/>
	            </li>
		     	<li>
	                <span class="name"><em class="red">*</em>人事编号：</span>
	                <form:input path="no" htmlEscape="false" maxlength="50" type="text" class="text required"/>
	            </li>
	            <li>
	                <span class="name"><em class="red">*</em>姓名：</span>
	                <form:input path="name" htmlEscape="false" maxlength="50" type="text" class="text required"/>
	            </li>
	            <li>
	                <span class="name">邮箱：</span>
	                <form:input path="email" htmlEscape="false" maxlength="100" class="text email"/>
	            </li>
	            <li>
	                <span class="name">电话：</span>
	                <form:input path="phone" class="text" type="text" htmlEscape="false" maxlength="100"/>
	            </li>
	            <li>
	                <span class="name">手机：</span>
	                <form:input path="mobile" class="text" type="text" htmlEscape="false" maxlength="100"/>
	            </li>
	            <li>
	                <span class="name">用户类型:</span>
	                <form:select path="userType" class="selectpicker">
	                    <form:option value="" label="请选择"/>
	                    <form:options items="${fns:getDictList('sys_user_type')}" itemLabel="label" itemValue="value"
	                                  htmlEscape="false"/>
	                </form:select>
	            </li>
	            <li class="w100">
	                <span class="name"><em class="red">*</em>用户角色：</span>
	                <div class="fl dib User_span02">
	                       <form:checkboxes path="roleIdList" items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false" class="required"/>
	                </div>
	                <div class="clear"></div>
	            </li>
	            <li class="w100">
	                <span class="name">备注：</span>
	                <form:textarea id="remark" path="remarks" htmlEscape="false" maxlength="200"
	                               class="textarea rdu3" onkeyup="remarkCount($(this),200)"/>
	                <span class="pl10"><font class="red">0</font>/200</span>
	            </li>
	            <c:if test="${not empty user.id}">
	                <li class="w100">
	                    <span class="name">创建时间：</span>
	                    <label class="lbl" style="line-height:34px;"><fmt:formatDate value="${user.createDate}" type="both"
	                                                       dateStyle="full"/></label>
	                </li>
	                <li class="w100">
	                    <span class="name">最后登陆：</span>
	                    <label class="lbl" style="line-height:34px;">IP： ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate
	                            value="${user.loginDate}" type="both" dateStyle="full"/></label>
	                </li>
	            </c:if>
	            <li class="btnPad pb20">
	                <shiro:hasPermission name="sys:user:edit">
	                    <a id="btnSubmit" href="javascript:void(0);" class="btn01 dib rdu3 f14 tc mr10"
	                       onclick="sub();">保存</a>
	                </shiro:hasPermission>
	                <a id="btnCancel" href="javascript:void(0);" class="btn02 dib rdu3 f14 tc"
	                   onclick="history.go(-1)">关闭</a>
	            </li>
	        </ul>
	    </div>
	    <!--基本信息 End-->
	</form:form>
</body>
</html>