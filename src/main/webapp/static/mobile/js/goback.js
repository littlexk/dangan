// JavaScript Document

//返回上一页面并涮新
$("#goback").on('tap',jems.goBack);
jems.goBack = function(){
	if (window.history.length > 1) {
		window.history.go(-1);
		return true;
	}
	return false;
}