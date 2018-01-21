package com.hnair.consumer.user.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


/**
 * SignInActive Entity.
 */
@Getter
@Setter
public class SignInActive implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//列信息
	private Long id;
	
	private String activeName;
	
	private Integer totalPeriod;
	
	private java.util.Date startTime;
	
	private java.util.Date endTime;
	
	private Integer reset;
	
	private Integer basisIntegral;
	
	private Integer status;
	
	private String signInActiveRule;
	
	private java.util.Date createTime;
}

