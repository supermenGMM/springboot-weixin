package com.mm.myenum;

/**
 * 响应码枚举
 */
public enum ResponseEnum {
    SUCCESS(0,"成功!"),
    ERROR(1,"失败"),
    REQUEST_CONTENT_ERROR(2,"请求格式错误"),
    REQUEST_PARAMETER_ERROR(6,"请求参数错误"),
    PRODUCT_NOT_FOUND(3,"无效的产品Id"),
    PRODUCT_STOCK_NOENOUGH(4, "产品缺货，当前剩余库存不足订单数量"),
    ORDER_NULL_ERROR(5,"购物车不能为空");
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
