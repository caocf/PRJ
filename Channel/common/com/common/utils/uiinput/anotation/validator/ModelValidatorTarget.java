package com.common.utils.uiinput.anotation.validator;

import com.common.utils.uiinput.anotation.AnotationModel;

/**
 * 当验证与界面上其它元素值是否相同时需要
 * 
 */
public class ModelValidatorTarget implements
		AnotationModel<ValidatorTarget, ModelValidatorTarget> {
	private String id;
	private String attr;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	@Override
	public ModelValidatorTarget fromAnotation(ValidatorTarget an) {
		if (an != null) {
			this.setAttr(an.attr());
			this.setId(an.id());
		}
		return this;
	}
}
