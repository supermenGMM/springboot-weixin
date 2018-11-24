package com.mm.util;

import com.mm.dto.OrderMasterDTO;
import com.mm.pojo.OrderMaster;

import java.util.ArrayList;
import java.util.List;

public class ConvertUtil {
    public static List<OrderMasterDTO> convertToListMasterDto(List<OrderMaster> list) {
        List<OrderMasterDTO> orderMasterDTOS = new ArrayList<>();
        list.forEach(om -> { orderMasterDTOS.add(om.convertToOrderMasterDto()); });
        return orderMasterDTOS;
    }
}
