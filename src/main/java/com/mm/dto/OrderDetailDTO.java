package com.mm.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDetailDTO implements Serializable{
    private String detailId;

    private String orderId;

    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 当前价格,单位分
     */
    private Double productPrice;

    /**
     * 数量
     */
    private Long productQuantity;

    /**
     * 小图
     */
    private String productIcon;
    /**
     * 图片
     */
    private String productImage;

}
