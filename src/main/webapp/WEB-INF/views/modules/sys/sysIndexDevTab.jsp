<%@ page contentType="text/html;charset=UTF-8" %>
<script src="${ctxStatic}/echarts/echarts.min.js" type="text/javascript"></script>
<!--首页统计tab切换内容-->
<script type="text/javascript">
	$(document).ready(function () {
		$("#sub1Type,#sub2Type").change(function(){
			refreshDev();
		});
	});
	//图表
    function createDev(data){
  		var myChart9 = echarts.init(document.getElementById("echarts_dev"));
  	    var option9 = {
  				title: {
				        text: '发展党员总数',
				        subtext: data.TOTAL_NUM+'人',
				        x: 'center',
				        y: 'center',
				        textStyle: {fontWeight: 'normal',fontSize: 18}
				    },
				    legend: {
				    	orient: 'vertical',
					    left: 'left',
				        data: ['入党申请人,'+data.APPLY_DEP_NUM+','+data.APPLY_DEP_RATIO+'%',
				               '入党积极分子,'+data.ACTIVE_DEP_NUM+','+data.ACTIVE_DEP_RATIO+'%',
				               '发展对象,'+data.DEV_DEP_NUM+','+data.DEV_DEP_RATIO+'%'],     
				        itemWidth: 20,
				        itemHeight: 10
				    }, 
				    tooltip: {trigger: 'item',formatter: "{a} <br/>{b}: {c} ({d}%)"},
  			    grid: {left: '0%',right: '0%',bottom: '15%',top:'10',containLabel: 'true'},
  			    series: [
  			             {
  			                 name:'发展党员情况',
  			                 type:'pie',
  			                 radius: ['50%', '70%'],
  			                 avoidLabelOverlap: false,
  			                 label: {
  			                     normal: {show: false,position: 'center'},
  			                     emphasis: {show: false,textStyle: {fontSize: '30',fontWeight: 'bold'}}
  			                 },
  			                 labelLine: {normal: {show: false}},
  			                 data:[
  			                     {value:data.APPLY_DEP_NUM, name:'入党申请人,'+data.APPLY_DEP_NUM+','+data.APPLY_DEP_RATIO+'%'},
  			                     {value:data.ACTIVE_DEP_NUM, name:'入党积极分子,'+data.ACTIVE_DEP_NUM+','+data.ACTIVE_DEP_RATIO+'%'},
  			                     {value:data.DEV_DEP_NUM, name:'发展对象,'+data.DEV_DEP_NUM+','+data.DEV_DEP_RATIO+'%'},
  			                 ]
  			             }
  			         ]
  	   };
  	   myChart9.setOption(option9);
  	} 
    function createPersonal(data){
  		var myChart10 = echarts.init(document.getElementById("echarts_personal"));
  	    var option10 = {
				    tooltip : {trigger: 'item',formatter: "{a} <br/>{b} : {c} ({d}%)"},
				    legend: {
				        orient: 'vertical',
				        left: 'left',
				        data: ['在职教职工发展党员,'+data.IN_TCH_DEP_NUM+','+data.IN_TCH_DEP_RATIO+'%',
				               '在校学生发展党员,'+data.IN_STU_DEP_NUM+','+data.IN_STU_DEP_RATIO+'%',
				               '离退休发展党员,'+data.RETIRE_DEP_NUM+','+data.RETIRE_DEP_RATIO+'%',
				               '其他发展党员,'+data.OTHER_DEP_NUM+','+data.OTHER_DEP_RATIO+'%']       
				    },
				    series : [
				        {
				            name: '个人身份情况',
				            type: 'pie',
				            radius : '50%',
				            center: ['60%', '50%'],
				            label: {
			                	normal: {show: false,position: 'center'},
	                     		emphasis: {show: false,textStyle: {fontSize: '30',fontWeight: 'bold'}}
			                 },
			                 labelLine: {normal: {show: false}},
				            data:[
				                {value:data.IN_TCH_DEP_NUM, name:'在职教职工发展党员,'+data.IN_TCH_DEP_NUM+','+data.IN_TCH_DEP_RATIO+'%'},
				                {value:data.IN_STU_DEP_NUM, name:'在校学生发展党员,'+data.IN_STU_DEP_NUM+','+data.IN_STU_DEP_RATIO+'%'},
				                {value:data.RETIRE_DEP_NUM, name:'离退休发展党员,'+data.RETIRE_DEP_NUM+','+data.RETIRE_DEP_RATIO+'%'},
				                {value:data.OTHER_DEP_NUM, name:'其他发展党员,'+data.OTHER_DEP_NUM+','+data.OTHER_DEP_RATIO+'%'},
				            ],
				            itemStyle: {
				                emphasis: {
				                    shadowBlur: 10,
				                    shadowOffsetX: 0,
				                    shadowColor: 'rgba(0, 0, 0, 0.5)'
				                }
				            }
				        }
				    ]
  	   };
  	   myChart10.setOption(option10);
  	} 
    function createDevSex(data){
  		var myChart11 = echarts.init(document.getElementById("echarts_devSex"));
  	    var option11 = {
				    tooltip : {trigger: 'item',formatter: "{a} <br/>{b} : {c} ({d}%)"},
				    legend: {
				        orient: 'vertical',
				        left: 'left',
				        data: ['男,'+data.MALE_DEP_NUM+','+data.MALE_DEP_RATIO+'%',
				               '女,'+data.FEMALE_DEP_NUM+','+data.FEMALE_DEP_RATIO+'%']
				    },
				    series : [
				        {
				            name: '男女情况',
				            type: 'pie',
				            radius : '40%',
				            center: ['50%', '50%'],
				            data:[
				                {value:data.MALE_DEP_NUM, name:'男,'+data.MALE_DEP_NUM+','+data.MALE_DEP_RATIO+'%'},
				                {value:data.FEMALE_DEP_NUM, name:'女,'+data.FEMALE_DEP_NUM+','+data.FEMALE_DEP_RATIO+'%'},
				            ],
				            itemStyle: {emphasis: {shadowBlur: 10,shadowOffsetX: 0,shadowColor: 'rgba(0, 0, 0, 0.5)'}}
				        }
				    ]
  	   };
  	   myChart11.setOption(option11);
  	} 
    function createDevNation(data){
  		var myChart12 = echarts.init(document.getElementById("echarts_devNation"));
  	    var option12 = {
				    tooltip : {trigger: 'item',formatter: "{a} <br/>{b} : {c} ({d}%)"},
				    legend: {
				        orient: 'vertical',
				        left: 'left',
				        data: ['汉族,'+data.HAN_DEP_NUM+','+data.HAN_DEP_RATIO+'%',
				               '少数民族,'+data.MIN_DEP_NUM+','+data.MIN_DEP_RATIO+'%']
				    },
				    series : [
				        {
				            name: '少数民族情况',
				            type: 'pie',
				            radius : '40%',
				            center: ['50%', '50%'],
				            data:[
				                {value:data.HAN_DEP_NUM, name:'汉族,'+data.HAN_DEP_NUM+','+data.HAN_DEP_RATIO+'%'},
				                {value:data.MIN_DEP_NUM, name:'少数民族,'+data.MIN_DEP_NUM+','+data.MIN_DEP_RATIO+'%'},
				            ],
				            itemStyle: {emphasis: {shadowBlur: 10,shadowOffsetX: 0,shadowColor: 'rgba(0, 0, 0, 0.5)'}}
				        }
				    ]
  	   };
  	   myChart12.setOption(option12);
  	} 
    function createDevPostLevel(data){
  		var myChart13 = echarts.init(document.getElementById("echarts_devPostLevel"));
  	    var option13 = {
  	    		tooltip : {trigger: 'item',formatter: "{a} <br/>{b} : {c} ({d}%)"},
				    legend: {
				        orient: 'vertical',
				        left: 'left',
				        data: ['正高,'+data.ZG_TCH_DEP_NUM+','+data.ZG_TCH_DEP_RATIO+'%',
				               '副高,'+data.FG_TCH_DEP_NUM+','+data.FG_TCH_DEP_RATIO+'%',
				               '中级,'+data.ZJ_TCH_DEP_NUM+','+data.ZJ_TCH_DEP_RATIO+'%',
				               '助理级,'+data.ZL_TCH_DEP_NUM+','+data.ZL_TCH_DEP_RATIO+'%']
				    },
				    series : [
				        {
				            name: '职级情况（专任教师）',
				            type: 'pie',
				            radius : '40%',
				            center: ['50%', '50%'],
				            label: {
			                	normal: {show: false,position: 'center'},
	                     		emphasis: {show: false,textStyle: {fontSize: '30',fontWeight: 'bold'}}
			                 },
			                 labelLine: {normal: {show: false}},
				            data:[
				                {value:data.ZG_TCH_DEP_NUM, name:'正高,'+data.ZG_TCH_DEP_NUM+','+data.ZG_TCH_DEP_RATIO+'%'},
				                {value:data.FG_TCH_DEP_NUM, name:'副高,'+data.FG_TCH_DEP_NUM+','+data.FG_TCH_DEP_RATIO+'%'},
				                {value:data.ZJ_TCH_DEP_NUM, name:'中级,'+data.ZJ_TCH_DEP_NUM+','+data.ZJ_TCH_DEP_RATIO+'%'},
				                {value:data.ZL_TCH_DEP_NUM, name:'助理级,'+data.ZL_TCH_DEP_NUM+','+data.ZL_TCH_DEP_RATIO+'%'},
				            ],
				            itemStyle: {emphasis: {shadowBlur: 10,shadowOffsetX: 0,shadowColor: 'rgba(0, 0, 0, 0.5)'}}
				        }
				    ]
  	   };
  	   myChart13.setOption(option13);
  	}
    function initDev(data){
		$(".devTotalNum").text(data.TOTAL_NUM);
		$(".applyDepNum").text(data.APPLY_DEP_NUM);
		$(".applyDepRatio").text(data.APPLY_DEP_RATIO);
		$(".activeDepNum").text(data.ACTIVE_DEP_NUM);
		$(".activeDepRatio").text(data.ACTIVE_DEP_RATIO);
		$(".devDepNum").text(data.DEV_DEP_NUM);
		$(".devDepRatio").text(data.DEV_DEP_RATIO);
    }
    function initDevSub(data,subType){
    	var str = "";
    	if(isNotNull(subType)){
    		str="_sub";
    	}
		$(".inTchDepNum"+str).text(data.IN_TCH_DEP_NUM);
		$(".inTchDepRatio"+str).text(data.IN_TCH_DEP_RATIO);
		$(".fullTchDepNum"+str).text(data.FULL_TCH_DEP_NUM);
		$(".under35TchDepNum"+str).text(data.UNDER_35_TCH_DEP_NUM);
		$(".inStuDepNum"+str).text(data.IN_STU_DEP_NUM);
		$(".inStuDepRatio"+str).text(data.IN_STU_DEP_RATIO);
		$(".inUnderDepNum"+str).text(data.IN_UNDER_DEP_NUM);
		$(".inPostDepNum"+str).text(data.IN_POST_DEP_NUM);
		$(".retireDepNum"+str).text(data.RETIRE_DEP_NUM);
		$(".retireDepRatio"+str).text(data.RETIRE_DEP_RATIO);
		$(".otherDepNum"+str).text(data.OTHER_DEP_NUM);
		$(".otherDepRatio"+str).text(data.OTHER_DEP_RATIO);
    }
    function initDevSub2(data,subType){
    	var str = "";
    	if(isNotNull(subType)){
    		str="_sub";
    	}
    	$(".maleDepNum"+str).text(data.MALE_DEP_NUM);
    	$(".maleDepRatio"+str).text(data.MALE_DEP_RATIO);
    	$(".femaleDepNum"+str).text(data.FEMALE_DEP_NUM);
    	$(".femaleDepRatio"+str).text(data.FEMALE_DEP_RATIO);
    	$(".hanDepNum"+str).text(data.HAN_DEP_NUM);
    	$(".hanDepRatio"+str).text(data.HAN_DEP_RATIO);
    	$(".minDepNum"+str).text(data.MIN_DEP_NUM);
    	$(".minDepRatio"+str).text(data.MIN_DEP_RATIO);
    	$(".zgTchDepNum"+str).text(data.ZG_TCH_DEP_NUM);
    	$(".zgTchDepRatio"+str).text(data.ZG_TCH_DEP_RATIO);
    	$(".fgTchDepNum"+str).text(data.FG_TCH_DEP_NUM);
    	$(".fgTchDepRatio"+str).text(data.FG_TCH_DEP_RATIO);
    	$(".zjTchDepNum"+str).text(data.ZJ_TCH_DEP_NUM);
    	$(".zjTchDepRatio"+str).text(data.ZJ_TCH_DEP_RATIO);
    	$(".zlTchDepNum"+str).text(data.ZL_TCH_DEP_NUM);
    	$(".zlTchDepRatio"+str).text(data.ZL_TCH_DEP_RATIO);
    }
    function refreshDev(year){
    	var sub1Type = $("#sub1Type").val();
    	var sub2Type = $("#sub2Type").val();
    	if(!isNotNull(year)){
			year = $("#year").text();
		}
    	setData(year,'DEV');
    	setData(year,'DEV',sub1Type);
    	setData(year,'DEV',sub1Type,sub2Type);
    	initDev(dataCenter.DEV);
    	initDevSub(dataCenter['DEV_SUB_'+sub1Type],sub1Type);
    	initDevSub2(dataCenter['DEV_SUB_'+sub1Type+'_'+sub2Type],sub2Type);
    	createDev(dataCenter.DEV);
      	createPersonal(dataCenter['DEV_SUB_'+sub1Type]);
      	createDevSex(dataCenter['DEV_SUB_'+sub1Type+'_'+sub2Type]);
      	createDevNation(dataCenter['DEV_SUB_'+sub1Type+'_'+sub2Type]);
      	createDevPostLevel(dataCenter['DEV_SUB_'+sub1Type+'_'+sub2Type]);
    }
</script>
<!-- 发展党员情况 -->
<div class="p10 jepor State">
	<h5 class="Basic_title"></h5>
 	<div class="personChart_fl jew49 fl" style="height:325px;">
	 	<div class="line_bot p10 f14 jepoa">                          
			 <select id="sub1Type" class="select2-choice">
			     <option value="1">发展党员</option>          
			     <option value="2">&#12288;1-入党申请人</option>
			     <option value="3">&#12288;2-入党积极分子</option>
			     <option value="4">&#12288;3-发展对象</option>
			 </select>
		 </div>
	     <h3 class="personChart_tab f16 tc">发展党员情况<span class="fr"><i class="t1 iconfont mr5 acur">&#xe66f;</i><i class="t2 iconfont mr5">&#xe63f;</i></span></h3>
	     <!--图表-->
	     <div id="echarts_dev" class="echarts" style="height:285px;"></div>
	     <!--图表 End-->
	     <!--表格-->
		 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="analysis_table" style="display:none;">
			  <tr class="bg-F8">
				    <td class="noString">分类</td>
				    <td>人数</td>
				    <td class="noWing">占比</td>
			  </tr>
			  <tr>
				    <td class="noString">入党申请人</td>
				    <td><span class="applyDepNum">0</span></td>
				    <td class="noWing"><span class="applyDepRatio">0</span>%</td>
			  </tr>
			  <tr>
				    <td class="noString">入党积极分子</td>
				    <td><span class="activeDepNum">0</span></td>
				    <td class="noWing"><span class="activeDepRatio">0</span>%</td>
			  </tr>
			  <tr>
				    <td class="noString">发展对象</td>
				    <td><span class="devDepNum">0</span></td>
				    <td class="noWing"><span class="devDepRatio">0</span>%</td>
			  </tr>
		  </table> 
		 <!--表格 End-->
	 </div>
	 <!--切换图表 2-->  
 	 <div class="personChart_fr jew49 fl jepor" style="height:325px;">   
     	<div class="line_bot p10 f14 jepoa">                          
			 <select id="sub2Type" class="select2-choice">
			     <option value="1">全部</option>
			     <option value="2">&#12288;1-在职教职工</option>
			     <option value="3">&#12288;&#12288;11-专任教师</option>
			     <option value="4">&#12288;&#12288;11-35岁及以下专任教师</option>
			     <option value="5">&#12288;2-在校学生</option>
			     <option value="6">&#12288;&#12288;21-在校本科生</option>
			     <option value="7">&#12288;&#12288;22-在校研究生生</option>
			     <option value="8">&#12288;3-离退休人员</option>
			     <option value="9">&#12288;4-其他人员</option>
			 </select>
		 </div>
		 <!--各类情况-->
         <div class="line_bot p10 f12 jepoa" style="left:5px; top: 50%;">                          
			 <ul name="" class="">
			     <li>在校本科生党员：<span class="inUnderDepNum_sub">0</span>人</li>
			     <li>在校研究生党员：<span class="inPostDepNum_sub">0</span>人</li>
			     <li>专任教师党员：<span class="fullTchDepNum_sub">0</span>人</li>
			     <li>35岁及一下：<span class="under35TchDepNum_sub">0</span>人</li>
			 </ul>
		 </div>
		 <!--各类情况 End-->
         <h3 class="personChart_tab f16 tc">个人身份情况<span class="fr"><i class="t1 iconfont mr5 acur">&#xe66f;</i><i class="t2 iconfont mr5">&#xe63f;</i></span></h3>
         <!--图表-->
         <div id="echarts_personal" class="echarts" style="height:285px;"></div>
         <!--图表 End-->
         <!--表格-->
		  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="analysis_table" style="display:none;">
			  <tr class="bg-F8">
				    <td class="noString">分类</td>
				    <td>人数</td>
				    <td class="noWing">占比</td> 
			  </tr>
			  <tr>
				    <td class="noString">在职教职工</td>
				    <td><span class="inTchDepNum_sub">0</span></td>
				    <td class="noWing"><span class="inTchDepRatio_sub">0</span>%</td> 
			  </tr>
			  <tr>
				    <td class="noString">在校学生</td>
				    <td><span class="inStuDepNum_sub">0</span></td>
				    <td class="noWing"><span class="inStuDepRatio_sub">0</span>%</td> 
			  </tr>
			  <tr>
				    <td class="noString">离退休人员</td>
				    <td><span class="retireDepNum_sub">0</span></td>
				    <td class="noWing"><span class="retireDepRatio_sub">0</span>%</td> 
			  </tr>
			  <tr>
				    <td class="noString">其他</td>
				    <td><span class="otherDepNum_sub">0</span></td>
				    <td class="noWing"><span class="otherDepRatio_sub">0</span>%</td> 
			  </tr>
		  </table> 
          <!--表格 End-->
       	 </div>   
		 <div class="space10"></div>
	     <div class="personChart_fr jew33 fl" style="height:285px;" >      
         	<h3 class="personChart_tab f16 tc">男女情况<span class="fr"><i class="t1 iconfont mr5 ">&#xe66f;</i><i class="t2 iconfont mr5 acur">&#xe63f;</i></span></h3>
            <!--图表-->
            <div id="echarts_devSex" class="echarts" style="height:245px; display:none;" ></div>
            <!--图表 End-->
            <!--表格-->
			  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="analysis_table mt10" >
				  <tr class="bg-F8">
					    <td class="noString">分类</td>
					    <td>人数</td>
					    <td class="noWing">占比</td>
				  </tr>
				  <tr>
					    <td class="noString">男</td>
					    <td><span class="maleDepNum_sub">0</span></td>
					    <td class="noWing"><span class="maleDepRatio_sub">0</span>%</td>
				  </tr>
				  <tr>
					    <td class="noString">女</td>
					    <td><span class="femaleDepNum_sub">0</span></td>
					    <td class="noWing"><span class="femaleDepRatio_sub">0</span>%</td>
				  </tr>
			  </table> 
              <!--表格 End-->
           </div>
           <div class="personChart_fr jew33 fl" style="height:285px;" >      
	           <h3 class="personChart_tab f16 tc">少数民族情况<span class="fr"><i class="t1 iconfont mr5 ">&#xe66f;</i><i class="t2 iconfont mr5 acur">&#xe63f;</i></span></h3>
	           <!--图表-->
	           <div id="echarts_devNation" class="echarts" style="height:245px; display:none;"></div>
	           <!--图表 End-->
	           <!--表格-->
				  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="analysis_table mt10">
					  <tr class="bg-F8">
						    <td class="noString">分类</td>
						    <td>人数</td>
						    <td class="noWing">占比</td>
					  </tr>
					  <tr>
						    <td class="noString">汉族</td>
						    <td><span class="hanDepNum_sub">0</span></td>
						    <td class="noWing"><span class="hanDepRatio_sub">0</span>%</td>
					  </tr>
					  <tr>
						    <td class="noString">少数民族</td>
						    <td><span class="minDepNum_sub">0</span></td>
						    <td class="noWing"><span class="minDepRatio_sub">0</span>%</td>
					  </tr>
				  </table> 
	              <!--表格 End-->
       		</div>   
            <div class="personChart_fr jew33 fl" style="height:285px;" >      
            	<h3 class="personChart_tab f16 tc">职级情况（专任教师）<span class="fr"><i class="t1 iconfont mr5 ">&#xe66f;</i><i class="t2 iconfont mr5 acur">&#xe63f;</i></span></h3>
                <!--图表-->
                <div id="echarts_devPostLevel" class="echarts" style="height:245px; display:none;"></div>
                <!--图表 End-->
                <!--表格-->
				  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="analysis_table mt10">
					  <tr class="bg-F8">
						    <td class="noString">分类</td>
						    <td>人数</td>
						    <td class="noWing">占比</td>
					  </tr>
					  <tr>
						    <td class="noString">正高级</td>
						    <td><span class="zgTchDepNum_sub">0</span></td>
						    <td class="noWing"><span class="zgTchDepRatio_sub">0</span>%</td>
					  </tr>
					  <tr>
						    <td class="noString">副高级</td>
						    <td><span class="fgTchDepNum_sub">0</span></td>
						    <td class="noWing"><span class="fgTchDepRatio_sub">0</span>%</td>
					  </tr>
					  <tr>
						    <td class="noString">中级</td>
						    <td><span class="zjTchDepNum_sub">0</span></td>
						    <td class="noWing"><span class="zjTchDepRatio_sub">0</span>%</td>
					  </tr>
					  <tr>
						    <td class="noString">助理级</td>
						    <td><span class="zlTchDepNum_sub">0</span></td>
						    <td class="noWing"><span class="zlTchDepRatio_sub">0</span>%</td>
					  </tr>
				  </table> 
                  <!--表格 End-->
               </div>   
               <div class="clear"></div>                              
         </div>
<!-- 发展党员情况  end-->