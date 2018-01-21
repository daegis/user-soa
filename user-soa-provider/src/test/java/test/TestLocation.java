package test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.hnair.consumer.user.api.IUserLocationApi;
import com.hnair.consumer.user.vo.UserMessageVo;
import com.hnair.consumer.user.vo.UserUsualLocationVo;

public class TestLocation {
	private static IUserLocationApi userLocationApi;

	@SuppressWarnings("resource")
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext cxt = new ClassPathXmlApplicationContext(new String[] { "config/spring_common.xml" });
		userLocationApi = (IUserLocationApi) cxt.getBean("userLocationApi");

	}

	@Test
	public void loctionTest() {
		UserMessageVo<UserUsualLocationVo> locationVo = userLocationApi.getUserUsualLocationDestination(100l);
		System.out.println(JSON.toJSON(locationVo));
	}

}
