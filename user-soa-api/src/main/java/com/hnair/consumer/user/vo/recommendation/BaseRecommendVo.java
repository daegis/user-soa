package com.hnair.consumer.user.vo.recommendation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/1/15 9:32
 */
@Setter
@Getter
@ToString
public class BaseRecommendVo implements Serializable {
    private Integer category;
    private Boolean isShown;
    private String whyFalse;
    private Integer count;
    private Integer weight;
    private String name;
    private List visaList;
    private List flightList;
    private List hotelList;
    private List tourList;
    private List mtsList;
    private String showMore;
    private Map<String, Object> searchParams;
}
