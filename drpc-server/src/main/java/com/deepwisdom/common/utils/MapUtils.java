package com.deepwisdom.common.utils;

import java.util.HashMap;


/**
* @ClassName: MapUtils.java
* @Author: justin(zhanglei@fuzhi.ai)
* @Date: 2021/8/2 16:24
* @Description: Map工具类
* @Version: 1.0
*/
public class MapUtils extends HashMap<String, Object> {

    @Override
    public MapUtils put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
