package com.hnair.consumer.user.vo.recommendation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Using IntelliJ IDEA.
 *
 * @author HNAyd.xian
 * @date 2018/1/16 17:35
 */
@Setter
@Getter
@ToString
public class MtsRecommendationVo implements Serializable {
    private int id;
    private String name;
    private String url;
    private String pic;
    private String describe;
    private int weight;
}
