package com.highwaycenter.qljbxx.dao.impl;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.highwaycenter.bean.Lxgk;
import com.highwaycenter.qljbxx.dao.QljbxxDao;
import com.highwaycenter.qljbxx.model.HJcQljbxx;

@Repository("qljbxxdao")
public class QljbxxDaoImpl extends BaseDaoDB<HJcQljbxx> implements QljbxxDao{

	@Override
	public HJcQljbxx selectByBzbm(String bzbm) {
		
		return super.findUnique(new HJcQljbxx(), "bzbm", bzbm);
	}

	@Override
	public BaseQueryRecords selectXqList(Integer page, Integer rows) {
		
		return super.find(new HJcQljbxx(), page, rows);
	}

	@Override
	public BaseQueryRecords selectGkList(Integer page, Integer rows,Integer xzqhdm,String selectvalue,String selectId) {
		StringBuffer sql =new StringBuffer( "select a.bzbm,a.qldm, a.lxdm,concat(a.lxdm,'(',a.lxjc,')') as lxjc,"+
			"a.qlbh, a.xzqhdm, a.xzqh, a.qlmc, a.qlzxzh,"+
			"a.gldwmc, a.qlszdm, a.gydwmc, a.jgdwmc,"+
			"a.qlqc,a.xjnd from h_jc_qljbxx a left join h_jc_xzdj b on left(a.lxdm,1)=b.djszm where 1=1 ");
		if(selectId!=null&&!selectId.equals("")){//只检索标志编码
			sql.append(" and a.bzbm ='"+selectId+"' ");
		}else{
		
		   if(xzqhdm!=null&&!xzqhdm.equals("")){
		     	sql.append(" and a.xzqhdm ="+xzqhdm+" ");
		   }
		   if(selectvalue!=null&&!selectvalue.trim().equals("")){
			  sql.append(" and (a.qldm like'%"+selectvalue+"%' or a.qlmc like'%"+selectvalue+"%') ");
		   }
		   sql.append(" order by b.xzdj asc,a.lxdm ,a.qlzxzh asc,a.lxdm asc,a.qlmc asc");
		}

		try {
			SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
			 q.addScalar("bzbm",StandardBasicTypes.STRING);
			 q.addScalar("qldm",StandardBasicTypes.STRING);
			 q.addScalar("lxdm", StandardBasicTypes.STRING);
			 q.addScalar("lxjc", StandardBasicTypes.STRING);
			 q.addScalar("qlbh", StandardBasicTypes.STRING);
			 q.addScalar("xzqh", StandardBasicTypes.STRING);
			 q.addScalar("xzqhdm", StandardBasicTypes.INTEGER);
			 q.addScalar("qlmc", StandardBasicTypes.STRING);
			 q.addScalar("qlzxzh", StandardBasicTypes.FLOAT);
			 q.addScalar("gldwmc", StandardBasicTypes.STRING);
			 q.addScalar("qlszdm", StandardBasicTypes.STRING);
			 q.addScalar("gydwmc",StandardBasicTypes.STRING);
			 q.addScalar("jgdwmc", StandardBasicTypes.STRING);
			 q.addScalar("qlqc", StandardBasicTypes.FLOAT);
			 q.addScalar("xjnd", StandardBasicTypes.STRING);
			 
			q.setResultTransformer(Transformers.aliasToBean(HJcQljbxx.class));
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
	public BaseQueryRecords selectPropertyList(String key) {
		String sql = "select a."+key +" from h_jc_qljbxx a ";
		return  super.find(new SQL(sql));
	}

	@Override
	public BaseQueryRecords selectByHql(HQL hql) {
		// TODO Auto-generated method stub
		return super.find(hql);
	}

	@Override
	public BaseQueryRecords selectByHql(HQL hql, Integer page, Integer rows) {
		// TODO Auto-generated method stub
		return super.find(hql,page,rows);
	}

	@Override
	public BaseQueryRecords selectGkListByCondition(Integer page, Integer rows,
			String condition) {
		StringBuffer sql =new StringBuffer( "select a.bzbm,a.qldm, a.lxdm,concat(a.lxdm,'(',a.lxjc,')') as lxjc,"+
				"a.qlbh, a.xzqhdm, a.xzqh, a.qlmc, a.qlzxzh,"+
				"a.gldwmc, a.qlszdm, a.gydwmc, a.jgdwmc,"+
				"a.qlqc,a.xjnd from h_jc_qljbxx a left join h_jc_xzdj b on left(a.lxdm,1)=b.djszm where 1=1 ");
		
			
			   if(condition!=null&&!condition.equals("")){
			     	sql.append(condition);
			   }
			   sql.append(" order by b.xzdj asc,a.qlzxzh asc,a.lxdm asc,a.qlmc asc");
			try {
				SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
				 q.addScalar("bzbm",StandardBasicTypes.STRING);
				 q.addScalar("qldm",StandardBasicTypes.STRING);
				 q.addScalar("lxdm", StandardBasicTypes.STRING);
				 q.addScalar("lxjc", StandardBasicTypes.STRING);
				 q.addScalar("qlbh", StandardBasicTypes.STRING);
				 q.addScalar("xzqh", StandardBasicTypes.STRING);
				 q.addScalar("xzqhdm", StandardBasicTypes.INTEGER);
				 q.addScalar("qlmc", StandardBasicTypes.STRING);
				 q.addScalar("qlzxzh", StandardBasicTypes.FLOAT);
				 q.addScalar("gldwmc", StandardBasicTypes.STRING);
				 q.addScalar("qlszdm", StandardBasicTypes.STRING);
				 q.addScalar("gydwmc",StandardBasicTypes.STRING);
				 q.addScalar("jgdwmc", StandardBasicTypes.STRING);
				 q.addScalar("qlqc", StandardBasicTypes.FLOAT);
				 q.addScalar("xjnd", StandardBasicTypes.STRING);
				 
				q.setResultTransformer(Transformers.aliasToBean(HJcQljbxx.class));
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

}
