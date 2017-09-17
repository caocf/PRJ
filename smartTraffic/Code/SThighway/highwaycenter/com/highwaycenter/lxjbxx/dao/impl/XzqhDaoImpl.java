package com.highwaycenter.lxjbxx.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.highwaycenter.lxjbxx.dao.XzqhDao;
import com.highwaycenter.lxjbxx.model.HJcXzqh;
@Repository("xzqhdao")
public class XzqhDaoImpl extends BaseDaoDB<HJcXzqh> implements XzqhDao{

	@Override
	public List<HJcXzqh> selectAll() {
		
		return (List<HJcXzqh>) super.findOrderBy(new HJcXzqh(), "ordercolumn", false).getData();
	}

}
