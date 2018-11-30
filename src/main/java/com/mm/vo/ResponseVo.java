package com.mm.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mm.exception.SellException;
import com.mm.myenum.ResponseEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseVo implements Serializable{
    @JsonProperty("code")
    private Integer code=0;
    @JsonProperty("msg")
    private String message="";
    @JsonProperty("data")
    private Object data ;

    public static ResponseVo success(Object data) {
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(ResponseEnum.SUCCESS.getCode());
        responseVo.setMessage(ResponseEnum.SUCCESS.getDesc());
        responseVo.setData(data);
        return responseVo;
    }

    public static ResponseVo error(ResponseEnum responseEnum) {
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(responseEnum.getCode());
        responseVo.setMessage(responseEnum.getDesc());
        return responseVo;
    }
    public static ResponseVo error(Integer code,String message) {
        ResponseVo responseVo = new ResponseVo();
        responseVo.setCode(code);
        responseVo.setMessage(message);
        return responseVo;
    }
    public static ResponseVo error(Exception e) {
        ResponseVo responseVo = new ResponseVo();
        if (e instanceof SellException) {
            responseVo.setCode(((SellException) e).getCode());
            responseVo.setMessage(e.getMessage());
        }else {
            responseVo.setCode(ResponseEnum.UNKONW_ERROR.getCode());
            responseVo.setMessage(ResponseEnum.UNKONW_ERROR.getDesc()+e.getMessage());
        }
        return responseVo;
    }
}
