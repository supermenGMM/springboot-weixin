package com.mm.controller;

import com.mm.dto.OrderDto;
import com.mm.dto.StockDTO;
import com.mm.exception.SellException;
import com.mm.form.OrderForm;
import com.mm.form.OrderFormJson;
import com.mm.form.ProductForm;
import com.mm.myenum.ResponseEnum;
import com.mm.service.OrderService;
import com.mm.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/buyer/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 通过表单方式请求
     * @param orderForm
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/create" )
    public ResponseVo create2(@Valid OrderForm orderForm, BindingResult bindingResult) {

        //检查 请求信息
        if (bindingResult.hasErrors()) {
            throw new SellException(ResponseEnum.REQUEST_CONTENT_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }

        //将vo转换为Dto。
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerAddress(orderForm.getAddress());
        orderDto.setBuyerName(orderForm.getName());
        orderDto.setBuyerOpenid(orderForm.getOpenid());
        orderDto.setBuyerPhone(orderForm.getPhone());
        //将items转换为数组
        List<ProductForm> productForms =null;

        if (productForms==null||productForms.isEmpty()) {//无法检查里面的字段是否有空的
            throw new SellException(ResponseEnum.ORDER_NULL_ERROR);
        }
        orderDto.setStockDTOS(productForms.stream().map(p -> new StockDTO(p.getProductId(), p.getProductQuantity())).collect(Collectors.toList()));

        return ResponseVo.success(orderService.createOrder(orderDto).getOrderId());
    }

    /**
     * 通过json方式请求
     * @param orderFormJson
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/createjson " )
    public ResponseVo create(@Valid @RequestBody OrderFormJson orderFormJson, BindingResult bindingResult) {

        //检查 请求信息
        if (bindingResult.hasErrors()) {
            throw new SellException(ResponseEnum.REQUEST_CONTENT_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }

        //将vo转换为Dto。
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerAddress(orderFormJson.getAddress());
        orderDto.setBuyerName(orderFormJson.getName());
        orderDto.setBuyerOpenid(orderFormJson.getOpenid());
        orderDto.setBuyerPhone(orderFormJson.getPhone());
        orderDto.setStockDTOS(orderFormJson.getItems().stream().map(o -> new StockDTO(o.getProductId(), o.getProductQuantity())).collect(Collectors.toList()));

        return ResponseVo.success(orderService.createOrder(orderDto).getOrderId());
    }
}
