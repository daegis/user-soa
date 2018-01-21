package com.hnair.consumer.user.vo;

import java.io.Serializable;
import java.util.List;
/**
 * Description: Himi后台返回前台用Bean
 * 根据传入信息返回相关的酒店信息、机票信息、错误结果
 * 
 * All Rights Reserved.
 * @version 1.0  2017年10月16日 下午1:12:36  by 张慧东（huid.zhang@haihangyun.com）创建
 */
public class HimiReplyInfoVo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * 返回结果的类型
	 * -1:错误结果
	 *  0:固定字符串
	 *  1:聊天记录
	 *  2:酒店信息
	 *  3:机票信息
	 */
	private String resultType;

	/**
	 * 回复消息内容
	 */
	private String replyContent;
	
	/**
	 * 酒店/机票信息
	 */
	private  List<Object> listData;
}
