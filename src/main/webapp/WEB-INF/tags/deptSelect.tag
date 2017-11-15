<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="隐藏域名称（ID）"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="隐藏域值（ID）"%>
<%@ attribute name="orgName" type="java.lang.String" required="false" description="隐藏域名称（ID）"%>
<%@ attribute name="orgValue" type="java.lang.String" required="true" description="隐藏域值（ID）"%>
<%@ attribute name="labelName" type="java.lang.String" required="true" description="输入框名称（Name）"%>
<%@ attribute name="labelValue" type="java.lang.String" required="true" description="输入框值（Name）"%>
<%@ attribute name="title" type="java.lang.String" required="true" description="选择框标题"%>
<%@ attribute name="checked" type="java.lang.Boolean" required="false" description="是否显示复选框"%>
<%@ attribute name="disabled" type="java.lang.String" required="false" description="是否限制选择，如果限制，设置为disabled"%>
<%@ attribute name="cssClass" type="java.lang.String" required="false" description="css样式"%>
<%@ attribute name="cssStyle" type="java.lang.String" required="false" description="css样式"%>
<%@ attribute name="roleId" type="java.lang.String" required="false" description="角色id"%>
<%@ attribute name="dataScope" type="java.lang.String" required="false" description="查看数据范围(人事处＝2，学院＝3)"%>
<%@ attribute name="required" type="java.lang.String" required="false" description="是否必填"%>

<div class="input-append">
	<input id="${id}Id" name="${name}" class="${cssClass}" type="hidden" value="${value}"${disabled eq 'true' ? ' disabled=\'disabled\'' : ''}/>
	<input id="${id}Org" name="${orgName}" class="${cssClass}" type="hidden" value="${orgValue}"${disabled eq 'true' ? ' disabled=\'disabled\'' : ''}/>
	<input id="${id}Name" name="${labelName}" readonly="readonly" type="text" value="${labelValue}" maxlength="4000"${disabled eq "true"? " disabled=\"disabled\"":""}" ${required eq 'true'?' required=\'true\'':''}
		class="${cssClass}" style="${cssStyle}"/>
	<a id="${id}Button" href="javascript:" class="btn${disabled eq 'true' ? ' disabled' : ''}">
    		<i class="icon-search"></i>
    </a>&nbsp;&nbsp;
</div>
<script type="text/javascript">
	var deptSelected = [];
	var deptNameSelected = [];
	var ids = "${value}";
	var names = "${labelValue}";
	if(ids!="" && names!=""){
		deptSelected = ids.split(",");
		deptNameSelected = names.split(",");
	}
	function getValueByJson(obj,key){
		var json = $(obj).attr("json");
		var ojson = $.parseJSON(json.replace(new RegExp("'","gm"),"\""));
		return ojson[key];
	}
	$("#${id}Button").click(function(){
		var orgId = $("#${id}Org").val();
		// 是否限制选择，如果限制，设置为disabled
		if ($("#${id}Id").attr("disabled")){
			return true;
		}
		// 正常打开	
		var options = {
				title:	"选择${title}",
				content:	"${ctx}/party/common/orgUtils/deptSelectList?orgId="+orgId,
				area:	["700px", "540px"],
				btn:	["确定","清除","关闭"],
				success: function(layero,index){
					$(layero).find("iframe")[0].contentWindow.deptSelected=deptSelected;
					$(layero).find("iframe")[0].contentWindow.deptNameSelected=deptNameSelected;
					$(layero).find("iframe")[0].contentWindow.setSelect();
				},
				yes: function(index, layero){
					var value = [], name = [];
					var s = $(layero).find("iframe")[0].contentWindow.$("#contentTable tbody :checkbox:checked");
					$.each(s, function(i, v){
						value.push($(v).val());
						name.push(getValueByJson($(v),"name"));
					});
					if(value.length>1){
						top.layer.msg("只能选择一行记录！");
						return false;
					}
					$("#${id}Id").val(value);
					$("#${id}Name").val(name);
					$("#${id}Id").change();
					$("#${id}Name").keyup();
					deptSelected = value;
					deptNameSelected = name;
					top.layer.close(index);
				},
				btn2: function(index, layero){
					$("#${id}Id").val("");
					$("#${id}Name").val("");
					$("#${id}Id").change();
					$("#${id}Name").keyup();
					deptSelected = [];
					deptNameSelected = [];
					top.layer.close(index);
				}
			};
			top.windowOpen(options);
		return false;//防止IE9，IE10点击所在单位选择框触发onbeforeunload事件弹出是否要离开提示
	});
</script>
