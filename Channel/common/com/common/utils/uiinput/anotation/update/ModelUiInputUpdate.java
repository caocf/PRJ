package com.common.utils.uiinput.anotation.update;

import java.util.ArrayList;
import java.util.List;

import com.common.utils.uiinput.anotation.AnotationModel;
import com.common.utils.uiinput.anotation.autoajax.ModelAutoAjax;
import com.common.utils.uiinput.anotation.inputtype.ModelInputType;
import com.common.utils.uiinput.anotation.other.Filter;
import com.common.utils.uiinput.anotation.position.ModelPosition;
import com.common.utils.uiinput.anotation.validator.ModelValidator;
import com.common.utils.uiinput.anotation.validator.Validator;

/**
 * 用于自动生成新增的前台页面
 * 
 * @author DJ
 * 
 */
public class ModelUiInputUpdate implements
		AnotationModel<UiInputUpdate, ModelUiInputUpdate> {
	/*********************** 字段描述信息 **************************/
	private String desc;

	/*********************** 字段位置信息 **************************/
	private ModelPosition position;

	/******************** 输入控件相关信息 输入控件相关信息 *****************/
	private ModelInputType inputType;

	/************************ 表单相关 ***************************/
	private ModelAutoAjax autoAjax;

	/*********************** 验证器 *****************************/
	private List<ModelValidator> validators;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public ModelPosition getPosition() {
		return position;
	}

	public void setPosition(ModelPosition position) {
		this.position = position;
	}

	public ModelInputType getInputType() {
		return inputType;
	}

	public void setInputType(ModelInputType inputType) {
		this.inputType = inputType;
	}

	public ModelAutoAjax getAutoAjax() {
		return autoAjax;
	}

	public void setAutoAjax(ModelAutoAjax autoAjax) {
		this.autoAjax = autoAjax;
	}

	@Override
	public ModelUiInputUpdate fromAnotation(UiInputUpdate an) {
		if (an != null) {
			this.setDesc(an.desc());
			this.setAutoAjax(new ModelAutoAjax().fromAnotation(an.autoAjax()));
			this.setInputType(new ModelInputType().fromAnotation(an.inputType()));
			this.setPosition(new ModelPosition().fromAnotation(an.position()));

			List<ModelValidator> validators = new ArrayList<ModelValidator>();
			Validator[] validatorsan = an.validators();
			if (validatorsan != null) {
				for (Validator validator : validatorsan)
					validators.add(new ModelValidator()
							.fromAnotation(validator));
			}

			this.setValidators(validators);
		}
		return this;
	}

	public List<ModelValidator> getValidators() {
		return validators;
	}

	public ModelValidator getValidator(Filter filter) {
		if (validators != null) {
			for (ModelValidator validator : validators) {
				if (validator.getFilter().equals(filter)) {
					return validator;
				}
			}
		}
		return null;
	}

	public void removeValidator(Filter filter) {
		if (validators != null) {
			for (int i = 0; i < validators.size(); i++) {
				if (validators.get(i).getFilter().equals(filter)) {
					validators.remove(i);
					return;
				}
			}
		}
	}

	public void addValidator(ModelValidator validator) {
		if (validators == null) {
			validators = new ArrayList<>();
		}
		if (getValidator(validator.getFilter()) == null) {
			validators.add(validator);
		} else {
			removeValidator(validator.getFilter());
			validators.add(validator);
		}
	}

	public void setValidators(List<ModelValidator> validators) {
		this.validators = validators;
	}
}
