package com.huangwu.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置文件读取工具类
 *
 * @Package: com.huangwu.util
 * @Author: huangwu
 * @Date: 2018/5/24 9:48
 * @Description:
 * @LastModify:
 */
public class PropertyUtil {
    private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
    private static Properties props;

    static {
        loadApplicationProps();
    }

    synchronized static private void loadApplicationProps() {
        logger.info("开始加载properties文件内容.......");
        props = new Properties();
        InputStream in = null;
        try {
            logger.info("hello......");
            in = PropertyUtil.class.getClassLoader().getResourceAsStream("application.properties");
            if (in == null) {
                logger.error("未找到配置文件：application.properties");
            }
            props.load(in);
        } catch (FileNotFoundException e) {
            logger.error("application.properties文件未找到");
        } catch (IOException e) {
            logger.error("加载application.properties出现IOException");
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("application.properties文件流关闭出现异常");
            }
        }
        logger.info("加载properties文件内容完成...........");
    }

    public static String getProperty(String key) {
        if (null == props) {
            loadApplicationProps();
        }
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        if (null == props) {
            loadApplicationProps();
        }
        return props.getProperty(key, defaultValue);
    }
}
