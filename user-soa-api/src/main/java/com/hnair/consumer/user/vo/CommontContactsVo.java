package com.hnair.consumer.user.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

import com.hnair.consumer.user.model.CommonIdtype;

@Setter
@Getter
public class CommontContactsVo implements Serializable{

	private static final long serialVersionUID = -559790819523975560L;

	//列信息
	private Long id;
	
	private String contactsName;
	
	private String phone;
	
	private Integer gender;
	
	private String familyName;
	
	
	private Long idType;
	
	private Long contactsId;
	
	private String firstName;
	
	private Integer isChildren;
	
	private java.util.Date dateOfBirth;
	
	private Integer isOneself;
	
	private String nationality;
	
	private String email;
	
	private Long userId;
	
	private String phonePrefix;
	
	private String phoneCountryName;
		
	private List<CommonIdtype> commonIdtypes = new ArrayList<>();
}
