package com.thinkgem.jeesite.modules.sys.security;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;

/**
 * 短信发送管理工具
 * @ClassName: MessageSenderManager 
 * @Description: TODO 管理短信发送功能
 * @author: WangCong
 * @date: 2016年5月19日 下午5:17:31
 */
public class MessageSenderManager {
	
	//连接超时默认时间
	private final static Integer HTTP_TIMEOUT = 1000 * 5;
	
	private final static String TYPE = "mobile";
	
	private final static String TUNNEL = "shouyisms";
	
	private final static String SENDER_ID = "1";
	
	private final static String FLAG_SUCCESS = "success";
	
	private final static String FLAG_FAILURE = "failure";
	
	/**
	 * 进行身份验证,通过则获得身份令牌
	 * @Title: authentication 
	 * @Description: TODO 进行身份验证
	 * @Date: 2016年5月19日 下午5:56:29
	 * @author: WangCong
	 * @return 身份令牌
	 * @throws Exception POST数据到服务器期间发生错误
	 */
	private String authentication() throws Exception{
        //生产接口地址
		String realurl1 ="http://openapi.jnu.edu.cn/service/app/security/getAccessToken.html";

        Map<String , String> params = Maps.newHashMap();
        params.put("appid",Global.getMsgAppId());
        params.put("appsecret",Global.getMsgAppSecret());

        //接口返回结果
        String responseMsg= MessageSenderManager.postURL(realurl1, null, params, "UTF-8");
        return createTokenId(responseMsg);
	}

	/**
	 * 获取身份令牌
	 * @Title: createTokenId 
	 * @Description: TODO 从身份认证结果信息中获取身份令牌
	 * @Date: 2016年5月20日 上午10:26:40
	 * @author: WangCong
	 * @param responseMsg 身份认证结果信息
	 * @return 身份令牌
	 */
	private String createTokenId(String responseMsg) throws Exception {
		String tokenId = null;
        if (StringUtils.isNotBlank(responseMsg)) {
        	JSONObject jsonObject=JSONObject.fromObject(responseMsg);
        	if (jsonObject.containsKey("result")){
        		//result=200,成功
        		int result=jsonObject.getInt("result");
        		if (result==200){
        			if (jsonObject.containsKey("data")){
        				JSONObject data =jsonObject.getJSONObject("data");
        				tokenId=data.getString("tokenid");
        			}
        		}
        	}
        }
        return tokenId;
	}

	/**
	* 短信发送
	* @Title: messageSend
	* @Description: TODO 短信发送
	* @Date: 2016年5月19日 下午5:37:35
	* @author: WangCong
	* @param tokenId 认证令牌
	* @param recipient 接收者
	* @param content 短信内容
	* @throws Exception POST数据到服务器期间发生错误
	* @return 短信发送结果(Map<String,String>数据格式，包含一个flag:success,failure，以及一个remark)
	*/
	private Map<String, String> messageSend(String tokenId, String emp, String account, String content) throws Exception{
		String url = Global.getMsgAppUrl();

		//头部的 key必须是 User-Token
		Map<String, String> header = Maps.newHashMap();
		header.put("User-Token", tokenId); 

		//设置相关发送参数
		Map<String, String> params = Maps.newHashMap();
		params.put("senderid", SENDER_ID);
		params.put("type", TYPE);
		params.put("tunnel", TUNNEL);
		params.put("target", account);//接收者
		params.put("message", content);//短信内容
		
		//接口返回结果
		String responseMsg = MessageSenderManager.postURL(url, header, params, "UTF-8");
		Map<String, String> msg =  createReturnMap(responseMsg);
		msg.put("emp", emp);
		return msg;
	}

	
	/**
	 * 封装短信发送结果信息
	 * @Title: createReturnMap 
	 * @Description: TODO 将短信发送结果信息封装到一个集合中，并作为最终结果返回
	 * @Date: 2016年5月20日 上午10:20:24
	 * @author: WangCong
	 * @param responseMsg 短信发送结果信息
	 * @return 短信发送结果相关返回信息
	 */
	private Map<String, String> createReturnMap(String responseMsg) {
		//将短信发送的结果封装到该集合中来
		Map<String, String> returnMap = Maps.newHashMap();
		
		if (StringUtils.isNotBlank(responseMsg)) {
			JSONObject jsonObject = JSONObject.fromObject(responseMsg);
			if (jsonObject.containsKey("result")) {
				/**
				 * result=200, 成功; result=404, tokenId为空; result=410,会话已过期,需重新进行身份验证;
				 * result=403, 访问受限，没有访问权限
				 */
				int result = jsonObject.getInt("result");

				if (result == 200) {//发送成功
					returnMap.put("flag", FLAG_SUCCESS);
					returnMap.put("remark", "短信发送成功");
				} else {//错误提示信息
					String msg = jsonObject.getString("message");
					returnMap.put("flag", FLAG_FAILURE);
					returnMap.put("remark", "短信发送失败，错误信息如下："+msg);
				}
			} else {//无结果信息返回则认为发送失败
				returnMap.put("flag", FLAG_FAILURE);
				returnMap.put("remark", "无结果信息返回");
			}
		} else {//无任何相关信息返回则认为发送失败
			returnMap.put("flag", FLAG_FAILURE);
			returnMap.put("remark", "无任何相关信息返回");
		}
		return returnMap;
	}

	/**
	 * POST数据到服务器
	 * @Title: postURL 
	 * @Description: TODO 将数据通过POST的方式发送到服务器
	 * @Date: 2016年5月19日 下午5:32:36
	 * @author: WangCong
	 * @param accessurl 服务器地址
	 * @param headers 头部信息
	 * @param params 相关参数
	 * @param encoding 编码方式
	 * @return 服务器返回信息
	 * @throws Exception
	 */
	 private static String postURL(String accessurl , Map<String , String> headers , Map<String , String> params , String encoding) throws Exception {
	        StringBuffer resultBuffer = new StringBuffer();
	        String content = null;
	        StringBuffer contentstr = new StringBuffer();
	        //处理参数
	        if(params != null){
	            Set<String> keys = params.keySet();
	            for(String key : keys){
	                String value = params.get(key);
	                contentstr.append(key).append("=").append(value).append("&");
	            }
	            if(contentstr.length()>0)contentstr.delete(contentstr.length()-1 , contentstr.length());
	            content=contentstr.toString();
	        }

	        HttpURLConnection conn = null;
	        try{
	            URL url = new URL(accessurl);
	            conn = (HttpURLConnection) url.openConnection();

	            //连接设置
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            conn.setUseCaches(false);

	            conn.setRequestMethod("POST");
	            conn.setRequestProperty("Accept-Charset", encoding);
	            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset="+ encoding);
	            conn.setRequestProperty("Content-Length" , Integer.toString(content.getBytes(encoding).length));

	            //加入header头部信息
	            if(headers != null){
	                Set<String> keys = headers.keySet();
	                for(String key : keys){
	                    conn.addRequestProperty(key , headers.get(key));
	                }
	            }

	            //设置链接超时
	            conn.setReadTimeout(HTTP_TIMEOUT);

	            //链接到服务器
	            conn.connect();

	            OutputStream os = conn.getOutputStream();
	            os.write(content.getBytes(encoding));
	            os.flush();

	            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),encoding));
	            String aline = null;
	            while((aline = reader.readLine()) != null){
	                resultBuffer.append(aline);
	            }
	        }catch(Exception ex){
	            ex.printStackTrace();
	            throw new Exception("连接到服务器发生错误，具体错误信息如下：" + ex);
	        } finally {//释放连接
	            if(conn != null)conn.disconnect();
	        }
	        return resultBuffer.toString();
	    }
	 
	 /**
	  * 短信发送
	  * @Title: send 
	  * @Description: TODO 短信发送
	  * @Date: 2016年5月20日 上午10:42:48
	  * @author: WangCong
	  * @param recipient 短信接收者
	  * @param content 短信发送内容
	  * @return 短信发送结果信息
	  * @throws Exception 短信发送期间发生的错误
	  */
	 public Map<String, String> send(String emp, String recipient, String content) throws Exception{
		 return messageSend(authentication(), emp, recipient, content);
	 }
	 
}
