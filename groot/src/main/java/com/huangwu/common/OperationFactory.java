package com.huangwu.common;

import com.huangwu.exception.GlobalException;
import com.huangwu.service.OperationRoute;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * UserOperation工厂类
 *
 * @Package: com.huangwu.common
 * @Author: huangwu
 * @Date: 2018/7/16 13:01
 * @Description:
 * @LastModify:
 */
@Component
public class OperationFactory {

    @Resource
    public ApplicationContext applicationContext;

    public <T extends OperationRoute> T getOperation(Class<T> tClass) throws GlobalException {
        T bean = applicationContext.getBean(tClass);
        if (bean == null) {
            throw new GlobalException(ErrorCode.NO_UNIQUE_BEAN_DEFINITION);
        }
        return bean;
    }

}
