<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html style="overflow-x:hidden;overflow-y:auto;">
<head>
	<title>数据选择</title>
	<%@include file="/WEB-INF/views/include/head.jsp" %>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
		var key, lastValue = "", nodeList = [];
		var tree = null;
		var urlreq = "${ctx}${url}?module="+${module}+"&t="+new Date().getTime();
		var setting = {
				async:{
					enable: true,
					url:urlreq,
					autoParam:["id","pId","name"],
					otherParam:{"otherParam":"zTreeAsyncTest"},
					dataFilter: filter
				},
				check:{enable:"${checked}",nocheckInherit:true},
				view:{
					fontCss:function(treeId, treeNode) {
						return (!!treeNode.highlight) ? {"font-weight":"bold"} : {"font-weight":"normal"};
					}
				},
				callback:{
					beforeClick:function(id, node){
						if("${checked}" == "true"){
							tree.checkNode(node, !node.checked, true, true);
							return false;
						}
					}, 
					onDblClick:function(){
						top.$.jBox.getBox().find("button[value='ok']").trigger("click");
				}}
			};

		function filter(treeId, parentNode, childNodes) {
			return childNodes;
		}
			
		$(document).ready(function(){
			var temp = $("#tree");
			//var temp = document.getElementById("tree");
			tree = $.fn.zTree.init($("#tree"), setting);
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
			$(".level1").children("a").each(function(){
				$(this).parent("li").css("display","");
			});
			var value = $.trim(key.get(0).value);
			if(value!=""){
			$(".level1").children("a").each(function(){
				var style = $(this).attr("style");
				if(style!="font-weight: bold;"){
					$(this).parent("li").css("display","none");
				}
			});
			}
			if(value==""){
			$(".level1").children("a").each(function(){
				$(this).attr("style","font-weight: normal;");
			});
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
			updateNodes(true);
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
	<div style="display:none">
	<div style="position:absolute;right:8px;top:5px;cursor:pointer;" onclick="search();">
		<i class="icon-search"></i><label id="txt">搜索</label>
	</div>
	<div id="search" class="control-group hide" style="padding:10px 0 0 15px;">
		<label for="key" class="control-label" style="float:left;padding:5px 5px 3px;">关键字：</label>
		<input type="text" class="empty" id="key" name="key" maxlength="50" style="width:120px;">
	</div>
	</div>
	<div id="tree" class="ztree" style="padding:15px 20px;"></div>
</body>