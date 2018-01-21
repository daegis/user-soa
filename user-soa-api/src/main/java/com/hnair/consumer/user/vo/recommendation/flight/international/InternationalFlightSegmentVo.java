package com.hnair.consumer.user.vo.recommendation.flight.international;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Description: 国际机票
 * All Rights Reserved.
 * @version 1.0  2017年2月16日 下午2:18:34  by 李超（li-ch3@hnair.com）创建
 */
@Getter
@Setter
public class InternationalFlightSegmentVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	//飞行时间
	private String journeyTime;
	//中转次数
	private String transferCount;
	//最迟出票时间
	private String lastTktTime;
	//航班代码
	private String airline;
	//航班代码
	private String airlineCode;
	//航空公司名称
	private String airlineName;
	//共享航空公司名称
	private String shareCarrierName;
	//航空公司logo
	private String airlineLogo;
	//机型名称
	private String equipment;
	//仓位
	private String cabinCode;
	//仓位等级
	private String cabinClass;
	//仓位描述
	private String cabinDesc;
	//剩余座位数
	private String availabilityCount;
	//出发机场
	private String departure;
	//出发机场名称
	private String departureName;
	//出发城市
	private String departureCityName;
	//到达城市
	private String arrivalCityName;
	//到达机场
	private String arrival;
	//到达机场名称
	private String arrivalName;
	//起飞航站楼
	private String departureTerminal;
	//到达航站楼
	private String arrivalTerminal;
	//起飞日期，时间戳
	private String departureDate;
	//到达日期，时间戳
	private String arrivalDate;
	//飞行时长，单位：分钟
	private String flightTime;
	//停留时长，单位：分钟
	private String stayTime;
	//是否共享航班：Y/N
	private String codeShare;
	//实际承运航班号
	private String opFltNo;
	//实际承运航司
	private String opFltAirline;
	//实际承运航司名称
	private String opFltAirlineName;
	//经停机场
	private String stopover;
	//经停城市
	private String stopCitys;
	//经停城市名称
	private String stopCitysName;
	//出发日期[yyyy-MM-dd]
	private String strDepartureDate;
	//出发时间[HH:mm]
	private String strDepartureTime;
	//到达日期[yyyy-MM-dd]
	private String strArrivalDate;
	//到达时间[HH:mm]
	private String strArrivalTime;
	// Q税需要的key
	private String segmentkey;
	
	

	public String getJourneyTime() {
		return journeyTime;
	}

	public void setJourneyTime(String journeyTime) {
		this.journeyTime = journeyTime;
	}

	public String getTransferCount() {
		return transferCount;
	}

	public void setTransferCount(String transferCount) {
		this.transferCount = transferCount;
	}

	public String getLastTktTime() {
		return lastTktTime;
	}

	public void setLastTktTime(String lastTktTime) {
		this.lastTktTime = lastTktTime;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getAirlineCode() {
		return airlineCode;
	}

	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public String getAirlineLogo() {
		return airlineLogo;
	}

	public void setAirlineLogo(String airlineLogo) {
		this.airlineLogo = airlineLogo;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getCabinDesc() {
		return cabinDesc;
	}

	public void setCabinDesc(String cabinDesc) {
		this.cabinDesc = cabinDesc;
	}


	public String getCabinClass() {
		return cabinClass;
	}

	public void setCabinClass(String cabinClass) {
		this.cabinClass = cabinClass;
	}
	public String getAvailabilityCount() {
		return availabilityCount;
	}

	public void setAvailabilityCount(String availabilityCount) {
		this.availabilityCount = availabilityCount;
	}

	public String getDeparture() {
		return departure;
	}

	public void setDeparture(String departure) {
		this.departure = departure;
	}

	public String getDepartureName() {
		return departureName;
	}

	public void setDepartureName(String departureName) {
		this.departureName = departureName;
	}

	public String getArrival() {
		return arrival;
	}

	public void setArrival(String arrival) {
		this.arrival = arrival;
	}

	public String getArrivalName() {
		return arrivalName;
	}

	public void setArrivalName(String arrivalName) {
		this.arrivalName = arrivalName;
	}

	public String getDepartureTerminal() {
		return departureTerminal;
	}

	public void setDepartureTerminal(String departureTerminal) {
		this.departureTerminal = departureTerminal;
	}

	public String getArrivalTerminal() {
		return arrivalTerminal;
	}

	public void setArrivalTerminal(String arrivalTerminal) {
		this.arrivalTerminal = arrivalTerminal;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public String getFlightTime() {
		return flightTime;
	}

	public void setFlightTime(String flightTime) {
		this.flightTime = flightTime;
	}

	public String getStayTime() {
		return stayTime;
	}

	public void setStayTime(String stayTime) {
		this.stayTime = stayTime;
	}

	public String getCodeShare() {
		return codeShare;
	}

	public void setCodeShare(String codeShare) {
		this.codeShare = codeShare;
	}

	public String getOpFltNo() {
		return opFltNo;
	}

	public void setOpFltNo(String opFltNo) {
		this.opFltNo = opFltNo;
	}

	public String getOpFltAirline() {
		return opFltAirline;
	}

	public void setOpFltAirline(String opFltAirline) {
		this.opFltAirline = opFltAirline;
	}

	public String getOpFltAirlineName() {
		return opFltAirlineName;
	}

	public void setOpFltAirlineName(String opFltAirlineName) {
		this.opFltAirlineName = opFltAirlineName;
	}

	public String getStopover() {
		return stopover;
	}

	public void setStopover(String stopover) {
		this.stopover = stopover;
	}

	public String getStrDepartureDate() {
		return strDepartureDate;
	}

	public void setStrDepartureDate(String strDepartureDate) {
		this.strDepartureDate = strDepartureDate;
	}

	public String getStrDepartureTime() {
		return strDepartureTime;
	}

	public void setStrDepartureTime(String strDepartureTime) {
		this.strDepartureTime = strDepartureTime;
	}

	public String getStrArrivalDate() {
		return strArrivalDate;
	}

	public void setStrArrivalDate(String strArrivalDate) {
		this.strArrivalDate = strArrivalDate;
	}

	public String getStrArrivalTime() {
		return strArrivalTime;
	}

	public void setStrArrivalTime(String strArrivalTime) {
		this.strArrivalTime = strArrivalTime;
	}
	public String getShareCarrierName() {
		return shareCarrierName;
	}
	
	public void setShareCarrierName(String shareCarrierName) {
		this.shareCarrierName = shareCarrierName;
	}



}
