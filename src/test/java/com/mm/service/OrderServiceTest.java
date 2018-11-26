package com.mm.service;

import com.mm.dto.OrderDto;
import com.mm.dto.StockDTO;
import com.mm.pojo.OrderDetail;
import com.mm.pojo.OrderMaster;
import com.mm.repository.OrderDetailRepository;
import com.mm.repository.OrderMasterRepository;
import com.oracle.webservices.internal.api.EnvelopeStyle;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NonUniqueResultException;
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

    @Autowired
    OrderMasterRepository orderDetailRepository;

    @Test
    public void test() {
        //测几种情况：1 查找不到
        OrderMaster orderMaster = orderService.findByOrderIdAndBuyerOpenid("154296233295337065534", "99202");
        System.out.println(orderMaster + "========");
        //2 查找到多个  因为orderId为主键，不可能重复 。
        try {

            OrderMaster orderMaster1 = orderDetailRepository.findByBuyerOpenidAndBuyerPhone("99202", "18511352466");
            System.out.println("======" + orderMaster1);
        }catch (NonUniqueResultException e) {
            System.out.println("NonUniqueResultException");
        } catch (IncorrectResultSizeDataAccessException e) {
            System.out.println("IncorrectResultSizeDataAccessException");
        }

    }

    @Test
    @Transactional
    public void cancel() {
        boolean cancel = orderService.cancel("154296233295337065534", "99202");
        assertEquals(cancel,true);
    }


}