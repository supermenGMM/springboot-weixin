package com.mm.service;

import com.mm.dto.OrderDto;
import com.mm.pojo.OrderDetail;
import com.mm.pojo.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Transactional
    @Test
    public void createOrder() {
        OrderDto orderDto = new OrderDto();
        orderDto.setBuyerAddress("北京市朝阳区");
        orderDto.setBuyerName("赵蒙");
        orderDto.setBuyerOpenid("003");
        orderDto.setBuyerPhone("1929230022");
        List<OrderDetail> orderDetails = new ArrayList<>();
        orderDetails.add(new OrderDetail("2", 92999L));
        orderDetails.add(new OrderDetail("1", 92999L));
        orderDto.setOrderDetails(orderDetails);
        OrderMaster order = orderService.createOrder(orderDto);
        assertNotNull(order);
    }
}