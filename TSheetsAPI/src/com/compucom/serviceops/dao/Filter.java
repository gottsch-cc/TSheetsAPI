/**
 * 
 */
package com.compucom.serviceops.dao;

/**
 * @author Mark Gottschling on Apr 14, 2016
 *
 */
public @interface Filter {
	String filterName() default "";
	String propertyName() default "";
}

