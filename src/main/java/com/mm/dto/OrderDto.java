package com.mm.dto;

import com.mm.pojo.OrderDetail;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private String  buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private List<OrderDetail> orderDetails;
}
