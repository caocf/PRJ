package com.highwaycenter.bzbx.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.highwaycenter.bzbx.dao.BzbxlxDao;
import com.highwaycenter.bzbx.model.HJcBzbxlx;
@Repository("bzbxlxdao")
public class BzbxlxDaoImpl extends BaseDaoDB<HJcBzbxlx> implements BzbxlxDao{

	@Override
	public List<HJcBzbxlx> selectBzbxlxList() {
		
		return (List<HJcBzbxlx>) super.find(new HJcBzbxlx()).getData();
	}

}
