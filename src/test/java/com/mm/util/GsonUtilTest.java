package com.mm.util;

import com.google.gson.reflect.TypeToken;
import com.mm.dto.StockDTO;
import com.mm.form.ProductForm;
import org.junit.Test;

import java.util.List;

public class GsonUtilTest {
    @Test
    public void test() {
        List<StockDTO> stockDTOS = GsonUtil.jsonToList("[{\"productId\":\"2\",\"productQuantity\":1},{\"productId\":\"3\"" +
                ",\"productQuantity\":1},{\"productId\":\"1\",\"productQuantity\":1},{\"productId\":" +
                "\"4\",\"productQuantity\":1}]",
            new TypeToken<List<StockDTO>>(){}.getType()
        );
        System.out.println(stockDTOS.toString());
        System.out.println(stockDTOS.get(0).getClass());
    }


}