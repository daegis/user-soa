package com.hnair.consumer.user.vo.recommendation.flight.domestic;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author XIAN_Yingda
 * @date 2018/1/12 10:22
 */
@Setter
@Getter
@ToString
public class RFlightDetailShowVo implements Serializable {
    private String adultFuel;
    private String adultPrice;
    private String adultSalePrice;
    private String adultTax;
    private String airlineLogo;
    private String airlineName;
    private String arrTerminal;
    private String arrTime;
    private Integer basePrice;
    private String bestContent;
    private String cabinClass;
    private String cabinDesc;
    private String carrier;
    private String childFuel;
    private String childPrice;
    private String childSalePrice;
    private String childTax;
    private String code;
    private String depTerminal;
    private String discount;
    private String dptTime;
    private String duration;
    private String endAirport;
    private String endAirportName;
    private String flightDays;
    private String flightTime;
    private String luggageInfo;
    private String meal;
    private String onTimeRate;
    private String planeType;
    private String saleCabinCode;
    private String searchKey;
    private String seatsForSale;
    private String startAirport;
    private String startAirportName;
    private String stops;
    private String supplier;
    private List<String> stopCitiesList;
}
