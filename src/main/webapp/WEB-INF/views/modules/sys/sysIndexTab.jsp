<%@ page contentType="text/html;charset=UTF-8" %>
<script src="${ctxStatic}/echarts/echarts.min.js" type="text/javascript"></script>
<!--首页统计tab切换内容-->
<script type="text/javascript">
	var dataCenter = new Object();
	$(document).ready(function () {
		//初始化年份为当前年
		$("#year").text(new Date().getFullYear());
		dataCenter['YEAR'] = $("#year").text();
		refreshBase($("#year").text());
		$("#AutoTable").find("h2 span").bind("click",function(){
  	        var bindex = $(this).parent().find("span").index($(this));
  	        $("#AutoTable").find("h2 span").removeClass("active");
  	        $("#AutoTable").find("h2 span").eq(bindex).addClass("active");
  	        $("#AutoTable").find("div[name='AutoContent']>div").hide();
  	        $("#AutoTable").find("div[name='AutoContent']>div").eq(bindex).show();
  	        if($(this).attr("type")=="org"){
  	        	refreshOrg();
  	        }else if($(this).attr("type")=="emp"){
  	        	refreshEmp();
  	        }else if($(this).attr("type")=="dev"){
  	        	refreshDev();
  	        }else{
  	        	refreshBase();
  	        }
  	      });
  	      $("#AutoTable").find("h2 span:eq(0)").click();
  	  	//图切换为表
 		$(".personChart_tab .t2").click(function(){
 		    $(this).siblings().removeClass("acur");
 		    $(this).addClass("acur");
  		    $(this).parent().parent().siblings("table").show();
 			$(this).parent().parent().siblings("div").hide();
 	   	});
  	  	//表切换为图
 	    $(".personChart_tab .t1").click(function(){
 		    $(this).siblings().removeClass("acur");
 			$(this).addClass("acur");
 	 		$(this).parent().parent().siblings("table").hide();
 			$(this).parent().parent().siblings("div").show();
 			var chartId = $(this).parent().parent().siblings("div.echarts").attr("id");
 			if(chartId=="echarts_baseOrg"){
 				createBaseOrg(dataCenter.ORG);
 			}else if(chartId=="echarts_branch"){
 				createBranch(dataCenter.ORG);
 				createStudentBranch(dataCenter.ORG);
 			}else if(chartId=="echarts_emp"){
 				createEmp(dataCenter.EMP);
 			}else if(chartId=="echarts_empType"){
 				createEmpType(dataCenter['EMP_SUB_'+$("#empType").val()]);
 			}else if(chartId=="echarts_sex"){
 				createSex(dataCenter['EMP_SUB_'+$("#empType").val()]);
 			}else if(chartId=="echarts_nation"){
 				createNation(dataCenter['EMP_SUB_'+$("#empType").val()]);
 			}else if(chartId=="echarts_postLevel"){
 				createPostLevel(dataCenter['EMP_SUB_'+$("#empType").val()]);
 			}else if(chartId=="echarts_dev"){
 				createDev(dataCenter['DEV']);
 			}else if(chartId=="echarts_personal"){
 				createPersonal(dataCenter['DEV_SUB_'+$("#sub1Type").val()]);
 			}else if(chartId=="echarts_devSex"){
 				createDevSex(dataCenter['DEV_SUB_'+$("#sub1Type").val()+'_'+$("#sub2Type").val()]);
 			}else if(chartId=="echarts_devNation"){
 				createDevNation(dataCenter['DEV_SUB_'+$("#sub1Type").val()+'_'+$("#sub2Type").val()]);
 			}else if(chartId=="echarts_devPostLevel"){
 				createDevPostLevel(dataCenter['DEV_SUB_'+$("#sub1Type").val()+'_'+$("#sub2Type").val()]);
 			}
 	    });
	});
	//将后台数据暂存到dataCenter
	function setData(year,type,subType,sub2Type){
		var data = null;
		if(isNotNull(sub2Type)){
			data = dataCenter[type+"_SUB2_"+sub2Type];
		}else if(isNotNull(subType)){
			data = dataCenter[type+"_SUB_"+subType];
		}else{
			data = dataCenter[type];
		}
		if(year!=dataCenter['YEAR']||!isNotNull(data)){
			if(year!=dataCenter['YEAR']){
				dataCenter = new Object();
				dataCenter['YEAR'] = year;
			}
			$.ajax({  
		        url: "${ctx}/sys/bus/indexData",  
		        type: "POST",  
		        data: {year:year,type:type,subType:subType,sub2Type:sub2Type},
		        async: false,
		        success: function (data) {
		        	if(isNotNull(sub2Type)){
	        			dataCenter[type+"_SUB_"+subType+"_"+sub2Type] = data;
	        		}else if(isNotNull(subType)){
	        			dataCenter[type+"_SUB_"+subType] = data;
	        		}else{
	        			dataCenter[type] = data;
	        		}
		        },  
		        error: function (data) { 
			        	top.layer.msg("数据初始化失败");
		        	}  
		    });
		}
	}
	//刷新基本信息页面
	function refreshBase(year){
		if(!isNotNull(year)){
			year = $("#year").text();
		}
		setData(year,'ORG');
		setData(year,'EMP');
		setData(year,'EMP','1');
		setData(year,'DEV');
		setData(year,'DEV','1');
		setData(year,'DEV','1','1');
		initOrg(dataCenter.ORG);
		initEmp(dataCenter.EMP);
		initEmpSub(dataCenter.EMP_SUB_1);
		initDev(dataCenter.DEV);
		initDevSub(dataCenter.DEV_SUB_1);
		initDevSub2(dataCenter.DEV_SUB_1_1);
	}
	//切换年份
	function changeYear(type){
		var year = parseInt($("#year").text());
		var curYear = parseInt(new Date().getFullYear());
		if(type=="last"){
			year = year-1;
		}else if(type=="next"){
			year = year+1;
		}
		if(year>curYear){
			year = curYear;
		}
		$("#year").text(year);
		var curTab = $(".analysis_tab2").find("span.active").attr("type");
		if(curTab=="base"){
			refreshBase(year);
		}else if(curTab=="org"){
			refreshOrg(year);
		}else if(curTab=="emp"){
			refreshEmp();
		}else if(curTab=="dev"){
			refreshDev(year);
		}
	}
</script>
	<div class="p10" id="AutoTable">	
		<h2 class="analysis_tab2 f16 line_bot">
            <span type="base">基本情况</span>
            <span type="org">党组织情况</span>
            <span type="emp">党员情况</span>
            <span type="dev">发展党员情况</span>
	     </h2>
	     <h5 class="Basic_title">
	     	<span class="s1"><a href="javascript:void(0)" onclick="changeYear('last')"><i class="iconfont f12">&#xe63e;</i>上一年</a></span>
	     	<span id="year">2017</span>年基本情况
	     	<span class="s2"><a href="javascript:void(0)" onclick="changeYear('next')">下一年<i class="iconfont f12">&#xe63c;</i></a></span>
     	</h5>
		 <div name="AutoContent">
   		 <!-- 基本情况 -->
		 <div class="p10 jepor State">	 		
		 	<dl>
		    	<dt>一、党组织情况</dt>
		     	<p>已建立党的基层组织<span class="orgTotalNum">0</span>个。
		     		<br>其中，党委<span class="comNum">0</span>个，占<span class="comRatio">0</span>%；
		     			党总支<span class="genBranchNum">0</span>个，占<span class="genBranchRatio">0</span>%；
		     			直属党支部<span class="branchNum">0</span>个，占<span class="branchRatio">0</span>%；
		     			党工委<span class="otherNum">0</span>个，占<span class="otherRatio">0</span>%。
		     		<br>其中，在职教职工党支部<span class="inTeacherBranchNum">0</span>个，占<span class="inTeacherBranchRatio">0</span>%；
		     			学生党支部<span class="studentBranchNum">0</span>个，占<span class="studentBranchRatio">0</span>%；
		     			离退休党支部<span class="retireBranchNum">0</span>个，占<span class="retireBranchRatio">0</span>%；
		     			师生共建党支部<span class="tAndSBranchNum">0</span>个，占<span class="tAndSBranchRatio">0</span>%；
		     			其他党支部<span class="otherBranchNum">0</span>个，占<span class="otherBranchRatio">0</span>%。
		     		<br>其中，在校学生本科党支部<span class="underStudentBranchNum">0</span>个，占<span class="underStudentBranchRatio">0</span>%；
		     			在校学生研究生党支部<span class="postStudentBranchNum">0</span>个，占<span class="postStudentBranchRatio">0</span>%；
		     			在校学生本研共建党支部<span class="underAndPostBranchNum">0</span>个，占<span class="underAndPostBranchRatio">0</span>%；
		     			学生暂缓就业党支部<span class="reprieveBranchNum">0</span>个，占<span class="reprieveBranchRatio">0</span>%。
   				</p>
		     	<dt>二、党员情况</dt>
		     	<p>党员总数<span class="empTotalNum">0</span>人，失联党员<span class="disContactNum">0</span>人。
			     	<br>其中，在职教职工党员<span class="inTchEmpNum">0</span>人，占<span class="inTchEmpRatio">0</span>%
			     		（专任教师党员<span class="fullTchEmpNum">0</span>人，35岁及以下的有<span class="under35TchEmpNum">0</span>人）；
			     	<br>&#12288;&#12288;&nbsp;&nbsp;
			     		在校学生党员<span class="inStuEmpNum">0</span>人，占<span class="inStuEmpRatio">0</span>%
			     		（在校本科生党员<span class="inUnderEmpNum">0</span>人，在校研究生党员<span class="inPostEmpNum">0</span>人）；
			     	<br>&#12288;&#12288;&nbsp;&nbsp;
			     		离退休党员<span class="retireEmpNum">0</span>人，占<span class="retireEmpRatio">0</span>%；
			     		其他党员<span class="otherEmpNum">0</span>人，占<span class="otherEmpRatio">0</span>%。
			     	<br>其中，预备党员<span class="preEmpNum">0</span>人，占<span class="preEmpRatio">0</span>%；
			     		正式党员<span class="fullEmpNum">0</span>人，占<span class="fullEmpRatio">0</span>%。
			     	<br>其中，男党员<span class="maleEmpNum">0</span>人，占<span class="maleEmpRatio">0</span>%；
			     		女党员<span class="femaleEmpNum">0</span>人，占<span class="femaleEmpRatio">0</span>%。
			     	<br>其中，汉族<span class="hanEmpNum">0</span>人，占<span class="hanEmpRatio">0</span>%；
			     		少数民族<span class="minEmpNum">0</span>人，占<span class="minEmpRatio">0</span>%。
			     	<br>专任教师中，正高级<span class="zgTchEmpNum">0</span>人，占<span class="zgTchEmpRatio">0</span>%；
			     		副高级<span class="fgTchEmpNum">0</span>人，占<span class="fgTchEmpRatio">0</span>%；
			     		中级<span class="zjTchEmpNum">0</span>人，占<span class="zjTchEmpRatio">0</span>%；
			     		助理级<span class="zlTchEmpNum">0</span>人，占<span class="zlTchEmpRatio">0</span>%。
		     	</p>
		     	<dt>三、发展党员情况</dt>
		     	<p>发展党员总数<span class="devTotalNum">0</span>人。
			     	<br>其中，入党申请人<span class="applyDepNum">0</span>人，占<span class="applyDepRatio">0</span>%；
			     		入党积极分子<span class="activeDepNum">0</span>人，占<span class="activeDepRatio">0</span>%；
			     		发展对象<span class="devDepNum">0</span>人，占<span class="devDepRatio">0</span>%。
			     	<br>其中，在职教职工发展党员<span class="inTchDepNum">0</span>人，占<span class="inTchDepRatio">0</span>%
			     		（专任教师发展党员<span class="fullTchDepNum">0</span>人，35岁及以下的有<span class="under35TchDepNum">0</span>人）；
			     	<br>&#12288;&#12288;&nbsp;&nbsp;
			     		在校学生发展党员<span class="inStuDepNum">0</span>人，占<span class="inStuDepRatio">0</span>%
			     		（在校本科生发展党员<span class="inUnderDepNum">0</span>人，在校研究生发展党员<span class="inPostDepNum">0</span>人）；
			     	<br>&#12288;&#12288;&nbsp;&nbsp;
			     		离退休发展党员<span class="retireDepNum">0</span>人，占<span class="retireDepRatio">0</span>%；
			     		其他发展党员<span class="otherDepNum">0</span>人，占<span class="otherDepRatio">0</span>%。
			     	<br>其中，男党员<span class="maleDepNum">0</span>人，占<span class="maleDepRatio">0</span>%；
			     		女党员<span class="femaleDepNum">0</span>人，占<span class="femaleDepRatio">0</span>%。
			     	<br>其中，汉族<span class="hanDepNum">0</span>人，占<span class="hanDepRatio">0</span>%；
			     		少数民族<span class="minDepNum">0</span>人，占<span class="minDepRatio">0</span>%。
			     	<br>专任教师中，正高级<span class="zgTchDepNum">0</span>人，占<span class="zgTchDepRatio">0</span>%；
			     		副高级<span class="fgTchDepNum">0</span>人，占<span class="fgTchDepRatio">0</span>%；
			     		中级<span class="zjTchDepNum">0</span>人，占<span class="zjTchDepRatio">0</span>%；
			     		助理级<span class="zlTchDepNum">0</span>人，占<span class="zlTchDepRatio">0</span>%。
		     	</p>
		    </dl>
		 </div>
		 <!-- 基本情况  end-->
		 <%@include file="sysIndexOrgTab.jsp"%>
		 <%@include file="sysIndexEmpTab.jsp"%>
		 <%@include file="sysIndexDevTab.jsp"%>
		 </div>
	</div>
