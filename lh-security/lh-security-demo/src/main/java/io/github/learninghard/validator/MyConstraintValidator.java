package io.github.learninghard.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: Learning Hard
 * \* Date: 2019-08-13
 * \* Time: 22:21
 * \* To change this template use File | Settings | File Templates.
 * \* Description: 自定义注解的约束
 * \
 */
public class MyConstraintValidator implements ConstraintValidator<MyConstraint,Object> {
    /**
     *  这里可以注入任意服务
     */


    /**
     * 初始化方法
     * @param constraintAnnotation
     */
    @Override
    public void initialize(MyConstraint constraintAnnotation) {
        System.out.println("自定义注解初始化");
    }

    /**
     * 校验方法
     * @param o
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("校验器拿到的参数是"+o);
        return false;
    }
}
