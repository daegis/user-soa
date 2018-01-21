package com.hnair.consumer.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hnair.consumer.content.enums.DestinationConfigType;
import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.flight.api.IHiFlightApi;
import com.hnair.consumer.flight.model.AirlineCity;
import com.hnair.consumer.order.vo.RefScheduleOrderVo;
import com.hnair.consumer.product.utils.TripProductUtils;
import com.hnair.consumer.search.api.IHotelSearchApi;
import com.hnair.consumer.search.api.IProductSearchApi;
import com.hnair.consumer.search.vo.*;
import com.hnair.consumer.search.vo.response.SearchResponseVo;
import com.hnair.consumer.user.model.ScheduleDetail;
import com.hnair.consumer.user.model.ScheduleMain;
import com.hnair.consumer.user.model.ScheduleMtsConfig;
import com.hnair.consumer.user.model.ScheduleMtsRecommend;
import com.hnair.consumer.user.service.IRecommendService;
import com.hnair.consumer.user.utils.HttpUtils;
import com.hnair.consumer.user.utils.SearchFlightUtils;
import com.hnair.consumer.user.vo.ScheduleVo;
import com.hnair.consumer.user.vo.recommendation.BaseRecommendVo;
import com.hnair.consumer.user.vo.recommendation.MtsRecommendationVo;
import com.hnair.consumer.user.vo.recommendation.flight.FlightRecommendationVo;
import com.hnair.consumer.user.vo.recommendation.flight.domestic.RFlightDetailShowVo;
import com.hnair.consumer.user.vo.recommendation.flight.international.InternationalFlightSearchVo;
import com.hnair.consumer.utils.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Console;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIAN_Yingda
 * @date 2018/1/11 17:22
 */
@Slf4j
@Service
public class RecommendServiceImpl implements IRecommendService {

    @Resource(name = "ucenterCommonService")
    private ICommonService userCommonService;

    @Autowired
    private IHiFlightApi hiFlightApi;

    @Autowired
    private IHotelSearchApi hotelSearchApi;

    @Autowired
    private IProductSearchApi prodSearchApi;

    @Autowired
    @Qualifier("masterRedisTemplate")
    private RedisTemplate masterRedisTemplate;

    @Autowired
    @Qualifier("slaveRedisTemplate")
    private RedisTemplate slaveRedisTemplate;

    @Override
    public FlightRecommendationVo getFlightRecommendList(String scheduleMainId) throws Exception {

        FlightRecommendationVo result = new FlightRecommendationVo();
        result.setName("机票推荐");
        result.setCategory(2);
        result.setWeight(100);

        // 根据主行程id查询出主行程
        ScheduleMain scheduleMain = userCommonService.get(ScheduleMain.class, "scheduleNo", scheduleMainId);
        if (scheduleMain == null) {
            throw new Exception("未查到主行程：" + scheduleMainId);
        }
        log.info(JSON.toJSONString(scheduleMain, SerializerFeature.WriteMapNullValue));
        if (!scheduleMain.getScheduleType().equals("1")) {
            // 如果不是机票订单不进行推荐
            result.setIsShown(false);
            return result;
        }
        // 如果主行程不是返程机票
        if (scheduleMain.getTripType().intValue() == 1) {
            // 本行程是往返行程，不进行推荐
            result.setIsShown(false);
            return result;
        }
        // 用户没有本次行程关联的返程机票
        if (1 == 2) {
            result.setIsShown(false);
            return result;
        }
        // 获取该主行程号
        String scheduleNo = scheduleMain.getScheduleNo();
        List<ScheduleDetail> detailList = userCommonService.getList(ScheduleDetail.class, "scheduleNo", scheduleNo);
        Short airLineType = scheduleMain.getAirLineType();
        String departCityName = scheduleMain.getDepartCityName(); // 出发城市为推荐返程机票到达城市
        String arrCityName = scheduleMain.getArrCityName(); // 到达城市为推荐返程机票起始城市
        Date endDate = scheduleMain.getEndDate(); // 去程航班到达时间
        // 去程航班到达时间
        LocalDateTime endLocalDatetime = LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault());
        // 预计的返程日期 是去程航班到达时间加一天
        LocalDateTime pendingReturnDate = endLocalDatetime.plusDays(1L);
        LocalDate pendingReturnDay = pendingReturnDate.toLocalDate();
        String pendingReturnTime = pendingReturnDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Map<String, Object> params = new LinkedHashMap<>();
        params.put("searchDeparture", arrCityName);
        params.put("searchDestination", departCityName);
        params.put("searchDate", pendingReturnTime);
        params.put("adultCount", 1);
        // 查询城市代码
        List<String> cityNameList = new LinkedList<>();
        cityNameList.add(arrCityName);
        cityNameList.add(departCityName);
        Map<String, AirlineCity> cityNameMap = hiFlightApi.queryCityCodeByCityName(cityNameList);
        AirlineCity startAC = cityNameMap.get(arrCityName);
        AirlineCity destinationAC = cityNameMap.get(departCityName);
        if (startAC == null || destinationAC == null) {
            log.info("未查到完整的城市信息，没有返回城市代码。请求参数：start:{},end:{}", arrCityName, departCityName);
            return null;
        }
        String startCityCode = startAC.getCityCode();
        String destinationCityCode = destinationAC.getCityCode();
        params.put("searchDeptCode", startCityCode);
        params.put("searchDestCode", destinationCityCode);
        result.setSearchParams(params);
        if (airLineType.intValue() == 0) {
            // 国内机票
            result.setAirlineType(0);
            List<RFlightDetailShowVo> flightList = getQualifiedDomesticFlightRecommendations(startCityCode, destinationCityCode, pendingReturnTime);
            if (flightList != null) {
                result.setFlightList(flightList);
                for (RFlightDetailShowVo vo : flightList) {
                    log.info("国内机票" + JSON.toJSONString(vo));
                }
            } else {
                log.info("未查询到有关的国内航班，检查参数的正确性");
            }
        } else if (airLineType.intValue() == 1) {
            // 国际机票
            result.setAirlineType(1);
            List<InternationalFlightSearchVo> internationalFlightList = getQualifiedInternationalFlightRecommendations(startCityCode, destinationCityCode, pendingReturnTime);
            if (internationalFlightList != null) {
                result.setFlightList(internationalFlightList);
                for (InternationalFlightSearchVo vo : internationalFlightList) {
                    log.info("国际机票" + JSON.toJSONString(vo));
                }
            } else {
                log.info("未查询到有关的国际航班，检查参数的正确性");
            }
        }

        // 检查结果参数
        List listResult = result.getFlightList();
        result.setIsShown(true);
        if (listResult != null && listResult.size() != 0) {
            result.setCount(listResult.size());
        } else {
            result.setCount(0);
            result.setIsShown(false);
            result.setWhyFalse("查询到合适的机票数量为0");
        }
        return result;
    }

    private List<RFlightDetailShowVo> getQualifiedDomesticFlightRecommendations(String sc, String dc, String date) throws Exception {
        String flightResultJson = SearchFlightUtils.getFlightList(sc, dc, date, "1");
        log.info("查询到的国内机票信息：{}", flightResultJson);
        JSONObject result = JSON.parseObject(flightResultJson);
        JSONObject status = result.getJSONObject("status");
        int code = (int) status.get("code");
        List<RFlightDetailShowVo> showVoList = new LinkedList<>();
        if (code == 0) {
            // 正常返回值
            JSONObject data = result.getJSONObject("data");
            String flightMap = data.getString("flightMap");
            List<RFlightDetailShowVo> flightDetailShowVos = JSON.parseArray(flightMap, RFlightDetailShowVo.class);
            log.info("查询到相关航班个数：{}", flightDetailShowVos.size());
            // 按照筛选条件进行排序之后取前三个进行
            if (flightDetailShowVos != null) {
                Iterator<RFlightDetailShowVo> iterator = flightDetailShowVos.iterator();
                int i = 3;
                while (iterator.hasNext()) {
                    if (i == 0) {
                        break;
                    }
                    showVoList.add(iterator.next());
                    i--;
                }
            }
        } else {
            log.info("查询国内机票发生了错误，错误码：" + code);
        }
        return showVoList;
    }

    private List<InternationalFlightSearchVo> getQualifiedInternationalFlightRecommendations(String sc, String dc, String date) throws Exception {
        String flightResultJson = SearchFlightUtils.getInternationalFlightList(sc, dc, date, "1", "1", "0", "0");
        log.info("查询到的国际机票信息：{}", flightResultJson);
        JSONObject resultJsonObj = JSON.parseObject(flightResultJson);
        JSONObject status = resultJsonObj.getJSONObject("status");
        int code = (int) status.get("code");
        List<InternationalFlightSearchVo> resultList = new LinkedList<>();
        if (code == 0) {
            JSONObject data = resultJsonObj.getJSONObject("data");
            String searchResultJsonString = data.getString("searchResult");
            List<InternationalFlightSearchVo> internationalFlightSearchVos = JSON.parseArray(searchResultJsonString, InternationalFlightSearchVo.class);
            if (internationalFlightSearchVos != null) {
                log.info("查询到国际机票的数量：{}", internationalFlightSearchVos.size());
            }
            if (internationalFlightSearchVos != null) {
                Iterator<InternationalFlightSearchVo> iterator = internationalFlightSearchVos.iterator();
                int i = 3;
                while (iterator.hasNext()) {
                    if (i == 0) {
                        break;
                    }
                    InternationalFlightSearchVo vo = iterator.next();
                    resultList.add(vo);
                    i--;
                }
            }
        } else {
            log.info("查询国际机票发生了错误，错误码：" + code);
        }
        return resultList;
    }

    private List<BaseRecommendVo> getCommonRecommendList(String scheduleNo, List<RefScheduleOrderVo> ls) throws Exception {
        // 根据主行程id查询出主行程
        ScheduleMain scheduleMain = userCommonService.get(ScheduleMain.class, "scheduleNo", scheduleNo);
        if (scheduleMain == null) {
            throw new Exception("未查到主行程：" + scheduleNo);
        }
        // 查询该主行程的详细信息
        List<ScheduleDetail> detailList = userCommonService.getList(ScheduleDetail.class, "scheduleNo", scheduleNo);
        Date beginDate = scheduleMain.getBeginDate();
        if (beginDate == null) {
            throw new Exception("主行程时间为空：" + scheduleNo);
        }
        LocalDateTime begin = LocalDateTime.ofInstant(beginDate.toInstant(), ZoneId.systemDefault());
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now, begin);
        long hourInterval = duration.toHours();
        List<BaseRecommendVo> resultList = userCommonService.getListBySqlId(ScheduleMain.class, "selectRecommendOrder", "inthour", hourInterval);
        log.info("查询到的推荐顺序排序:" + JSON.toJSONString(resultList));
        for (BaseRecommendVo baseRecommendVo : resultList) {
            if (baseRecommendVo.getName().equals("签证服务")) {
                List<FreeProdSearchResultDetailVo> visaList = null;
                try {
                    visaList = getQualifiedVisa(scheduleMain, detailList, ls);
                } catch (Exception e) {
                    e.printStackTrace();
                    baseRecommendVo.setWhyFalse(e.getMessage());
                }
                if (visaList != null && visaList.size() > 0) {
                    baseRecommendVo.setVisaList(visaList);
                    baseRecommendVo.setCount(visaList.size());
                    baseRecommendVo.setIsShown(true);
                } else {
                    baseRecommendVo.setIsShown(false);
                }
            } else if (baseRecommendVo.getName().equals("机票预订")) {
                baseRecommendVo.setIsShown(true);
            } else if (baseRecommendVo.getName().equals("酒店预订")) {
                List<HotelSearchResultDetailVo> qualifiedHotel = null;
                try {
                    qualifiedHotel = getQualifiedHotel(scheduleMain, detailList, ls, baseRecommendVo);
                } catch (Exception e) {
                    e.printStackTrace();
                    baseRecommendVo.setWhyFalse(e.getMessage());
                }
                if (qualifiedHotel != null && qualifiedHotel.size() != 0) {
                    baseRecommendVo.setHotelList(qualifiedHotel);
                    baseRecommendVo.setCount(qualifiedHotel.size());
                    baseRecommendVo.setIsShown(true);
                } else {
                    baseRecommendVo.setIsShown(false);
                }
            } else if (baseRecommendVo.getName().equals("度假产品")) {
                // do nothing here
                baseRecommendVo.setIsShown(false);
            } else if (baseRecommendVo.getName().equals("MTS服务")) {
                List<MtsRecommendationVo> qualifiedMtsServiceList = null;
                try {
                    qualifiedMtsServiceList = getQualifiedMtsList(scheduleMain, detailList, ls, hourInterval);
                } catch (Exception e) {
                    e.printStackTrace();
                    baseRecommendVo.setWhyFalse(e.getMessage());
                }
                if (qualifiedMtsServiceList != null && qualifiedMtsServiceList.size() != 0) {
                    baseRecommendVo.setMtsList(qualifiedMtsServiceList);
                    baseRecommendVo.setCount(qualifiedMtsServiceList.size());
                    baseRecommendVo.setIsShown(true);
                } else {
                    baseRecommendVo.setIsShown(false);
                }
                // mts服务额外参数 更多地址 使用了缓存
                String rec_mts_more = (String) slaveRedisTemplate.opsForValue().get("rec_mts_more");
                if (stringCheckNotNull(rec_mts_more)) {
                    baseRecommendVo.setShowMore(rec_mts_more);
                } else {
                    List<ScheduleMtsRecommend> recList = userCommonService.getList(ScheduleMtsRecommend.class, "id", 9);
                    ScheduleMtsRecommend moreInfo = recList.listIterator().next();
                    String moreUrl = moreInfo.getUrl();
                    baseRecommendVo.setShowMore(moreUrl);
                    masterRedisTemplate.opsForValue().set("rec_mts_more", moreUrl, 5, TimeUnit.MINUTES);
                }
            }

        }
        return resultList;
    }

    private List<MtsRecommendationVo> getQualifiedMtsList(ScheduleMain scheduleMain, List<ScheduleDetail> detailList, List<RefScheduleOrderVo> ls, long hourInterval) throws Exception {
        ScheduleDetail detail = getDetail(scheduleMain, detailList);
        if (detail == null) {
            throw new Exception("行程未查到detail：" + scheduleMain.getScheduleNo());
        }
        List<MtsRecommendationVo> mtsInfoList = userCommonService.getListBySqlId(ScheduleMain.class, "selectMtsInfo", "hourInterval", hourInterval);
        log.info("查询到Mts服务个数：" + mtsInfoList.size());
        log.info("查询到Mts服务：" + JSON.toJSONString(mtsInfoList));
        List<MtsRecommendationVo> result = new LinkedList<>();
        String departCityName = detail.getDepartCityName(); // 出发城市名称
        String carrier = detail.getCarrier(); // 航司名
        String dptAirportName = detail.getDptAirportName(); // 出发机场名
        String depTerminal = detail.getDepTerminal(); // 出发航站楼
        String arrAirportName = detail.getArrAirportName(); // 到达城市名
        String arrTerminal = detail.getArrTerminal(); // 到达航站楼
        log.info("detail:" + JSON.toJSONString(detail, SerializerFeature.WriteMapNullValue));
        // 空中餐食预订 rid=2 dinner-----------------------------------------------------------------
        // 查询餐食预订配置
        List<ScheduleMtsConfig> dinnerConfig = null;
        String rec_mts_dinner = (String) slaveRedisTemplate.opsForValue().get("rec_mts_dinner");
        if (stringCheckNotNull(rec_mts_dinner)) {
            dinnerConfig = JSON.parseArray(rec_mts_dinner, ScheduleMtsConfig.class);
            log.info("通过redis查询到了dinner配置");
        } else {
            dinnerConfig = userCommonService.getList(ScheduleMtsConfig.class, "rid", 2);
            masterRedisTemplate.opsForValue().set("rec_mts_dinner", JSON.toJSONString(dinnerConfig), 5, TimeUnit.MINUTES);
            log.info("通过数据库查询到了dinner配置,存入redis");
        }
        List<String> carriers = dinnerConfig.stream().map(ScheduleMtsConfig::getCarrierName).collect(Collectors.toList());
        if (stringCheckNotNull(carrier) && carriers.contains(carrier)) {
            // 满足餐食预订条件 结果中添加餐食预订
            MtsRecommendationVo dinner = getMtsVo(mtsInfoList, 2);
            result.add(dinner);
        }
        // 登机引导服务 rid=7----------------------------------------------------------------------------
        String rec_mts_aboard = (String) slaveRedisTemplate.opsForValue().get("rec_mts_aboard");
        List<ScheduleMtsConfig> aboardConfig = null;
        if (stringCheckNotNull(rec_mts_aboard)) {
            aboardConfig = JSON.parseArray(rec_mts_aboard, ScheduleMtsConfig.class);
            log.info("通过redis查询到了aboard配置");
        } else {
            aboardConfig = userCommonService.getList(ScheduleMtsConfig.class, "rid", 7);
            masterRedisTemplate.opsForValue().set("rec_mts_aboard", JSON.toJSONString(aboardConfig), 5, TimeUnit.MINUTES);
            log.info("通过数据库查询到了aboard配置,存入redis");
        }
        for (ScheduleMtsConfig config : aboardConfig) {
            String deptCityName = config.getDeptCityName();
            // 登机引导服务配置表中配置的是城市名 如果出发城市在配置列表中就进行推荐
            if (deptCityName.equals(departCityName)) {
                MtsRecommendationVo getOn = getMtsVo(mtsInfoList, 7);
                result.add(getOn);
                break;
            }
        }
        // 接机引导服务 rid=6 ---------------------------------------------------------------------------------
        String rec_mts_offboard = (String) slaveRedisTemplate.opsForValue().get("rec_mts_offboard");
        List<ScheduleMtsConfig> offboardConfig = null;
        if (stringCheckNotNull(rec_mts_offboard)) {
            offboardConfig = JSON.parseArray(rec_mts_offboard, ScheduleMtsConfig.class);
            log.info("通过redis查询到了offboard配置");
        } else {
            offboardConfig = userCommonService.getList(ScheduleMtsConfig.class, "rid", 6);
            masterRedisTemplate.opsForValue().set("rec_mts_offboard", JSON.toJSONString(offboardConfig), 5, TimeUnit.MINUTES);
            log.info("通过数据库查询到了offboard配置,存入redis");
        }
        // 本类筛选需要满足两类条件 海航系航班 和 出发机场 先对集合进行分离,分成航空公司集合和机场集合
        ListIterator<ScheduleMtsConfig> listIterator = offboardConfig.listIterator();
        List<ScheduleMtsConfig> offboardAirportConfig = new LinkedList<>();
        while (listIterator.hasNext()) {
            ScheduleMtsConfig next = listIterator.next();
            if (next.getCarrierName() == null) {
                // 航司配置为空 本条为机场配置 添加到机场配置集合中 同时在原集合中移出机场配置
                offboardAirportConfig.add(next);
                listIterator.remove();
            }
        }
        // offboardConfig 航司要求  offboardAirportConfig 机场要求
        outer:
        for (ScheduleMtsConfig carrierLimit : offboardConfig) {
            String qualifiedCarrier = carrierLimit.getCarrierName();
            if (qualifiedCarrier.equals(carrier)) {
                // 航司满足条件啦~
                inner:
                for (ScheduleMtsConfig airportLimit : offboardAirportConfig) {
                    String deptAirportTerminal = airportLimit.getDeptAirportTerminal();
                    String deptAirportName = airportLimit.getDeptAirportName();
                    if (dptAirportName.contains(deptAirportName)) {
                        // 机场名字满足了条件
                        if (stringCheckNotNull(deptAirportTerminal) && !deptAirportTerminal.equals(depTerminal)) {
                            // 航站楼需求字段不为空,必须满足航站楼筛选才可以添加本服务
                            // 如果航站楼不匹配 则继续循环 如果匹配不会进入本if 执行添加业务之后跳出循环
                            continue;
                        }
                        MtsRecommendationVo fetch = getMtsVo(mtsInfoList, 6);
                        result.add(fetch);
                        break outer;
                    }
                }
            }
        }
        // 行李速递 rid=3 ------------------------------------------------------------------------------------
        String rec_mts_luggage = (String) slaveRedisTemplate.opsForValue().get("rec_mts_luggage");
        List<ScheduleMtsConfig> luggageConfig = null;
        if (stringCheckNotNull(rec_mts_luggage)) {
            luggageConfig = JSON.parseArray(rec_mts_luggage, ScheduleMtsConfig.class);
            log.info("通过redis查询到了luggage配置");
        } else {
            luggageConfig = userCommonService.getList(ScheduleMtsConfig.class, "rid", 3);
            masterRedisTemplate.opsForValue().set("rec_mts_luggage", JSON.toJSONString(luggageConfig), 5, TimeUnit.MINUTES);
            log.info("通过数据库查询到了luggage配置,存入redis");
        }
        for (ScheduleMtsConfig config : luggageConfig) {
            String deptAirportName = config.getDeptAirportName();
            String deptAirportTerminal = config.getDeptAirportTerminal();
            if (stringCheckNotNull(deptAirportName)) {
                // 出发机场名字不为空 这一条配置的是出发机场配置信息
                if (dptAirportName.contains(deptAirportName)) {
                    // 出发机场名字满足了条件
                    if (stringCheckNotNull(deptAirportTerminal) && !deptAirportTerminal.equals(depTerminal)) {
                        // 在配置航站楼信息不为空的条件下出发机场航站楼不满足条件
                        continue;
                    }
                    // 出发机场满足推荐条件 进行推荐
                    MtsRecommendationVo luggage = getMtsVo(mtsInfoList, 3);
                    result.add(luggage);
                    break;
                }
            } else {
                // 出发信息为空 获取到达的机场信息
                String arrAirName = config.getArrAirportName();
                String arrAirportTerminal = config.getArrAirportTerminal();
                if (arrAirportName.contains(arrAirName)) {
                    // 到达机场名字满足了条件
                    if (stringCheckNotNull(arrAirportTerminal) && !arrTerminal.equals(arrAirportTerminal)) {
                        // 在配置航站楼信息不为空的条件下到达机场航站楼不满足条件
                        continue;
                    }
                    // 到达机场满足推荐条件 进行推荐
                    MtsRecommendationVo luggage = getMtsVo(mtsInfoList, 3);
                    result.add(luggage);
                    break;
                }
            }
        }
        // 接送机rid=1----------------------------------------------------------------------------------------
        String rec_mts_drive = (String) slaveRedisTemplate.opsForValue().get("rec_mts_drive");
        List<ScheduleMtsConfig> driveConfig = null;
        if (stringCheckNotNull(rec_mts_drive)) {
            driveConfig = JSON.parseArray(rec_mts_drive, ScheduleMtsConfig.class);
            log.info("通过redis查询到了drive配置");
        } else {
            driveConfig = userCommonService.getList(ScheduleMtsConfig.class, "rid", 1);
            masterRedisTemplate.opsForValue().set("rec_mts_drive", JSON.toJSONString(driveConfig), 5, TimeUnit.MINUTES);
            log.info("通过数据库查询到了drive配置,存入redis");
        }
        if (driveConfig == null || driveConfig.size() == 0) {
            // 如果配置为空 无条件进行推荐
            MtsRecommendationVo drive = getMtsVo(mtsInfoList, 1);
            result.add(drive);
        } else {
            // 待添加未来的筛选条件
        }
        // 贵宾厅-------------------------------------------------------------------------------------------
        String rec_mts_vip = (String) slaveRedisTemplate.opsForValue().get("rec_mts_vip");
        List<ScheduleMtsConfig> vipConfig = null;
        if (stringCheckNotNull(rec_mts_vip)) {
            vipConfig = JSON.parseArray(rec_mts_vip, ScheduleMtsConfig.class);
            log.info("通过redis查询到了vip配置");
        } else {
            vipConfig = userCommonService.getList(ScheduleMtsConfig.class, "rid", 4);
            masterRedisTemplate.opsForValue().set("rec_mts_vip", JSON.toJSONString(vipConfig), 5, TimeUnit.MINUTES);
            log.info("通过数据库查询到了vip配置,存入redis");
        }
        if (vipConfig == null || vipConfig.size() == 0) {
            // 如果配置为空 无条件进行推荐
            MtsRecommendationVo vip = getMtsVo(mtsInfoList, 4);
            result.add(vip);
        } else {
            // 待添加未来的筛选条件
        }
        // 快速安检通道rid=8---------------------------------------------------------------------------------------
        String rec_mts_fastcheck = (String) slaveRedisTemplate.opsForValue().get("rec_mts_fastcheck");
        List<ScheduleMtsConfig> checkConfig = null;
        if (stringCheckNotNull(rec_mts_fastcheck)) {
            checkConfig = JSON.parseArray(rec_mts_fastcheck, ScheduleMtsConfig.class);
            log.info("通过redis查询到了fastcheck配置");
        } else {
            checkConfig = userCommonService.getList(ScheduleMtsConfig.class, "rid", 8);
            masterRedisTemplate.opsForValue().set("rec_mts_fastcheck", JSON.toJSONString(checkConfig), 5, TimeUnit.MINUTES);
            log.info("通过数据库查询到了fastcheck配置,存入redis");
        }
        for (ScheduleMtsConfig config : checkConfig) {
            String deptAirportName = config.getDeptAirportName();
            if (dptAirportName.contains(deptAirportName)) {
                // 出发机场匹配 推荐快速安检通道
                MtsRecommendationVo fast = getMtsVo(mtsInfoList, 8);
                result.add(fast);
                break;
            }
        }
        // 海外出行保障rid=5---------------------------------------------------------------------------------------
        String rec_mts_abroad = (String) slaveRedisTemplate.opsForValue().get("rec_mts_abroad");
        List<ScheduleMtsConfig> abroadConfig = null;
        if (stringCheckNotNull(rec_mts_abroad)) {
            abroadConfig = JSON.parseArray(rec_mts_abroad, ScheduleMtsConfig.class);
            log.info("通过redis查询到了abroad配置");
        } else {
            abroadConfig = userCommonService.getList(ScheduleMtsConfig.class, "rid", 5);
            masterRedisTemplate.opsForValue().set("rec_mts_abroad", JSON.toJSONString(abroadConfig), 5, TimeUnit.MINUTES);
            log.info("通过数据库查询到了abroad配置,存入redis");
        }
        log.info("海外:" + JSON.toJSONString(abroadConfig));
        String destCountry = scheduleMain.getDestCountry();
        for (ScheduleMtsConfig config : abroadConfig) {
            if (config.getArrCountry().equals(destCountry)) {
                // 到达国家复合条件 推荐海外出行保障
                MtsRecommendationVo fast = getMtsVo(mtsInfoList, 5);
                result.add(fast);
                break;
            }
        }
        // 根据出发时间对列表进行排序-------------------------------------------------------------------------------
        log.info("排序依据:距离出发时间:小时:" + hourInterval);
        result.removeIf(o -> o == null);
        result.sort(new Comparator<MtsRecommendationVo>() {
            @Override
            public int compare(MtsRecommendationVo o1, MtsRecommendationVo o2) {
                return -1 * (o1.getWeight() - o2.getWeight());
            }
        });
        int count = 3;
        List<MtsRecommendationVo> top3 = new LinkedList<>();
        for (MtsRecommendationVo vo : result) {
            if (count == 0) {
                break;
            }
            top3.add(vo);
            count--;
        }
        result.clear();
        return top3;
    }

    private MtsRecommendationVo getMtsVo(List<MtsRecommendationVo> mtsInfoList, int id) {
        for (MtsRecommendationVo vo : mtsInfoList) {
            if (vo.getId() == id) {
                return vo;
            }
        }
        return null;
    }

    private List<FreeProdSearchResultDetailVo> getQualifiedVisa(ScheduleMain scheduleMain, List<ScheduleDetail> detailList, List<RefScheduleOrderVo> ls) {
        if (scheduleMain.getAirLineType().intValue() == 0) {
            // 国内航班 不进行推荐
            return null;
        }
        if (scheduleMain.getGoBack().intValue() == 1) {
            // 返程机票 不进行推荐
            return null;
        }
        if (1 == 2) {
            // 关联行程内有相关签证订单 不进行推荐
            return null;
        }
        // 获取目的地国家
        String destCountry = scheduleMain.getDestCountry();
        log.info("签证推荐目的地国家:" + destCountry);
        if (!StringUtils.isNotBlank(destCountry)) {
            // 目的地国家为空 不推荐
            return null;
        }
        if (destCountry.contains("中国")
                || destCountry.contains("香港")
                || destCountry.contains("澳门")
                || destCountry.contains("台湾")) {
            // 目的地国家为中国 不推荐
            return null;
        }
        // 目的地为其他国家 进行签证推荐
        List<FreeProdSearchResultDetailVo> resultList = new LinkedList<>();
        FreeProdSearchRequestVo requestVo = new FreeProdSearchRequestVo();
        requestVo.setStart(0);
        requestVo.setStart(9);
        List<String> classify = new LinkedList<>();
        classify.add("LY-DJ-QZ");
        requestVo.setClassifyPathes(classify);
        requestVo.setSearchText(destCountry);
        requestVo.setSortField("recommend");
        log.info("签证信息查询vo:" + JSON.toJSONString(requestVo));
        SearchResponseVo<FreeProdSearchResultDetailVo> responseVo = prodSearchApi.freeProdSearch(requestVo);
        if (responseVo != null) {
            List<FreeProdSearchResultDetailVo> details = responseVo.getDetails();
            if (details != null) {
                int i = 3;
                for (FreeProdSearchResultDetailVo vo : details) {
                    if (i == 0) {
                        break;
                    }
                    resultList.add(vo);
                    i--;
                }
            }
        }
        return resultList;
    }

    // 获取推荐的酒店
    private List<HotelSearchResultDetailVo> getQualifiedHotel(ScheduleMain scheduleMain, List<ScheduleDetail> detailList, List<RefScheduleOrderVo> refList, BaseRecommendVo baseRecommendVo) {
        List<HotelSearchResultDetailVo> list = new LinkedList<>();
        ScheduleDetail detail = getDetail(scheduleMain, detailList);
        String cabinDesc = detail.getCabinDesc();
        List<Long> searchStarLevel = new LinkedList<>();
        // 判断逻辑：当前主订单不是返程机票 并且 用户没有本次行程的关联酒店订单
        if (scheduleMain.getScheduleType().equals("1") && scheduleMain.getGoBack().intValue() == 1) {
            // 返程机票 不进行推荐
            return list;
        }
        for (RefScheduleOrderVo ref : refList) {
            if (ref.getOrderType().equals("2")) {
                // 这个是一个酒店订单
                log.info("待判断的酒店订单:" + JSON.toJSONString(ref));
            }
        }
        if (cabinDesc != null && (cabinDesc.equals("头等舱") || cabinDesc.equals("商务舱"))) {
            // 头等舱或商务舱 5
            searchStarLevel.add(5L);
        }
        searchStarLevel.add(4L);
        searchStarLevel.add(3L);
        searchStarLevel.add(2L);
        searchStarLevel.add(1L);
        // 初始化全部查询参数map
        Map<String, Object> paramsMap = new LinkedHashMap<>();
        String arrCityName = scheduleMain.getArrCityName();
        if (scheduleMain.getAirLineType().intValue() == 0) {
            // 国内航线
            Date beginDate = scheduleMain.getBeginDate();
            LocalDateTime beginLocalDateTime = LocalDateTime.ofInstant(beginDate.toInstant(), ZoneId.systemDefault());
            LocalDateTime tomorrow = beginLocalDateTime.plusDays(1L);
            LocalDate localDate = tomorrow.toLocalDate();
            String tomorrowDateStr = localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String beginDateStr = new SimpleDateFormat("yyyy-MM-dd").format(beginDate);
            paramsMap.put("startDate", beginDateStr);
            paramsMap.put("endDate", tomorrowDateStr);
            paramsMap.put("cityName", arrCityName);
            paramsMap.put("isDomestic", true);
            int count = 3;
            for (Long level : searchStarLevel) {
                if (count == 0) {
                    break;
                }
                HotelSearchRequestVo requestVo = new HotelSearchRequestVo();
                List<Long> starList = new LinkedList<>();
                starList.add(level);
                // 设置搜索城市
                requestVo.setCityName(arrCityName);
                // 设置搜索星级 只搜一个星级
                requestVo.setStarRates(starList);
                // 设置国内
                requestVo.setIsChina(1);
                // 设置开始结束时间
                requestVo.setStartDate(beginDateStr);
                requestVo.setEndDate(tomorrowDateStr);
                log.debug("{}星国内酒店查询vo：{}", level, JSON.toJSONString(requestVo));
                HotelSearchResponseVo responseVo = hotelSearchApi.hotelSearch(requestVo);
                log.debug("{}星国内酒店查询结果：{}", level, JSON.toJSONString(responseVo));
                if (responseVo != null) {
                    List<HotelSearchResultDetailVo> details = responseVo.getDetails();
                    if (CollectionUtils.isNotEmpty(details)) {
                        // 查询到了结果
                        HotelSearchResultDetailVo next = details.listIterator().next();
                        list.add(next);
                        count--;
                    }
                }
            }
        } else {
            // 国际航线
            Date endDate = scheduleMain.getEndDate();
            // 判断逻辑：如果到达时间<6am 入住日期=到达日期-1   如果到达时间>6am 入住日期=到达日期
            LocalDateTime arr = LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault());
            LocalDateTime _6am = LocalDateTime.of(arr.getYear(), arr.getMonth(), arr.getDayOfMonth(), 6, 0);
            LocalDate arrDay = arr.toLocalDate();
            LocalDate searchDay = null;
            if (arr.isBefore(_6am)) {
                // 六点之前到的 推荐前一天的
                searchDay = arrDay.minusDays(1L);
            } else {
                // 六点之后到的 推荐当天的
                searchDay = arrDay;
            }
            LocalDate nextDay = searchDay.plusDays(1L);
            String searchDayStr = searchDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String nextDatStr = nextDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            paramsMap.put("startDate", searchDayStr);
            paramsMap.put("endDate", nextDatStr);
            paramsMap.put("cityName", arrCityName);
            paramsMap.put("isDomestic", false);
            int count = 3;
            for (Long level : searchStarLevel) {
                if (count == 0) {
                    break;
                }
                HotelSearchRequestVo requestVo = new HotelSearchRequestVo();
                List<Long> starList = new LinkedList<>();
                starList.add(level);
                // 设置搜索城市
                requestVo.setCityName(arrCityName);
                // 设置搜索星级 只搜一个星级
                requestVo.setStarRates(starList);
                // 设置国内
                requestVo.setIsChina(0);
                // 设置开始结束时间
                requestVo.setStartDate(searchDayStr);
                requestVo.setEndDate(nextDatStr);
                log.info("{}星国际酒店查询vo：{}", level, JSON.toJSONString(requestVo));
                HotelSearchResponseVo responseVo = hotelSearchApi.hotelSearch(requestVo);
                log.info("{}星国际酒店查询结果：{}", level, JSON.toJSONString(responseVo));
                if (responseVo != null) {
                    List<HotelSearchResultDetailVo> details = responseVo.getDetails();
                    if (CollectionUtils.isNotEmpty(details)) {
                        // 查询到了结果
                        HotelSearchResultDetailVo next = details.listIterator().next();
                        list.add(next);
                        count--;
                    }
                }
            }
        }
        baseRecommendVo.setSearchParams(paramsMap);
        return list;
    }

    private ScheduleDetail getDetail(ScheduleMain main, List<ScheduleDetail> detailList) {
        String scheduleNo = main.getScheduleNo();
        Short goBack = main.getGoBack();
        for (ScheduleDetail detail : detailList) {
            if (detail.getScheduleNo().equals(scheduleNo) && detail.getGoBack().intValue() == goBack.intValue()) {
                return detail;
            }
        }
        return null;
    }


    @Override
    public String test08(String s) {
        FreeProdSearchRequestVo vo = JSON.parseObject(s, FreeProdSearchRequestVo.class);
        SearchResponseVo<FreeProdSearchResultDetailVo> prodSearchResponseVo = prodSearchApi.freeProdSearch(vo);
        if (null != prodSearchResponseVo && null != prodSearchResponseVo.getDetails()) {
            prodSearchResponseVo.getDetails().stream().forEach(detailVo -> {
                // 目的地 ＋ 类型
                String departure = vo.getDepartGeoName();
                //工具类封装
                List<String> tripTags = TripProductUtils.getTags(detailVo.getClassifyName(), departure, detailVo.getDepartPlaceNames());
                detailVo.setTags(tripTags);
                detailVo.setFeatures(detailVo.getTagNames());
            });
        }
        return JSON.toJSONString(prodSearchResponseVo);
    }

    @Override
    public void putRecommendInfo(String scheduleNo, List<RefScheduleOrderVo> ls, ScheduleVo scheduleVo) throws Exception {
        List<BaseRecommendVo> commonRecommendList = getCommonRecommendList(scheduleNo, ls);
        log.info("设置了未预定的推荐信息:" + JSON.toJSONString(commonRecommendList));
        scheduleVo.setUnscheduled(commonRecommendList);
    }

    private boolean stringCheckNotNull(String... strs) {
        for (String str : strs) {
            if (str == null || str.equals("")) {
                return false;
            }
        }
        return true;
    }
}
