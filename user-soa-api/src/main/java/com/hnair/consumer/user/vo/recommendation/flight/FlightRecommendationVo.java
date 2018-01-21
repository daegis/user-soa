package com.hnair.consumer.user.vo.recommendation.flight;

import com.hnair.consumer.user.vo.recommendation.BaseRecommendVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/1/13 16:04
 */
@Setter
@Getter
@ToString
public class FlightRecommendationVo extends BaseRecommendVo {
    private Integer airlineType;
}
