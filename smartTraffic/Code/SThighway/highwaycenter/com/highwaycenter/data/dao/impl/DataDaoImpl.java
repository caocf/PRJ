package com.highwaycenter.data.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.SQL;
import com.highwaycenter.data.dao.DataDao;
import com.highwaycenter.data.model.Snapshot;

@Repository("datadao")
public class DataDaoImpl extends BaseDaoDB<Snapshot> implements DataDao{

	@Override
	public BaseQueryRecords selectDataState(int page, int rows) {
		String sql = "SELECT b.* ,DATE_FORMAT(b.date,'%Y-%m-%d %H:%m:%s') as datestr from snapshot b join ( select d.id ,d.stepName ,max(d.date) as maxdate from snapshot  as d GROUP by stepName "
				+ " ) t on b.stepName= t.stepName and t.maxdate=b.date group by  b.stepName";
	       
	       try {
				SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
				 q.addScalar("id",StandardBasicTypes.INTEGER);
				 q.addScalar("date",StandardBasicTypes.DATE);
				 q.addScalar("transName", StandardBasicTypes.STRING);
				 q.addScalar("stepName",StandardBasicTypes.STRING);
				 q.addScalar("stepCopy", StandardBasicTypes.INTEGER);
				 q.addScalar("totalLinesRead", StandardBasicTypes.INTEGER);
				 q.addScalar("totalLinesWritten", StandardBasicTypes.INTEGER);
				 q.addScalar("totalLinesInput", StandardBasicTypes.INTEGER);
				 q.addScalar("totalLinesOutput", StandardBasicTypes.INTEGER);
				 q.addScalar("totalLinesOutput", StandardBasicTypes.INTEGER);
				 q.addScalar("totalLinesRejected", StandardBasicTypes.INTEGER);
				 q.addScalar("totalErrors", StandardBasicTypes.INTEGER);
				 q.addScalar("timeDifference", StandardBasicTypes.INTEGER);
				 q.addScalar("linesRead", StandardBasicTypes.INTEGER);
				 q.addScalar("linesWritten", StandardBasicTypes.INTEGER);
				 q.addScalar("linesInput", StandardBasicTypes.INTEGER);
				 q.addScalar("linesOutput", StandardBasicTypes.INTEGER);
				 q.addScalar("linesUpdated", StandardBasicTypes.INTEGER);
				 q.addScalar("linesRejected", StandardBasicTypes.INTEGER);
				 q.addScalar("errors", StandardBasicTypes.INTEGER);
				 q.addScalar("inputBufferSize", StandardBasicTypes.INTEGER);
				 q.addScalar("outputBufferSize", StandardBasicTypes.INTEGER);
				 q.addScalar("datestr",StandardBasicTypes.STRING);
				 q.setResultTransformer(Transformers.aliasToBean(Snapshot.class));
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
	public Date selectCurrentMaxTime(String transName) {
		String sql = "select max(date) from snapshot where left(transName,2)='"+transName+"'";
		return (Date) super.findUnique(new SQL(sql));
	}


	/*@Override
	public SnapshotRecord selectTotalLine(String transName) {
		String sql = "SELECT sum(b.totalLinesRead) as totalLinesRead ,sum(b.totalLinesWritten)/2 as totalLinesWritten,sum(b.totalLinesUpdated)  as totalLinesUpdated"
				+ ",sum(b.totalLinesRejected) as totalLinesRejected,DATE_FORMAT(b.date,'%Y-%m-%d %H:%m:%s') as finishtime from snapshot b join ( select d.id ,d.stepName ,max(d.date) as maxdate from snapshot  as d GROUP by stepName "
				+ " ) t on b.stepName= t.stepName and t.maxdate=b.date where left(b.transName,2)='"+transName+"'  group by  b.stepName";
		 try {
				SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
				 q.addScalar("totalLinesRead",StandardBasicTypes.INTEGER);
				 q.addScalar("totalLinesWritten",StandardBasicTypes.INTEGER);
				 q.addScalar("totalLinesUpdated", StandardBasicTypes.INTEGER);
				 q.addScalar("totalLinesRejected",StandardBasicTypes.INTEGER);
				 q.addScalar("finishtime", StandardBasicTypes.STRING);
				 q.setResultTransformer(Transformers.aliasToBean(SnapshotRecord.class));
				 List list = q.list();
				 if(list!=null&&list.size()>0){
					 return (SnapshotRecord) q.list().get(0);
				 }else{
					 return null;
				 }
				 
		 } catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
	}
*/

	@Override
	public void deleteModuleData(String transName) {
		String sql = "delete from snapshot where left(b.transName,2)='"+transName+"'";
		super.delete(new SQL(sql));
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<String> selectRunTransname() {
		String sql = "select TRANSNAME from h_snope_tranlog where STATUS ='start' ";
		 try {
				SQLQuery q = getCurrentSession().createSQLQuery(sql.toString());
				return q.list();
		 } catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
						
	}





}
