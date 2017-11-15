<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html style="overflow-x:hidden;overflow-y:auto;">
<head>
	<title>数据选择</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
		var fIndex = null;
		var key, lastValue = "", nodeList = [],showNodeList = [];
		var tree, setting = {view:{selectedMulti:false},check:{enable:"${checked}",nocheckInherit:false,chkStyle: "checkbox",chkboxType: { "Y": "", "N": "" }},
				data:{simpleData:{enable:true}},
				view:{
					fontCss:function(treeId, treeNode) {
						var isShow = treeNode.isShow;
						var highlight = treeNode.highlight;
						var noSelect = treeNode.noSelect;
						var style = null;
						if(highlight){
							if(isShow){
								style = {"font-weight":" bold"};
							}else{
								style = {"font-weight":" bold"};
							}
						}else{
							if(isShow){
								style = {"font-weight":" normal"};
							}else{
								style = {"font-weight":" normal"};
							}
						}
						if(noSelect=="true"){
							style.color= "gray";
						}
						return style;
					}
				},
				callback:{beforeClick:function(id, node){
					if("${checked}" == "true"){
						tree.checkNode(node, !node.checked, true, true);
						return false;
					}
					if(node.noSelect=="true"){
						return false;
					}
				}, 
				onDblClick:function(){
					//TODO: 回调layer的确定按钮
					var selectedNodes = tree.getSelectedNodes();
					if(selectedNodes.length>0){
						parent.$("div[times='"+fIndex+"'] .layui-layer-btn0").trigger("click");
					}
				}}};
		$(document).ready(function(){
			$.get("${ctx}${url}${fn:indexOf(url,'?')==-1?'?':'&'}extId=${extId}&module=${module}&rootId=${rootId}&t="+new Date().getTime(), function(zNodes){
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
				//若可勾选，初始化将不可选项的复选框隐藏(最多5层)
				if("${checked}" == "true"){
					for(var i=0;i<5;i++){
						var nodesList = tree.getNodesByParam("level", i);
						for(var j=0; j<nodesList.length; j++) {
							if(nodesList[j].noSelect=="true"){
								nodesList[j].nocheck=true;
								$("#"+nodesList[j].tId).find("span.chk").hide();
							}else{
								nodesList[j].nocheck=false;
								$("#"+nodesList[j].tId).find("span.chk").show();
							}
						}
					}
				}
			});
			key = $("#key");
			key.bind("focus", focusKey).bind("blur", blurKey).bind("change keydown cut input propertychange", searchNode);
		});
		//回车事件
	  	$(document).keypress(function(e) {
	  		searchTree(e);
	  	});
	  	function focusKey(e) {
			if (key.hasClass("empty")) {
				key.removeClass("empty");
			}
		}
		function blurKey(e) {
			if (key.get(0).value === "") {
				key.addClass("empty");
			}
			searchNode(e);
			searchTree(e);
		}
		//查询,隐藏非查询结果
		function searchTree(e){
			tree.refresh();
			$(".level1").children("a").each(function(){
				$(this).parent("li").css("display","");
			});
			var value = $.trim(key.get(0).value);
			if(value!=""){
				for(var i=1;i<5;i++){
					$(".level"+i).children("a").each(function(){
						var style = $(this).attr("style");
						if(isNotNull(style)&&style.indexOf("font-weight: bold;")==-1){
							$(this).parent("li").hide();
						}
					});
				}
			}
			if(value==""){
				for(var i=0;i<5;i++){
					$(".level"+i).attr("style","");
					$(".level"+i).children("a").each(function(){
						var style = $(this).attr("style");
						if(isNotNull(style)){
							$(this).attr("style",style.replace("font-weight: bold;","font-weight: normal;"));
						}
					});
				}
			}
		}
		function searchNode(e) {
			// 取得输入的关键字的值
			var value = $.trim(key.get(0).value);
			
			// 按名字查询
			var keyType = "name";
			if (key.hasClass("empty")) {
				value = "";
			}
			
			// 如果和上次一次，就退出不查了。
			if (lastValue === value) {
				return;
			}
			
			// 保存最后一次
			lastValue = value;
			
			// 如果要查空字串，就退出不查了。
			if (value === "") {
				return;
			}
			updateNodes(false);
			nodeList = tree.getNodesByParamFuzzy(keyType, value);
			for(var i = 0; i < nodeList.length;i++){
				var nodeTemp = nodeList[i];
				nodeTemp.highlight = true;
				nodeTemp.isShow = true;
				var parentTemp = nodeTemp.getParentNode();
				var isExist = 0;
				if(parentTemp != null){
				for(var j=0; j < nodeList.length;j++){
					var currentNode = nodeList[j];
					if(parentTemp.id == currentNode.id){
						isExist = 1;
						break;
					}
				}
				if(isExist == 0){
					parentTemp.highlight = false;
					parentTemp.isShow=true;
					nodeList.push(parentTemp)
				}
				}
				
			}
			updateNodes(true);
			searchTree(e);
		}
		function updateNodes(highlight) {
			for(var i=0, l=nodeList.length; i<l; i++) {
				nodeList[i].highlight = highlight;
				tree.updateNode(nodeList[i]);
				tree.expandNode(nodeList[i].getParentNode(), true, false, false);
			}
		}
		function search() {
			$("#search").slideToggle(200);
			$("#txt").toggle();
			$("#key").focus();
		}
	</script>
</head>
<body>
	<div style="position:absolute;right:8px;top:15px;cursor:pointer;" onclick="search();">
		<i class="icon-search"></i><label id="txt">搜索</label>
	</div>
	<div id="search" class="control-group" style="padding:10px 0 0 10px;" hidden="hidden">
		<label for="key" class="control-label" style="float:left;padding:5px 5px 3px;">关键字：</label>
		<input type="text" class="empty" id="key" name="key" maxlength="50" style="width:180px;">
	</div>
	<div id="tree" class="ztree" style="padding:15px 10px;"></div>
</body>