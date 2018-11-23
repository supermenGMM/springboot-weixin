package com.mm.service;

import com.mm.exception.SellException;
import com.mm.myenum.ResponseEnum;
import com.mm.pojo.ProductInfo;
import com.mm.repository.ProductInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
@Service
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
}
