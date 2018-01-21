package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * ScheduleMtsRecommend Entity.
 */
public class ScheduleMtsRecommend implements Serializable{
	
	//列信息
	private Integer id;
	
	private String name;
	
	private String url;
	
	private String pic;
	
	private String describe;
	

		
	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}
		
		
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
		
		
	public void setUrl(String value) {
		this.url = value;
	}
	
	public String getUrl() {
		return this.url;
	}
		
		
	public void setPic(String value) {
		this.pic = value;
	}
	
	public String getPic() {
		return this.pic;
	}
		
		
	public void setDescribe(String value) {
		this.describe = value;
	}
	
	public String getDescribe() {
		return this.describe;
	}
		
}

