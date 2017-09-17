package com.sts.suggstion.service;

import java.util.List;

import com.sts.suggstion.dao.SuggestionTypeDao;
import com.sts.suggstion.model.SuggestionType;

public class SuggestionTypeService 
{
	SuggestionTypeDao suggestionTypeDao;

	public SuggestionTypeDao getSuggestionTypeDao() {
		return suggestionTypeDao;
	}

	public void setSuggestionTypeDao(SuggestionTypeDao suggestionTypeDao) {
		this.suggestionTypeDao = suggestionTypeDao;
	}
	
	
	public List<SuggestionType> GetAllSuggestionType()
	{
		return this.suggestionTypeDao.getAllSuggestionTypes();
	}
	
}
