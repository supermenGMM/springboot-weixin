package com.mm.repository;

import com.mm.pojo.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String>{
    //根据categoryId查询

    List<ProductInfo>  findByCategoryType(Long categoryType);
}

