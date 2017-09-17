package com.common.utils.uiinput.anotation.other;

import com.common.utils.uiinput.anotation.AnotationModel;

public class ModelKeyValue implements AnotationModel<KeyValue, ModelKeyValue> {
	private String key;
	private Object value;

	public ModelKeyValue() {

	}

	public ModelKeyValue(String key, Object value) {
		setKey(key);
		setValue(value);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public ModelKeyValue fromAnotation(KeyValue an) {
		if (an != null) {
			this.setKey(an.key());
			this.setValue(an.value());
		}
		return this;
	}

}
