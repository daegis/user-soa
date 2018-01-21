package com.hnair.consumer.user.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * UserExtInfo Entity.
 */
@Setter
@Getter
@ToString
public class UserExtInfo implements Serializable {
	private static final long serialVersionUID = 4674895263966274104L;
	private Long id;
	private Integer fansCount;
	private Integer attentionCount;
	private Integer receivePraiseCount;
	private Integer receiveCollectCount;
	private Long userId;
	private Integer postCount;
	private Date createDate;
	private Date modifyDate;
	private String lastAppVersion;	
	private String lastChannel;
}
