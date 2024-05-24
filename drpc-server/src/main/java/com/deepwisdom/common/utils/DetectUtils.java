package com.deepwisdom.common.utils;

import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 检测 -工具类
 */
@Component
public class DetectUtils {

    /**
     * 检测字符串中的分隔符
     * 逻辑：
     * 1、如果包含\t，则直接返回\t(如果字符串中分隔符不是\t，但是却包含\t，这种场景目前不支持)
     * 2、对常见分隔符:逗号、分号、冒号、空格进行支持
     * 3、扫描字符串的每一个字符，将逗号、分号、冒号、空格作为map的key进行统计，如果map为空，则直接返回\t
     * 4、对map的value进行从大到小排序
     * 5、返回第一个map的key
     * @param str
     * @return
     */
    public String detect_delimiter(String str){
        if(str.contains("\t") || str.isEmpty()){
            return "\t";
        }
        Map<String,Integer> map = new HashMap<>();

        /**
         * 先入map
         * commaNum 逗号数量
         * semicolonNum 分号数量
         * colonNum 冒号数量
         * spaceNum 空格数量
         */
        Integer commaNum = 0,semicolonNum = 0,colonNum = 0,spaceNum = 0;
        for(Character c: str.toCharArray()){
            if(c == ','){
                map.put(",",commaNum++);
            }else if(c == ';'){
                map.put(";",semicolonNum++);
            }else if(c == ':'){
                map.put(":",colonNum++);
            }else if(c == ' '){
                map.put(" ",spaceNum++);
            }
        }

        if(map.isEmpty()){
            return "\t";
        }

        //从大到小排序
        List<Map.Entry<String, Integer>> sortList = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(sortList, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });

        return sortList.get(0).getKey();
    }
}
