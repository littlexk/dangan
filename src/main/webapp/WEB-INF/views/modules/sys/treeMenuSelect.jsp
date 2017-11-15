<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html style="overflow-x:hidden;overflow-y:auto;">
<head>
	<title>快捷菜单选择</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<link rel="stylesheet" href="${ctxStatic}/jquery-ztree/3.5.24/css/demo.css" type="text/css">
	<link href="${ctxStatic}/jquery-ztree/3.5.24/css/metroStyle/metroStyle.css" rel="stylesheet" type="text/css"/>
	<script src="${ctxStatic}/jquery-ztree/3.5.24/js/jquery.ztree.core.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery-ztree/3.5.24/js/jquery.ztree.excheck.min.js" type="text/javascript"></script>
	<link rel="Stylesheet" href="${ctxStatic}/jquery-ui/jquery-ui.min.css"/>
	<script type="text/javascript" src="${ctxStatic}/jquery-ui/jquery-ui.min.js"></script>
	<script type="text/javascript">
		var key, lastValue = "", nodeList = [];
		var html = '<li><a href="#" data-parentid="{parentId}" data-id="{id}" data-name="{name}" data-url={url} data-menuicon="{menuIconCode}"><i class="iconfont icon mr5 f16">{menuIcon}</i><span>{name}</span></a></li>';
		var tree, setting = {view:{selectedMulti:false},check:{enable:"${checked}",nocheckInherit:true},
				data:{simpleData:{enable:true}},
				view:{
					fontCss:function(treeId, treeNode) {
						return (!!treeNode.highlight) ? {"font-weight":"bold"} : {"font-weight":"normal"};
					}
				},
				callback:{beforeClick:function(id, node){
					if("${checked}" == "true"){
						tree.checkNode(node, !node.checked, true, true);
						return false;
					}
				}, 
				onDblClick:function(){
					var selectedNodes = tree.getSelectedNodes();
					if(selectedNodes.length>0){
						$("#addMenu").trigger("click");
					}
				}}};
		$(document).ready(function(){
			$("body").css({"background-color": "#ffffff", "min-width": "inherit"});
			$( "#shortcutMenu" ).sortable();
			$( "#shortcutMenu" ).disableSelection();
			$.get("${ctx}${url}${fn:indexOf(url,'?')==-1?'?':'&'}&extId=${extId}&module=${module}isShowHide=1&t="+new Date().getTime(), function(zNodes){
				// 初始化树结构
				tree = $.fn.zTree.init($("#tree"), setting, zNodes);
				
				// 默认展开一级节点
				var nodes = tree.getNodesByParam("level", 0);
				for(var i=0; i<nodes.length; i++) {
					tree.expandNode(nodes[i], true, false, false);
				}
				// 默认选择节点
				var ids = "${selectIds}".split(",");
				for(var i=0; i<ids.length; i++) {
					var node = tree.getNodeByParam("id", ids[i]);
					if("${checked}" == "true"){
						try{tree.checkNode(node, true, true);}catch(e){}
						tree.selectNode(node, false);
					}else{
						tree.selectNode(node, true);
					}
				}
				//获得已设置的快捷菜单
				$.get("${ctx}/sys/menu/shortcutMenuList?t="+new Date().getTime(), function(menus){
					$.each(menus,function(){
						var menu=this;
						var url="";
							url=menu.href;
						$("#shortcutMenu").append(html.format({id:menu.id,url:url,name:menu.name,parentId:menu.parentId,menuIconCode:menu.icon||'&#xe60e',menuIcon:menu.icon?menu.icon:'&#xe60e'}));
						//删除已存在的菜单
						tree.removeNode(tree.getNodesByParam("id",menu.id)[0])
					});
				});

			});


			key = $("#key");
			//key.bind("focus", focusKey).bind("blur", blurKey).bind("change keydown cut input propertychange", searchNode);

			$(".shortcutMenu li").live("click",function(){
				$(".shortcutMenu li").removeClass("selected");
				$(".shortcutMenu li").css("background-color","#e5e5e5");
				$(this).addClass("selected");
				$(this).css("background-color","#00a0e9");
			});
			$("#addMenu").click(function(){
				var nodes = tree.getSelectedNodes();
				var id,name,parentId,menuIcon,url;;
				for(var i=0; i<nodes.length; i++) {
						id = nodes[i].id;
						name = nodes[i].name;
						menuIcon = nodes[i].menuIcon;
						if(isNotNull(menuIcon)){
							menuIcon = "&#" + menuIcon;
						}
					    var href = nodes[i].href;
					    var menuType = nodes[i].MENU_TYPE;
						var	url="${ctx}"+href;
						if(nodes[i].getParentNode()){
							parentId = nodes[i].getParentNode().id;
						}
						if (nodes[i].level == 0){
							layer.msg("不能选择根节点（"+nodes[i].name+"）请重新选择。", {time: 5000, icon:6});
							return false;
						}
						if (nodes[i].isParent){
							layer.msg("不能选择父节点（"+nodes[i].name+"）请重新选择。");
							return false;
						}
					    $("#shortcutMenu").append(html.format({id:id,url:url,name:name,parentId:parentId,menuIcon:menuIcon?menuIcon:'&#xe60e',menuIconCode:menuIcon||'&#xe60e'}));
					   tree.removeNode(nodes[i]);
				}

			});

			$("#delMenu").click(function(){
				var selectedNode = $(".shortcutMenu li.selected").find("a");
					$(".shortcutMenu li").remove(".selected");
					var name = selectedNode.data("name");
					if(name){
						var id = selectedNode.data("id");
						var parentId = selectedNode.data("parentid");
						var menuicon = selectedNode.data("menuicon");
						tree.addNodes(tree.getNodesByParam("id",parentId)[0],{name:name,id:id,menuIcon:menuicon||'&#xe60e'});
					}

			})
		});
	</script>
</head>
<body>
	<div class="fl ml20">
		<ul id="tree" class="ztree" style=""></ul>
	</div>
	<div class="fl pl10 ml10 zTreeArrow">
		<div style="cursor:pointer" id="addMenu" class="pt10 pb10"><i class="iconfont icon mr10 f30">&#xe7b7;</i></div>
		<div style="cursor:pointer" id="delMenu" class="pt10 pb10"><i class="iconfont icon mr10 f30">&#xe7b6;</i></div>
	</div>
	<div class="fl ml10">
		<ul id="shortcutMenu" class="ztree_frBtn shortcutMenu" style="">
		</ul>
	</div>
</body>