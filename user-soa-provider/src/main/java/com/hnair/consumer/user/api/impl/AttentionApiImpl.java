package com.hnair.consumer.user.api.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.hnair.consumer.dao.service.ICommonService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.utils.CollectionUtils;

import com.hnair.consumer.user.api.IAttentionApi;
import com.hnair.consumer.user.enums.AttentionTypeEnum;
import com.hnair.consumer.user.model.Attention;
import com.hnair.consumer.user.service.IUserBaseInfoService;
import com.hnair.consumer.user.vo.UserMessageVo;

/**
 * Description: 关注接口
 * All Rights Reserved.
 *
 * @version 1.0  2016年11月17日 下午2:54:04  by 李超（li-ch3@hnair.com）创建
 */
@Component("attentionApi")
public class AttentionApiImpl implements IAttentionApi {
    private static Logger logger = LoggerFactory.getLogger(AttentionApiImpl.class);
    @Autowired
    private IUserBaseInfoService userBaseInfoService;
    @Resource(name = "ucenterCommonService")
    private ICommonService ucenterService;

    @Override
    public UserMessageVo<Attention> attention(Attention attention) {

        return userBaseInfoService.attention(attention);
    }

    @Override
    public UserMessageVo<Attention> cancelAttention(Attention attention) {

        return userBaseInfoService.cancelAttention(attention);
    }

    @Override
    public Map<String, Object> queryAttentionInfo(Long userId, Long targetUserId) {

        return userBaseInfoService.queryAttentionInfo(userId, targetUserId);
    }

    @Override
    public Map<String, Object> queryAttentionList(String createTime, Long userId, Integer type, Integer pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        //查询关注列表
        Date createDate = null;
        if (StringUtils.isNotEmpty(createTime) && !"0".equals(createTime)) {
            logger.info("createTime为：" + createTime);
            createDate = new Date(Long.parseLong(createTime));
        }

        List<Attention> attentionList = ucenterService.getList(Attention.class, AttentionTypeEnum.ATTENTION.getType().intValue() == type ? "userId" : "attentionUserId", userId, "order", "orderDesc", "pageSize", pageSize + 1, "createTime", createDate);
        Integer isHasNext = 0;
        if (CollectionUtils.isNotEmpty(attentionList) && attentionList.size() > pageSize) {
            isHasNext = 1;
            attentionList.remove(attentionList.size() - 1);
        }
        //查询关注人数
        Integer attentionCount = ucenterService.getBySqlId(Attention.class, "pageCount", AttentionTypeEnum.ATTENTION.getType() == type ? "userId" : "attentionUserId", userId);
        map.put("attentionList", attentionList);
        map.put("attentionCount", attentionCount);
        map.put("isHasNext", isHasNext);
        return map;
    }

    @Override
    public Map<Long, AttentionTypeEnum> queryAttentionRelation(Long userId, List<Long> targetUserIds) {
        Map<Long, AttentionTypeEnum> result = new HashMap<Long, AttentionTypeEnum>();
        //查询人关注结果
        List<Attention> attentionList = ucenterService.getListBySqlId(Attention.class, "selectByUserIds", "userId", userId, "userIds", targetUserIds);
        Map<Long, Attention> relation = new HashMap<Long, Attention>();
        if (CollectionUtils.isNotEmpty(attentionList)) {
            //将列表转为map
            for (Attention attention : attentionList) {
                relation.put(attention.getAttentionUserId(), attention);
            }
        }
        //遍历关注关系
        for (Long targetUserId : targetUserIds) {
            Attention targetAttention = relation.get(targetUserId);
            //不存在关系
            if (targetAttention == null) {
                result.put(targetUserId, AttentionTypeEnum.NO_RELATION);
            }
            //关注
            else if (targetAttention != null && targetAttention.getIsMutualAttention() == 0) {
                result.put(targetUserId, AttentionTypeEnum.ATTENTION);
            }
            //相互关注
            else if (targetAttention != null && targetAttention.getIsMutualAttention() == 1) {
                result.put(targetUserId, AttentionTypeEnum.MUTUAL_ATTENTION);
            } else {
                result.put(targetUserId, AttentionTypeEnum.NO_RELATION);
            }
        }
        logger.info("结果集:" + result);
        return result;
    }

}
