package com.mm.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mm.exception.SellException;
import com.mm.myenum.ResponseEnum;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GsonUtil {
    private GsonUtil() {
    }

    public static <T> List<T> jsonToList(String json, Type type){
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, type);

        }catch (JsonSyntaxException e){
            throw new SellException(ResponseEnum.REQUEST_CONTENT_ERROR);
        }
    }



}
