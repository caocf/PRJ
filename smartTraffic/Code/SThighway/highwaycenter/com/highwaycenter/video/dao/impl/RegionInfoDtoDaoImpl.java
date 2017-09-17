package com.highwaycenter.video.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.SQL;
import com.highwaycenter.video.dao.RegionInfoDtoDao;
import com.highwaycenter.video.model.HSpRegionInfoDTO;
@Repository("regioninfodtodao")
public class RegionInfoDtoDaoImpl extends BaseDaoDB<HSpRegionInfoDTO> implements RegionInfoDtoDao{

	@Override
	public void deleteall() {
		String sql = "delete from h_sp_regioninfo ";
		super.delete(new SQL(sql));
		
	}

	@Override
	public List<Integer> selectAllRegionId() {
		String sql ="select regionId from h_sp_regioninfo ";
		return (List<Integer>) super.find(new SQL(sql)).getData();
	}

	@Override
	public List<HSpRegionInfoDTO> selectAllRegion(Integer unionid) {
		return (List<HSpRegionInfoDTO>) super.find(new HSpRegionInfoDTO(), "controlUnitId",unionid).getData();
	}

}
