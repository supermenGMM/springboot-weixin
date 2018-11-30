package com.mm.aspect;

import com.mm.exception.SellException;
import com.mm.pojo.SellerInfo;
import com.mm.vo.ResponseVo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.FORBIDDEN)//这个可以直接设置http返回的响应码
    public ResponseVo handler(Exception e) throws Exception {

        if (e instanceof SellException) {
            return ResponseVo.error(e);
        }else {
            throw e;
        }
    }
}
