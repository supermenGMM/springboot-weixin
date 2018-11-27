package com.mm.aspect;

import com.mm.vo.ResponseVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseVo handler(Exception e) {
        return ResponseVo.error(e);
    }
}
