package com.hnair.consumer.user.api.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.user.api.ICommenContactsApi;
import com.hnair.consumer.user.api.ICommonAddressApi;
import com.hnair.consumer.user.model.CommonContacts;
import com.hnair.consumer.user.model.CommonAddress;
import com.hnair.consumer.user.vo.CommontAddressVo;
@Component("commonAddressApi")
public class CommentAddressApiImpl implements ICommonAddressApi {
	 @Resource(name = "ucenterCommonService")
	 private ICommonService commentAddressService;
	 /**
	  * 查询当前用户的常用地址列表
	  */
	@Override
	public List<CommontAddressVo> queryCommontAddressList(
			CommonAddress commonAddress) {
		List<CommontAddressVo> address = commentAddressService.getList(commonAddress);
		return address;
	}
    /**
     *根据id查询当前地址信息的内容
     */
	@Override
	public CommonAddress queryCommontAddressById(CommonAddress commonAddress) {
		CommonAddress object = commentAddressService.get(commonAddress);
		return object;
	}
	/***
	 * 删除常用地址信息
	 */
	@Override
	public void deleteCommontAddress(CommonAddress commonAddress) {
		commentAddressService.update(commonAddress);
		
	}
	/***
	 * 常用地址修改编辑
	 */
	@Override
	public String updateCommontContacts(CommonAddress commonAddress) {
		try {
			commentAddressService.update(commonAddress);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
		
	}
	/**
	 * 保存常用地址
	 */
	@Override
	public String saveCommontContacts(CommonAddress commonAddress) {
		try {
			commentAddressService.save(commonAddress);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		}
		
	}

}
