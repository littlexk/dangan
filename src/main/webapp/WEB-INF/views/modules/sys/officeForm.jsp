<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
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
		//提交保存
        function save() {
            $("#inputForm").submit();
        }
	</script>
</head>
<body>
	<%-- <ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/office/">机构列表</a></li>
		<li class="active"><a href="${ctx}/sys/office/form?id=${office.id}&parent.id=${office.parent.id}">机构<shiro:hasPermission name="sys:office:edit">${not empty office.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:office:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="office" action="${ctx}/sys/office/save" method="post" class="form-horizontal">
		<input type="hidden" name="bean['old_master']" value = "${office.master}"/>
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">上级机构:</label>
			<div class="controls">
                <tags:treeselect id="office" name="parent.id" value="${office.parent.id}" labelName="parent.name" labelValue="${office.parent.name}"
					title="机构" url="/party/common/orgUtils/treeData" extId="${office.id}" cssClass="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">归属区域:</label>
			<div class="controls">
                <tags:treeselect id="area" name="area.id" value="${office.area.id}" labelName="area.name" labelValue="${office.area.name}"
					title="区域" url="/sys/area/treeData" cssClass="required" disabled="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构编码:</label>
			<div class="controls">
				<form:input path="code" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构类型:</label>
			<div class="controls">
				<form:select path="type">
					<form:options items="${fns:getDictList('sys_office_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">机构级别:</label>
			<div class="controls">
				<form:select path="grade">
					<form:options items="${fns:getDictList('sys_office_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系地址:</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮政编码:</label>
			<div class="controls">
				<form:input path="zipCode" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">负责人:</label>
			<div class="controls">
				<tags:examEmployeeSelect id="master" name="master" value="${office.master}" 
								labelName="office.master" labelValue="${office.master}" checked="false"
								title="负责人" cssClass="" cssStyle="width:165px;"/>
				<form:input path="master" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话:</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">传真:</label>
			<div class="controls">
				<form:input path="fax" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">邮箱:</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="50"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:office:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form> --%>
	<form:form id="inputForm" modelAttribute="office" action="${ctx}/sys/office/save" method="post">
		<input type="hidden" name="bean['old_master']" value = "${office.master}"/>
	    <form:hidden path="id"/>
	    <tags:message content="${message}"/>
	    <div class="System_addInfo">
	        <ul class="pt10">
	            <li>
	                <span class="name"><em class="red">*</em>上级机构：</span>
	                <tags:treeselect id="office" name="parent.id" value="${office.parent.id}" labelName="parent.name"
	                                 labelValue="${office.parent.name}" title="机构" url="/party/common/orgUtils/treeData"
	                                 extId="${office.id}" cssClass="required"/>
	            </li>
	            <li>
	                <span class="name">归属区域：</span>
	                <tags:treeselect id="area" name="area.id" value="${office.area.id}" labelName="area.name"
	                                 labelValue="${office.area.name}" title="区域" url="/sys/area/treeData"/>
	            </li>
	            <li>
	                <span class="name"><em class="red">*</em>机构名称：</span>
	                <form:input path="name" htmlEscape="false" maxlength="50" class="text  required"/>
	            </li>
	            <li>
	                <span class="name"><em class="red">*</em>机构编码：</span>
	                <form:input path="code" htmlEscape="false" maxlength="50" cssClass="text  required"/>
	            </li>
	            <li>
	                <span class="name"><em class="red">*</em>机构类型：</span>
	                <form:select path="type">
	                    <form:options items="${fns:getDictList('sys_office_type')}" itemLabel="label" itemValue="value"
	                                  htmlEscape="false" class=""
	                                  cssClass="required"/>
	                </form:select>
	            </li>
	            <li class="hide">
	                <span class="name">机构级别：</span>
	                <form:select path="grade">
	                    <form:options items="${fns:getDictList('sys_office_grade')}" itemLabel="label" itemValue="value"
	                                  htmlEscape="false" class=""/>
	                </form:select>
	            </li>
	            <li>
	                <span class="name">联系地址：</span>
	                <form:input path="address" htmlEscape="false" maxlength="50" class="text "/>
	            </li>
	            <li>
	                <span class="name">邮政编码：</span>
	                <form:input path="zipCode" htmlEscape="false" maxlength="50" class="text "/>
	            </li>
	            <li>
	                <span class="name">负责人：</span>
	                <form:input path="master" htmlEscape="false" maxlength="50" class="text "/>
	            </li>
	            <li>
	                <span class="name">电话：</span>
	                <form:input path="phone" htmlEscape="false" maxlength="50" class="text "/>
	            </li>
	            <li>
	                <span class="name">传真：</span>
	                <form:input path="fax" htmlEscape="false" maxlength="50" class="text "/>
	            </li>
	            <li>
	                <span class="name">邮箱：</span>
	                <form:input path="email" htmlEscape="false" maxlength="50" class="text "/>
	            </li>
	            <li class="w100">
	                <span class="name">备注：</span>
	                <form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200"
	                               class="input-xlarge textarea rdu3"/>
	            </li>
	
	            <li class="btnPad">
	                <shiro:hasPermission name="sys:office:edit">
	                    <a href="javascript:void(0);" class="btn01 dib rdu3 f14 tc mr10" onclick="save();">保存</a>
	                </shiro:hasPermission>
	
	                <a href="javascript:void(0);" class="btn02 dib rdu3 f14 tc" onclick="history.go(-1)">返回</a>
	            </li>
	        </ul>
	    </div>
	</form:form>
</body>
</html>