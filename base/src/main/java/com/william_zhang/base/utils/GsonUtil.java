package com.william_zhang.base.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Created by william_zhang on 2018/4/29.
 */

public class GsonUtil {
    /**
     * @param json
     * @param classOfT
     * @param <T>
     * @return
     * @throws JsonSyntaxException
     */
    public static <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        return new Gson().fromJson(json, classOfT);
    }

    public static String toJson(Object src) {
        return new Gson().toJson(src);
    }


}
