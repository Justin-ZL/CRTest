/**
 * deepwisdom
 */

package com.deepwisdom.common.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 *
 * @author justin zhanglei@fuzhi.ai
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	String value() default "";
}
