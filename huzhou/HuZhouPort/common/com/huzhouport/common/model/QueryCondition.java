package com.huzhouport.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询条件
 * 
 * @author 王翠
 * 
 */
public class QueryCondition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String orderByFielName;// 按某个字段排序

	private String sequence;// 排序顺序

	private String isFuzzy;// 是否模糊查询

	private int showRows;// 每行显示多少行

	// 存取当前流水号

	private String functionId;// 功能id

	private String curOrderNumber;// 当前流水号

	private List<KeyValuePair> listKeyValuePair = new ArrayList<KeyValuePair>();// 查询条件

	public String getOrderByFielName() {
		return orderByFielName;
	}

	public void setOrderByFielName(String orderByFielName) {
		this.orderByFielName = orderByFielName;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getIsFuzzy() {
		return isFuzzy;
	}

	public void setIsFuzzy(String isFuzzy) {
		this.isFuzzy = isFuzzy;
	}

	public List<KeyValuePair> getListKeyValuePair() {
		return listKeyValuePair;
	}

	public void setListKeyValuePair(List<KeyValuePair> listKeyValuePair) {
		this.listKeyValuePair = listKeyValuePair;
	}

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getCurOrderNumber() {
		return curOrderNumber;
	}

	public void setCurOrderNumber(String curOrderNumber) {
		this.curOrderNumber = curOrderNumber;
	}

	public int getShowRows() {
		return showRows;
	}

	public void setShowRows(int showRows) {
		this.showRows = showRows;
	}

}
