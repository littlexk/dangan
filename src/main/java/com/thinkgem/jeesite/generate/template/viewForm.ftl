<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>${functionName}</title>
    <meta name="decorator" content="default" />
    <style type="text/css">
        .input-append input {
            margin-bottom: 10px;
        }
    </style>
    <script type="text/javascript">
        var parentForm = null;
        $(document).ready(function() {
            if("${r"${opType}"}"=="view"){
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
                            if(result == "${r"${fns:getStandard('OPERATE_SUCCESS')}"}"){
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
<form:form id="inputForm" modelAttribute="map" action="${r"${ctx}"}/${moduleName}/${subModuleName}/${className}/save" method="post">
    <form:hidden path="bean['${priColName}']" />
    <tags:message content="${r"${message}"}" />
    <div class="">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="Party_dyxx mt15">
                <tr>
        <#list colDetails as col>
            <#if col.COLUMN_NAME!=priColName>
                    <td>
                        <label class="name"><#if col.NULLABLE ='N'><span class="red">*</span></#if>${col.COMMENTS}：</label>
                        <div class="textBox">
                            <#if col.DATA_TYPE='DATE'>
                                <input type="text" maxlength="20" class="text Wdate <#if col.NULLABLE ='N'>required</#if>" name="bean['${col.COLUMN_NAME}']" value="${r"${fns:formatDate(map.bean."}${col.COLUMN_NAME}${r",'yyyy-MM-dd')}"}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
                                <#elseif col.DATA_TYPE='NUMBER'>
                                <form:input path="bean['${col.COLUMN_NAME}']" class="text number valid <#if col.NULLABLE ='N'>required</#if>" maxlength="${col.DATA_LENGTH}"></form:input>
                                <#else>
                                <form:input path="bean['${col.COLUMN_NAME}']" class="text <#if col.NULLABLE ='N'>required</#if>" maxlength="${col.DATA_LENGTH}"></form:input>
                            </#if>
                        </div>
                    </td>
            </#if>
            <#if col_index%2=0 && col_index!=0>
                <#if col_has_next>
                </tr>
                <tr>
                </#if>
            </#if>
        </#list>
                </tr>
        </table>
    </div>
    <div class="space10"></div>
</form:form>
</body>
</html>