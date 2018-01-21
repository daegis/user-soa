package com.hnair.consumer.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.hnair.consumer.dao.spi.ICommonDao;
import org.springframework.stereotype.Service;

import com.hnair.consumer.user.model.ThirdPartyUserInfo;
import com.hnair.consumer.user.service.IThirdUserService;

/**
 * Description: 
 * All Rights Reserved.
 * @version 1.0  2016年11月2日 下午7:26:17  by 李超（li-ch3@hnair.com）创建
 */
@Service
public class ThirdUserServiceImpl  implements IThirdUserService{

	@Resource(name="ucenterCommonDao")
	private ICommonDao ucenterCommonDao;
	
	@Override
	public ThirdPartyUserInfo getThirdUserInfoByOpenId(String openId) {
		List<ThirdPartyUserInfo> partyUserInfoList=null;
		ThirdPartyUserInfo partyUserInfo = null;
		try {
			partyUserInfoList = ucenterCommonDao.getList(ThirdPartyUserInfo.class, "openId",openId);
			
			if(partyUserInfoList != null && partyUserInfoList.size() > 0){
				partyUserInfo = partyUserInfoList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return partyUserInfo;
	}

	@Override
	public ThirdPartyUserInfo getThirdUserInfoBySessionId(String sessionId) {
		List<ThirdPartyUserInfo> partyUserInfoList=null;
		ThirdPartyUserInfo partyUserInfo = null;
		try {
			partyUserInfoList = ucenterCommonDao.getList(ThirdPartyUserInfo.class, "sessionId", sessionId);
			
			if(partyUserInfoList != null && partyUserInfoList.size() > 0){
				partyUserInfo = partyUserInfoList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return partyUserInfo;
	}

	@Override
	public void updateThirdUserInfo(ThirdPartyUserInfo partyUserInfo) {
		ucenterCommonDao.update(partyUserInfo);
	}

	@Override
	public void saveThirdUserInfo(ThirdPartyUserInfo partyUserInfo) {
		ucenterCommonDao.save(partyUserInfo);
	}
}
