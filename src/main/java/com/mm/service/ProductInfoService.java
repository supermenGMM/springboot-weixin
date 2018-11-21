package com.mm.service;

import com.mm.pojo.ProductInfo;
import com.mm.repository.ProductInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
