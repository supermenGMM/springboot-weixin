package com.mm.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class OrderVo implements Serializable
{
    @NotEmpty(message = "订单不能为空")
    public String orderId;
    @NotEmpty(message = "客户号不能为空")
    private String openid;
}
