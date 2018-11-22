package com.mm.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mm.dto.OrderDto;
import com.mm.form.OrderForm;
import com.mm.pojo.OrderDetail;
import com.mm.service.OrderService;
import com.mm.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/buyer/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @PostMapping(value = "/create")
    public ResponseVo create(OrderForm orderForm) {

        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerAddress(orderForm.getAddress());
        orderDto.setBuyerName(orderForm.getName());
        orderDto.setBuyerOpenid(orderForm.getOpenid());
        orderDto.setBuyerPhone(orderForm.getPhone());
        Gson gson = new Gson();
        orderDto.setOrderDetails(gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {}.getType()));

        return ResponseVo.success(orderService.createOrder(orderDto).getOrderId());
    }
}
