package com.common.utils.uiinput.anotation.autoajax;

import com.common.utils.uiinput.anotation.AnotationModel;

public class ModelAutoAjax implements AnotationModel<AutoAjax, ModelAutoAjax> {
	/********************** 表单提交相关 ******************/
	private boolean autoAjax;

	// 表单提交时提交名
	private String autoAjaxName;

	// 表单提交时，如果没有输入或输入为""，则自动以此属性进行提交
	private String autoAjaxDftVal;

	public boolean isAutoAjax() {
		return autoAjax;
	}

	public void setAutoAjax(boolean autoAjax) {
		this.autoAjax = autoAjax;
	}

	public String getAutoAjaxName() {
		return autoAjaxName;
	}

	public void setAutoAjaxName(String autoAjaxName) {
		this.autoAjaxName = autoAjaxName;
	}

	public String getAutoAjaxDftVal() {
		return autoAjaxDftVal;
	}

	public void setAutoAjaxDftVal(String autoAjaxDftVal) {
		this.autoAjaxDftVal = autoAjaxDftVal;
	}

	@Override
	public ModelAutoAjax fromAnotation(AutoAjax an) {
		if (an != null) {
			this.setAutoAjax(an.autoAjax());
			this.setAutoAjaxDftVal(an.autoAjaxDftVal());
			this.setAutoAjaxName(an.autoAjaxName());
		}
		return this;
	}
}
