<%@ page import="com.thinkgem.jeesite.modules.sys.utils.UserUtils" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@ include file="/WEB-INF/views/include/taglib.jsp" %>
    <title>${fns:getConfig('productName')}</title>
    <%@include file="/WEB-INF/views/include/dialog.jsp" %>
    <meta name="decorator" content="default"/>
    <script src="${ctxStatic}/bns/js/topAndmenu.js" type="text/javascript"></script>
    <link rel="Stylesheet" href="${ctxStatic}/bns/js/jerichotab/jquery.jerichotab_other.css"/>
    <script type="text/javascript" src="${ctxStatic}/bns/js/jerichotab/jquery.jerichotab_other.js"></script>
    <style type="text/css">
       .jericho_tab .tab_content div.content {overflow:auto; background:#f1f4f9;}
    </style>
    <script type="text/javascript">
	    $(document).ready(function () {
		    var p$ = window.parent.window.$;
		    $("#menu a.menu").click(function () {
		    	//如果菜单配置了url，直接打开
		    	var href = $(this).attr("href");
                var showType = $(this).attr("showtype");
                if (href) {
                	if(showType == 2){
                		window.open(href);
                	}else{
                		return addTab($(this), true);
                	}
                }
              	//如果左侧菜单收起，即先展开
              	p$("#topMenuIco").show();
		    	if(p$("#topMenuIco").hasClass("Open")){
		    		p$("#topMenuIco").trigger("click");
                }
                // 一级菜单焦点
                p$("#menu li").removeClass("acur");
                p$(this).parent().addClass("acur");
              	
                var dataId = $(this).attr("data-id");//所点击菜单id
                var dataId2 = $(this).attr("root-id");//业务管理id
                var dataId3 = $(this).attr("data-pid");//所点击菜单的上级菜单id
                if(!isNotNull(dataId3)){
                	dataId3 = "";
                }
                var dataHref = $(this).attr("data-href");
                //已加载过的菜单不需重新加载--开发模式系统菜单
                if (p$("#menu-" + dataId2+dataId3).length > 0) {
                    p$("div[id^='menu-']").hide();
                    p$("#menu-" + dataId2+dataId3).show();
                    p$("#menu-" + dataId2+dataId3).find(".subMenu").find("a").off("click");
                    clickLeftMenu(p$,dataId, dataId2,dataId3);
                    return false;
                }
                // 获取二级菜单数据(${fns:getUser().id}，为了解决IE浏览器切换用户，浏览器缓存问题)
                if (dataHref) {
                    $.get(dataHref+"&empId=${fns:getUser().id}", function (data) {
                        var unlog = false;
                        if (data.indexOf("id=\"loginForm\"") != -1) {
                            unlog = true;
                            layer.alert("未登录或登录超时。请重新登录，谢谢！", {icon:6},function(index){
                                top.location = "${ctx}";
                            });
                        }
                        if(unlog)return false;
                        p$("div[id^='menu-']").hide();
                        p$("#left").append(data);
                        p$(".Sidebar").css("top", "${not empty isDevelop or isDevelop?'100px':'60px'}");
                        //二级 左侧菜单点击展开下级菜单
                        clickLeftMenu(p$,dataId, dataId2,dataId3);
                    });
                }
                // 大小宽度调整
                //wSizeWidth();
                return false;
            });
	    });
	    function addTab($this, refresh) {
	    	var p$ = window.parent.window.$;
	        var isI = $this.find(".iconfont");
	        var text = $this.text();
	        var parentName = $this.attr("data-parentname");
	        if (isI.length > 0) {
	            text = $this.text().substring(1, $this.text().length);
	        }
	        text = text.trim().replace("", "");
	        var trimText = text;
	        if(text.length > 4 ){
	        	trimText = text.substring(0,4) + "...";
	        }
	        var fullName = text; 
	        p$(".jericho_tab").show();
	        p$.fn.jerichoTab.addTab({
	            tabFirer: $this,
	            title: fullName,
	            name: trimText,
	            closeable: true,
	            data: {
	                dataType: 'iframe',
	                dataLink: $this.attr('ahref') || $this.attr('href') 
	            },onLoadCompleted: function(e){
	                if(e && e.target && e.target.contentWindow.uiReady){
	                    e.target.contentWindow.uiReady(function(){
	                    	p$.fn.removeLoader();
	                    });
	                }else{
	                	p$.fn.removeLoader();
	                }
	                return false;
	            }
	        }).loadData(refresh);
	        return false;
	    }
	    //给新加载的左侧菜单绑定事件
	    function clickLeftMenu(p$,dataId, dataId2,dataId3){
	    	p$("#menu-" + dataId2+dataId3).find(".subMenu").find("a").bind("click", function () {
	    		var href = $(this).attr("data-href");
                var showType = $(this).attr("showtype");
                if (!href) {
                	if(showType == 2){
                		window.open($(this).attr("href"));
                	}else{
                		return addTab($(this), true);
                	}
                }else if($(this).attr("menuLevel") == 3 || $(this).attr("menuLevel") == 4){
                		if ($(this).siblings().css("display") == "none") {
                            $(this).siblings().css("display", "block");
                        }
                        else {
                        	//$(this).addClass("arrowOn");
                            $(this).siblings().css("display", "none");
                        }
                	}else if ($(this).attr("menuLevel") != 5) {
                        if ($(this).parent().siblings().css("display") == "none") {
                            $(this).addClass("acur");
                            $(this).parent().siblings().css("display", "block");
                            if(p$("#topMenuIco").hasClass("Open")){//左侧菜单收缩状态
                            	$(this).find("font").show();
                            }
                        }else {
                            $(this).removeClass("acur");
                            //$(this).addClass("arrowOn");
                            $(this).parent().siblings().css("display", "none");
                            if(p$("#topMenuIco").hasClass("Open")){//左侧菜单收缩状态
                            	$(this).find("font").hide();
                            }
                        }
                    }
                return false;
            });
	    	//点击打开对应的页面并展开对应的菜单
	    	if(!p$("#menu-" + dataId2+dataId3).find(".subMenu a[data-id='"+dataId3+"']").hasClass("acur")){
	    		p$("#menu-" + dataId2+dataId3).find(".subMenu a[data-id='"+dataId3+"']").click();
	    	}
	    	p$("#menu-" + dataId2+dataId3).find(".subMenu .sub a[data-id='"+dataId+"']").click();
	    	
	    }
    </script>
</head>
<body>
<div style="display: none;"><tags:message content="${message}"/></div>
<!--全屏的遮盖层-->
<div class="Cover_layer"></div>
<!--全屏的遮盖层 End-->
<div id="userIndex" style="display: block;" class="p15">
    <!--快捷菜单-->
    <div id="menu" class="H_menuBox BoderAndBg pl5 pr5 pb10 pt10">
          <dl>
       		  <c:forEach items="${fns:getMenuList()}" var="pmenu" varStatus="pidxStatus">
       		  	<c:if test="${pmenu.parent.id eq id  && pmenu.isShow eq '1' }">
       		  		<c:set var = "pahref" value="${fn:indexOf(pmenu.href, '://') eq -1 ? ctx :''}${not empty pmenu.href?pmenu.href:'/404'}"></c:set>
       		  		<h4 class="f14 pl15 pb10 pr10 line pt15">${pmenu.name}</h4>
       		  		<c:forEach items="${fns:getMenuList()}" var="menu" varStatus="idxStatus">
       		  			<c:if test="${menu.parent.id eq pmenu.id  && menu.isShow eq '1' }">
       		  				<dt class="pt10">
	                            <c:if test="${empty menu.href}">
	                                <a class="dib menu f12"  href="${ctx}/404"
	                                   isDevelop="1"
	                                   data-href="${ctx}/sys/menu/tree?parentId=${menu.id}&develop=2"
	                                   data-id="${menu.id}" data-menuname="${menu.name}"
	                                   data-formurl='${ctx}/sys/menu/form?id=${menu.id}'>
	                                   <span class="white show rdu5 mb5" style="background-color:${menu.bgColor}"><i class="iconfont f28">&#${menu.icon};</i></span>${menu.name}
	                                </a>
	                            </c:if>
	                            <c:if test="${not empty menu.href}">
	                            	<a class="dib menu f12"  href=""
	                                   isDevelop="1"
	                                   data-href="${ctx}/sys/menu/tree?parentId=${id}&customId=${pmenu.id}&develop=2"
	                                   data-pid="${pmenu.id}" root-id="${id}" data-id="${menu.id}" data-menuname="${menu.name}"
	                                   data-formurl='${ctx}/sys/menu/form?id=${menu.id}'>
	                                   <span class="white show rdu5 mb5" style="background-color:${menu.bgColor}"><i class="iconfont f28">&#${menu.icon};</i></span>${menu.name}
	                                </a>
	                            </c:if>
	                        </dt>
       		  			</c:if>
       		  		</c:forEach>
       		  		<div class="clear"></div>
       		  	</c:if>
       		  </c:forEach>
          </dl>
          <div class="clear"></div>
    </div>
</div>
</body>
</html>