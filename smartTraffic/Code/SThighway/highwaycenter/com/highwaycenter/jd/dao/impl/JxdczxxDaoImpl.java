package com.highwaycenter.jd.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.highwaycenter.jd.dao.JxdczxxDao;
import com.highwaycenter.jd.model.HJdJxdczxx;
import com.highwaycenter.jd.model.HJdJxzdjwd;


@Repository("jxdczxxdao")
public class JxdczxxDaoImpl extends BaseDaoDB<HJdJxdczxx> implements JxdczxxDao{

	@Override
	public HJdJxdczxx selectJxdczxx(String dczbh) {
		
		return super.findUnique(new HJdJxdczxx(), "dczbh",dczbh);
	}

	@Override
	public List<Object[]> selectOptionlist(String sql) {
		
		return (List<Object[]>) super.find(new SQL(sql)).getData();
	}

	@Override
	public HJdJxdczxx selectJxdczxxSimple(String dczbh) {
		String hql = "select new com.highwaycenter.jd.model.HJdJxdczxx(a.dczbh,a.dczmc,a.nf,a.gljg,a.xzqh.xzqhmc as xzqhmc,a.lx,a.dczlx.dczlx as dczlxmc ,a.jzsj,"
			+"a.dcff.dcff as dcffmc,a.tcyf,a.zh,a.qdzh,a.zdzh,a.qdmc,a.zdmc,a.dblc,a.gdfs.gdfs as gdfsmc,a.txfs.txfs as txfsmc,a.glgn.glgn as glgnmc ,a.dcrysl,a.bz,a.dczzt) from HJdJxdczxx as  a"
			+ " left join a.xzqh left join a.dczlx left join a.dcff left join a.gdfs left join a.txfs left join a.glgn where a.dczbh ='"+dczbh+"'";
		return (HJdJxdczxx) super.findUnique(new HQL(hql));
	}

	@Override
	public BaseQueryRecords selectJxdczxxList(String condition, int page,
			int rows,String selectId){
		String hql = "select new com.highwaycenter.jd.model.HJdJxdczxx(a.dczbh, a.dczmc,a.xzqh.xzqhdm,a.xzqh.xzqhmc,a.lx, a.dczlx.dczlxbh, a.dczlx.dczlx as dczlxmc,"
				 +"a.dcff.dcffbh,a.dcff.dcff as dcffmc, a.gdfs.gdfsbh,a.gdfs.gdfs as gdfsmc,a.txfs.txfsbh,a.txfs.txfs as txfsmc, a.glgn.glgnbh,a.glgn.glgn as glgnmc,a.zh,a.qdzh,a.zdzh)"
				+" from HJdJxdczxx as  a left join a.xzqh as xzqh left join a.dczlx as dczlx left join a.dcff as dcff left join a.gdfs as gdfs left join a.txfs as txfs left join a.glgn as glgn where 1=1 ";
		
		if(selectId!=null&&!selectId.equals("")){
			hql += "and a.dczbh ='"+selectId+"' ";
		}else{
		hql +=condition;
		}
		return super.find(new HQL(hql),page,rows);
	}

	@Override
	public HJdJxzdjwd selectJWd(String gczbh) {
		String hql = " from HJdJxzdjwd where gczbh ='"+gczbh+"'";
		return (HJdJxzdjwd) super.findUnique(new HQL(hql));
	}

	


}
