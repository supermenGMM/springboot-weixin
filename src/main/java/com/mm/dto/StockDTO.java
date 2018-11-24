package com.mm.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class StockDTO implements Serializable{
    private String productId;
    private Long productQuantity;

    public StockDTO(String productId, Long productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public StockDTO() {

    }
}
