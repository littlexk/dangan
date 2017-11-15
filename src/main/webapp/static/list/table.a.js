$(document).ready(function() {
	//自动计算固定列/动态列宽度
	var tableBoxWidth = $(".TableBox").width();
	var table1Width = $(".table01").width();
	var ratio1 = table1Width/tableBoxWidth*100;
	$(".sheet01").css("width",ratio1+"%");
	$(".sheet02").css("width",(100-ratio1)+"%");
	
	//选中列着色
	$("tbody tr td input[type='checkbox']").change(function () {
		$("tbody tr td input[type='checkbox']").each(function () {
			var curIndex = $(this).parent().parent().index();
			if(this.checked){
				$("tbody tr").eq(curIndex).children().css("background-color","#BBDCF2");
				$(".table02 tbody tr").eq(curIndex).children().css("background-color","#BBDCF2");//动态列
			}else{
				$("tbody tr").eq(curIndex).children().css("background-color","");
				$(".table02 tbody tr").eq(curIndex).children().css("background-color","");//动态列
			}
		});
	});
	
	//全选着色
	$("thead tr.title td input[type='checkbox']").change(function () {
		if(this.checked){
			$("tbody tr td").css("background-color","#BBDCF2");
		}else{
			$("tbody tr td").css("background-color","");
		}
	});
	//列表无数据时添加（无可显示的数据）
	addShowTd();
	addDyShowTd();
	//通过拖拽改变列宽
	dyTableTd("contentTable");
	dyTableTd("headTable");
	
	//鼠标移进着色
	$("tbody tr").mouseover(function(){
		if($(this).parent().parent().hasClass("Party_dyxx")){//排除表单
			return false;
		}
		var curIndex = $(this).index();
		$(this).css("background","#f4f4f4");
		//动态列情况
		$(".table01 tbody tr").eq(curIndex).css("background","#f4f4f4");
		$(".table02 tbody tr").eq(curIndex).css("background","#f4f4f4");
	});
	$("tbody tr").mouseout(function(){
		if($(this).parent().parent().hasClass("Party_dyxx")){//排除表单
			return false;
		}
		var curIndex = $(this).index();
		$(this).css("background","");
		//动态列情况
		$(".table01 tbody tr").eq(curIndex).css("background","");
		$(".table02 tbody tr").eq(curIndex).css("background","");
	});
	
	//将表格内容添加到title
	$("#contentTable tr td,#headTable tr td").each(function(){
		if(!isNotNull($(this).attr("title"))){
			$(this).attr("title",$(this).text());
		}
	});
});	
/**非动态列表start**/
//获取当前列表数据数
function getCurrListNum(){
	var num = 0;
	$(".TableBox >.Table_box > table > tbody > tr").each(function(){
		num++;
	});
	return num;
}
//获取当前列表表头字段数
function getCurrListHeadNum(){
	var num = 0;
	$(".TableBox >.Table_box > table > thead > tr > td").each(function(){
		num++;
	});
	return num;
}
//非动态列表无数据时添加（无可显示的数据）
function addShowTd(){
	var listNum = getCurrListNum();//获取当前列表数据数
	if(listNum==0){
		var headNum = getCurrListHeadNum();//获取当前列表表头字段数
		$(".TableBox .Table_box > table > tbody").append($("<tr style='height:60px' class='noData'><td colspan='"+headNum+"' align='center' style='color:#bfbfbf;'>无可显示的数据</td></tr>"));
	}
}
/**非动态列表end**/

/**动态列表start**/
//获取当前列表数据数
function getDyCurrListNum(){
	var num = 0;
	$(".TableBox >.Table_box >.sheet01 > table > tbody > .noData").remove();
	$(".TableBox >.Table_box >.sheet02 > table > tbody > .noData").remove();
	$(".TableBox >.Table_box >.sheet01 > table > tbody > tr").each(function(){
		num++;
	});
	return num;
}
//获取当前列表表头字段数
function getDyCurrListHeadNum(){
	var num = {};
	var fixedNum = 0;
	var dynamicNum = 0;
	//固定列
	$(".TableBox >.Table_box >.sheet01 > table > thead > tr > td").each(function(){
		fixedNum++;
	});
	//动态列
	$(".TableBox >.Table_box >.sheet02 > table > thead > tr > td").each(function(){
		dynamicNum++;
	});
	num = {fixedNum:fixedNum,dynamicNum:dynamicNum};
	return num;
}
//动态列表无数据时添加（无可显示的数据）
function addDyShowTd(){
	var dyListNum = getDyCurrListNum();//获取当前列表数据数
	if(dyListNum==0){
		var dyHeadNum = getDyCurrListHeadNum();//获取当前列表表头字段数
		$(".TableBox .Table_box > .sheet01 > table > tbody").append($("<tr style='height:60px' class='noData'><td colspan='"+dyHeadNum.fixedNum+"' align='center' style='color:#bfbfbf;'></td></tr>"));
		$(".TableBox .Table_box > .sheet02 > table > tbody").append($("<tr style='height:60px' class='noData'><td colspan='"+dyHeadNum.dynamicNum+"' align='center' style='color:#bfbfbf;'>无数据</td></tr>"));
	}
	return dyListNum;
}
/**动态列表end**/
//通过拖拽改变列宽
function dyTableTd(tableId){
	var tTD; //用来存储当前更改宽度的Table Cell,避免快速移动鼠标的问题   
	var table = document.getElementById(tableId); 
	if(!isNotNull(table)){
		return;
	}
	for (j = 0; j < table.rows[0].cells.length; j++) {
		table.rows[0].cells[j].onmousedown = function () {
			//记录单元格
			tTD = this;   
			if (event.offsetX > tTD.offsetWidth - 10) {   
			tTD.mouseDown = true;   
			tTD.oldX = event.x;   
			tTD.oldWidth = tTD.offsetWidth;   
			}   
			//记录Table宽度   
			//table = tTD; while (table.tagName != 'TABLE') table = table.parentElement;   
			//tTD.tableWidth = table.offsetWidth;   
		};   
		table.rows[0].cells[j].onmouseup = function () {   
			//结束宽度调整   
			if (tTD == undefined) tTD = this;   
			tTD.mouseDown = false;   
			tTD.style.cursor = 'default';   
		};   
		table.rows[0].cells[j].onmousemove = function () {   
			//更改鼠标样式   
			if (event.offsetX > this.offsetWidth - 10)   
				this.style.cursor = 'e-resize';   
			else   
				this.style.cursor = 'default';   
			//取出暂存的Table Cell   
			if (tTD == undefined) tTD = this;  
			//调整宽度   
			if (tTD.mouseDown != null && tTD.mouseDown == true) { 
				tTD.style.cursor = 'default';   
				if (tTD.oldWidth + (event.x - tTD.oldX)>0)   
					tTD.width = tTD.oldWidth + (event.x - tTD.oldX);   
				//调整列宽   
				tTD.style.width = tTD.width;   
				tTD.style.cursor = 'e-resize';   
				//调整该列中的每个Cell   
				table = tTD; while (table.tagName != 'TABLE') table = table.parentElement;   
				for (j = 0; j < table.rows.length; j++) {   
					table.rows[j].cells[tTD.cellIndex].width = tTD.width;   
				}
			}   
		};
	}
}