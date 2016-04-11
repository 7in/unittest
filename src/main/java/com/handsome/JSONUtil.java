package com.handsome;

import com.alibaba.common.lang.StringUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

/**
 * Created by jiayu.shenjy on 2016/2/1.
 */
public class JSONUtil {
    /**
     * 将对象转换成json字符串
     * @param object
     * @return
     */
    public static String toJsonString(Object object) {
        return object == null ? "" : JSON.toJSONString(object);
    }

    /**
     * 将字符串转换成json对象
     * @param string
     * @return
     */
    public static JSONObject toJSONObj(String string) {
        return StringUtil.isBlank(string) ? null : JSON.parseObject(string);
    }
    /**
     * 将字符串转换成jsonArray对象
     * @param string
     * @return
     */
    public static JSONArray toJSONArray(String string) {
        return StringUtil.isBlank(string) ? null : JSON.parseArray(string);
    }
    /**
     * json串转换成pojo类
     * @param json
     * @param pojoClass
     * @param <T>
     * @return
     */
    public  static <T> T jsonToObject(String json, Class<T> pojoClass) {
        Object pojo;
        pojo = JSON.parseObject(json, pojoClass);
        return (T) pojo;
    }

    /**
     * json串转换成pojo类
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public  static <T> T jsonToObject(String json, TypeReference<T> type) {
        Object pojo;
        pojo = JSON.parseObject(json, type);
        return (T) pojo;
    }
}
