package com.common.utils.uiinput.model;

import com.common.utils.uiinput.anotation.add.ModelUiInputAdd;
import com.common.utils.uiinput.anotation.update.ModelUiInputUpdate;

public class ModelProperty {
	private String name; // 属性名
	private String type; // 属性类型

	private ModelUiInputAdd uiInputAdd; //
	private ModelUiInputUpdate uiInputUpdate; //

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public ModelUiInputAdd getUiInputAdd() {
		return uiInputAdd;
	}

	public void setUiInputAdd(ModelUiInputAdd uiInputAdd) {
		this.uiInputAdd = uiInputAdd;
	}

	public ModelUiInputUpdate getUiInputUpdate() {
		return uiInputUpdate;
	}

	public void setUiInputUpdate(ModelUiInputUpdate uiInputUpdate) {
		this.uiInputUpdate = uiInputUpdate;
	}
}
