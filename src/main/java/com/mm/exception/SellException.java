package com.mm.exception;

import com.mm.myenum.ResponseEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SellException extends RuntimeException {
    private int code;

    public SellException() {
        super();
    }
    public SellException(ResponseEnum responseEnum) {
        super(responseEnum.getDesc());
        this.code = responseEnum.getCode();
    }

    public SellException(int code,String message) {
        super(message);
        this.code = code;
    }
}
