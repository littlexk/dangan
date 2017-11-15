/*!
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
// 引入js和css文件
function include(id, path, file){
	if (document.getElementById(id)==null){
        var files = typeof file == "string" ? [file] : file;
        for (var i = 0; i < files.length; i++){
            var name = files[i].replace(/^\s|\s$/g, "");
            var att = name.split('.');
            var ext = att[att.length - 1].toLowerCase();
            var isCSS = ext == "css";
            var tag = isCSS ? "link" : "script";
            var attr = isCSS ? " type='text/css' rel='stylesheet' " : " type='text/javascript' ";
            var link = (isCSS ? "href" : "src") + "='" + path + name + "'";
            document.write("<" + tag + (i==0?" id="+id:"") + attr + link + "></" + tag + ">");
        }
	}
}
//获取字典标签
function getDictLabel(data, value, defaultValue){
	for (var i=0; i<data.length; i++){
		var row = data[i];
		if (row.value == value){
			return row.label;
		}
	}
	return defaultValue;
}
// 打开一个窗体
/*function windowOpen(url, name, width, height){
	var top=parseInt((window.screen.height-height)/2,10),left=parseInt((window.screen.width-width)/2,10),
		options="location=no,menubar=no,toolbar=no,dependent=yes,minimizable=no,modal=yes,alwaysRaised=yes,"+
		"resizable=yes,scrollbars=yes,"+"width="+width+",height="+height+",top="+top+",left="+left;
	window.open(url ,name , options);
}*/
function windowOpen(params) {
    this.options = $.extend({
        title: "窗口",
        type: 2,
        offset: 'auto',
        content: "a",
        area: ["800px","500"],
        maxmin: false
    }, params);
    var index = layer.open(this.options);
    if (this.options.isFull) {
        layer.full(index);
    }
}

/*// 显示加载框
function loading(mess){
	top.$.jBox.tip.mess = null;
	top.$.jBox.tip(mess,'loading',{opacity:0});
}

// 确认对话框
function confirmx(mess, href){
	top.$.jBox.confirm(mess,'系统提示',function(v,h,f){
		if(v=='ok'){
			loading('正在提交，请稍等...');
			location = href;
		}
	},{buttonsFocus:1});
	top.$('.jbox-body .jbox-icon').css('top','55px');
	return false;
}*/
//恢复提示框显示
function resetTip(){
	top.$.jBox.tip.mess = null;
}

// 关闭提示框
function closeTip(){
	top.$.jBox.closeTip();
}

//显示提示框
function showTip(mess, type, timeout, lazytime){
	resetTip();
	setTimeout(function(){
		top.$.jBox.tip(mess, (type == undefined || type == '' ? 'info' : type), {opacity:0, 
			timeout:  timeout == undefined ? 2000 : timeout});
	}, lazytime == undefined ? 500 : lazytime);
}

//显示加载框
function loading(mess) {
    layer.msg(mess, {icon: 16});
}

// 确认对话框
function confirmx(mess, href) {
    top.layer.confirm(mess, {
        btn: ['确定', '取消'] //按钮
    }, function (index, layero) {
        loading('正在提交，请稍等...');
        location = href;
        top.layer.close(index);
    });
    return false;
}

// 警告对话框
function alertx(mess, closed){
	top.$.jBox.info(mess, '提示', {closed:function(){
		if (typeof closed == 'function') {
			closed();
		}
	}});
	top.$('.jbox-body .jbox-icon').css('top','55px');
}
/*
 * 屏蔽删除键返回功能
 */
function shieldBack(){
		$(document).keydown(function (e) {  
	    var doPrevent;  
	    // for IE && Firefox  
	    var varkey = (e.keyCode) || (e.which) || (e.charCode);  
	    if (varkey == 8) {  
	        var d = e.srcElement || e.target;   
	        if (d.tagName.toUpperCase() == 'INPUT' || d.tagName.toUpperCase() == 'TEXTAREA') {  
	            doPrevent = d.readOnly || d.disabled;  
	            // for button,radio and checkbox  
	            if (d.type.toUpperCase() == 'SUBMIT'  
	                || d.type.toUpperCase() == 'RADIO'  
	                || d.type.toUpperCase() == 'CHECKBOX'  
	                || d.type.toUpperCase() == 'BUTTON') {  
	                doPrevent = true;  
	            }  
	        }  
	        else {  
	            doPrevent = true;  
	        }  
	    }  
	    else {  
	        doPrevent = false;  
	    }  
	
	if (doPrevent)  
	    e.preventDefault();  
	});  
}
var GlobalDataUtil={
	set:function(key,val){
		parent.dataCenter[key]=val;
	},
	get:function(key){
		return parent.dataCenter[key];
	}
};
/**
 * 设置必填属性的输入框在前面加星
 */
function requiredHandle(){
	$("form input,areatext,radio,checkbox,select").each(function(i){
		var req = $(this).attr("required");
		if(req){
			var label = $(this).closest(".control-group").children(".control-label");
			if(label.children("font").length<=0){
				label.prepend("<font color='red'>*</font>")
			}
		}
	}); 
}
$(document).ready(function() {
	try{
		// 链接去掉虚框
		$("a").bind("focus",function() {
			if(this.blur) {this.blur()};
		});
		//所有下拉框使用select2
		$("select").not(".no_select2").select2();
	}catch(e){
		// blank
	}
	shieldBack();
	requiredHandle();
	$(".accordion-inner .table").wrap("<div style='overflow: auto;height: 250px;'></div>");
	$('.fancybox').fancybox();
	
	$("#paging").change(function () {
        page($("#pageNo").val(), $(this).val());
    });
    $("#paging").val($("#pageSize").val());
    $("#paging").closest("li").find(".select2-container a span").text($("#pageSize").val());
    $("#paging").closest("em").find(".select2-container a span").text($("#pageSize").val());
    $("#paging").closest("li").find(".select2-container").attr("style", "width:80px");
    $("#paging").closest("em").find(".select2-container").attr("style", "width:61px");
	
	//表格tr选中换色
    $(".Mytable_box table tbody tr td").click(function(){
    	$(".Mytable_box table tbody tr").removeClass("trBg");
    	$(this).parent().addClass("trBg");
    	//$(this).parent().parent().parent().parent().siblings(".tipsDiv").fadeIn();
    });
    
    //表头浮动
    $(window).scroll(function () {
        var sTop = $(document).scrollTop();
        /////gettop
        if($(this).scrollTop()>140){//当window的scrolltop距离大于1时，go to top按钮淡出，反之淡入
		    $(".listHead").addClass("listHead_locate");
		} else {
		    $(".listHead").removeClass("listHead_locate");
		}
    });
    $("body").css({"min-width": "inherit"});
    //用于选择框校验
    $("select").parent().bind("focusout",function(){
    	$(this).find("select").keyup();
    });
    
	//全选
	$("#tableHead").click(function(){
		$("input:checkbox[name='rowSelect']").prop("checked",this.checked);
	});
	
	//回车键执行查询
	$(document).keydown(function(event){
        if(event.keyCode==13){
            $("a.btnCX").trigger("click");
        }
    });
});
//Textarea
function remarkCount(e, limit) {
   var text = e.val();
   var font = e.parent().find("font");
   if (text.length <= limit) {
       font.text(text.length);
   }
}
//添加TAB页面
function addTabPage(title, url, closeable, $this, refresh){
	top.$.fn.jerichoTab.addTab({
        tabFirer: $this,
        title: title,
        closeable: closeable == undefined,
        data: {
            dataType: 'iframe',
            dataLink: url
        }
    }).loadData(refresh != undefined);
}
/**
 * 获取信息
 */
function getCondition() {
    var conditionList = [];
    $("#searchUl").find("li[condition]").each(function () {
        var name = $(this).find("span[conditionName]").text();
        var operator = $(this).find("select[operatorSelect]").val();
        var input = $(this).find("input[conditionValue]");
        var value = input.val();
        var code = input.attr("code");
        var dataType = input.attr("dataType");
        var dateFormat = input.attr("dateFormat");
        var allowReset = input.attr("allowReset");
        conditionList.push({
            "name": name,
            "code": code,
            "operator": operator,
            "value": value,
            "dateFormat": dateFormat,
            "dataType": dataType,
            "allowReset": allowReset
        })
    });
    return conditionList;
}

/**
 * 触发改变事件
 */
function valueChange() {
    var conditionList = getCondition();
    var conditionListStr = JSON.stringify(conditionList);
    $("#conditionListStr").val(conditionListStr);
}

/**
 * 重置搜索面板条件设置
 *
 * @param searchForm 需要重置的查询区域
 */
function resetSearchForm(searchForm) {
    $(searchForm).find("input[allowReset!='false'][id!='showColumnValue']").val("");
    $(searchForm).find("select[id!='showColumn']").selectpicker("val","");
    //valueChange();
}
//格式化日期
function formatDate(date, fmt) {
	if(date=="Invalid Date"){
		return "";
	}
    var o = {
        "M+": date.getMonth() + 1, //月份 
        "d+": date.getDate(), //日 
        "h+": date.getHours(), //小时 
        "m+": date.getMinutes(), //分 
        "s+": date.getSeconds(), //秒 
        "q+": Math.floor((date.getMonth() + 3) / 3), //季度 
        "S": date.getMilliseconds() //毫秒 
    };
    if(/(y+)/.test(fmt)) {
    	fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for(var k in o) {
    	if(new RegExp("(" + k + ")").test(fmt)) {
    		fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    	}
    }
    return fmt;
}

//刷新form表单
function refreshSearchForm(searchForm){
	$(searchForm).find("input[allowReset!='false']").val("");//文本框
	$(searchForm).find("select").val("");//下拉控件
	$(searchForm).find(".btnSearch").click();
}

//判断是否为空
function isNotNull(str){
	if(str == null || str == undefined || str == ''){
		return false;
	}
	return true;
}
//比较date1与date2，转为数值比较
//date格式为yyyy-MM-dd字符串
function compareDate(date1,date2){
	var date1Num = parseInt(date1.replace(/-/g,""));
	var date2Num = parseInt(date2.replace(/-/g,""));
	var result = date1Num - date2Num;
	return result;
}
//validate校验结果展示屏幕自适应
function selfAdaption(error,element){
	var dNum = error.attr("data-hint").length;//标签字数
	var errorLong = dNum*12+20;//标签长度=字数*12+边距
	var eWidth = element.width();
	var eLeft = element.offset().left;
	var scrollWidth = 30;//预留滚动条宽度
	if(element.is("select")||element.parent().is(".input-append")
			||element.parent().is(".textBox")){//bootstrap将原select设为宽度0.5，所有要取得它实际宽度
		eWidth = element.parent().width();
		eLeft = element.parent().offset().left;
	}
	if($(window).width()-eLeft-eWidth-scrollWidth<errorLong){
		error.removeClass("hint--right");
		error.addClass("hint--top");
		error.addClass("hint--top"+dNum);
		error.css("left","-"+eWidth/2+"px");
		if(element.is("textarea")){
			var eHeight = element.height();
			error.css("top","-"+(eHeight/2)+"px");
		}
	}
	if (element.is(":checkbox")||element.is(":radio")){
		error.appendTo(element.parent().parent());
	} else if(element.parent().is(".input-append")){
		error.insertAfter(element.parent());
	}else{
		error.insertAfter(element);
	}
}

function downloadFile(basePath, fileName){
	top.layer.confirm('是否确定下载该材料?', {icon: 3, title:'提示', offset:['220px', '650px']}, function(index){
		window.location.href = basePath+"/front/fileUpload/downloadFile?fileName="+encodeURI(encodeURI(fileName));
		top.layer.close(index);
	});
}


/**
 * js加减乘除精确计算
 */
var numberUtils = {
	//加
	floatAdd : function(arg1,arg2){
		var r1,r2,m;
		try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
		try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
		m=Math.pow(10,Math.max(r1,r2));
		return (arg1*m+arg2*m)/m;
	},
	//减
    floatSub :function (arg1,arg2){
		var r1,r2,m,n;
		try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
		try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
		m=Math.pow(10,Math.max(r1,r2));
		//动态控制精度长度
		n=(r1>=r2)?r1:r2;
		return ((arg1*m-arg2*m)/m).toFixed(n);
	},

	//乘
    floatMul :function (arg1,arg2)   {
		var m=0,s1=arg1.toString(),s2=arg2.toString();
		try{m+=s1.split(".")[1].length}catch(e){}
		try{m+=s2.split(".")[1].length}catch(e){}
		return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m);
	},

	//除
    floatDiv:function (arg1,arg2){
		var t1=0,t2=0,r1,r2;
		try{t1=arg1.toString().split(".")[1].length}catch(e){}
		try{t2=arg2.toString().split(".")[1].length}catch(e){}

		r1=Number(arg1.toString().replace(".",""));

		r2=Number(arg2.toString().replace(".",""));
		return (r1/r2)*Math.pow(10,t2-t1);
	},
	//保留两位小数，没有就被.00
    toDecimal2 :function(x){
		var f = parseFloat(x);
		if (isNaN(f)) {
			return "";
		}
		var f = Math.round(x * 100) / 100;
		var s = f.toString();
		var rs = s.indexOf('.');
		if (rs < 0) {
			rs = s.length;
			s += '.';
		}
		while (s.length <= rs + 2) {
			s += '0';
		}
		return s;
	}

}