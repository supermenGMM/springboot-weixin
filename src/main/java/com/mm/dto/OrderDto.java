package com.mm.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private String  buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private List<StockDTO> stockDTOS;
}
