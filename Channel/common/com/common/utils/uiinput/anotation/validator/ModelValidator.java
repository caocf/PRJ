package com.common.utils.uiinput.anotation.validator;

import com.common.utils.uiinput.anotation.AnotationModel;
import com.common.utils.uiinput.anotation.other.Filter;

public class ModelValidator implements
		AnotationModel<Validator, ModelValidator> {
	/**
	 * 字段验证过滤器
	 * 
	 * @return
	 */
	private Filter filter;

	/**
	 * 字段验证不成功时提示信息
	 * 
	 * @return
	 */
	private String msg;

	/**
	 * length验证长度时长度>=min <=max
	 * 
	 * @return
	 */
	private int min;

	/**
	 * length验证长度时长度>=min <=max
	 * 
	 * @return
	 */
	private int max;

	/**
	 * equal验证时，对比的元素id，常用于再次输入密码验证
	 * 
	 * @return
	 */
	private ModelValidatorTarget target;

	/**
	 * regexp自定义正则验证时的正则表达式
	 * 
	 * @return
	 */
	private String exp;

	/**
	 * 自定义验证js函数,该函数必须返回true/false,true表示验证成功，false表示验证失败
	 * 
	 * @return
	 */
	private String fn;

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public ModelValidatorTarget getTarget() {
		return target;
	}

	public void setTarget(ModelValidatorTarget target) {
		this.target = target;
	}

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public String getFn() {
		return fn;
	}

	public void setFn(String fn) {
		this.fn = fn;
	}

	@Override
	public ModelValidator fromAnotation(Validator an) {
		if (an != null) {
			this.setExp(an.exp());
			this.setFilter(an.filter());
			this.setFn(an.fn());
			this.setMax(an.max());
			this.setMin(an.min());
			this.setMsg(an.msg());
			this.setTarget(new ModelValidatorTarget().fromAnotation(an.target()));
		}
		return this;
	}

	/*
	 * public static String getMsgMap(UiInputUpdate uiInput, Validator
	 * validator) { String msg = "您的输入有误"; if
	 * (validator.filter().toLowerCase().equals("notempty")) { if
	 * (uiInput.inputtype().equals("input")) { msg = "输入不能为空"; } else if
	 * (uiInput.inputtype().startsWith("select")) { msg = "请选择" +
	 * uiInput.desc(); } } else if
	 * (validator.filter().toLowerCase().equals("length")) { msg = "输入长度必须在" +
	 * validator.min() + "~" + validator.max() + "之间"; } else if
	 * (validator.filter().toLowerCase().equals("valrange")) { msg = "输入值必须在" +
	 * validator.min() + "~" + validator.max() + "之间"; } else if
	 * (validator.filter().toLowerCase().equals("regexp")) { msg = "输入格式有误"; }
	 * else if (validator.filter().toLowerCase().equals("equal")) { msg =
	 * "再次输入不正确"; } else if (validator.filter().toLowerCase().equals("email")) {
	 * msg = "邮件格式输入有误"; } else if
	 * (validator.filter().toLowerCase().equals("phone")) { msg = "手机号格式输入有误"; }
	 * else if (validator.filter().toLowerCase().equals("number")) { msg =
	 * "您的输入必须为数字"; } else if (validator.filter().toLowerCase().equals("char"))
	 * { msg = "您的输入必须为字符"; } else if
	 * (validator.filter().toLowerCase().equals("charnumber")) { msg =
	 * "您的输入必须为字符或数字"; } else if
	 * (validator.filter().toLowerCase().equals("double")) { msg = "您的输入必须为小数";
	 * } return msg; }
	 */
}
