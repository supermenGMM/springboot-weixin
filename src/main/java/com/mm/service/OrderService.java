package com.mm.service;

import com.mm.dto.OrderDto;
import com.mm.exception.SellException;
import com.mm.myenum.ResponseEnum;
import com.mm.pojo.OrderMaster;
import com.mm.pojo.ProductInfo;
import com.mm.repository.OrderDetailRepository;
import com.mm.repository.OrderMasterRepository;
import com.mm.repository.ProductInfoRepository;
import com.mm.util.KeyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    @Autowired
    private ProductInfoRepository productInfoRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductService productService;

    @Transactional
    public OrderMaster createOrder( OrderDto orderDto) {
        //减少库存 todo  库存放在最开始。因为如果库存不足，没必要做下面的操作
        //如果库存为0，则修改产品状态为 下架；如果库存不够，则抛异常。 todo  应该先检查库存。
        orderDto.getOrderDetails().stream().forEach(o->{
            ProductInfo productInfo = productService.findById(o.getProductId());
            Long remain = productInfo.getProductStock()-o.getProductQuantity();
            if(remain<0){
                throw new SellException(ResponseEnum.PRODUCT_STOCK_NOENOUGH);
            } else if(remain == 0){
               productInfo.down();
            } else {
              productInfo.setProductStock(productInfo.getProductStock()-o.getProductQuantity());
            }
            productInfoRepository.saveAndFlush(productInfo);
        });


        //获取一个orderId
        String orderId = KeyUtils.genUniqueKey();

        //遍历items,获取orderDetail
        orderDto.getOrderDetails().forEach(orderDetail -> {
            ProductInfo productInfo = productService.findById(orderDetail.getProductId());
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
