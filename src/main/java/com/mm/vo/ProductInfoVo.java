package com.mm.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductInfoVo implements Serializable{
    //     "id": "123456",
//         "name": "皮蛋粥",
//         "price": 1.2,
//         "description": "好吃的皮蛋粥",
//         "icon": "http://xxx.com",
    @JsonProperty(value = "id")
    private String productId;
    @JsonProperty("name")
    private String productName;
    @JsonProperty("price")
    private BigDecimal productPrice;
    @JsonProperty("description")
    private String productDescription;
    @JsonProperty("icon")
    private String productIcon;

}
