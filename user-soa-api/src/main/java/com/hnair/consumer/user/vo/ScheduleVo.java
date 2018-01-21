package com.hnair.consumer.user.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.hnair.consumer.order.vo.RefScheduleOrderVo;
import com.hnair.consumer.user.vo.recommendation.BaseRecommendVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/1/15 9:27
 */
@Setter
@Getter
@ToString
public class ScheduleVo implements Serializable {

    // 未预定列表
    private List<BaseRecommendVo> unscheduled;
    //主行程详细信息
    private List<ScheduleRespVo> scheduleDetailInfo;
    //主行程关联已预订订单列表
    private List<RefScheduleOrderVo> refScheduleOrderInfo;

}