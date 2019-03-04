package com.mm.controller;

import com.mm.dto.OrderDto;
import com.mm.dto.StockDTO;
import com.mm.pojo.ProductCategory;
import com.mm.pojo.ProductInfo;
import com.mm.service.OrderService;
import com.mm.service.ProductCategoryservice;
import com.mm.service.ProductInfoService;
import com.mm.vo.ProductInfoVo;
import com.mm.vo.ProductListVo;
import com.mm.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/buyer/product")
@Slf4j
public class ProductInfoController {
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryservice productCategoryservice;

    @GetMapping(value = "/list",produces = "application/json;charset=utf-8")
    public ResponseVo findProductList() {

        List<ProductInfo> productInfos = productInfoService.findAll();
        List<Integer> categoryIds = productInfos.stream().map(ProductInfo::getCategoryType).distinct().collect(Collectors.toList());
        List<ProductCategory> categoryList = productCategoryservice.findByCategoryTypeIn(categoryIds);

        //data
        List<ProductListVo> data = new ArrayList<>();
        categoryList.stream().forEach(
            //获取同一类型的product集合
            c ->{
                ProductListVo productListVo=  new ProductListVo();
                productListVo.setCategoryName(c.getCategoryName());
                productListVo.setCategoryType(c.getCategoryType());
                productListVo.setProductList(new ArrayList<ProductInfoVo>());
                data.add(productListVo);
                //根据categoryType获取产品list
                productInfos.stream().filter(p -> c.getCategoryType() == p.getCategoryType()).forEach(
                    subp->{
                        //将product转换为productVo
                        ProductInfoVo productInfoVo = new ProductInfoVo();
                        BeanUtils.copyProperties(subp,productInfoVo);
                        productListVo.getProductList().add(productInfoVo);
                    }
                );
            }
        );
        //组装报文
        return ResponseVo.success(data);
    }

    /**
     * 产品秒杀。demo方法
     */
    @Autowired
    private OrderService orderService;
    @GetMapping(value = "/seckill/{productId}")
    public void seckill(@PathVariable String productId){
        //查询产品库存。
        ProductInfo productInfo = productInfoService.findStock(productId);
        //保存订单  //减少产品库存。
        if (productInfo.getProductStock() > 0) {
            OrderDto orderDto = new OrderDto();
            List<StockDTO> list = new ArrayList();
            StockDTO stockDTO = new StockDTO();
            stockDTO.setProductId(productId);
            stockDTO.setProductQuantity(1L);
            list.add(stockDTO);
            orderService.createOrder(orderDto);
        }
    }

    @GetMapping(value = "/queryStock/{productId}")
    public Long queryStock(@PathVariable String productId) {
        ProductInfo productInfo = productInfoService.findStock(productId);
        if (productInfo != null) {
            return productInfo.getProductStock();
        }
        return 0L;
    }
}
