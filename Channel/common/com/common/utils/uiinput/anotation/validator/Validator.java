package com.common.utils.uiinput.anotation.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.common.utils.uiinput.anotation.other.Filter;

/**
 * 字段验证过滤器，目前支持 以下几种缺省filter,这几种过滤器是目前前台支持的
 */
// filter: notempty: 非空检查, 默认空值为''或null
// length：长度检查,必须指定min和max
// valrange: 数值大小检查,必须指定min和max
// regexp： 自定义正则检查，必须指定exp
// equal： 与页面另一元素值是否相等，必须指定target
// email： 邮件格式检查
// phone： 手机号或座机号检查
// number： 纯数字检查
// char：纯字符检查
// charnumber： 字符或数字
// double： 小数检查
// user: 自定义验证js函数， 此时fn不能为空
/*
 * msg: 指定检查出错时的提示信息 attr: 若指定了该值，则前台jquery取值时取自定义属性，否则取value
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Validator {

	/**
	 * 字段验证过滤器
	 * 
	 * @return
	 */
	public Filter filter();

	/**
	 * 字段验证不成功时提示信息
	 * 
	 * @return
	 */
	public String msg() default "";

	/**
	 * length验证长度时长度>=min <=max
	 * 
	 * @return
	 */
	public int min() default 0;

	/**
	 * length验证长度时长度>=min <=max
	 * 
	 * @return
	 */
	public int max() default 0;

	/**
	 * equal验证时，对比的元素id，常用于再次输入密码验证
	 * 
	 * @return
	 */
	public ValidatorTarget target() default @ValidatorTarget(id = "");

	/**
	 * regexp自定义正则验证时的正则表达式
	 * 
	 * @return
	 */
	public String exp() default "";

	/**
	 * 自定义验证js函数,该函数必须返回true/false,true表示验证成功，false表示验证失败
	 * 
	 * @return
	 */
	public String fn() default "";
}
