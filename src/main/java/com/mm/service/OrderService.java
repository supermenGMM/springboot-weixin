package com.mm.service;

import com.mm.dto.OrderDto;
import com.mm.pojo.OrderMaster;
import com.mm.pojo.ProductInfo;
import com.mm.repository.OrderDetailRepository;
import com.mm.repository.OrderMasterRepository;
import com.mm.repository.ProductInfoRepository;
import com.mm.util.KeyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private ProductInfoRepository productInfoRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public OrderMaster createOrder(OrderDto orderDto) {
        //获取一个orderId
        String orderId = KeyUtils.genUniqueKey();

        //遍历items,获取orderDetail
        orderDto.getOrderDetails().forEach(orderDetail -> {
            ProductInfo productInfo = productInfoRepository.findById(orderDetail.getProductId()).get();
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);
        });
//        创建订单
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);
        orderMaster.setOrderId(orderId);
        //从orderDetail中计算价格的总和
        orderMaster.setOrderAmount(orderDto.getOrderDetails().stream().mapToDouble(o->o.getProductPrice()).sum());
        orderMasterRepository.save(orderMaster);

        return orderMaster;
    }

}
