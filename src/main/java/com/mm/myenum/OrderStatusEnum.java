package com.mm.myenum;

public enum OrderStatusEnum {
    NEW(0, "新建"),
    CANCEL(1, "取消"),
    REVEIVE(2, "已接单"),
    DILIVERYING(3, "配送中"),
    FINISHED(4, "已送达"),;
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
