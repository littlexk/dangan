<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>

    <title>订单管理</title>
    <meta name="decorator" content="default" />
    <script src="${ctxStatic}/table/table.editor.js" type="text/javascript"></script>
    <style type="text/css">
        .input-append input {
            margin-bottom: 10px;
        }
        .Party_dyxx .name {
            width:80px;
        }
        .Party_dyxx .text {
            width: 120px;
        }
        #orderDetailTbody td{
            padding:0px 5px;
        }
        #orderDetailTbody td input{
            border: 0px;
            margin: 0px;
        }
    </style>
    <script type="text/javascript">
        var parentForm = null;
        var detailTemplate = "<tr><td><input name='rowSelect'' type='checkbox' value='{ID}'/></td><td widgetType='proSelect' name='pro_name'>{PRO_NAME}</td>" +
            "<td widgetType='text' name='pro_std'>{PRO_STD}</td><td widgetType='combobox' selectId='unitSelect' name='unit'>{UNIT}</td>" +
            "<td  name='num' widgetType='text'>{NUM}</td><td widgetType='text'  name='unit_money'>{UNIT_MONEY}</td>" +
            "<td  name='money'>{MONEY}</td><td name='remark' widgetType='text'>{REMARK}</td></tr>";
        var nullVal = {PRO_NAME:"",PRO_STD:"",UNIT:"",NUM:"",UNIT_MONEY:"",MONEY:"",REMARK:""};
        function editor(){
            $("#orderDetailTbody").VanTableEditor({
                editorComplete:function(cell){
                    var unitMoney  = cell.parent().find("[name='unit_money']").html();
                    var num  = cell.parent().find("[name='num']").html();
                    var money ="";
                    if(unitMoney && num){
                        money = numberUtils.floatMul(unitMoney,num);
                    }
                    cell.parent().find("[name='money']").html(money);
                    $("#totalMoney").html(calMoneyTotal());
                    moneyChange();
                }
            });
        }
        function calMoneyTotal(){
            var totalMoney = 0;
            $("#orderDetailTbody > tr > td[name='money']").each(function(){
               totalMoney =  numberUtils.floatAdd(totalMoney,$(this).html())
            });
            return numberUtils.toDecimal2(totalMoney);
        }
        $(document).ready(function() {
            var orderId = $("#orderId").val();
            orderId ="1";
            if(orderId){
                $.ajax({
                    url: "${ctx}/tw/order/orderManage/getDetailByOrderId",
                    type: "POST",
                    data: {orderId:orderId},
                    success: function (orderDetail) {
                        if(orderDetail){
                          var rowNum = orderDetail.length;
                            for(var i =0;i<rowNum;i++){
                                var od = orderDetail[i];
                                var html =  detailTemplate.format({ID:od.ID,PRO_NAME:od.PRO_NAME,PRO_STD:od.PRO_STD,UNIT:od.UNIT,NUM:od.NUM,UNIT_MONEY:od.UNIT_MONEY,MONEY:od.MONEY,REMARK:od.REMARK});
                                $("#orderDetailTbody").append(html);
                            }
                              for(var i =0;i<5-rowNum;i++){
                                  var html =  detailTemplate.format(nullVal);
                                  $("#orderDetailTbody").append(html);
                              }
                            editor();
                            //初始化总金额
                            $("#totalMoney").html(calMoneyTotal());
                        }
                    }
                });
            }



            if("${opType}"=="view"){
                $("#inputForm input,select,textarea").attr("disabled","true");
                $("#inputForm .input-append a").addClass("disabled");
                parent.$(".layui-layer-btn0").hide();
            }
          //  $("select").attr("data-width","162px");

            $("#inputForm").validate({
                submitHandler: function(form){
                    $(form).ajaxSubmit({
                        type: 'post',
                        url:  $(form).attr("action"),
                        data:[{

                        }],
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
        function addRow() {
            $("#orderDetailTbody").append(detailTemplate.format(nullVal));
            editor();
        }
        function deleteData(){
            $("#orderDetailTbody").find("[name='rowSelect']").each(function() {
                if(this.checked){
                    $(this).closest("tr").remove();
                }
            })
        }
        function moneyChange(){
            var delDecimal = $("#DEL_DECIMAL");
            var payableMoney = "0.00";
            var totalMoney = $("#totalMoney").html();
           if(delDecimal.attr("checked")){
               var zeroMoney  ="0."+totalMoney.replace(/\d+\.(\d*)/,"$1");
               $("#DEL_ZERO_MONEY").val(zeroMoney);//抹零金额
               $("#DEL_ZERO_MONEY").attr("readonly",true);
               payableMoney = numberUtils.floatSub(totalMoney,zeroMoney);
           }else{
               $("#DEL_ZERO_MONEY").val("0.00");
               $("#DEL_ZERO_MONEY").attr("readonly",false);
               payableMoney = totalMoney;
           }
            $("#PAYABLE_MONEY").val(payableMoney);//应收金额
            var paidMoney =$("#PAID_MONEY").val();//已付金额
            $("#UNPAID_MONEY").val(numberUtils.floatSub(payableMoney,paidMoney));//未付金额
        }

        /**
         * 提交表单
         */
        function sub() {
            return $("#inputForm").submit();
        }
    </script>
</head>
<body>
<form:form id="inputForm" modelAttribute="map" action="${ctx}/tw/order/orderManage/save" method="post">
    <form:hidden id="orderId" path="bean['ID']" />
    <tags:message content="${message}" />

    <select id="unitSelect" style="display: none">
        <option value=""></option>
        <c:forEach items="${fns:getDictList('T_TW_UNIT')}" var="unit">
            <option value="${unit.label}">${unit.label}</option>
        </c:forEach>
    </select>


    <div class="">
        <table width="98%" border="0" cellspacing="0" cellpadding="0" class="Party_dyxx mt15" style="margin: 5px 10px;">
                <tr>
                    <td>
                        <label class="name"><span class="red">*</span>订单编号：</label>
                        <div class="textBox">
                                <form:input path="bean['ORDER_NUM']" class="text required" maxlength="32" readonly="true"></form:input>
                        </div>
                    </td>
                    <td colspan="2">
                        <label class="name"><span class="red">*</span>客户名称：</label>
                        <div class="textBox">
                                <form:input path="bean['CUSTOMER']" class="text required" maxlength="200" cssStyle="width: 373px;"></form:input>
                        </div>
                    </td>
                    <td>
                        <label class="name"><span class="red">*</span>联系人：</label>
                        <div class="textBox">
                            <form:input path="bean['CONTACTS']" class="text required" maxlength="200"></form:input>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label class="name"><span class="red">*</span>开单时间：</label>
                        <div class="textBox">
                            <input type="text" maxlength="20" class="text Wdate required" name="bean['BILLING_DATE']" value="${fns:formatDate(map.bean.BILLING_DATE,'yyyy-MM-dd')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
                        </div>
                    </td>
                    <td colspan="2">
                        <label class="name"><span class="red">*</span>地址：</label>
                        <div class="textBox">
                                <form:input path="bean['ADDRESS']" class="text required" maxlength="500" cssStyle="width: 373px;"></form:input>
                        </div>
                    </td>
                    <td>
                        <label class="name"><span class="red">*</span>电话：</label>
                        <div class="textBox">
                            <form:input path="bean['PHONE']" class="text required" maxlength="200"></form:input>
                        </div>
                    </td>
                </tr>
            <tr>
                <td>
                    <label class="name"><span class="red">*</span>交货时间：</label>
                    <div class="textBox">
                        <input type="text" maxlength="20" class="text Wdate required" name="bean['DELIVERY']" value="${fns:formatDate(map.bean.DELIVERY,'yyyy-MM-dd')}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
                    </div>
                </td>
                <td>
                    <label class="name"><span class="red">*</span>交货方式：</label>
                    <div class="textBox">
                    <form:select path="bean['DELIVERY_TYPE']" class="selectpicker select required" data-width="130px">
                        <form:option value="">请选择</form:option>
                        <form:options items="${fns:getDictList('T_TW_DELIVERY_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
                    </form:select>
                    </div>
                </td>
                <td>
                    <label class="name"><span class="red">*</span>开单人：</label>
                    <div class="textBox">
                        <form:input path="bean['BILLING_NAME']" class="text required" maxlength="32"></form:input>
                    </div>
                </td>
                <td>
                    <label class="name"><span class="red">*</span>业务员：</label>
                    <div class="textBox">
                        <form:input path="bean['BUSI_NAME']" class="text required" maxlength="32"></form:input>
                    </div>
                </td>
            </tr>
        </table>

        <div class="TableBox MainContent BoderAndBg pt6 pb6 m10 rdu3" style=" height: 218px;margin-top: 0px;">
            <h4 class="Title_right mb6 jepor ml10 mr10">
	               <span class="btnDiv fr">
	                 <a href="javascript:void(0);" onclick="addRow()" class="rdu3 mr10 acur"><i class="iconfont mr5 f14">&#xe60a;</i>新增</a>
	                 <a href="javascript:void(0);" class="rdu3 mr10" onclick="deleteData()"><i class="iconfont mr5 f14">&#xe618;</i>删除</a>
	               </span>
                <span class="f16"></span>
            </h4>
            <div class="Table_box jepor" >
                <div class="fr jew75 sheet02 mb10" style="overflow-x:auto;width:100%;height: 190px;">
                    <table id="orderDetail" border="0" cellspacing="0" cellpadding="0" style="" class="dyTable table02 jew100" initShowNum="8">
                        <thead>
                        <tr class="title">
                            <td width="10" style="padding-left: 5px;padding-right: 5px;"><input type="checkbox" id="tableHead"></td>
                            <td class="DynamicTd" width="20%">产品</td>
                            <td class="DynamicTd"  width="15%">规格</td>
                            <td class="DynamicTd" width="10%">单位</td>
                            <td class="DynamicTd" width="10%">数量</td>
                            <td class="DynamicTd" width="10%">单价</td>
                            <td class="DynamicTd" width="15%">金额</td>
                            <td class="DynamicTd" width="20%">备注</td>
                        </tr>
                        </thead>
                        <tbody id="orderDetailTbody">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="Party_dyxx mt15" style="margin: 5px 10px;">
                <tr>
                    <td>
                        <label class="name"><span class="red">*</span>结算方式：</label>
                        <form:select path="bean['SETTLE_TYPE']" class="selectpicker" data-width="115px">
                            <form:option value="">请选择</form:option>
                            <form:options items="${fns:getDictList('T_TW_SETTLE_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
                        </form:select>
                        &nbsp;&nbsp;<input id="DEL_DECIMAL" name="bean['DEL_DECIMAL']" type="checkbox" style="" onchange="moneyChange(this)"/><label for="DEL_DECIMAL">去小数点后面数值</label>
                        <span style="color: red;margin-left: 50px;"><span>￥</span><sapn  id="totalMoney" >0.00</sapn></span>
                    </td>
                </tr>
        </table>
        <hr style="margin: 5px 10px;border-top: 1px solid #dee3ec;" width="98%"/>
        <table width="98%" border="0" cellspacing="0" cellpadding="0" class="Party_dyxx mt15"  style="margin: 5px 10px;">
            <tr style="padding-top: 10px;">
                <td>
                    <label class="name"><span class="red">*</span>应收金额：</label>
                    <form:input id="PAYABLE_MONEY" path="bean['PAYABLE_MONEY']" value="${empty map.bean['PAYABLE_MONEY']?0.00:map.bean['PAYABLE_MONEY']}"
                                class="text number valid required" onchange="moneyChange(this)" maxlength="22" cssStyle="width:100px;"></form:input>
                </td>
                <td>
                    <label class="name"><span class="red">*</span>抹零金额：</label>
                    <form:input id="DEL_ZERO_MONEY" path="bean['DEL_ZERO_MONEY']" value="${empty map.bean['DEL_ZERO_MONEY']?0.00:map.bean['DEL_ZERO_MONEY']}"
                                class="text number valid required" maxlength="22" cssStyle="width:100px;"></form:input>
                </td>
                <td>
                    <label class="name"><span class="red">*</span>已付金额：</label>
                     <form:input id="PAID_MONEY" path="bean['PAID_MONEY']" class="text number valid " value="${empty map.bean['PAID_MONEY']?0.00:map.bean['PAID_MONEY']}"
                                 maxlength="22" onchange="moneyChange(this)"></form:input>
                </td>
                <td>
                    <label class="name"><span class="red">*</span>未付金额：</label>
                    <form:input id="UNPAID_MONEY" path="bean['UNPAID_MONEY']" class="text number valid required" value="${empty map.bean['UNPAID_MONEY']?0.00:map.bean['UNPAID_MONEY']}" maxlength="22"></form:input>
                </td>
            </tr>
            <tr>
                <td colspan="4">
                    <label class="name" >订单备注：</label>
                    <div class="textBox">
                        <form:input path="bean['REMARK']" class="text " maxlength="500" cssStyle="width: 585px;"></form:input>（不打印该信息）
                    </div>
                </td>
            </tr>
        </table>
    </div>
    <div class="space10"></div>

    <div class="layui-layer-btn layui-layer-btn-"><a class="layui-layer-btn0" onclick="sub()">保存</a><a class="layui-layer-btn0">打印</a><a class="layui-layer-btn1">关闭</a></div>
</form:form>
</body>
</html>