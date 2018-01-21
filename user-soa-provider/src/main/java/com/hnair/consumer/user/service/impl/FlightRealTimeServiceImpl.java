package com.hnair.consumer.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hnair.consumer.flight.api.IHiFlightApi;
import com.hnair.consumer.flight.model.AirlineCity;
import com.hnair.consumer.flight.model.AirlineCompany;
import com.hnair.consumer.flight.vo.RealTimeQuery.RealTimeQueryRespFlightInfo;
import com.hnair.consumer.user.service.IFlightRealTimeService;
import com.hnair.consumer.user.vo.FlightSearchRealTimeDecVo;

import lombok.extern.slf4j.Slf4j;
/**
 * 
 * @author jiangyu
 *
 * 2018年1月16日
 */
@Slf4j
@Service
public class FlightRealTimeServiceImpl implements IFlightRealTimeService{
	
	@Autowired
    private IHiFlightApi flightApi;
	
	@Override
    public void buildFlightSearchDecVos(List<FlightSearchRealTimeDecVo> resultList, List<RealTimeQueryRespFlightInfo> realTimeFlightInfos) {
        //查询航空公司信息
        List<String> airlineCodes = realTimeFlightInfos.stream().filter(o -> org.apache.commons.lang.StringUtils.isNotBlank(o.getFlightNo())).map(o -> o.getFlightNo().substring(0,2)).collect(Collectors.toList());
        Map<String, AirlineCompany> companyInfo = flightApi.queryCompanyByCodes(airlineCodes);
        List<String> stopCityCodes = new ArrayList<String>();
        realTimeFlightInfos.stream().forEach(flightInfo -> {
            FlightSearchRealTimeDecVo resultVo = new FlightSearchRealTimeDecVo();
            //转换同名属性
            BeanUtils.copyProperties(flightInfo,resultVo);
            //到达城市
            resultVo.setArrCityName(flightInfo.getFlightArr());
            //到达机场
            resultVo.setEndAirportName(flightInfo.getArrAirport());
            //到达航站楼
            resultVo.setArrTerminal(flightInfo.getFlightTerminal());
            //航班号
            resultVo.setCode(flightInfo.getFlightNo());
            //出发城市
            resultVo.setDepartCityName(flightInfo.getFlightDep());
            //出发机场
            resultVo.setStartAirportName(flightInfo.getDepAirport());
            //出发航站楼
            resultVo.setDepTerminal(flightInfo.getFlightHTerminal());
            //飞行时长
            resultVo.setFlightTime(flightInfo.getFlyTime());
            //是否经停 无经停为0;有经停为1
            resultVo.setStops(flightInfo.getStop());
            //计划起飞日期
            resultVo.setFlightDeptimePlan(org.apache.commons.lang.StringUtils.isNotBlank(flightInfo.getFlightDeptimePlan())? flightInfo.getFlightDeptimePlan() : "");
            //计划到达时间
            resultVo.setFlightArrtimePlan(org.apache.commons.lang.StringUtils.isNotBlank(flightInfo.getFlightArrtimePlan())? flightInfo.getFlightArrtimePlan() : "");
            //预计起飞时间
            resultVo.setFlightDeptimeReady(org.apache.commons.lang.StringUtils.isNotBlank(flightInfo.getFlightDeptimeReady())? flightInfo.getFlightDeptimeReady() : flightInfo.getFlightDeptimePlan());
            //预计到达时间
            resultVo.setFlightArrtimeReady(org.apache.commons.lang.StringUtils.isNotBlank(flightInfo.getFlightArrtimeReady())? flightInfo.getFlightArrtimeReady() : flightInfo.getFlightArrtimePlan());
            //实际起飞时间
            resultVo.setFlightDeptime(org.apache.commons.lang.StringUtils.isNotBlank(flightInfo.getFlightDeptime())? flightInfo.getFlightDeptime() : flightInfo.getFlightDeptimePlan());
            //实际到达时间
            resultVo.setFlightArrtime(org.apache.commons.lang.StringUtils.isNotBlank(flightInfo.getFlightArrtime())?flightInfo.getFlightArrtime():flightInfo.getFlightArrtimePlan());
            //历史出发准点率
            resultVo.setOnTimeRate(org.apache.commons.lang.StringUtils.isNotBlank(flightInfo.getProbability())?flightInfo.getProbability()+"%":"");
            //机型
            resultVo.setPlaneType(this.formatFlightModel(flightInfo.getFlightModel(),flightInfo.getPlaneSize()));
            if (org.apache.commons.lang.StringUtils.isNotBlank(flightInfo.getStopCode())) {
                stopCityCodes.add(flightInfo.getStopCode());
            }
            //航空logo
            resultVo.setAirlineLogo(companyInfo.get(flightInfo.getFlightNo().substring(0,2)) == null ? "" : companyInfo.get(flightInfo.getFlightNo().substring(0,2)).getAirlineLogo());
            //航空公司名
            resultVo.setAirlineName(companyInfo.get(flightInfo.getFlightNo().substring(0,2)) == null ? "" : companyInfo.get(flightInfo.getFlightNo().substring(0,2)).getAirlineName());
            resultList.add(resultVo);
        });
        //==========查询城市名称================
        Map<String, AirlineCity> citys = flightApi.queryCityByCodes(stopCityCodes);
        if (MapUtils.isNotEmpty(citys)) {
            resultList.stream().filter(resultVo -> org.apache.commons.lang.StringUtils.isNotBlank(resultVo.getStopCode())).forEach(resultVo -> resultVo.setStopCity(citys.get(resultVo.getStopCode()).getCityName()));
        }
    }
	private String formatFlightModel(String flightModel, String planeSize) {
        if (StringUtils.isBlank(flightModel)) {
            return "";
        }
        if (StringUtils.isBlank(planeSize)) {
            return flightModel;
        }
        if (flightModel.contains("(")) {
            flightModel = flightModel.substring(0,flightModel.indexOf("("));
        }
        return flightModel+"("+planeSize+")";
    }

}
