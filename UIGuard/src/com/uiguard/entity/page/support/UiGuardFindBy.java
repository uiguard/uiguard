package com.uiguard.entity.page.support;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface UiGuardFindBy {

	String id() default "";
	
	String name() default "";
	
	String css() default ""; 
	
	String xpath() default ""; 
	
	String className() default ""; 
	
	String linkText() default ""; 
	
	String tagName() default ""; 
	
	String partialLinkText() default ""; 
	
	String dom() default "";
	
	String iframePath() default "";
	
	boolean usePropertiesValue() default false;	
	
}