package com.hnair.consumer.user.listener;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.order.util.DateUtil;
import com.hnair.consumer.poi.vo.hi.LocationCityVo;
import com.hnair.consumer.poi.vo.hi.UserLocationMsg;
import com.hnair.consumer.user.model.UserLocationHistory;
import com.hnair.consumer.utils.CollectionUtils;

/**
 * 
 * @author 许文轩
 * @comment 增加定位历史记录
 * @date 2018年1月3日 上午10:46:11
 *
 */
public class AddLocationHistoryListener {

	@Resource(name = "ucenterCommonService")
	private ICommonService ucenterService;

	private final static Logger logger = LoggerFactory.getLogger(AddLocationHistoryListener.class);

	public void handleMessage(Object locationMsg) {
		try {
			UserLocationMsg msg = (UserLocationMsg) locationMsg;
			logger.info("收到增加定位历史记录消息:" + JSON.toJSONString(msg));
			if (msg != null && msg.getUserId() != null && msg.getLocationCityVo() != null) {
				String today = DateUtil.format(new Date(), "yyyy-MM-dd");
				List<UserLocationHistory> historyDBList = ucenterService.getListFromMaster(UserLocationHistory.class,
						"userId", msg.getUserId(), "locationDate", today);
				if (CollectionUtils.isEmpty(historyDBList)) {
					LocationCityVo locationVo = msg.getLocationCityVo();
					UserLocationHistory history = new UserLocationHistory();
					history.setCityId(locationVo.getCityId());
					history.setCreateTime(new Date());
					history.setDestinationId(locationVo.getDestinationId());
					history.setLatitude(msg.getLatitude());
					history.setLongtitude(msg.getLongtitude());
					history.setLocationDate(DateUtil.getDateByFormat(today, "yyyy-MM-dd"));
					history.setUserId(msg.getUserId());
					ucenterService.save(history);
				}
			}

		} catch (Exception e) {
			logger.info("处理增加定位历史消息异常", e);
		}

	}
}
