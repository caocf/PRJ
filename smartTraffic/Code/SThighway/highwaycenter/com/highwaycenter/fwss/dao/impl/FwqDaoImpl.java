package com.highwaycenter.fwss.dao.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.highwaycenter.bean.CzrzInfo;
import com.highwaycenter.bean.FwssBean;
import com.highwaycenter.bzbx.model.HBzbxBzbx;
import com.highwaycenter.fwss.dao.FwqDao;
import com.highwaycenter.fwss.model.HFwFwq;
import com.highwaycenter.log.model.HJcCzrz;

@Repository("fwqdao")
public class FwqDaoImpl extends BaseDaoDB<HFwFwq> implements FwqDao{

	@Override
	public BaseQueryRecords selectFwqList(int page, int rows, String selectValue,String selectId) {
		/*StringBuffer hql = new StringBuffer("from HFwFwq where 1=1 ");
		if(selectValue!=null&&!selectValue.equals("")){
			hql.append(" and fwqmc like '%"+selectValue+"%' ");
		}
		return super.find(new HQL(hql.toString()),page,rows);*/
		StringBuffer sql = new StringBuffer("select a.fwqbh,a.fwqmc,a.ssgs,IFNULL(concat(a.xlmc,'(',b.lxjc,')'),a.xlmc) as xlmc ,a.xlmc as lxjc, "
				+ "a.sxckzh,a.sxjkzh,a.xxckzh,a.xxjkzh from h_fw_fwq as a left"
				+ "  join (select distinct lxdm,lxjc from h_jc_lxjbxx) as b on b.lxdm=a.xlmc  left join h_jc_xzdj d on left(a.xlmc,1)=d.djszm where 1=1 ");
		
		if(selectId!=null&&!selectId.equals("")){
			sql.append("and a.fwqbh = "+selectId+"  ");
			
		}else{
		if(selectValue!=null&&!selectValue.trim().equals("")){
			sql.append(" and a.fwqmc like '%"+selectValue+"%'  ");
		}
		}
		sql.append(" order by d.xzdj asc,a.xlmc ");
		try {
			SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
			 q.addScalar("fwqbh",StandardBasicTypes.INTEGER);
			 q.addScalar("fwqmc",StandardBasicTypes.STRING);
			 q.addScalar("ssgs",StandardBasicTypes.STRING);
			 q.addScalar("xlmc", StandardBasicTypes.STRING);
			 q.addScalar("lxjc",StandardBasicTypes.STRING);
			 q.addScalar("sxckzh",StandardBasicTypes.STRING);
			 q.addScalar("sxjkzh",StandardBasicTypes.STRING);
			 q.addScalar("xxckzh",StandardBasicTypes.STRING);
			 q.addScalar("xxjkzh",StandardBasicTypes.STRING);
			 
			q.setResultTransformer(Transformers.aliasToBean(HFwFwq.class));
			// page和rows 都 >0 时返回分页数据
			if (page > 0 && rows > 0) {
				int total = q.list().size();
				q.setFirstResult((page - 1) * rows);
				q.setMaxResults(rows);
				return new BaseQueryRecords(q.list(), total, page, rows);
			} else {
				return new BaseQueryRecords(q.list());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		
		
	}

	@Override
	public HFwFwq selectFwq(int fwqbh) {
		
		return super.findUnique(new HFwFwq(), "fwqbh",fwqbh);
	}

	@Override
	public Object saveAndReturn(HFwFwq fwq) {
		
		return super.saveAndReturn(fwq);
	}

	@Override
	public void update(HFwFwq fwq) {
		 super.update(fwq);
		
	}

	@Override
	public void delete(int fwqbh) {
		String sql = "delete from h_fw_fwq where fwqbh="+fwqbh;
		 super.delete(new SQL(sql));
		
	}

	@Override
	public int countSameName(String fwqmc, Integer fwqbh) {
		return super.countSameName("fwqmc", fwqmc, "h_fw_fwq", "fwqbh", fwqbh);
	}

	@Override
	public BaseQueryRecords selectUnion(int page, int rows,String selectValue,String fwssType) {
		
		StringBuffer sql =new StringBuffer("select * from ");
		if(fwssType==null||fwssType.equals("")){
			sql.append("  (select CONCAT('fwq_',a.fwqbh) as bh,a.fwqmc as mc,a.ssgs as ssgs,a.xlmc as xlmc ,'服务区' as fwtype from h_fw_fwq UNION ALL "+
                   " select  CONCAT('sfz_',b.sfzbh) as bh,b.sfzmc as mc,b.ssgs as fwtype,b.xlmc as xlmc,'收费站' as fwtype from h_fw_sfz b) as c where 1=1");
		}else if(fwssType.equals("1")){//服务区
			sql.append("(select CONCAT('fwq_',a.fwqbh) as bh,a.fwqmc as mc,a.ssgs as ssgs,a.xlmc as xlmc ,'服务区' as fwtype from h_fw_fwq ) as c where 1=1");
			
		}else{
			sql.append("(select  CONCAT('sfz_',b.sfzbh) as bh,b.sfzmc as mc,b.ssgs as ssgs,b.xlmc as xlmc,'收费站' as fwtype from h_fw_sfz b) as c where 1=1");
		}
		
		if(selectValue!=null&&!selectValue.equals("")){
			sql.append(" and c.mc like '%"+selectValue+"%' ");
		}
		

		 try{
				SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
			
				 q.addScalar("bh",StandardBasicTypes.STRING);
				 q.addScalar("mc", StandardBasicTypes.STRING);
				 q.addScalar("ssgs", StandardBasicTypes.TIMESTAMP);
				 q.addScalar("xlmc", StandardBasicTypes.STRING);
				 q.addScalar("fwtype", StandardBasicTypes.STRING);
				 q.setResultTransformer(Transformers.aliasToBean(FwssBean.class));
				 if (page > 0 && rows > 0) {
						int total = q.list().size();
						q.setFirstResult((page - 1) * rows);
						q.setMaxResults(rows);
						return new BaseQueryRecords(q.list(), total, page, rows);
					} else {
						return new BaseQueryRecords(q.list());
					}
					
		    }catch(Exception e){
		    	e.printStackTrace();
		    }
		        return null;
		
	}

}
