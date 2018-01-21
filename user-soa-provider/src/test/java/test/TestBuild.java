package test;

import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.user.model.Customers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/1/21 12:23
 */
public class TestBuild extends BaseJunit4Test {

    @Autowired
    private ICommonService userCommonService;

    @Test
    public void test01() {
        List<Customers> cid = userCommonService.getList(Customers.class, "cid", 1);
        System.out.println(cid.get(0).getName());
    }
}
