package com.hnair.consumer.user.listener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.nlpcn.commons.lang.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.poi.api.IPoiGEOApi;
import com.hnair.consumer.poi.model.Country;
import com.hnair.consumer.user.model.ScheduleDetail;
import com.hnair.consumer.user.model.ScheduleMain;
import com.hnair.consumer.user.vo.ScheduleDetailVo;
import com.hnair.consumer.user.vo.ScheduleMainVo;

/**
 * 
 * 行程信息的修改
 * @author jy
 *
 */
public class SaveScheduleListener {

	@Resource(name = "ucenterCommonService")
	private ICommonService ucenterService;
	@Autowired
    private IPoiGEOApi poiGEOApi;

	private final static Logger logger = LoggerFactory.getLogger(SaveScheduleListener.class);

	public void handleMessage(Object scheduleMainMsg) {
		try {
			ScheduleMainVo scheduleMainVo = (ScheduleMainVo) scheduleMainMsg;
			logger.info("新增行程信息scheduleMainVo {}", scheduleMainVo.toString());
			if (scheduleMainVo != null || scheduleMainVo.getUserId() != null) {
				ScheduleMain scheduleMain = new ScheduleMain();
				//通过vo对shcedulemain赋值
				scheduleMain.setAirLineType(scheduleMainVo.getAirLineType());
				scheduleMain.setArrCity(scheduleMainVo.getArrCity());
				scheduleMain.setArrCityName(scheduleMainVo.getArrCityName());
				scheduleMain.setDepartCity(scheduleMainVo.getDepartCity());
				scheduleMain.setDepartCityName(scheduleMainVo.getDepartCityName());
				scheduleMain.setOrderNo(scheduleMainVo.getOrderNo());
				scheduleMain.setProductId(scheduleMainVo.getProductId());
				scheduleMain.setProductName(scheduleMainVo.getProductName());
				scheduleMain.setScheduleSecondType(scheduleMainVo.getScheduleSecondType());
				scheduleMain.setScheduleType(scheduleMainVo.getScheduleType());
				scheduleMain.setTripType(scheduleMainVo.getTripType());
				scheduleMain.setUserId(scheduleMainVo.getUserId());
				scheduleMain.setUserName(scheduleMainVo.getUserName());
				scheduleMain.setBeginDate(scheduleMainVo.getBeginDateGo());
				if(StringUtil.isNotBlank(scheduleMainVo.getArrCityName())){
					Country country =poiGEOApi.getCountryByCityName(scheduleMainVo.getArrCityName());
					if(country!=null){
						scheduleMain.setDestCountry(country.getCountryName());
					}
				}
				scheduleMain.setDestCity(scheduleMainVo.getArrCityName());
				scheduleMain.setDelFlag((short)0);
				scheduleMain.setEndDate(scheduleMainVo.getEndDateGo());		
				if(scheduleMainVo.getScheduleType().equals("1")){
					scheduleMain.setEndDate(addDateMinut(scheduleMainVo.getEndDateGo(),1));
				}
					
				scheduleMain.setRefOrderNo("");
				scheduleMain.setScheduleNo(scheduleMainVo.getOrderNo()+"_"+1);
				scheduleMain.setGoBack((short)0);				
				ucenterService.save(scheduleMain);
				logger.info("新增主行程scheduleNo {}", scheduleMain.getScheduleNo());
				if(scheduleMainVo.getScheduleType().equals("1") && scheduleMainVo.getTripType()==1){//往返存储为两个行程
					scheduleMain.setArrCity(scheduleMainVo.getDepartCity());
					scheduleMain.setArrCityName(scheduleMainVo.getDepartCityName());
					scheduleMain.setDepartCity(scheduleMainVo.getArrCity());
					scheduleMain.setDepartCityName(scheduleMainVo.getArrCityName());
					scheduleMain.setBeginDate(scheduleMainVo.getBeginDateBack());
					scheduleMain.setEndDate(addDateMinut(scheduleMainVo.getEndDateBack(),1));
					scheduleMain.setDestCity(scheduleMainVo.getDepartCityName());
					if(StringUtil.isNotBlank(scheduleMainVo.getDepartCityName())){
						Country country =poiGEOApi.getCountryByCityName(scheduleMainVo.getDepartCityName());
						if(country!=null){
							scheduleMain.setDestCountry(country.getCountryName());
						}
					}
					scheduleMain.setScheduleNo(scheduleMainVo.getOrderNo()+"_"+2);
					scheduleMain.setGoBack((short)1);
					ucenterService.save(scheduleMain);
					logger.info("新增主行程scheduleNo {}", scheduleMain.getScheduleNo());
				}
				
				
				List<ScheduleDetailVo> scheduleDetailList = scheduleMainVo.getScheduleDetails();
				for(ScheduleDetailVo sd:scheduleDetailList){
					ScheduleDetail scheduleDetail = new ScheduleDetail();
					scheduleDetail.setScheduleNo(scheduleMainVo.getOrderNo()+"_"+1);
					scheduleDetail.setArrAirport(sd.getArrAirport());
					scheduleDetail.setArrAirportName(sd.getArrAirportName());
					scheduleDetail.setArrCity(sd.getArrCity());
					scheduleDetail.setArrCityName(sd.getArrCityName());
					scheduleDetail.setArrDate(sd.getArrDate());
					scheduleDetail.setArrTerminal(sd.getArrTerminal());
					scheduleDetail.setArrTime(sd.getArrTime());
					
					
					scheduleDetail.setCarrier(sd.getCarrier());
					scheduleDetail.setCarrierName(sd.getCarrierName());
					
					scheduleDetail.setCode(sd.getCode());
					scheduleDetail.setDepartCity(sd.getDepartCity());
					scheduleDetail.setDepartCityName(sd.getDepartCityName());
					scheduleDetail.setDepTerminal(sd.getDepTerminal());
					scheduleDetail.setDptAirport(sd.getDptAirport());
					scheduleDetail.setDptAirportName(sd.getDptAirportName());
					scheduleDetail.setDptDate(sd.getDptDate());
					scheduleDetail.setDptTime(sd.getDptTime());
					scheduleDetail.setFlightTime(sd.getFlightTime());
					scheduleDetail.setGoBack(sd.getGoBack());
					scheduleDetail.setOrderNo(sd.getOrderNo());
					scheduleDetail.setOrderSubNo(sd.getOrderSubNo());
					scheduleDetail.setTerminal(sd.getTerminal());
					scheduleDetail.setCabinDesc(sd.getCabinDesc());
					
					scheduleDetail.setDelFlag((short)0);
					
					ucenterService.save(scheduleDetail);
					logger.info("新增主行程详细信息scheduleNo {},OrderSubNo {}", scheduleDetail.getScheduleNo(),scheduleDetail.getOrderSubNo());
					if(scheduleMainVo.getScheduleType().equals("1") && scheduleMainVo.getTripType()==1){//往返存储为两个行程
						scheduleDetail.setScheduleNo(scheduleMainVo.getOrderNo()+"_"+2);
						ucenterService.save(scheduleDetail);
						logger.info("新增主行程详细信息scheduleNo {},OrderSubNo {}", scheduleDetail.getScheduleNo(),scheduleDetail.getOrderSubNo());
					}
				}
			}

		} catch (Exception e) {
			logger.info("添加行程信息异常", e);
		}

	}
	public static Date addDateMinut(Date date, int hour){   
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      
        if (date == null)   
            return null;   
       // System.out.println("front:" + format.format(date)); //显示输入的日期  
        Calendar cal = Calendar.getInstance();   
        cal.setTime(date);   
        cal.add(Calendar.HOUR, hour);// 24小时制   
        date = cal.getTime();   
        //System.out.println("after:" + format.format(date));  //显示更新后的日期 
        cal = null;   
        return date;   

    }
}
