package com.deepwisdom.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.BooleanConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ConvertUtil {
    private static final Logger LOG = LoggerFactory.getLogger(ConvertUtil.class);
    /**
     * 将Map转换为对象
     * @param paramMap
     * @param cls
     * @return
     */
    public static <T> T parseMap2Object(Map<String, Object> paramMap, Class<T> cls) {

        return JSONObject.parseObject(JSONObject.toJSONString(paramMap), cls);
    }


    public ConvertUtil() {
    }

    public static <FROM, TO> TO po2dto(FROM po, Class<TO> clazz) {
        return converte(po, clazz);
    }

    public static <FROM, TO> List<TO> po2dto(List<FROM> poList, Class<TO> clazz) {
        return converte(poList, clazz);
    }

    public static <FROM, TO> TO dto2po(FROM dto, Class<TO> clazz) {
        return converte(dto, clazz);
    }

    public static <FROM, TO> List<TO> dto2po(List<FROM> dtoList, Class<TO> clazz) {
        return converte(dtoList, clazz);
    }

    private static <FROM, TO> TO converte(FROM from, Class<TO> clazz) {
        TO to = null;
        if (null != from) {
            try {
                to = clazz.newInstance();
                ConvertUtils.register(new DateConverter((Object)null), Date.class);
                ConvertUtils.register(new LongConverter((Object)null), Long.class);
                ConvertUtils.register(new ShortConverter((Object)null), Short.class);
                ConvertUtils.register(new IntegerConverter((Object)null), Integer.class);
                ConvertUtils.register(new DoubleConverter((Object)null), Double.class);
                ConvertUtils.register(new BigDecimalConverter((Object)null), BigDecimal.class);
                ConvertUtils.register(new BooleanConverter((Object)null), Boolean.class);
                BeanUtils.copyProperties(from, to);
            } catch (IllegalAccessException var4) {
                LOG.error("IllegalAccessException: ", var4.getMessage());
            } catch (InstantiationException var5) {
                LOG.error("InstantiationException: ", var5.getMessage());
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return to;
    }

    private static <FROM, TO> List<TO> converte(List<FROM> fromList, Class<TO> clazz) {
        List<TO> toList = new ArrayList();
        if (null != fromList && !fromList.isEmpty()) {
            Iterator var3 = fromList.iterator();

            while(var3.hasNext()) {
                FROM from = (FROM) var3.next();
                TO to = converte(from, clazz);
                if (null != to) {
                    toList.add(to);
                }
            }
        }

        return toList;
    }

    public static List<String> Long2String(List<Long> list) {
        if (null == list) {
            return null;
        } else {
            List<String> stringList = new ArrayList();
            if (!list.isEmpty()) {
                Iterator var2 = list.iterator();

                while(var2.hasNext()) {
                    Long l = (Long)var2.next();
                    stringList.add(String.valueOf(l));
                }
            }

            return stringList;
        }
    }

    public static Map<String, String> Object2Map(Object obj) {
        Map<String, Object> map = (Map) JSON.parseObject(JSON.toJSONString(obj), Map.class);
        Map<String, String> resultMap = new HashMap();
        Iterator var3 = map.entrySet().iterator();

        while(var3.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry)var3.next();
            if (null != entry.getValue()) {
                resultMap.put(entry.getKey(), entry.getValue().toString());
            }
        }

        return resultMap;
    }

    public static <T> T Map2Object(Map<?, ?> map, Class<T> clazz) {
        if (map == null) {
            return null;
        } else {
            Object obj = null;

            try {
                obj = clazz.newInstance();
                ConvertUtils.register(new DateConverter((Object)null), Date.class);
                ConvertUtils.register(new LongConverter((Object)null), Long.class);
                ConvertUtils.register(new ShortConverter((Object)null), Short.class);
                ConvertUtils.register(new IntegerConverter((Object)null), Integer.class);
                ConvertUtils.register(new DoubleConverter((Object)null), Double.class);
                ConvertUtils.register(new BigDecimalConverter((Object)null), BigDecimal.class);
                ConvertUtils.register(new BooleanConverter((Object)null), Boolean.class);
                org.apache.commons.beanutils.BeanUtils.populate(obj, (Map<String, ? extends Object>) map);
            } catch (InstantiationException var4) {
                var4.printStackTrace();
            } catch (IllegalAccessException var5) {
                LOG.error("IllegalAccessException: ", var5.getMessage());
            } catch (InvocationTargetException var6) {
                LOG.error("InvocationTargetException: ", var6.getMessage());
            }

            return (T) obj;
        }
    }

}

