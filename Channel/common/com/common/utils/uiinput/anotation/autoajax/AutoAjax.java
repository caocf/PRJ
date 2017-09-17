package com.common.utils.uiinput.anotation.autoajax;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface AutoAjax {
	/********************** 表单提交相关 ******************/
	public boolean autoAjax() default true;

	// 表单提交时提交名
	public String autoAjaxName() default "";

	// 表单提交时，如果没有输入或输入为""，则自动以此属性进行提交
	public String autoAjaxDftVal() default "";
}
