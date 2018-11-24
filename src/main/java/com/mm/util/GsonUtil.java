package com.mm.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.mm.dto.StockDTO;
import com.mm.exception.SellException;
import com.mm.myenum.ResponseEnum;
import sun.applet.Main;

import java.lang.reflect.Type;
import java.util.List;

public class GsonUtil {

    public static <T> List<T> jsonToList(String json, Type type){
        List<T> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(json, type);

        }catch (JsonSyntaxException e){
            throw new SellException(ResponseEnum.REQUEST_CONTENT_ERROR);
        }
        return list;
    }



}
