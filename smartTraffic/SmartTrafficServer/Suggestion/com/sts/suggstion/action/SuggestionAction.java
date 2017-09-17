package com.sts.suggstion.action;

import java.util.List;

import com.sts.suggstion.model.Suggestion;
import com.sts.suggstion.service.SuggestionService;

public class SuggestionAction 
{
	Suggestion suggestion;
	SuggestionService suggestionService;
	int type;
	String title;
	String content;
	String contact;
	String contactPerson;
	boolean saveResult;
	
	private List<Suggestion> suggestions;
	
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}
	public List<Suggestion> getSuggestions() {
		return suggestions;
	}

	public void setSuggestions(List<Suggestion> suggestions) {
		this.suggestions = suggestions;
	}

	public Suggestion getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(Suggestion suggestion) {
		this.suggestion = suggestion;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public void setSuggestionService(SuggestionService suggestionService) {
		this.suggestionService = suggestionService;
	}

	public boolean isSaveResult() {
		return saveResult;
	}

	public void setSaveResult(boolean saveResult) {
		this.saveResult = saveResult;
	}

	public String SaveSuggestion()
	{
		suggestion=new Suggestion();
		suggestion.setContact(contact);
		suggestion.setType(type);
		suggestion.setContent(content);
		suggestion.setTitle(title);
		suggestion.setSuggestionPersion(contactPerson);
		
		saveResult=suggestionService.save(suggestion);
		
		return "success";
	}
	
	public String GetSuggestion()
	{
		suggestions=suggestionService.getALLSuggestions();
		return "success";
	}
}
