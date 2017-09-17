package com.sts.breakRules.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.sts.breakRules.model.BreakRulesRecord;

public class BreakRulesService 
{
	public List<BreakRulesRecord> GetRecords(String carID,String engineID,String frameID)
	{
		return simulateBreakRulesRecords(carID,engineID,frameID);
	}
	
	public List<BreakRulesRecord> simulateBreakRulesRecords(String carID,String engineID,String frameID)
	{
		List<BreakRulesRecord> records=new ArrayList<BreakRulesRecord>();
		
		Random random=new Random();
		for(int i=0;i<random.nextInt(10);i++)
		{
			records.add(simulateRecord(carID,engineID,frameID));
		}
		
		return records;
	}
	
	public BreakRulesRecord simulateRecord(String carID,String engineID,String frameID)
	{
		String[] addrs=new String[]{"中山路","建国路","安乐路","环城南路","中环","三环"};
		String[] cross=new String[]{"交叉路口","十字路口","路中"};
		String[] contents=new String[]{"违章停车","车辆相撞","撞人"};
		
		String temp="";
		Random random=new Random();
		
		BreakRulesRecord breakRulesRecord=new BreakRulesRecord();
		breakRulesRecord.setCarID(carID);
		
		temp+=addrs[random.nextInt(addrs.length)];
		temp+=addrs[random.nextInt(addrs.length)];
		temp+=cross[random.nextInt(cross.length)];
		
		breakRulesRecord.setAddr(temp);
		temp="";
		
		breakRulesRecord.setCarType(1);
		breakRulesRecord.setType(1);
		
		temp+=contents[random.nextInt(contents.length)];
		breakRulesRecord.setContent(temp);
		breakRulesRecord.setCutPoint(random.nextInt(10));
		breakRulesRecord.setCutMoney(random.nextInt(100));
		breakRulesRecord.setDate(new Date());
		
		return breakRulesRecord;
		
	}
}
