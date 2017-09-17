package com.channel.modelutil.anotation;

public class ValidatorDefaultMsg {
	public static String getMsgMap(UiInput uiInput, Validator validator) {
		String msg = "您的输入有误";
		if (validator.filter().toLowerCase().equals("notempty")) {
			if (uiInput.inputtype().equals("input")) {
				msg = "输入不能为空";
			} else if (uiInput.inputtype().startsWith("select")) {
				msg = "请选择" + uiInput.desc();
			}
		} else if (validator.filter().toLowerCase().equals("length")) {
			msg = "输入长度必须在" + validator.min() + "~"
					+ validator.max() + "之间";
		} else if (validator.filter().toLowerCase().equals("valrange")) {
			msg = "输入值必须在" + validator.min() + "~"
					+ validator.max() + "之间";
		} else if (validator.filter().toLowerCase().equals("regexp")) {
			msg = "输入格式有误";
		} else if (validator.filter().toLowerCase().equals("equal")) {
			msg = "再次输入不正确";
		} else if (validator.filter().toLowerCase().equals("email")) {
			msg = "邮件格式输入有误";
		} else if (validator.filter().toLowerCase().equals("phone")) {
			msg = "手机号格式输入有误";
		} else if (validator.filter().toLowerCase().equals("number")) {
			msg = "您的输入必须为数字";
		} else if (validator.filter().toLowerCase().equals("char")) {
			msg = "您的输入必须为字符";
		} else if (validator.filter().toLowerCase().equals("charnumber")) {
			msg = "您的输入必须为字符或数字";
		} else if (validator.filter().toLowerCase().equals("double")) {
			msg = "您的输入必须为小数";
		}
		return msg;
	}
}
