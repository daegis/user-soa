package com.hnair.consumer.user.vo.recommendation.flight.international;

import com.hnair.consumer.order.model.OrderCustomerInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Description: 国际机票
 * All Rights Reserved.
 * @version 1.0  2017年2月16日 下午2:18:34  by 李超（li-ch3@hnair.com）创建
 */
@Getter
@Setter
public class InternationalFlightSearchVo implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//查询ID（缓存Key）
	private String queryId;
	//航线标识
	private String airlineCodeKey;
	//总票价
	private String totalFare;
	//优惠券金额
	private String couponPrice;
	//支付金额
	private String needPayPrice;
	//飞行时间
	private String journeyTime;
	//中转次数
	private String transferCount;
	//中转城市
	private String transferCity;
	//是否是中转(0:否,1:是)
	private String isTransfer;
	//是否是经停(0:否,1:是)
	private String isStop;
	//最迟出票时间
	private String lastTktTime;
	//起飞日期，时间戳
	private String departureDate;
	//到达日期，时间戳
	private String arrivalDate;
	//出发日期[yyyy-MM-dd]
	private String strDepartureDate;
	//出发时间[HH:mm]
	private String strDepartureTime;
	//到达日期[yyyy-MM-dd]
	private String strArrivalDate;
	//到达时间[HH:mm]
	private String strArrivalTime;
	//出发机场
	private String departure;
	//出发机场名称
	private String departureName;
	//到达机场
	private String arrival;
	//到达机场名称
	private String arrivalName;
	//间隔天数
	private String flightDays;
	//总飞行时长
	private String totalFlightTime;
	//交易手续费
	private BigDecimal merchantFee;
	//成人的数量
	private Integer adults;
	//儿童的数量
	private Integer children;
	//婴儿数量
	private Integer infants;
	//运价类型
	private String fareType;
	//币种
	private String currency;
	//成人票价
	private BigDecimal adtFare;
	//成人税费
	private BigDecimal adtTax;
	//儿童[2-12岁]票价
	private BigDecimal chdFare;
	//儿童税费
	private BigDecimal chdTax;
	//订单号
	private String orderNum;
	//PNR码
	private String pnr;
	//出发城市名称
	private String dptCity;
	//到达城市名称
	private String arrCity;
	//出发日期
	private String dptDate;
	//航班号
	private String code;
	//sessionId
	private String sessionId;
	//yuetu需要的data属性
	private String data;
	// 联系人
	private String contactor;
	// 联系电话
	private String contactPhone;
	// 订单状态
	private Integer orderStatus;
	// 订单状态中文名
	private String orderStatusName;
	// 订单创建时间
	private Long orderCreatetime;
	
	//是否共享航班：(0:否,1:是)
	private String isShare;

	//乘客票号，多个逗号分隔
	private String ticketNos;
		
	//航段
	private Map<String,InternationalFlightSegmentVo> flightSegments;
	private List<InternationalFlightSegmentVo> flightSegment;
	//返程结果
	private List<InternationalFlightSearchVo> backFlight;
	//供应商报价信息
	private  List<InternationalFlightSupplierPriceInfoVo> supplierPriceInfo;
	//乘机人信息
	private List<OrderCustomerInfo> passengers;

	Long failureTime;
	
	private boolean success;
	
	private String msg;

	// 返回数据的渠道标识
	private String source;
	// Q税需要的key
	private String segmentkey;
	// 票价key
	private String farekey;
	// 积分抵扣金额
	private BigDecimal integralPrice;
}
