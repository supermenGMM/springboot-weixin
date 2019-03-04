package com.mm.service;

import com.mm.exception.SellException;
import com.mm.myenum.ResponseEnum;
import com.mm.pojo.ProductInfo;
import com.mm.repository.ProductInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductInfoRepository productInfoRepository;

    public ProductInfo findById(String productId) {
        try {
            return  productInfoRepository.findById(productId).get();
        } catch (InvalidDataAccessApiUsageException e) {//因为controller已经校验了字段的非空和正确性。这里findById不会报IllegalArgumentException异常。
            System.out.println("-----");
            throw new SellException(ResponseEnum.REQUEST_CONTENT_ERROR);
        }catch (NoSuchElementException e){
            throw new SellException(ResponseEnum.PRODUCT_NOT_FOUND);
        }
    }

    /**
     * 根据数量，减掉库存
     * @param productId 商品id
     * @param quantity 商品数量
     */
    public Long reduceStock(String productId, Long quantity){
        ProductInfo productInfo = this.findById(productId);
        Long newStock = productInfo.getProductStock()- quantity;
        upOrDown(productInfo, newStock);
        productInfoRepository.saveAndFlush(productInfo);
        return newStock;
    }

    /**
     * 根据传的库存修改商品的数量和状态
     * @param productInfo
     * @param newProductStock
     */
    public void upOrDown(ProductInfo productInfo, Long newProductStock) {
        log.info("开始更新产品状态和库存。产品的id为[{}],新库存为为[{}]");
        if(newProductStock<0){
            throw new SellException(ResponseEnum.PRODUCT_STOCK_NOENOUGH);
        } else if(newProductStock == 0){
            productInfo.down();
        } else {
            productInfo.up(newProductStock);
        }
        log.info("更新产品状态和库存结束。产品的id为[{}],新库存为为[{}]");
    }
}
