<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>

<link rel="stylesheet" href="${ctxStatic}/bns/css/user_icon/demo.css">
<link rel="stylesheet" href="${ctxStatic}/bns/css/user_icon/iconfont.css">

<%@ attribute name="id" type="java.lang.String" required="true" description="编号" %>
<%@ attribute name="name" type="java.lang.String" required="true" description="输入框名称" %>
<%@ attribute name="value" type="java.lang.String" required="true" description="输入框值" %>
<a id="${id}Button" href="javascript:" class="btn fl mr10">选择</a>
<div id="iconDiv" class="fl pt5"></div>

<input id="${id}" name="${name}" type="hidden" value="${value}"/>
<script type="text/javascript">
    $(document).ready(function () {
        var value = $("#${id}").val();
        createIcon(value);
    });

    $("#${id}Button").click(function (){
    windowOpen({
        title: "选择图标",
        content: "${ctx}/tag/iconselect?value=" + $("#${id}").val(),
        btn: ['确定', '清除'],
        offset:'auto',
        area: ["300px", "400px"],
        yes: function (index, layero) {
            var icon = $(layero).find("iframe")[0].contentWindow.$("#icon").val();
            $("#${id}").val(icon);
            createIcon(icon);
            //layer.close(index);
            closeLayer();
        }, btn2: function () {
            $("#${id}").val("");
            createIcon("");
        }
        , cancel: function () {

        },
        maxmin: false
    });
    });

    /**
     * 生成图标
     * @param value
     */
    function createIcon(value) {
        $("#iconDiv").empty();
        var iconId = "#${id}Icon";
        var labelId = "#${id}IconLabel";
        var icon = '';
        var label = '';
        if (value) {
            icon = $("<i id='" + iconId + "' class='icon iconfont'>&#" + value + ";</i>&nbsp;");
            label = $("<label id='" + labelId + "'></label>");
        } else {
            icon = $("<i id='" + iconId + "' class='hide'></i>&nbsp;");
            label = $("<label id='" + labelId + "'>无&nbsp;</label>");
        }
        $("#iconDiv").append(icon).append(label);
    }

    function closeLayer(){
        layer.closeAll();
    }
</script>