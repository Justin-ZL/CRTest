package com.deepwisdom.common.utils;

/**
* @ClassName: RedisKeys.java
* @Author: justin(zhanglei@fuzhi.ai)
* @Date: 2021/8/2 16:23
* @Description: Redis所有Keys
* @Version: 1.0
*/
public class RedisKeys {

    public static String getUserSessionKey(String key){
        return "tianji:admin:user:login:info:" + key;
    }

    public static String getUserPermissionsKey(String key){
        return "tianji:admin:user:permissions:info:" + key;
    }

    public static String getDatasetUploadStatusKey(String key){
        return "dataset:upload:status" + key;
    }

}
