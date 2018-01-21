package com.hnair.consumer.user.listener;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import com.hnair.consumer.user.api.IUserApi;
import com.hnair.consumer.user.model.MessageCode;
import com.hnair.consumer.user.vo.MobileMessageVo;
import com.hnair.consumer.user.vo.UserMessageVo;

public class SendPhoneMessageListener {
	
	private final Logger log = LoggerFactory.getLogger(SendPhoneMessageListener.class);
	
	protected final Integer REDIS_LOCK_TIMEOUT_120 = 120;

	@Autowired
	private IUserApi userApi;
	
	@SuppressWarnings("rawtypes")
	@Resource(name="masterRedisTemplate")
    protected RedisTemplate masterRedisTemplate;  
	
	/**
	 * Description: 发送短信
	 */
	@SuppressWarnings("unchecked")
	public void handleMessage(MobileMessageVo messageVo) {
		try {
			log.info("messageVo {}", messageVo.toString());
			if (messageVo == null || StringUtils.isEmpty(messageVo.getUuid()) || 
				StringUtils.isEmpty(messageVo.getPhoneNo()) || StringUtils.isEmpty(messageVo.getContent())) {
				return ;
			}
			
			String phone_message_key = "phone_message_prefix_" + messageVo.getUuid();
			if (!masterRedisTemplate.hasKey(phone_message_key)) {
				UserMessageVo<MessageCode>  resultVo = userApi.sendOrderStatusMessage(messageVo.getRegion(), messageVo.getPhoneNo(), messageVo.getContent());
				log.info("发送短信 状态={}, messageVo = {}", resultVo.isResult(), messageVo.toString());
				if (resultVo.isResult()) {
					masterRedisTemplate.opsForValue().setIfAbsent(phone_message_key, 1L);
					masterRedisTemplate.expire(phone_message_key, REDIS_LOCK_TIMEOUT_120, TimeUnit.MINUTES);
				}
			} else {
				log.info("发送短信重复发送, messageVo = {}", messageVo.toString());
			}
		} catch (Exception e) {
			log.info("发送短信 Exception,  messageVo = {}", messageVo.toString());
			log.error("", e);
		}
	}
}
