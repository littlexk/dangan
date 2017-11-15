/* 说明：
 * 1.搜索div的id必须为SearchBox
 * 2.搜索div需包含：<li class="myList">
	                      <span class="mySpan">
	                      <label>&#12288;&#12288;&#12288;&#12288;&#12288;显示列:</label>
	                      	<form:input id="showColumnValue" path="bean['showColumn']" type="hidden"/>
	                      	<select id="showColumn" class="selectpicker no_select2" title="请选择" multiple data-live-search="true" data-width="145px" data-size="8">
							</select>
						  </span>
					 </li>
 * 3.table必须含于id为TableBox的div中
 * 4.table的id必须为contentTable
 * 5table需包含<thead><td>字段</td></thead>和<tbody><td>内容</td>,<thead>为表头,<tbody>为列表内容
 * 6.table的initShowNum属性指定初始化显示列表的列数（除固定列外）
 * 7.所有动态列需用DynamicTd修饰，如<td class="DynamicTd">性别</td>
 * */

//固定表头、选择显示列
(function ($) {
	    $.fn.extend({
	    	//读取所有表头字段，生成字段选择框
	        SelectField:function(columnArray){
	        	var columnNum = [];
	        	columnNum = columnArray.split(",");
	            return this.each(function () {
	            	var $this = $(this); //指向当前的SearchBox
	            	var $li = $this.find(".myList");
	            	$this.siblings(".TableBox").find("#contentTable thead > tr > td.FixedTd,td.DynamicTd").each(function(){
	            		var $td = $(this);//指向表头td
	            		if($td.text()!=null&&$td.text()!=""){
	            			if($td.hasClass("FixedTd")){
	            				var $option = $("<option data-tokens='"+$td.text()+
		            					"' value='"+$td.index()+"' class='Fixed' selected>"+$td.text()+"</option>");
	            			}else{
	            				if(columnNum!=null&&$.inArray($td.index().toString(),columnNum)>-1){
		            				var $option = $("<option data-tokens='"+$td.text()+
			            					"' value='"+$td.index()+"' class='Dynamic' selected>"+$td.text()+"</option>");
		            			}else{
		            				var $option = $("<option data-tokens='"+$td.text()+
			            					"' value='"+$td.index()+"' class='Dynamic'>"+$td.text()+"</option>");
		            			}
	            			}
	            			$li.find("select").append($option);
	            		}
	            	});
	            	$this.children("ul").append($li);
	            });
	        },
	    	//生成固定表头覆盖原来表头，并隐藏未选择的动态列
	        FixedHead: function (options) {
	            var op = $.extend({ tableLayout: "auto" }, options);
	            return this.each(function () {
	                var $this = $(this); //指向当前的table
	                var $thisParentDiv = $(this).parent(); //指向当前table的父级DIV，这个DIV要自己手动加上去
	                $thisParentDiv.wrap("<div class='tablewrap'></div>").parent().css({ "position":"relative" }); //在当前table的父级DIV上，再加一个DIV
	                var position = $thisParentDiv.position();

	                var fixedDiv = $("<div class='headwrap Table_box rdu3 mb15 jepor' style='clear:both;overflow:hidden;z-index:2;position:absolute;'></div>")
	                    .insertBefore($thisParentDiv)//在当前table的父级DIV的前面加一个DIV，此DIV用来包装tabelr的表头
	                    .css({ "width":$thisParentDiv[0].clientWidth, "left":position.left, "top":position.top });

	                var $thisClone = $this.clone(true);
	                $thisClone.find("tbody").remove(); //复制一份table，并将tbody中的内容删除，这样就仅余thead，所以要求表格的表头要放在thead中
	                $thisClone.prop("id","cloneContentTable");
	                $thisClone.css("margin-bottom", 0);
	                $thisClone.appendTo(fixedDiv); //将表头添加到fixedDiv中

	                $this.css({ "marginTop":0, "table-layout":op.tableLayout });
	                //当前TABLE的父级DIV有水平滚动条，并水平滚动时，同时滚动包装thead的DIV
	                $thisParentDiv.scroll(function () {
	                    fixedDiv[0].scrollLeft = $(this)[0].scrollLeft;
	                });

	                //因为固定后的表头与原来的表格分离开了，难免会有一些宽度问题
	                //下面的代码是将原来表格中每一个TD的宽度赋给新的固定表头
	                var $fixHeadTrs = $thisClone.find("thead tr");
	                var $orginalHeadTrs = $this.find("thead");
	                $fixHeadTrs.each(function (indexTr) {
	                    var $curFixThs = $(this).find("td");
	                    var $curOrgTr = $orginalHeadTrs.find("tr:eq(" + indexTr + ")");
	                    $curFixThs.each(function (indexTh) {
	                        $(this).css("width", $curOrgTr.find("td:eq(" + indexTh + ")").width());
	                    });
	                });
	            });
	        }
	    });
	})(jQuery);
$(document).ready(function() {
	//根据初始化列数initShowNum显示
	var initShowNum = $("#contentTable").attr("initShowNum");
	if(initShowNum!="-1"&&$("#showColumnValue").val()==""){
		var initColumn = "";
		var num = 0;
		$("#contentTable thead > tr > td.DynamicTd").each(function(){
			if(num<initShowNum){
				initColumn = initColumn + $(this).index() + ",";
				num = num + 1;
			}
		});
		$("#showColumnValue").val(initColumn);
	}
	$("#SearchBox").SelectField($("#showColumnValue").val()+"");
	//页面列表加载
	var column = new String();
	column = $("#showColumn").val() + "";
	if(initShowNum!="-1"&&column=="null"){
		column = "";
		var num = 0;
		$("#showColumn option").each(function(){
			if(num<initShowNum){
				column = column + $(this).val() + ",";
				num = num + 1;
			}
		});
	}
	var columnArray = column.split(",");
	$("#showColumn option").each(function(){
		var flag = false;
		for(var i=0;i<columnArray.length;i++){
			if($(this).val()==columnArray[i]){
				flag = true;
				break;
			}
		}
		if(flag){
			showColumn($(this).val());//显示列
		}else{
			hideColumn($(this).val());//隐藏列
		}
	});
	noDataDo();//查询数据为空时，保持显示无数据
	allHideDo();//若隐藏全部动态列，添加空表格
	
	//给“显示列”下拉框绑定事件
	$("#showColumn").change(function(){
		$("#cloneContentTable").remove();//删除固定表头
		var showColumnValue = "";
		$("#showColumn option").each(function(){
			if($(this).prop("selected")==true){
				showColumn($(this).val());//显示列
				showColumnValue = showColumnValue +$(this).val() + ",";
			}else{
				hideColumn($(this).val());//隐藏列
			}
		});
		noDataDo();//查询数据为空时，保持显示无数据
		allHideDo();//若隐藏全部动态列，添加空表格
//		repaintFixedHead();//重新绘制固定表头
		$("#contentTable").attr("initShowNum","-1");//标识非初次进入页面
		$("#showColumnValue").val(showColumnValue);//选择项转换成字符串
	});
	
//	if($("#cloneContentTable").length>0){
//		$("#cloneContentTable").remove();//删除固定表头
//	}
	//绘制固定表头
//	$("#contentTable").FixedHead();
});

function showColumn(index){
	$("#contentTable thead > tr > td").eq(index).removeClass("myHide");
	$("#contentTable tbody >tr").each(function(){
		$(this).find('td').eq(index).removeClass("myHide");
	});
}
function hideColumn(index){
	$("#contentTable thead > tr > td").eq(index).addClass("myHide");
	$("#contentTable tbody >tr").each(function(){
		$(this).find('td').eq(index).addClass("myHide");
	});
}
//调用table.a.js方法，解决查询无数据时显示（无可显示数据）时，又隐藏所有动态列时表格样式问题
function noDataDo(){
	var dyListNum = getDyCurrListNum();//获取当前列表数据数
	if(dyListNum==0){
		addDyShowTd();
	}
}
function allHideDo(){
	var allHide = true;
	$("#contentTable thead > tr > td").each(function(){
		if(!$(this).hasClass("myHide")){
			allHide = false;
		}
	});
	if(allHide){
		$("#contentTable thead").append($("<tr class='title nullTr'><td></td></tr>"));
		var dyListNum = getDyCurrListNum();//获取当前列表数据数
		if(dyListNum==0){
			addDyShowTd();
		}else{
			var tbHeight = $("#headTable tbody").height()-1;
			$("#contentTable tbody").append($("<tr class='nullTr'><td style='height:"+tbHeight+"px'></td></tr>"));
		}
	}else{
		$(".nullTr").remove();
	}
}
function repaintFixedHead(){
	var $fixedHead = $("#contentTable").clone(true);
	$fixedHead.find("tbody").remove(); //复制一份table，并将tbody中的内容删除，这样就仅余thead，所以要求表格的表头要放在thead中
    $fixedHead.prop("id","cloneContentTable");
    $fixedHead.css("margin-bottom", 0);
    $fixedHead.appendTo($("div.headwrap")); //将表头添加到fixedDiv中
    
    var $fixedHeadTrs = $fixedHead.find("thead tr");
    var $orginalHeadTrs = $("#contentTable").find("thead");
    $fixedHeadTrs.each(function (indexTr) {
        var $curFixThs = $(this).find("td");
        var $curOrgTr = $orginalHeadTrs.find("tr:eq(" + indexTr + ")");
        $curFixThs.each(function (indexTh) {
            $(this).css("width", $curOrgTr.find("td:eq(" + indexTh + ")").width());
        });
    });
}
function showList(){
	var options = {
			title:	"显示列",
			content: ctx+"/party/emp/common/dynamicList",
			area:	["20%", "80%"],
			btn:	["确定","关闭"],
			success: function(layero,index){
				$(layero).find("iframe")[0].contentWindow.columns=$(".myList");
				$(layero).find("iframe")[0].contentWindow.initPage();
			},
			yes: function(index, layero){
				var nums = $(layero).find("iframe")[0].contentWindow.setSelect();
				$("#showColumn").selectpicker('val', nums);
				$("#showColumn").change();
				top.layer.close(index);
			},
			btn2: function(index, layero){
				top.layer.close(index);
			}
		};
		top.windowOpen(options);
}