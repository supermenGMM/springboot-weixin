package com.mm.controller;

import com.mm.pojo.ProductCategory;
import com.mm.pojo.ProductInfo;
import com.mm.service.ProductCategoryservice;
import com.mm.service.ProductInfoService;
import com.mm.vo.ProductInfoVo;
import com.mm.vo.ProductListVo;
import com.mm.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        List<ProductCategory> categoryList = productCategoryservice.findByCategoryIdIn(categoryIds);

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
}
