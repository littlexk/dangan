package com.thinkgem.jeesite.modules.sys.web;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jr.utils.FormBean;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.service.ActLogService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;


/**
 * Controller
 * 
 * @author yiw
 * @version 2013-04-05
 */
@Controller
@RequestMapping(value = "${adminPath}/org/actLog")
public class ActLogController extends BaseController {

	@Autowired
	private ActLogService actLogService;

	@SuppressWarnings("unused")
	@RequiresPermissions("org:actLog:view")
	@RequestMapping(value = { "list",""})
	public String list(@ModelAttribute("map") FormBean formbean,String createId, String applyId,String beginDate,String endDate,
			HttpServletRequest request, HttpServletResponse response,Model model) {
		formbean.getBean().put("CREATE_ID", createId);
		formbean.getBean().put("APPLY_ID", applyId);
		formbean.getBean().put("BEGINDATE", beginDate);
		formbean.getBean().put("ENDDATE", endDate);
		
		model.addAttribute("CREATE_ID", createId);
		model.addAttribute("APPLY_ID", applyId);
		model.addAttribute("BEGINDATE", beginDate);
		model.addAttribute("ENDDATE", endDate);
		
		Page<Map<String, Object>> page = actLogService.list(
				new Page<Map<String, Object>>(request, response), formbean);
		for (Map<String, Object> map : page.getList()) {
			String tableName =  MapUtils.getString(map, "TABLE_NAME");
			String tableColumn =  MapUtils.getString(map, "TABLE_COLUMN");
			String tableNameColumn = tableName+"/"+tableColumn;
			map.put("TABLE_NAME_COLUMN", tableNameColumn);
			String preValue =  MapUtils.getString(map, "OLD_VALUE");
			String curValue =  MapUtils.getString(map, "NEW_VALUE");
			String stdName =  MapUtils.getString(map, "STD_NAME");
			
			if("A_TEACHER_TYPE".equals(tableColumn)){
				preValue = preValue==null?"未填":preValue.substring(preValue.length()-1);
				curValue = curValue==null?"未填":curValue.substring(curValue.length()-1);
				map.put("OLD_VALUE", preValue);
				map.put("NEW_VALUE", curValue);
			}else if("A_SCH_POST_LEVEL".equals(tableColumn)){
				preValue  = IsTrue(preValue);
				curValue  = IsTrue(curValue);
				map.put("OLD_VALUE", preValue);
				map.put("NEW_VALUE", curValue);
			}else if("T_COMM_PAPER".equals(tableName) && "RANKING".equals(tableColumn)){//PAPER_SORT 只有确定T_COMM_PAPER才有效
				String paperSort =  MapUtils.getString(map, "PAPER_SORT");
				preValue  = IsTruePaPer(preValue,paperSort);
				curValue  = IsTruePaPer(curValue,paperSort);
				map.put("OLD_VALUE", preValue);
				map.put("NEW_VALUE", curValue);
			}
		}
		
		model.addAttribute("page", page);
		model.addAttribute("bean", formbean);
		return "modules/sys/logList";
	}

	private String IsTruePaPer(String values,String paperSort) {
			if("12".equals(paperSort)){//著作
				values = getValue(values,"T_PG_ST_WORK_RANKING"); 
			}else if("21".equals(paperSort)){//论文
				values = getValue(values,"T_STF_PAPER_RANKING"); 
			}
		return values;
	}

	/**
	 * 根据 value、获得特别值
	 * @param value
	 * @return 
	 */
	private String IsTrue(String value) {
		if("1".equals(value)){
			value="教授";
		}else if("2".equals(value)){
			value="副教授";
		}else if("3".equals(value)){
			value="讲师";
		}else if("4".equals(value)){
			value="助教";
		}else{
			value="未填";
		}
		return value;
	}
	
	
	
	@SuppressWarnings("unused")
	@RequestMapping(value = { "selectList"})
	public String selectList(@ModelAttribute("map") FormBean formbean,HttpServletRequest request, HttpServletResponse response,
			Model model) {	

		model.addAttribute("bean", formbean);
		
		String numId = MapUtils.getString(formbean.getBean(), "NUM_ID");
		String tableName = MapUtils.getString(formbean.getBean(), "TABLE_NAME");
		String tableColumn = MapUtils.getString(formbean.getBean(), "TABLE_COLUMN");
		String tableNameColumn = MapUtils.getString(formbean.getBean(), "TABLE_NAME_COLUMN");
		String preValue = MapUtils.getString(formbean.getBean(), "OLD_VALUE");
		String curValue = MapUtils.getString(formbean.getBean(), "NEW_VALUE");
		
		Map<String, Object> bean = new HashMap<String, Object>();
		
		if("T_LEADER_BASE".equals(tableName)){//干部基本基本信息
			bean = actLogService.selectList(formbean);
			FormBean base = new FormBean(bean);
			request.setAttribute("base", base);
			return "modules/sys/LogbaseForm";
		}else if("T_COMM_EDU_EXP".equals(tableName)){//教育经历
			bean = actLogService.selectList(formbean);

			bean.put("TABLE_COLUMN", tableColumn);
			bean.put("OLD_VALUE", preValue);
			bean.put("NEW_VALUE", curValue);
			bean.put("TABLE_NAME", tableName);
			bean.put("NUM_ID", numId);
			model.addAttribute("map", new FormBean(bean));
			
			return "modules/pg/tech/actLog/LogEduForm";
		}
		return null;
	}
	
	@RequestMapping(value = "getModifyLog")
	public @ResponseBody
	List<Map<String, Object>> getModifyLog(FormBean formbean,String numId,String tableName,String tableColumn,String preValue,String curValue,String paperSort,
			HttpServletRequest request, HttpServletResponse response) {
		List<Map<String, Object>> modifyLog = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		
		preValue = Encodes.decode2Base64(preValue);
		curValue = Encodes.decode2Base64(curValue);
		if(!StringUtils.isEmpty(paperSort)){
			paperSort = Encodes.decode2Base64(paperSort);
		}
		preValue = getTableColumn(tableColumn, preValue,tableName,paperSort);//字典匹配label
		curValue = getTableColumn(tableColumn, curValue,tableName,paperSort);//字典匹配label
		map.put("NUM_ID", numId);
		map.put("TABLE_NAME", tableName);
		map.put("TABLE_COLUMN", tableColumn);
		map.put("OLD_VALUE", preValue);
		map.put("NEW_VALUE", curValue);
		modifyLog.add(map);
		return modifyLog;
	}

	/**
	 * 根据 字段名、值、表名、获得字典里的label值
	 * @param tableColumn
	 * @param values
	 * @param tableName
	 * @param paperSort 区别论文21 和 著作12
	 * @return getValue
	 */
	private String getTableColumn(String tableColumn, String values,String tableName,String paperSort) {
		String getValue =values;
		if(values==null || values==""){
			getValue="未填";
		}else{
			if("T_COMM_EDU_EXP".equals(tableName)){//教育经历
				if("EDUCATION".equals(tableColumn)){
					getValue = getValue(values,"T_EDU_EDUCATION"); 
				}else if("HIGHEST_EDU".equals(tableColumn)){
					getValue = getValue(values,"T_PG_COMMON_YESNO"); 
				}else if("EDU_DEGREE".equals(tableColumn)){
					getValue = getValue(values,"T_EDU_DEGREE"); 
				}else if("HIGHEST_DEGREE".equals(tableColumn)){
					getValue = getValue(values,"T_PG_COMMON_YESNO"); 
				}
			}
		}
		return getValue;
	}
	/**
	 * 根据 values获得对应type，字典里的label值
	 * @param values
	 * @param type
	 * @return getValue
	 */
	private String getValue(String values,String type) {
		String getValue = values;
		if(getValue ==null || getValue ==""){
			getValue = "未填";
		}else {
			List<Dict> dicts = DictUtils.getDictList(type);
			for (Dict dict : dicts) {
				if(values.equals(dict.getValue()))
					getValue = dict.getLabel();
			}
		}
		return getValue;
	}
	
	@RequestMapping(value = { "typeList"})
	public String typeList(@ModelAttribute("map") FormBean formbean,HttpServletRequest request, HttpServletResponse response,
			Model model) {	

		return "modules/pg/tech/actLog/list";
	}


}
