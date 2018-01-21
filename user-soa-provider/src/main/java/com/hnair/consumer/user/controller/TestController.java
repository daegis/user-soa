package com.hnair.consumer.user.controller;

import com.hnair.consumer.user.model.Customers;
import com.hnair.consumer.user.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/1/21 21:02
 */
@Controller
@Slf4j
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/t01/{id}")
    public String test01(Model model, @PathVariable Integer id) {
        log.info("查询id:{}", id);
        Customers customer = testService.getCustomer(id);
        model.addAttribute("customer", customer);
        return "test";
    }

    @RequestMapping("/t02")
    @ResponseBody
    public String test02() {
        return "return string";
    }
}
