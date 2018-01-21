package com.hnair.consumer.user.vo;

import java.io.Serializable;

/**
 * 
 * @author 许文轩
 * @comment 用户常用定位vo
 * @date 2018年1月2日 下午5:46:52
 *
 */
public class UserUsualLocationVo implements Serializable {

	private static final long serialVersionUID = 7866621475552409306L;

	private Long userId;

	private Long cityId;

	private Long destinationId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public Long getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(Long destinationId) {
		this.destinationId = destinationId;
	}

}
