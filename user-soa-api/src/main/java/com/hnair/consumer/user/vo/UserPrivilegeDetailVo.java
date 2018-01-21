package com.hnair.consumer.user.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户会员权益活动vo
 * @author zhangxianbin
 *
 */
@Setter
@Getter
public class UserPrivilegeDetailVo implements Serializable {

	private static final long serialVersionUID = 8395910823237672567L;

	private String privilegeName;
	
	private String privilegeDesc;
	
	private String privilegeImg;
	
	private String toPath;
	
}
