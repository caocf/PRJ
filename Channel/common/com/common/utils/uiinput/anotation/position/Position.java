package com.common.utils.uiinput.anotation.position;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.common.utils.uiinput.anotation.other.LineWidth;

/**
 * 字段显示时的位置相关信息
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Position {
	// 分组
	public int group() default 0;

	// 组名字
	public String groupname() default "";

	// 分组排序
	public int order() default 0;

	// 用于自定义排序
	public int suborder() default 0;

	// 该字段表示该属性占有一行中的多少，只能取1或0.5，1表示占一行，0.5表示占一半
	public LineWidth linewidth() default LineWidth.HALF;
}
