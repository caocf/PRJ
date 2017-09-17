package com.highwaycenter.video.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.SQL;
import com.highwaycenter.video.dao.ControlUnitDtoDao;
import com.highwaycenter.video.model.HSpControlUnitDTO;
@Repository("controlunitdtodao")
public class ControlUnitDtoDaoImpl extends BaseDaoDB<HSpControlUnitDTO> implements ControlUnitDtoDao{

	@Override
	public void save(HSpControlUnitDTO center) {
		super.save(center);
		
	}

	@Override
	public void deleteall() {
		String sql = "delete from h_sp_controlunit ";
		super.delete(new SQL(sql));
		
	}

	@Override
	public List<HSpControlUnitDTO> selectAllControlUnit() {
		return (List<HSpControlUnitDTO>) super.find(new HSpControlUnitDTO()).getData();
	}

}
