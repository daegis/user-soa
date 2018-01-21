package com.hnair.consumer.user.utils;

import java.util.HashMap;
import java.util.Map;



import com.hnair.consumer.utils.HttpClientUtils;
import com.hnair.consumer.utils.system.ConfigPropertieUtils;

public class SearchFlightUtils {
	
	/**
	 * 跳转前端页面的域名和访问接口
	 */
	private static final String H5_BASE_URL = ConfigPropertieUtils.getString("hapi.inner.url");
	
	//解密
	private static final String PUBLIC_KEY = ConfigPropertieUtils.getString("hapi.publicKey");
	//查询机票接口
	private static final String SEARCH_FLIGHT_URL = ConfigPropertieUtils.getString("search.flight.url");
	
	private static final String APP_VERSION = ConfigPropertieUtils.getString("app.version");
	
	public static String getFlightList(String startCityCode,String endCityCode,
			String date,String isNative) throws Exception{
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("publicKey",PUBLIC_KEY);
		params.put("app_version",APP_VERSION);
		//起始城市码
		params.put("startCityCode",startCityCode);
		//到达城市码
		params.put("endCityCode",endCityCode);
		params.put("date",date);
		//1表示原生,0表示h5
		params.put("isNative",isNative);
		params.put("action","searchFlightList");
		//查询信息
			//flightMap中取只3个值
			/*JSONObject jsonObject = JSON.parseObject(sendPost);
			JSONObject data = jsonObject.getJSONObject("data");
			JSONArray flightMapArray = data.getJSONArray("flightMap");
			if(null!=flightMapArray&&flightMapArray.size()>3){
				data.put("flightMap", flightMapArray.subList(0, 3));
				sendPost =JSON.toJSONString(jsonObject);
			}*/
		return HttpClientUtils.sendPost(H5_BASE_URL+SEARCH_FLIGHT_URL, params, "utf-8");
		
		
	}
	
	public static String getInternationalFlightList(String startCityCode,String endCityCode,
			String date,String isNative,String adultNum,String childNum,String tripType) throws Exception{
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("publicKey",PUBLIC_KEY);
		params.put("startCityCode",startCityCode);
		params.put("endCityCode",endCityCode);
		params.put("date",date);
		//1表示原生,0表示h5
		params.put("isNative",isNative);
		params.put("action","searchInternationalFlightListFromMeiYa");
		//成人数量
		params.put("adultNum", adultNum);
		//儿童数量
		params.put("childNum", childNum);
		//0表示单层,1表示往返
		params.put("tripType", tripType);
		//查询信息
			//searchResult中取只3个值
			/*JSONObject jsonObject = JSON.parseObject(sendPost);
			JSONObject data = jsonObject.getJSONObject("data");
			JSONArray searchResultArray = data.getJSONArray("searchResult");
			if(null!=searchResultArray&&searchResultArray.size()>3){
				data.put("searchResult", searchResultArray.subList(0, 3));
				sendPost =JSON.toJSONString(jsonObject);
			}*/
		return HttpClientUtils.sendPost(H5_BASE_URL+SEARCH_FLIGHT_URL, params, "utf-8");
		
		
	}
	
	public static String getFlightRealTimesUniqueInfo(
			String date,String flightNo) throws Exception{
		Map<String, String> params = new HashMap<String, String>();
		params.put("publicKey",PUBLIC_KEY);
		//出发日期
		params.put("date",date);
		//航班号
		params.put("flightNo",flightNo);
		params.put("action","searchFlightRealTimesUniqueInfo");
		
		return HttpClientUtils.sendPost(H5_BASE_URL+SEARCH_FLIGHT_URL, params, "utf-8");
		
	}
	
	

}
