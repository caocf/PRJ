package com.huzhouport.electric.dao.impl;

import com.huzhouport.common.dao.impl.BaseDaoImpl;
import com.huzhouport.dangerousgoodsportln.model.Port;
import com.huzhouport.electric.dao.PortDao;

public class PortDaoImpl extends BaseDaoImpl<Port> implements PortDao {

	public String addPortInfo(Port port) throws Exception {
		this.hibernateTemplate.save(port);
		return null;
	}

}
