package com.utopia.demo.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GsonUtils {
    private static final Gson gson;

    static {
        gson = new GsonBuilder().create();
    }

    public static String toJson(Object src) {
        return gson.toJson(src);
    }

    public static  <T> T toObject(String json, Class<T> claxx) {
        T t;
        try {
            t = gson.fromJson(json, claxx);
        } catch (Exception e) {
            t = null;
            e.printStackTrace();
        }
        return t;
    }

    public static <T> List<T> toList(String json, Class<T[]> claxx) {
        T[] list = null;

        try {
            list = gson.fromJson(json, claxx);
        } catch (Exception ignored) {

        }

        if(list == null || list.length == 0){
            return new ArrayList<>();
        }
        return Arrays.asList(list);
    }
}
