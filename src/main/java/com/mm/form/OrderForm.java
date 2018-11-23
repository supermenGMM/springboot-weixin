package com.mm.form;

import com.mm.pojo.OrderDetail;
import com.mm.service.OrderService;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
public class OrderForm implements Serializable {
    @NotNull(message = "姓名不能为空")
    private String name;
    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$",flags = {},message = "手机号格式错误")
    private String phone;
    @NotNull(message = "地址不能为空")
    private String address;
    @NotNull(message = "微信id不能为空")
    private String openid;
    @NotNull(message = "购物车不能为空")
    @Size(min = 1,message = "购物车不能为空")
    private List<@Valid ProductForm> items;
}

