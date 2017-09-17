package com.channel.modelutil;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.common.action.BaseAction;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultOK;

@Controller
public class ModelAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 118103109631869840L;

	@Resource(name = "ModelService")
	private ModelService modelService;

	private BaseResult result;
	private String modelname;
	private String inputdata;
	private boolean editmode = false;

	@SuppressWarnings("unchecked")
	public String queryModelInfo() {
		result = new BaseResultOK();
		Map<String, Object> datamap = new HashMap<>();
		if (inputdata != null && !inputdata.equals("")) {
			try {
				datamap = JSON.parseObject(inputdata, datamap.getClass());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		result.setObj(this.modelService.queryModelInfo(modelname, datamap,editmode));
		return SUCCESS;
	}

	public BaseResult getResult() {
		return result;
	}

	public void setResult(BaseResult result) {
		this.result = result;
	}

	public String getModelname() {
		return modelname;
	}

	public void setModelname(String modelname) {
		this.modelname = modelname;
	}

	public String getInputdata() {
		return inputdata;
	}

	public void setInputdata(String inputdata) {
		this.inputdata = inputdata;
	}

	public boolean isEditmode() {
		return editmode;
	}

	public void setEditmode(boolean editmode) {
		this.editmode = editmode;
	}
}
