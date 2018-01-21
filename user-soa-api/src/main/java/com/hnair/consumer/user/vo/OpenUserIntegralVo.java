package com.hnair.consumer.user.vo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenUserIntegralVo implements Serializable {

	private static final long serialVersionUID = -8897582543493951202L;
	
	private String userName;
	
	private String mobile;
	
	private Integer integral;
	
	private String gender;
	
	private String changeFrom;

}
