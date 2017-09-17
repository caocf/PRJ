package com.common.utils.uiinput.model;

import java.util.List;

/**
 * 模型实体
 */
public class Model {
	private String modelName; // 模型名
	private List<ModelProperty> properties; // 模型属性

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public List<ModelProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<ModelProperty> properties) {
		this.properties = properties;
	}
}
