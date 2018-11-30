package com.mm.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * 使用表单方式请求
 */
@Data
@ApiModel(value = "买家下的订单",description = "表单传过来的数据")
public class OrderForm implements Serializable {
    //todo 修改notNull为notEmpty
    @ApiModelProperty(name = "name",value = "姓名")
    @NotNull(message = "姓名不能为空")
    private String name;

    @ApiModelProperty(name = "phone",value = "手机号",allowableValues = "12311231123")
    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$", message = "手机号格式错误")
    private String phone;

    @ApiModelProperty(name = "address",value = "地址")
    @NotNull(message = "地址不能为空")
    private String address;

    @ApiModelProperty(name = "openid",value = "微信openid")
    @NotNull(message = "微信id不能为空")
    private String openid;

    @ApiModelProperty(name = "items",value = "购物车，格式为json串 - ",allowableValues = "[\"productId\":3\\\\,\"productQuantity\":900 }]")
    @NotNull(message = "购物车不能为空")
    private String items;

    @ApiModelProperty(name = "aa",hidden = true)
    private String aa;
}

