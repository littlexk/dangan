<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>高级查询</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/bns/js/highLevelSearch.js" type="text/javascript"></script>
	<style type="text/css">
		.Wdate{
			margin-top:5px;
			margin-bottom:10px;
			width:150px;
		}
		.input-append{
			margin-top:5px;
			margin-bottom:10px;
		}
	</style>
	<script type="text/javascript">
		var ctx = "${ctx}"; 
		$(document).ready(function() {
			initCondition('${bean.bean.conditionNum}');
		});
	</script>
</head>
<body>
<form:form action="" modelAttribute="bean">
	    <!--基本信息-->
	    <div class="searchInfo">
	        <ul class="pt10">
	            <li>
	                <span class="name">满足以下：</span>
	                <input id="searchId" value="${bean.bean.searchInfo.NUM_ID}" type="hidden">
	                <select id="condition" data-value="${bean.bean.searchInfo.CONDITON_REL}" class="selectpicker">
                        <option value="or">任一</option>
                        <option value="and">所有</option>
                    </select>
                    <span class="f14 condition pl5">条件</span>
	            </li>
	          
	        </ul>
	        <div class="clear"></div>
	     </div>
	     <!--列表-->
         <div class="Table_box rdu3 mb15 jepor m10">
	     <table width="100%" border="0" cellspacing="0" cellpadding="0" class="Pay_table">
            <tr class="title">
                  <td>查询列</td>
                  <td>关系</td>
                  <td>值</td>
            </tr>
            <tr class="content" style="display:none">
           		<td class="columnTD">
              		<div class="input-append">
						<!-- 列id -->
						<input id="columnId" type="hidden" value="" class="input-small columnId"/>
						<!-- 列类型 -->
						<input id="columnType" type="hidden" value="" class="input-small columnType"/>
						<!-- 数据字典类型编码 -->
						<input id="columnCode" type="hidden" value="" class="input-small columnCode"/>
						<!-- 字段名 -->
						<input id="columnSqlName" type="hidden" value="" class="input-small columnSqlName"/>
						<!-- 列名称 -->
						<input id="columnName" readonly="readonly" type="text" value="" class="input-small columnName" maxlength="50" />
						<!-- 表名称 -->
						<input id="columnTable" type="hidden" value="" class="input-small columnTable"/>
						<!-- 搜索图标 -->
						<a id="columnButton" href="javascript:void(0)" onClick="columnBtnClick(this)" class="btn columnButton"><i class="icon-search"></i></a>&nbsp;&nbsp;
					</div>
                </td>
                <td class="relationTD">
                   <select id="relationId" class="selectpicker" ></select>
                </td>
               	<td class="valueTD text">
                  	<input id="textId" type="text" value="" class="text mt5 searBox" />
                  	<a href="javascript:void(0);" class="dib pl5 pr5 ml10" onClick="addContent(this)"><i class="iconfont mr5 f16">&#xe60a;</i></a>
                   	<a href="javascript:void(0);" class="dib pl5 pr5 minus" onClick="removeContent(this)"><i class="iconfont mr5 f16">&#xe611;</i></a>
               	</td>
               	<td class="valueTD num" style="display:none">
                 	<input id="numId" type="text" value="" class="text mt5 searBox" />
                 	<a href="javascript:void(0);" class="dib pl5 pr5 ml10" onClick="addContent(this)"><i class="iconfont mr5 f16">&#xe60a;</i></a>
                   	<a href="javascript:void(0);" class="dib pl5 pr5 minus" onClick="removeContent(this)"><i class="iconfont mr5 f16">&#xe611;</i></a>
               	</td>
             	<td class="valueTD dict" style="display:none">
                   	<div class="input-append">
						<input id="dictId" class="input-small dictId" type="hidden" value="" />
						<input id="dictType" class="input-small dictType" type="hidden" value=""/>
						<input id="dictName" readonly="readonly" type="text" value="" maxlength="4000" class="input-small dictName" />
						<a id="dictButton" href="javascript:void(0)" onClick="dictBtnClick(this)" class="btn dictButton"><i class="icon-search"></i></a>&nbsp;&nbsp;
					</div>
                   	<a href="javascript:void(0);" class="dib pl5 pr5 ml10" onClick="addContent(this)"><i class="iconfont mr5 f16">&#xe60a;</i></a>
                   	<a href="javascript:void(0);" class="dib pl5 pr5 minus" onClick="removeContent(this)"><i class="iconfont mr5 f16">&#xe611;</i></a>
              	</td>
               	<td class="valueTD org" style="display:none">
               		<div class="input-append" style="${display eq 'false'?'display:none':''}">
						<input id="orgId" class="input-small orgId" type="hidden" value="" />
						<input id="orgName" readonly="readonly" type="text" value="" maxlength="50" class="input-small orgName"/>
						<a id="orgButton" href="javascript:void(0)" onClick="orgBtnClick(this)" class="btn orgButton"><i class="icon-search"></i></a>&nbsp;&nbsp;
					</div>
                	<a href="javascript:void(0);" class="dib pl5 pr5 ml10" onClick="addContent(this)"><i class="iconfont mr5 f16">&#xe60a;</i></a>
                   	<a href="javascript:void(0);" class="dib pl5 pr5 minus" onClick="removeContent(this)"><i class="iconfont mr5 f16">&#xe611;</i></a>
               	</td>
               	<td class="valueTD date" style="display:none">
                   	<input id="dateId" type="text" maxlength="20" class="input-small Wdate" 
						value="" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
                 	<a href="javascript:void(0);" class="dib pl5 pr5 ml10" onClick="addContent(this)"><i class="iconfont mr5 f16">&#xe60a;</i></a>
                   	<a href="javascript:void(0);" class="dib pl5 pr5 minus" onClick="removeContent(this)"><i class="iconfont mr5 f16">&#xe611;</i></a>
              	</td>
              </tr>
              <c:set var="currNum" value="1"></c:set>
              <c:forEach items="${bean.bean.conditionList}" var="condition">
              	<tr class="valueContent">
	           		<td class="columnTD">
	              		<div class="input-append">
							<!-- 列id -->
							<input id="columnId_#HL#${currNum}" type="hidden" value="${condition.COLUMN_ID}" class="input-small columnId"/>
							<!-- 列类型 -->
							<input id="columnType_#HL#${currNum}" type="hidden" value="${condition.DATA_TYPE}" class="input-small columnType"/>
							<!-- 数据字典类型编码 -->
							<input id="columnCode_#HL#${currNum}" type="hidden" value="${condition.DATA_CODE}" class="input-small columnCode"/>
							<!-- 字段名 -->
							<input id="columnSqlName_#HL#${currNum}" type="hidden" value="${condition.COLUMN_NAME_SQL}" class="input-small columnSqlName"/>
							<!-- 列名称 -->
							<input id="columnName_#HL#${currNum}" readonly="readonly" type="text" value="${condition.COLUMN_NAME}" class="input-small columnName" maxlength="50" />
							<!-- 表名称 -->
							<input id="columnTable_#HL#${currNum}" type="hidden" value="${condition.TABLE_NAME}" class="input-small columnTable"/>
							<!-- 搜索图标 -->
							<a id="columnButton_#HL#${currNum}" href="javascript:void(0)" onClick="columnBtnClick(this)" class="btn columnButton"><i class="icon-search"></i></a>&nbsp;&nbsp;
						</div>
	                </td>
	                <td class="relationTD">
	                   <select id="relationId_#HL#${currNum}" class="selectpicker" data-value="${condition.REL}" ></select>
	                </td>
	               	<td class="valueTD text" style="${condition.DATA_TYPE eq 'text'?'':'display:none'}">
	                  	<input id="textId_#HL#${currNum}" type="text" value="${condition.DATA_TYPE eq 'text' ? condition.VALUE : ''}" class="text mt5 searBox" />
	                  	<a href="javascript:void(0);" class="dib pl5 pr5 ml10" onClick="addContent(this)"><i class="iconfont mr5 f16">&#xe60a;</i></a>
	                   	<a href="javascript:void(0);" class="dib pl5 pr5 minus" onClick="removeContent(this)"><i class="iconfont mr5 f16">&#xe611;</i></a>
	               	</td>
	               	<td class="valueTD num" style="${condition.DATA_TYPE eq 'num'?'':'display:none'}">
	                 	<input id="numId_#HL#${currNum}" type="text" value="${condition.DATA_TYPE eq 'num' ? condition.VALUE : ''}" class="text mt5 searBox" />
	                 	<a href="javascript:void(0);" class="dib pl5 pr5 ml10" onClick="addContent(this)"><i class="iconfont mr5 f16">&#xe60a;</i></a>
	                   	<a href="javascript:void(0);" class="dib pl5 pr5 minus" onClick="removeContent(this)"><i class="iconfont mr5 f16">&#xe611;</i></a>
	               	</td>
	             	<td class="valueTD dict" style="${condition.DATA_TYPE eq 'dict'?'':'display:none'}">
	                   	<div class="input-append">
							<input id="dictId_#HL#${currNum}" class="input-small dictId" type="hidden" value="${condition.DATA_TYPE eq 'dict' ? condition.VALUE : ''}" />
							<input id="dictType_#HL#${currNum}" class="input-small dictType" type="hidden" value="${condition.DATA_TYPE eq 'dict' ? condition.DATA_CODE : ''}"/>
							<input id="dictName_#HL#${currNum}" readonly="readonly" type="text" value="${fns:getDictLabel(condition.VALUE,condition.DATA_CODE,'')}" maxlength="4000" class="input-small dictName" />
							<a id="dictButton_#HL#${currNum}" href="javascript:void(0)" onClick="dictBtnClick(this)" class="btn dictButton"><i class="icon-search"></i></a>&nbsp;&nbsp;
						</div>
	                   	<a href="javascript:void(0);" class="dib pl5 pr5 ml10" onClick="addContent(this)"><i class="iconfont mr5 f16">&#xe60a;</i></a>
	                   	<a href="javascript:void(0);" class="dib pl5 pr5 minus" onClick="removeContent(this)"><i class="iconfont mr5 f16">&#xe611;</i></a>
	              	</td>
	               	<td class="valueTD org" style="${condition.DATA_TYPE eq 'org'?'':'display:none'}">
	               		<div class="input-append" style="${display eq 'false'?'display:none':''}">
							<input id="orgId_#HL#${currNum}" class="input-small orgId" type="hidden" value="${condition.DATA_TYPE eq 'org' ? condition.VALUE : ''}" />
							<input id="orgName_#HL#${currNum}" readonly="readonly" type="text" value="${fns:getOfficeLabelBySeparator(condition.VALUE,'-','')}" maxlength="50" class="input-small orgName"/>
							<a id="orgButton_#HL#${currNum}" href="javascript:void(0)" onClick="orgBtnClick(this)" class="btn orgButton"><i class="icon-search"></i></a>&nbsp;&nbsp;
						</div>
	                	<a href="javascript:void(0);" class="dib pl5 pr5 ml10" onClick="addContent(this)"><i class="iconfont mr5 f16">&#xe60a;</i></a>
	                   	<a href="javascript:void(0);" class="dib pl5 pr5 minus" onClick="removeContent(this)"><i class="iconfont mr5 f16">&#xe611;</i></a>
	               	</td>
	               	<td class="valueTD date" style="${condition.DATA_TYPE eq 'date'?'':'display:none'}">
	                   	<input id="dateId_#HL#${currNum}" type="text" maxlength="20" class="input-small Wdate" 
							value="${condition.DATA_TYPE eq 'date' ? condition.VALUE : ''}" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
	                 	<a href="javascript:void(0);" class="dib pl5 pr5 ml10" onClick="addContent(this)"><i class="iconfont mr5 f16">&#xe60a;</i></a>
	                   	<a href="javascript:void(0);" class="dib pl5 pr5 minus" onClick="removeContent(this)"><i class="iconfont mr5 f16">&#xe611;</i></a>
	              	</td>
	              </tr>
	              <c:set var="currNum" value="${currNum+1}"></c:set>
              </c:forEach>
           </table>
	    </div>
    </form:form>
</body>
</html>