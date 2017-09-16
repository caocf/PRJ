package com.huzhouport.statistic.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.huzhouport.illegal.model.IllegalReason;
import com.huzhouport.statistic.dao.StatisticDao;
import com.huzhouport.statistic.model.DepartmentReport;
import com.huzhouport.statistic.model.ReportModel;
@SuppressWarnings("unchecked")
public class StatisticDaoImpl implements StatisticDao {
	protected HibernateTemplate hibernateTemplate;

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public List<IllegalReason> CountReason() throws Exception {
		String hql = "from IllegalReason";
		return this.hibernateTemplate.find(hql);
	}

	public List<Object[]> CountIllegal(ReportModel reportModel)
			throws Exception {
		String hql="";
		if(reportModel.getCondition()==2){
			hql = "select count(*),i.illegalCategories,i.illegalReason,ir.reasonName from Illegal as i,IllegalReason ir " +
			"where i.illegalTime between '"+ reportModel.getBeginTime() + "'" + " and '"
			+ reportModel.getEndTime() + "' and ir.reasonId=i.illegalReason group by i.illegalReason, i.illegalCategories";
		}else{
			hql = "select count(*),i.illegalCategories,i.illegalReason,ir.reasonName from Illegal as i,IllegalReason ir  " +
			"where i.illegalTime between '"+ reportModel.getBeginTime() + "'" + " and '"
			+ reportModel.getEndTime() + "' and i.reviewResult=1 and ir.reasonId=i.illegalReason group by i.illegalReason, i.illegalCategories";
		}
		
		return this.hibernateTemplate.find(hql);
	}
	public List<?> ElectByYear() throws Exception{
		String hql="select YEAR(elc.reportTime) from ElectricReport as elc";
		return this.hibernateTemplate.find(hql);
	}
	 public int CountElectByQuarter(int quarter,ReportModel reportModel) throws Exception{
		 String hql = "select elc from ElectricReport as elc where YEAR(elc.reportTime)="
				+ reportModel.getBeginTime()+ " and QUARTER(elc.reportTime)="+ quarter;
		return this.hibernateTemplate.find(hql).size();
	 }
	 public int CountElectByMonth(int month,ReportModel reportModel) throws Exception{
		 String hql = "select elc from ElectricReport as elc where YEAR(elc.reportTime)="
				+ reportModel.getBeginTime()+ " and MONTH(elc.reportTime)="+ month;
		return this.hibernateTemplate.find(hql).size();
	 
	 }
	  public List<?>  FixByYear() throws Exception{
			String hql="select YEAR(rv.reportTime) from RegularVisa as rv";
			return this.hibernateTemplate.find(hql);	
	  }
	  public int CountFixByQuarter(int quarter,ReportModel reportModel) throws Exception{
		 String hql = "select rv from RegularVisa as rv where YEAR(rv.reportTime)="
					+ reportModel.getBeginTime()+ " and QUARTER(rv.reportTime)="+ quarter;
		return this.hibernateTemplate.find(hql).size();
	 }
	 public int CountFixByMonth(int month,ReportModel reportModel) throws Exception{
		 String hql = "select rv from RegularVisa as rv where YEAR(rv.reportTime)="
				+ reportModel.getBeginTime()+ " and MONTH(rv.reportTime)="+ month;
		return this.hibernateTemplate.find(hql).size();
	 
	 }
	 public List<?> LeaveByPartTime() throws Exception{
		 String hql = "select MAX(lo.LeaveOrOtDate),MIN(lo.LeaveOrOtDate)  from LeaveOrOt as lo";
		 return this.hibernateTemplate.find(hql);
		 
	 }
	 public  List<Object[]> CountLeaveDate(ReportModel reportModel,int userId) throws Exception {
		String hql = "select sum(lo.lastDate),lok.kindType from LeaveOrOt as lo,LeaveOrOtKind as lok where lo.leaveOrOtKind=lok.kindID" +
					" and ( lo.beginDate  BETWEEN '"+ reportModel.getBeginTime()+ "' and '"+ reportModel.getEndTime()+"'"
					+ " or lo.endDate BETWEEN '"+ reportModel.getBeginTime()+ "' and '"+ reportModel.getEndTime()+"' )" +
					" and lo.leaveOrOtUser="+ userId+" group by lok.kindType";
		return this.hibernateTemplate.find(hql);
	 }
	 private List<Integer> didlist;
		public void FindDepartment(List<Integer> did){
			String hql="select d.departmentId from Department d where d.partOfDepartmentId="+did.get(0);
			for(int i=1;i<did.size();i++){
				hql+=" or d.partOfDepartmentId="+did.get(i);
			}
			List<Integer> list=this.hibernateTemplate.find(hql);
			if(list!=null){
				didlist.addAll(list);
				if(list.size()>0){
					FindDepartment(list);
				}
			}
			
		}	 
	 public  List<Object[]> CountLeaveDateByDepartment(DepartmentReport departmentReport) throws Exception {
			String hql = "select sum(lo.lastDate),u.userName,u.userId,lok.kindType from LeaveOrOt as lo,LeaveOrOtKind as lok,User u  where lo.leaveOrOtKind=lok.kindID" +
			" and ( lo.beginDate  BETWEEN '"+ departmentReport.getStartTime()+ "' and '"+ departmentReport.getToTime()+"'"
			+ " or lo.endDate BETWEEN '"+ departmentReport.getStartTime()+ "' and '"+ departmentReport.getToTime()+"' )" +
			" and lo.leaveOrOtUser=u.userId ";
			if(departmentReport.getDepartmentId()!=0){
				didlist=new ArrayList<Integer>();
				didlist.add(departmentReport.getDepartmentId());
				FindDepartment(didlist);
				for(int i=0;i<didlist.size();i++){
					if(i==0){
						hql+=" and (u.partOfDepartment="+didlist.get(i);
					}else if(i==didlist.size()-1){
						hql+=" or u.partOfDepartment="+didlist.get(i)+")";
					}else{
						hql += "or u.partOfDepartment="+didlist.get(i);
					}
				}
				
			}
				hql += " group by u.userName,u.userId,lok.kindType order by u.userId desc";
			
			return this.hibernateTemplate.find(hql);
		 }
	 //打印
	 public  List<?> printPersonData(ReportModel reportModel) throws Exception {
			String hql = "select lo.beginDate,lo.endDate,lo.lastDate,lok.kindType,lok.kindName,lo.address from LeaveOrOt as lo,LeaveOrOtKind as lok where lo.leaveOrOtKind=lok.kindID" +
					" and ( lo.beginDate  BETWEEN '"+ reportModel.getBeginTime()+ "' and '"+ reportModel.getEndTime()+"'"
					+ " or lo.endDate BETWEEN '"+ reportModel.getBeginTime()+ "' and '"+ reportModel.getEndTime()+"' ) and lo.leaveOrOtUser="+ reportModel.getAmount();
			return this.hibernateTemplate.find(hql);
		 }

	public List<?> CountDraftDate(ReportModel reportModel)throws Exception {
		 String hql="";
		if(reportModel.getOneName()!=null&&!reportModel.getOneName().equals("")&&!reportModel.getOneName().equals("null")){
			hql = "select sum(e.draft) from ElectricReportNew e where e.draftTime  BETWEEN '"+ reportModel.getBeginTime()+ 
			"' and '"+ reportModel.getEndTime()+"' and e.shipName='"+reportModel.getOneName()+"' ";
		}else{
			 hql+="select sum(e.draft),e.shipName from ElectricReportNew e where e.draftTime  BETWEEN '"+ reportModel.getBeginTime()+ 
			 "' and '"+ reportModel.getEndTime()+"'  group by e.shipName ";
		
		}
		 return this.hibernateTemplate.find(hql);
	}

	public List<?> DraftByBoat() throws Exception {
		 String hql = "select DISTINCT shipName  from ElectricReportNew e ";
		 return this.hibernateTemplate.find(hql);
	}
}
