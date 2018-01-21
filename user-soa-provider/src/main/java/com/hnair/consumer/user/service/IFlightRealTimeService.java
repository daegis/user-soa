package com.hnair.consumer.user.service;

import java.util.List;

import com.hnair.consumer.flight.vo.RealTimeQuery.RealTimeQueryRespFlightInfo;
import com.hnair.consumer.user.vo.FlightSearchRealTimeDecVo;
import com.hnair.consumer.user.vo.ScheduleRespVo;

public interface IFlightRealTimeService {
	
	void buildFlightSearchDecVos(List<FlightSearchRealTimeDecVo> resultList, List<RealTimeQueryRespFlightInfo> realTimeFlightInfos) ;
	

}
