package com.mm.myenum;

public enum OrderStatusEnum {
    SUCCESS(0, "成功"),
    FAIL(1, "失败");
    private int code;
    private String desc;

    OrderStatusEnum(int code, String desc) {
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
