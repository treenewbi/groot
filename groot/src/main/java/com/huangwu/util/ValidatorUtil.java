package com.huangwu.util;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {

    private static final Pattern MOBILE_PATTERN = Pattern.compile("1\\d{10}");
    private static final Pattern ETCD_PATH_PATTERN = Pattern.compile("(\\/([0-9a-zA-Z_-]+))+(\\/?)");
    private static final Pattern ETCD_ADDRESS = Pattern.compile("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}");


    public static boolean isMobile(String src) {
        if (StringUtils.isEmpty(src)) {
            return false;
        }
        Matcher m = MOBILE_PATTERN.matcher(src);
        return m.matches();
    }

    public static boolean isAddress(String src) {
        if (StringUtils.isEmpty(src) || src.indexOf(":") == -1) {
            return false;
        }
        String p = src.substring(src.lastIndexOf(":") + 1, src.length());
        int port = Integer.valueOf(p);
        src = src.substring(0,src.indexOf(":"));
        if (port > 65536 || port < 0) {
            return false;
        }
        Matcher m = ETCD_ADDRESS.matcher(src);
        return m.matches();
    }

    public static boolean isPath(String src) {
        if (StringUtils.isEmpty(src)) {
            return false;
        }
        if ("/".equals(src)) {
            return true;
        }
        Matcher m = ETCD_PATH_PATTERN.matcher(src);
        return m.matches();
    }

    public static void main(String[] args) {
        System.out.println(isAddress("192.168.0.99:5000"));
//        System.out.println(isAddress("19"));
    }
}
