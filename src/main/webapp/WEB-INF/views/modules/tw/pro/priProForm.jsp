<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>主产品</title>
    <meta name="decorator" content="default" />
    <style type="text/css">
        .input-append input {
            margin-bottom: 10px;
        }
    </style>
    <script type="text/javascript">
        var parentForm = null;
        $(document).ready(function() {
            if("${opType}"=="view"){
                $("#inputForm input,select,textarea").attr("disabled","true");
                $("#inputForm .input-append a").addClass("disabled");
                parent.$(".layui-layer-btn0").hide();
            }
            $("select").attr("data-width","162px");

            $("#inputForm").validate({
                submitHandler: function(form){
                    $(form).ajaxSubmit({
                        type: 'post',
                        url:  $(form).attr("action"),
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
        /**
         * 提交表单
         */
        function sub() {
            return $("#inputForm").submit();
        }
    </script>
</head>
<body>
<form:form id="inputForm" modelAttribute="map" action="${ctx}/tw/pro/priPro/save" method="post">
    <form:hidden path="bean['ID']" />
    <tags:message content="${message}" />
    <div class="">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="Party_dyxx mt15">
                <tr>
                    <td>
                        <label class="name"><span class="red">*</span>名称：</label>
                        <div class="textBox">
                            <form:input path="bean['NAME']" class="text required" maxlength="100" ></form:input>
                        </div>
                    </td>
                     <td>
                    <label class="name"><span class="red">*</span>分类：</label>
                    <form:select id="TYPE" path="bean['TYPE']" class="selectpicker select required">
                        <form:option value="">请选择</form:option>
                        <form:options items="${fns:getDictList('T_TW_PRO_TYPE')}" itemLabel="label" itemValue="value" htmlEscape="false" />
                    </form:select>
                </td>
                </tr>
                <tr>
                    <td>
                        <label class="name"><span class="red">*</span>排序：</label>
                        <div class="textBox">
                            <form:input path="bean['P_SORT']" class="text required" maxlength="22"></form:input>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2"><label class="name">备注：</label>
                        <form:textarea path="bean['REMARK']" class="textarea rdu3" maxlength="500"/>
                    </td>
                </tr>
        </table>
    </div>
    <div class="space10"></div>
</form:form>
</body>
</html>