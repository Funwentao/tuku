package com.thinkgem.jeesite.modules.basic.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by fandaz on 2018/1/16
 */
public class JsonUtil {
    public static String toJson(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}
