package com.mm.myenum;

/**
 * 响应码枚举
 */
public enum ResponseEnum {
    SUCCESS(0,"成功!"),
    ERROR(1,"失败"),
    REQUEST_CONTENT_ERROR(2,"请求格式错误"),
    PRODUCT_NOT_FOUND(3,"无效的产品Id");
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
