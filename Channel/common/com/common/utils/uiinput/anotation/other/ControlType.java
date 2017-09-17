package com.common.utils.uiinput.anotation.other;

public enum ControlType {
	INPUT, // 文本输入
	TEXTAREA, // 文本输入区域
	MULTISELECT, // 下拉多选框
	SELECT, // 下拉单选
	YEAR, // 下拉选择年份
	MONTH, // 下拉选择月份
	DAY, // 下拉选择日期
	TIME, // 下拉选择时间
	CHECKBOX, // 单值复选框，如是否记住密码
	CHECKBOXGROUP, // 多值复选框，如爱好
	RADIOGROUP, // 多值单选框
	HIDDEN; // 隐藏,常用于设置隐藏域，在提交表单时进行提交
}
