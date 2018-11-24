package com.mm.service;

import com.mm.dto.OrderDto;
import com.mm.dto.StockDTO;
import com.mm.pojo.OrderDetail;
import com.oracle.webservices.internal.api.EnvelopeStyle;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;
//    @Transactional
    @Test
    public void createOrder() {
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerAddress("北京市朝阳区");
        orderDto.setBuyerName("赵蒙");
        orderDto.setBuyerOpenid("003");
        orderDto.setBuyerPhone("1929230022");
        List<StockDTO> stockDTOS = new ArrayList<>();
        stockDTOS.add(new StockDTO("4", 95L));
        orderDto.setStockDTOS(stockDTOS);
        orderService.createOrder(orderDto);
    }


}