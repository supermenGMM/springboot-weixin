package com.mm.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mm.myenum.OrderStatusEnum;
import com.mm.myenum.PayStatusEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrderMasterDTO extends OrderDto {
    private String orderId;
    private Double orderAmount;

    /**
     * 订单状态, 默认为新下单
     */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /**
     * 支付状态, 默认未支付
     */
    private Integer payStatus= PayStatusEnum.UNPAY.getCode();

    /**
     * 创建时间
     */
    @JsonSerialize(using = DateSerialize.class)
    private Date createTime;

    /**
     * 修改时间
     */
    @JsonSerialize(using = DateSerialize.class)
    private Date updateTime;

    private List<OrderDetailDTO> orderDetailList;
}
