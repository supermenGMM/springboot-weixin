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
import javassist.bytecode.stackmap.BasicBlock;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.xml.ws.Response;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private ProductInfoRepository productInfoRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public OrderMaster createOrder( OrderDto orderDto) {
        //获取一个orderId
        String orderId = KeyUtils.genUniqueKey();

        //遍历items,获取orderDetail
        orderDto.getOrderDetails().forEach(orderDetail -> {
            ProductInfo productInfo = null;
            try {
                productInfo = productInfoRepository.findById(orderDetail.getProductId()).get();
            } catch (InvalidDataAccessApiUsageException e) {//因为controller已经校验了字段的非空和正确性。这里findById不会报IllegalArgumentException异常。
                System.out.println("-----");
                throw new SellException(ResponseEnum.REQUEST_CONTENT_ERROR);
            }catch (NoSuchElementException e){
                throw new SellException(ResponseEnum.PRODUCT_NOT_FOUND);
            } catch (Exception e) {
                System.out.println("---"+e);
            }
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
