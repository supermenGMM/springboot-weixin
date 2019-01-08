package com.mm.aspect;

import com.mm.exception.SellException;
import com.mm.myenum.ResponseEnum;
import com.mm.pojo.SellerInfo;
import com.mm.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import sun.util.locale.provider.LocaleServiceProviderPool;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)//这个可以直接设置http返回的响应码
    public ResponseVo handler(Exception e)  {

        if (e instanceof SellException) {
            return ResponseVo.error(e);
        }else {
            log.error("系统异常",e);
            log.info("异常message,[{}]", e.getMessage());
            return  ResponseVo.error(ResponseEnum.UNKONW_ERROR.getCode(),e.getMessage());
        }
    }
}
