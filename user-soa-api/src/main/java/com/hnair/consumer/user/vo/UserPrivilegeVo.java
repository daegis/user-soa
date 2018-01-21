package com.hnair.consumer.user.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户会员权益vo
 * @author zhangxianbin
 *
 */
@Setter
@Getter
public class UserPrivilegeVo implements Serializable {

	private static final long serialVersionUID = -508390072331495117L;

	/**
	 * 会员等级名称
	 */
	private String memberLevel;
	
	/**
	 * 会员等级code
	 */
	private String memberLevelCode;
	
	/**
	 * 会员等级图片地址
	 */
	private String memberLevelImg;
	
	/**
	 * 会员等级icon地址
	 */
	private String memberLevelIcon;
	
	/**
	 * 积分数量
	 */
	private Integer creditBillCount;
	
	/**
	 * 会员权益徽章
	 */
	private List<Map<String,String>> privilegeBadges = new ArrayList<>();
	
	/**
	 * 会员升级任务名称
	 */
	private String upgradeTaskName;
	
	private String userAges;
	
	/**
	 * 会员升级任务最小年龄限制
	 */
	private String upgradeTaskMinAgeRestrict;
	
	/**
	 * 会员升级任务最大年龄限制
	 */
	private String upgradeTaskMaxAgeRestrict;
	
	/**
	 * 会员升级任务进度
	 */
	private Integer upgradeTaskSchedule;
	
	/**
	 * 会员升级任务达成目标
	 */
	private Integer upgradeTaskGoal;
	
	/**
	 * 用户是否为当前会员等级
	 */
	private Integer isCurrentLevel;
	
	/**
	 * 会员升级任务支持订单类型
	 */
	private Set<String> orderTypes = new HashSet<>();
	
	/**
	 * 会员权益活动列表
	 */
	private List<UserPrivilegeDetailVo> privilegeDetails = new ArrayList<>();
	
	
	public static void main(String[] args){
		List<UserPrivilegeVo> list = new ArrayList<>();
		
		UserPrivilegeVo vo1 = new UserPrivilegeVo();
		vo1.setMemberLevel("青年乐园");
		vo1.setMemberLevelCode("1");
		vo1.setMemberLevelImg("http://img22.uyutrip.com/hnair/column/2017/09/22/48/49483b1fcff546599ea94911b14be808_501_579.jpg");
		
		Map<String, String> map1 = new HashMap<>();
		map1.put("badgeIcon", "http://img22.uyutrip.com/hnair/column/2017/09/22/48/49483b1fcff546599ea94911b14be808_501_579.jpg");
		map1.put("badgeName", "行李管家");
		vo1.getPrivilegeBadges().add(map1);
		
		Map<String, String> map2 = new HashMap<>();
		map2.put("badgeIcon", "http://img22.uyutrip.com/hnair/column/2017/09/22/48/49483b1fcff546599ea94911b14be808_501_579.jpg");
		map2.put("badgeName", "拼车");
		vo1.getPrivilegeBadges().add(map2);
		
		Map<String, String> map3 = new HashMap<>();
		map3.put("badgeIcon", "http://img22.uyutrip.com/hnair/column/2017/09/22/48/49483b1fcff546599ea94911b14be808_501_579.jpg");
		map3.put("badgeName", "HI领队");
		vo1.getPrivilegeBadges().add(map3);
		
		Map<String, String> map4 = new HashMap<>();
		map4.put("badgeIcon", "http://img22.uyutrip.com/hnair/column/2017/09/22/48/49483b1fcff546599ea94911b14be808_501_579.jpg");
		map4.put("badgeName", "机票服务");
		vo1.getPrivilegeBadges().add(map4);
		
		list.add(vo1);
		
		UserPrivilegeVo vo2 = new UserPrivilegeVo();
		vo2.setMemberLevel("精英贵族");
		vo2.setMemberLevelCode("2");
		vo2.setMemberLevelImg("http://img22.uyutrip.com/hnair/column/2017/09/22/48/49483b1fcff546599ea94911b14be808_501_579.jpg");
		
		Map<String, String> jmap1 = new HashMap<>();
		jmap1.put("badgeIcon", "http://img22.uyutrip.com/hnair/column/2017/09/22/48/49483b1fcff546599ea94911b14be808_501_579.jpg");
		jmap1.put("badgeName", "行李管家");
		vo2.getPrivilegeBadges().add(jmap1);
		
		Map<String, String> jmap2 = new HashMap<>();
		jmap2.put("badgeIcon", "http://img22.uyutrip.com/hnair/column/2017/09/22/48/49483b1fcff546599ea94911b14be808_501_579.jpg");
		jmap2.put("badgeName", "拼车");
		vo2.getPrivilegeBadges().add(jmap2);
		
		Map<String, String> jmap3 = new HashMap<>();
		jmap3.put("badgeIcon", "http://img22.uyutrip.com/hnair/column/2017/09/22/48/49483b1fcff546599ea94911b14be808_501_579.jpg");
		jmap3.put("badgeName", "HI领队");
		vo2.getPrivilegeBadges().add(jmap3);
		
		Map<String, String> jmap4 = new HashMap<>();
		jmap4.put("badgeIcon", "http://img22.uyutrip.com/hnair/column/2017/09/22/48/49483b1fcff546599ea94911b14be808_501_579.jpg");
		jmap4.put("badgeName", "机票服务");
		vo2.getPrivilegeBadges().add(jmap4);
		
		list.add(vo2);
		
		UserPrivilegeVo vo3 = new UserPrivilegeVo();
		vo3.setMemberLevel("温馨家庭");
		vo3.setMemberLevelCode("3");
		vo3.setMemberLevelImg("http://img22.uyutrip.com/hnair/column/2017/09/22/48/49483b1fcff546599ea94911b14be808_501_579.jpg");
		
		Map<String, String> wmap1 = new HashMap<>();
		wmap1.put("badgeIcon", "http://img22.uyutrip.com/hnair/column/2017/09/22/48/49483b1fcff546599ea94911b14be808_501_579.jpg");
		wmap1.put("badgeName", "行李管家");
		vo3.getPrivilegeBadges().add(wmap1);
		
		Map<String, String> wmap2 = new HashMap<>();
		wmap2.put("badgeIcon", "http://img22.uyutrip.com/hnair/column/2017/09/22/48/49483b1fcff546599ea94911b14be808_501_579.jpg");
		wmap2.put("badgeName", "拼车");
		vo3.getPrivilegeBadges().add(wmap2);
		
		Map<String, String> wmap3 = new HashMap<>();
		wmap3.put("badgeIcon", "http://img22.uyutrip.com/hnair/column/2017/09/22/48/49483b1fcff546599ea94911b14be808_501_579.jpg");
		wmap3.put("badgeName", "HI领队");
		vo3.getPrivilegeBadges().add(wmap3);
		
		Map<String, String> wmap4 = new HashMap<>();
		wmap4.put("badgeIcon", "http://img22.uyutrip.com/hnair/column/2017/09/22/48/49483b1fcff546599ea94911b14be808_501_579.jpg");
		wmap4.put("badgeName", "机票服务");
		vo3.getPrivilegeBadges().add(wmap4);
		
		list.add(vo3);
		
		System.out.println(JSON.toJSONString(list));
	}
	
}
