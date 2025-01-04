package org.example.springboot.common.common.annotation;

import java.lang.annotation.*;

/**
 * 允许匿名访问
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Anonymous {
}
