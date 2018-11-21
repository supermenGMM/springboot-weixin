package com.mm.myenum;

public enum  ProductStatusEnum {
    UP(1,"上架"),
    DOWN(2,"下架");
    private int code;
    private String desc;

    ProductStatusEnum(int code, String desc) {
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
