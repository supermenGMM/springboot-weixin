package com.mm.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderDto implements Serializable{
    private String  buyerName;
    private String buyerPhone;
    private String buyerAddress;
    private String buyerOpenid;
    private List<StockDTO> stockDTOS;
}
