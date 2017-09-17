package com.common.utils.uiinput.anotation.inputtype;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.common.utils.uiinput.anotation.other.ControlType;
import com.common.utils.uiinput.anotation.other.KeyValue;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface InputType {
	// input -->输入框 textarea --> 输入区域 select --> 下拉选择框 date --> 时间
	public ControlType inputType() default ControlType.INPUT;

	// 当前此输入是从value中获取值，还是从attr自定义属性中获取
	public String valueAttr() default "";

	/*******************************************************************
	 * 对不同的inputtype需要进行设置的可选参数
	 ********************************************************************/
	// 文本输入区域行数
	public int textAreaRows() default 8;

	// 用户 可选择输入的默认值
	public KeyValue[] selKeyVals() default {};/* 其中该字段为输出字段，如果没有该字段，则该值会从下面两个字段中获取 */

	// 从json数据中加载默认值
	public String selKeyValJson() default "";

	// 从web前台中获取json数据
	public String selKeyValWebJson() default "";

	/**************************************************************/
	// 控件只读，如不可输入，不能选择等
	public boolean readonly() default false;

	// 控件提示信息
	public String hint() default "";

	/*****************
	 * 默认值可以直接指定，也可以从前台传值过来中取，也可以从后台相关的后台接口中获取
	 * *******************/
	// 控件默认值
	public String dftVal() default "";

	// 从前台传过来的数据中取默认值
	public String dftWebVal() default "";
}
