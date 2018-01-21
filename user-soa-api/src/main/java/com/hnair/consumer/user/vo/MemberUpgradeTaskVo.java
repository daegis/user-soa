package com.hnair.consumer.user.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

/**
 * 会员升级任务vo
 * @author zhangxianbin
 *
 */
@Setter
@Getter
public class MemberUpgradeTaskVo implements Serializable{

	private static final long serialVersionUID = -7135656603496918181L;

	private String taskName;
	
	private String taskImg;
	
	private List<MemberUpgradeTaskDetailVo> details = new ArrayList<>();
	
	
	public static void main(String[] args){
		MemberUpgradeTaskVo vo = new MemberUpgradeTaskVo();
		vo.setTaskName("会员升级任务");
		vo.setTaskImg("http://img22.uyutrip.com/hnair/column/2017/09/22/48/49483b1fcff546599ea94911b14be808_501_579.jpg");
		
		MemberUpgradeTaskDetailVo detail1 = new MemberUpgradeTaskDetailVo();
		detail1.setMemberLevel("青年乐园");
		detail1.setMemberLevelCode("1");
		detail1.getOrderTypes().add("4");
		detail1.setUpgradeTaskMaxAgeRestrict("30");
		detail1.setUpgradeTaskGoal(3);
		detail1.setUpgradeTaskName("用户为30岁及以下青年，且在hiapp平台预定过3次机票");
		vo.getDetails().add(detail1);
		
		MemberUpgradeTaskDetailVo detail2 = new MemberUpgradeTaskDetailVo();
		detail2.setMemberLevel("精英贵族");
		detail2.setMemberLevelCode("2");
		detail2.getOrderTypes().add("4");
		detail2.setUpgradeTaskGoal(10);
		detail2.setUpgradeTaskName("在hiapp平台预定过10次机票");
		vo.getDetails().add(detail2);
		
		MemberUpgradeTaskDetailVo detail3 = new MemberUpgradeTaskDetailVo();
		detail3.setMemberLevel("温馨家庭");
		detail3.setMemberLevelCode("3");
		detail3.getOrderTypes().add("1");
		detail3.setUpgradeTaskGoal(5);
		detail3.setUpgradeTaskName("在hiapp平台预定过5次酒店");
		vo.getDetails().add(detail3);
		
		System.out.println(JSON.toJSONString(vo));
	}
	
}
