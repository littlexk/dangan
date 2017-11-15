/**
 * 搜索栏更多搜索下拉
 */

$(document).ready(function() {
	//搜索栏
	$(".Search_box > ul").children("li").hide();
	$(".Search_box > ul").children("li").slice(0, 4).show();
	$(".Search_box > ul").children("li.searchLi").show();
	$(".Search_box .IsShow span").click(function(){
		$(this).hide();
		$(this).siblings("font").show();
		$(".Search_box > ul").children("li:not(.myList)").fadeIn();
		$("#isShow").val("1");
	}); 
	$(".Search_box .IsShow font").click(function(){
		$(this).hide();
		$(this).siblings("span").show();
		$(".Search_box > ul").children("li").hide();
		$(".Search_box > ul").children("li").slice(0, 4).fadeIn();
		$(".Search_box > ul").children("li.searchLi").fadeIn();
		$("#isShow").val("0");
	}); 
	if($("#isShow").val()=="1"){
		$(".Search_box .IsShow span").hide();
		$(".Search_box .IsShow font").show();
		$(".Search_box > ul").children("li:not(.myList)").show();
	}
});