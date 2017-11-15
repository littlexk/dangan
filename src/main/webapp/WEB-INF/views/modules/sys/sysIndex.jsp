<%@page import="com.thinkgem.jeesite.modules.sys.entity.Role"%>
<%@page import="java.util.List"%>
<%@ page import="com.thinkgem.jeesite.modules.sys.utils.UserUtils" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <%@ include file="/WEB-INF/views/include/taglib.jsp" %>
    <title>${fns:getConfig('productName')}</title>
    <meta name="decorator" content="default"/>

    <script src="${ctxStatic}/bns/js/topAndmenu.js" type="text/javascript"></script>
    <link rel="Stylesheet" href="${ctxStatic}/bns/js/jerichotab/jquery.jerichotab_other.css"/>
    <script type="text/javascript" src="${ctxStatic}/bns/js/jerichotab/jquery.jerichotab_other.js"></script> 

    <style type="text/css">
       .jericho_tab .tab_content div.content {overflow:auto; background:#fff;}
    </style>
    <c:set var="tabmode" value="${empty cookie.tabmode.value ? '1' : cookie.tabmode.value}"/>
    <!--右边 End-->
    <script type="text/javascript">
        var adminPath = "${adminPath}";
        var tabTitleHeight = 42; // 页签的高度
        $(document).ready(function () {
        	$("html").css("height","100%");
            //跳动的时间
            setInterval(function () {
                myDate = new Date();
                $("#TIME").html(myDate.toLocaleString());
            }, 1000);
            //个人信息和修改密码点击事件
            $("#user_info ul li a").click(function () {
                if($(this).data("href")){
                    addTab($(this), true);
                    return false;
                }
            });

            //初始化左边内容
            $("#left").html($("#menuIndex").html());
            //首页不显示功能导航
            if($("#menu-index").attr("style").indexOf("display: none")==-1){
            	$("#topMenuIco").hide();
            }

            $.fn.initJerichoTab({
                renderTo: '#right', uniqueId: 'jerichotab',loader:'layer',
                contentCss: {'height': $('#right').height() - tabTitleHeight},
                tabs: [{
                    title: '首页',
                    closeable: false,
                    data: {dataType: 'formtag', dataLink: '#userIndex'},
                    onLoadCompleted: function (h) {
                    }
                }], activeTabIndex: 0, loadOnce: true
            });
            //点击顶部菜单
            $("#menu a.menu, #menuTab a.menu").click(function () {
                // 一级菜单焦点
                $("#menu li").removeClass("acur");
                $(this).parent().addClass("acur");
              
                var dataId2 = $(this).attr("data-id");
                var dataHref = $(this).attr("data-href");
                if ($(this).attr("name") == "index") {
                    //首页点击显示。
                    $("#topMenuIco").hide();
                    if($("#topMenuIco").hasClass("Open")){//如果左侧菜单收起，即先展开
                    	$("#topMenuIco").trigger("click");
                    }
                    if ($("#menu-index").length > 0) {
                        $("div[id^='menu-']").hide();
                        $("#menu-index").show();
                        $.fn.setTabActive(0).loadData(false);
                    }else{
                        $("#left").append($("#menuIndex").html());
                        initShortcutMenu();
                        editShortcutBindClick();
                        $.fn.setTabActive(0).loadData(false);
                    }
                    return false;
                } else {
                	if($("#topMenuIco").hasClass("Open")){//如果左侧菜单收起，即先展开
                		$("#topMenuIco").trigger("click");
                    }
                    //已加载过的菜单不需重新加载--开发模式系统菜单
                    if ($("#menu-" + dataId2).length > 0) {
                        $("div[id^='menu-']").hide();
                        $("#menu-" + dataId2).show();
                      	//首页不显示功能导航
                        if($("#menu-index").attr("style").indexOf("display: none")==-1){
                        	$("#topMenuIco").hide();
                        }else{
                        	$("#topMenuIco").show();
                        }
                      	//默认左则菜单的第一个页面
                        $("#menu-" + dataId2).find(".subMenu").find("a").eq(0).click();
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
                            $("div[id^='menu-']").hide();
                            $("#left").append(data);
                          	//首页不显示功能导航
                            if($("#menu-index").attr("style").indexOf("display: none")==-1){
                            	$("#topMenuIco").hide();
                            }else{
                            	$("#topMenuIco").show();
                            }
                            $(".Sidebar").css("top", "${not empty isDevelop or isDevelop?'100px':'60px'}");
                            //二级 左侧菜单点击展开下级菜单
                            $("#menu-" + dataId2).find(".subMenu").find("a").bind("click", function () {
                            	var href = $(this).attr("data-href");
                                var showType = $(this).attr("showtype");
                                if (!href) {
                                	if(showType == 2){
                                		window.open($this.attr('href'));
                                	}else{
                                		return addTab($(this), true);
                                	}
                                }else if($(this).attr("menuLevel") == 3){
                            		if ($(this).siblings().css("display") == "none") {
                                        $(this).siblings().css("display", "block");
                                    }
                                    else {
                                        $(this).siblings().css("display", "none");
                                    }
                            	}else if ($(this).attr("menuLevel") != 4) {
                                    if ($(this).parent().siblings().css("display") == "none") {
                                        $(this).addClass("acur");
                                        $(this).parent().siblings().css("display", "block");
                                        if($("#topMenuIco").hasClass("Open")){//左侧菜单收缩状态
                                        	$(this).find("font").show();
                                        }
                                    }else {
                                        $(this).removeClass("acur");
                                        $(this).parent().siblings().css("display", "none");
                                        if($("#topMenuIco").hasClass("Open")){//左侧菜单收缩状态
                                        	$(this).find("font").hide();
                                        }
                                    }
                                } 
                                return false;
                            });
                            
                          	//默认左则菜单的第一个页面
                          	$("#menu-" + dataId2).find(".subMenu").find("a").eq(0).click();
                        });
                    } else {
                        var href = $(this).attr("href");
                        var showType = $(this).attr("showtype");
                        if (href) {
                        	if(showType == 2){
                        		window.open(href);
                        	}else{
                        		return addTab($(this), true);
                        	}
                        }
                    }
                }
                // 大小宽度调整
                wSizeWidth();
                return false;
            });
        });
        function addTab($this, refresh) {
            var isI = $this.find(".iconfont");
            var text = $this.text();
            if (isI.length > 0) {
                text = $this.text().substring(1, $this.text().length);
            }
            text = text.trim().replace("", "").replace("", "");
            $(".jericho_tab").show();
            if($this.attr("jerichotabindex") == $(".tab_selected").attr("id").replace("jerichotab_","")){
            	$(".tab_selected").attr("dataLink", $this.attr('ahref') || $this.attr('href'));
            }
            $.fn.jerichoTab.addTab({
                tabFirer: $this,
                title: text,
                closeable: true,
                data: {
                    dataType: 'iframe',
                    dataLink: $this.attr('ahref') || $this.attr('href') 
                },onLoadCompleted: function(e){
                    if(e && e.target && e.target.contentWindow.uiReady){
                        e.target.contentWindow.uiReady(function(){
                            $.fn.removeLoader();
                        });
                    }else{
                        $.fn.removeLoader();
                    }
                    return false;
                },onClose: function(){
	            	if(text == "业务管理"){
	            		$("div[id^='menu-']").hide();
                        $("#menu-index").show();
                        $("#topMenuIco").hide();
                        $.fn.setTabActive(0).loadData(false);
	            	} 
	            }
            }).loadData(refresh);
            return false;
        }
        //重新加载左侧菜单内容
        function redrawMenu(id,name,position){
            if(position=="left"){
                var redradiv = $("#left div[class='Sidebar']:visible");
                var mid = $(redradiv).attr("id").replace("menu-","");
                //更新正打开页面的名称
                var redra = $(redradiv).find("a[data-id='"+id+"']");
                if(redra.length=1){
                    var aindex = $(redra).attr("jerichotabindex");
                    if(aindex){
                        var tabli = $("#jerichotab li[id='jerichotab_"+aindex+"']");
                        $(tabli).attr("name",name);
                        $(tabli).find("div[class='tab_text']").attr("title",name);
                        $(tabli).find("div[class='tab_text']").text(name.length>5?name.substr(0,5)+"...":name);
                    }
                }
                //已经打开的tab页层级记录
                var tabList = {};
                $(redradiv).find("a").each(function(){
                    var jtn = $(this).attr("jerichotabindex");
                    var did = $(this).attr("data-id");
                    if(jtn){tabList[did] = jtn;}
                });
                //重新加载左侧菜单
                $(redradiv).remove();
                $("#menu a[data-id='"+mid+"']").click();
                //恢复tabindex属性
                setTimeout(function(){
                    $("#menu-"+mid).find("a").each(function(){
                        var did = $(this).attr("data-id");
                        if(tabList[did]){$(this).attr("jerichotabindex",tabList[did]);}
                    });
                },500);
            }else{
                window.location.reload();
            }
        }

        var shortcutHtml = '<li class="pl20"><a href="{url}"><i class="iconfont icon mr10 f20">{menuicon}</i>{menuname}</a></li>';
        var topShortcutHtml = '<li class="fl tc pt15"><a href="{url}"><em class="show rdu3"><i class="iconfont icon f24">{menuicon}</i></em>{menuname}</a></li>';

        function initShortcutMenu() {
            //获得已设置的快捷菜单
            $.get("${ctx}/sys/menu/shortcutMenuList?t=" + new Date().getTime(), function (menus) {
            	$.each(menus, function () {
                    var menu = this;
                    var url = "${ctx}" + menu.HREF;
                    var name =menu.NAME;
                    var icon= menu.icon;
                    $("#shortcutMenu").append(shortcutHtml.format({
                        url: url,
                        menuname: name,
                        menuicon: (icon ? ('&#' + icon) : '&#xe637')
                    }));
                    $("#topShortcutMenu").append(topShortcutHtml.format({
                        url: url,
                        menuname: name,
                        menuicon: (icon ? ('&#' + icon) : '&#xe637')
                    }));
                });
            });
        }

        function editShortcutBindClick() {
            $("#editShortcut").click(function () {
                windowOpen({
                    title: "快捷菜单",
                    content: "${ctx}/tag/treemenuselect?url=" + encodeURIComponent("/sys/menu/treeData") + "&module=&checked=&extId=&nodesLevel=2&selectIds=1",
                    btn: ['确定', '关闭'],
                    area: ["600px", "500px"],
                    offset: "auto",
                    yes: function (index, layero) {
                        var document = $(layero).find("iframe")[0].contentWindow.document;//h.find("iframe").contents();
                        var menuIds = "";
                        //清除右侧已打开的tab页
                        $("#shortcutMenu a").each(function(){
                            var jetabindex = $(this).attr("jerichotabindex")
                            if(jetabindex){
                                $("#jerichotab").find("#jerichotab_"+jetabindex).find("a").click();
                            }
                        });
                        $("#shortcutMenu").empty();
                        $("#topShortcutMenu").empty();
                        $(document).find("#shortcutMenu").find("li").each(function () {
                            var selectedNode = $(this).find("a");
                            var name = selectedNode.data("name");
                            var id = selectedNode.data("id");
                            var url = selectedNode.data("url");
                            var menuicon = selectedNode.data("menuicon") || '';
                            menuIds += id + ",";
                            $("#shortcutMenu").append(shortcutHtml.format({
                                url: url,
                                menuname: name,
                                menuicon: menuicon ? menuicon: ''
                            }));
                            $("#topShortcutMenu").append(topShortcutHtml.format({
                                url: url,
                                menuname: name,
                                menuicon: menuicon ? menuicon: ''
                            }));
                        });
                        //if (menuIds) {
                        $.post("${ctx}/sys/menu/saveShortcutMenu", {data: menuIds});
                        //}
                        layer.close(index);
                    }, btn2: function () {
                    }
                    , cancel: function () {
                        //右上角关闭回调
                    },
                    maxmin: true
                });
            });
        }

        $(document).ready(function () {
            initShortcutMenu();
            //getTodoList(0);
            //更多菜单下拉
            $moreHtml = null;
            $("#menu ul li.hiddenMenu").hide();
            $("#menu ul li.hiddenMenu a").each(function () {
                $moreHtml = $('<li></li>');
                $(this).appendTo($moreHtml);
                $moreHtml.appendTo(".menuMore ul");
            });
            $("#menu ul li.hiddenMenu").length == 0 && $(".moreBtn").attr("style", "display:none;")

            $("#shortcutMenu li a").live('click', function () {
                addTab($(this), true);
                return false;
            });
            
            $("#topShortcutMenu li a").live('click', function () {
                addTab($(this), true);
                return false;
            });
            editShortcutBindClick();
        });
        function getTodoList(status){
        	$("#todoList").empty();
			$.get("${ctx}/exam/notice/toDoIndex?todoStatus="+status,function(data){
				if(data.list){
					$.each(data.list,function(i,n){
						var title = n.title;
						var id = n.id;
						var todoUrl = n.todoUrl;
						if(title!=''&&title.length>30){
							title = title.substring(0,30)+"...";
						}
						if(status=='0'){
							var li = $("<li><a class='btnCl fr dib tc rdu3' href='javascript:void(0);' onclick="+"dealTask('"+id+"','"+todoUrl+"','"+status+"')"+">处理</a>"+title+"</li>");
							$("#todoList").append(li);
						}
					 });
				}
			});
		}
        function dealTask(todoId,todoUrl,status) {
            windowOpenRb({
                title: "待办事项",
                area: ["900px","600px"],
                content: todoUrl+"&todoId="+todoId+"&status="+status,
                yes: function (index, layero) {

                },cancel: function () {
                	$(window.parent.document).contents().find("#todoFrame")[0].contentWindow.statuFilter(status);
                }
            });
        }
    </script>
</head>
<body>
<div style="display: none;"><tags:message content="${message}"/></div>
<!--全屏的遮盖层-->
<div class="Cover_layer"></div>
<!--全屏的遮盖层 End-->
<div id="main">
    <!--头部下部分-->
    <div class="Header jepor">
        <h4 class="logo jepoa bg-blue tc"><div style="color: white;font-size: 20px;margin-top: 20px;">管理系统</div>
        </h4>
        <div class="personal jepoa">
            <!--快捷菜单-->
            <div class="btnDiv dib jepor fl mr10" title="" >
                <ul class="nav pull-right fr">
	                 	<li id="topMenuIco" class="Close top_menuIcon fl pl20 pr15">
	           		         <em class="arrowFr fr"><i class="white iconfont f14">&#xe63b;</i></em>
	           		         <em class="arrowFl fr"><i class="white iconfont f14">&#xee8a;</i></em>
	           		         <em class="arrowText fl f14">功能导航<span style="width:131px; display: inline-block;"></span></em>
                		</li>
                		<li class="fl pl10 pr10"><em><i class="white iconfont f16">&#xe62c;</i></em></li>
                </ul>
            </div>
            <!--快捷菜单 End-->
            <!--个人信息-->
            <div id="user_info" class="photo pr20 jepor dib mt10">
                <img src="${ctxStatic}/bns/images/default_photo1.jpg" onerror="javascript:this.src='${ctxStatic}/bns/images/default_photo1.jpg'" class="mr5 rdu"/>
                <span class="name white" id="user_name"><shiro:principal property="name"/></span>
                <div class="rightDown jepoa pb15 tc">
                    <ul class="pt20 pb5">
                        <li><a data-href="${ctx}/sys/user/info" href="${ctx}/sys/user/info">个人信息</a></li>
                        <li><a data-href="${ctx}/sys/user/modifyPwd" href="${ctx}/sys/user/modifyPwd">修改密码</a>
                        <li><a data-href="" href="${ctx}/logout">退出系统</a>
                        </li>
                    </ul>
                </div>
            </div>
            <!--个人信息 End-->
            <!--快捷菜单弹出层-->
            <div class="leftDown jepoa pb15">
                <ul id="topShortcutMenu" class="p10"></ul>
            </div>
            <!--快捷菜单弹出层 End-->
            <!--通知信息弹出层-->
            <div class="notice jepoa pb15">
                <ul class="pt10 pr20 pb10 pl20">
                    <c:forEach items="${page.list}" var="bean">
                        <c:if test="${bean.DEAL_STATE eq '0'}">
                            <li><a class="btnCl fr dib tc rdu3"
                                   href="javascript:dealTask('${bean.ORDER_ID}', '${bean.ID}',
                                '${bean.BUSINESS_ID}', '${bean.FORM_ID}','edit');">处理</a>${bean['PROCESS_NAME']},${bean['TASK_TITLE']},${bean['EMPLOYEE_NAME']}
                            </li>
                        </c:if>
                    </c:forEach>
                </ul>
            </div>
            <!--通知信息弹出层 End-->
            <!--代办事项弹出层-->
            <div class="daiban jepoa pb15">
                <ul class="pt10 pr20 pb10 pl20" id="todoList">
                </ul>
            </div>
            <!--代办事项弹出层 End-->
            <!--帮助中心弹出层-->
            <div class="help jepoa pb15">
                <ul class="pt10 pr20 pb10 pl20">
                </ul>
            </div>
            <!--帮助中心弹出层 End-->

        </div>
        <!--顶部菜单 Start-->
        <div id="menu" class="menu jepoa f16">
            <ul>
                <c:set var="firstMenu" value="true"/>
                <c:set var="leftMenu" value="close"/>
                <li class="fl pr30 acur">
                    <a name="index" class="dib menu" href="###" data-id="menu-index">首页</a>
                </li>
                <c:set var="index" value="0"></c:set>
                <c:forEach items="${fns:getMenuList()}" var="menu" varStatus="idxStatus">
                    <c:if test="${menu.parent.id eq '1' && menu.isShow eq '1'}">
                        <li class="fl pr30 ${index>=6?'hiddenMenu':''}">
                            <c:if test="${empty menu.href}">
                                <c:if test="${menu.showType eq '1'}"><!-- 页面图标显示菜单 -->
	                            	<a class="dib menu ${index>=6?'hiddenA':''}"  target="${menu.target }"
	                                   href="${ctx}${menu.href}?id=${menu.id}"
	                                   data-id="${menu.id}" data-menuname="${menu.name}"
	                                   data-formurl='${ctx}/sys/menu/form?id=${menu.id}'>${menu.name}</a>
	                            </c:if>
	                            <c:if test="${menu.showType eq '0' || empty menu.showType}">
	                            	<a class="dib menu ${index>=6?'hiddenA':''}"  target="${menu.target }"
	                                   href=""
	                                   data-href="${ctx}/sys/menu/tree?parentId=${menu.id}&develop=2"
	                                   data-id="${menu.id}" data-menuname="${menu.name}"
	                                   data-formurl='${ctx}/sys/menu/form?id=${menu.id}'>${menu.name}</a>
	                            </c:if>
	                            <c:if test="${menu.showType eq '2'}">
	                            	<a class="dib menu ${index>=6?'hiddenA':''}"  target="${menu.target }"
	                                   href="${menu.href}" showtype="${menu.showType}"
	                                   data-id="${menu.id}" data-menuname="${menu.name}"
	                                   data-formurl='${ctx}/sys/menu/form?id=${menu.id}'>${menu.name}</a>
	                            </c:if>
                            </c:if>
                            <c:if test="${not empty menu.href}">
                            	<c:set var = "ahref" value="${fn:indexOf(menu.href, '://') eq -1 ? ctx :''}${not empty menu.href?menu.href:'/404'}"></c:set>
                            	<a class="dib menu ${index>=6?'hiddenA':''}"  target="${menu.target }"
                                   href="${ahref}?id=${menu.id}" showtype="${menu.showType}"
                                   data-id="${menu.id}" data-menuname="${menu.name}"
                                   data-formurl='${ctx}/sys/menu/form?id=${menu.id}'>${menu.name}</a>
                            </c:if>
                        </li>
                        <c:if test="${firstMenu}">
                            <c:set var="firstMenuId" value="${menu.id}"/>
                        </c:if>
                        <c:set var="firstMenu" value="false"/>
                        <c:set var="index" value="${index+1}"></c:set>
                    </c:if>
                </c:forEach>

                <!--更多菜单-->
                <li class="moreBtn jepor fl">
                    <i class="iconfont f16 mr5 white">&#xe62e;</i>
                    <!--更多菜单下拉-->
                    <div class="menuMore jepoa pb15 tc">
                        <ul>
                        </ul>
                    </div>
                    <!--更多菜单下拉 End-->
                </li>
                <!--更多菜单 End-->

                <c:if test="${isDevelop}">
                    <li class="addMenu" href="${ctx}/sys/menu/form?position=top"></li>
                </c:if>
            </ul>
        </div>
        <!--顶部菜单 End-->
    </div>
    <!--头部下部分 End-->
    
    
    <!--左边-->
    <div class="H_left" id="left" style="top:60px"></div>
    <!--左边 End-->
    <!--右边-->
    <div class="H_right bg-wh" id="right" style="top:60px"></div>
    <!--右边 End-->

</div>

<div id="menuIndex" style="display: none;">
    <div id="menu-index">
    <!--个人信息-->
    <div class="H_personal tc">
        <h3>
            <img src="${ctxStatic}/bns/images/default_photo1.jpg" onerror="javascript:this.src='${ctxStatic}/bns/images/default_photo1.jpg'" class="photo show rdu"/>
            <span class="name show f18 pt10"><shiro:principal property="name"/></span>
            <span class="show time pb10" id="TIME"></span>
        </h3>
        <p class="show btnBox pt5" style="display: none"><a class="show white rdu3 a2" href="#">账户设置</a></p>
        <p class="show btnBox pt5"><a class="show white rdu3 a3" href="${ctx}/logout">退出系统</a></p>
    </div>
    <!--个人信息 End-->
    <!--便捷菜单-->
    <div class="H_menu">
        <h3 class="f14 pl10 pr10 white"><a id="editShortcut" class="fr white" href="#" title="编辑"><i class="iconfont icon f16">&#xe61b;</i></a>便捷通道</h3>
        <ul id="shortcutMenu" class="f14">
        </ul>
    </div>
    <!--便捷菜单 End-->
    </div>
</div>

<div id="userIndex" style="display:none" class="m10 jepor Home">
  我的首页
</div>
<script type="text/javascript">
    var leftWidth = 230; // 左侧窗口大小
   
    var htmlObj = $("html"), mainObj = $("#main");
    var headerObj = $(".Header");
    var frameObj = $("#left, #right, #right iframe");
    function wSize(m_width) {
    	if(m_width==null){
    		m_width=leftWidth ; 
    	}
    	 $("#right").width($("#main").width()- m_width);
        var minHeight = 500, minWidth = 980;
        var strs = getWindowSize().toString().split(",");
        if (strs[1] < minWidth) {
            $("#main").css("width", minWidth - 10);
            $("html,body").css({"overflow": "auto", "overflow-x": "auto", "overflow-y": "auto"});
        } else {
            $("#main").css("width", "auto");
            $("html,body").css({"overflow": "hidden", "overflow-x": "hidden", "overflow-y": "hidden"});
        }
    } 
    function wSizeWidth() {
        if (!$("#openClose").is(":hidden")) {
            var leftWidth = ($("#left").width() < 0 ? 0 : $("#left").width());
        } else {
            //$("#right").width("100%");
        }
    }
    function openCloseClickCallBack(b) {
        $.fn.jerichoTab.resize();
    }
</script>
<script src="${ctxStatic}/common/wsize.min.js" type="text/javascript"></script>
</body>
</html>