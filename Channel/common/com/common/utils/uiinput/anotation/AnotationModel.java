package com.common.utils.uiinput.anotation;

public interface AnotationModel<Anotation, Model> {
	public Model fromAnotation(Anotation an);
}
