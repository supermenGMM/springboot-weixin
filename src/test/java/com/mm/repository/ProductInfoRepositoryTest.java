package com.mm.repository;

import com.mm.myenum.ProductStatusEnum;
import com.mm.pojo.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductInfoRepositoryTest {
    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Rollback(value = true)
//    @Transactional
    @Test
    public void test() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductName("雪花");
        productInfo.setProductDescription("勇闯天涯");
        productInfo.setProductPrice(new BigDecimal(3.4d));
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo.setProductIcon("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542733358602&di=7021badd77df2b8431fd27c62055a76f&imgtype=0&src=http%3A%2F%2Fimg001.hc360.cn%2Fm6%2FM04%2F33%2F6C%2FwKhQoVZEP52ETej8AAAAAIJZwv4986.jpg");
        productInfo.setProductStock(99999L);
        productInfo.setCategoryType(6);
        productInfoRepository.save(productInfo);

    }

    @Test
    @Transactional
    public void update() {
        Optional<ProductInfo> byId = productInfoRepository.findById("4028b88167317ef60167317f048c0000");
        byId.get().setProductStock(1120L);
        ProductInfo save = productInfoRepository.save(byId.get());
        Assert.assertEquals(new Long(1120),save.getProductStock());
    }
}