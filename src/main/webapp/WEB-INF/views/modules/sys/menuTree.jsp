<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<script type="text/javascript" language="javascript">
		$(document).ready(function(){	
			//左侧一级菜单点击事件	
			 $(".subMenu li h3").on("click",function(){
				 $(this).find(".arrow").css({ display: "block" });
					 if($(this).siblings(".sub").css("display") =="block"){
						 $(this).siblings(".sub").hide();
						 $(this).find("font").hide();
						 $(".subMenu li h3 a").removeClass("acur");
						 $('.subMenu li .sub a').addClass("acur");
						 $(".subMenu li h3 .arrow").removeClass("arrowOn");
					 }else{
						$(".subMenu li .sub").hide();
						$(".subMenu li h3 a").removeClass("acur");
						$(".subMenu li h3 .arrow").removeClass("arrowOn");
						$(this).siblings(".sub").show();
						$(this).find("a").addClass("acur");
						$(this).find("a").find("em").addClass("arrowOn");
					}
			 })	;
			 //左侧菜单收起时鼠标移进菜单图标事件
			 $(".subMenu li").on("mouseover",function(){
				 $(this).find(".arrow").addClass("arrowOn").show();
				 if($("#topMenuIco").hasClass("Open")){
					$(this).find(".sub").addClass("hoverClass");
					$(this).find(".sub").show();
					$(this).find(".sub").css({"background":"#fff","border-left":"solid 5px #fff"});
					$(this).find(".arrow").removeClass("arrowOn").hide();
					$(".subMenu li h3 span").hide();
					$(this).find("h3").find("font").show();
					$(".subMenu li h3 a").removeClass("acur");
					$(this).find("h3").find("a").addClass("acur");
				}
			 });
			//左侧菜单收起时鼠标移出菜单图标事件
		  	$(".subMenu li").on("mouseout",function(){
				   $(this).find(".arrow").removeClass("arrowOn").hide();
				 if($("#topMenuIco").hasClass("Open")){
				 	$(this).find(".sub").hide();
					$(this).find(".sub").css({"background":"#222933","border-left":"solid 5px #3b8cff"});
					$(this).find(".arrow").addClass("arrowOn").hide();
					$(".subMenu li h3 span").hide();
					$(this).find("h3").find("font").hide();
					$(".subMenu li h3 a").removeClass("acur");
					$(".subMenu li .sub").removeClass("hoverClass");
				}
				 
			 });
	   });
		function highLight(a){
			$(".subMenu li .sub li a").removeClass("acur2");
	  		$(a).addClass("acur2");
		}
 </script>

<div class="Sidebar" id="menu-${param.parentId}${param.customId}">
    <div class="subMenu">
        <ul>
        <c:set var="menuList" value="${fns:getMenuList()}"/>
        <c:set var="url" value="sys/view"/>
        <c:forEach items="${menuList}" var="menu" varStatus="idxStatus">
            <c:if test="${menu.parent.id eq (not empty param.parentId?param.parentId:'1')&&menu.id eq (not empty param.customId?param.customId:menu.id)&&menu.isShow eq '1'}">
                  <c:set var = "ahref" value="${fn:indexOf(menu.href, '://') eq -1 ? ctx :''}${not empty menu.href?menu.href:'/404'}"></c:set>
                  <c:set var = "systemName" value = "${menu.parent.name }"></c:set>
                <li>
                    <c:if test="${not empty menu.href}"><!--左侧菜单无下级的情况-->
                    <h3>
                    	<a data-href="" data-formurl="${ctx}/sys/menu/form?id=${menu.id}" href="${ahref}" data-parentname="${systemName}" showtype="${menu.showType}"
                           data-id="${menu.id}" data-menuname="${menu.name}">
                            <em class="arrow"></em>
                            <i class="f16 white ml10  mr5 iconfont">${empty menu.icon?'&#xe637':'&#'.concat(menu.icon)}</i>
                            <span>${menu.name.length() > 9 ? menu.name.substring(0,9).concat("...") : menu.name}</span>
                            <font>${menu.name.length() > 9 ? menu.name.substring(0,9).concat("...") : menu.name}<i class="f14 iconfont fr">&#xe642;</i></font>
                        </a>
                    </h3>
                    </c:if>
                    <!--二级菜单-->
                    <c:if test="${empty menu.href}">
                        <h3><a href="" data-toggle="dropdown" data-href="#menu-${menu.id}" href="#collapse-${menu.id}" data-parentname="${systemName}"
                               data-id="${menu.id}" data-menuname="${menu.name}" title="${menu.name}" class="show f16">
                             	<em class="arrow"></em>
                             	<i class="f16 white ml10 mr5 iconfont">${empty menu.icon?'&#xe637':'&#'.concat(menu.icon)}</i>
                             	<span>${menu.name.length() > 9 ? menu.name.substring(0,9).concat("...") : menu.name}</span>
                             	<font>${menu.name.length() > 9 ? menu.name.substring(0,9).concat("...") : menu.name}</font>
                           	</a>
                       	</h3>
                        <div class="sub accordion-body in">
                        	<ul>
                            <c:forEach items="${menuList}" var="menu2">
                                <c:if test="${menu2.parent.id eq menu.id&&menu2.isShow eq '1'}">
                                	<c:if test="${empty menu2.href}">
	                            	   <li>
	                            	   	<a formurl="${ctx}/sys/menu/form?id=${menu2.id}" href="${ctx}/404" showtype="${menu2.showType}"
                                           title="${menu2.name}" data-parentname="${systemName}" target="${not empty menu2.target?menu2.target:'mainFrame'}"
                                           data-href="" data-id="${menu2.id}" data-menuname="${menu2.name}"><i class="f14 iconfont icon-desktop icon-white">${empty menu2.icon?'&#xe609':'&#'.concat(menu2.icon)}</i>${menu2.name.length() > 8 ? menu2.name.substring(0,8).concat("...") : menu2.name}</a>
                                       </li>
                                       <!-- todo添加三级菜单代码 -->
                                    </c:if>
                                    <c:if test="${not empty menu2.href}">
                                       <c:set var = "ahref2" value="${fn:indexOf(menu2.href, '://') eq -1 ? ctx :''}${not empty menu2.href?menu2.href:'/404'}"></c:set>
	                            	   <li>
	                            	   	<a formurl="${ctx}/sys/menu/form?id=${menu2.id}" href="${ahref2}" showtype="${menu2.showType}"
                                           title="${menu2.name}" data-parentname="${systemName}" target="${not empty menu2.target?menu2.target:'mainFrame'}"
                                           data-href="" data-id="${menu2.id}" data-menuname="${menu2.name}" onClick="highLight(this)"><i class="f14 iconfont icon-desktop icon-white">${empty menu2.icon?'&#xe609':'&#'.concat(menu2.icon)}</i>${menu2.name.length() > 8 ? menu2.name.substring(0,8).concat("...") : menu2.name}</a>
                                       </li>
                                    </c:if>
                                </c:if>
                            </c:forEach>
                            </ul>
                        </div>
                    </c:if>
                </li>
            </c:if>
        </c:forEach>

    </ul>
    </div>
<!-- 原部分  end -->
    <c:if test="${param.develop eq '1'}">
        <h5 class="Addmenu_btn tc jepof pb10 f12">
        	<a href="javascript:void(0);" data-parent-id="${param.parentId}" formurl="${ctxDevelop}/sys/menu/form?parent.id=${param.parentId}"
				class="show rdu3" onclick="addMenu(this,'left')">添加菜单
			</a>
		</h5>
    </c:if>
</div>