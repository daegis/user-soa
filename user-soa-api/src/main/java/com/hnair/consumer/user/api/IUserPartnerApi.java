package com.hnair.consumer.user.api;

import com.hnair.consumer.user.model.CreditPartner;
import com.hnair.consumer.user.model.CreditPartnerMap;

import java.util.List;

public interface IUserPartnerApi {

    public List<CreditPartnerMap> queryCreditPartnerMap(Long userId);

    public List<CreditPartner> queryCreditPartner();
}
