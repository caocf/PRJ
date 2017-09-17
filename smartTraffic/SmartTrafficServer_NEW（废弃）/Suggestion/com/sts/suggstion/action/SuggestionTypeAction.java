package com.sts.suggstion.action;

import java.util.List;

import com.sts.suggstion.model.SuggestionType;
import com.sts.suggstion.service.SuggestionTypeService;

public class SuggestionTypeAction 
{
	List<SuggestionType> suggestionTypes;
	
	SuggestionTypeService suggestionTypeService;

	public List<SuggestionType> getSuggestionTypes() {
		return suggestionTypes;
	}

	public void setSuggestionTypes(List<SuggestionType> suggestionTypes) {
		this.suggestionTypes = suggestionTypes;
	}

	public void setSuggestionTypeService(SuggestionTypeService suggestionTypeService) {
		this.suggestionTypeService = suggestionTypeService;
	}
	
	public String SearchAllSuggestionType()
	{
		suggestionTypes=suggestionTypeService.GetAllSuggestionType();
		
		return "success";
	}
}
