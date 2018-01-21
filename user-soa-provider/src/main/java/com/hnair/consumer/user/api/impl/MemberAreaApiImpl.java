package com.hnair.consumer.user.api.impl;

import com.hnair.consumer.dao.service.ICommonService;
import com.hnair.consumer.user.api.IMemberAreaApi;
import com.hnair.consumer.user.model.ActiveInfo;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

//会员专区
@Component("iMemberAreaApi")
public class MemberAreaApiImpl implements IMemberAreaApi{

    @Resource(name = "ucenterCommonService")
    private ICommonService ucenterService;


    @Override
    public List<ActiveInfo> getActiveInfos(Integer start, Integer limit, String activePosition) {
        List<ActiveInfo> activeInfos=ucenterService.getListBySqlId(ActiveInfo.class,"getActiveInfoByPosition","activePosition",activePosition,"start", start,
                "limit", limit);
        return activeInfos;
    }

    @Override
    public boolean toCheckProductId(Long productId) {
        List<ActiveInfo> activeInfos=ucenterService.getList(ActiveInfo.class,"productId",productId);
        if(activeInfos!=null&&activeInfos.size()>0) return true;
        return false;
    }
}
