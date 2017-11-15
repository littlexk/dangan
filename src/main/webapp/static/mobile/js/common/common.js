// JavaScript Document
var jems = {}, msonionUrl, msPicPath;
$(function(){
	//获取设定的网址路径与图片路径
	var pachurl = $('link').attr('href'), mspathUrl = pachurl.indexOf("../") != -1 ? "../":"";
	$.ajax({	 
		url : mspathUrl+"js/common/base.json",
		type : "get",dataType:"json",async: false,		
        success:function(data){ 
			for(var i=0; i <data.pathUrl.length; i++){
			    msonionUrl = data.pathUrl[i].nionUrl;
			    msPicPath = data.pathUrl[i].PicPath;
			}
		}
    });
	
	(function posMarpad(){
		var bod=$("body"), isMarg=bod.attr("marpad"), fixtop=bod.attr("fixtop"), fixbot=bod.attr("fixbot");	
		var ftop="padding-top", fbot="padding-bottom";
		if(bod.attr("marpad") || bod.attr("padding")){ 
			if(fixtop){bod.css(ftop,fixtop);}
			if(fixbot){bod.css(fbot,fixbot);}
		}else{
			return false;
		}
	})();
	//返回上一页
	$("#goback").on('tap',jems.goBack);
	$("#myAccount").on('tap',jems.myAccount);
	$("#myShopCart").on('tap',jems.myShopCart);
});
//通用超链接跳转
jems.goUrl = function(url){
	var ParHref = jems.parsURL().params;
	var tmn = ParHref.tmn, urlTmn = url.indexOf('tmn')	
	if(tmn != "undefined" || tmn != "") {
		if(urlTmn == -1){
		    url = url.indexOf("?") != -1 ? url+"&tmn="+tmn : url+"?tmn="+tmn;
		}else{
			url = url;
		}
	}
	window.location.href = url;
}
//返回上一页面并涮新
jems.goBack = function(){
	if (window.history.length > 1) {
		window.history.go(-1);
		return true;
	}
	return false;
}
/*
	解析URL地址
	jems.parsURL( url ).file;     // = 'index.html'  	
	jems.parsURL( url ).hash;     // = 'top'  	
	jems.parsURL( url ).host;     // = 'www.abc.com'  	
	jems.parsURL( url ).query;    // = '?id=255&m=hello'  
	jems.parsURL( url ).queryURL  // = 'id=255&m=hello' 	
	jems.parsURL( url ).params;   // = Object = { id: 255, m: hello }  	
	jems.parsURL( url ).prefix;   // = 'www'
	jems.parsURL( url ).path;     // = '/dir/index.html'  	
	jems.parsURL( url ).segments; // = Array = ['dir', 'index.html']  	
	jems.parsURL( url ).port;     // = '8080'  	
	jems.parsURL( url ).protocol; // = 'http'  	
	jems.parsURL( url ).source;   // = 'http://www.abc.com:8080/dir/index.html?id=255&m=hello#top' 
*/
jems.parsURL = function ( url ) { 
    url = arguments[0] == undefined ? window.location.href : url;	
	var a =  document.createElement('a');  	
	a.href = url;  	
	return {  	
		source: url,	
		protocol: a.protocol.replace(':',''),	
		host: a.hostname,	
		port: a.port,  	
		query: a.search,
		params: (function(){  	
			var ret = {},seg = a.search.replace(/^\?/,'').split('&'),len = seg.length, i = 0, s;  	
			for (;i<len;i++) {  	
				if (!seg[i]) { continue; }  	
				s = seg[i].split('=');   ret[s[0]] = s[1];  	
			}  	
			return ret;  
		})(),  
        prefix: a.hostname.split('.')[0],
		file: (a.pathname.match(/\/([^\/?#]+)$/i) || [,''])[1], 	
		hash: a.hash.replace('#',''),  	
		path: a.pathname.replace(/^([^\/])/,'/$1'),  	
		relative: (a.href.match(/tps?:\/\/[^\/]+(.+)/) || [,''])[1],  	
		segments: a.pathname.replace(/^\//,'').split('/'),
		queryURL:a.search.replace(/^\?/,''),
	};  	
}
//点击获取自定义属性链接后跳转
jems.onUrl = function (cell){
	$(cell).on("tap",function(){
		if($(this)[0].hasAttribute('data-url')){
			location.href= $(this).data('url');
		}
	});
}
//我的账户
jems.myAccount = function (){
	var retmn = jems.parsURL().params.tmn;
	$.ajax({
        type : "post",
        url : msonionUrl+"menbercenter/memberInfo",
			dataType : "json",
			//jsonp:"callback",
			success:function(data){
			if(data.login_flag){
				if(data.memberrec.memberType == 2){//
					jems.goUrl("store/agents.html");
				} else if (data.memberrec.memberType == 3) {
					jems.goUrl("store/stores.html");
				} else {
				    jems.goUrl("ucenter/members.html");
				}
			}else{
				var fromurl = window.location.href;
				if (fromurl.indexOf("index.html") > 0){
					jems.goUrl("login.html?"+fromurl.substring(0,fromurl.indexOf("index.html"))+"ucenter/members.html?tmn="+retmn);
				} else{
				    jems.goUrl("login.html?"+window.location.href);
				}
			}
		}
	});
}
//我的购物车
jems.myShopCart =function (){
	$.ajax({
        type : "post",
        url : msonionUrl+"menbercenter/memberInfo",
			dataType : "json",
			//jsonp:"callback",
			success:function(data){
			if(data.login_flag){
				if(data.memberrec.memberType == 2){//
					jems.mboxMsg("对不起，洋葱商家无法使用本功能");
				} else {
					jems.goUrl("ucenter/cart.html?&_v="+new Date().getTime());
				}
			}else{
				jems.goUrl("login.html?"+window.location.href);
			}
		}
	});
}
//购物车数量
// 查看购物车数量
jems.showCartNum = function (){
	$.ajax({
		type:"get",
		url : msonionUrl+"cart/count",
		dataType : "json",
		//jsonp:"callback",
		success:function(data){
			if(data.num != -1){
				// 显示购物车数量
				if(data.num == 0){
				    $("#cartNum").css({display:"none"}).text(data.num);
			    }else{
					$("#cartNum").css({display:"block"}).text(data.num);
				}	
			}else{
				// 如果未登录，则不显示数量提示
				$("#cartNum").css({display:"none"});
			}
		}
	});
}
/**
 * 保留符点数后几位，默认保留两位
 * @param num 要格式化的数字
 * @param pos 要保留的位数,不传默认保留两位
 * @returns
 */
jems.formatNum = function (num,pos){
	// 默认保留两位
	pos = pos?pos:2;
	// 四舍五入
	var pnum = Math.round(num*Math.pow(10,pos))/Math.pow(10,pos);
	// 将四舍五入后的数字转为字符串
	var snum = pnum.toString();
	// 检测小数点位置
	var len = snum.indexOf('.');
	// 如果是整数，小数点位置为-1
	if(len<0){
		len = snum.length;
		snum += '.';
	}
	// 不足位数以零填充
	while(snum.length<=len+pos){
		snum += '0';
	}
	return snum;
}
//弹层提示
jems.mboxMsg = function(texts){
	mbox.open({
        content: texts,
        btnName: ['确定']
    });
}
jems.tipMsg = function(texts){
	mBox.open({
	    content: texts,
	    time: 2.5 //2.5秒后自动关闭
    });
}
