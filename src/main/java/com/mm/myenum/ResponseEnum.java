package com.mm.myenum;

/**
 * 响应码枚举
 */
public enum ResponseEnum {
    SUCCESS(0,"成功!"),
    UNKONW_ERROR(1,"未知错误"),
    REQUEST_CONTENT_ERROR(2,"请求格式错误"),
    REQUEST_PARAMETER_ERROR(6,"请求参数错误"),
    PRODUCT_NOT_FOUND(3,"无效的产品Id"),
    PRODUCT_STOCK_NOENOUGH(4, "产品缺货，当前剩余库存不足订单数量"),
    ORDER_NULL_ERROR(5,"购物车不能为空"),
    ORDER_HAS_CANCELD(6,"订单目前的状态不支持取消订单。请确认订单是否已经取消，或者订单已在配送中或已送达");
    private int code;
    private String desc;

    ResponseEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
