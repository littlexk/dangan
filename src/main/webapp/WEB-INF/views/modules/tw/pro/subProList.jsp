<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>子产品</title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/dialog.jsp" %>
    <link href="${ctxStatic}/bns/css/dynamicList.css" rel="stylesheet" type="text/css" />
    <script src="${ctxStatic}/bns/js/dynamicList.js" type="text/javascript"></script>
    <script type="text/javascript">
        var ctx = "${ctx}";
        $(document).ready(function() {
        });
        function add(){
            var priProId = $("#PRI_PRO_ID").val();
            var options = {
                title:	"新增",
                content:	"${ctx}/tw/pro/subPro/form?priProId="+priProId,
                area:	["750px", "545px"],
                btn:	["保存","关闭"],
                maxmin: true,
                success:function(layero,index){
                    $(layero).find("iframe")[0].contentWindow.parentForm = $("#searchForm");
                },
                yes: function(index, layero){
                    $(layero).find("iframe")[0].contentWindow.sub();
                },
                btn2: function(index, layero){
                    layer.close(index);
                    $("#searchForm").submit();
                }
            };
            top.windowOpen(options);
        };
        function getSelectedData(){
            var data=[];
            $("#headTable tbody :checkbox").each(function(){
                if(this.checked){
                    var id = $(this).val();
                    data.push({
						ID:id
                    })
                }
            });
            return data;
        };
        function get(){
            var data = getSelectedData();
            if(data.length>1){
                top.layer.msg("只能选择一条记录");
                return false;
            }else if(data.length==0){
                top.layer.msg("请选择一条记录");
                return false;
            }
            var numId = data[0].ID;

            var priProId = $("#PRI_PRO_ID").val();
            var options = {
                title:	"编辑",
                content:	"${ctx}/tw/pro/subPro/form?id="+numId+"&priProId="+priProId,
                area:	["750px", "545px"],
                btn:	["保存","关闭"],
                maxmin: true,
                success:function(layero,index){
                    $(layero).find("iframe")[0].contentWindow.parentForm = $("#searchForm");
                    setTitle(empName,orgName);
                },
                yes: function(index, layero){
                    $(layero).find("iframe")[0].contentWindow.sub();
                },
                btn2: function(index, layero){
                    layer.close(index);
                    $("#searchForm").submit();
                }
            };
            top.windowOpen(options);
        };
        function deleteData(){
            var ids = "";
            $("input[type='checkbox'][name='rowSelect']:checked").each(function(){
                ids += $(this).val() + ",";
            });
            if (ids.length == 0){
                top.layer.msg("请至少选择一条记录");
                return ;
            } else {
                ids = ids.substring(0,ids.length-1);
                top.layer.confirm('确定删除所选择的记录?', {icon:3, title:'提示'}, function(index){
                    top.layer.close(index);
                    $.ajax({
                        url: "${ctx}/tw/pro/subPro/delete",
                        type: "POST",
                        data: {ids:ids},
                        success: function (result) {
                            if(result == "${fns:getStandard('OPERATE_SUCCESS')}"){
                                top.layer.msg("删除成功",{time:1000},function(){
                                    $("#searchForm").submit();
                                });
                            }else{
                                top.layer.msg("删除失败，请联系系统管理员");
                            }
                        },
                        error: function (result) {
                            layer.msg("操作失败");
                        }
                    });
                });
            }
        };
        function page(n,s){
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").attr("action","${ctx}/tw/pro/subPro/list");
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<form:form id="searchForm" modelAttribute="map" action="${ctx}/tw/pro/subPro/list" method="post">
    <input id="searchCondition" name="bean['searchCondition']" type="hidden" value=""/>
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

    <input id="PRI_PRO_ID" name="bean['PRI_PRO_ID']" type="hidden" value="${map.bean['PRI_PRO_ID']}" allowReset="false"/>
    <div id="SearchBox" class="Search_box pl5 m10 rdu3 jepor">
        <ul>
            <li class="fl pl5 pr5">
                <span class="show">产品名称</span>
                <form:input allowReset="true" path="bean['PRO_NAME']" htmlEscape="false" maxlength="50" class="text"
                            type="text" placeholder="产品名称"/>
            </li>
        </ul>
        <div class="frBtn jepoa">
            <span class="fl"><a href="javascript:void(0);" class="rdu3 btnCX tc dib" onclick="return page('${page.pageNo}','${page.pageSize}');"><i class="iconfont f14 mr5">&#xe686;</i>查询</a></span>
            <span class=" fl ml5">
                         <a href="javascript:void(0);" title="刷新" class="rdu3  tc dib btn_line" onclick="resetSearchForm($('#searchForm'))"><i class="iconfont f14 mr5">&#xe60b;</i>重置</a>
                       </span>
        </div>
        <div class="clear"></div>
    </div>
    <div class="TableBox MainContent BoderAndBg pt6 pb6 m10 rdu3">
        <h4 class="Title_right mb6 jepor ml10 mr10">
	               <span class="btnDiv fr">
	                 <a href="javascript:void(0);" onclick="add();" class="rdu3 mr10 acur"><i class="iconfont mr5 f14">&#xe60a;</i>新增</a>
	                 <a href="javascript:void(0);"  onclick="get('edit')" class="first"><i class="iconfont mr5 f14">&#xe61b;</i>编辑</a>
	                 <a href="javascript:void(0);" class="last mr5" onclick="deleteData()"><i class="iconfont mr5 f14">&#xe618;</i>删除</a>
	                 <a href="javascript:void(0);" class="first"><i class="iconfont f14">&#xe66c;</i></a>
	  		   		 <a href="javascript:void(0);" class="last" onclick="showList()"><i class="iconfont f14">&#xe90c;</i></a>
	               </span>
            <span class="f16">子产品列表</span>
        </h4>
        <div class="Table_box jepor" >
            <div class="fl jew25 sheet01 mb10" style="width:30px;">
                <table id="headTable" border="0" cellspacing="0" cellpadding="0" class="table01 jew100">
                    <thead>
                    <tr class="title">
                        <td width="10"><input type="checkbox" id="tableHead"></td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${page.list}" var="bean">
                        <tr>
                            <td><input name="rowSelect" type="checkbox" value="${bean.ID}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="fr jew75 sheet02 mb10" style="overflow-x:auto;">
                <table id="contentTable" border="0" cellspacing="0" cellpadding="0" class="dyTable table02 jew100" initShowNum="8">
                    <thead>
                    <tr class="title">
	                            <td class="DynamicTd">编号</td>
	                            <td class="DynamicTd">产品名称</td>
	                            <td class="DynamicTd">产品规格</td>
	                            <td class="DynamicTd">单位</td>
	                            <td class="DynamicTd">数量</td>
	                            <td class="DynamicTd">标准价</td>
	                            <td class="DynamicTd">成本价</td>
	                            <td class="DynamicTd">备注/工艺</td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${page.list}" var="bean">
                        <tr>
											<td>${bean.SERIAL}</td>
											<td>${bean.PRO_NAME}</td>
											<td>${bean.PRO_STD}</td>
											<td>${fns:getDictLabel(bean.UNIT,'T_TW_UNIT',bean.UNIT)}</td>
											<td>${bean.NUM}</td>
											<td>${bean.STD_PRICE}</td>
											<td>${bean.COST_PRICE}</td>
											<td>${bean.REMARK}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="Pages">${page}</div>
            <div class="clear"></div>
        </div>
</form:form>
</body>
</html>