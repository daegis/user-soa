package com.hnair.consumer.user.job;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.alibaba.fastjson.JSONObject;
import com.hnair.consumer.flight.api.IHiFlightApi;
import com.hnair.consumer.flight.model.AirlineCity;
import com.hnair.consumer.flight.vo.RealTimeQuery.RealTimeQueryReq;
import com.hnair.consumer.flight.vo.RealTimeQuery.RealTimeQueryResp;
import com.hnair.consumer.flight.vo.RealTimeQuery.RealTimeQueryRespWeatherInfo;
import com.hnair.consumer.user.service.IFlightRealTimeService;
import com.hnair.consumer.user.vo.FlightSearchRealTimeDecVo;

import lombok.extern.slf4j.Slf4j;

/**
 * 行程关联订单查询
 * @author jy
 *
 */
@Slf4j
public class GetFlightRealTimeInfoJob implements Callable<FlightSearchRealTimeDecVo> {
	
    private IHiFlightApi flightApi;
    RealTimeQueryResp resultVo = null;
    RealTimeQueryReq req;
    IFlightRealTimeService flightRealTimeService;
    String flightDepcode;
    String flightArrcode;


    public GetFlightRealTimeInfoJob(RealTimeQueryReq req,String flightDepcode,String flightArrcode, IHiFlightApi flightApi,IFlightRealTimeService flightRealTimeService) {
        this.req =req;
        this.flightDepcode =flightDepcode;
        this.flightArrcode = flightArrcode;
        this.flightApi = flightApi;
        this.flightRealTimeService =flightRealTimeService;
    }

    @Override
    public FlightSearchRealTimeDecVo call() throws Exception {
    	try {
    		if (!this.checkParam(req.getDate(), req.getVNum())) {
    			log.info("航班管家查询请求信息为空");
    			return null;
    		}
            log.info("航班管家查询请求信息:" + JSONObject.toJSONString(req));
            resultVo = flightApi.getRealTimeQueryResp(req);
            log.info("航班管家查询返回信息:{}", JSONObject.toJSONString(resultVo));
        } catch (Exception e) {
            log.error("", e);
            log.error("", ExceptionUtils.getFullStackTrace(e));
        }
        if (resultVo == null || CollectionUtils.isEmpty(resultVo.getFlightInfo())) {
            return null;
        }
        List<FlightSearchRealTimeDecVo> resultList = new ArrayList<>();
        flightRealTimeService.buildFlightSearchDecVos(resultList, resultVo.getFlightInfo());
        if (CollectionUtils.isEmpty(resultList)) {
            return null;
        }
        FlightSearchRealTimeDecVo result = this.getResultFlightInfo(resultList,flightDepcode,flightArrcode);
        if (result != null && CollectionUtils.isNotEmpty(resultVo.getWeathers())){
            List<RealTimeQueryRespWeatherInfo> weatherList = new ArrayList<>();
            resultVo.getWeathers().stream().filter(weathers -> weathers!= null && CollectionUtils.isNotEmpty(weathers.getWeather())).forEach(weathers -> weatherList.addAll(weathers.getWeather()));
            this.buildWeatherInfo(result,weatherList);
        }
        
        return result;
    }
    private FlightSearchRealTimeDecVo getResultFlightInfo(List<FlightSearchRealTimeDecVo> resultList,String flightDepcode,String flightArrcode) {
        if (CollectionUtils.isEmpty(resultList)) {
            return null;
        }
        FlightSearchRealTimeDecVo result = null;
        if (resultList.size() > 1) {
            log.info("航班动态按航班号查询时返回值数量:{}",resultList.size());
            if (StringUtils.isBlank(flightDepcode) && StringUtils.isBlank(flightArrcode)) {
                List<FlightSearchRealTimeDecVo> filterList= resultList.stream().filter(flightInfo -> "1".equals(flightInfo.getStops())).collect(Collectors.toList());
                if (CollectionUtils.isNotEmpty(filterList)) {
                    resultList = filterList;
                }
            }
            if (StringUtils.isNotBlank(flightDepcode) && StringUtils.isNotBlank(flightArrcode)) {
                log.info("航班动态按航班号查询,匹配出发机场三字码:{},到达机场三字码:{}",flightDepcode,flightArrcode);
                resultList = resultList.stream().filter(flightInfo -> flightDepcode.equals(flightInfo.getFlightDepcode()) && flightArrcode.equals(flightInfo.getFlightArrcode())).collect(Collectors.toList());
                log.info("航班动态按航班号查询匹配出发机场,到达机场,筛选后数量:{}",resultList.size());
            }
        }
        if (CollectionUtils.isNotEmpty(resultList)) {
            resultList.stream().forEach(realTimeDecVo ->{
                //20171218前端需要值机柜台显示短一些, 产品决定多个值机柜台时用'-'链接,无论中间是否连续
                //3个及以上就要'-'
                if (StringUtils.isNotBlank(realTimeDecVo.getChkdesk()) && realTimeDecVo.getChkdesk().split(",").length > 2) {
                    String[] splitChkdesk = realTimeDecVo.getChkdesk().split(",");
                    realTimeDecVo.setChkdesk(splitChkdesk[0]+"-"+splitChkdesk[splitChkdesk.length-1]);
                }
                if (StringUtils.isNotBlank(realTimeDecVo.getStopCode())) {
                    Map<String, AirlineCity> airlineCityMap = flightApi.queryCityByCodes(Arrays.asList(realTimeDecVo.getStopCode().equals("XIY") ? "SIA":realTimeDecVo.getStopCode()));
                    if (MapUtils.isNotEmpty(airlineCityMap)) {
                        realTimeDecVo.setStopCity(airlineCityMap.get(realTimeDecVo.getStopCode().equals("XIY") ? "SIA":realTimeDecVo.getStopCode()).getCityName());
                    }
                }
            });
            result = resultList.get(0);
        }
        return result;
    }
    
    private void buildWeatherInfo(FlightSearchRealTimeDecVo result, List<RealTimeQueryRespWeatherInfo> weathers) {
        weathers.stream().filter(weather -> weather.getCityCode().equals(result.getFlightDepcode())).forEach(weather -> {
            result.setCityName(weather.getCityName());
            result.setWind(weather.getWind());
            result.setTemperatureHigh(weather.getTemperatureHigh());
            result.setTemperatureLow(weather.getTemperatureLow());
            result.setWeatherType(weather.getWeatherType());
        });
    }
    private boolean checkParam(String date, String flightNo) {

		if (StringUtils.isBlank(date)) {
			return false;
		}
		if (StringUtils.isBlank(flightNo)) {
			return false;
		}
		return true;
	}
    
}
