package com.hnair.consumer.user.api.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.order.api.IOrderBaseApi;
import com.hnair.consumer.order.api.IRefScheduleOrderApi;
import com.hnair.consumer.order.vo.RefScheduleOrderVo;
import com.hnair.consumer.order.vo.RefScheduleQueryVo;
import com.hnair.consumer.poi.api.IPoiGEOApi;
import com.hnair.consumer.user.api.IScheduleApi;
import com.hnair.consumer.user.enums.UserErrorCodeEnum;
import com.hnair.consumer.user.model.ScheduleDetail;
import com.hnair.consumer.user.model.ScheduleMain;
import com.hnair.consumer.user.service.IRecommendService;
import com.hnair.consumer.user.service.IScheduleService;
import com.hnair.consumer.user.vo.FlightSearchRealTimeDecVo;
import com.hnair.consumer.user.vo.ScheduleReqVo;
import com.hnair.consumer.user.vo.ScheduleRespVo;
import com.hnair.consumer.user.vo.ScheduleVo;
import com.hnair.consumer.user.vo.UserMessageVo;
import com.hnair.consumer.user.vo.recommendation.flight.FlightRecommendationVo;
import com.hnair.consumer.utils.SafeConvert;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("scheduleApi")
public class ScheduleApiImpl implements IScheduleApi {

    private static Logger logger = LoggerFactory.getLogger(HimiApiImpl.class);

    @Resource(name = "ucenterCommonService")
    private ICommonService scheduleCommonService;

    @Autowired
    private IRecommendService recommendService;


    @Resource(name = "refScheduleOrderApi")
    IRefScheduleOrderApi refScheduleOrderApi;
    
    @Autowired
    private IScheduleService scheduleService;

    /**
     * 添加行程信息
     */
    private final String SCHEDULE_MAIN_INFO_KEY = "schedule_main_info_cache_";

    @Override
    @SuppressWarnings("unchecked")
    public UserMessageVo<ScheduleReqVo> saveScheduleCheckinInfo(ScheduleReqVo scheduleReqVo) {
        UserMessageVo<ScheduleReqVo> result = new UserMessageVo<ScheduleReqVo>();
        result.setResult(false);
        try {
            if (scheduleReqVo == null) {
                result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12050.getErrorCode().toString());
                result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12050.getErrorMessage());
                return result;
            }

            scheduleCommonService.updateBySqlId(ScheduleDetail.class, "updateScheduleCheckinInfo", "checkinStatus", scheduleReqVo.getCheckinStatus(),
                    "seatId", scheduleReqVo.getSeatId(), "orderSubNo", scheduleReqVo.getOrderSubNo());


        } catch (Exception e) {
            logger.info("更新行程信息异常", e);
            result.setErrorCode(UserErrorCodeEnum.ERROR_CODE_12050.getErrorCode().toString());
            result.setErrorMessage(UserErrorCodeEnum.ERROR_CODE_12050.getErrorMessage());
            return result;
        }

        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ScheduleRespVo getScheduleMainInfo(Long userId) {
        if (userId == null) {
            logger.error("传入的userId为空");
            return null;
        }
        log.info("查询主行程userId：{}", userId);
        ScheduleRespVo scheduleServiceVo = new ScheduleRespVo();
        try {
            List<ScheduleRespVo> scheduleRespVoList = scheduleCommonService.getListBySqlId(ScheduleDetail.class, "selectScheduleMainByNowDateAndUserId", "userId", userId);
            if (CollectionUtils.isNotEmpty(scheduleRespVoList)) {
            	scheduleServiceVo = scheduleRespVoList.get(0);               
           }else{
        	   scheduleRespVoList = scheduleCommonService.getListBySqlId(ScheduleDetail.class, "selectScheduleMainFlightByNowDateAndUserId", "userId", userId);
        	   if (CollectionUtils.isNotEmpty(scheduleRespVoList)) {
               	scheduleServiceVo = scheduleRespVoList.get(0);  
        	   }
           }
            log.info("查询主行程scheduleServiceVo：{}", scheduleServiceVo);
        } catch (Exception e) {
            logger.info("查询行程主页信息异常", e);
        }


        return scheduleServiceVo;
    }

    @Override
    @SuppressWarnings("unchecked")
    public ScheduleVo getScheduleDetailInfo(String scheduleNo, Long userId) {
        if (StringUtils.isBlank(scheduleNo)) {
            logger.error("传入的scheduleNo为空");
            return null;
        }
        if (userId == null) {
            logger.error("传入的userId为空");
            return null;
        }
        log.info("行程scheduleNo：{}", scheduleNo);
        ScheduleVo scheduleVo = new ScheduleVo();
        ScheduleMain scheduleMain = scheduleCommonService.get(ScheduleMain.class, "scheduleNo", scheduleNo);
        RefScheduleQueryVo param = new RefScheduleQueryVo();
        
		param.setUserId(userId);

		param.setBeginDate(scheduleMain.getBeginDate());
		param.setEndDate(scheduleMain.getEndDate());
		param.setDestCity(scheduleMain.getDestCity());
		param.setDestCountry(scheduleMain.getDestCountry());
		param.setOrderNo(scheduleMain.getOrderNo());
		param.setMainOrderType("flight");
      //行程关联订单
        List<RefScheduleOrderVo> refScheduleOrderList = new ArrayList<RefScheduleOrderVo>();
        try {
        	//map包含了关联订单信息，以及主行程的订单状态
        	Map<String,Object> map =refScheduleOrderApi.getRefScheduleOrder(param);
            refScheduleOrderList = (List<RefScheduleOrderVo>)map.get("refOrders");
            String orderStatusStr = (String)map.get("orderStatus");
            //行程详细信息
            List<ScheduleRespVo> scheduleDetailList = scheduleService.getScheduleDetail(scheduleNo, orderStatusStr);
            scheduleVo.setScheduleDetailInfo(scheduleDetailList);
            scheduleVo.setRefScheduleOrderInfo(refScheduleOrderList);

            // 推荐服务方法-缐英达
            recommendService.putRecommendInfo(scheduleNo, refScheduleOrderList, scheduleVo);
            // 推荐服务结束

        } catch (Exception e) {
            logger.info("查询行程详细信息异常", e);
        }

        return scheduleVo;

    }
    @Override
    public List<FlightSearchRealTimeDecVo> getFligtRealTimeInfo(String scheduleNo){
    	 if (StringUtils.isBlank(scheduleNo)) {
             logger.error("传入的scheduleNo为空");
             return null;
         }
    	return scheduleService.getFligtRealTimeInfo(scheduleNo);
    }

    @Override
    public FlightRecommendationVo getFlightRecommendList(String userToken, String scheduleMainId) throws Exception {
        log.info("用户token：{},主行程id：{}", userToken, scheduleMainId);
        // 检查用户token和id是否匹配 不匹配抛出异常
        return recommendService.getFlightRecommendList(scheduleMainId);
    }

    @Override
    public String testApi(String s) throws Exception {
        return recommendService.test08(s);
    }

}
