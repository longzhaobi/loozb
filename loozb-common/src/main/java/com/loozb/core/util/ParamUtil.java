package com.loozb.core.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 根据参数组装Map
 * @Author： 龙召碧
 * @Date: Created in 2017-2-26 15:00
 */
public class ParamUtil {

    public static Map<String, Object> getPageParams(String pages, String size, String keyWord, String orderBy) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("pages", pages);
        param.put("size", size);
        param.put("keyWord", keyWord);
        param.put("orderBy", orderBy);
        return param;
    }

    public static Map<String,Object> getMap() {
        return new HashMap<String, Object>();
    }
}
