package com.hnair.consumer.user.api.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.order.constant.IdentityTypeConstant;
import com.hnair.consumer.user.api.ICommenContactsApi;
import com.hnair.consumer.user.model.CommonContacts;
import com.hnair.consumer.user.model.CommonIdtype;
import com.hnair.consumer.user.utils.BeanUtilEx;
import com.hnair.consumer.user.vo.CommontContactsVo;
import com.hnair.consumer.user.vo.UserMessageVo;
import com.hnair.consumer.utils.MD5Utils;

import lombok.extern.slf4j.Slf4j;
@Component("commenContactsApi")
@Slf4j
public class CommentContactsApiImpl implements ICommenContactsApi {
	 @Resource(name = "ucenterCommonService")
	 private ICommonService commentContactsService;
	 /**
	  * 常用联系人列表
	  */
	@Override
	public List<CommontContactsVo> queryCommentContactList(CommonContacts commenContacts) {
	//创建映射对象
	List<CommontContactsVo> contactsVoList = new ArrayList<CommontContactsVo>();
	//查询当前用户常用联系人
	List<CommonContacts> contacts =null;
	if (commenContacts.getSortIdentifier()==1) {
		contacts = commentContactsService.getListBySqlId(CommonContacts.class, "getAllList", "isDelete",0,"userId",commenContacts.getUserId());
	}else if (commenContacts.getSortIdentifier()==2) {
		contacts = commentContactsService.getListBySqlId(CommonContacts.class, "getAllListAll", "isDelete",0,"userId",commenContacts.getUserId());
	}else{
		contacts = commentContactsService.getList(commenContacts);
	}
	
	
	//遍历获取contactsId并添加list
	List<Long> contactsIdList=new  ArrayList<>();
	if (CollectionUtils.isNotEmpty(contacts)) {
		contactsIdList.addAll(contacts.stream().collect(Collectors.groupingBy(CommonContacts :: getId)).keySet());
		List<CommonIdtype> commonIdtypeList = commentContactsService.getListBySqlId(CommonIdtype.class, "selectByContactsIds", "list",contactsIdList);
		Map<Long,List<CommonIdtype>> commonIdtypeMap = null;
		if(!CollectionUtils.isEmpty(commonIdtypeList)){
			commonIdtypeMap = commonIdtypeList.stream().collect(Collectors.groupingBy(CommonIdtype :: getContactsId));
		}
		if(commonIdtypeMap == null){
			commonIdtypeMap = new HashMap<>();
		}
		for(CommonContacts c :contacts){
			CommontContactsVo vo = new CommontContactsVo();
			try {
				BeanUtilEx.copyProperties(vo, c);
			} catch (Exception e) {
				e.printStackTrace();
			}
			List<CommonIdtype> commonIdtypes = commonIdtypeMap.get(c.getId());
			if(!CollectionUtils.isEmpty(commonIdtypes)){
				vo.getCommonIdtypes().addAll(commonIdtypes);
			}
			contactsVoList.add(vo);
		}

	}	
	return contactsVoList;
    
	}
	/**
	 * 根据id查询常用联系人
	 */
	@Override
	public CommontContactsVo queryContactById(CommonContacts commenContacts,String idTypeId) {
		CommonContacts contacts = commentContactsService.get(commenContacts);
		CommontContactsVo vo = new CommontContactsVo();
		try {
			BeanUtilEx.copyProperties(vo, contacts);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		 List<CommonIdtype> commonIdtype = commentContactsService.getList(CommonIdtype.class,"contactsId",commenContacts.getId());
		vo.getCommonIdtypes().addAll(commonIdtype);
		return vo;
	}
	/***
	 * 编辑联系人
	 */
	@Override
	@Transactional
	public List<CommonIdtype> updateCommontContacts(CommonContacts commenContacts, List<CommonIdtype> commonIdtypeList) {
		try {
			commentContactsService.update(commenContacts);
			List<CommonIdtype> list = commentContactsService.getList(CommonIdtype.class,"contactsId",commenContacts.getId());
			updatecommonIdtypes(list, commonIdtypeList);
			return commonIdtypeList;
		} catch (Exception e) {
			log.error("保存联系人错误", e);
			return null;
		}
	}
	
	@Override
	@Transactional
	public UserMessageVo<CommonContacts> AddOrUpdateCommontContacts(CommonContacts commenContacts, List<CommonIdtype> commonIdtypeList) {
		// TODO
		return null;
	}
	
	
	public void updatecommonIdtypes(List<CommonIdtype> selectList,List<CommonIdtype> paramList){
		List<CommonIdtype> insertList = paramList.stream().filter(f -> f.getId() == null || "".equals(f.getId())).collect(Collectors.toList());
		if (CollectionUtils.isNotEmpty(insertList)) {
			for (CommonIdtype commonIdtype : insertList) {
				commonIdtype.setCreateTime(new Date());
				commonIdtype.setLastModifyTime(new Date());
				saveCommonIdType(commonIdtype);
			}
		}
		
		Map<Long,CommonIdtype> updateMap = paramList.stream().filter(f -> f.getId() != null || !"".equals(f.getId()) ).collect(Collectors.toMap(CommonIdtype::getId, o->o));
		selectList.stream().forEach(o -> {
			Long id = o.getId();
			o.setCreateTime(null);
			o.setLastModifyTime(null);
			if (updateMap.keySet().contains(id)) {
				CommonIdtype m = updateMap.get(id);
				m.setCreateTime(null);
				log.info("更新证件信息:" + JSON.toJSONString(m));
				commentContactsService.update(m);
			} else {
				commentContactsService.delete(id, CommonIdtype.class);
			}
		});
	}
	
	/**
	 * 添加联系人
	 */
	@Transactional
	public Integer saveCommontContacts(CommonContacts commenContacts,List<CommonIdtype> commonIdtypeList) {
		try {
			commentContactsService.save(commenContacts);
			for (CommonIdtype commonIdtype : commonIdtypeList) {
				commonIdtype.setContactsId(commenContacts.getId());
				saveCommonIdType(commonIdtype);
			}
			return 1;
		} catch (Exception e) {
			return 0;
		}
		
		
	    
		
	}
	/**
	 * 删除联系人
	 */
	@Override
	public void deleteCommontContacts(CommonContacts commenContacts) {
		commentContactsService.update(commenContacts);
		
	}
	/**
	 * 修改联系人证件类别
	 */
	public void updateCommonIdType(CommonIdtype commonIdtype) {
		commentContactsService.update(commonIdtype);
	}
/***
 * 保存联系人证件信息
 */
	public void saveCommonIdType(CommonIdtype commonIdtype) {
		commentContactsService.save(commonIdtype);
		
	}
@Override
public CommonContacts queryContactByIdOne(CommonContacts commenContactsAll) {
	return commentContactsService.get(commenContactsAll);
}
@Override
public List<CommonContacts> queryCommentContactListAll(
		CommonContacts commenContactsAll) {
	return commentContactsService.getList(commenContactsAll);
}
@Override
public void updateCommontContactsListAll(List<CommonContacts> commonContactsList) {
	for (CommonContacts commonContacts : commonContactsList) {
		commentContactsService.update(commonContacts);
	}
	
}

/**
 * 根据id主键集合查询联系人
 */
@Override
public List<CommontContactsVo> queryContactsListByContactsId(List<Long> idTypeIds) {
	//创建映射对象
	List<CommontContactsVo> contactsVoList = new ArrayList<CommontContactsVo>();
	List<CommonIdtype> commonIdtypeList = commentContactsService.getListBySqlId(CommonIdtype.class, "selectByIds", "list",idTypeIds);
	List<Long> contactsIdList=new  ArrayList<>();
	if (CollectionUtils.isNotEmpty(commonIdtypeList)) {
		contactsIdList.addAll(commonIdtypeList.stream().collect(Collectors.groupingBy(CommonIdtype :: getContactsId)).keySet());
		List<CommonContacts> contactsList = commentContactsService.getListBySqlId(CommonContacts.class, "selectByIds", "list",contactsIdList);
		Map<Long,List<CommonIdtype>> commonIdtypeMap = null;
		if(!CollectionUtils.isEmpty(commonIdtypeList)){
			commonIdtypeMap = commonIdtypeList.stream().collect(Collectors.groupingBy(CommonIdtype :: getContactsId));
		}
		if(commonIdtypeMap == null){
			commonIdtypeMap = new HashMap<>();
		}
		for(CommonContacts c :contactsList){
			CommontContactsVo vo = new CommontContactsVo();
			try {
				BeanUtilEx.copyProperties(vo, c);
			} catch (Exception e) {
				e.printStackTrace();
			}
			List<CommonIdtype> commonIdtypes = commonIdtypeMap.get(c.getId());
			if(!CollectionUtils.isEmpty(commonIdtypes)){
				vo.getCommonIdtypes().addAll(commonIdtypes);
			}
			contactsVoList.add(vo);
		}
	}
	return contactsVoList;
}
@Override
public Integer checkIdTypeNoByNumber(String replace,Long userId) {
	CommonIdtype commonIdtype = new CommonIdtype();
	commonIdtype.setIdNo(replace);
	commonIdtype.setUserId(userId);
	List<CommonIdtype> commonIdlist = commentContactsService.getList(commonIdtype);
	if (CollectionUtils.isNotEmpty(commonIdlist)) {
		return 2;
	}else{
		return 1;
	}
}

	

}
