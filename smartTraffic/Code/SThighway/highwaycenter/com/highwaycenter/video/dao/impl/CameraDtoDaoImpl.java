package com.highwaycenter.video.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.SQL;
import com.highwaycenter.bean.GlryInfo;
import com.highwaycenter.video.dao.CameraDtoDao;
import com.highwaycenter.video.model.HSpCameraDTO;
import com.highwaycenter.video.model.HSpSxx;

@Repository("cameradtodao")
public class CameraDtoDaoImpl extends BaseDaoDB<HSpCameraDTO> implements CameraDtoDao{

	@Override
	public void saveCameraDto(HSpCameraDTO deviceDto) {
		super.save(deviceDto);
		
	}

	@Override
	public void deleteAll() {
		String sql = "delete from h_sp_cameradto ";
		super.delete(new SQL(sql));
		
	}

	@Override
	public List<HSpCameraDTO> selectCameraList(Integer deviceId) {
		return (List<HSpCameraDTO>) super.find(new HSpCameraDTO(),"deviceId",deviceId).getData();
	}

	@Override
	public HSpCameraDTO selectCamera(Integer cameraId) {
		
		return super.findUnique(new HSpCameraDTO(), "cameraId",cameraId);
	}

	@Override
	public void saveCameraInfo(Integer cameraId, Integer regionId) {
		String sql = "insert into h_sp_camerainfo (cameraId,regionId) values("+cameraId+","+regionId+")";
		super.executeSql(new SQL(sql));
	}

	@Override
	public void deleteallCamerainfo() {
		String sql ="delete from h_sp_camerainfo";
		super.delete(new SQL(sql));
		
	}

	@Override
	public List<HSpCameraDTO> selectCameralistByRegion(Integer regionId) {
		String sql = "select b.cameraId,b.indexCode,b.deviceId,b.name,b.chanNum,b.streamId,b.longitude,b.latitude,"
				+ "c.networkAddr from h_sp_camerainfo a,h_sp_cameradto b,h_sp_devicedto c "
				+" where a.cameraId = b.cameraId and b.deviceId=c.deviceId and a.regionId="+regionId+" ";
		try {
			SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
			 q.addScalar("cameraId",StandardBasicTypes.INTEGER);
			 q.addScalar("indexCode",StandardBasicTypes.STRING);
			 q.addScalar("deviceId", StandardBasicTypes.INTEGER);
			 q.addScalar("name", StandardBasicTypes.STRING);
			 q.addScalar("chanNum", StandardBasicTypes.INTEGER);
			 q.addScalar("streamId", StandardBasicTypes.INTEGER);
			 q.addScalar("longitude", StandardBasicTypes.DOUBLE);
			 q.addScalar("latitude", StandardBasicTypes.DOUBLE);
			 q.addScalar("networkAddr", StandardBasicTypes.STRING);
			q.setResultTransformer(Transformers.aliasToBean(HSpCameraDTO.class));
			// page和rows 都 >0 时返回分页数据
				return (List<HSpCameraDTO>)q.list();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public List<Integer> selectNoCameraByRegion() {
		String sql = "select regionId from h_sp_regioninfo where regionId not in "
				+ "(select distinct regionId from h_sp_camerainfo) and regionId not in (select distinct parentId from h_sp_regioninfo"
				+ "  )";
		return (List<Integer>) super.find(new SQL(sql)).getData();
	}

	@Override
	public Integer selectDir(String lxdm, String fxm) {
		String sql ="select sxx from h_sp_sxx where lxdm='"+lxdm+"' and fxm='"+fxm+"'";
		return (Integer) super.findUnique(new SQL(sql));
	}

	

}
