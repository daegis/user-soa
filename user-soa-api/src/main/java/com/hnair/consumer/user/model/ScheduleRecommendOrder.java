package com.hnair.consumer.user.model;

import java.io.Serializable;


/**
 * ScheduleRecommendOrder Entity.
 */
public class ScheduleRecommendOrder implements Serializable{
	
	//列信息
	private Integer id;
	
	private Integer ttl;
	
	private Integer tol;
	
	private String name;
	
	private Integer weight;
	
	private Integer inUse;
	

		
	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
		return this.id;
	}
		
		
	public void setTtl(Integer value) {
		this.ttl = value;
	}
	
	public Integer getTtl() {
		return this.ttl;
	}
		
		
	public void setTol(Integer value) {
		this.tol = value;
	}
	
	public Integer getTol() {
		return this.tol;
	}
		
		
	public void setName(String value) {
		this.name = value;
	}
	
	public String getName() {
		return this.name;
	}
		
		
	public void setWeight(Integer value) {
		this.weight = value;
	}
	
	public Integer getWeight() {
		return this.weight;
	}
		
		
	public void setInUse(Integer value) {
		this.inUse = value;
	}
	
	public Integer getInUse() {
		return this.inUse;
	}
		
}

