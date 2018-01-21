package com.hnair.consumer.user.api;

import java.util.List;
import java.util.Map;

import com.hnair.consumer.user.enums.AttentionTypeEnum;
import com.hnair.consumer.user.model.Attention;
import com.hnair.consumer.user.model.CommonContacts;
import com.hnair.consumer.user.model.CommonAddress;
import com.hnair.consumer.user.vo.CommontAddressVo;
import com.hnair.consumer.user.vo.UserMessageVo;


/**
 * Description: 常用地址接口
 * All Rights Reserved.
 * @version 1.0  2017年11月02日 下午19:49  by 张建波  创建
 */
public interface ICommonAddressApi {
   /***
    * 常用地址列表
    * @param commonAddress
    * @return
    */
	List<CommontAddressVo> queryCommontAddressList(CommonAddress commonAddress);
     /***
      * 根据id查询当前的地址信息
      * @param commonAddress
      * @return
      */
    CommonAddress queryCommontAddressById(CommonAddress commonAddress);
       
       /**
        * 删除常用地址信息
        * @param commonAddress
        */
	void deleteCommontAddress(CommonAddress commonAddress);
	/**
	 * 修改常用地址
	 * @param commonAddress
	 * @return 
	 */
	String updateCommontContacts(CommonAddress commonAddress);
	/**
	 * 保存常用地址
	 * @param commonAddress
	 * @return 
	 */
	String saveCommontContacts(CommonAddress commonAddress);
	
}
