package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.modules.sys.entity.SimpleMessage;
import com.thinkgem.jeesite.modules.sys.security.MessageSenderManager;
import com.thinkgem.jeesite.modules.sys.utils.MessageUtils;

/**
 * 手机短信发送服务层
 * @ClassName: MessageSendService 
 * @Description: TODO 负责手机短信发送相关逻辑处理
 * @author: WangCong
 * @date: 2016年5月20日 上午11:00:23
 */
@Service
@Transactional(readOnly = true)
public class MessageSendService {

	private final static String FLAG_FAILURE = "failure";
	
	/**
	 * 发送手机短信
	 * @Title: sendMessage 
	 * @Description: TODO 发送手机短信
	 * @Date: 2016年5月20日 上午11:03:21
	 * @author: WangCong
	 * @param message 简单手机短信类
	 * @return 将短信发送的结果信息封装到List集合中(List集合的数据类型为Map，该Map会有两条记录，一条的key为flag作为成功或失败标识，一条的key为remark作为失败信息记录)
	 */
	public List<Map<String, String>> sendMessage(SimpleMessage message){
		//短信接收者
		Map<String,String> recipients = message.getRecipients();
		//短信内容
		String content = message.getContent();
		//短信发送管理工具
		MessageSenderManager messageSenderManager = MessageUtils.getMessageSenderManager();
		//短信发送结果记录
		Map<String, String> tempMap = null;
		//将所有短信发送结果记录添加到该List集合中，并作为最终结果返回
		List<Map<String,String>> result = Lists.newArrayList();
		String emp="",account="";
		for (Map.Entry<String, String> recipient : recipients.entrySet()) {
			emp =  recipient.getKey();
			account =  recipient.getValue();

			if (recipient.getValue() == null) {
				tempMap = Maps.newHashMap();
				tempMap.put("flag", FLAG_FAILURE);
				tempMap.put("remark", "短信发送失败，号码为空");
				tempMap.put("emp", emp);
			} else {
				try {
					//短信发送
					tempMap = messageSenderManager.send(emp, account, content);
				} catch (Exception e) {
					tempMap = Maps.newHashMap();
					tempMap.put("flag", FLAG_FAILURE);
					tempMap.put("remark", "短信发送失败，错误信息为："+e.getLocalizedMessage());
					tempMap.put("emp", emp);
					e.printStackTrace();
				}
			}
			result.add(tempMap);
		
		}
		return result;
	}
}
