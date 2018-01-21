package com.hnair.consumer.user.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Description: 航班搜索vo
 * All Rights Reserved.
 * @version 1.0  2017年1月12日 上午11:39:42  by 李超（li-ch3@hnair.com）创建
 */
/**
 * @author Lenbo
 *
 */
public class FlightSearchVo implements Serializable {

	private static final long serialVersionUID = 6031964456690855768L;
	/**
	 * 起飞时刻(24小时制)，如2020
	 */
	private String dptTime;
	/**
	 * 到达时刻(24小时制)，如2235
	 */
	private String arrTime;
	/**
	 * 航空公司代码，如CA
	 */
	private String carrier;
	/**
	 * 航班号,如CA1234
	 */
	private String code;
	/**
	 * 销售的舱位代码，如S
	 */
	private String saleCabinCode;
	/**
	 * 舱位描述
	 */
	private String cabinDesc;
	/**
	 * 销售价，此为列表页展示的报价 adu成人chi儿童baby婴儿
	 */
	private String adultPrice;
	private String childPrice;
	private String babyPrice;
	/**
	 * 折扣 8.0
	 */
	private String discount;
	/**
	 * 机建费,adu成人chi儿童baby婴儿
	 */
	private String adultTax;
	private String childTax;
	private String babyTax;
	/**
	 * 燃油费,adu成人chi儿童baby婴儿
	 */
	private String adultFuel;
	private String childFuel;
	private String babyFuel;
	/**
	 * 准点率
	 */
	private String onTimeRate;
	/**
	 * 是否有餐食，false：无餐食，true：有餐食
	 */
	private String meal;
	/**
	 * 机型
	 */
	private String planeType;
	/**
	 * 0代表直达，1代表一次经停
	 */
	private String stops;
	/**
	 * 经停城市,数组结构,如:(["厦门","福建"])
	 */
	private String stopCities;
	/**
	 * 若为非共享航班则为空；若为共享航班则填入共享航班号
	 */
	private String codeShare;
	/**
	 * 大于9为A；小于9为具体数字，此时航班列表页展示“少量”提示，L Q S C等 表示无库存
	 */
	private String seatsForSale;
	/**
	 * 出发航站楼
	 */
	private String depTerminal;
	/**
	 * 到达航站楼
	 */
	private String arrTerminal;
	/**
	 * 行李额说明
	 */
	private String luggageInfo;
	/**
	 * 报价标识,后传给预定接口(sessionId)
	 */
	private String searchKey;
	/**
	 * 出发机场3字码
	 */
	private String startAirport;
	/**
	 * 到达机场3字码
	 */
	private String endAirport;
	/**
	 * 出发机场名称
	 */
	private String startAirportName;
	/**
	 * 到达机场名称
	 */
	private String endAirportName;
	/**
	 * 航班LOGO
	 */
	private String airlineLogo;
	/**
	 * 航空公司名称
	 */
	private String airlineName;
	/**
	 * 起飞时间
	 */
	private String flightTime;
	/**
	 * 舱等
	 */
	private String cabinClass;
	/**
	 * 渠道
	 * 途牛=tUN;51游=51Y
	 */
	private String supplier;
	/**
	 * 飞行时长
	 */
	private String duration;

	//共享航班名称
	private String shareName;
	//规则基价
	private BigDecimal basePrice;
	//成人售卖价格
	private String adultSalePrice;
	//儿童售卖价格
	private String childSalePrice;
	//productCode
	private String productCode;
	//间隔天数
	private String flightDays;

	public String getAirlineLogo() {
		return airlineLogo;
	}
	public void setAirlineLogo(String airlineLogo) {
		this.airlineLogo = airlineLogo;
	}
	public String getAirlineName() {
		return airlineName;
	}
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
	public String getStartAirportName() {
		return startAirportName;
	}
	public void setStartAirportName(String startAirportName) {
		this.startAirportName = startAirportName;
	}
	public String getEndAirportName() {
		return endAirportName;
	}
	public void setEndAirportName(String endAirportName) {
		this.endAirportName = endAirportName;
	}
	public String getStartAirport() {
		return startAirport;
	}
	public void setStartAirport(String startAirport) {
		this.startAirport = startAirport;
	}
	public String getEndAirport() {
		return endAirport;
	}
	public void setEndAirport(String endAirport) {
		this.endAirport = endAirport;
	}
	public String getDptTime() {
		return dptTime;
	}
	public void setDptTime(String dptTime) {
		this.dptTime = dptTime;
	}
	public String getArrTime() {
		return arrTime;
	}
	public void setArrTime(String arrTime) {
		this.arrTime = arrTime;
	}
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSaleCabinCode() {
		return saleCabinCode;
	}
	public void setSaleCabinCode(String saleCabinCode) {
		this.saleCabinCode = saleCabinCode;
	}
	public String getCabinDesc() {
		return cabinDesc;
	}
	public void setCabinDesc(String cabinDesc) {
		this.cabinDesc = cabinDesc;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getOnTimeRate() {
		return onTimeRate;
	}
	public void setOnTimeRate(String onTimeRate) {
		this.onTimeRate = onTimeRate;
	}
	public String getMeal() {
		return meal;
	}
	public void setMeal(String meal) {
		this.meal = meal;
	}
	public String getPlaneType() {
		return planeType;
	}
	public void setPlaneType(String planeType) {
		this.planeType = planeType;
	}
	public String getStops() {
		return stops;
	}
	public void setStops(String stops) {
		this.stops = stops;
	}
	public String getStopCities() {
		return stopCities;
	}
	public void setStopCities(String stopCities) {
		this.stopCities = stopCities;
	}
	public String getCodeShare() {
		return codeShare;
	}
	public void setCodeShare(String codeShare) {
		this.codeShare = codeShare;
	}
	public String getSeatsForSale() {
		return seatsForSale;
	}
	public void setSeatsForSale(String seatsForSale) {
		this.seatsForSale = seatsForSale;
	}
	public String getDepTerminal() {
		return depTerminal;
	}
	public void setDepTerminal(String depTerminal) {
		this.depTerminal = depTerminal;
	}
	public String getArrTerminal() {
		return arrTerminal;
	}
	public void setArrTerminal(String arrTerminal) {
		this.arrTerminal = arrTerminal;
	}
	public String getLuggageInfo() {
		return luggageInfo;
	}
	public void setLuggageInfo(String luggageInfo) {
		this.luggageInfo = luggageInfo;
	}
	public String getSearchKey() {
		return searchKey;
	}
	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
	}

	public String getFlightTime() {
		return flightTime;
	}

	public void setFlightTime(String flightTime) {
		this.flightTime = flightTime;
	}
	public String getCabinClass() {
		return cabinClass;
	}
	public void setCabinClass(String cabinClass) {
		this.cabinClass = cabinClass;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getShareName() {
		return shareName;
	}

	public void setShareName(String shareName) {
		this.shareName = shareName;
	}

	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getAdultPrice() {
		return adultPrice;
	}
	public void setAdultPrice(String adultPrice) {
		this.adultPrice = adultPrice;
	}
	public String getChildPrice() {
		return childPrice;
	}
	public void setChildPrice(String childPrice) {
		this.childPrice = childPrice;
	}
	public String getBabyPrice() {
		return babyPrice;
	}
	public void setBabyPrice(String babyPrice) {
		this.babyPrice = babyPrice;
	}
	public String getAdultTax() {
		return adultTax;
	}
	public void setAdultTax(String adultTax) {
		this.adultTax = adultTax;
	}
	public String getChildTax() {
		return childTax;
	}
	public void setChildTax(String childTax) {
		this.childTax = childTax;
	}
	public String getBabyTax() {
		return babyTax;
	}
	public void setBabyTax(String babyTax) {
		this.babyTax = babyTax;
	}
	public String getAdultFuel() {
		return adultFuel;
	}
	public void setAdultFuel(String adultFuel) {
		this.adultFuel = adultFuel;
	}
	public String getChildFuel() {
		return childFuel;
	}
	public void setChildFuel(String childFuel) {
		this.childFuel = childFuel;
	}
	public String getBabyFuel() {
		return babyFuel;
	}
	public void setBabyFuel(String babyFuel) {
		this.babyFuel = babyFuel;
	}
	public BigDecimal getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(BigDecimal basePrice) {
		this.basePrice = basePrice;
	}
	public String getAdultSalePrice() {
		return adultSalePrice;
	}
	public void setAdultSalePrice(String adultSalePrice) {
		this.adultSalePrice = adultSalePrice;
	}
	public String getChildSalePrice() {
		return childSalePrice;
	}
	public void setChildSalePrice(String childSalePrice) {
		this.childSalePrice = childSalePrice;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getFlightDays() {
		return flightDays;
	}
	public void setFlightDays(String flightDays) {
		this.flightDays = flightDays;
	}
	
}
