package com.mm.repository;

import com.mm.pojo.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Long>{
    List<ProductCategory> findByCategoryTypeIn(List<Integer> ids);
}

