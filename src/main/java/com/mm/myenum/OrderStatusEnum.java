package com.mm.myenum;

public enum OrderStatusEnum {
    NEW(0, "新建",true),
    CANCEL(1, "取消",true),
    REVEIVE(2, "已接单",true),
    DILIVERYING(3, "配送中",false),
    FINISHED(4, "已送达", false),
    /**
     * 可能是支付失败引起的。
     */
    ERROR(5,"失败",false)
    ;
    private int code;
    private String desc;
    private boolean canancel;

    OrderStatusEnum(int code, String desc, boolean canancel) {
        this.code = code;
        this.desc = desc;
        this.canancel = canancel;
    }

    public int getCode() {

        return code;
    }

    public String getDesc() {
        return desc;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isCanancel() {
        return canancel;
    }

    public void setCanancel(boolean canancel) {
        this.canancel = canancel;
    }

    //通过code获取相对应的enum
    public static OrderStatusEnum findByCode(int code) {
        for (OrderStatusEnum orderStatusEnum : OrderStatusEnum.values()) {
            if (orderStatusEnum.getCode() == code) {
                return orderStatusEnum;
            }
        }
        return null;
    }
}
