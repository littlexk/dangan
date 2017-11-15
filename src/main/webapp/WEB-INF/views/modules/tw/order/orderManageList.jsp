<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>订单管理</title>
    <meta name="decorator" content="default"/>
    <%@include file="/WEB-INF/views/include/dialog.jsp" %>
    <link href="${ctxStatic}/bns/css/dynamicList.css" rel="stylesheet" type="text/css" />
    <script src="${ctxStatic}/bns/js/dynamicList.js" type="text/javascript"></script>
    <script type="text/javascript">
        var ctx = "${ctx}";
        $(document).ready(function() {
        });
        function add(){
            var options = {
                title:	"新增",
                content:	"${ctx}/tw/order/orderManage/form",
                area:	["1000px", "625px"],
                //btn:	["保存","关闭"],
                maxmin: true,
                success:function(layero,index){
                    $(layero).find("iframe")[0].contentWindow.parentForm = $("#searchForm");
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
            var options = {
                title:	"编辑",
                content:	"${ctx}/tw/order/orderManage/form?id="+numId,
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
                        url: "${ctx}/tw/order/orderManage/delete",
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
            $("#searchForm").attr("action","${ctx}/tw/order/orderManage/list");
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<form:form id="searchForm" modelAttribute="map" action="${ctx}/tw/order/orderManage/list" method="post">
    <input id="searchCondition" name="bean['searchCondition']" type="hidden" value=""/>
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <div id="SearchBox" class="Search_box pl5 m10 rdu3 jepor">
        <ul>
            <li class="fl pl5 pr5">
                <span class="show">订单编号</span>
                <form:input allowReset="true" path="bean['ORDER_NUM']" htmlEscape="false" maxlength="50" class="text"
                            type="text" placeholder="订单编号"/>
            </li>
            <li class="fl pl5 pr5">
                <span class="show">客户名称</span>
                <form:input allowReset="true" path="bean['客户名称']" htmlEscape="false" maxlength="50" class="text"
                            type="text" placeholder="客户名称"/>
            </li>
            <li class="fl pl5 pr5" style="width:230px;">
                <span class="show">开单时间</span>
                <input type="text" maxlength="20" style="width:90px" class="text Wdate" name="bean['BILLING_DATE_S']" value="${fns:formatDate(map.bean.BILLING_DATE_S,'yyyy-MM-dd')}"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />&nbsp;-&nbsp;
                <input type="text" maxlength="20" style="width:90px" class="text Wdate" name="bean['BILLING_DATE_E']" value="${fns:formatDate(map.bean.BILLING_DATE_E,'yyyy-MM-dd')}"
                       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
            </li>
            <li class="fl pl5 pr5">
                <span class="show">状态</span>
                <form:select path="bean['STATUS']" class="selectpicker">
                    <form:option value=""></form:option>
                    <form:options items="${fns:getDictList('T_TW_ORDER_STATUS')}" itemLabel="label" itemValue="value" htmlEscape="false" />
                </form:select>
            </li>
            <li class="fl pl5 pr5" >
                <span class="show">业务员</span>
                <form:input allowReset="true" path="bean['BUSI_NAME']" htmlEscape="false" maxlength="50" class="text"
                            type="text" placeholder="业务员"/>
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
            <span class="f16">订单管理列表</span>
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
                        <td class="DynamicTd">订单编号</td>
                        <td class="DynamicTd">客户名称</td>
                        <td class="DynamicTd">开单时间</td>
                        <td class="DynamicTd">交货时间</td>
                        <td class="DynamicTd">订单状态</td>
                        <td class="DynamicTd">应收金额</td>
                        <td class="DynamicTd">已付金额</td>
                        <td class="DynamicTd">未付金额</td>
                        <td class="DynamicTd">结算方式</td>
                        <td class="DynamicTd">业务员</td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${page.list}" var="bean">
                        <tr>
                            <td>${bean.ORDER_NUM}</td>
                            <td>${bean.CUSTOMER}</td>
                            <td>${fns:formatDate(bean.BILLING_DATE,'yyyy-MM-dd')}</td>
                            <td>${fns:formatDate(bean.DELIVERY,'yyyy-MM-dd')}</td>
                            <td>${fns:getDictLabel(bean.STATUS,'T_TW_ORDER_STATUS','')}</td>
                            <td>${bean.PAYABLE_MONEY}</td>
                            <td>${bean.PAID_MONEY}</td>
                            <td>${bean.UNPAID_MONEY}</td>
                            <td>${fns:getDictLabel(bean.SETTLE_TYPE,'T_TW_SETTLE_TYPE','')}</td>
                            <td>${bean.BUSI_NAME}</td>
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