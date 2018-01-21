package com.hnair.consumer.user.service;

import com.hnair.consumer.order.vo.RefScheduleOrderVo;
import com.hnair.consumer.user.vo.ScheduleVo;
import com.hnair.consumer.user.vo.recommendation.flight.FlightRecommendationVo;

import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIAN_Yingda
 * @date 2018/1/11 17:22
 */
public interface IRecommendService {
    FlightRecommendationVo getFlightRecommendList(String scheduleMainId) throws Exception;

    String test08(String s);

    void putRecommendInfo(String scheduleNo, List<RefScheduleOrderVo> ls, ScheduleVo scheduleVo) throws Exception;
}
