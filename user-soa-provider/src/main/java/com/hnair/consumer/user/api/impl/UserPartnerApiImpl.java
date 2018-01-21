package com.hnair.consumer.user.api.impl;

import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.user.api.IUserPartnerApi;
import com.hnair.consumer.user.model.CreditPartner;
import com.hnair.consumer.user.model.CreditPartnerMap;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component("userPartnerApi")
public class UserPartnerApiImpl implements IUserPartnerApi {

    @Resource(name = "ucenterCommonService")
    private ICommonService ucenterCommonService;


    @Override
    public List<CreditPartnerMap> queryCreditPartnerMap(Long userId) {
        return ucenterCommonService.getList(CreditPartnerMap.class,"userId",userId);
    }

    @Override
    public List<CreditPartner> queryCreditPartner() {
        return ucenterCommonService.getList(CreditPartner.class);
    }
}
