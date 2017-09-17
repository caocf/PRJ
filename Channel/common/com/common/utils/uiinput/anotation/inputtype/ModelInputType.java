package com.common.utils.uiinput.anotation.inputtype;

import java.util.ArrayList;
import java.util.List;

import com.common.utils.uiinput.anotation.AnotationModel;
import com.common.utils.uiinput.anotation.other.ControlType;
import com.common.utils.uiinput.anotation.other.KeyValue;
import com.common.utils.uiinput.anotation.other.ModelKeyValue;

public class ModelInputType implements
		AnotationModel<InputType, ModelInputType> {
	// input -->输入框 textarea --> 输入区域 select --> 下拉选择框 date --> 时间
	private ControlType inputType;

	// 当前此输入是从value中获取值，还是从attr自定义属性中获取
	private String valueAttr;

	// *** 对不同的inputtype需要进行设置的可选参数 ****/
	// 文本输入区域行数
	private int textAreaRows;

	// 用户 可选择输入的默认值
	private List<ModelKeyValue> selKeyVals;

	// 控件只读，如不可输入，不能选择等
	private boolean readonly;

	// 控件提示信息
	private String hint;

	// 控件默认值
	private String dftVal;

	public ControlType getInputType() {
		return inputType;
	}

	public void setInputType(ControlType inputType) {
		this.inputType = inputType;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	@Override
	public ModelInputType fromAnotation(InputType an) {
		if (an != null) {
			this.setDftVal(an.dftVal());
			this.setReadonly(an.readonly());
			this.setHint(an.hint());
			this.setInputType(an.inputType());
			this.setValueAttr(an.valueAttr());
			List<ModelKeyValue> keyValues = new ArrayList<>();

			if (an.selKeyVals() != null && an.selKeyVals().length != 0) { // 如果设置了keyval
				for (KeyValue keyValue : an.selKeyVals()) {
					keyValues.add(new ModelKeyValue().fromAnotation(keyValue));
				}
			}

			this.setSelKeyVals(keyValues);
		}
		return this;
	}

	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public String getDftVal() {
		return dftVal;
	}

	public void setDftVal(String dftVal) {
		this.dftVal = dftVal;
	}

	public int getTextAreaRows() {
		return textAreaRows;
	}

	public void setTextAreaRows(int textAreaRows) {
		this.textAreaRows = textAreaRows;
	}

	public List<ModelKeyValue> getSelKeyVals() {
		return selKeyVals;
	}

	public void setSelKeyVals(List<ModelKeyValue> selKeyVals) {
		this.selKeyVals = selKeyVals;
	}

	public String getValueAttr() {
		return valueAttr;
	}

	public void setValueAttr(String valueAttr) {
		this.valueAttr = valueAttr;
	}
}
