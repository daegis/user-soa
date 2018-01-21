package com.hnair.consumer.user.api;

import com.hnair.consumer.user.model.ActiveInfo;

import java.util.List;

//会员专区
public interface IMemberAreaApi {

public List<ActiveInfo>  getActiveInfos(Integer start, Integer limit,String activePosition);

public boolean toCheckProductId(Long productId);

}
