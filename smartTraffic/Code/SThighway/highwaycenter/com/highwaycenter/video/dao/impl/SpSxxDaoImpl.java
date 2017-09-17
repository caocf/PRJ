package com.highwaycenter.video.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.SQL;
import com.highwaycenter.video.dao.SpSxxDao;
import com.highwaycenter.video.model.HSpSxx;
@Repository("spsxxdao")
public class SpSxxDaoImpl extends BaseDaoDB<HSpSxx> implements SpSxxDao{

	@Override
	public void saveSpSxx(HSpSxx sxx) {
		super.save(sxx);
		
	}

	@Override
	public BaseQueryRecords selectSxxList(int page, int rows) {
		//return super.find(new HSpSxx(), page, rows);
		return super.findOrderBy(new HSpSxx(), "lxdm",false,  page, rows);
	}

	@Override
	public List<String> selectCameraName(String selectvalue) {
		String sql = "select name from h_sp_cameradto where name like '%K%' ";
		if(selectvalue!=null&&!"".equals(selectvalue)){
			sql+=" and name like '%"+selectvalue+" %' ";
		}
		return (List<String>) super.find(new SQL(sql)).getData();
	}

	@Override
	public void updateSpSxx(HSpSxx sxx) {
		super.update(sxx);		
	}

	@Override
	public void deleteSpSxx(int sxxId) {
		String sql = "delete from h_sp_sxx where sxxId="+sxxId;
		super.delete(new SQL(sql));
		
	}

	@Override
	public int countSameSpsxx(HSpSxx spsxx) {
		String sql = "select count(*) from h_sp_sxx where lxdm='"+spsxx.getLxdm()+"' and fxm='"+spsxx.getFxm()+
				"'  ";
		if(spsxx.getSxxId()!=null&&!"".equals(spsxx.getSxxId())){
			sql+=" and sxxId <> "+spsxx.getSxxId();
		}
		 try {
				int cnt = 0;
				Query q = getCurrentSession().createSQLQuery(sql.toString());
				cnt = ((BigInteger) q.uniqueResult()).intValue();
				return cnt;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}	
	}

	@Override
	public HSpSxx selectUnique(int sxxId) {
		
		return super.findUnique(new HSpSxx(), "sxxId", sxxId);
	}

}
