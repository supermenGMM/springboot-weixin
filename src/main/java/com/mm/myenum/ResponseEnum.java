package com.mm.myenum;

/**
 * 响应码枚举
 */
public enum ResponseEnum {
    SUCCESS(0,"成功!"),
    ERROR(1,"失败");
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
