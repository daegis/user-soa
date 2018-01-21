package com.hnair.consumer.user.service.impl;

import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.user.model.Customers;
import com.hnair.consumer.user.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/1/21 21:24
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private ICommonService commonService;

    @Override
    public Customers getCustomer(Integer id) {
        return commonService.get(Customers.class, "cid", id);
    }
}
