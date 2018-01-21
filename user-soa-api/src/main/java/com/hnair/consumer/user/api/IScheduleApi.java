package com.hnair.consumer.user.api;

import java.util.List;

import com.hnair.consumer.order.vo.RefScheduleQueryVo;
import com.hnair.consumer.user.vo.FlightSearchRealTimeDecVo;
import com.hnair.consumer.user.vo.ScheduleReqVo;
import com.hnair.consumer.user.vo.ScheduleRespVo;
import com.hnair.consumer.user.vo.ScheduleVo;
import com.hnair.consumer.user.vo.UserMessageVo;
import com.hnair.consumer.user.vo.recommendation.flight.FlightRecommendationVo;

/**
 * 
 * @author jy
 *
 */
public interface IScheduleApi {

	/**
	 * 
	 * @param customServiceThirdId
	 * @return
	 */
	UserMessageVo<ScheduleReqVo> saveScheduleCheckinInfo(ScheduleReqVo scheduleReqVo);
	
	ScheduleRespVo getScheduleMainInfo(Long userId);
	
	ScheduleVo getScheduleDetailInfo(String scheduleNo, Long userId);
	
	List<FlightSearchRealTimeDecVo> getFligtRealTimeInfo(String scheduleNo);
	
    FlightRecommendationVo getFlightRecommendList(String userToken, String scheduleMainId) throws Exception;

    String testApi(String s) throws Exception;
}
