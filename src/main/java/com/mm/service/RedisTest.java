package com.mm.service;


import com.mm.pojo.ProductInfo;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RedisTest {
    private static String key = "product";

    public static String getKey() {
        return key;
    }

    /**
     * 测试添加缓存
     * @param id
     * @return
     */
    @Cacheable(value = "testMap" ,key="T(com.mm.service.RedisTest).getKey()+'.'+#targetClass.getName()+#methodName+#id",
        unless="#result==null&&#result.isEmpty()&&#result.size()==0")//unless当不是某个情况下
    public List<ProductInfo> findList(String id) {
        System.out.println("执行方法====");
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(id);
        productInfo.setProductName("嘿嘿");
        productInfo.setCategoryType(1);
        List<ProductInfo> list = new ArrayList<>();
        list.add(productInfo);
        return list;
    }

    /**
     * 测试添加缓存
     * @param id
     * @return
     */
    @Cacheable(value =  "testMap" ,key="T(com.mm.service.RedisTest).getKey()+'.'+targetClass.getName()+#id",
    unless="#result==null")//unless当不是某个情况下
    public ProductInfo findById(String id) {
        System.out.println("执行方法====");
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(id);
        productInfo.setProductName("嘿嘿");
        productInfo.setCategoryType(1);
        return null;
    }

    /**
     * 清楚缓存
     * @param id
     */
    @CacheEvict(value =  "testMap" ,key = "T(com.mm.service.RedisTest).getKey()+'.'+targetClass.getName()+#id")
    public void delProduct(String id) {
        System.out.println("删除对象");
        //update 或者删除这个对象
    }

    /**
     * 更新缓存
     * @param id
     */
    @CachePut(value =  "testMap" ,key = "T(com.mm.service.RedisTest).getKey()+'.'+targetClass.getName()+#id")
    public ProductInfo updateProduct(String id) {

        System.out.println("更新对象");
        //update 或者删除这个对象
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(id);
        productInfo.setProductName("呵呵");
        return productInfo;
    }
}
