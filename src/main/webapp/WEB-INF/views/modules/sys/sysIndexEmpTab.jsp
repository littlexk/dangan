<%@ page contentType="text/html;charset=UTF-8" %>
<script src="${ctxStatic}/echarts/echarts.min.js" type="text/javascript"></script>
<!--首页统计tab切换内容-->
<script type="text/javascript">
	$(document).ready(function () {
	    //党员类型改变，联动更新其他自己数据
	    $("#empType").change(function(){
	    	refreshEmp();
	    });
	});
	//刷新党员情况整个页面数据
	function refreshEmp(year){
		var subType = $("#empType").val();
		if(!isNotNull(year)){
			year = $("#year").text();
		}
		setData(year,'EMP');
   		setData(year,'EMP',subType,'');
    	initEmp(dataCenter.EMP);
    	initEmpSub(dataCenter['EMP_SUB_'+subType],subType);
    	createEmp(dataCenter.EMP);
    	createEmpType(dataCenter['EMP_SUB_'+subType]);
    	createSex(dataCenter['EMP_SUB_'+subType]);
    	createNation(dataCenter['EMP_SUB_'+subType]);
    	createPostLevel(dataCenter['EMP_SUB_'+subType]);
	}
    function createEmp(data){
  		var myChart4 = echarts.init(document.getElementById("echarts_emp"));
  	    var option4 = {
	  	    		title: {
				        text: '党员总数',
				        subtext: '   '+data.TOTAL_NUM+'人',
				        x: '52%',
				        y: 'center',
				        textStyle: {fontWeight: 'normal',fontSize: 18},
				    },
				    legend: {
				    	orient: 'vertical',
					    left: 'left',
				        data: ['在职教职工党员,'+data.IN_TCH_EMP_NUM+','+data.IN_TCH_EMP_RATIO+'%',
				               '在校学生党员,'+data.IN_STU_EMP_NUM+','+data.IN_STU_EMP_RATIO+'%',
				               '离退休党员,'+data.RETIRE_EMP_NUM+','+data.RETIRE_EMP_RATIO+'%',
				               '其他党员,'+data.OTHER_EMP_NUM+','+data.OTHER_EMP_RATIO+'%'],
				        itemWidth: 20,
				        itemHeight: 10
				    }, 
				    tooltip: {trigger: 'item',formatter: "{a} <br/>{b}: {c} ({d}%)"},
  			    grid: {left: '0%',right: '0%',bottom: '15%',top:'10',containLabel: 'true'},
  			    series: [
  			             {
  			                 name:'党员情况',
  			                 type:'pie',
  			                 center: ['60%', '50%'],
  			                 radius: ['50%', '70%'],
  			                 avoidLabelOverlap: false,
  			                 label: {normal: {show: false,position: 'center'},
		                     		emphasis: {show: false,textStyle: {fontSize: '30',fontWeight: 'bold'}}
  			                 },
  			                 labelLine: {normal: {show: false}},
  			                 data:[
  			                     {value:data.IN_TCH_EMP_NUM, name:'在职教职工党员,'+data.IN_TCH_EMP_NUM+','+data.IN_TCH_EMP_RATIO+'%'},
  			                     {value:data.IN_STU_EMP_NUM, name:'在校学生党员,'+data.IN_STU_EMP_NUM+','+data.IN_STU_EMP_RATIO+'%'},
  			                     {value:data.RETIRE_EMP_NUM, name:'离退休党员,'+data.RETIRE_EMP_NUM+','+data.RETIRE_EMP_RATIO+'%'},
  			                     {value:data.OTHER_EMP_NUM, name:'其他党员,'+data.OTHER_EMP_NUM+','+data.OTHER_EMP_RATIO+'%'},
  			                 ]
  			             }
  			         ]
  	   };
  	   myChart4.setOption(option4);
  	} 
    //图表
    function createEmpType(data){
  		var myChart5 = echarts.init(document.getElementById("echarts_empType"));
  	    var option5 = {
				    tooltip : {trigger: 'item',formatter: "{a} <br/>{b} : {c} ({d}%)"},
				    legend: {
				        orient: 'vertical',
				        left: 'left',
				        data: ['预备党员,'+data.PRE_EMP_NUM+','+data.PRE_EMP_RATIO+'%',
				               '正式党员 ,'+data.FULL_EMP_NUM+','+data.FULL_EMP_RATIO+'%']
				    },
				    series : [
				        {
				            name: '党员类型情况',
				            type: 'pie',
				            radius : '65%',
				            center: ['50%', '50%'],
				            data:[
				                {value:data.PRE_EMP_NUM, name:'预备党员,'+data.PRE_EMP_NUM+','+data.PRE_EMP_RATIO+'%'},
				                {value:data.FULL_EMP_NUM, name:'正式党员 ,'+data.FULL_EMP_NUM+','+data.FULL_EMP_RATIO+'%'},
				            ],
				            itemStyle: {
				                emphasis: {shadowBlur: 10,shadowOffsetX: 0,shadowColor: 'rgba(0, 0, 0, 0.5)'}
				            }
				        }
				    ]
  	   };
  	   myChart5.setOption(option5);
  	}
    function createSex(data){
  		var myChart6 = echarts.init(document.getElementById("echarts_sex"));
  	    var option6 = {
				    tooltip : {trigger: 'item',formatter: "{a} <br/>{b} : {c} ({d}%)"},
				    legend: {
				        orient: 'vertical',
				        left: 'left',
				        data: ['男,'+data.MALE_EMP_NUM+','+data.MALE_EMP_RATIO+'%',
				               '女,'+data.FEMALE_EMP_NUM+','+data.FEMALE_EMP_RATIO+'%']
				    },
				    series : [
				        {
				            name: '男女情况',
				            type: 'pie',
				            radius : '40%',
				            center: ['50%', '50%'],
				            data:[
								{value:data.MALE_EMP_NUM, name:'男,'+data.MALE_EMP_NUM+','+data.MALE_EMP_RATIO+'%'},
				                {value:data.FEMALE_EMP_NUM, name:'女,'+data.FEMALE_EMP_NUM+','+data.FEMALE_EMP_RATIO+'%'}
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
  	   myChart6.setOption(option6);
  	} 
    function createNation(data){
  		var myChart7 = echarts.init(document.getElementById("echarts_nation"));
  	    var option7 = {
				    tooltip : {trigger: 'item',formatter: "{a} <br/>{b} : {c} ({d}%)"},
				    legend: {
				        orient: 'vertical',
				        left: 'left',
				        data: ['汉族,'+data.HAN_EMP_NUM+','+data.HAN_EMP_RATIO+'%',
				               '少数民族,'+data.MIN_EMP_NUM+','+data.MIN_EMP_RATIO+'%']
				    },
				    series : [
				        {
				            name: '少数民族情况',
				            type: 'pie',
				            radius : '40%',
				            center: ['50%', '50%'],
				            data:[
				                {value:data.HAN_EMP_NUM, name:'汉族,'+data.HAN_EMP_NUM+','+data.HAN_EMP_RATIO+'%'},
				                {value:data.MIN_EMP_NUM, name:'少数民族,'+data.MIN_EMP_NUM+','+data.MIN_EMP_RATIO+'%'},
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
  	   myChart7.setOption(option7);
  	} 
    function createPostLevel(data){
  		var myChart8 = echarts.init(document.getElementById("echarts_postLevel"));
  	    var option8 = {
				    tooltip : {trigger: 'item',formatter: "{a} <br/>{b} : {c} ({d}%)"},
				    legend: {
				        orient: 'vertical',
				        left: 'left',
				        data: ['正高,'+data.ZG_TCH_EMP_NUM+','+data.ZG_TCH_EMP_RATIO+'%',
				               '副高,'+data.FG_TCH_EMP_NUM+','+data.FG_TCH_EMP_RATIO+'%',
				               '中级,'+data.ZJ_TCH_EMP_NUM+','+data.ZJ_TCH_EMP_RATIO+'%',
				               '助理级,'+data.ZL_TCH_EMP_NUM+','+data.ZL_TCH_EMP_RATIO+'%']
				    },
				    series : [
				        {
				            name: '职级情况（专任教师）',
				            type: 'pie',
				            radius : '40%',
				            center: ['50%', '50%'],
				            avoidLabelOverlap: false,
			                 label: {
			                	normal: {show: false,position: 'center'},
	                     		emphasis: {show: false,textStyle: {fontSize: '30',fontWeight: 'bold'}}
			                 },
			                 labelLine: {normal: {show: false}},
				            data:[
				                {value:data.ZG_TCH_EMP_NUM, name:'正高,'+data.ZG_TCH_EMP_NUM+','+data.ZG_TCH_EMP_RATIO+'%'},
				                {value:data.FG_TCH_EMP_NUM, name:'副高,'+data.FG_TCH_EMP_NUM+','+data.FG_TCH_EMP_RATIO+'%'},
				                {value:data.ZJ_TCH_EMP_NUM, name:'中级,'+data.ZJ_TCH_EMP_NUM+','+data.ZJ_TCH_EMP_RATIO+'%'},
				                {value:data.ZL_TCH_EMP_NUM, name:'助理级,'+data.ZL_TCH_EMP_NUM+','+data.ZL_TCH_EMP_RATIO+'%'},
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

  	   myChart8.setOption(option8);
  	}
    function initEmp(data){
		$(".empTotalNum").text(data.TOTAL_NUM);
		$(".disContactNum").text(data.DIS_CONTACT_NUM);
		$(".inTchEmpNum").text(data.IN_TCH_EMP_NUM);
		$(".inTchEmpRatio").text(data.IN_TCH_EMP_RATIO);
		$(".fullTchEmpNum").text(data.FULL_TCH_EMP_NUM);
		$(".under35TchEmpNum").text(data.UNDER_35_TCH_EMP_NUM);
		$(".inStuEmpNum").text(data.IN_STU_EMP_NUM);
		$(".inStuEmpRatio").text(data.IN_STU_EMP_RATIO);
		$(".inUnderEmpNum").text(data.IN_UNDER_EMP_NUM);
		$(".inPostEmpNum").text(data.IN_POST_EMP_NUM);
		$(".retireEmpNum").text(data.RETIRE_EMP_NUM);
		$(".retireEmpRatio").text(data.RETIRE_EMP_RATIO);
		$(".otherEmpNum").text(data.OTHER_EMP_NUM);
		$(".otherEmpRatio").text(data.OTHER_EMP_RATIO);
	}
    function initEmpSub(data,subType){
    	var str = "";
    	if(isNotNull(subType)){
    		str="_sub";
    	}
    	$(".preEmpNum"+str).text(data.PRE_EMP_NUM);
    	$(".preEmpRatio"+str).text(data.PRE_EMP_RATIO);
    	$(".fullEmpNum"+str).text(data.FULL_EMP_NUM);
    	$(".fullEmpRatio"+str).text(data.FULL_EMP_RATIO);
    	$(".maleEmpNum"+str).text(data.MALE_EMP_NUM);
    	$(".maleEmpRatio"+str).text(data.MALE_EMP_RATIO);
    	$(".femaleEmpNum"+str).text(data.FEMALE_EMP_NUM);
    	$(".femaleEmpRatio"+str).text(data.FEMALE_EMP_RATIO);
    	$(".hanEmpNum"+str).text(data.HAN_EMP_NUM);
    	$(".hanEmpRatio"+str).text(data.HAN_EMP_RATIO);
    	$(".minEmpNum"+str).text(data.MIN_EMP_NUM);
    	$(".minEmpRatio"+str).text(data.MIN_EMP_RATIO);
    	$(".zgTchEmpNum"+str).text(data.ZG_TCH_EMP_NUM);
    	$(".zgTchEmpRatio"+str).text(data.ZG_TCH_EMP_RATIO);
    	$(".fgTchEmpNum"+str).text(data.FG_TCH_EMP_NUM);
    	$(".fgTchEmpRatio"+str).text(data.FG_TCH_EMP_RATIO);
    	$(".zjTchEmpNum"+str).text(data.ZJ_TCH_EMP_NUM);
    	$(".zjTchEmpRatio"+str).text(data.ZJ_TCH_EMP_RATIO);
    	$(".zlTchEmpNum"+str).text(data.ZL_TCH_EMP_NUM);
    	$(".zlTchEmpRatio"+str).text(data.ZL_TCH_EMP_RATIO);
    }
</script>
<!-- 党员情况 -->
	 <div class="p10 jepor State">
 		<h5 class="Basic_title"></h5>
		 <div class="personChart_fl jew49 fl jepor" style="height:325px;">
		 	<!--类型-->
             <div class="line_bot p10 f14 jepoa">                          
				 <select id="empType" class="select2-choice">
				     <option value="1">党员</option>
				     <option value="2">&#12288;1-在职教职工党员</option>
				     <option value="3">&#12288;&#12288;11-专任教师党员</option>
				     <option value="4">&#12288;&#12288;11-35岁及以下专任教师党员</option>
				     <option value="5">&#12288;2-在校学生党员</option>
				     <option value="6">&#12288;&#12288;21-在校本科生党员</option>
				     <option value="7">&#12288;&#12288;22-在校研究生生党员</option>
				     <option value="8">&#12288;3-离退休党员</option>
				     <option value="9">&#12288;4-其他党员</option>
				 </select>
			 </div>
			 <!--类型 End-->
			 <!--各类情况-->
             <div class="line_bot p10 f12 jepoa" style="left:5px; top: 50%;">                          
				 <ul name="" class="">
				     <li>失联党员总数：<span class="disContactNum">0</span>人</li>
				     <li>在校本科生党员：<span class="inUnderEmpNum">0</span>人</li>
				     <li>在校研究生党员：<span class="inPostEmpNum">0</span>人</li>
				     <li>专任教师党员：<span class="fullTchEmpNum">0</span>人</li>
				     <li>35岁及以下：<span class="under35TchEmpNum">0</span>人</li>
				 </ul>
			 </div>
			 <!--各类情况 End-->
             <h3 class="personChart_tab f16 tc">党员情况<span class="fr"><i class="t1 iconfont mr5 acur">&#xe66f;</i><i class="t2 iconfont mr5">&#xe63f;</i></span></h3>
             <!--图表-->
             <div id="echarts_emp" class="echarts" style="height:285px;"></div>
             <!--图表 End-->
             <!--表格-->
			 <table width="100%" border="0" cellspacing="0" cellpadding="0" class="analysis_table" style="display:none;">
				  <tr class="bg-F8">
					    <td class="noString">分类</td>
					    <td>人数</td>
					    <td class="noWing">占比</td>
				  </tr>
				  <tr>
					    <td class="noString">在职教职工党员</td>
					    <td><span class="inTchEmpNum">0</span></td>
					    <td class="noWing"><span class="inTchEmpRatio">0</span>%</td>
				  </tr>
				  <tr>
					    <td class="noString">在校学生党员</td>
					    <td><span class="inStuEmpNum">0</span></td>
					    <td class="noWing"><span class="inStuEmpRatio">0</span>%</td>
				  </tr>
				  <tr>
					    <td class="noString">离退休党员</td>
					    <td><span class="retireEmpNum">0</span></td>
					    <td class="noWing"><span class="retireEmpRatio">0</span>%</td>
				  </tr>
				  <tr>
					    <td class="noString">其他党员</td>
					    <td><span class="otherEmpNum">0</span></td>
					    <td class="noWing"><span class="otherEmpRatio">0</span>%</td>
				  </tr>
			  </table> 
			 <!--表格 End-->
		 </div>
		 <!--切换图表 2-->  
		 <div class="personChart_fr jew49 fl" style="height:325px;">      
	       	 <h3 class="personChart_tab f16 tc">党员类型情况<span class="fr"><i class="t1 iconfont mr5 acur">&#xe66f;</i><i class="t2 iconfont mr5">&#xe63f;</i></span></h3>
	         <!--图表-->
	         <div id="echarts_empType" class="echarts" style="height:285px;"></div>
	         <!--图表 End-->
	         <!--表格-->
			  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="analysis_table" style="display:none;">
				  <tr class="bg-F8">
					    <td class="noString">分类</td>
					    <td>人数</td>
					    <td class="noWing">占比</td> 
				  </tr>
				  <tr>
					    <td class="noString">预备党员</td>
					    <td><span class="preEmpNum_sub">0</span></td>
					    <td class="noWing"><span class="preEmpRatio_sub">0</span>%</td> 
				  </tr>
				  <tr>
					    <td class="noString">正式党员</td>
					    <td><span class="fullEmpNum_sub">0</span></td>
					    <td class="noWing"><span class="fullEmpRatio_sub">0</span>%</td> 
				  </tr>
			  </table> 
           	  <!--表格 End-->
         </div>   
		 <div class="space10"></div>
	     <div class="personChart_fr jew33 fl" style="height:285px;" >      
       	 	<h3 class="personChart_tab f16 tc">男女情况<span class="fr"><i class="t1 iconfont mr5 ">&#xe66f;</i><i class="t2 iconfont mr5 acur">&#xe63f;</i></span></h3>
            <!--图表-->
            <div id="echarts_sex" class="echarts" style="height:245px; display:none;" ></div>
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
					    <td><span class="maleEmpNum_sub">0</span></td>
					    <td class="noWing"><span class="maleEmpRatio_sub">0</span>%</td>
				  </tr>
				  <tr>
					    <td class="noString">女</td>
					    <td><span class="femaleEmpNum_sub">0</span></td>
					    <td class="noWing"><span class="femaleEmpRatio_sub">0</span>%</td>
				  </tr>
			  </table> 
              <!--表格 End-->
          </div>
          <div class="personChart_fr jew33 fl" style="height:285px;" >      
          	<h3 class="personChart_tab f16 tc">少数民族情况<span class="fr"><i class="t1 iconfont mr5 ">&#xe66f;</i><i class="t2 iconfont mr5 acur">&#xe63f;</i></span></h3>
            <!--图表-->
            <div id="echarts_nation" class="echarts" style="height:245px; display:none;"></div>
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
					    <td><span class="hanEmpNum_sub">0</span></td>
					    <td class="noWing"><span class="hanEmpRatio_sub">0</span>%</td>
				  </tr>
				  <tr>
					    <td class="noString">少数民族</td>
					    <td><span class="minEmpNum_sub">0</span></td>
					    <td class="noWing"><span class="minEmpRatio_sub">0</span>%</td>
				  </tr>
			  </table> 
              <!--表格 End-->
           </div>   
           <div class="personChart_fr jew33 fl" style="height:285px;" >      
           	<h3 class="personChart_tab f16 tc">职级情况（专任教师）<span class="fr"><i class="t1 iconfont mr5 ">&#xe66f;</i><i class="t2 iconfont mr5 acur">&#xe63f;</i></span></h3>
	            <!--图表-->
	            <div id="echarts_postLevel" class="echarts" style="height:245px; display:none;"></div>
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
						    <td><span class="zgTchEmpNum_sub">0</span></td>
						    <td class="noWing"><span class="zgTchEmpRatio_sub">0</span>%</td>
					  </tr>
					  <tr>
						    <td class="noString">副高级</td>
						    <td><span class="fgTchEmpNum_sub">0</span></td>
						    <td class="noWing"><span class="fgTchEmpRatio_sub">0</span>%</td>
					  </tr>
					  <tr>
						    <td class="noString">中级</td>
						    <td><span class="zjTchEmpNum_sub">0</span></td>
						    <td class="noWing"><span class="zjTchEmpRatio_sub">0</span>%</td>
					  </tr>
					  <tr>
						    <td class="noString">助理级</td>
						    <td><span class="zlTchEmpNum_sub">0</span></td>
						    <td class="noWing"><span class="zlTchEmpRatio_sub">0</span>%</td>
					  </tr>
				  </table> 
	              <!--表格 End-->
          </div>   
      	  <div class="clear"></div>                              
 </div>
<!-- 党员情况 end -->