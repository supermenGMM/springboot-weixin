package com.mm.controller;

import com.google.gson.reflect.TypeToken;
import com.mm.dto.OrderDto;
import com.mm.dto.OrderMasterDTO;
import com.mm.dto.StockDTO;
import com.mm.exception.SellException;
import com.mm.form.OrderForm;
import com.mm.form.OrderFormJson;
import com.mm.form.ProductForm;
import com.mm.myenum.ResponseEnum;
import com.mm.pojo.ProductInfo;
import com.mm.service.OrderService;
import com.mm.util.GsonUtil;
import com.mm.vo.OrderIdVo;
import com.mm.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/buyer/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 通过表单方式请求
     *
     * @param orderForm
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/create")
    public ResponseVo create2(@Valid OrderForm orderForm, BindingResult bindingResult) {

        //检查 请求信息
        if (bindingResult.hasErrors()) {
            throw new SellException(ResponseEnum.REQUEST_CONTENT_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        //todo  检查商品是否存在。检查商品是否下架。 是否需要？其实后面的逻辑都会处理。先检查只是不需要执行前面的这些逻辑。
        //将vo转换为Dto。
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerAddress(orderForm.getAddress());
        orderDto.setBuyerName(orderForm.getName());
        orderDto.setBuyerOpenid(orderForm.getOpenid());
        orderDto.setBuyerPhone(orderForm.getPhone());
        //将items转换为数组,
        List<ProductForm> productForms = GsonUtil.jsonToList(orderForm.getItems(), new TypeToken<List<ProductForm>>() {
        }.getType());

        if (productForms == null || productForms.isEmpty()) {//无法检查里面的字段是否有空的
            throw new SellException(ResponseEnum.ORDER_NULL_ERROR);
        }
        orderDto.setStockDTOS(productForms.stream().map(p -> new StockDTO(p.getProductId(), p.getProductQuantity())).collect(Collectors.toList()));

        Map<String, String> map = new HashMap();
        map.put("orderId", orderService.createOrder(orderDto).getOrderId());
        return ResponseVo.success(map);
    }

    /**
     * 通过json方式请求
     *
     * @param orderFormJson
     * @param bindingResult
     * @return
     */
    @PostMapping(value = "/createjson")
    public ResponseVo create(@Valid @RequestBody OrderFormJson orderFormJson, BindingResult bindingResult) {

        //检查 请求信息
        if (bindingResult.hasErrors()) {
            throw new SellException(ResponseEnum.REQUEST_CONTENT_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }

        //将vo转换为Dto。
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerAddress(orderFormJson.getAddress());
        orderDto.setBuyerName(orderFormJson.getName());
        orderDto.setBuyerOpenid(orderFormJson.getOpenid());
        orderDto.setBuyerPhone(orderFormJson.getPhone());
        orderDto.setStockDTOS(orderFormJson.getItems().stream().map(o -> new StockDTO(o.getProductId(), o.getProductQuantity())).collect(Collectors.toList()));

        Map<String, String> map = new HashMap();
        map.put("orderId", orderService.createOrder(orderDto).getOrderId());
        return ResponseVo.success(map);
    }

    //todo，根据orderId查找订单。然后与传入的openId做比较。openid这样用
    @GetMapping("/list")
    public ResponseVo findByPage(@RequestParam(name = "openid", required = true) String openid, @RequestParam(name = "page",
        defaultValue = "0", required = false) Integer page, @RequestParam(name = "size", defaultValue = "10", required = false) Integer size) {
        if (StringUtils.isNotBlank("openid")) {
            throw new SellException(ResponseEnum.REQUEST_PARAMETER_ERROR);
        }
        List<OrderMasterDTO> orderMasterDTOS = orderService.findAllByPage(openid, PageRequest.of(page, size));
        return ResponseVo.success(orderMasterDTOS);
    }

    @PostMapping("/cancel")
    public ResponseVo cancel(@RequestParam(value = "openid") String openid, @RequestParam(value = "orderId") String orderId) {
        //todo 检查请求参数
        if (orderService.cancel(orderId,openid)) {
            return ResponseVo.success(null);
        } else {
            return ResponseVo.error(ResponseEnum.ORDER_HAS_CANCELD);
        }
    }

    @GetMapping("/detail")
    public ResponseVo detail(@RequestParam(value = "openid") String openid, @RequestParam(value = "orderId")String orderId){
        //todo检查请求参数
       return ResponseVo.success( orderService.findOrderAll(openid, orderId));
    }

    @PostMapping("/detail2")
    public ResponseVo detailJson(@RequestBody String openid,@RequestBody String orderId) throws InterruptedException {
        Thread.sleep(60000);
        //todo检查请求参数
        return ResponseVo.success( orderService.findOrderAll(openid,orderId));
    }
}
