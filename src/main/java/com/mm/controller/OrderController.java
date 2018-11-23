package com.mm.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mm.dto.OrderDto;
import com.mm.exception.SellException;
import com.mm.form.OrderForm;
import com.mm.myenum.ResponseEnum;
import com.mm.pojo.OrderDetail;
import com.mm.service.OrderService;
import com.mm.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.runtime.Log;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.List;
@Slf4j
@RestController
@RequestMapping(value = "/buyer/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping(value = "/create" )
    public ResponseVo create(@RequestBody @Valid OrderForm orderForm, BindingResult bindingResult) {

        //检查 请求信息
        if (bindingResult.hasErrors()) {
            throw new SellException(ResponseEnum.REQUEST_CONTENT_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }

        Gson gson = new Gson();
        List<OrderDetail> orderDetails =gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {}.getType());
        log.debug("解析到的orderDetail数组为:[{}]",orderDetails.toString());
        if (orderDetails.isEmpty()) {
            throw new SellException(ResponseEnum.REQUEST_CONTENT_ERROR.getCode(), "购物车为空");
        }
        //todo 检查手机号?
        orderDetails.forEach(o->{
            if (StringUtils.isBlank(o.getProductId()) && (o.getProductQuantity() == null || o.getProductQuantity() <= 0)) {
                throw new SellException(ResponseEnum.REQUEST_CONTENT_ERROR.getCode(), "购物车的产品id和数量不能为空，且数量不能小于0");
            }
        });
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerAddress(orderForm.getAddress());
        orderDto.setBuyerName(orderForm.getName());
        orderDto.setBuyerOpenid(orderForm.getOpenid());
        orderDto.setBuyerPhone(orderForm.getPhone());
        orderDto.setOrderDetails(orderDetails);

        return ResponseVo.success(orderService.createOrder(orderDto).getOrderId());
    }
}
