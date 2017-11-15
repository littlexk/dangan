	$(document).ready(function() {
		//字段校验
		//出生日期应早于入党时间  
		jQuery.validator.addMethod("dateCheck", function(value, element) {
			var birthday = $("#birthday").val();
			var joinDate = $("#joinDate").val();
			if(!isNotNull(joinDate)||!isNotNull(birthday)){
				return true;
			}
			return this.optional(element) || (compareDate(birthday,joinDate)<0);     
		}, "出生日期应早于入党时间");
		//工作时间可为空或参加工作时年龄应在10周岁以上
		jQuery.validator.addMethod("workDateCheck", function(value, element) {
			var birthday = $("#birthday").val();
			var workDate = $("#workDate").val();
			if(!isNotNull(workDate)||!isNotNull(birthday)){
				return true;
			}
			birthday=(parseInt(birthday.substring(0,4))+10)+birthday.substring(4);
			return this.optional(element) || (compareDate(birthday,workDate)<0);     
		}, "参加工作时年龄应在10周岁以上");
		//退休时间为空或在1960年之后
		jQuery.validator.addMethod("retireDateCheck", function(value, element) {
			return this.optional(element) || (compareDate(value,"1960-01-01")>=0);     
		}, "退休时间应在1960年之后");
		//离岗时间为空或在1980年之后
		jQuery.validator.addMethod("leaveDateCheck", function(value, element) {
			return this.optional(element) || (compareDate(value,"1980-01-01")>=0);     
		}, "离岗时间应在1980年之后");
	});
	//上传照片处理
	function doPreviewPhoto() {
	var file = document.getElementById("PHOTO");
	var img = document.getElementById("staPhoto");
	
	var value = file.value;
	
	//检测类型
	if (/^.*?\.(gif|png|jpg|jpeg|bmp)$/.test(value)) {
	
	} else {
	
		top.layer.msg('选择的电子相片不是gif, png, jpg, bmp格式，请重新选择！');
		//'只能上传gif, png, jpg, bmp格式的图片';
		return false;
	}
	
	var imgSize = 1024 * 1024 * 10; //最大10M
	
	var photoSize = 0;
	if (file.files && file.files[0]) {//chrome \ firefox
		photoSize = file.files[0].size;
	} else {//IE
		photoSize = file.fileSize;
	}
	
	if (photoSize > imgSize) {//'电子相片大小不能超过10M'
	
		top.layer.msg('选择的电子相片大小不能超过10M，请重新选择！');
		return false;
	}
	
	if (file.files && file.files[0]) {
		//火狐下，直接设img属性
		img.style.display = 'block';
	
		//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式  
		img.src = window.URL.createObjectURL(file.files[0]);
	
	} else {
		//IE下，使用滤镜
		file.select();
		$('#picDiv').focus();
		var imgSrc = document.selection.createRange().text;//获得选择区域的文本创建文本域和得到文本
		var localImagId = document.getElementById("localImag");
		//必须设置初始大小
		localImagId.style.width = "165px";
		localImagId.style.height = "200px";
		//图片异常的捕捉，防止用户修改后缀来伪造图片
		try {
			localImagId.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
			localImagId.filters
					.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
	
		} catch (e) {
			top.layer.msg('您选择的电子相片不符合规定，请重新选择！');
			return false;
		}
	
		img.style.display = 'none';
		document.selection.empty();
	}
	$("#staPhoto").attr("style","");
	$("#isUpdate").val("1");
		return true;
	}
		//计算年龄（当前年份-出生年份+1）
	function calAge(date,idStr){
		if(isNotNull($(date).val())){
			var curYear = new Date().getFullYear();
			var dateYear = $(date).val().substring(0,4);
			var age = curYear - dateYear + 1;
			if(age<0){
				age=0;
			}
			if(isNotNull(idStr)){
				$("#"+idStr).val(age);
			}
			return age;
		}
	}
	//计算党龄（当前日期-入党日期，不足一年为0）
	function calPAge(date,idStr){
		var dateStr = $(date).val();
		var pAge = 0;
		if(!isNotNull(dateStr)){
			return;
		}
		var curDate = new Date();
		var curYear = parseInt(curDate.getFullYear());
		var curMonth = parseInt(curDate.getMonth())+1;
		var curDay = parseInt(curDate.getDate());
		var dateYear = parseInt(dateStr.substring(0,4));
		var dateMonth = parseInt(dateStr.substring(5,7));
		var dateDay = parseInt(dateStr.substring(8,10));
		pAge = curYear - dateYear;
		if(pAge>0){
			curMD = curMonth*100+curDay;
			dateMD = dateMonth*100+dateDay;
			if(curMD<dateMD){
				pAge--;
			}
		}
		$("#"+idStr).val(pAge);
	}