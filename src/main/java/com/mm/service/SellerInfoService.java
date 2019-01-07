package com.mm.service;

import com.mm.pojo.SellerInfo;
import com.mm.repository.SellerInfoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerInfoService {
    @Autowired
    private SellerInfoRespository sellerInfoRespository;
    public SellerInfo findByOpenid(String openId) {
        return sellerInfoRespository.findByOpenid(openId);
    }
}
