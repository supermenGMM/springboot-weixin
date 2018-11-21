package com.mm.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mm.myenum.ResponseEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseVo implements Serializable{
    @JsonProperty("code")
    private Integer code;
    @JsonProperty("msg")
    private String message;
    @JsonProperty("data")
    private Object data;

    public static ResponseVo success(Object data) {
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(ResponseEnum.SUCCESS.getCode());
        responseVo.setMessage(ResponseEnum.SUCCESS.getDesc());
        responseVo.setData(data);
        return responseVo;
    }
    public static ResponseVo error() {
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(ResponseEnum.ERROR.getCode());
        responseVo.setMessage(ResponseEnum.ERROR.getDesc());
        return responseVo;
    }
}
