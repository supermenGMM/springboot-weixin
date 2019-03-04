package com.mm.service;

import com.mm.pojo.ProductInfo;
import com.mm.repository.ProductInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductInfoService {
    @Autowired
    private ProductInfoRepository productInfoRepository;

    /*
    根据类目查找商品
     */
    public List<ProductInfo> findByCatetory(Long categoryId) {
        return productInfoRepository.findByCategoryType(categoryId);
    }
    public List<ProductInfo> findAll() {
        return productInfoRepository.findAll();
    }

    public ProductInfo findStock(String productId) {
        Optional<ProductInfo> optional = productInfoRepository.findById(productId);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    //减少库存。这里应该加锁
    public synchronized void reduceStock(String id, Long sellNum) {
        ProductInfo productInfo = findStock(id);
        productInfo.setProductStock(productInfo.getProductStock() - sellNum);
        productInfoRepository.save(productInfo);
    }
}
