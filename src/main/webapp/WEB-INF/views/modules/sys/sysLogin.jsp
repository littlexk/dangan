<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${fns:getConfig('productName')} 登录</title>

    <script src="${ctxStatic}/jquery/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jquery/jquery.cookies.1.4.0.js" type="text/javascript"></script>
    <script src="${ctxStatic}/jquery/jquery.form.js" type="text/javascript"></script>
    <link href="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.css" type="text/css" rel="stylesheet" />
    <script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.min.js" type="text/javascript"></script>

    <link href="${ctxStatic}/bns/js/layer/skin/default/layer.css" type="text/css" rel="stylesheet" />
    <script src="${ctxStatic}/bns/js/layer/layer.js" type="text/javascript"></script>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta name="Author" content="深圳市信息技术有限公司">
	<meta content="深圳市信息技术有限公司 www.jr.com" name="design">
	<meta name="Author" content="深圳市信息技术有限公司制作" />
	<link href="${ctxStatic}/bns/css/jeui.css" rel="stylesheet" type="text/css" />
	<link href="${ctxStatic}/bns/css/css_bns.css" rel="stylesheet" type="text/css" />
	<link href="${ctxStatic}/bns/css/css_blue.css" rel="stylesheet" type="text/css" />
	<link href="${ctxStatic}/bns/css/iconFont/iconfont.css" rel="stylesheet">
    <style type="text/css">
        input,button,select,textarea{ outline:none; background:none; border:none; margin:0; padding:0; font-size:12px; font-family:"Microsoft YaHei",Arial; /*-webkit-appearance:none;*//*强制去除表单自带的样式*/ }
    </style>
    <script type="text/javascript">
		$(document).ready(function() {
            $("#loginForm").validate({
                submitHandler: function(form){
                    layer.load(0, {shade: false});
                    form.submit();
                },
                errorContainer: "#loginError",
                errorPlacement: function(error, element) {
                    $("#loginError").text("输入有误，请先更正。");
                }
            });
            $(document).keydown(function(event){
                if(event.keyCode==13){
                    $("#loginForm").submit();
                }
            });

        });
		// 如果在框架中，则跳转刷新上级页面
		if(self.frameElement && self.frameElement.tagName=="IFRAME"){
			parent.location.reload();
		}
        function login(){
            $("#loginForm").submit();
        }
	</script>
</head>
<body style="height:600px;">
<form id="loginForm"  class="form login-form" action="${ctx}/login" method="post"  class="">
<!--Star-->
<div class="Login_box bg-wh rdu5 jepoa pr30 pl30">
	<!--<h4 class="logo tc"><img src="" /></h4>-->
	<ul>
		<li class="pt10 line"><i class="iconfont icon mr10 f18">&#xe7b8;</i><input class="f18 text" name="username" type="text" value="" placeholder="用户名"/></li>
		<li class="pt15 line"><i class="iconfont icon mr10 f18">&#xe644;</i><input class="f18 text" name="password" type="password" value="" placeholder="密码"/></li>
		<li class="pt15 line jepor hide"><i class="iconfont icon mr10 f18">&#xe691;</i><input class="f18 text" name="" type="text" value="请输入右边验证码"/><img src="${ctxStatic}/bns/uploadfiles/Code.png" class="Code jepoa"/></li>
		<li>
            <%String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);%>
            <%String messName =  request.getParameter("username");String messPassword =  request.getParameter("password");%>
			<em class="tips red error <%=error==null?"hide":""%>" id="loginError">
                <i class="iconfont icon mr5 f18">&#xe635;</i>
                <%
                    if("com.thinkgem.jeesite.modules.sys.security.CaptchaException".equalsIgnoreCase(error)){
                        out.print("验证码错误, 请重试.&nbsp;&nbsp;&#12288;");
                    }else if("com.thinkgem.jeesite.modules.sys.security.PrivilegesException".equalsIgnoreCase(error)){
                        out.print("权限不足.");
                    }else if(messName=="" && messPassword==""){
                        out.print("请填写用户名和密码.&nbsp;&nbsp;&#12288;");
                    }else if(messName==""){
                        out.print("请填写用户名.&#12288;&#12288;&#12288;&#12288;&#12288;");
                    }else if(messPassword==""){
                        out.print("请填写密码.&#12288;&#12288;&#12288;&#12288;&#12288;&#12288;");
                    }else{
                        out.print("用户或密码错误, 请重试.");
                    }
                %>
            </em>
			<span class="fr g6 f14 hide"><input name="" class="mr5" type="checkbox" value="" />记住账号</span>
		</li>
		<li class="pt10"><a href="javascript:void(0);" class="btn show white f24 rdu5 tc" onclick="login()">登录</a></li>
	</ul>
</div>
</form>
<div class="Login_copy white tc jepoa">Copyright © 2013-2015 jr.com. All Rights Reserved</div>
<!--End-->


</body>
</html>
