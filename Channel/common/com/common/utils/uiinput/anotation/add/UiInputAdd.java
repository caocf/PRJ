package com.common.utils.uiinput.anotation.add;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.common.utils.uiinput.anotation.autoajax.AutoAjax;
import com.common.utils.uiinput.anotation.inputtype.InputType;
import com.common.utils.uiinput.anotation.position.Position;
import com.common.utils.uiinput.anotation.validator.Validator;

/**
 * 用于自动生成新增的前台页面
 * 
 * @author DJ
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface UiInputAdd {
	/*********************** 字段描述信息 **************************/
	public String desc() default "";

	/*********************** 字段位置信息 **************************/
	public Position position() default @Position();

	/******************** 输入控件相关信息 输入控件相关信息 *****************/
	public InputType inputType() default @InputType();

	/************************ 表单相关 ***************************/
	public AutoAjax autoAjax() default @AutoAjax(autoAjax = false);

	/*********************** 验证器 *****************************/
	public Validator[] validators() default {};
}
