package com.huangwu.util;

import java.util.Collection;

/**
 * 集合工具类
 *
 * @Package: com.huangwu.util
 * @Author: huangwu
 * @Date: 2018/5/19 12:18
 * @Description:
 * @LastModify:
 */
public class CollectionHelper {

    public static String collectionToString(Object value, String separator) {
        if (value == null)
            return "";
        StringBuffer sb = new StringBuffer();
        if (value instanceof Object[]) {
            for (Object o : (Object[]) value) {
                if (sb.length() > 0)
                    sb.append(separator);
                sb.append(o);
            }
        } else if (value instanceof Collection<?>) {
            for (Object o : (Collection<?>) value) {
                if (sb.length() > 0)
                    sb.append(separator);
                sb.append(o);
            }
        } else
            sb.append(value);
        return sb.toString();
    }
}
