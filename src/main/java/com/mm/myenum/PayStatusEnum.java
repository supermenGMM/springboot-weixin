package com.mm.myenum;

public enum PayStatusEnum {
    UNPAY(0, "未支付"),
    SUCCESS(1, "成功"),
    PAYING(2, "支付中"),
    FAILED(3, "失败");

    private int code;
    private String desc;

    PayStatusEnum(int code, String desc) {
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
