<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html style="overflow-x:hidden;overflow-y:auto;">
<head>
	<title>数据选择</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<script type="text/javascript">
	
	$(document).ready(function() {
			var dictSelected = [];
			var	dictNameSelected = [];
			
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
			
			$("#contentTable tbody td:not(.checkboxTd)").click(function(){
				$(this).siblings(".checkboxTd").children().trigger("click");
			});
			
			var isAll = true;
			$("input[name=rowSelect]").each(function(t){
				for (var i=0,len=dictSelected.length; i<len; i++){
					if (dictSelected[i]==$(this).val()){
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
			var allSelectFunction = function(){
				var isAll = true;
				 $("#contentTable tbody :checkbox").each(function(){
					 if(!this.checked){
							isAll = false;
					}
		         });
				return isAll;
			}
			var selectFunction =  function(obj){
				var checked =obj.checked;
				var isExists = false;
				var index = null;
				for (var i=0,len=dictSelected.length; i<len; i++){
					if (dictSelected[i]==$(obj).val()){
						isExists = true;
						index = i;
						break;
					}
				}
				if(checked){
					if(!isExists){
						dictSelected.push($(obj).val());
						dictNameSelected.push(getValueByJson(obj,"name"));
					}
				}else{
					if(isExists){
						remove(dictSelected,index);
						remove(dictNameSelected,index);
					}
				}
			}
			var getValueByJson = function(obj,key){
				var json = $(obj).attr("json");
				var ojson = $.parseJSON(json.replace(new RegExp("'","gm"),"\""));
				return ojson[key];
			}
			if(allSelectFunction()){
				$("#tableHead").attr("checked", true);  
			}
	});
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
		$("#searchForm").attr("action","${ctx}/party/common/highLevelSearch/dictDataList?type=${type}&filter=${filter}");
		$("#searchForm").submit();
    	return false;
    }
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="map" action="${ctx}/party/common/highLevelSearch/dictDataList?type=${type}&filter=${filter}" method="post">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<div id="SearchBox" class="Search_box pl5 m10 rdu3 jepor">
             <ul>
                <li class="fl pl5 pr5">
               	<span class="show">名称</span>
              	<form:input allowReset="true" path="bean['LABEL']" htmlEscape="false" maxlength="50" class="text"
                       type="text" placeholder="名称"/>
                </li>
            <div class="frBtn jepoa">
       		   <span class="fl"><a href="javascript:void(0);" class="rdu3 btnCX tc dib" onclick="return page('${page.pageNo}','${page.pageSize}');"><i
						class="iconfont f14 mr5">&#xe686;</i>查询</a></span>
			    <span class="fl ml5 btnOther"> <a href="javascript:void(0);" title="刷新" class="tc dib frist last btn_line" onclick="resetSearchForm($('#searchForm'))"><i
						class="iconfont f14 mr5">&#xe60b;</i>重置</a>
				</span> 
             <div class="clear"></div>
    		</div>
    		</ul>
       	<div class="clear"></div>
       </div>
	<div class="TableBox MainContent BoderAndBg pb6 m10 rdu3">
		<div class="Table_box mb10 jepor" style="overflow-x:auto;">
		<table id="contentTable" border="0" cellspacing="0" cellpadding="0" class="jew100">
       		<thead>
                <tr class="title">
					<td><input type="checkbox" id="tableHead"></td>
					<td>名称</td>
					<td>说明</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${page.list}" var="bean">
				<tr>
					<td class="checkboxTd" width="15px"><input type="checkbox" name="rowSelect" value="${bean.value}" json="{'name':'${bean.label}'}"></td>
					<td>${bean.label}</td>
					<td>${bean.title}</td>
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