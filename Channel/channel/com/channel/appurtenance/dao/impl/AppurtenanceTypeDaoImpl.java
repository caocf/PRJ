package com.channel.appurtenance.dao.impl;

import com.common.dao.BaseQueryRecords;
import org.springframework.stereotype.Repository;

import com.channel.model.hd.CZdAppurtenance;
import com.channel.model.hd.CZdAppurtenanceRela;
import com.common.utils.tree.impl.TreeDaoImpl;

@Repository("appurtenancetypedao")
public class AppurtenanceTypeDaoImpl extends TreeDaoImpl<CZdAppurtenance, CZdAppurtenanceRela> {

	@Override
	public Class<?> getEntryClass() {
		// TODO Auto-generated method stub
		return CZdAppurtenance.class;
	}

	@Override
	public Class<?> getEntryRelationClass() {
		// TODO Auto-generated method stub
		return CZdAppurtenanceRela.class;
	}


	public CZdAppurtenanceRela queryPidBysid(int fswlx) {
		return (CZdAppurtenanceRela) super.findUnique(new CZdAppurtenanceRela(),"sid",fswlx);
	}
}
