package com.domain.app.security;

/**
 * User: ybli
 * Date: 13-12-17
 * Description: 用于标注需要登录检查的spring-mvc方法
 *
 */
@java.lang.annotation.Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.TYPE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface Login {
    ResultTypeEnum value() default ResultTypeEnum.page;
}