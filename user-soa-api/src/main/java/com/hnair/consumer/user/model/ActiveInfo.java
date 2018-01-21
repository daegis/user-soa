package com.hnair.consumer.user.model;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * ActiveInfo Entity.
 */
public class ActiveInfo implements Serializable{

	//列信息
	private Long id;

	private Integer activePosition;

	private Long productId;

	private java.util.Date createTime;

	private java.util.Date lastModifyTime;

	private String creator;

	private String lastModifyName;

	private String productName;

	private BigDecimal marketPrice;
	private BigDecimal showPrice;

	private String defultCoverPicUrl;

	public String getDefultCoverPicUrl() {
		return defultCoverPicUrl;
	}

	public void setDefultCoverPicUrl(String defultCoverPicUrl) {
		this.defultCoverPicUrl = defultCoverPicUrl;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public BigDecimal getShowPrice() {
		return showPrice;
	}

	public void setShowPrice(BigDecimal showPrice) {
		this.showPrice = showPrice;
	}

	public void setId(Long value) {
		this.id = value;
	}

	public Long getId() {
		return this.id;
	}


	public void setActivePosition(Integer value) {
		this.activePosition = value;
	}

	public Integer getActivePosition() {
		return this.activePosition;
	}


	public void setProductId(Long value) {
		this.productId = value;
	}

	public Long getProductId() {
		return this.productId;
	}


	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}


	public void setLastModifyTime(java.util.Date value) {
		this.lastModifyTime = value;
	}

	public java.util.Date getLastModifyTime() {
		return this.lastModifyTime;
	}


	public void setCreator(String value) {
		this.creator = value;
	}

	public String getCreator() {
		return this.creator;
	}


	public void setLastModifyName(String value) {
		this.lastModifyName = value;
	}

	public String getLastModifyName() {
		return this.lastModifyName;
	}

}

