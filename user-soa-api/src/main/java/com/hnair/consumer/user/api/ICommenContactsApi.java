package com.hnair.consumer.user.api;

import java.util.List;

import com.hnair.consumer.user.model.CommonContacts;
import com.hnair.consumer.user.model.CommonIdtype;
import com.hnair.consumer.user.vo.CommontContactsVo;
import com.hnair.consumer.user.vo.UserMessageVo;


/**
 * Description: 常用联系人接口
 * All Rights Reserved.
 * @version 1.0  2017年11月02日 下午19:49  by 张建波  创建
 */
public interface ICommenContactsApi {
	
	public List<CommontContactsVo> queryCommentContactList(CommonContacts commenContacts);

	public CommontContactsVo queryContactById(CommonContacts commenContacts, String idTypeId);

	public List<CommonIdtype> updateCommontContacts(CommonContacts commenContacts, List<CommonIdtype> commonIdtypeList);

	public Integer saveCommontContacts(CommonContacts commenContacts, List<CommonIdtype> commonIdtypeList);

	public void deleteCommontContacts(CommonContacts commenContacts);

	public void updateCommonIdType(CommonIdtype commonIdtype);

	public void saveCommonIdType(CommonIdtype commonIdtype);

	public CommonContacts queryContactByIdOne(CommonContacts commenContactsAll);

	public List<CommonContacts> queryCommentContactListAll(
			CommonContacts commenContactsAll);

	public void updateCommontContactsListAll(
			List<CommonContacts> commonContactsList);
	
	public List<CommontContactsVo> queryContactsListByContactsId(List<Long> idTypeIds);

	/**
	 * 
	 * @author 许文轩
	 * @comment 增加或者修改联系人
	 * @date 2017年11月22日 下午4:10:36
	 */
	public UserMessageVo<CommonContacts> AddOrUpdateCommontContacts(CommonContacts commenContacts,
			List<CommonIdtype> commonIdtypeList);

	public Integer checkIdTypeNoByNumber(String replace, Long userId);

}
