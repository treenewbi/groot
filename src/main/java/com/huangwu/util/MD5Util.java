package com.huangwu.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5工具类
 *
 * @Package: com.huangwu.util
 * @Author: huangwu
 * @Date: 2018/5/27 12:32
 * @Description:
 * @LastModify:
 */
public class MD5Util {

    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }

    /**
     * 根据前端传递的password进行加盐加密，salt为UUID字串，此处取盐的360位置字串，避免salt泄漏后通过碰撞解密
     *
     * @param formPass
     * @param salt
     * @return
     */
    public static String formPassToDBPass(String formPass, String salt) {
        String str = formPass + salt.charAt(3) + salt.charAt(6) + salt.charAt(0);
        return md5(str);
    }
}
