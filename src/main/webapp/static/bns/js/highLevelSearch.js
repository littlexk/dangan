/**
 * ChangjieLai 2017-05-12
 * 高级查询
 * 
 */

/*
 * 定义全局变量
 */
$(document).ready(function() {
	var count = 0;
	var cnum = 1;
	var cnumFlag = "_#HL#"+cnum;
});
/**
 * 弹出高级查询面板
 * 要求父页面有searchCondition和searchForm
 */
function openSearch(searchId,tableSort){
	var pageInfo = window.location.pathname;
	var options = {
		title:	"高级查询",
		content:	ctx+"/party/common/highLevelSearch/search?searchId="+searchId+"&pageInfo="+pageInfo+"&tableSort="+tableSort,
		area:	["700px", "500px"],
		btn:	["查询","保存并查询"],
		yes: function(index, layero){
			var result = $(layero).find("iframe")[0].contentWindow.getConditionMapArray();
			if(result){
				$("#searchCondition").val(result);
				top.layer.close(index);
				$("#searchForm").submit();
			}
		},
		btn2: function(index, layero){
			var searchListNum = 0;
			$(".gjSearch ul li").each(function(){
				searchListNum++;
			});
			if(searchListNum>=9){
				top.layer.msg("查询方案最多只能保存9条！");
				return false;
			}
			var searchId = $(layero).find("iframe")[0].contentWindow.$("#searchId").val();
			var result = $(layero).find("iframe")[0].contentWindow.getConditionMapArray();
			if(result){
				//打开查询名称窗口
				openSearchName(index, layero ,result,searchId,pageInfo);
			}
			return false;
		}
	};
	top.windowOpen(options);
}
/**
 * 弹出查询方案名称面板
 * @param fIndex
 * @param fLayero
 * @param fResult
 * @param searchId
 */
function openSearchName(fIndex,fLayero,fResult,searchId,pageInfo){
	var options = {
			title:	"查询方案名称",
			content:	ctx+"/party/common/highLevelSearch/searchName?searchId="+searchId,
			area:	["400px", "200px"],
			btn:	["保存","关闭"],
			yes: function(index, layero){
				var searchName = $(layero).find("iframe")[0].contentWindow.getSearchName();
				if(searchName){
					top.layer.close(index);
					//保存
					$(fLayero).find("iframe")[0].contentWindow.saveCondition(searchName,pageInfo);
					//查询
					$("#searchCondition").val(fResult);
					top.layer.close(fIndex);
					$("#searchForm").submit();
				}
			}
		};
		top.windowOpen(options);
}
/**
 * 展开下拉列表
 * @param em
 */
function openSearchList(em) {
    if ($(em).siblings(".gjSearch").css("display") == "block") {
        $(em).siblings(".gjSearch").fadeOut();
    }
    else {
        $(em).siblings(".gjSearch").fadeIn();
    }
}
/*
 * 初始化参数,选项
 * 若初始化没有条件（新增），则默认添加一条空的条件
 */
function initCondition(conditionNum){
	count = parseInt(conditionNum);
	cnum = count + 1;
	cnumFlag = "_#HL#"+cnum;
	if(count==0){
		addContent();
	}else{
		initSelect($("#condition"));
		$(".valueContent .relationTD").find("select").each(function(){
			initSelect($(this),'change')
		});
	}
}
/**
 * 初始化页面的select项
 * @param select
 * @param value
 */
function initSelect(select,value){
	select.selectpicker('render');
	if(value){
		columnIdChange(select.parent().parent().siblings(".columnTD").find(".columnId"),$(select[0]).attr("data-value"));
	}else{
		select.selectpicker('val', $(select[0]).attr("data-value"));
	}
}
/*
 * 查询列按钮点击
 */
function columnBtnClick(btn,tableSort){
	// 是否限制选择，如果限制，设置为disabled
	var $this = $(btn);
	var $id = $this.siblings(".columnId");
	var $table = $this.siblings(".columnTable");
	var $sqlName = $this.siblings(".columnSqlName");
	var $type = $this.siblings(".columnType");
	var $code = $this.siblings(".columnCode");
	var $name = $this.siblings(".columnName");
	if ($id.attr("disabled")){
		return true;
	}
    var nameLevel = "1";
	// 正常打开	
	var options = {
			title:	"选择字段",
			content:	ctx+"/tag/treeselect?url=/party/common/highLevelSearch/columnTreeData?type="+tableSort+"&selectIds="+$id.val(),
			area:	["300px", "420px"],
			btn:	["确定","清除","关闭"],
			yes: function(index, layero){
				var tree = $(layero).find("iframe")[0].contentWindow.tree;
				var ids = [], tables = [], sqlNames = [], names = [], dataTypes = [],codes = [], nodes = [];
					nodes = tree.getSelectedNodes();
				for(var i=0; i<nodes.length; i++) {
					if (nodes[i].level == 0){
						top.layer.msg("不能选择根节点（"+nodes[i].name+"）请重新选择。");
						return false;
					}
					if (nodes[i].isParent){
						top.layer.msg("不能选择父节点（"+nodes[i].name+"）请重新选择。");
						return false;
					}
					if (nodes[i].module == ""){
						top.layer.msg("不能选择公共模型（"+nodes[i].name+"）请重新选择。");
						return false;
					}
					ids.push(nodes[i].id);
					sqlNames.push(nodes[i].sqlName);
					tables.push(nodes[i].pId);
					dataTypes.push(nodes[i].dataType);
					codes.push(nodes[i].dataCode);
                    var t_node = nodes[i];
                    var t_name = "";
                    var name_l = 0;
                    do{
                        name_l++;
                        t_name = t_node.name + " " + t_name;
                        t_node = t_node.getParentNode();
                    }while(name_l < nameLevel);
					names.push(t_name);
					break; // 如果为非复选框选择，则返回第一个选择  </c:if>
				}
				$id.val(ids);
				$table.val(tables);
				$sqlName.val(sqlNames);
				$type.val(dataTypes);
				$code.val(codes);
				$name.val(names);
				columnIdChange($id);
				top.layer.close(index);
			},
			btn2: function(index, layero){
				$id.val("");
				$table.val("");
				$sqlName.val("");
				$type.val("");
				$code.val("");
				$name.val("");
				top.layer.close(index);
			}
		};
		top.windowOpen(options);
	return false;//防止IE9，IE10点击所在单位选择框触发onbeforeunload事件弹出是否要离开提示
}
/*
 * 查询列值变化，关系和值类型根据列值变化
 */
function columnIdChange(cId,value) {
	var $td = $(cId).parent().parent();
	var $select = $td.siblings(".relationTD").find("select");
	$select.children("option").remove();//隐藏所有选项
	$td.siblings(".relationTD").children("div").find("button span.filter-option").text("请选择");
    var dataType = $(cId).siblings(".columnType").val();
    if(dataType=='text'){//文本
    	$select.append($("<option value='eq'>等于</option>"));
    	$select.append($("<option value='ne'>不等于</option>"));
    	$select.append($("<option value='in'>包含</option>"));
    	$td.siblings(".valueTD").hide();
    	$td.siblings(".valueTD.text").show();
    }else if(dataType=='num'){//数字
    	$select.append($("<option value='gt'>大于</option>"));
    	$select.append($("<option value='ge'>大于等于</option>"));
    	$select.append($("<option value='eq'>等于</option>"));
    	$select.append($("<option value='ne'>不等于</option>"));
    	$select.append($("<option value='lt'>小于</option>"));
    	$select.append($("<option value='le'>小于等于</option>"));
    	$td.siblings(".valueTD").hide();
    	$td.siblings(".valueTD.num").show();
    }else if(dataType=='date'){//日期
    	$select.append($("<option value='gt'>大于</option>"));
    	$select.append($("<option value='ge'>大于等于</option>"));
    	$select.append($("<option value='eq'>等于</option>"));
    	$select.append($("<option value='ne'>不等于</option>"));
    	$select.append($("<option value='lt'>小于</option>"));
    	$select.append($("<option value='le'>小于等于</option>"));
    	$td.siblings(".valueTD").hide();
    	$td.siblings(".valueTD.date").show();
    }else if(dataType=='dict'){//数据字典
    	$select.append($("<option value='eq'>等于</option>"));
    	$select.append($("<option value='ne'>不等于</option>"));
    	$td.siblings(".valueTD").hide();
    	$td.siblings(".valueTD.dict").find("input.dictType").val($(cId).siblings(".columnCode").val());
    	$td.siblings(".valueTD.dict").show();
    }else if(dataType=='org'){//树结构
    	$select.append($("<option value='eq'>等于</option>"));
    	$select.append($("<option value='ne'>不等于</option>"));
    	$td.siblings(".valueTD").hide();
    	$td.siblings(".valueTD.org").show();
    }else{//其他
    	$select.append($("<option value='eq'>等于</option>"));
    	$select.append($("<option value='ne'>不等于</option>"));
    	$select.append($("<option value='in'>包含</option>"));
    	$td.siblings(".valueTD").hide();
    	$td.siblings(".valueTD.text").show();
    }
    var $selectClone = $select.clone();
    var $relationTD = $select.parent().parent();
    $select.parent().remove();
    $relationTD.append($selectClone);
    $selectClone.selectpicker('refresh');
    if(value){//初始化时赋关系列值，不清空值列
    	$selectClone.selectpicker('val', value);
	}else{
		$td.siblings(".valueTD").find("input").val("");
	}
}

function getValueByJson(obj,key){
	var json = $(obj).attr("json");
	var ojson = $.parseJSON(json.replace(new RegExp("'","gm"),"\""));
	return ojson[key];
}
/*
 * 数据字典选择按钮点击
 */
function dictBtnClick(btn){
	var $this = $(btn);
	var $id = $this.siblings(".dictId");
	var $type = $this.parent().parent().siblings(".columnTD").find("input.columnCode");
	var $name = $this.siblings(".dictName");
	var columnName = $this.parent().parent().siblings(".columnTD").find("input.columnName").val();
	// 是否限制选择，如果限制，设置为disabled
	if ($id.attr("disabled")){
		return true;
	}
	// 正常打开	
	var options = {
			title:	"选择数据标准",
			content:	ctx+"/party/common/highLevelSearch/dictDataList?type="+$type.val(),
			area:	["700px", "540px"],
			btn:	["确定","清除","关闭"],
			success: function(layero, index){
				setSelfTitleInCurLayer([{name:'标准类型',value:columnName}], layero);
			},
			yes: function(index, layero){
				var value = [], name = [];
				var s = $(layero).find("iframe")[0].contentWindow.$("#contentTable tbody :checkbox:checked");
				$.each(s, function(i, v){
					if(isNotNull($(v).val())){
						value.push($(v).val());
						name.push(getValueByJson($(v),"name"));
					}
				});
				if(value.length>1){
					top.layer.msg("只能选择一行记录！");
					return false;
				}
				if(value.length==0){
					top.layer.msg("请选择记录");
					return false;
				}
				$id.val(value);
				$name.val(name);
				top.layer.close(index);
			},
			btn2: function(index, layero){
				$id.val("");
				$name.val("");
				top.layer.close(index);
			}
		};
		top.windowOpen(options);
	return false;//防止IE9，IE10点击所在单位选择框触发onbeforeunload事件弹出是否要离开提示
}
/*
 * 机构数按钮点击
 */
function orgBtnClick(btn){
	var $this = $(btn);
	var $id = $this.siblings(".orgId");
	var $name = $this.siblings(".orgName");
	// 是否限制选择，如果限制，设置为disabled
	if ($id.attr("disabled")){
		return true;
	}
    var nameLevel = "1";
	// 正常打开	
	var options = {
			title:	"选择组织机构",
			content:	ctx+"/tag/treeselect?url=/sys/office/treeData?type=1&selectIds="+$id.val(),
			area:	["300px", "420px"],
			btn:	["确定","清除","关闭"],
			yes: function(index, layero){
				var tree = $(layero).find("iframe")[0].contentWindow.tree;
				var ids = [], names = [], nodes = [];
				nodes = tree.getSelectedNodes();
				for(var i=0; i<nodes.length; i++) {
					if (nodes[i].level == 0){
						top.layer.msg("不能选择根节点（"+nodes[i].name+"）请重新选择。");
						return false;
					}
					if (nodes[i].isParent){
						top.layer.msg("不能选择父节点（"+nodes[i].name+"）请重新选择。");
						return false;
					}
					if (nodes[i].module == ""){
						top.layer.msg("不能选择公共模型（"+nodes[i].name+"）请重新选择。");
						return false;
					}
					ids.push(nodes[i].id);
                    var t_node = nodes[i];
                    var t_name = "";
                    var name_l = 0;
                    do{
                        name_l++;
                        t_name = t_node.name + " " + t_name;
                        t_node = t_node.getParentNode();
                    }while(name_l < nameLevel);
					names.push(t_name);
					break; // 如果为非复选框选择，则返回第一个选择  </c:if>
				}
				$id.val(ids);
				$name.val(names);
				top.layer.close(index);
			},
			btn2: function(index, layero){
				$id.val("");
				$name.val("");
				top.layer.close(index);
			}
		};
		top.windowOpen(options);
	return false;//防止IE9，IE10点击所在单位选择框触发onbeforeunload事件弹出是否要离开提示
}
/*
 * 点击添加行
 */
function addContent(btn){
	var $curContent = $(btn).parent().parent();
	var $content = $(".content").clone();
	$content.attr("class","valueContent");
	//修改查询列id
	$content.find(".columnTD > div.input-append").children().each(function(){
		var id = $(this).attr("id");
		$(this).attr("id",id+cnumFlag);
	});
	//修改关系列id
	$content.find(".relationTD").children().each(function(){
		var id = $(this).find("select").attr("id");
		$(this).find("button").attr("data-id",id+cnumFlag);
		$(this).find("select").attr("id",id+cnumFlag);
	});
	//修改值列id
	$content.find(".valueTD").find("input").each(function(){
		var id = $(this).attr("id");
		$(this).attr("id",id+cnumFlag);
	});
	$content.find(".valueTD").find("a").each(function(){
		var id = $(this).attr("id");
		$(this).attr("id",id+cnumFlag);
	});
	if($curContent.length>0){
		$curContent.after($content);
	}else{
		$(".content").after($content);
	}
	$content.show();
	count++;
	cnum++;
	cnumFlag = "_#HL#"+cnum;
}
/*
 * 点击减少行
 */
function removeContent(btn){
	if(count==1){
		top.layer.msg("至少保留一组查询条件");
		return false;
	}
	var $curContent = $(btn).parent().parent();
	$curContent.remove();
	count--;
}
/*
 * 将查询条件封装成键值对数组
 */
function getConditionMapArray(){
	var validFlag = true;//所有字段必填校验
	var mapArray = [];//存储所有条件
	$(".valueContent").each(function(){
		var $this = $(this);
		var $columnTD = $this.children("td.columnTD");
		var $relationTD = $this.children("td.relationTD");
		var $textTD = $this.children("td.text");
		var $numTD = $this.children("td.num");
		var $dictTD = $this.children("td.dict");
		var $orgTD = $this.children("td.org");
		var $dateTD = $this.children("td.date");
		//查询列值
		var columnId = $columnTD.find("div > input.columnId").val();
		var columnType = $columnTD.find("div > input.columnType").val();
		var columnTable = $columnTD.find("div > input.columnTable").val();
		var columnSqlName = $columnTD.find("div > input.columnSqlName").val();
		//关系值
		var relationValue = $relationTD.find("select").val();
		//值(5种类型)
		var columnValue = "";
		if(columnType=='text'){//文本
			columnValue = $textTD.find("input[id^=textId]").val();
		}else if(columnType=='num'){
			columnValue = $numTD.find("input[id^=numId]").val();
		}else if(columnType=='date'){
			columnValue = $dateTD.find("input[id^=dateId]").val();
		}else if(columnType=='dict'){
			columnValue = $dictTD.find("input[id^=dictId]").val();
		}else if(columnType=='org'){
			columnValue = $orgTD.find("input[id^=orgId]").val();
		}else{
			columnValue = $textTD.find("input[id^=textId]").val();
		}
		//满足关系
		var condition = $("#condition").val();
		//验证必填
		if(columnId==null||columnId==''||relationValue==null||relationValue==''
			||columnValue==null||columnValue==''||condition==null||condition==''){
			validFlag = false;
		}
		
		//封装成键值对
		var map = {columnId:columnId,columnType:columnType,columnTable:columnTable,columnSqlName:columnSqlName,
				relationValue:relationValue,columnValue:columnValue,condition:condition};
		mapArray.push(map);
	});
	//返回JSON格式
	if(!validFlag){
		layer.msg("所有字段必填，请检查!");
		return false;
	}
	return JSON.stringify(mapArray);
}
/*
 * 获取查询方案名称
 */
function getSearchName(){
	var searchName = $("#searchName").val();
	if(searchName==null||searchName==''){
		layer.msg("请填写方案名称!");
		return false;
	}
	return searchName;
}
/*
 * 保存查询方案
 */
function saveCondition(searchName,pageInfo){
	var searchCondition = getConditionMapArray();
	var searchId = $("#searchId").val();
	$.ajax({  
        url: ctx+"/party/common/highLevelSearch/saveSearch",  
        type: "POST",  
        data: {searchCondition:searchCondition,searchName:searchName,searchId:searchId,pageInfo:pageInfo},
        dataType: "json",  
        success: function (data) {  
        	layer.msg("保存成功");
        },  
        error: function (data) { 
	        	layer.msg("保存失败");
        	}  
    });  
}
/*
 * 点击方案名查询
 */
function doSearch(searchId){
	$.ajax({  
        url: ctx+"/party/common/highLevelSearch/getSearchInfo",  
        type: "POST",  
        data: {searchId:searchId},
        dataType: "json",  
        success: function (data) {  
        	var result = {tableSql:data.TABLE_SQL,conditionSql:data.CONDITION_SQL};
        	var resultArray = [];
        	resultArray.push(result);
        	$("#searchCondition").val(JSON.stringify(resultArray));
			$("#searchForm").submit();
        },  
        error: function (data) { 
	        	layer.msg("查询失败");
        	}  
    }); 
}
function deleteSearch(searchId,a){
	top.layer.confirm("确认删除？", {
        btn: ['确定', '取消'] //按钮
    }, function (index, layero) {
        top.layer.close(index);
        $.ajax({  
            url: ctx+"/party/common/highLevelSearch/deleteSearch",  
            type: "POST",  
            data: {searchId:searchId},
            dataType: "json"  
        }); 
        $(a).parent().parent().remove();
    });
}