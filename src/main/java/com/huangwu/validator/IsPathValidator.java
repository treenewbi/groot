package com.huangwu.validator;

import com.huangwu.util.ValidatorUtil;
import com.huangwu.util.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Package: com.huangwu.validator
 * @Author: huangwu
 * @Date: 2018/5/25 9:15
 * @Description:
 * @LastModify:
 */
public class IsPathValidator implements ConstraintValidator<IsPath, String> {
    private boolean required = false;

    public void initialize(IsPath constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (required) {
            return ValidatorUtil.isPath(value);
        }
        return true;
    }
}
