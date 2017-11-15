<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>邮箱配置</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function(){
		$("#loginName").change(function(){
			var newLoginName = $(this).val();
			$("#newLoginName").val(newLoginName);
		})
		
		/*清空浏览器设置导致的自动保存密码*/
		setTimeout(function(){$("#password").val("");},50);
		
		/*新密码输入框的信息提示*/
		$("#iconSpan").hover(
			function(){
				$("#passDiv").append($("<span id='infoSpan'><font color='red'>如果需要修改密码则填写此栏目</font></span>"));
			},
			function(){
				$("#passDiv").find("span#infoSpan").remove();
			}
		);
		
		/*邮箱格式验证*/
		jQuery.validator.addMethod("isEmailFormat", function(value,element){
			var format =  /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
			return this.optional(element)||(format.test(value));
		},$.validator.format("请输入一个有效的email地址"));
		
		/*端口范围验证*/
		jQuery.validator.addMethod("stateRange", function(value,element){
			var number = parseInt(value);
			return this.optional(element) || (number>=0 && number<=65535);
		},$.validator.format("请输入一个0到65535之间的整数"));
		
		/*开始验证*/
		$("#inputForm").validate({
			debug: true, //调试模式取消submit的默认提交功能 
			
			ignore: "", // 开启hidden验证， 1.9版本后默认关闭
			
			rules: {
				newLoginName: {remote: '${ctx}/sys/mail/check'}
			},
			
			messages: {
				newLoginName: {remote: '帐号已经存在'}
			},
			
			submitHandler: function(form){
				loading('正在提交，请稍等...');
				form.submit();
			},
			
			errorContainer: "#messageBox",
			/*设置错误信息提示DOM*/
			errorPlacement: function(error, element) {
				$("#messageBox").text("输入有误，请先更正");
		        if ( element.is(".btn") ) error.appendTo( element.parent() );			      
		        else error.appendTo( element.parent());
	    	},
	    }); 
	});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/mail/list">邮箱管理</a></li>
		<li class="active"><a href="${ctx}/sys/mail/form?id=${mailMap.bean.ID}">邮箱${not empty mailMap.bean.ID?'修改':'添加'}</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="mailMap" action="${ctx}/sys/mail/save" method="post" class="form-horizontal">
		<tags:message content="${message}"/>
		<form:input id="id" path="bean['ID']" type="hidden" htmlEscape="false" />
		<div class="control-group">
			<label class="control-label"><font color="red">*</font>发送服务器:</label>
			<div class="controls">
				<form:input path="bean['SMTP_HOST']" htmlEscape="false" class="required" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><font color="red">*</font>发送端口:</label>
			<div class="controls">
				<form:input path="bean['SMTP_PORT']" htmlEscape="false"  class="required digits stateRange" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><font color="red">*</font>帐号:</label>
			<div class="controls">
				<input id="newLoginName" name="newLoginName" type="hidden" value=""/>
				<form:input id="loginName" path="bean['LOGIN_NAME']" htmlEscape="false" class="required isEmailFormat" />
			</div>
		</div>
		<div class="control-group">
			<c:choose>
				<c:when test="${empty mailMap.bean.ID}">
					<label class="control-label"><font color="red">*</font>密码:</label>
					<div class="controls">
						<form:password path="bean['PASSWORD']" htmlEscape="false" class="required"/>
					</div>
				</c:when>
				<c:otherwise>
					<label class="control-label">
					<span id="iconSpan" class="icon-info-sign"></span>
					 新密码:</label>
					<div id=passDiv class="controls">
						<form:password id="password" path="bean['PASSWORD']" htmlEscape="false" />
					</div>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="control-group">
			<label class="control-label"><font color="red">*</font>是否使用SSL:</label>
			<div class="controls">
				<form:select path="bean['SMTP_SSL']" >
					<form:option value="1">是</form:option>
					<form:option value="0">否</form:option>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<input id="btnSubmit" name="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
			<input id="btnCancel" name="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>