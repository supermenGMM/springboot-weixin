package com.mm.controller;

import com.mm.pojo.SellerInfo;
import com.mm.service.SellerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SellerUserController {
    @Autowired
    private SellerInfoService sellerInfoService;
    public void loginin(String openid) {
        //1.获取openid，和数据库做匹配。
        SellerInfo byOpenid = sellerInfoService.findByOpenid(openid);
        //2.设置token至redis

        //3.设置token至cookie
    }

    public void loginOff() {

    }
}
