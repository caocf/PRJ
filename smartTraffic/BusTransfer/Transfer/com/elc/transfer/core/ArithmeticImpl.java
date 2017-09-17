package com.elc.transfer.core;

import java.util.List;

import com.elc.transfer.entity.Reply;
import com.elc.transfer.entity.Request;
import com.elc.transfer.entity.Station;
import com.elc.transfer.util.TransferUtil;

public class ArithmeticImpl implements IArithmeticCore{

	private final int RESULT_COUNT=5;
	
	@Override
	public Reply arithmeticTransfer(Request r) {
		Reply result=new Reply();
		
		//搜索半径，步行和自行车的搜索半径
		int radius=r.getContainsBike()==1?Params.BIKE_NEARBY_DISTANCE:Params.FOOTER_NEARBY_DISTANCE;
		
		//获取起点附近点
		List<Station> starts=TransferUtil.queryNearbyStation(r.getLan1(), r.getLon1(), radius);
		
		//获取终点附近点
		List<Station> ends=TransferUtil.queryNearbyStation(r.getLan2(), r.getLon2(), radius);
		
		for(Station s:starts)
			System.out.println("起点附近点:"+s.getName()+s.getId());
		
		for(Station s:ends)
			System.out.println("终点附近点:"+s.getName()+s.getId());
		
		
		
		return result;
	}

	@Override
	public void saveResultToCache(Reply reply) {
		// TODO Auto-generated method stub
		
	}

}

