package com.mm.myenum;

public enum PayStatusEnum {
    SUCCESS(0, "成功"),
    FAIL(1, "失败");
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
