package com.highwaycenter.fwss.dao.impl;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.highwaycenter.fwss.dao.SfzDao;
import com.highwaycenter.fwss.model.HFwFwq;
import com.highwaycenter.fwss.model.HFwSfz;

@Repository("sfzdao")
public class SfzDaoImpl extends BaseDaoDB<HFwSfz> implements SfzDao{

	@Override
	public BaseQueryRecords selectSfzList(int page, int rows, String selectValue,String selectId) {
		/*StringBuffer hql = new StringBuffer("from HFwSfz where 1=1 ");
		if(selectValue!=null&&!selectValue.equals("")){
			hql.append(" and sfzmc like '%"+selectValue+"%' ");
		}
		return super.find(new HQL(hql.toString()),page,rows);*/
		StringBuffer sql = new StringBuffer("select a.sfzbh,a.sfzmc,a.ssgs,a.sfzlxbh,a.ckzx,IFNULL(concat(a.xlmc,'(',b.lxjc,')'),a.xlmc) as xlmc ,a.xlmc as lxjc "
				+ ",a.zxsxckzh,a.zxsxjkzh,a.zxxxckzh,a.zxxxjkzh from h_fw_sfz as a left"
				+ "  join (select distinct lxdm,lxjc from h_jc_lxjbxx) as b on b.lxdm=a.xlmc left join h_jc_xzdj d on left(a.xlmc,1)=d.djszm   where 1=1 ");
		if(selectId!=null&&!selectId.equals("")){
			sql.append("and a.sfzbh = "+selectId+"  ");
			
		} else{
		if(selectValue!=null&&!selectValue.trim().equals("")){
			sql.append(" and a.sfzmc like '%"+selectValue+"%'  ");
		}
		}
		sql.append(" order by d.xzdj asc,a.xlmc ");
		try {
			SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
			 q.addScalar("sfzbh",StandardBasicTypes.INTEGER);
			 q.addScalar("sfzmc",StandardBasicTypes.STRING);
			 q.addScalar("ssgs",StandardBasicTypes.STRING);
			 q.addScalar("xlmc", StandardBasicTypes.STRING);
			 q.addScalar("ckzx",StandardBasicTypes.STRING);
			 q.addScalar("sfzlxbh",StandardBasicTypes.INTEGER);
			 q.addScalar("lxjc",StandardBasicTypes.STRING);
			 q.addScalar("zxsxckzh",StandardBasicTypes.STRING);
			 q.addScalar("zxsxjkzh",StandardBasicTypes.STRING);
			 q.addScalar("zxxxckzh",StandardBasicTypes.STRING);
			 q.addScalar("zxxxjkzh",StandardBasicTypes.STRING);
			q.setResultTransformer(Transformers.aliasToBean(HFwSfz.class));
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
	public HFwSfz selectSfz(int sfzbh) {
		
		return super.findUnique(new HFwSfz(), "sfzbh",sfzbh);
	}

	@Override
	public Object saveAndReturn(HFwSfz sfz) {
		
		return super.saveAndReturn(sfz);
	}

	@Override
	public void update(HFwSfz sfz) {
		super.update(sfz);
		
	}

	@Override
	public void delete(int sfzbh) {
        String sql = "delete from h_fw_sfz where sfzbh ="+sfzbh;
        super.delete(new SQL(sql));
		
	}

	@Override
	public int countSameName(String sfzmc, Integer sfzbh) {
		
		return super.countSameName("sfzmc", sfzmc, "h_fw_sfz", "sfzbh", sfzbh);
	}

}
