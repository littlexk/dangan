	$(document).ready(function() {
		//初始化出勤/缺勤人员
		setEmpMap("att");
		initEmps("att");
		setEmpMap("abs");
		initEmps("abs");
	});
	//添加出勤/缺勤人员
	function addEmp(type,title){
		var orgId = $("#orgIdId").val();
		if(!isNotNull(orgId)){
			top.layer.msg("请先选择党组织");
			return false;
		}
		var excIds = $("#attendanceIds").val()+$("#absenteeIds").val();
		var options = {
				title:	"选择"+title,
				content:	ctx+"/party/affairs/common/attendanceEmpSelectList?orgId="+orgId+"&excIds="+excIds,
				area:	["1000px", "540px"],
				btn:	["确定","关闭"],
				success:function(layero,index){
					$(layero).find("iframe")[0].contentWindow.parentForm = $("#searchForm");
					$(layero).find("iframe")[0].contentWindow.curIndex = index;
					//添加表头信息
					setSelfTitleInCurLayer([{name:'注',value:'已出勤或缺勤的人员不会出现在以下列表'}],layero);
				},
				yes: function(index, layero){
					empMap = $(layero).find("iframe")[0].contentWindow.getEmps();
					if(empMap){
						setEmps(type);
					}
				},
				btn2: function(index, layero){
					top.layer.close(index);
				}
			};
			top.windowOpen(options);
	}
	//初始化时设置empMap值
	function setEmpMap(type){
		empMap = [];
		var idName = "";
		if(type=="att"){
			idName = "attendance";
		}else{
			idName = "absentee";
		}
		var ids = $("#"+idName+"Ids").val();
		var names = $("#"+idName+"Names").val();
		if(isNotNull(ids)){
			var idArray = ids.split(",");
			var nameArray = names.split(",");
			for(var i=0;i<idArray.length;i++){
				empMap.push({id:idArray[i],name:nameArray[i]});
			}
			$("#"+idName+"Ids").val(","+ids+",");//使字符串符合',EMP_NAME,'格式
		}
	}
	//初始化时将empMap渲染成页面元素
	function initEmps(type){
		if(isNotNull(empMap)&&empMap.length>0){
			var empIds = [];
			//拼接名字
			for(var i=0;i<empMap.length;i++){
				empIds.push(empMap[i].id);
				var aStr = "<a class='empSpan rdu3 "+type+"' title='双击删除' empId='"+empMap[i].id+"' ondblclick='removeEmp(this,\""+type+"\")'>"+empMap[i].name+"</a>";
				if(i<empMap.length-1){
					aStr+="<span class='emp"+empMap[i].id+"'>,</span>";
				}
				var $last = $("div."+type).children(":last-child");
				if($($last).is("a")){
					var empId = $($last).attr("empId");
					$("div."+type).append($("<span class='emp"+empId+"'>,</span>"));
				}
				$("div."+type).append($(aStr));
			}
			//计算人数
			var $num = $("#"+type+"Num");
			$num.text(empMap.length);
		}
	}
	//将empMap中的值渲染成页面元素
	function setEmps(type){
		if(isNotNull(empMap)&&empMap.length>0){
			var empIds = [];
			//拼接名字
			for(var i=0;i<empMap.length;i++){
				empIds.push(empMap[i].id);
				var aStr = "<a class='empSpan rdu3 "+type+"' title='双击删除' empId='"+empMap[i].id+"' ondblclick='removeEmp(this,\""+type+"\")'>"+empMap[i].name+"</a>";
				if(i<empMap.length-1){
					aStr+="<span class='emp"+empMap[i].id+"'>,</span>";
				}
				var $last = $("div."+type).children(":last-child");
				if($($last).is("a")){
					var empId = $($last).attr("empId");
					$("div."+type).append($("<span class='emp"+empId+"'>,</span>"));
				}
				$("div."+type).append($(aStr));
			}
			//拼接id
			var idName = "";
			if(type=="att"){
				idName = "attendance";
			}else{
				idName = "absentee";
			}
			var ids = $("#"+idName+"Ids").val();
			if(!isNotNull(ids)){
				ids=",";
			}
			ids+=empIds.join(",")+",";
			$("#"+idName+"Ids").val(ids);
			//计算人数
			var $num = $("#"+type+"Num");
			var num = parseInt($num.text());
			if(!isNotNull(num)){
				num=0;
			}
			num += empMap.length;
			$num.text(num);
			calEmp();
		}
	}
	//统计应到人数、实到人数和出勤率
	function calEmp(){
		var attNum = parseInt($("#attNum").text());
		var absNum = parseInt($("#absNum").text());
		$("#arrivalsNums").val(attNum+absNum);
		$("#actualNums").val(attNum);
		if(attNum+absNum>0){
			var cqrate = parseFloat(attNum)/parseFloat(attNum+absNum);
			$("#cqrate").val(cqrate.toFixed(4)*100);
		}
	}
	//清除人员
	function removeEmp(a,type){
		top.layer.confirm("确定移除此人员？", {
	        btn: ['确定', '取消'] //按钮
	    }, function (index, layero) {
	    	var empId = $(a).attr("empId");
	    	var ids = "";
	    	var $id = null;
	    	if(type=="att"){
	    		$id = $("#attendanceIds");
	    	}else{
	    		$id = $("#absenteeIds");
	    	}
	    	//移除id
	    	ids = $id.val();
	    	ids = ids.replace(","+empId+",",",");
	    	if(ids==","){
	    		ids="";
	    	}
	    	$id.val(ids);
	    	//移除姓名
	    	$(a).remove();
	    	$("span.emp"+empId).remove();
	    	var $last = $("div."+type).children(":last-child");
			if($($last).is("span")){
				$($last).remove();
			}
			//计算人数
			var $num = $("#"+type+"Num");
			var num = parseInt($num.text());
			if(!isNotNull(num)){
				num=0;
			}else{
				num--;
				$num.text(num);
			}
			calEmp();
	        top.layer.close(index);
		});
	}
	//清除全部人员
	function removeAllEmp(type){
		$(".divLine."+type).children().remove();
		$("#"+type+"Num").text("0");
		var idName = "";
		if(type=="att"){
			idName = "attendance";
		}else{
			idName = "absentee";
		}
		$("#"+idName+"Ids").val("");
		$("#"+idName+"Names").val("");
		calEmp();
	}
