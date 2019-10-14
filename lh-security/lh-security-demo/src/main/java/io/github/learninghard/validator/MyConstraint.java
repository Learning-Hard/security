package io.github.learninghard.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义校验器
 */
@Target({ElementType.METHOD,ElementType.FIELD})//注解的作用范围
@Retention(RetentionPolicy.RUNTIME) //运行时注解
@Constraint(validatedBy = MyConstraintValidator.class) //约束方法
public @interface MyConstraint {
    /**
     * 必须实现的三个方法
     * message()
     * groups()
     * payload()
     */

    /**
     * 返回校验失败的信息
     * @return
     */
    String message() default "{自定义校验失败}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
