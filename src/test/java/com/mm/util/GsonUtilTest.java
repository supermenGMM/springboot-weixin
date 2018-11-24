package com.mm.util;

import com.mm.dto.StockDTO;
import org.junit.Test;

import java.util.List;

public class GsonUtilTest {
    @Test
    public void test() {
        List<StockDTO> stockDTOS = GsonUtil.jsonToList("[{\"productId\":\"2\",\"productQuantity\":1},{\"productId\":\"3\"" +
                ",\"productQuantity\":1},{\"productId\":\"1\",\"productQuantity\":1},{\"productId\":" +
                "\"4\",\"productQuantity\":1}]",
            StockDTO.class
        );
        System.out.println(stockDTOS.toString());
    }


}