package com.huzhouport.wharfSelect.dao;

import java.util.List;

import com.huzhouport.common.dao.impl.BaseDaoImpl;
import com.huzhouport.wharfSelect.model.PieceArea;

public class PieceAreaDao extends BaseDaoImpl<PieceArea>{
	public PieceArea findPieceByName(String name) {
		PieceArea area = null;
		List<PieceArea> list = null;
		try {
			list = this.queryDataByConditions(new PieceArea(), "name", name);
			
			if (list != null && list.size() > 0)
				area = list.get(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return area;
	}
}
