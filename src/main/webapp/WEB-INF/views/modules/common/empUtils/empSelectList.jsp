<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html style="overflow-x:hidden;overflow-y:auto;">
<head>
	<title>数据选择</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<script type="text/javascript">
	var empSelected = [];
	var empNameSelected = [];
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
			for (var i=0,len=empSelected.length; i<len; i++){
				if (empSelected[i]==$(this).val()){
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
		for (var i=0,len=empSelected.length; i<len; i++){
			if (empSelected[i]==$(obj).val()){
				isExists = true;
				index = i;
				break;
			}
		}
		if(checked){
			if(!isExists){
				empSelected.push($(obj).val());
				empNameSelected.push(getValueByJson(obj,"name"));
			}
		}else{
			if(isExists){
				remove(empSelected,index);
				remove(empNameSelected,index);
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
		$("#searchForm").attr("action","${ctx}${empty url?'/party/common/empUtils/empSelectList':url}");
		$("#searchForm").submit();
    	return false;
    }
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="map" action="${ctx}${empty url?'/party/common/empUtils/empSelectList':url}" method="post">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/>
		<!--搜索-->
           <div id="SearchBox" class="Search_box pl5 pt5 jepor BoderAndBg m10">
               <ul>
                  <li class="fl pl5 pr5 jepor">
                   	<span class="show">党组织</span>
                   	<tags:treeselect id="orgId" name="bean['ORG_ID']" value="${map.bean.ORG_ID}" labelName="bean['P_ORG_NAME']" labelValue="${map.bean.P_ORG_NAME}" 
					title="党组织" url="/party/common/orgUtils/treeData?type=1" cssClass="input-small" allowClear="true"/>
                  </li>
                  <li class="fl pl5 pr5">
                   	<span class="show">姓名/人员编号</span>
                	<form:input allowReset="true" path="bean['EMPLOYEE_NAME']" htmlEscape="false" maxlength="50" class="text"
                         type="text" placeholder="姓名/人员编号"/>
                  </li>
	             <li class="fl pl5 pr5">
	              	<span class="show">组织名称</span>
	         			<form:input allowReset="true" path="bean['ORG_NAME']" htmlEscape="false" maxlength="50" class="text"
	                    type="text" placeholder="组织名称"/>
	             </li>
               </ul>
              <div class="frBtn jepoa">
         		   <span class="fl"><a href="javascript:void(0);" class="rdu3 btnCX tc dib" onclick="return page('${page.pageNo}','${page.pageSize}');"><i
						class="iconfont f14 mr5">&#xe686;</i>查询</a></span>
				    <span class="fl ml5 btnOther"> <a href="javascript:void(0);" title="刷新" class="tc dib frist last btn_line" onclick="resetSearchForm($('#searchForm'))"><i
							class="iconfont f14 mr5">&#xe60b;</i>重置</a>
					</span> 
               <div class="clear"></div>
     	</div>
        <div class="clear"></div>
       </div>
	<div class="TableBox MainContent BoderAndBg pb6 m10 rdu3">
		<div class="Table_box mb10 jepor" style="overflow-x:auto;">
		<table id="contentTable" border="0" cellspacing="0" cellpadding="0" class="jew100">
       		<thead>
                <tr class="title">
					<td width="10"><input type="checkbox" id="tableHead"></td>
					<td width="10">序号</td>
					<td>所在党组织</td>
					<td>上级党组织</td>
					<td>姓名</td>
					<td>人员编号</td>
					<td>性别</td>
					<td>个人身份</td>
					<td>入党时间</td>
					<td>申请入党时间</td>
					<td>发展阶段</td>
				</tr>
			</thead>
			<tbody>
			<c:set var="curNum" value="1"/> 
			<c:forEach items="${page.list}" var="bean">
				<tr>
					<td><input type="checkbox" name="rowSelect" value="${bean.EMP_ID}" json="{'name':'${bean.NAME}'}"></td>
					<td>${curNum}</td>
					<td>${bean.ORG_NAME}</td>
					<td>${bean.U_ORG_NAME}</td>
					<td>${bean.NAME}</td>
					<td>${bean.EMPLOYEE_CODE}</td>
					<td>${fns:getDictLabel(bean.SEX,'XB','')}</td>
					<td>${fns:getDictLabel(bean.PERSONAL_INFO,'ZYFL','')}</td>
					<td>${fns:formatDate(bean.JOIN_DATE,'yyyy-MM-dd')}</td>
					<td>${fns:formatDate(bean.APPLY_DATE,'yyyy-MM-dd')}</td>
					<td>${fns:getDictLabel(bean.PHASE,'PHASE','')}</td>
				</tr>
			<c:set var="curNum" value="${curNum+1}"/>
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