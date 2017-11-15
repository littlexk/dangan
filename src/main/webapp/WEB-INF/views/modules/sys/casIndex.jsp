<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<HTML>
	<HEAD>
		<title>${fns:getConfig('productName')} 登录</title>
		<meta name="decorator" content="default"/>
	    <link rel="stylesheet" href="${ctxStatic}/common/hg-login.css">
	    
	    <script type="text/javascript">
	    $(document).ready(function() {
			$(".select2-container").css('float', 'none');
			$("#s2id_autogen1,#s2id_autogen3").css('width', '210px');
			$(":input[class='input']").css('width', '195px');
			$("#loginForm").validate({
				rules: {
				},
				messages: {
				},
				errorLabelContainer: "#messageBox",
				errorPlacement: function(error, element) {
					error.appendTo($("#loginError").parent());
				} 
			});
		});
	    function postTypeSelectChange(obj){
	    	var type = $(obj).val();
	    	$("#postType").val(type);
	    }
	 // 如果在框架中，则跳转刷新上级页面
		if(self.frameElement && self.frameElement.tagName=="IFRAME"){
			parent.location.reload();
		}
	    </script>
	</HEAD>
	<BODY class="conten">
	<DIV class="middle">
	<DIV class="bg_middle">
	<DIV class="logo"></DIV>
	<DIV class="ico"></DIV>
	<DIV class="login">
		
		
		 <form id="loginForm"  class="form login-form" action="casLogin" method="post"  class="">
		 		<input type="hidden" id="postType" name="postType" value="1">
				<UL class="in_list" style="margin-top: 20px;">
			         <LI>
			         		<div>
							   <label>选择角色：</label>
							   <select class="input"  name="roleType" value="${roleType}">
									<option value="1" ${roleType eq '1'?"selected":""}>申报人</option>
									<option value="3" ${roleType eq '3' ?"selected":""}>院（系）级单位管理人员</option>
									<option value="2" ${roleType eq '2' ?"selected":""}>人事处管理人员</option>
								</select>
						   </div>
					</LI>
				  <LI>
					 			<div>
					  			<label>业务类型：</label>
					  				<select class="input"  onchange="postTypeSelectChange(this)" >
											<option value="1" ${postType eq '1'?"selected":""}></option>

									</select>
								</div>
					</LI>
				  <LI>
					  <TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
						    <TBODY>
							    <TR>
							       <TD align="left" valign="middle">
							         <INPUT id="btn_login" style="width:209px;" type="image" src="${ctxStatic}/images/login/btn_login.png">		             			             
							      </TD>
							     </TR>
						     </TBODY>
					   </TABLE>
				   </LI>
			   </UL>
	   </form>
  </DIV>
	<DIV class="clear"></DIV>		  
</DIV>	
	
		<div id="messageBox" class="alert alert-error ${empty isPrivileges ?'hide':'' }" align="right"><button data-dismiss="alert" class="close">×</button>
			<label id="loginError" class="error" style="margin-right: 260px">
				${empty isPrivileges ?'':'权限不足.'  }
			</label>
		</div>
<BR/>ss
<BR/>
<BR/>
<BR/>
<DIV class="copyright">
	Copyright &copy; 2012-${fns:getConfig('copyrightYear')} <a href="${pageContext.request.contextPath}">${fns:getConfig('productName')}</a> - Powered By <a href="http://www.szbonus.com" target="_blank"></a>
<P></P>
</DIV>
</DIV>

</BODY>
</HTML>
