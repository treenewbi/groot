package com.huangwu.util;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * @Package: com.huangwu.util
 * @Author: huangwu
 * @Date: 2018/5/19 13:01
 * @Description:
 * @LastModify:
 */
public class StringHelper {

    private static final char[] ops = new char[]{'+', '-', '*'};
    private static Random random = new Random();

    /**
     * 格式化时间
     *
     * @param time   时间
     * @param format 时间格式
     * @return 时间字串
     */
    public static String formatDateTime(Date time, String format) {
        if (time == null)
            return "";
        SimpleDateFormat f = new SimpleDateFormat(format);
        return f.format(time);
    }

    /**
     * 当ip端口前面没有http://时则默认拼接一个http://字串
     *
     * @param ipport
     * @return
     */
    public static String buildEtcdUrl(String ipport) {
        if (!ipport.startsWith("http")) {
            ipport = "http://" + ipport;
        }
        return ipport;
    }

    /**
     * 返回ip:port形式的字串
     *
     * @param url
     * @return
     */
    public static String buildAddressUrl(String url) {
        if (url != null && !"".equals(url)) {
            return url.startsWith("http://") ? url.replaceFirst("http://", "") : url;
        }
        return null;
    }

    /**
     * 生成一个UUID字串
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 生成4位随机数字
     *
     * @return
     */
    public static String getFourRandom() {
        Random random = new Random();
        String fourRandom = random.nextInt(10000) + "";
        int randLength = fourRandom.length();
        if (randLength < 4) {
            for (int i = 1; i <= 4 - randLength; i++)
                fourRandom = "0" + fourRandom;
        }
        return fourRandom;
    }

    /**
     * 随机生成一个形如 3+4*6的字串
     *
     * @return
     */
    public static String getVerifyCode() {
        int num1 = random.nextInt(10);
        int num2 = random.nextInt(10);
        int num3 = random.nextInt(10);
        char op1 = ops[random.nextInt(3)];
        char op2 = ops[random.nextInt(3)];
        String verifyCode = "" + num1 + op1 + num2 + op2 + num3;
        int value = num1 + op1;
        return verifyCode;
    }

    /**
     * 计算验证码的值
     *
     * @param exp
     * @return
     */
    public static int calculateVarifyCode(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer) engine.eval(exp);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

//    public static void main(String[] args) {
//        getVerifyCode();
//    }
}
