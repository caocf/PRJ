package com.channel.modelutil.model;

import java.util.List;

public class ModelInfo {
	private String modelName; //模型名
	private List<ModelPropertyInfo> propertyInfos; //模型属性

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public List<ModelPropertyInfo> getPropertyInfos() {
		return propertyInfos;
	}

	public void setPropertyInfos(List<ModelPropertyInfo> propertyInfos) {
		this.propertyInfos = propertyInfos;
	}
}
