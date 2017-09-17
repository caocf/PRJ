package com.highwaycenter.legal.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.common.action.Constants.LegalState;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.SQL;
import com.highwaycenter.bean.selectListBean;
import com.highwaycenter.legal.dao.XzpunishmentDao;
import com.highwaycenter.legal.model.HXzPunishment;

@Repository("xzpunishimentdao")
public class XzpunishmentDaoImpl extends BaseDaoDB<HXzPunishment> implements XzpunishmentDao{

	@Override
	public void delete(int xzcfbh) {
		String sql = "update  h_xz_punishment set isdelete = 1,state = "+LegalState.DELETE.toInt() +" where xzcfbh="+xzcfbh;
	    super.update(new SQL(sql));
	}

	@Override
	public BaseQueryRecords selectXzcfList(int page, int rows, String xzcfjgdm,
			String selectKey, String selectValue) {
		String sql = "select a.xzcfbh,a.xzcfjgdm,a.xzcfjdwh,a.ajmc,a.cfrxm, a.wfqyzjjgdm,a.fddbrxm,"+
			"a.xzcfrqdate ,a.bz ,date_format(a.xzcfrqdate,'%Y-%m-%d') as xzcfrq ,a.xzcfly from h_xz_punishment a  where isdelete = 0 " ;
		if(xzcfjgdm!=null&&!xzcfjgdm.equals("")){
			sql +=" and xzcfjgdm ='"+xzcfjgdm+"'";
		}
		if(selectValue!=null&&!"".equals(selectValue)){
			if(selectKey!=null){
					sql +=" and "+selectKey+" like '%"+selectValue+"%' ";	
			}
		}
		
		sql+=" order by a.xzcfrqdate desc,a.cfrxm ";
	       try {
				SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
				 q.addScalar("xzcfbh",StandardBasicTypes.INTEGER);
				 q.addScalar("xzcfjgdm",StandardBasicTypes.STRING);
				 q.addScalar("xzcfjdwh", StandardBasicTypes.STRING);
				 q.addScalar("ajmc", StandardBasicTypes.STRING);
				 q.addScalar("cfrxm", StandardBasicTypes.STRING);
				 q.addScalar("wfqyzjjgdm", StandardBasicTypes.STRING);
				 q.addScalar("fddbrxm", StandardBasicTypes.STRING);
				 q.addScalar("xzcfrqdate", StandardBasicTypes.DATE);
				 q.addScalar("xzcfrq", StandardBasicTypes.STRING);
				 q.addScalar("bz", StandardBasicTypes.STRING);
				 q.addScalar("xzcfly",StandardBasicTypes.INTEGER);
				// q.addScalar("xzqh", StandardBasicTypes.STRING);
				q.setResultTransformer(Transformers.aliasToBean(HXzPunishment.class));
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
	public int countSameNameByWh(String whname, Integer xzcfbh,Integer isdelete) {
		StringBuffer sql = new StringBuffer("select count(*) from h_xz_punishment where isdelete = "+isdelete+" and xzcfjdwh='"+whname+"' ");
	    if(xzcfbh!=null){
	    	sql.append(" and xzcfbh <> "+xzcfbh+"  ");
	    	
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
	public HXzPunishment selectXzPunishmentXq(int xzcfbh) {
		String sql = "select a.*,b.orgname as xzcfjgmc,c.TASKNAME as xzcfname ,"
				+ " d.cardname as xzcfcardtypename ,e.cfdxname as xzcftypename,date_format(a.xzcfrqdate,'%Y-%m-%d') as xzcfrq ,a.xzcfly"
				+ " from h_xz_punishment a left join  h_jc_gljg b"
				+ "  on a.xzcfjgdm=b.gljgdm left join h_xz_task c on a.xzcfitemid=c.ITEM_ID left join h_xz_cardtype d"
				+ " on a.xzcfcardtype=d.typebh left join h_xz_cfdxtype e on e.typebh=a.xzcftype where a.xzcfbh="+xzcfbh ;
	     try {
					SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
					 q.addScalar("xzcfbh",StandardBasicTypes.INTEGER);
					 q.addScalar("xzcfjgdm",StandardBasicTypes.STRING);
					 q.addScalar("xzcfjdwh", StandardBasicTypes.STRING);
					 q.addScalar("ajmc", StandardBasicTypes.STRING);
					 q.addScalar("cfrxm", StandardBasicTypes.STRING);
					 q.addScalar("wfqyzjjgdm", StandardBasicTypes.STRING);
					 q.addScalar("fddbrxm", StandardBasicTypes.STRING);
					 q.addScalar("xzcfrqdate", StandardBasicTypes.DATE);
					 q.addScalar("xzcfrq", StandardBasicTypes.STRING);
					 q.addScalar("zywfss", StandardBasicTypes.STRING);
					 q.addScalar("xzcf", StandardBasicTypes.STRING);
					 q.addScalar("xzcfzl", StandardBasicTypes.STRING);
					 q.addScalar("xzcfjgmc", StandardBasicTypes.STRING);
					 q.addScalar("bz", StandardBasicTypes.STRING);
					 q.addScalar("ouGuid", StandardBasicTypes.STRING);
					 q.addScalar("ouName", StandardBasicTypes.STRING);
					 q.addScalar("state", StandardBasicTypes.INTEGER);
					 q.addScalar("isdelete", StandardBasicTypes.INTEGER);
					 q.addScalar("xzcftype", StandardBasicTypes.STRING);
					 q.addScalar("xzcfcardtype", StandardBasicTypes.STRING);
					 q.addScalar("xzcfcardnumber", StandardBasicTypes.STRING);
					 q.addScalar("legalmanIdcard", StandardBasicTypes.STRING);
					 q.addScalar("xzcfitemid", StandardBasicTypes.STRING);
					// q.addScalar("xzcfjgmc", StandardBasicTypes.STRING);
					 q.addScalar("xzcfname", StandardBasicTypes.STRING);
					 q.addScalar("xzcftypename", StandardBasicTypes.STRING);
					 q.addScalar("xzcfcardtypename", StandardBasicTypes.STRING);
					 q.addScalar("xzcfly",StandardBasicTypes.INTEGER);
					q.setResultTransformer(Transformers.aliasToBean(HXzPunishment.class));
					List<HXzPunishment> lists = q.list();
					 if (lists!=null&&lists.size() > 0) {
							return lists.get(0);
					 }else{
							return null;
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
	
	
	}

	@Override
	public void updateState(Integer state, int xzcfbh) {
		String sql = "update h_xz_punishment set state ="+state+" where xzcfbh ="+xzcfbh;
		super.update(new SQL(sql));
		
	}

	@Override
	public Integer selectState(int xzcfbh) {
		String sql = "select state from h_xz_punishment where xzcfbh="+xzcfbh;
		return (Integer) super.findUnique(new SQL(sql));
	}

	@Override
	public Object selectValueByKey(String key,int xzcfbh) {
		String sql = "select "+key+" from h_xz_punishment where xzcfbh="+xzcfbh;
		return super.findUnique(new SQL(sql));
	}

	@Override
	public List<HXzPunishment> selectXzlistByState(int state) {
		String sql = "select a.*,b.gljgmc as xzcfjgmc ,date_format(a.xzcfrqdate,'%Y-%m-%d') as xzcfrq  from h_xz_punishment a left join  h_jc_gljg b"
				+ "  on a.xzcfjgdm=b.gljgdm where a.state="+state ;
	     try {
					SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
					 q.addScalar("xzcfbh",StandardBasicTypes.INTEGER);
					 q.addScalar("xzcfjgdm",StandardBasicTypes.STRING);
					 q.addScalar("xzcfjdwh", StandardBasicTypes.STRING);
					 q.addScalar("ajmc", StandardBasicTypes.STRING);
					 q.addScalar("cfrxm", StandardBasicTypes.STRING);
					 q.addScalar("wfqyzjjgdm", StandardBasicTypes.STRING);
					 q.addScalar("fddbrxm", StandardBasicTypes.STRING);
					 q.addScalar("xzcfrqdate", StandardBasicTypes.DATE);
					 q.addScalar("xzcfrq", StandardBasicTypes.STRING);
					 q.addScalar("zywfss", StandardBasicTypes.STRING);
					 q.addScalar("xzcf", StandardBasicTypes.STRING);
					 q.addScalar("xzcfzl", StandardBasicTypes.STRING);
					 q.addScalar("xzcfjgmc", StandardBasicTypes.STRING);
					 q.addScalar("bz", StandardBasicTypes.STRING);
					 q.addScalar("ouGuid", StandardBasicTypes.STRING);
					 q.addScalar("ouName", StandardBasicTypes.STRING);
					 q.addScalar("state", StandardBasicTypes.INTEGER);
					 q.addScalar("isdelete", StandardBasicTypes.INTEGER);
					 q.setResultTransformer(Transformers.aliasToBean(HXzPunishment.class));
					 return q.list();
					
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
	}

	@Override
	public void updateStateAll(Integer state, String zxwhlist) {
		String sql = "update h_xz_punishment set state ="+state+" where xzcfjdwh in ("+zxwhlist+")";
		super.update(new SQL(sql));
	}

	@Override
	public Integer selectcountSameByWh(String whname,
			Integer isdelete) {
		String sql = "select xzcfbh from h_xz_punishment where isdelete = "+isdelete+" and xzcfjdwh='"+whname+"' ";
		return (Integer) super.findUnique(new SQL(sql));
	}

	@Override
	public void updateStateByxzwh(Integer state, String wh) {
		String sql = "update h_xz_punishment set state ="+state+" where xzcfjdwh ='"+wh+"'";
		super.update(new SQL(sql));
		
	}

	@Override
	public List<selectListBean> selectList(String tablename, String keycolumn,
			String valuecolumn) {
		  String  sql=String.format("select %s as idObj,%s as name from %s ", keycolumn,valuecolumn,tablename);
		  try {
				SQLQuery q = getCurrentSession().createSQLQuery(sql);
				 q.addScalar("idObj",StandardBasicTypes.STRING);
				 q.addScalar("name",StandardBasicTypes.STRING);
				 q.setResultTransformer(Transformers.aliasToBean(selectListBean.class));
				 return q.list();
		  } catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
	}

	@Override
	public void updateDataVersion() {
		String sql = "update h_xz_punishment set dateversion= dateversion+1 where state>0 and  xzcfjgdm ='402881fb4c2a9bb8014c2a9c43c00000' ";
		super.executeSql(new SQL(sql));
		}
		

	@Override
	public void updateStatesByCondition(int state, String condition) {
		String sql = String.format("update h_xz_punishment set state= %d where 1=1 '%s' and xzcfjgdm ='402881fb4c2a9bb8014c2a9c43c00000' ",state,condition);
		super.executeSql(new SQL(sql));
		}




}
