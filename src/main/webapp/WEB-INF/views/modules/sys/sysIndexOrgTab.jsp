<%@ page contentType="text/html;charset=UTF-8" %>
<script src="${ctxStatic}/echarts/echarts.min.js" type="text/javascript"></script>
<!--首页统计tab切换内容-->
<script type="text/javascript">
	$(document).ready(function () {
	});
	function createBaseOrg(orgData){
  		var myChart1 = echarts.init(document.getElementById("echarts_baseOrg"));
  	   	var option1 = {
  				title: {
				        text: '基层党组织总数',
				        subtext: orgData.TOTAL_NUM+'个',
				        x: 'center',
				        y: 'center',
				        textStyle: {fontWeight: 'normal',fontSize: 18}
				    },
			    legend: {
			    	orient: 'vertical',
				    left: 'left',
			        data: ['党委,'+orgData.COM_NUM+','+orgData.COM_RATIO+'%',
			               '党总支,'+orgData.GEN_BRANCH_NUM+','+orgData.GEN_BRANCH_RATIO+'%', 
			               '直属党支部,'+orgData.BRANCH_NUM+','+orgData.BRANCH_RATIO+'%',
			               '党工委,'+orgData.OTHER_NUM+','+orgData.OTHER_RATIO+'%'],
			        itemWidth: 20,
			        itemHeight: 10
			    }, 
			    tooltip: {trigger: 'item',formatter: "{a} <br/>{b}: {c} ({d}%)"},
  			    grid: {left: '0%',right: '0%',bottom: '15%',top:'10',containLabel: true},
  			    series: [
  			             {
  			                 name:'基层党组织情况',
  			                 type:'pie',
  			                 radius: ['50%', '70%'],
  			                 avoidLabelOverlap: false,
  			                 label: {
  			                     normal: {show: false,position: 'center'},
  			                     emphasis: {show: false,textStyle: {fontSize: '30',fontWeight: 'bold'}}
  			                 },
  			                 labelLine: {normal: {show: false}},
  			                 data:[
  			                     {value:orgData.COM_NUM, name:'党委,'+orgData.COM_NUM+','+orgData.COM_RATIO+'%'},
  			                     {value:orgData.GEN_BRANCH_NUM, name:'党总支,'+orgData.GEN_BRANCH_NUM+','+orgData.GEN_BRANCH_RATIO+'%'},
  			                     {value:orgData.BRANCH_NUM, name:'直属党支部,'+orgData.BRANCH_NUM+','+orgData.BRANCH_RATIO+'%'},
  			                     {value:orgData.OTHER_NUM, name:'党工委,'+orgData.OTHER_NUM+','+orgData.OTHER_RATIO+'%'},
  			                 ]
  			             }
  			         ]
  	   };
  	   myChart1.setOption(option1);
  	} 
    function createBranch(orgData){
  		var myChart2 = echarts.init(document.getElementById("echarts_branch"));
  	   var option2 = {
  				title: {
				        text: '党支部情况',
				        subtext: orgData.BRANCH_NUM+'个',
				        x: 'center',
				        y: 'center',
				        textStyle: {fontWeight: 'normal',fontSize: 13}
				    },
			    legend: {
			    	orient: 'vertical',
				    left: 'left',
			        data: ['在职教职工,'+orgData.IN_TEACHER_BRANCH_NUM+','+orgData.IN_TEACHER_BRANCH_RATIO+'%',
			               '学生,'+orgData.STUDENT_BRANCH_NUM+','+orgData.STUDENT_BRANCH_RATIO+'%',
			               '离退休人员,'+orgData.RETIRE_BRANCH_NUM+','+orgData.RETIRE_BRANCH_RATIO+'%',
			               '师生共建,'+orgData.T_AND_S_BRANCH_NUM+','+orgData.T_AND_S_BRANCH_RATIO+'%',
			               '其他,'+orgData.OTHER_BRANCH_NUM+','+orgData.OTHER_BRANCH_RATIO+'%'],
			        itemWidth: 20,
			        itemHeight: 10
			    }, 
			    tooltip: {trigger: 'item',formatter: "{a} <br/>{b}: {c} ({d}%)"},
  			    grid: {left: '2%',right: '0%',bottom: '10%',top:'5',containLabel: true},
  			    series: [
			             {
			                 name:'党支部情况',
			                 type:'pie',
			                 radius: ['50%', '80%'],
			                 avoidLabelOverlap: false,
			                 label: {
			                     normal: {show: false,position: 'center'},
			                     emphasis: {show: false,textStyle: {fontSize: '30',fontWeight: 'bold'}}
			                 },
			                 labelLine: {normal: {show: false}},
			                 data:[
			                     {value:orgData.IN_TEACHER_BRANCH_NUM, name:'在职教职工,'+orgData.IN_TEACHER_BRANCH_NUM+','+orgData.IN_TEACHER_BRANCH_RATIO+'%'},
			                     {value:orgData.STUDENT_BRANCH_NUM, name:'学生,'+orgData.STUDENT_BRANCH_NUM+','+orgData.STUDENT_BRANCH_RATIO+'%'},
			                     {value:orgData.RETIRE_BRANCH_NUM, name:'离退休人员,'+orgData.RETIRE_BRANCH_NUM+','+orgData.RETIRE_BRANCH_RATIO+'%'},
			                     {value:orgData.T_AND_S_BRANCH_NUM, name:'师生共建,'+orgData.T_AND_S_BRANCH_NUM+','+orgData.T_AND_S_BRANCH_RATIO+'%'},
			                     {value:orgData.OTHER_BRANCH_NUM, name:'其他,'+orgData.OTHER_BRANCH_NUM+','+orgData.OTHER_BRANCH_RATIO+'%'}
			                 ]
			             }
  			         ]
  	   };
  	   myChart2.setOption(option2);
  	} 
    function createStudentBranch(orgData){
  		var myChart3 = echarts.init(document.getElementById("echarts_studentBranch"));
  	   var option3 = {
			    title : {
			        text: '学生党支部构成',
			        x:'center',
			        y: 'top',
			        textStyle: {fontWeight: 'normal',fontSize: 14}
			    }, 
			    tooltip : {trigger: 'item',formatter: "{a} <br/>{b} : {c} ({d}%)"},
			    legend: {
			        orient: 'vertical',
			        left: 'left',
			        data: ['在校学生本科,'+orgData.UNDER_STUDENT_BRANCH_NUM+','+orgData.UNDER_STUDENT_BRANCH_RATIO+'%',
			               '在校学生研究生,'+orgData.POST_STUDENT_BRANCH_NUM+','+orgData.POST_STUDENT_BRANCH_RATIO+'%',
			               '在校学生本研工建,'+orgData.UNDER_AND_POST_BRANCH_NUM+','+orgData.UNDER_AND_POST_BRANCH_RATIO+'%',
			               '学生暂缓就业,'+orgData.REPRIEVE_BRANCH_NUM+','+orgData.REPRIEVE_BRANCH_RATIO+'%']
			    },
			    series : [{        
				            name: '学生党支部构成',
				            type: 'pie',
				            radius : '55%',
				            center: ['50%', '60%'],
				            avoidLabelOverlap: false,
				            label: {
			                     normal: {show: false,position: 'center'},
			                     emphasis: {show: false,textStyle: {fontSize: '30',fontWeight: 'bold'}}
			                 },
				            labelLine: {normal: {show: false}},
				            data:[
				                {value:orgData.UNDER_STUDENT_BRANCH_NUM, name:'在校学生本科,'+orgData.UNDER_STUDENT_BRANCH_NUM+','+orgData.UNDER_STUDENT_BRANCH_RATIO+'%'},
				                {value:orgData.POST_STUDENT_BRANCH_NUM, name:'在校学生研究生,'+orgData.POST_STUDENT_BRANCH_NUM+','+orgData.POST_STUDENT_BRANCH_RATIO+'%'},
				                {value:orgData.UNDER_AND_POST_BRANCH_NUM, name:'在校学生本研工建,'+orgData.UNDER_AND_POST_BRANCH_NUM+','+orgData.UNDER_AND_POST_BRANCH_RATIO+'%'},
				                {value:orgData.REPRIEVE_BRANCH_NUM, name:'学生暂缓就业,'+orgData.REPRIEVE_BRANCH_NUM+','+orgData.REPRIEVE_BRANCH_RATIO+'%'},
				            ],
				            itemStyle: {emphasis: {shadowBlur: 10,shadowOffsetX: 0,shadowColor: 'rgba(0, 0, 0, 0.5)'}}
			        	}]
  	   };
  	   myChart3.setOption(option3);
  	}
    function initOrg(data){
		$(".orgTotalNum").text(data.TOTAL_NUM);
		$(".comNum").text(data.COM_NUM);
		$(".comRatio").text(data.COM_RATIO);
		$(".genBranchNum").text(data.GEN_BRANCH_NUM);
		$(".genBranchRatio").text(data.GEN_BRANCH_RATIO);
		$(".branchNum").text(data.BRANCH_NUM);
		$(".branchRatio").text(data.BRANCH_RATIO);
		$(".otherNum").text(data.OTHER_NUM);
		$(".otherRatio").text(data.OTHER_RATIO);
		$(".inTeacherBranchNum").text(data.IN_TEACHER_BRANCH_NUM);
		$(".inTeacherBranchRatio").text(data.IN_TEACHER_BRANCH_RATIO);
		$(".studentBranchNum").text(data.STUDENT_BRANCH_NUM);
		$(".studentBranchRatio").text(data.STUDENT_BRANCH_RATIO);
		$(".retireBranchNum").text(data.c);
		$(".retireBranchRatio").text(data.RETIRE_BRANCH_RATIO);
		$(".tAndSBranchNum").text(data.T_AND_S_BRANCH_NUM);
		$(".tAndSBranchRatio").text(data.T_AND_S_BRANCH_RATIO);
		$(".otherBranchNum").text(data.OTHER_BRANCH_NUM);
		$(".otherBranchRatio").text(data.OTHER_BRANCH_RATIO);
		$(".underStudentBranchNum").text(data.UNDER_STUDENT_BRANCH_NUM);
		$(".underStudentBranchRatio").text(data.UNDER_STUDENT_BRANCH_RATIO);
		$(".postStudentBranchNum").text(data.POST_STUDENT_BRANCH_NUM);
		$(".postStudentBranchRatio").text(data.POST_STUDENT_BRANCH_RATIO);
		$(".underAndPostBranchNum").text(data.UNDER_AND_POST_BRANCH_NUM);
		$(".underAndPostBranchRatio").text(data.UNDER_AND_POST_BRANCH_RATIO);
		$(".reprieveBranchNum").text(data.REPRIEVE_BRANCH_NUM);
		$(".reprieveBranchRatio").text(data.REPRIEVE_BRANCH_RATIO);
	}
    function refreshOrg(year){
    	if(!isNotNull(year)){
			year = $("#year").text();
		}
    	setData(year,'ORG');
    	initOrg(dataCenter.ORG);
    	createBaseOrg(dataCenter.ORG);
      	createBranch(dataCenter.ORG);
      	createStudentBranch(dataCenter.ORG);
    }
</script>
<!-- 党组织情况 -->
<div class="p10 jepor State">
	<h5 class="Basic_title"></h5>
	<div class="personChart_fl jew49 fl" style="height:360px;">
    	<h3 class="personChart_tab f16 tc">基层党组织情况<span class="fr"><i class="t1 iconfont mr5 acur">&#xe66f;</i><i class="t2 iconfont mr5">&#xe63f;</i></span></h3>
        <!--图表-->
        <div id="echarts_baseOrg" class="echarts" style="height:360px;"></div>
        <!--图表 End-->
        <!--表格-->
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="analysis_table " style="display:none;">
			<tr class="bg-F8">
				<td class="noString">&nbsp;</td>
				<td>合计</td>
				<td>党委</td>
				<td>党总支</td>
				<td>直属党支部</td>
				<td class="noWing">党工委</td>
			</tr>
			<tr>
			    <td class="noString">数量</td>
			    <td><span class="orgTotalNum">0</span></td>
			    <td><span class="comNum">0</span></td>
			    <td><span class="genBranchNum">0</span></td>
			    <td><span class="branchNum">0</span></td>
			    <td class="noWing"><span class="otherNum">0</span></td>
		    </tr>
		    <tr>
			    <td class="noString">占比</td>
			    <td>100%</td>
			    <td><span class="comRatio">0</span>%</td>
			    <td><span class="genBranchRatio">0</span>%</td>
			    <td><span class="branchRatio">0</span>%</td>
			    <td class="noWing"><span class="otherRatio">0</span>%</td>
			</tr>
		 </table> 
		 <!--表格 End-->
		 </div>
		 <!--切换图表 2-->  
		 <div class="personChart_fl jew49 fr" style="height:360px;">      
         	<h3 class="personChart_tab f16 tc">党支部情况<span class="fr"><i class="t1 iconfont mr5 acur">&#xe66f;</i><i class="t2 iconfont mr5">&#xe63f;</i></span></h3>
            <!--图表-->
            <div id="echarts_branch" class="echarts" style="height:165px;"></div>
            <div id="echarts_studentBranch" class="echarts" style="height:160px;"></div>
            <!--图表 End-->
            <!--表格-->
		  	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="analysis_table" style="display:none;">
		  		<tr class="bg-F8">
				    <td class="noString">&nbsp;</td>
				    <td>合计</td>
				    <td>在职教职工</td>
				    <td>学生</td>
				    <td>离退休人员</td>
				    <td>师生共建</td>
				    <td class="noWing">其他</td> 
			  	</tr>
			  	<tr>
				    <td  class="noString">数量</td>
				    <td><span class="branchNum">0</span></td>
				    <td><span class="inTeacherBranchNum">0</span></td>
				    <td><span class="studentBranchNum">0</span></td>
				    <td><span class="retireBranchNum">0</span></td>
				    <td><span class="tAndSBranchNum">0</span></td>
				    <td class="noWing"><span class="otherBranchNum">0</span></td> 
			  	</tr>
			  	<tr>
				    <td  class="noString">占比</td>
				    <td>100%</td>
				    <td><span class="inTeacherBranchRatio">0</span>%</td>
				    <td><span class="studentBranchRatio">0</span>%</td>
				    <td><span class="retireBranchRatio">0</span>%</td>
				    <td><span class="tAndSBranchRatio">0</span>%</td>
				    <td class="noWing"><span class="otherBranchRatio">0</span>%</td> 
			  	</tr>
		  </table> 
          <!--表格 End-->
          <!--表格-->
		  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="analysis_table mt10" style="display:none;">
		  		<tr class="bg-F8">
				    <td class="noString">&nbsp;</td>
				    <td>合计</td>
				    <td>在校学生本科</td>
				    <td>在校学生研究生</td>
				    <td>在校学生本研共建</td>
				    <td class="noWing">学生暂缓就业</td> 
			  	</tr>
			  	<tr>
				    <td  class="noString">数量</td>
				    <td><span class="studentBranchNum">0</span></td>
				    <td><span class="underStudentBranchNum">0</span></td>
				    <td><span class="postStudentBranchNum">0</span></td>
				    <td><span class="underAndPostBranchNum">0</span></td>
				    <td class="noWing"><span class="reprieveBranchNum">0</span></td>
			  	</tr>
			  	<tr>
				    <td  class="noString">占比</td>
				    <td>100%</td>
				    <td><span class="underStudentBranchRatio">0</span>%</td>
				    <td><span class="postStudentBranchRatio">0</span>%</td>
				    <td><span class="underAndPostBranchRatio">0</span>%</td>
				    <td class="noWing"><span class="reprieveBranchRatio">0</span>%</td>
			  	</tr>
		  </table> 
          <!--表格 End-->        
	</div>   
	<div class="clear"></div>                           
</div>
<!-- 党组织情况 end-->