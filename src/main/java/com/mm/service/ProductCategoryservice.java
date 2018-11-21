package com.mm.service;

import com.mm.pojo.ProductCategory;
import com.mm.repository.ProductCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class ProductCategoryservice {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    public List<ProductCategory> findByCategoryIdIn(List<Integer> ids){
        return productCategoryRepository.findByCategoryIdIn(ids);
    }
}
