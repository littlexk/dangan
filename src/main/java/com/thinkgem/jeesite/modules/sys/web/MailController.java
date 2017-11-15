package com.thinkgem.jeesite.modules.sys.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jr.utils.FormBean;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.security.Cryptos;
import com.thinkgem.jeesite.common.utils.Encodes;
import com.thinkgem.jeesite.modules.sys.service.MailService;

/**
 * 邮箱管理逻辑控制层
 * @ClassName: MailController 
 * @Description: TODO 负责邮箱管理相关业务逻辑处理
 * @author: WangCong
 * @date: 2016年5月5日 上午11:15:02
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/mail")
public class MailController {

	@Autowired
	private MailService mailService;
	
	//AES加密算法密钥
	private static final byte[] key = {-74,-61,-75,53,-104,122,-77,-100,-112,119,-66,75,123,86,44,60};
	
	
	/**
	 * 普通邮箱查询方法
	 * @Title: searchMailByConditions 
	 * @Description: TODO 通过指定条件查询邮箱记录
	 * @Date: 2016年5月5日 上午11:18:56
	 * @author: WangCong
	 * @param formBean 页面传入的查询条件
	 * @param model 响应数据模型
	 * @return 响应页面地址
	 */
	@RequestMapping(value = "list")
	public String searchMailByConditions(FormBean formBean, HttpServletRequest request, HttpServletResponse response, Model model){
		Page<Map<String, Object>> page = mailService.searchMailByConditions(formBean, new Page<Map<String,Object>>(request, response));
		model.addAttribute("page", page);
		model.addAttribute("mailMap",formBean);
		return "modules/sys/mailList";
	}
	
	/**
	 * 设置为默认邮箱
	 * @Title: setAsDefault 
	 * @Description: TODO 将当前操作邮箱设置为默认邮箱
	 * @Date: 2016年5月5日 下午2:10:54
	 * @author: WangCong
	 * @param id 当前所操作邮箱的ID
	 * @return 响应页面地址
	 */
	@RequestMapping(value = "default")
	public String setAsDefault(String id){
		//先取消当前系统的默认邮箱
		mailService.cancelDefault();
		//再将当前操作的邮箱设置为新的系统默认邮箱
		mailService.setAsDefault(id);
		return "redirect:" + Global.getAdminPath() + "/sys/mail/list";
	}
	
	/**
	 * 修改或者新增一个邮箱
	 * @Title: editOrAddMail 
	 * @Description: TODO 修改或者新增一个邮箱
	 * @Date: 2016年5月5日 下午3:35:01
	 * @author: WangCong
	 * @param id 当操作为修改邮箱时ID值为当前操作的邮箱
	 * @param model 响应数据模型
	 * @return 响应页面地址
	 */
	@RequestMapping(value = "form")
	public String editOrAddMail(String id, Model model){
		FormBean formBean = new FormBean();
		Map<String, Object> map = null;
		if (StringUtils.isNotBlank(id)) {//修改操作
			//获取当前操作邮箱的详细信息
			map = mailService.searchMailById(id);
			//对密码进行AES解密
			String password = (String)map.get("PASSWORD");
			if (StringUtils.isNotEmpty(password)) {
				password = Cryptos.aesDecrypt(Cryptos.parseHexStr2Byte(password), key);
				map.put("PASSWORD", password);
			}
		}else { //新增操作
			map = new HashMap<String, Object>();
			model.addAttribute("type", "1");
		}
		formBean.setBean(map);
		model.addAttribute("mailMap", formBean);
		return "modules/sys/mailForm";
	}
	
	/**
	 * 保存邮箱信息
	 * @Title: saveMail 
	 * @Description: TODO 保存邮箱信息
	 * @Date: 2016年5月5日 下午4:27:41
	 * @author: WangCong
	 * @param formBean 页面传入的邮箱信息
	 * @return 响应页面地址
	 */
	@RequestMapping(value = "save")
	public String saveMail(FormBean formBean){
		Map<String, Object> mailBean = formBean.getBean();
		String mailId =  (String)mailBean.get("ID");
		String password = (String)mailBean.get("PASSWORD");
		if (StringUtils.isNotEmpty(password)) {
			password = Cryptos.parseByte2HexStr(Cryptos.aesEncrypt(password.getBytes(), key));
			mailBean.put("PASSWORD", password);
		}
		if (StringUtils.isNotEmpty(mailId)) {//更新操作
			mailService.updateMail(mailBean);
		}else {//新增操作
			mailService.insertMail(mailBean);
		}
		return "redirect:" + Global.getAdminPath() + "/sys/mail/list";
	}
	
	/**
	 * 删除邮箱记录
	 * @Title: deleteMail 
	 * @Description: TODO 根据主键ID来删除邮箱记录
	 * @Date: 2016年5月6日 上午9:41:33
	 * @author: WangCong
	 * @param ids 页面传入的邮箱主键ID，是一个数组，里面可以包括多个邮箱主键，达到批量删除的目的
	 * @return 响应页面地址
	 */
	@RequestMapping(value = "delete")
	public String deleteMail(String ids){
		String[] mailIds = ids.split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", mailIds);
		mailService.deleteMail(map);
		//检查目前系统是否还存在有默认邮箱
		if (!mailService.checkIfDefaultMailExist()) {
			//如果不存在，则随机将其中一个邮箱设置为默认邮箱
			mailService.setDefaultMailRandomly();
		}
		return "redirect:" + Global.getAdminPath() + "/sys/mail/list";
	}
	
	/**
	 * 检查某个邮箱是否已经存在
	 * @Title: checkIfMailExit 
	 * @Description: TODO 通过帐号来查询某个邮箱是否已经存在
	 * @Date: 2016年5月6日 上午11:29:11
	 * @author: WangCong
	 * @param loginName 帐号
	 * @return 如果已经存在则返回false，否则返回true
	 */
	@ResponseBody
	@RequestMapping(value = "check")
	public String checkIfMailExist(String newLoginName){
		if (StringUtils.isNotBlank(newLoginName)) {
			return mailService.checkIfMailExist(newLoginName);
		} else {
			return "true";
		}
	}
	
	/**
	 * 邮箱有效性验证
	 * @Title: validate 
	 * @Description: TODO 验证指定邮箱是否真实有效
	 * @Date: 2016年5月27日 下午4:59:34
	 * @author: WangCong
	 * @return 验证结果
	 */
	@RequestMapping(value = "validate")
	public @ResponseBody Map<String, Object> validate(HttpServletRequest request,HttpServletResponse response,FormBean formBean){
		//验证结果
		Map<String, Object> result = Maps.newHashMap();
		//邮箱参数
		String userName = request.getParameter("loginName");
		String password = Encodes.decode2Base64(request.getParameter("password"));
		if (StringUtils.isEmpty(password)) {//如何密码为空且能够通过前台非空校验进入后台则证明该情况发生于修改邮箱信息(新密码栏目不填)的过程
			password = mailService.getPasswordById(request.getParameter("id"));
			//解密
			password = Cryptos.aesDecrypt(Cryptos.parseHexStr2Byte(password), key);
		}
		String host = request.getParameter("host");
		String port = request.getParameter("port");
		String ssl = StringUtils.equals(request.getParameter("ssl"), "1")?"true":"false";
		//进行邮箱有效性验证
		String flag = mailService.validate(userName, password, port, host, ssl)==true?"true":"false";
		result.put("result", flag);
		return result;
	}
}
