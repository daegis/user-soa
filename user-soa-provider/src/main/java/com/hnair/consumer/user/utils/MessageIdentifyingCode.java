package com.hnair.consumer.user.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.alibaba.dubbo.common.utils.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esms.MessageData;
import com.esms.PostMsg;
import com.esms.common.entity.Account;
import com.esms.common.entity.GsmsResponse;
import com.esms.common.entity.MTPack;
import com.esms.common.entity.MTPack.MsgType;
import com.esms.common.entity.MTPack.SendType;
import com.hnair.consumer.utils.HttpClientUtils;
import com.hnair.consumer.utils.system.ConfigPropertieUtils;

/**
 * Description: 发送短信验证码
 * All Rights Reserved.
 * @version 1.0  2016年11月2日 上午9:24:48  by 李超（li-ch3@hnair.com）创建
 */
public class MessageIdentifyingCode {
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageIdentifyingCode.class);
	//API服务接口请求调用地址
	private static String  URL = ConfigPropertieUtils.getString("yimei_send_message_url");
	//序列号
	private static String cdkey = ConfigPropertieUtils.getString("yimei_send_message_cdkey");
	//密码
	private static String password = ConfigPropertieUtils.getString("yimei_send_message_password");
	//==============海航通信验证码参数====================//
	//海航通信技术账户
	private static String  tsd_username = "BU3hiapp";
	//海航通信技术短信密码
	private static String  tsd_password = "qFhWxNcz";
	//海航通信国际短信技术账户
	private static String  tsd_guoji_username = "BU3hiapp";
	//海航通信国际短信技术短信密码
	private static String  tsd_guoji_password = "qFhWxNcz";
	//有美海航通信技术账户
	private static String  tsd_ym_username = "BU3";
	//有美海航通信技术短信密码
	private static String  tsd_ym_password = "2YCyQNQJ";
	//有美海航通信国际短信技术账户
	private static String  tsd_ym_guoji_username = "BU3guoji";
	//有美海航通信国际短信技术短信密码
	private static String  tsd_ym_guoji_password = "TjOPU2l1";
	//海航通信技术短信密码
	private static String  tsd_url = "api.10044.cn:8090";

	/**
	 * Description: 发送短信(亿美)
	 * @Version1.0 2016年11月2日 下午12:15:38 by 李超（li-ch3@hnair.com）创建
	 * @param phoneNumber
	 * @param content
	 * @return  -2	     客户端	客户端异常
     *   		  -9000	数据格式错误,数据超出数据库允许范围
	 * 	   		  -9001	客户端,所有业务	序列号格式错误
	 *	          -9002	客户端,所有业务	密码格式错误
	 *	          -9003	客户端,所有业务	客户端Key格式错误
	 *	          -9004	客户端,转发业务	设置转发格式错误
	 *               0     成功
	 */
	public static String sendMessage(String phoneNumber,String content) throws Exception{
		Map<String,String> params=new HashMap<String,String>();
		params.put("cdkey", cdkey);
		params.put("password", password);
		params.put("phone", phoneNumber);
		params.put("message", content.trim());
		//返回值0代表成功
		String result="0";
		try {
			 result=HttpClientUtils.sendPost(URL, params, HttpClientUtils.URL_PARAM_CHARSET_UTF8);
	         Document doc = DocumentHelper.parseText(result.trim());
			 Element root = doc.getRootElement();  
			 Element eName = root.element("error"); 
			 result=eName.getTextTrim();
		} catch (Exception e) {
			e.printStackTrace();
			if(!"0".equals(result)){
				result="-2";
			}
			LOGGER.error("错误码:"+result);
		}
		return result;
	} 
	/**
	 * Description: 海航通信短信平台
	 * @Version1.0 2017年1月3日 下午3:23:00 by 李超（li-ch3@hnair.com）创建
	 * @param phoneNumber
	 * @param content
	 * @return
	 * @throws Exception
	 */
	public static String sendMessageHNA(String phoneNumber,String content) throws Exception{
		if(StringUtils.isEmpty(content)){
			return "0";
		}
		String result="";
		String hnaUsername = tsd_username;
		String hnaPassword = tsd_password;
		String guojiUsername = tsd_guoji_username;
		String guojiPassword = tsd_guoji_password;		
		if(content.startsWith("【有美】")){
			content =content.replace("【有美】","");
			hnaUsername = tsd_ym_username;
			hnaPassword = tsd_ym_password;
			guojiUsername = tsd_ym_guoji_username;
			guojiPassword = tsd_ym_guoji_password;
		}else{
			content =content.replace("【嗨途】","");
		}	
		try {
			Account account=null;
			//如果是手机号是86开头走国内短信,否则走国外
			if("86".equals(phoneNumber.substring(0,2))){
				LOGGER.info("tsd_username:"+hnaUsername+",tsd_password:"+hnaPassword+",tsd_url:"+tsd_url.split(":")[0]+",port:"+tsd_url.split(":")[1]);
				 account = new Account(hnaUsername, hnaPassword);//国内
			}else{
				LOGGER.info("tsd_guoji_username:"+guojiUsername+",tsd_guoji_password:"+guojiPassword+",tsd_url:"+tsd_url.split(":")[0]+",port:"+tsd_url.split(":")[1]);
				 account = new Account(guojiUsername, guojiPassword);//国际
			}
			PostMsg pm = new PostMsg();
			pm.getWsHost().setHost(tsd_url.split(":")[0],8088);
			pm.getCmHost().setHost(tsd_url.split(":")[0],Integer.parseInt(tsd_url.split(":")[1]));//设置网关的IP和port，用于发送信息
			MTPack pack = new MTPack();
			pack.setBatchID(UUID.randomUUID());
			pack.setBatchName("发送验证码");
			pack.setMsgType(MsgType.SMS); //SMS短信发送，MMS彩信发送
			//如果是手机号是86开头走国内短信,否则走国外
			if("86".equals(phoneNumber.substring(0,2))){
				pack.setBizType(44); //业务类型
			}else{
				pack.setBizType(47); //国外业务类型
			}
			pack.setDistinctFlag(false); //是否过滤重复号码
			pack.setSendType(SendType.MASS);
			ArrayList<MessageData> msgs = new ArrayList<MessageData>();
			/** 单发，一号码一内容 */		
			msgs.add(new MessageData(phoneNumber, content));
			pack.setMsgs(msgs);
			GsmsResponse resp = pm.post(account, pack);
			result = String.valueOf(resp.getResult());
			LOGGER.info("返回结果:"+result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	} 
	public static void main(String[] args) {
			try {
				String a="8613522260176".substring(0,2);
				System.out.println(a+"  "+a.equals("86"));
			sendMessageHNA("8613522260176","【嗨途】验证码:123445，请尽快填写完成验证，为保障您账户安全，切勿将此码告诉他人。");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
