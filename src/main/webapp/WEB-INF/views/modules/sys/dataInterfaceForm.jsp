<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>接口服务页面</title>
<meta name="decorator" content="default" />
<style type="text/css">
.input-append input {
	margin-bottom: 10px;
}
.name{
	width:120px !important;
}
.textarea{
	width:67.3% !important;
}
</style>
<script type="text/javascript">
	var parentForm = null;
	$(document).ready(function() {
		if("${opType}"=="edit"){
			$("#type").attr("readonly","true");
		}
		$("select").each(function(){
			$(this).attr("data-width","162px");
		});
		$("#inputForm").validate({
			submitHandler: function(form){
				$(form).ajaxSubmit({
					type: 'post',
					url:  $(form).attr("action"),
					success:function(result){
						if(result == "${fns:getStandard('OPERATE_SUCCESS')}"){
							top.layer.msg("保存成功",{time:1000},function(){
								parentForm.submit();
								top.layer.closeAll('iframe');
							});
						}else{
							top.layer.msg("保存失败，请联系系统管理员");
						}
					}
				});
			},
			errorContainer: "#messageBox",
			errorPlacement: function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
				selfAdaption(error,element);//屏幕自适应
			}
		});
	});
	/**
      * 提交表单
      */
     function sub() {
         return $("#inputForm").submit();
     }
</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="map" action="${ctx}/sys/dataInterface/dataInterfaceSave" method="post">
		<tags:message content="${message}" />
		<div class="partyALL">
		    <h4><span class="tc show pt10 pb10 f16">接口服务</span></h4>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="Party_dyxx mt15">
                  <tr>
                  	<td>
		    			<label class="name"><span class="red">*</span>服务类型：</label>
		    			<div class="textBox">
		    		       <form:input id="type" path="bean['TYPE']" class="text" required="true" maxlength="50"/>
						</div>
		    		</td>
		    		<td>
		    			<label class="name"><span class="red">*</span>服务名称：</label>
		    			<div class="textBox">
		    		       <form:input id="name" path="bean['NAME']" class="text" required="true" maxlength="50"/>
						</div>
		    		</td>
	           	</tr>
	           	<tr>
	           		<td>
		    			<label class="name"><span class="red">*</span>自动执行：</label>
		    			<div class="textBox">
		    				<form:select id="status" path="bean['STATUS']" class="selectpicker select">
								<form:option value="on">开启</form:option>
								<form:option value="off">关闭</form:option>
							</form:select>
						</div>
		    		</td>
	           	</tr>
	           	<tr>
	           		<td colspan="2">
		    			<label class="name"><span class="red">*</span>执行的存储过程：</label>
		    			<div class="textBox">
		    				<form:input id="excuPro" path="bean['EXCU_PRO']" class="text" maxlength="1000" required="true" style="width:545px"/>
		    			</div>
		    		</td>
	           	</tr>
	           	<tr>
	           		<td colspan="2"><label class="name">更新信息：</label>
	    				<form:textarea path="bean['REMARKS']" class="textarea rdu3" maxlength="500" readonly="true"/>
    				</td>
	           	</tr>
			</table>
		</div>
		<div class="space10"></div>
	</form:form>
</body>
</html>