package com.hnair.consumer.user.api;

import com.hnair.consumer.user.vo.CustomServiceVo;

/**
 * 
 * @author 许文轩
 * @comment Himi相关接口
 * @date 2017年11月6日 上午9:16:22
 *
 */
public interface IHimiApi {

	/**
	 * 
	 * @author 许文轩
	 * @comment 获取客服信息接口
	 * @date 2017年11月6日 上午9:35:17
	 */
	public CustomServiceVo getCustomServiceInfo(String customServiceThirdId);

}
