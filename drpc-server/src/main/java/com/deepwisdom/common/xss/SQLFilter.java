package com.deepwisdom.common.xss;

import com.deepwisdom.common.enums.ExceptionCodeEnum;
import com.deepwisdom.common.exception.RRException;
import org.apache.commons.lang3.StringUtils;

/**
* @ClassName: SQLFilter.java
* @Author: justin(zhanglei@fuzhi.ai)
* @Date: 2021/8/2 16:28
* @Description: SQL过滤
* @Version: 1.0
*/
public class SQLFilter {

    /**
     * SQL注入过滤
     * @param str  待验证的字符串
     */
    public static String sqlInject(String str){
        if(StringUtils.isBlank(str)){
            return null;
        }
        //去掉'|"|;|\字符
        str = StringUtils.replace(str, "'", "");
        str = StringUtils.replace(str, "\"", "");
        str = StringUtils.replace(str, ";", "");
        str = StringUtils.replace(str, "\\", "");

        //转换成小写
        str = str.toLowerCase();

        //非法字符
        String[] keywords = {"master", "truncate", "insert", "select", "delete", "update", "declare", "alter", "drop"};

        //判断是否包含非法字符
        for(String keyword : keywords){
            if(str.indexOf(keyword) != -1){
                throw new RRException(ExceptionCodeEnum.MYSQL_SQL_INVALID_STRING_EXCEPTION.getMsg(), ExceptionCodeEnum.MYSQL_SQL_INVALID_STRING_EXCEPTION.getCode());
            }
        }

        return str;
    }
}
