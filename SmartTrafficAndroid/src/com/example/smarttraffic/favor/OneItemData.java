package com.example.smarttraffic.favor;

import com.example.smarttraffic.common.localDB.OneItemHistory;

public class OneItemData extends OneItemHistory
{
	public OneItemData()
	{
		super();
		isSelect=false;
	}
	private boolean isSelect;

	private String data;
	
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}
}
