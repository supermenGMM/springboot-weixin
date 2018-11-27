package com.mm.service;

import com.mm.dto.OrderDetailDTO;
import com.mm.dto.OrderDto;
import com.mm.dto.OrderMasterDTO;
import com.mm.dto.StockDTO;
import com.mm.exception.SellException;
import com.mm.myenum.OrderStatusEnum;
import com.mm.myenum.PayStatusEnum;
import com.mm.myenum.ResponseEnum;
import com.mm.pojo.OrderDetail;
import com.mm.pojo.OrderMaster;
import com.mm.pojo.ProductInfo;
import com.mm.repository.OrderDetailRepository;
import com.mm.repository.OrderMasterRepository;
import com.mm.util.ConvertUtil;
import com.mm.util.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderService {
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductService productService;

    /**
     * 创建订单
     *
     * @param orderDto
     * @return
     */
    @Transactional
    public OrderMaster createOrder(OrderDto orderDto) {
        //减少库存   库存放在最开始。因为如果库存不足，没必要做下面的操作
        //如果库存为0，则修改产品状态为 下架；如果库存不够，则抛异常。   应该先检查库存。
        for (StockDTO dto : orderDto.getStockDTOS()) {
            productService.reduceStock(dto.getProductId(), dto.getProductQuantity());
        }

        //获取一个orderId
        String orderId = KeyUtils.genUniqueKey();

        //遍历items,获取orderDetail
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (StockDTO stockDTO : orderDto.getStockDTOS()) {
            orderDetails.add(saveOrderDetailByPorductIdAndQuantity(stockDTO.getProductId(), stockDTO.getProductQuantity(), orderId));
        }
        //   创建订单
        return saveOrderMasterByOrderDto(orderDto, orderId, calcOrderAmountByOrderDetail(orderDetails));

    }

    /**
     * 保存订单
     *
     * @param orderDto
     * @param orderId
     * @param orderAmount
     */
    public OrderMaster saveOrderMasterByOrderDto(OrderDto orderDto, String orderId, Double orderAmount) {
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDto, orderMaster);
        orderMaster.setOrderId(orderId);
        //从orderDetail中计算价格的总和
        orderMaster.setOrderAmount(orderAmount);
        orderMasterRepository.save(orderMaster);
        return orderMaster;
    }

    /**
     * 根据订单详情计算订单总金额
     *
     * @param orderDetails
     * @return
     */
    public Double calcOrderAmountByOrderDetail(List<OrderDetail> orderDetails) {
        return orderDetails.stream().mapToDouble(o -> o.getProductPrice() * o.getProductQuantity()).sum();
    }

    /**
     * 保存订单详情
     *
     * @param productId
     * @param quantity
     * @param orderId
     * @return
     */
    private OrderDetail saveOrderDetailByPorductIdAndQuantity(String productId, Long quantity, String orderId) {
        ProductInfo productInfo = productService.findById(productId);
        OrderDetail orderDetail = new OrderDetail();
        BeanUtils.copyProperties(productInfo, orderDetail);
        orderDetail.setOrderId(orderId);
        orderDetail.setProductQuantity(quantity);
        orderDetailRepository.save(orderDetail);
        return orderDetail;
    }

    /**
     * 分页查找所有order
     * @param openId
     * @param pageable
     * @return
     */
    public List<OrderMasterDTO> findAllByPage(String openId, Pageable pageable) {
        List<OrderMaster> all = orderMasterRepository.findOrderMastersByBuyerOpenidOrderByCreateTimeDesc(openId, pageable);
        return ConvertUtil.convertToListMasterDto(all);
    }

    /**
     * 取消订单
     * 只有没有异常 就是成功。
     * @param orderId
     * @param buyerOpenid
     */
    @Transactional
    public boolean cancel(String orderId, String buyerOpenid){

        OrderMaster orderMaster = findByOrderIdAndBuyerOpenid(orderId, buyerOpenid);
        if ( orderMaster == null) {
            throw new SellException(ResponseEnum.PRODUCT_NOT_FOUND);
        }
        //如果现在不能取消。就返回false
        if(!OrderStatusEnum.findByCode(orderMaster.getOrderStatus()).isCanancel()){
            log.info("id为[{}]的订单已取消[{}]",orderId);
            return false;
        }
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());

        if (orderMaster.getPayStatus() == PayStatusEnum.SUCCESS.getCode()) {
            //todo  如果支付了。则退款
            orderMaster.setPayStatus(PayStatusEnum.REFUNDING.getCode());
        }

        //查找子订单
        List<OrderDetail> orderDetails = findByOrderId(orderId);
        orderDetails.forEach((OrderDetail od) ->{
            //查找每个子订单上的产品。修改产品状态和库存
            ProductInfo productInfo = productService.findById(od.getProductId());
            productService.upOrDown(productInfo,productInfo.getProductStock() + od.getProductQuantity());
        });
        return true;
    }

    //todo 测试可能抛出的异常
    public OrderMaster findByOrderIdAndBuyerOpenid(String orderId, String buyerOpenid){
        return orderMasterRepository.findByOrderIdAndBuyerOpenid(orderId, buyerOpenid);
    }

    /**
     * 根据订单查找订单详情列表
     * @param orderId
     * @return
     */
    private List<OrderDetail> findByOrderId(String orderId){
        return  orderDetailRepository.findByOrderId(orderId);
    }

    public OrderMasterDTO findOrderAll(String openid, String orderId) {
        OrderMaster orderMaster = findByOrderIdAndBuyerOpenid(orderId, openid);
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        BeanUtils.copyProperties(orderMaster, orderMasterDTO);

        List<OrderDetailDTO> orderDetailDTOS = new ArrayList<>();
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(orderId);
        for (OrderDetail orderDetail1 :orderDetails){
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            BeanUtils.copyProperties(orderDetail1, orderDetailDTO);
            orderDetailDTOS.add(orderDetailDTO);
        }
        orderMasterDTO.setOrderDetailList(orderDetailDTOS);
        return orderMasterDTO;
    }


}

