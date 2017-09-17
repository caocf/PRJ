package com.elc.transfer.entity;

import java.util.ArrayList;
import java.util.List;

public class Reply {
	
	public Reply()
	{
		transfers=new ArrayList<Transfer>();
	}
	
	List<Transfer> transfers;
	
	public List<Transfer> getTransfers() {
		return transfers;
	}
	
	public void setTransfers(List<Transfer> transfers) {
		this.transfers = transfers;
	}
	
}

