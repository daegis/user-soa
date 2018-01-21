package com.hnair.consumer.user.vo.recommendation.finance;


import lombok.Data;


/**
 * 退款订单积分支付流水号
 * 
 * @author hexiaohong
 */
@Data
public class RefundOrderTradeNo
{
    /**
     * 订单号
     */
    private String businessNo;

    /**
     * 积分支付流水号
     */
    private String tradeNo;
}
