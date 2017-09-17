package com.highwaycenter.video.dao;

import java.util.List;

import com.highwaycenter.video.model.HSpRegionInfoDTO;

public interface RegionInfoDtoDao {
	public void save(HSpRegionInfoDTO regioninfo);
	public void deleteall();
    public List<Integer> selectAllRegionId();
    public List<HSpRegionInfoDTO> selectAllRegion(Integer unionid);
}
