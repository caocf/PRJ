package com.channel.modelutil.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于自动生成新增编辑的前台页面,输入验证信息
 * 
 * @author DJ
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface UiInputValidator {
	public Validator[] value();
}
