package com.example.smarttraffic.common.localDB;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

import com.example.smarttraffic.util.DoString;

public class DoHistoryBaseList 
{
	public DoHistoryBaseList(Context context,int type)
	{
		init(context,type);
	}
	
	private List<TwoItemHistory> baseList;
	private HistoryDBOperate db;
	public int type;

	private void init(Context context,int type)
	{
		this.db=new HistoryDBOperate(context);
		this.type=type;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<TwoItemHistory> getBaseList() {
		return baseList;
	}

	public void setBaseList(List<TwoItemHistory> baseList) {
		this.baseList = baseList;
	}

	public List<TwoItemHistory> select()
	{
		baseList=new ArrayList<TwoItemHistory>();
		
		Cursor cursor=db.select(getType());
		cursor.moveToFirst();
		
		TwoItemHistory temp=new TwoItemHistory();
		String pointString="";
		String[] pointStrings;
		
		for(int i=0;i<cursor.getCount();i++)
		{
			temp.setId(cursor.getInt(0));
			temp.setType(cursor.getInt(1));
			
			pointString=cursor.getString(2);
			pointStrings=DoString.splitByDefault(pointString);
			
			temp.setStart(pointStrings[0]);
			temp.setEnd(pointStrings[1]);
			
			baseList.add(temp);
			cursor.moveToNext();
		}
		
		cursor.close();
		
		return baseList;
	}
	
	public void insert(String[] data)
	{
		String content=DoString.mergerByDefault(data);
		db.insert(getType(), content);
	}
	
	public void delete(int id)
	{
		db.delete(id);
	}
	
	public void close()
	{
		db.CloseDB();
	}
}
