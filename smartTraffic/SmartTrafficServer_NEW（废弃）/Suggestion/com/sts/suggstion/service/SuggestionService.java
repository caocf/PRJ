package com.sts.suggstion.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sts.suggstion.dao.SuggestionDao;
import com.sts.suggstion.dao.SuggestionTypeDao;
import com.sts.suggstion.model.Suggestion;
import com.sts.suggstion.model.SuggestionType;

public class SuggestionService 
{
	SuggestionDao suggestionDao;
	SuggestionTypeDao suggestionTypeDao;
	
	public SuggestionDao getSuggestionDao() {
		return suggestionDao;
	}

	public void setSuggestionDao(SuggestionDao suggestionDao) {
		this.suggestionDao = suggestionDao;
	}

	public SuggestionTypeDao getSuggestionTypeDao() {
		return suggestionTypeDao;
	}

	public void setSuggestionTypeDao(SuggestionTypeDao suggestionTypeDao) {
		this.suggestionTypeDao = suggestionTypeDao;
	}

	public boolean save(Suggestion suggestion)
	{
		return suggestionDao.save(suggestion);
	}
	
	public List<Suggestion> getALLSuggestions()
	{		 
		List<Suggestion> result= this.suggestionDao.getAllSuggestions();	
		
		List<SuggestionType> types=this.suggestionTypeDao.getAllSuggestionTypes();
		
		Map<String, String> typeName=new HashMap<String, String>();
		for(int i=0;i<types.size();i++)
		{
			typeName.put(String.valueOf(types.get(i).getId()), types.get(i).getName());
		}
		
		for(int i=0;i<result.size();i++)
		{
			result.get(i).setTypeName(typeName.get(String.valueOf(result.get(i).getType())));
		}
		
		return result;
	}
}
