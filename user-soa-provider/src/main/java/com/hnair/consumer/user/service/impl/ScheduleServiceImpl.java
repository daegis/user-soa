package com.hnair.consumer.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.flight.api.IHiFlightApi;
import com.hnair.consumer.flight.vo.RealTimeQuery.RealTimeQueryReq;
import com.hnair.consumer.order.api.IRefScheduleOrderApi;
import com.hnair.consumer.user.job.GetFlightRealTimeInfoJob;
import com.hnair.consumer.user.model.ScheduleDetail;
import com.hnair.consumer.user.service.IFlightRealTimeService;
import com.hnair.consumer.user.service.IScheduleService;
import com.hnair.consumer.user.vo.FlightSearchRealTimeDecVo;
import com.hnair.consumer.user.vo.ScheduleRespVo;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author jiangyu
 *
 * 2018年1月16日
 */
@Slf4j
@Service
public class ScheduleServiceImpl implements IScheduleService{
	@Resource(name = "ucenterCommonService")
    private ICommonService scheduleCommonService;
	
	@Resource(name = "refScheduleOrderApi")
    IRefScheduleOrderApi refScheduleOrderApi;
	
	@Autowired
    private IHiFlightApi flightApi;
	@Autowired
	private IFlightRealTimeService flightRealTimeService;
	
	@Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;
	
	public static final String REALTIME_DOMESTIC_USER = "hiapp";
	@Override
	public List<ScheduleRespVo> getScheduleDetail(String scheduleNo,String orderStatusStr){
		List<ScheduleRespVo> list =scheduleCommonService.getListBySqlId(ScheduleDetail.class, "selectScheduleDetailByNowDateAndUserId", "scheduleNo", scheduleNo);
		List<ScheduleRespVo> returnList =new ArrayList<ScheduleRespVo>();
		if(StringUtils.isNotBlank(orderStatusStr)){
			for(ScheduleRespVo sr :list){
				sr.setOrderStatusStr(orderStatusStr);
				returnList.add(sr);
			}	
		}
			
	    return returnList;
		
	}
	@Override
	public List<FlightSearchRealTimeDecVo> getFligtRealTimeInfo(String scheduleNo){
		List<FlightSearchRealTimeDecVo> ls =new ArrayList<FlightSearchRealTimeDecVo>();
		List<ScheduleRespVo> list =scheduleCommonService.getListBySqlId(ScheduleDetail.class, "selectScheduleDetailByNowDateAndUserId", "scheduleNo", scheduleNo);
		for(ScheduleRespVo sr:list){
			RealTimeQueryReq req= new RealTimeQueryReq();
			req.setUser(REALTIME_DOMESTIC_USER);
            req.setDate(sr.getDptDate());
            req.setCmd("flyno");
            req.setVNum(sr.getCode());
            String flightDepcode = sr.getDptAirport();
            String flightArrcode = sr.getArrAirport();
            GetFlightRealTimeInfoJob flightJob = new GetFlightRealTimeInfoJob(req,flightDepcode,flightArrcode, flightApi,flightRealTimeService);
            Future<FlightSearchRealTimeDecVo> flight = taskExecutor.submit(flightJob);
            FlightSearchRealTimeDecVo flightVo =null;
            try {
				flightVo =flight.get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            if(flightVo!=null){
            	ls.add(flightVo);
            }
		}
		
		return ls;
		
	}
	

}
