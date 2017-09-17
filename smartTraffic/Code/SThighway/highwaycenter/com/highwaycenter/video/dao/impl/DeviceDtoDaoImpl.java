package com.highwaycenter.video.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.SQL;
import com.highwaycenter.video.dao.DeviceDtoDao;
import com.highwaycenter.video.model.HSpDeviceDTO;

@Repository("devicedtodao")
public class DeviceDtoDaoImpl extends BaseDaoDB<HSpDeviceDTO> implements DeviceDtoDao{

	@Override
	public void saveDeviceDto(HSpDeviceDTO deviceDto) {
		super.save(deviceDto);
		
	}

	@Override
	public void updateDtoName(String name, Integer dtoid, String tableName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		String sql = "delete from h_sp_devicedto ";
		super.delete(new SQL(sql));
		
	}

	@Override
	public List<HSpDeviceDTO> selectDeviceList() {
		
		return (List<HSpDeviceDTO>) super.find(new HSpDeviceDTO()).getData();
	}

	@Override
	public String selectNetWorkAddr(Integer deviceId) {
		String sql = "select networkAddr  from h_sp_devicedto where deviceId="+deviceId;
		return (String) super.findUnique(new SQL(sql));
	}

}
