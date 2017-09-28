/**
 * 
 */
package com.compucom.serviceops.tsheetsapi.service;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * NOTE this is just a test....
 * @author Mark Gottschling on Oct 28, 2016
 *
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface BaseUrl {
	String value() default "";
}
