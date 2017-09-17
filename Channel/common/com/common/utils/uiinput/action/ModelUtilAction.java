package com.common.utils.uiinput.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.common.action.BaseAction;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultOK;
import com.common.utils.uiinput.service.ModelUtilService;

@Controller
public class ModelUtilAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 118103109631869840L;

	@Resource(name = "ModelUtilService")
	private ModelUtilService modelService;

	private BaseResult result;
	private String modelpath; // 模型所在完整路径
	private Map<String, Object> inputMap = new HashMap<String, Object>();

	public String queryModelAddInfo() {
		result = new BaseResultOK();
		result.setObj(this.modelService.queryModelAddInfo(modelpath, inputMap));
		return SUCCESS;
	}

	public String queryModelUpdateInfo() {
		result = new BaseResultOK();
		result.setObj(this.modelService.queryModelUpdateInfo(modelpath,
				inputMap));
		return SUCCESS;
	}

	public BaseResult getResult() {
		return result;
	}

	public void setResult(BaseResult result) {
		this.result = result;
	}

	public String getModelpath() {
		return modelpath;
	}

	public void setModelpath(String modelpath) {
		this.modelpath = modelpath;
	}

	public Map<String, Object> getInputMap() {
		return inputMap;
	}

	public void setInputMap(Map<String, Object> inputMap) {
		this.inputMap = inputMap;
	}
}
