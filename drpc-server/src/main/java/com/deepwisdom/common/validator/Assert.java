package com.deepwisdom.common.validator;

import com.deepwisdom.common.exception.RRException;
import org.apache.commons.lang3.StringUtils;

/**
* @ClassName: Assert.java
* @Author: justin(zhanglei@fuzhi.ai)
* @Date: 2021/8/2 16:27
* @Description: 数据校验
* @Version: 1.0
*/
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new RRException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new RRException(message);
        }
    }
}
