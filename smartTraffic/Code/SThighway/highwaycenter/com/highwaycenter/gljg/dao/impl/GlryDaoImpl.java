package com.highwaycenter.gljg.dao.impl;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.common.action.Constants;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.highwaycenter.bean.GlryInfo;
import com.highwaycenter.gljg.dao.GlryDao;
import com.highwaycenter.gljg.model.HJcGlbm;
import com.highwaycenter.gljg.model.HJcGlry;

@Repository("glrydao")
public class GlryDaoImpl extends BaseDaoDB<HJcGlry> implements GlryDao{

	@Override
	public HJcGlry findById(int glry_id) {
		
		return super.findUnique(new HJcGlry(), "rybh",glry_id);
	}

	@Override
	public BaseQueryRecords findByKey(String key, Object value, int page,
			int rows) {
		
		return super.find(new HJcGlry(), key, value, page, rows);
	}

	@Override
	public void save(HJcGlry glry) {
		super.save(glry);
		
	}
	
	@Override
	public Integer saveAndReturn(HJcGlry glry) {
		return (Integer) super.saveAndReturn(glry);
		
	}

	@Override
	public void update(HJcGlry glry) {
		super.update(glry);
		
	}

	@Override
	public void delete(HJcGlry glry) {
		super.delete(glry);
		
	}

	@Override
	public HJcGlry findByKey(String key, Object value) {
		return super.findUnique(new HJcGlry(),key,value );
	}

	@Override
	public HJcGlry findByName(String username) {
		return this.findByKey("ryxm",username);
	}

	@Override
	public HJcGlry findBySjch(String sjch) {
		return this.findByKey("sjch",sjch);
	}

	@Override
	public int selectCountByGlbm(HJcGlbm glbm) {
		String sql = "select count(*) from h_jc_rybm where bmbh ='"+glbm.getBmdm()+"'";
		return ((BigInteger)super.find(new SQL(sql)).getData().get(0)).intValue();
		
	}

	@Override
	public HJcGlry findByBhAndSjch(int rybh, String sjch) {
		try {
			Criteria criteria = super.getCriteria(new HJcGlry());

			criteria.add(Restrictions.ne("rybh", rybh));   //人员编号不为其本身
			criteria.add(Restrictions.eq("sjch", sjch));
			List<HJcGlry> list = criteria.list();
			if(list.size()>0){
				return (HJcGlry)list.get(0);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	
	@Override
	public HJcGlry findByBhAndRyxm(int rybh, String ryxm) {
		try {
			Criteria criteria = super.getCriteria(new HJcGlry());

			criteria.add(Restrictions.ne("rybh", rybh));   //人员编号不为其本身
			criteria.add(Restrictions.eq("ryxm", ryxm));
			List<HJcGlry> list = criteria.list();
			if(list!=null&&list.size()>0){
				return (HJcGlry)list.get(0);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public GlryInfo selectRyInfo(int rybh) {
	    String sql = "select a.rybh,a.ryxm,a.xmqp,a.xmpyszm,a.sjch,a.sjdh,d.jsbh,d.jsmc from  h_jc_glry as a  "+
                "left join h_jc_ryjs as c on a.rybh = c.rybh left join h_jc_jbjsb d on c.jsbh = d.jsbh "
   		+"where a.rybh ="+rybh;
		/*String hqlstring = "select new com.highwaycenter.bean.GlryInfo(a.rybh,a.HJcGlbm.bmdm,a.HJcGlbm.bmmc,"+
	     "a.ryxm,a.xmqp,a.xmpyszm,a.sjch,a.sjdh,a.zw,a.bgsdh,c.id.HJcJbjsb.jsbh,c.id.HJcJbjsb.jsmc) "+
		 " from HJcGlry a , HJcRyjs c where a.rybh =c.id.HJcGlry.rybh and a.rybh = ? ";
		HQL hql = new HQL(hqlstring,rybh);*/
	    try {
			SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
			 q.addScalar("rybh",StandardBasicTypes.INTEGER);
			 /*q.addScalar("bmdm",StandardBasicTypes.STRING);
			 q.addScalar("bmmc", StandardBasicTypes.STRING);*/
			 q.addScalar("ryxm",StandardBasicTypes.STRING);
			 q.addScalar("xmqp", StandardBasicTypes.STRING);
			 q.addScalar("xmpyszm", StandardBasicTypes.STRING);
			 q.addScalar("sjch", StandardBasicTypes.STRING);
			 q.addScalar("sjdh", StandardBasicTypes.STRING);
			 
			
			 q.addScalar("jsbh", StandardBasicTypes.INTEGER);
			 q.addScalar("jsmc", StandardBasicTypes.STRING);
			 q.setResultTransformer(Transformers.aliasToBean(GlryInfo.class));
			 List<GlryInfo> lists = q.list();
			 if (lists!=null&&lists.size() > 0) {
					return lists.get(0);
			 }else{
					return null;
			}
		
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
			return null;
	}

	@Override
	public BaseQueryRecords selectGlryListNoBm(int page, int rows) {
		String hqlstring = "select new com.highwaycenter.bean.GlryInfo(a.rybh,"+
			     "a.ryxm,a.xmqp,a.xmpyszm,a.sjch,a.sjdh,a.zw,a.bgsdh,c.id.HJcJbjsb.jsbh,c.id.HJcJbjsb.jsmc) "+
				 " from HJcGlry a,HJcGlbm b,HJcRyjs c where a.rybh =c.id.HJcGlry.rybh";
				HQL hql = new HQL(hqlstring);
		 
		return this.find(hql, page, rows);
	}

	@Override
	public BaseQueryRecords selectGlryListByProterty(int page, int rows,
			String key, Object value) {
		String hqlstring ="select new com.highwaycenter.bean.GlryInfo(a.rybh,a.HJcGlbm.bmdm,a.HJcGlbm.bmmc,"+
			     "a.ryxm,a.xmqp,a.xmpyszm,a.sjch,a.sjdh,a.zw,a.bgsdh) "+
				 " from HJcGlry a,HJcGlbm b where a.HJcGlbm.bmdm = b.bmdm and a."+key+" = '" +value +"'";
		HQL hql = new HQL(hqlstring);
		return this.find(hql, page, rows);
	}

	@Override  
	public BaseQueryRecords selectGlryListByProtertys(int page, int rows,
			List<String> keys, List<Object> values) {
		StringBuffer hqlstring = new StringBuffer("select new com.highwaycenter.bean.GlryInfo( rybh, HJcGlbm.bmdm, HJcGlbm.bmmc,"+
			     " ryxm, xmqp, xmpyszm, sjch, sjdh, zw, bgsdh) "+
				 " from HJcGlry a where 1=1 ");
		if(keys.size()>0){
			for(int i=0;i<keys.size();i++){
				if(keys.get(i).equals("xmpyszm")){
					hqlstring.append(" and "+ keys.get(i) + " like '" +values.get(i).toString()+"%'" );	
				}else{
				    hqlstring.append(" and "+ keys.get(i) + " = '" +values.get(i).toString()+"'" );
				}
			}
		}
		System.out.println(hqlstring.toString());	  
		HQL hql = new HQL(hqlstring.toString());
		return this.find(hql, page, rows);
	}
	

	@Override
	public BaseQueryRecords selectGlryListNoJS(int page, int rows) {
		String hqlstring = "select new com.highwaycenter.bean.GlryInfo(a.rybh,a.HJcGlbm.bmdm,a.HJcGlbm.bmmc,"+
			     "a.ryxm,a.xmqp,a.xmpyszm,a.sjch,a.sjdh,a.zw,a.bgsdh) "+
				 " from HJcGlry a,HJcGlbm b where a.HJcGlbm.bmdm = b.bmdm  ";
	    HQL hql = new HQL(hqlstring);
		 
		return this.find(hql, page, rows);
	}

	@Override
	public BaseQueryRecords selectGlryListNoBMJS(int page, int rows) {
		String hqlstring = "select new com.highwaycenter.bean.GlryInfo(a.rybh,"+
			     "a.ryxm,a.xmqp,a.xmpyszm,a.sjch,a.sjdh,a.zw,a.bgsdh) "+
				 " from HJcGlry a where a.HJcGlbm.bmdm = b.bmdm  ";
	    HQL hql = new HQL(hqlstring);
		 
		return this.find(hql, page, rows);
	}

	@Override
	public String selectRymm(Integer rybn) {
		String sqlstring = "select rymm from h_jc_glry where rybh = " +rybn;
		SQL sql = new SQL(sqlstring);
		return (String) super.findUnique(sql);
	}

	@Override
	public BaseQueryRecords selectGlryList(int page, int rows, String bmlist ,
			String xmpyszm,String selectvalue,int fjgFlag) {
		/*String sqlorder = " left join "
			+"(select * from "
			+ "(select a.rybh ,b.bmdm,b.bmmc,b.ordercolumn from h_jc_ryzwgx as a,h_jc_zw as"
				+ " b where a.zwbh=b.zwbh )as t "+
				 " where not exists  "
				 + "( select 1 from "+
				  " (select a.rybh,b.zwbh,b.zwmc,b.ordercolumn from"
				  + " h_jc_ryzwgx as a,h_jc_zw as b "
				  + "where a.zwbh=b.zwbh ) as "
				  + "q where q.rybh=t.rybh and q.ordercolumn>t.ordercolumn)) as z on z.rybh=a.rybh ";*/
		
/*        String bmsql = "select distinct rybh from h_jc_rybm where bmbh in ("+bmlist+") ";		
		
		String sqlorder ="left join (select * from (select * from h_jc_rybm )as t  where not exists  "
				+"( select 1 from (select * from h_jc_rybm ) as q where q.rybh=t.rybh and q.ryordercolumn>t.ryordercolumn) group by rybh)"
		        +" as z on a.rybh=z.rybh left join h_jc_glbm e on z.bmbh=e.bmdm ";
        StringBuffer sql =new StringBuffer( "select a.rybh,a.ryxm,a.xmqp,a.xmpyszm,a.sjch,a.sjdh,a.zw,a.bgsdh,d.jsbh,d.jsmc from  h_jc_glry as a  "+
	                 " left join h_jc_ryjs as c on a.rybh = c.rybh left join h_jc_jbjsb d on c.jsbh = d.jsbh "
        		+sqlorder+" where a.ryxm not like 'admin%' and a.rybh <> "+Constants.SUPERMANAGER_RYBH+" ");  */
        String sqlmain = "select * from (select * from h_jc_rybm  where bmbh in ("+bmlist+") ) as t "
        		+ "where not exists  ( select 1 from (select rybmbh, ryordercolumn from h_jc_rybm where bmbh in ("+bmlist+") )"
        				+ " as q where q.rybmbh=t.rybmbh   and q.ryordercolumn>t.ryordercolumn) group by t.rybh ) ";
		StringBuffer sql =new StringBuffer("select a.*,d.jsbh,d.jsmc from ( "+sqlmain+" as b left join h_jc_glry as a on a.rybh=b.rybh left join "
				            + "h_jc_ryjs as c on b.rybh=c.rybh left join  h_jc_jbjsb d on c.jsbh=d.jsbh left join h_jc_glbm e on "
				            + " b.bmbh=e.bmdm where 1=1 and  a.ryxm not like '"+Constants.SUPERMANAGER_RYNAME +"'  and a.rybh <>  " + Constants.SUPERMANAGER_RYBH  );
        
        
        	/*if(bmlist!=null&&!bmlist.equals("")){
        		sql.append(" and a.rybh in ("+bmsql+") ");
        	}*/
        		
       
		if(xmpyszm!=null&&!xmpyszm.trim().equals("")){
			sql.append(" and left(xmqp,1) ='"+xmpyszm+"'");
		}

		if(selectvalue!=null&&!selectvalue.trim().equals("")){
			sql.append(" and ( a.ryxm like '%"+selectvalue+"%' or a.sjch like '%"+selectvalue+"%' or a.sjdh like '%"+selectvalue+"%')");
		}
		
		sql.append(" order by e.ordercolumn desc ,b.ryordercolumn desc,a.xmpyszm");
		
/*		BaseQueryRecords  records=  super.find(new SQL(sql.toString()),page,rows);
        List<Object[]> list = (List<Object[]>) records.getData();
       if(list!=null&&list.size()>0){
    	  Iterator<Object[]> it =  list.iterator();
    	  while(it.hasNext()){
    		  GlryInfo ryinfo = new GlryInfo();
    		  Object[] ob = it.next();
    		  ryinfo.setRybh((Integer)ob[0]);
    		  ryinfo.setBmdm((String)ob[1]);
    		  ryinfo.setBmmc((String)ob[2] );
    		  ryinfo.setRyxm((String)ob[3] );
    		  ryinfo.setXmqp((String)ob[4] );
    		  ryinfo.setXmpyszm((String)ob[5]);
    		  ryinfo.setSjch((String)ob[6] );
    		  ryinfo.setSjdh((String)ob[7] );
    		  ryinfo.setZw((String)ob[8] );
    		  ryinfo.setBgsdh((String)ob[9] );
    		  ryinfo.setJsbh((Integer)ob[10]);
    		  ryinfo.setJsmc((String)ob[11] );
    		  listryinfo.add(ryinfo);
    	  }

       }*/
       
	/*   BaseQueryRecords  records=
       records.setData(listryinfo);
       return records;*/
       
       try {
			SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
			 q.addScalar("rybh",StandardBasicTypes.INTEGER);
			// q.addScalar("bmdm",StandardBasicTypes.STRING);
			// q.addScalar("bmmc", StandardBasicTypes.STRING);
			 q.addScalar("ryxm",StandardBasicTypes.STRING);
			 q.addScalar("xmqp", StandardBasicTypes.STRING);
			 q.addScalar("xmpyszm", StandardBasicTypes.STRING);
			 q.addScalar("sjch", StandardBasicTypes.STRING);
			 q.addScalar("sjdh", StandardBasicTypes.STRING);	 
			 q.addScalar("jsbh", StandardBasicTypes.INTEGER);
			 q.addScalar("jsmc", StandardBasicTypes.STRING);
			q.setResultTransformer(Transformers.aliasToBean(GlryInfo.class));
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
	public HJcGlry findByUsername(String username) {
		String hql = "from HJcGlry where sjch ='"+username+"' or ryxm ='"+username+"'";
		HJcGlry glry = (HJcGlry) super.findUnique(new HQL(hql));
		return glry;
	}



	@Override
	public int countPhoneName(String sjch, String ryxm) {
		String sql = "select count(*) from h_jc_glry  where sjch ='"+sjch+"' or ryxm ='"+ryxm+"'";
		BigInteger rycount = (BigInteger) super.findUnique(new SQL(sql));
		int rycount_int =rycount.intValue();
		return rycount_int;
	}

	@Override
	public int countBhPhoneName(int rybh, String sjch, String ryxm) {
		String sql = "select count(*) from h_jc_glry  where (sjch ='"+sjch+"' or ryxm ='"+ryxm+"') and rybh <> "+rybh;
		BigInteger rycount = (BigInteger) super.findUnique(new SQL(sql));
		int rycount_int =rycount.intValue();
		return rycount_int;
	}

	
	
	

}
