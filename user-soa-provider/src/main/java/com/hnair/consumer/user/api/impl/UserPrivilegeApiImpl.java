package com.hnair.consumer.user.api.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.promotion.api.IPrivilegeApi;
import com.hnair.consumer.promotion.model.Privilege;
import com.hnair.consumer.system.api.loader.IColumnBannerLoaderApi;
import com.hnair.consumer.system.model.Block;
import com.hnair.consumer.user.api.ICreditApi;
import com.hnair.consumer.user.api.IUserPrivilegeApi;
import com.hnair.consumer.user.model.CreditAccount;
import com.hnair.consumer.user.model.UserBaseInfo;
import com.hnair.consumer.user.model.UserOrderCount;
import com.hnair.consumer.user.vo.MemberUpgradeTaskDetailVo;
import com.hnair.consumer.user.vo.MemberUpgradeTaskVo;
import com.hnair.consumer.user.vo.UserPrivilegeDetailVo;
import com.hnair.consumer.user.vo.UserPrivilegeVo;
import com.hnair.consumer.utils.DateUtil;

/**
 * 用户会员权益接口实现类
 * @author zhangxianbin
 *
 */
@Component("userPrivilegeApi")
public class UserPrivilegeApiImpl implements IUserPrivilegeApi {

	@Resource(name = "ucenterCommonService")
	private ICommonService ucenterService;
	
	@Resource
	private IPrivilegeApi privilegeApi;
	
	@Autowired
	private IColumnBannerLoaderApi columnBannerLoaderApi;
	
	@Resource(name = "creditApi")
	private ICreditApi creditApi;
	
	@Override
	public UserPrivilegeVo queryUserPrivilege(Long userId,String memberLevelCode,String selfPage) throws Exception {
		// 查询会员等级
		UserBaseInfo user = ucenterService.getBySqlId(UserBaseInfo.class, "getAll", "userId", userId);
		if(user == null){
			return null;
		}
		String userLevel = user.getGrade() == null ? "0" : user.getGrade().toString();
		if(StringUtils.isBlank(memberLevelCode)){
			memberLevelCode = userLevel;
		}
		final String finallevelCode = memberLevelCode;
		// 获取会员年龄
		Integer years = null;
		if(!StringUtils.isBlank(user.getBirthday())){
			String birthday = user.getBirthday().split("\\.")[0].trim();
			if(!StringUtils.isBlank(birthday) && !birthday.equals("0") && StringUtils.isNumeric(birthday)){
				years = DateUtil.getYear(new Date()) - DateUtil.getYear(new Date(Long.valueOf(birthday)));
			}
		}
		// 查询会员任务进度
		UserOrderCount userOrderCount = ucenterService.getBySqlId(UserOrderCount.class, "getAll", "userId", userId);
		// 查询会员权益活动信息
		Map<Integer, List<Privilege>> privilegeActives = privilegeApi.getPrivileges();
		List<Privilege> privileges = privilegeActives.get(Integer.valueOf(finallevelCode)) == null ? new ArrayList<>() : privilegeActives.get(Integer.valueOf(finallevelCode));
		// 查询会员权益信息
		Block privilegeBlock = null;
		if("1".equals(selfPage)){
			privilegeBlock = columnBannerLoaderApi.getBlock("member_native_privilege_setting");
		}else{
			privilegeBlock = columnBannerLoaderApi.getBlock("member_privilege_setting");
		}
		if(privilegeBlock != null && privilegeBlock.getStatus() != null && privilegeBlock.getStatus() == 1 ){
			String privilegeLabels = privilegeBlock.getContent();
			List<UserPrivilegeVo> userPrivilegeVos = JSON.parseArray(privilegeLabels, UserPrivilegeVo.class);
			UserPrivilegeVo userPrivilegeVo = userPrivilegeVos.stream().filter(u -> finallevelCode.equals(u.getMemberLevelCode())).findFirst().orElse(null);
			if(userPrivilegeVo == null){
				return null;
			}
			// 查询会员升级任务信息
			Block taskBlock=columnBannerLoaderApi.getBlock("member_upgrade_task_setting");
			if(taskBlock != null && taskBlock.getStatus() != null && taskBlock.getStatus() == 1 ){
				String taskLabels = taskBlock.getContent();
				MemberUpgradeTaskVo memberUpgradeTaskVo = JSON.parseObject(taskLabels, MemberUpgradeTaskVo.class);
				Map<String,List<MemberUpgradeTaskDetailVo>> details = memberUpgradeTaskVo.getDetails().stream().collect(Collectors.groupingBy(MemberUpgradeTaskDetailVo :: getMemberLevelCode));
				if(details.get(userPrivilegeVo.getMemberLevelCode()) != null){
					MemberUpgradeTaskDetailVo detail = details.get(userPrivilegeVo.getMemberLevelCode()).get(0);
					userPrivilegeVo.setIsCurrentLevel(finallevelCode.equals(userLevel) ? 1 : 0);
					if(userPrivilegeVo.getIsCurrentLevel().intValue() == 1){
						// 查询积分
						CreditAccount creditAccount = creditApi.getCreditAccountByUserId(userId);
						userPrivilegeVo.setCreditBillCount(creditAccount != null && creditAccount.getCreditsAvailable() != null ? creditAccount.getCreditsAvailable().intValue() : 0);
					}
					userPrivilegeVo.setUpgradeTaskGoal(detail.getUpgradeTaskGoal());
					userPrivilegeVo.setUpgradeTaskMaxAgeRestrict(detail.getUpgradeTaskMaxAgeRestrict());
					userPrivilegeVo.setUpgradeTaskMinAgeRestrict(detail.getUpgradeTaskMinAgeRestrict());
					userPrivilegeVo.setUpgradeTaskName(detail.getUpgradeTaskName());
					userPrivilegeVo.setUserAges(years != null && years >= 0 ? years.toString() : null);
					userPrivilegeVo.setOrderTypes(detail.getOrderTypes());
					Integer upgradeTaskSchedule = 0;					
					upgradeTaskSchedule += (detail.getOrderTypes().contains("1") && userOrderCount != null && userOrderCount.getHotelOrderCount() != null ? userOrderCount.getHotelOrderCount() : 0); 
					upgradeTaskSchedule += (detail.getOrderTypes().contains("2") && userOrderCount != null && userOrderCount.getTourismOrderCount() != null ? userOrderCount.getTourismOrderCount() : 0); 
					upgradeTaskSchedule += (detail.getOrderTypes().contains("4") && userOrderCount != null && userOrderCount.getFlightOrderCount() != null ? userOrderCount.getFlightOrderCount() : 0); 
					userPrivilegeVo.setUpgradeTaskSchedule(upgradeTaskSchedule > userPrivilegeVo.getUpgradeTaskGoal() ? userPrivilegeVo.getUpgradeTaskGoal() : upgradeTaskSchedule);
					privileges.stream().forEach(p -> {
						UserPrivilegeDetailVo detailVo = new UserPrivilegeDetailVo();
						try {
							BeanUtils.copyProperties(detailVo, p);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
						userPrivilegeVo.getPrivilegeDetails().add(detailVo);
					});					
				}				
			}
			return userPrivilegeVo;
		}				
		return null;
	}

	@Override
	public List<UserPrivilegeVo> queryAllPrivilege(Long userId) throws Exception {
		List<UserPrivilegeVo> result = new ArrayList<>();
		result.add(this.queryUserPrivilege(userId, "1", null));
		result.add(this.queryUserPrivilege(userId, "2", null));
		result.add(this.queryUserPrivilege(userId, "3", null));
		return result.stream().filter(u -> u != null).sorted((x,y) -> y.getIsCurrentLevel() - x.getIsCurrentLevel()).collect(Collectors.toList());
	}

}
