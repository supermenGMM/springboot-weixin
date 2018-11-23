package com.mm.form;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data
public class ProductForm implements Serializable{
    @NotNull(message = "商品Id不能为null")
    private String productId;
    @Positive
    @Min(value = 1,message = "商品数量必须大于1")
    private Long productQuanti;
}
