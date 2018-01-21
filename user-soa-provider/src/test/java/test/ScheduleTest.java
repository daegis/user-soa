package test;

import java.io.IOException;

import javax.annotation.Resource;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.common.json.JSON;
import com.hnair.consumer.user.api.IScheduleApi;
import com.hnair.consumer.user.api.IUserApi;
import com.hnair.consumer.user.vo.ScheduleReqVo;
import com.hnair.consumer.user.vo.ScheduleRespVo;
import com.hnair.consumer.user.vo.UserMessageVo;

public class ScheduleTest{
	@Autowired
    protected ApplicationContext ctx;
	@Resource
	private static IScheduleApi scheduleApi;
	
	@Resource
	private static IUserApi userApi;
	
	@SuppressWarnings("resource")
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext cxt = new ClassPathXmlApplicationContext(new String[] { "config/spring_common.xml" });
		userApi = (IUserApi)cxt.getBean("userApi");
		scheduleApi = (IScheduleApi)cxt.getBean("scheduleApi");
	
	}
	@Test
	public void getScheduleMainInfo() throws IOException{
		ScheduleRespVo vo=scheduleApi.getScheduleMainInfo(0L);
		System.out.println(JSON.json(vo).toString());
	}
	
//	@Test
//	public void testSave(){
//		ScheduleReqVo scheduleReqVo = new ScheduleReqVo();
//		scheduleReqVo.setOrderNo("FO201701230100115");
//		scheduleReqVo.setOrderSubNo("FO201701230100115_1");
//		scheduleReqVo.setCheckinStatus(0);
//		scheduleReqVo.setSeatId("E001");
//		UserMessageVo<ScheduleReqVo> us=scheduleApi.saveScheduleCheckinInfo(scheduleReqVo);
//		System.out.println(us.getMessage());
//	}
//	
	/*@Test
	public void getCreditAndMoney(){
		ApplicationContext aoc = new ClassPathXmlApplicationContext("classpath:config/spring_common.xml");
		ICreditApi bean = aoc.getBean(ICreditApi.class);
		UserMessageVo<RefundCreditAndMoneyRespVo> vo = bean.getRefundCreditAndMoney("abcdefg",80.88);
		RefundCreditAndMoneyRespVo t = vo.getT();
		System.out.println(t.getCredit());
		System.out.println(t.getMoney());
		
	}*/
	

	/*@Test
	public void test1(){
		ApplicationContext aoc = new ClassPathXmlApplicationContext("classpath:config/spring_common.xml");
		ICreditApi bean = aoc.getBean(ICreditApi.class);
		UserMessageVo<UserCountAndCreditRespVo> vo = bean.getDonatePeopleAndCredit("1001");
		UserCountAndCreditRespVo t = vo.getT();
		System.out.println(t.getCreditCount());
		System.out.println(t.getUserCount());
		List<UserNameAndCreditVo> userNameAndCredits = t.getUserNameAndCredits();
		for (UserNameAndCreditVo userNameAndCredit : userNameAndCredits) {
			System.out.println(userNameAndCredit.getUserName()+"==>"+userNameAndCredit.getDonateCredit());
		}
		
	}*/
	
}
