package test;

import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hnair.consumer.user.api.ICreditApi;
import com.hnair.consumer.user.api.IUserApi;
import com.hnair.consumer.user.api.IUserBlackListApi;
import com.hnair.consumer.user.api.impl.CreditApiImpl;
import com.hnair.consumer.user.model.UserBlackList;
import com.hnair.consumer.user.vo.RefundCreditAndMoneyRespVo;
import com.hnair.consumer.user.vo.UserCountAndCreditRespVo;
import com.hnair.consumer.user.vo.UserMessageVo;
import com.hnair.consumer.user.vo.UserNameAndCreditVo;

public class MessageCodeTest{
	private static IUserApi userApi;
	private static IUserBlackListApi userBlackListApi;
	
	@SuppressWarnings("resource")
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext cxt = new ClassPathXmlApplicationContext(new String[] { "config/spring_common.xml" });
		userApi = (IUserApi)cxt.getBean("userApi");
		userBlackListApi = (IUserBlackListApi)cxt.getBean("userBlackListApi");
	
	}
	public void blackTest(){
		UserBlackList userBlackList= new UserBlackList();
		userBlackList.setUserId(100001675l);
		userBlackList.setBanUserId(6l);
		UserMessageVo<UserBlackList> r = userBlackListApi.addUserBlackList(userBlackList);
		userBlackList.setBanUserId(7l);
		 r = userBlackListApi.addUserBlackList(userBlackList);
		userBlackList.setBanUserId(9l);
		r = userBlackListApi.addUserBlackList(userBlackList);
		userBlackList.setBanUserId(12l);
		 r = userBlackListApi.addUserBlackList(userBlackList);
		userBlackList.setBanUserId(1002l);
		 r = userBlackListApi.addUserBlackList(userBlackList);
		System.out.println(r.isResult());
		System.out.println(r.getErrorCode());
		System.out.println(r.getErrorMessage());
	}
	
	public void getBlackTest(){
		UserMessageVo<List<UserBlackList>> r = userBlackListApi.getUserBlackList(100001675l, null, 2);
		System.out.println(r.isResult());
		System.out.println(r.getT());
		System.out.println(r.getIsHasNext());
		UserMessageVo<List<UserBlackList>> r2 = userBlackListApi.getUserBlackList(100001675l, 6l, 2);
		System.out.println(r2.isResult());
		System.out.println(r2.getT());
		System.out.println(r2.getIsHasNext());
		UserMessageVo<List<UserBlackList>> r3 = userBlackListApi.getUserBlackList(100001675l, 3l, 2);
		System.out.println(r3.isResult());
		System.out.println(r3.getT());
		System.out.println(r3.getIsHasNext());
	}
	
	public void deleteBlackTest(){
		UserMessageVo<UserBlackList> r = userBlackListApi.deleteUserBlackList(1l,1l);
		System.out.println(r.isResult());
		System.out.println(r.getErrorCode());
		System.out.println(r.getErrorMessage());
	}
	
	
	public void messagetest(){
		userApi.cleanUserPostCount(100000381l);
	}
	@Test
	public void testSendMessage(){
		UserMessageVo vo=userApi.sendOrderStatusMessage("86", "13718456928", "【嗨s途】ssssssss");
		System.out.println(vo);
	}
	
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
