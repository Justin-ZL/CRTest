/**
 * deepwisdom
 */

package com.deepwisdom.common.annotation;

import java.lang.annotation.*;

/**
 * 系统日志注解
 *
 * @author maobo@fuzhi.ai
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLogByGrpc {

	String value() default "";
}
