<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人信息</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
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
	</script>
</head>
<body >
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/info" method="post">
		<tags:message content="${message}"/>
		 <!--基本信息-->
	   <div class="System_addInfo m10" style="border:1px solid #e5e5e5;" >
	            <ul class="pt10">
	            	<li>
	                    <span class="name">上级部门:</span>
	                    <input class="text" type="text" value="${user.company.name}" readonly/>
	                </li>
	                <li>
	                    <span class="name">所在单位:</span>
	                    <input class="text" type="text" value="${user.office.name}" readonly/>
	                </li>
	            </ul>
	            <div class="clear"></div>
	            <h4 class="f14 m15">归属部门：<span class="f12">${user.office.name}</span></h4>
	            <ul>
	            	<li>
	            		<span class="name">姓名:</span>
	                    <input class="text" type="text" />
	            	</li>
	            	<li>
	            		<span class="name">邮箱:</span>
	                    <input class="text" type="text" />
	            	</li>
	            	<li>
	            		<span class="name">电话:</span>
	                    <input class="text" type="text" />
	            	</li>
	            	<li>
	            		<span class="name">手机:</span>
	                    <input class="text" type="text" />
	            	</li>
	            	<li style="width:100%;">
	            		<span class="name">备注:</span>
	            		<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="Remark" style="width:796px;"/>
	            	</li>
	            </ul>
	            <div class="clear"></div>
	            <dl class="m15">
	            	<span class="n1"><b>用户类型:</b ><em>${fns:getDictLabel(user.userType, 'sys_user_type', '无')}</em></span>
	            	<span class="n2"><b>用户角色:</b><em>${user.roleNames}</em></span>
	            	<span class="n3"><b>最后登陆:</b><em>IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.loginDate}" type="both" dateStyle="full"/></em></span>
	            	<span class="Save">
	            		<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
	            	</span>
	            </dl>
	    </div>
	</form:form>
</body>
</html>