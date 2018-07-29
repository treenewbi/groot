package com.huangwu.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Package: com.huangwu.validator
 * @Author: huangwu
 * @Date: 2018/5/25 9:14
 * @Description:
 * @LastModify:
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {IsPathValidator.class })
public @interface IsPath {

    boolean required() default true;

    String message() default "etcd的path格式错误,只能包含'-'、'_'特殊符号";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
