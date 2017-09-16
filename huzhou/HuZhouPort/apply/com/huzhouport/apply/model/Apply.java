package com.huzhouport.apply.model;

import com.huzhouport.common.model.QueryCondition;

public class Apply implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private int preapplyID;
	private String preapplyType;
	private String preapplyContent;
	
	private QueryCondition queryCondition = new QueryCondition();

	public int getPreapplyID() {
		return preapplyID;
	}

	public void setPreapplyID(int preapplyID) {
		this.preapplyID = preapplyID;
	}

	public String getPreapplyType() {
		return preapplyType;
	}

	public void setPreapplyType(String preapplyType) {
		this.preapplyType = preapplyType;
	}

	public String getPreapplyContent() {
		return preapplyContent;
	}

	public void setPreapplyContent(String preapplyContent) {
		this.preapplyContent = preapplyContent;
	}

	public QueryCondition getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(QueryCondition queryCondition) {
		this.queryCondition = queryCondition;
	}

}
