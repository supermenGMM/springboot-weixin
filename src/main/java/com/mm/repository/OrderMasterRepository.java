package com.mm.repository;

import com.mm.pojo.OrderMaster;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String>{
    List<OrderMaster> findOrderMastersByBuyerOpenidOrderByCreateTimeDesc(String buyerOpenId, Pageable pageable);
}

