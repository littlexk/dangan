<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>首页统计执行页面</title>
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
							top.layer.msg("执行成功",{time:1000},function(){
								parentForm.submit();
								top.layer.closeAll('iframe');
							});
						}else{
							top.layer.msg("执行失败，请联系系统管理员");
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
		$("#orgType").change(function(){
			if($(this).val()=="1"){
				$(".org").find("input").val("");
				$(".org").hide();
			}else{
				$(".org").show();
			}
		});
		$("#year").change(function(){
			$(".org").find("input").val("");
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
	<form:form id="inputForm" modelAttribute="map" action="${ctx}/sys/bus/excuIndex" method="post">
	<form:input path="bean['TYPE']" type="hidden"/>
		<tags:message content="${message}" />
		<div class="partyALL">
		    <h4><span class="tc show pt10 pb10 f16">执行服务</span></h4>
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="Party_dyxx mt15">
                  <tr>
		    		<td>
		    			<label class="name">服务名称：</label>
		    			<div class="textBox">
		    		       <form:input id="name" path="bean['NAME']" class="text" disabled="true"/>
						</div>
		    		</td>
	           	  </tr>
	           	  <tr>
	           	  	<td>
	           	  		<label class="name"><span class="red">*</span>党组织范围：</label>
	           	  		<div class="textBox">
		    		       <form:select id="orgType" path="bean['ORG_TYPE']" class="selectpicker select" required="true">
								<form:option value="1">全部</form:option>
								<form:option value="2">指定</form:option>
							</form:select>
						</div>
	           	  	</td>
	           	  	<td>
	           	  		<label class="name"><span class="red">*</span>年份：</label>
	           	  		<div class="textBox">
		    		       <input id="year" type="text" maxlength="20" class="text Wdate" name="bean['YEAR']" required="true"
							value="${fns:formatDate(map.bean.YEAR,'yyyy')}" onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
						</div>
	           	  	</td>
	           	  </tr>
	           	  <tr class="org" style="display:none">
	           	  	<td colspan="2">
	           	  		<label class="name"><span class="red">*</span>选定党组织：</label>
	           	  		<div class="textBox">
		    		       <tags:treeselect id="orgId" name="bean['ORG_ID']" value="${map.bean.ORG_ID}" required="true"
			            	labelName="bean['ORG_NAME']" labelValue="${map.bean.ORG_NAME}" cssStyle="width:500px"
							title="党组织" url="/party/common/orgUtils/treeData?type=1" allowClear="true"/>
						</div>
	           	  	</td>
	           	  </tr>
			</table>
		</div>
		<div class="space10"></div>
	</form:form>
</body>
</html>