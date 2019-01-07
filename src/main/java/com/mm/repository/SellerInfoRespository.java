package com.mm.repository;

import com.mm.pojo.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerInfoRespository  extends JpaRepository<SellerInfo,String>{
    SellerInfo findByOpenid(String openId);

}
