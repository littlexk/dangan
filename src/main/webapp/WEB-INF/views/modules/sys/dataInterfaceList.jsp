<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html style="overflow-x:hidden;overflow-y:auto;">
<head>
	<title>接口服务</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/dialog.jsp" %>
	<script type="text/javascript">
	$(document).ready(function() {
		
	});
	function getSelectedData(){
		var data=[];
		$("#contentTable tbody :checkbox").each(function(){
			if(this.checked){
					var type = $(this).val();
					var status = $(this).attr("status");
					data.push({
						TYPE:type,
						STATUS:status
					})
				}
		});
		return data;
	};
	function openData(opType){
		var type = "";
		if(opType=="edit"){
			var data = getSelectedData();
			if(data.length>1){
				top.layer.msg("只能选择一条记录");
				return false;
			}else if(data.length==0){
				top.layer.msg("请选择一条记录");
				return false;
			}
			type = data[0].TYPE;
		}
		var options = {
				title:	"接口服务编辑",
				content: "${ctx}/sys/dataInterface/dataInterfaceForm?type="+type+"&opType="+opType,
				area:	["800px", "445px"],
				maxmin: true,
				btn:	["保存","关闭"],
				success:function(layero,index){
					var iframeHtml = parent.layer.getChildFrame('html', index);
					$(iframeHtml).css("overflow-y","hidden");
					$(layero).find("iframe")[0].contentWindow.parentForm=$("#searchForm");
				},
				yes: function(index, layero){
					$(layero).find("iframe")[0].contentWindow.sub();
				},
				btn2: function(index, layero){
					layer.close(index);
				}
			};
		top.windowOpen(options);
	}
	function synDo(status){
		var data = getSelectedData();
		if(data.length>1){
			top.layer.msg("只能选择一条记录");
			return false;
		}else if(data.length==0){
			top.layer.msg("请选择一条记录");
			return false;
		}
		if(status==data[0].STATUS){
			var mess = "该服务已";
			if(status=="on"){
				mess += "开启自动执行！"
			}else{
				mess += "关闭自动执行！"
			}
			top.layer.msg(mess);
			return false;
		}
		var type = data[0].TYPE;
		var mess = "确定";
		if(status=="on"){
			mess += "开启自动执行?"
		}else{
			mess += "关闭自动执行?"
		}
		top.layer.confirm(mess, {icon:3, title:'提示'}, function(index){
			window.location="${ctx}/sys/dataInterface/dataInterfaceTurn?type="+type+"&status="+status;
			top.layer.close(index);
		});
	}
	function excuDo(type,href){
		if(isNotNull(href)){
			var options = {
				title:	"执行服务",
				content:	"${ctx}"+href+"?type="+type,
				area:	["768px", "350px"],
				btn:	["执行","关闭"],
				maxmin: true,
				success:function(layero,index){
					$(layero).find("iframe")[0].contentWindow.parentForm = $("#searchForm");
				},
				yes: function(index, layero){
					$(layero).find("iframe")[0].contentWindow.sub();
				},
				btn2: function(index, layero){
					top.layer.close(index);
				}
			};
			top.windowOpen(options);
		}else{
			top.layer.confirm("确定执行此服务？", {icon:3, title:'提示'}, function(index){
				top.layer.close(index);
				$.ajax({  
			        url: "${ctx}/sys/dataInterface/excuDo",  
			        type: "POST",  
			        data: {type:type},
			        success: function (result) {  
			        	if(result== "${fns:getStandard('OPERATE_SUCCESS')}"){
			        		top.layer.msg("执行成功",{time: 1000}, function(){
			        			$("#searchForm").submit();       			
		        			});
			        	}else{
			        		top.layer.msg("执行失败，请联系管理员");
			        	}
			        },  
			        error: function (data) { 
				        	top.layer.msg("操作失败");
			        	}  
			    });
			});
		}
	}
	function page(n,s){
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").attr("action","${ctx}/sys/dataInterface/dataInterfaceList");
		$("#searchForm").submit();  
    	return false;
    }
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="map" action="${ctx}/sys/dataInterface/dataInterfaceList" method="post">
	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
	<tags:message content="${message}"/>
	<div id="SearchBox" class="Search_box pl5 m10 rdu3 jepor">
      <ul>
		<li class="fl pl5 pr5"><span class="show">服务名称</span> <form:input allowReset="true" path="bean['NAME']" htmlEscape="false" maxlength="50" class="text" type="text" /></li>
		<li class="fl pl5 pr5"><span class="show">服务类型</span> <form:input allowReset="true" path="bean['TYPE']" htmlEscape="false" maxlength="50" class="text" type="text" /></li>
        </ul>
         <div class="frBtn jepoa">
  		   <span class="fl"><a href="javascript:void(0);" class="rdu3 btnCX tc dib" onclick="return page('${page.pageNo}','${page.pageSize}');"><i class="iconfont f14 mr5">&#xe686;</i>查询</a></span>
             <span class="fl ml5">
               <a href="javascript:void(0);" title="刷新" class="rdu3 btn_line tc dib" onclick="resetSearchForm($('#searchForm'))"><i class="iconfont f14 mr5">&#xe60b;</i>重置</a>
             </span>
		</div>
		<div class="clear"></div>
    </div>
	<div class="TableBox MainContent BoderAndBg pt6 pb6 m10 rdu3">
      <!--标题-->
      <h4 class="Title_right mb6 jepor ml10 mr10">
       <span class="btnDiv fr">
       	 <!-- <a href="javascript:void(0);" onclick="synDo('on');" class="first"><i class="iconfont mr5 f14">&#xe609;</i>开启自动执行</a>
         <a href="javascript:void(0);" onclick="synDo('off');" class="last mr5"><i class="iconfont mr5 f14">&#xe615;</i>关闭自动执行</a> -->
         <a href="javascript:void(0);" onclick="openData();" class="first acur"><i class="iconfont mr5 f14">&#xe60a;</i>新增</a>
         <a href="javascript:void(0);"  onclick="openData('edit')" class=""><i class="iconfont mr5 f14">&#xe61b;</i>编辑</a>
         <a href="javascript:void(0);" class="last mr5" onclick="deleteData()"><i class="iconfont mr5 f14">&#xe618;</i>删除</a>	
       </span>
       <span class="f16">接口服务列表</span>
      </h4>
      <!--标题 End-->
      <!--列表-->
      <div class="Table_box mb10 jepor" style="overflow-x:auto;">
              <table id="contentTable" border="0" cellspacing="0" cellpadding="0" class="jew100" initShowNum="8" >
              	<thead>
                   <tr class="title">
					<td width="15"><input type="checkbox" id="tableHead"></td>
					<td width="30">序号</td>
					<td>操作</td>
					<td>服务名</td>
					<!-- <td>自动执行</td> -->
					<td>最近一次更新时间</td>
					<td>更新信息</td>
					<td>操作人</td>
				</tr>
			</thead>
			<tbody>
			<c:set var="curNum" value="1"/> 
			<c:forEach items="${page.list}" var="bean">
				<tr>
					<td><input type="checkbox" name="rowSelect" value="${bean.TYPE}" status="${bean.STATUS}"></td>
					<td>${curNum}</td>
					<td><a href="javascript:void(0)" class="blue" onclick="excuDo('${bean.TYPE}','${bean.HREF}')">执行</a></td>
					<td>${bean.NAME}</td>
					<%-- <td>${bean.STATUS eq 'on'?'开启':'关闭'}</td> --%>
					<td>${fns:formatDate(bean.LAST_TIME,'yyyy-MM-dd HH:mm:ss')}</td>
					<td>${bean.REMARKS}</td>
					<td>${bean.OPERATOR}</td>
				</tr>
			<c:set var="curNum" value="${curNum+1}"/>
			</c:forEach>
			</tbody>
		</table>
		</div>
		<!--翻页-->
		<div class="Pages ">${page}</div>
		<!--翻页 End-->
	    <div class="clear"></div>
    </div>
    </form:form>
</body>