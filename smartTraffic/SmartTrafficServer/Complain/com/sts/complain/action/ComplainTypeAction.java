package com.sts.complain.action;

import java.util.List;

import com.sts.complain.model.ComplainType;
import com.sts.complain.service.ComplainTypeService;

public class ComplainTypeAction 
{
	List<ComplainType> complainTypes;
	ComplainTypeService complainTypeService;
	public List<ComplainType> getComplainTypes() {
		return complainTypes;
	}
	public void setComplainTypes(List<ComplainType> complainTypes) {
		this.complainTypes = complainTypes;
	}
	public void setComplainTypeService(ComplainTypeService complainTypeService) {
		this.complainTypeService = complainTypeService;
	}
	
	public String SearchAllComplainType()
	{
		complainTypes=this.complainTypeService.GetAllComplainType();
		return "success";
	}
}
