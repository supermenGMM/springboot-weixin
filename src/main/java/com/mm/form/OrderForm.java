package com.mm.form;

import com.mm.pojo.OrderDetail;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OrderForm {
    @NotNull(message = "姓名不能为空")
    private String name;
    @NotNull(message = "手机号不能为空")
    private String phone;
    @NotNull(message = "地址不能为空")
    private String address;
    @NotNull(message = "微信id不能为空")
    private String openid;
//    @NotNull(message = "订单列表不能为空")
    private String items;

}

