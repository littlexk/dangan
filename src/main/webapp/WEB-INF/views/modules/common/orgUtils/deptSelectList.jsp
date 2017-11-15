<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html style="overflow-x:hidden;overflow-y:auto;">
<head>
	<title>数据选择</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<script type="text/javascript">
	var deptSelected = [];
	var deptNameSelected = [];
	$(document).ready(function() {
			$("#tableHead").click(function () {
				if($(this).attr("checked")){
		            $("#contentTable tbody :checkbox").attr("checked", true);  
		            $("#contentTable tbody :checkbox").each(function(){
		            	selectFunction(this);
		            });
				}else{
					$("#contentTable tbody :checkbox").attr("checked", false);  
					$("#contentTable tbody :checkbox").each(function(){
		            	selectFunction(this);
		            });
				}
	        });  
			var isAll = true;
			setSelect();
			if(allSelectFunction()){
				$("#tableHead").attr("checked", true);  
			}
	});
	function setSelect(){
		$("input[name=rowSelect]").each(function(t){
			for (var i=0,len=deptSelected.length; i<len; i++){
				if (deptSelected[i]==$(this).val()){
					this.checked = true;
				}
			}
			$(this).click(function(){
				selectFunction(this);
				if(allSelectFunction()){
					$("#tableHead").attr("checked", true);  
				}else{
					$("#tableHead").attr("checked", false);  
				}
			});
		});
	}
	function selectFunction(obj){
		var checked =obj.checked;
		var isExists = false;
		var index = null;
		for (var i=0,len=deptSelected.length; i<len; i++){
			if (deptSelected[i]==$(obj).val()){
				isExists = true;
				index = i;
				break;
			}
		}
		if(checked){
			if(!isExists){
				deptSelected.push($(obj).val());
				deptNameSelected.push(getValueByJson(obj,"name"));
			}
		}else{
			if(isExists){
				remove(deptSelected,index);
				remove(deptNameSelected,index);
			}
		}
	}
	function getValueByJson(obj,key){
		var json = $(obj).attr("json");
		var ojson = $.parseJSON(json.replace(new RegExp("'","gm"),"\""));
		return ojson[key];
	}
	function allSelectFunction(){
		var isAll = true;
		 $("#contentTable tbody :checkbox").each(function(){
			 if(!this.checked){
					isAll = false;
			}
         });
		return isAll;
	}
	function remove(a,dx){
		if(dx>a.length){
			return false;
	  	 }
		for(var i=0,n=0,len=a.length;i<len;i++){
			if(a[i]!=a[dx]){
				a[n++]=a[i]
			}
		}
			a.length-=1;
	}
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/party/common/orgUtils/deptSelectList?orgId=${orgId}");
		$("#searchForm").submit();
    	return false;
    }
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="map" action="${ctx}/party/common/orgUtils/deptSelectList?orgId=${orgId}" method="post">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
	<div class="TableBox MainContent BoderAndBg pb6 m10 rdu3">
		<div class="Table_box mb10 jepor" style="overflow-x:auto;">
		<table id="contentTable" border="0" cellspacing="0" cellpadding="0" class="jew100">
       		<thead>
                <tr class="title">
					<td><input type="checkbox" id="tableHead"></td>
					<td>单位编号</td>
					<td>单位名称</td>
					<td>单位隶属关系</td>
					<td>单位属性</td>
					<td>单位所属行业</td>
					<td>经济类型</td>
					<td>中介组织类型</td>
					<td>企业类型</td>
					<td>企业规模</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${page.list}" var="bean">
				<tr>
					<td><input type="checkbox" name="rowSelect" value="${bean.NUM_ID}" json="{'name':'${bean.C_NAME}'}"></td>
					<td>${bean.C_NO}</td>
					<td>${bean.C_NAME}</td>
					<td>${fns:getDictLabel(bean.C_AFFILIATION,'DWLS',bean.C_AFFILIATION)}</td>
					<td>${fns:getDictLabel(bean.C_PROPERTY,'DWXZ',bean.C_PROPERTY)}</td>
					<td>${fns:getDictLabel(bean.INDUSTRY,'SSHY',bean.INDUSTRY)}</td>
					<td>${fns:getDictLabel(bean.ECONOMY_TYPE,'JJLX',bean.ECONOMY_TYPE)}</td>
					<td>${fns:getDictLabel(bean.AGENT_TYPE,'ZJLX',bean.AGENT_TYPE)}</td>
					<td>${fns:getDictLabel(bean.ENTERPRISE_TYPE,'QYLX',bean.ENTERPRISE_TYPE)}</td>
					<td>${fns:getDictLabel(bean.ENTERPRISE_SIZE,'QYGM',bean.ENTERPRISE_SIZE)}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</div>
	    <!--分页-->
	    <div class="Pages">${page}</div>
	    <!--分页 结束-->
	    <div class="clear"></div>
    </div>
    </form:form>
</body>