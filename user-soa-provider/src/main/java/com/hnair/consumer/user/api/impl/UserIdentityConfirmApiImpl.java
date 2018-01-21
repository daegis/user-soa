package com.hnair.consumer.user.api.impl;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.system.api.ICdnUploadApi;
import com.hnair.consumer.system.enums.CdnFileSourceEnum;
import com.hnair.consumer.user.api.IUserIdentityConfirmApi;
import com.hnair.consumer.user.job.UserIdentityConfirmUploadImageJob;
import com.hnair.consumer.user.model.NewPayBackInfo;
import com.hnair.consumer.user.model.UserIdentityConfirm;


/**
 * 实名认证api
 * @author 张加娄2017年11月9日 16:34:16
 *
 */
@Component("userIdentityConfirmApi")
public class UserIdentityConfirmApiImpl implements IUserIdentityConfirmApi {
	
	@Resource(name = "ucenterCommonService")
	private ICommonService ucenterCommonService;
	
	@Resource(name = "taskExecutor")
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Resource
	ICdnUploadApi cdnUploadApi;
	
	@Transactional
	@Override
	public Integer saveUserIdentityConfirmInfo(UserIdentityConfirm userConfirm, NewPayBackInfo newpay, String photo) {
		try {
			ucenterCommonService.save(userConfirm);
			newpay.setIdentityConfirmId(userConfirm.getId());
			ucenterCommonService.save(newpay);
			UserIdentityConfirmUploadImageJob job = new UserIdentityConfirmUploadImageJob(photo, userConfirm.getId(), 
					cdnUploadApi, CdnFileSourceEnum.NEWPAY_IDENTITY_CONFIRM, ucenterCommonService);
			taskExecutor.submit(job);
			return 1;
		} catch (Exception e) {
			return -1;
		}
	}
	
	@Transactional
	@Override
	public Integer updateUserIdentityConfirmInfo(NewPayBackInfo newpay, String photo) {
		String orderId = newpay.getOrderId();
		NewPayBackInfo newpayParams = new NewPayBackInfo();
		newpayParams.setOrderId(orderId);
		List<NewPayBackInfo> listcount = ucenterCommonService.getList(newpayParams);
		Integer resultNum = -1;
		if(listcount.size()>0){
			NewPayBackInfo newpayinfo = listcount.get(0);
			UserIdentityConfirm userConfirm = ucenterCommonService.get(newpayinfo.getIdentityConfirmId(),UserIdentityConfirm.class);
			
			copyPropertyNotNull(newpayinfo,newpay);
			resultNum = ucenterCommonService.update(newpayinfo);
			if(userConfirm.getPhone()==null || "".equals(userConfirm.getPhone())){
				UserIdentityConfirmUploadImageJob job = new UserIdentityConfirmUploadImageJob(photo, userConfirm.getId(), 
						cdnUploadApi, CdnFileSourceEnum.NEWPAY_IDENTITY_CONFIRM, ucenterCommonService);
				taskExecutor.submit(job);
			}
		}
		return resultNum;
	}
	
	public <T> T copyPropertyNotNull(T target, T source){
		Class<?> classSource = source.getClass();
		Class<?> classTarget = target.getClass();
		Field[] fieldSource = classSource.getDeclaredFields();
		for(Field f : fieldSource){
			Type type = f.getGenericType();
			String name = f.getName();
			String getName = "get"+name.substring(0, 1).toUpperCase() + name.substring(1);
			String setName = "set"+name.substring(0, 1).toUpperCase() + name.substring(1);
			
			Method m;
			Object nameValue = null;
			try {
				m = classSource.getMethod(getName);
				nameValue = m.invoke(source);
			} catch (Exception e) {
				continue;
			}
			
			if(nameValue != null && !"".equals(nameValue.toString())){
				Method mt;
				try {
					mt = classTarget.getMethod(setName,Class.forName(type.getTypeName()));
					mt.invoke(target, nameValue);
				} catch (Exception e) {
					continue;
				}
			}
			
		}
		return target;
	}
	

	@Override
	public UserIdentityConfirm selectUserIdentityConfirmInfo(UserIdentityConfirm userConfirm) {
		UserIdentityConfirm returnData = ucenterCommonService.get(userConfirm);
		return returnData;
	}

	@Override
	public Integer saveNewPayBackInfo(NewPayBackInfo newpay) {
		try{
			ucenterCommonService.save(newpay);
			return 1;
		}catch (Exception e) {
			return -1;
		}
	}

	@Override
	public List<NewPayBackInfo> getNewPayBackInfo(NewPayBackInfo newpay) {
		return ucenterCommonService.getList(newpay);
	}



	
}
