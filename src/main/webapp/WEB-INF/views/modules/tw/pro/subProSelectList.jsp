<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>子产品</title>
    <meta name="decorator" content="default"/>
    <link href="${ctxStatic}/bns/css/dynamicList.css" rel="stylesheet" type="text/css" />
    <script src="${ctxStatic}/bns/js/dynamicList.js" type="text/javascript"></script>
    <script src="${ctxStatic}/list/table.a.js" type="text/javascript"></script>
    <script type="text/javascript">
        var ctx = "${ctx}";
        $(document).ready(function() {
            var typeVal = $("#priProType").val();
            var getPriProData = function (type) {
                $.ajax({
                    url: "${ctx}/tw/pro/priPro/getPriProByType?type="+type,
                    type: "POST",
                    success: function (priProList) {
                            $("#priProId").find("option[value!='']").remove();
                            for(var i = 0; i < priProList.length; i++) {// 新增下拉框
                                var priPro = priProList[i];
                                $("#priProId").append("<option value='"+priPro.ID+"'>"+priPro.NAME+"</option>");
                            }
                            $("#priProId").selectpicker("refresh");
                             $("#priProId").selectpicker("val","${map.bean.PRI_PRO_ID}");
                    }
                });
            }
            $("#priProType").change(function(){
                getPriProData($(this).val());
            });
            getPriProData(typeVal);

        });

        function page(){
            $("#searchForm").attr("action","${ctx}/tw/pro/subPro/proSelectList");
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<form:form id="searchForm" modelAttribute="map" action="${ctx}/tw/pro/subPro/proSelectList" method="post">
    <input id="searchCondition" name="bean['searchCondition']" type="hidden" value=""/>
    <div id="SearchBox" class="Search_box pl5 m10 rdu3 jepor">
        <ul>
            <li class="fl pl5 pr5">
                <span class="show">分类</span>
                <form:select id="priProType" path="bean['TYPE']" class="selectpicker">
                    <form:options items="${fns:getDictList('T_TW_PRO_TYPE')}" itemLabel="label"  itemValue="value" htmlEscape="false" />
                </form:select>
            </li>
            <li class="fl pl5 pr5">
                <span class="show">主产品</span>
                <form:select id="priProId" path="bean['PRI_PRO_ID']" class="selectpicker" data-live-search="true">
                    <form:option value=""></form:option>
                </form:select>
            </li>
            <li class="fl pl5 pr5">
                <span class="show">产品名称</span>
                <form:input allowReset="true" path="bean['PRO_NAME']" htmlEscape="false" maxlength="50" class="text"
                            type="text" placeholder="产品名称"/>
            </li>
        </ul>
        <div class="frBtn jepoa">
            <span class="fl"><a href="javascript:void(0);" class="rdu3 btnCX tc dib" onclick="return page();"><i class="iconfont f14 mr5">&#xe686;</i>查询</a></span>
            <span class=" fl ml5">
                         <a href="javascript:void(0);" title="刷新" class="rdu3  tc dib btn_line" onclick="resetSearchForm($('#searchForm'))"><i class="iconfont f14 mr5">&#xe60b;</i>重置</a>
                       </span>
        </div>
        <div class="clear"></div>
    </div>
    <div class="TableBox MainContent BoderAndBg pt6 pb6 m10 rdu3" style="border: 0px;">
        <div class="Table_box jepor" >
            <div class="fl jew25 sheet01 mb10" style="width:30px;">
                <table id="headTable" border="0" cellspacing="0" cellpadding="0" class="table01 jew100">
                    <thead>
                    <tr class="title">
                        <td width="10"><input type="checkbox" id="tableHead"></td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="bean">
                        <tr>
                            <td><input name="rowSelect" type="checkbox" value="${bean.ID}" data-pro_name="${bean.SERIAL}"
                                       data-pro_std="${bean.pro_std}" data-unit="${fns:getDictLabel(bean.UNIT,'T_TW_UNIT',bean.UNIT)}" data-num="${bean.NUM}"
                                       data-std_price="${bean.std_price}" data-cost_price="${bean.COST_PRICE}" data-remark="${bean.REMARK}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="fr jew75 sheet02 mb10" style="overflow-x:auto;border: 0px;">
                <table id="contentTable" border="0" cellspacing="0" cellpadding="0" class="dyTable table02 jew100" initShowNum="8" style="border-right: solid 1px #dee3ec;">
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
                    <c:forEach items="${list}" var="bean">
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
            <div class="clear"></div>
        </div>
</form:form>
</body>
</html>