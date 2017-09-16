package com.huzhouport.statistic.service.impl;

import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.illegal.model.IllegalReason;
import com.huzhouport.statistic.action.DraftExcel;
import com.huzhouport.statistic.action.ElectExcel;
import com.huzhouport.statistic.action.FixExcel;
import com.huzhouport.statistic.action.IllegalExcel;
import com.huzhouport.statistic.action.LeaveAndOvertimeExcel;
import com.huzhouport.statistic.dao.StatisticDao;
import com.huzhouport.statistic.model.DepartmentReport;
import com.huzhouport.statistic.model.ReportModel;
import com.huzhouport.statistic.service.StatisticService;


public class StatisticServiceImpl implements StatisticService{
     private StatisticDao statisticDao;

	public void setStatisticDao(StatisticDao statisticDao) {
		this.statisticDao = statisticDao;
	}
	//----------------------违章取证start----------------
	//违章数据-excel
	public InputStream IllegalReport(ReportModel reportModel) throws Exception{
		List<IllegalReason> reason=this.statisticDao.CountReason();
		List<ReportModel> list =ListOfIllegalReport(reportModel);		
		IllegalExcel.creatChartBar(list);
		InputStream	in=IllegalExcel.ExportDataToExcleNew(list,reason,reportModel);
		return in;
	}
	//违章数据-data
	public List<?> IllegalReportDate(ReportModel reportModel) throws Exception{
		List<IllegalReason> reason=this.statisticDao.CountReason();
		List<ReportModel> list =new ArrayList<ReportModel>();
		List<ArrayList<HashMap<String, Object>>> list2=new ArrayList<ArrayList<HashMap<String, Object>>>();
		ArrayList<HashMap<String, Object>> list3=new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> list4=new ArrayList<HashMap<String, Object>>();
		//int[] sumCount=new int[reason.size()*4];
		//int n=0;
		int sum_total = 0;
		int sum_GZ = 0, sum_YZ = 0, sum_HS = 0, sum_HZ = 0;
		List<Object[]> listdate =this.statisticDao.CountIllegal(reportModel);
		for(int i=0;i<listdate.size();i++){
			int sumCount = Integer.parseInt(listdate.get(i)[0].toString());

			ReportModel rm = new ReportModel();
			rm.setAmount(sumCount);
			sum_total += sumCount;
			if (listdate.get(i)[1].toString().equals("1")) {
				rm.setOneName("港航");
				sum_GZ += sumCount;
			} else if (listdate.get(i)[1].toString().equals("2")) {
				sum_YZ += sumCount;
				rm.setOneName("运政");
			} else if (listdate.get(i)[1].toString().equals("3")) {
				rm.setOneName("航政");
				sum_HZ += sumCount;
			} else if (listdate.get(i)[1].toString().equals("4")) {
				rm.setOneName("海事");
				sum_HS += sumCount;
			}
			rm.setTwoName(listdate.get(i)[3].toString());
			list.add(rm);
			
		}
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(2); // 保留到小数点后几位
		
		HashMap<String,Object> map0=new HashMap<String,Object>();
		map0.put("Sum", sum_total);
		list3.add(map0);
		HashMap<String,Object> map1=new HashMap<String,Object>();
		map1.put("Name", "港航");
		map1.put("Count", sum_GZ);
		if(sum_total==0){
			map1.put("Percentage",0);
		}
		else{
			map1.put("Percentage",nf.format(sum_GZ * 1.0 / sum_total * 1.0));
		}
		list3.add(map1);
		HashMap<String,Object> map2=new HashMap<String,Object>();
		map2.put("Name", "运政");
		map2.put("Count", sum_YZ);
		if(sum_total==0){
			map2.put("Percentage",0);
		}
		else{
		map2.put("Percentage", 	nf.format(sum_YZ * 1.0 / sum_total * 1.0));
		}
		list3.add(map2);
		HashMap<String,Object> map3=new HashMap<String,Object>();
		map3.put("Name", "航政");
		map3.put("Count", sum_HZ);
		if(sum_total==0){
			map3.put("Percentage",0);
		}
		else{
		map3.put("Percentage", 	nf.format(sum_HZ * 1.0 / sum_total * 1.0));
		}
		list3.add(map3);
		HashMap<String,Object> map4=new HashMap<String,Object>();
		map4.put("Name", "海事");
		map4.put("Count", sum_HS);
		if(sum_total==0){
			map4.put("Percentage",0);
		}
		else{
		map4.put("Percentage", 	nf.format(sum_HS * 1.0 / sum_total * 1.0));
		}
		list3.add(map4);
		list2.add(list3);
		//list3.clear();
		for (int i = 0; i < reason.size(); i++) {
			HashMap<String,Object> map=new HashMap<String,Object>();
			map.put("Name",reason.get(i).getReasonName());
			int reasonNumber = 0;
			for (int j = 0; j < list.size(); j++) {
				if (list.get(j).getTwoName().equals(
						reason.get(i).getReasonName()))
					reasonNumber += list.get(j).getAmount();
			}
			map.put("Count", reasonNumber);
			if(sum_total==0){
				map.put("Percentage",0);
			}
			else{
			map.put("Percentage",nf.format(reasonNumber * 1.0 / sum_total * 1.0));
			}
			list4.add(map);
		}
		list2.add(list4);
		return list2;
	
	}
	//违章取证的数据-List
	public List<ReportModel> ListOfIllegalReport(ReportModel reportModel) throws Exception {
		List<ReportModel> list =new ArrayList<ReportModel>();
		List<Object[]> listdate =this.statisticDao.CountIllegal(reportModel);
		for(int i=0;i<listdate.size();i++){
			int sumCount = Integer.parseInt(listdate.get(i)[0].toString());
			ReportModel rm = new ReportModel();
			rm.setAmount(sumCount);
			int type = Integer.parseInt(listdate.get(i)[1].toString().trim());
			if (type==1) {
				rm.setOneName("港航");
				
			} else if (type==2) {
			
				rm.setOneName("运政");
			} else if (type==3){
				rm.setOneName("航政");
				
			} else if (type==4) {
				rm.setOneName("海事");
			
			}else{
				rm.setOneName("错误数据");
			}
			rm.setTwoName(listdate.get(i)[3].toString());
			list.add(rm);
			
		}
		
		return list;
	}
	//违章图片
	public InputStream IllegalImage(ReportModel reportModel) throws Exception{
		//List<IllegalReason> reason=this.statisticDao.CountReason();
		List<ReportModel> list =ListOfIllegalReport(reportModel);		
		InputStream	in=IllegalExcel.creatChartBar(list);
		return in;
	}
	
	//----------------------违章取证end----------------
	//----------------------船舶航行电子报告start-----------
	//船舶航行电子报告-excel
	public InputStream ElectReport(ReportModel reportModel) throws Exception{
		List<ReportModel> list=ListOfElectReport(reportModel);
		ElectExcel.creatChartBar(list);
		InputStream	in=ElectExcel.ExportDataToExcleNew(list,reportModel);
		return in;
	}
	//船舶航行电子报告-跨年
	public List<?> ElectByYear() throws Exception{
		List<?> list=this.statisticDao.ElectByYear();
		if(list.size()>0){
			int minYear=Integer.parseInt(list.get(0).toString());
			int maxYear=Integer.parseInt(list.get(0).toString());
			for(int i=1;i<list.size();i++){
				int thisyear=Integer.parseInt(list.get(i).toString());
				if(minYear>thisyear)
					minYear=thisyear;
				if(maxYear<thisyear)
					maxYear=thisyear;
			}
			HashMap<String,Integer> map=new HashMap<String,Integer>();
			map.put("minYear", minYear);
			map.put("maxYear", maxYear);
			list.clear();
			List<HashMap<String,Integer>> list2=new ArrayList<HashMap<String,Integer>>();
			list2.add(map);
			return list2;
		}		
		return list;
	}
	//船舶航行电子报告-image
	public InputStream ElectImage(ReportModel reportModel) throws Exception {
		List<ReportModel> list=ListOfElectReport(reportModel);
		InputStream oStream=ElectExcel.creatChartBar(list);
		return oStream;
	}
	//船舶航行电子报告-date
	public List<?> ElectReportDate(ReportModel reportModel) throws Exception {		
		List<ReportModel> list=ListOfElectReport(reportModel);
		ArrayList<HashMap<String, Object>> list2=new ArrayList<HashMap<String, Object>>();
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(2); // 保留到小数点后几位
		int sum_total=0;
		for (int i = 0; i < list.size(); i++) {
			sum_total+=list.get(i).getAmount();
		}
		HashMap<String,Object> map1=new HashMap<String,Object>();
		map1.put("Sum",sum_total);			
		list2.add(map1);
		for (int i = 0; i < list.size(); i++) {
			HashMap<String,Object> map=new HashMap<String,Object>();	
			map.put("Name",list.get(i).getTwoName());	
			map.put("Count", list.get(i).getAmount());
			if(sum_total==0){
				map.put("Percentage",0);
			}
			else{
			map.put("Percentage",nf.format(list.get(i).getAmount() * 1.0 / sum_total * 1.0));
			}
			list2.add(map);
		}
		return list2;
	}
	//船舶航行电子报告的数据List
	public List<ReportModel> ListOfElectReport(ReportModel reportModel) throws Exception {
		List<ReportModel> list =new ArrayList<ReportModel>();
		//季
		if(reportModel.getEndTime().equals("1")){
			for(int j=1;j<5;j++){
				int sumCount=this.statisticDao.CountElectByQuarter(j,reportModel);
				ReportModel rm=new ReportModel();
				rm.setAmount(sumCount);
				rm.setOneName("船舶航行电子报告");				
				rm.setTwoName(GlobalVar.QUARTER[j-1]);
				list.add(rm);
			}
		}
		//月
		if(reportModel.getEndTime().equals("2")){
			for(int j=1;j<13;j++){
				int sumCount=this.statisticDao.CountElectByMonth(j,reportModel);
				ReportModel rm=new ReportModel();
				rm.setAmount(sumCount);
				rm.setOneName("船舶航行电子报告");				
				rm.setTwoName(GlobalVar.MONTH[j-1]);
				list.add(rm);
			}
		}
		return list;
	}
	//----------------------船舶航行电子报告end----------------
	//----------------------定期签证Start----------------
	//定期签证年份
	public List<?> FixByYear() throws Exception{
		List<?> list=this.statisticDao.FixByYear();
		if(list.size()>0){
			int minYear=Integer.parseInt(list.get(0).toString());
			int maxYear=Integer.parseInt(list.get(0).toString());
			for(int i=1;i<list.size();i++){
				int thisyear=Integer.parseInt(list.get(i).toString());
				if(minYear>thisyear)
					minYear=thisyear;
				if(maxYear<thisyear)
					maxYear=thisyear;
			}
			HashMap<String,Integer> map=new HashMap<String,Integer>();
			map.put("minYear", minYear);
			map.put("maxYear", maxYear);
			list.clear();
			List<HashMap<String,Integer>> list2=new ArrayList<HashMap<String,Integer>>();
			list2.add(map);
			return list2;
		}		
		return list;
	
	}
	//定期签证excel
	public InputStream FixReport(ReportModel reportModel) throws Exception{
		List<ReportModel> list=ListOfFixReport(reportModel);
		FixExcel.creatChartBar(list);
		InputStream	in=FixExcel.ExportDataToExcleNew(list,reportModel);
		return in;
	
	}
	//定期签证数据
	public List<?> DateOfFixReport(ReportModel reportModel) throws Exception{
		List<ReportModel> list=ListOfFixReport(reportModel);
		ArrayList<HashMap<String, Object>> list2=new ArrayList<HashMap<String, Object>>();
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(2); // 保留到小数点后几位
		int sum_total=0;
		for (int i = 0; i < list.size(); i++) {
			sum_total+=list.get(i).getAmount();
		}
		HashMap<String,Object> map1=new HashMap<String,Object>();
		map1.put("Sum",sum_total);			
		list2.add(map1);
		for (int i = 0; i < list.size(); i++) {
			HashMap<String,Object> map=new HashMap<String,Object>();	
			map.put("Name",list.get(i).getTwoName());	
			map.put("Count", list.get(i).getAmount());
			if(sum_total==0){
				map.put("Percentage",0);
			}
			else{
			map.put("Percentage",nf.format(list.get(i).getAmount() * 1.0 / sum_total * 1.0));
			}
			list2.add(map);
		}
		return list2;
	
	}
	//定期签证图片
	public InputStream FixImage(ReportModel reportModel) throws Exception{
		List<ReportModel> list=ListOfFixReport(reportModel);
		InputStream oStream=FixExcel.creatChartBar(list);
		return oStream;
	}
	//定期签证的数据List
	public List<ReportModel> ListOfFixReport(ReportModel reportModel) throws Exception {
		List<ReportModel> list =new ArrayList<ReportModel>();
		//季
		if(reportModel.getEndTime().equals("1")){
			for(int j=1;j<5;j++){
				int sumCount=this.statisticDao.CountFixByQuarter(j,reportModel);
				ReportModel rm=new ReportModel();
				rm.setAmount(sumCount);
				rm.setOneName("定期签证航次报告");				
				rm.setTwoName(GlobalVar.QUARTER[j-1]);
				list.add(rm);
			}
		}
		//月
		if(reportModel.getEndTime().equals("2")){
			for(int j=1;j<13;j++){
				int sumCount=this.statisticDao.CountFixByMonth(j,reportModel);
				ReportModel rm=new ReportModel();
				rm.setAmount(sumCount);
				rm.setOneName("定期签证航次报告");				
				rm.setTwoName(GlobalVar.MONTH[j-1]);
				list.add(rm);
			}
		}
		return list;
	}
	//----------------------定期签证end----------------
	//----------------------请假加班Start----------------
	//计算天数 4小时算0.5天。
	private float GetDayByHover(float hover){
		float dlong;
		if (hover >= 8) {
			float d = (int) (hover / 8); // 去商
			float l = hover % 8; // 去余
			if (l == 0) {
				dlong = d;
			} else {
				dlong = (float) (d+0.5);
			}
		} else if (hover >= 4){
			dlong = (float) 0.5;
		}else{
			dlong = 0;
		}
		return dlong;
		
	}
	//考勤管理时间段
	public List<?> LeaveByTime() throws Exception{
		return this.statisticDao.FixByYear();
		
	}
	//考勤管理excel
	public InputStream LeaveReport(ReportModel reportModel,int userId,String userName) throws Exception{
		List<HashMap<String,Object>> list=ListOfLeaveReport(reportModel,userId,userName);
		InputStream	in=LeaveAndOvertimeExcel.ExportDataToExcleNew(list,reportModel,userName);
		return in;
		
	}
	//考勤管理数据
	public List<?> DateOfLeaveReport(ReportModel reportModel,int userId,String userName) throws Exception{
		List<HashMap<String,Object>> list=ListOfLeaveReport(reportModel,userId,userName);
		return list;
		
	}
	//考勤管理图片
	public InputStream LeaveImage(ReportModel reportModel,int userId,String userName) throws Exception{
		List<HashMap<String,Object>> list=ListOfLeaveReport(reportModel,userId,userName);
		InputStream inputStream = LeaveAndOvertimeExcel.creatChartBar(list,userName);
		return inputStream;
		
	}
	//考勤管理的数据List
	public List<HashMap<String,Object>> ListOfLeaveReport(ReportModel reportModel,int userId,String userName) throws Exception {
		List<HashMap<String,Object>> list=new ArrayList<HashMap<String,Object>>();
		HashMap<String,Object> map=new HashMap<String,Object>();
		List<Object[]> Listsum=this.statisticDao.CountLeaveDate(reportModel,userId);
		float sumCount=0;
		map.put("leave", sumCount);
		map.put("work", sumCount);
		map.put("business", sumCount);
		map.put("name", userName);
		map.put("total", sumCount);
		if(Listsum!=null){
			for(int i=0;i<Listsum.size();i++){
				int type=Integer.parseInt(Listsum.get(i)[1].toString());
				float time=Float.parseFloat(Listsum.get(i)[0].toString());
				switch (type) {
				case 1:
					map.put("leave", GetDayByHover(time));
					break;
				case 2:
					map.put("work", GetDayByHover(time));
					break;
				case 3:
					map.put("business", GetDayByHover(time));
					break;
				default:
					break;
				}
				sumCount+=time;
			}
			map.put("total", GetDayByHover(sumCount));
		}
		list.add(map);
		return list;
	}
	//考勤管理打印
	 public  List<?> printPersonData(ReportModel reportModel) throws Exception{
		 return this.statisticDao.printPersonData(reportModel);
	 }
	 
	 
	// 考勤管理部门
	public DepartmentReport DateOfLeaveReportByDepartment(
			DepartmentReport departmentReport) throws Exception {
		return ListOfLeaveReportByDepartment(departmentReport);
	}
   //数据处理
	public DepartmentReport ListOfLeaveReportByDepartment(
			DepartmentReport departmentReport) throws Exception {
		List<HashMap<String,Object>> list_person = new ArrayList<HashMap<String,Object>>();
		List<HashMap<String,Object>> list_department = new ArrayList<HashMap<String,Object>>();
		
		List<Object[]> Listsum = this.statisticDao.CountLeaveDateByDepartment(departmentReport);
		if(Listsum!=null){
			String useronly="";
			float total=0,leaveCount=0,workCount=0,businessCount=0,totalCount=0;
			HashMap<String,Object> map = null;
			for (int i = 0; i < Listsum.size(); i++) {
				if(i==0){
					useronly=Listsum.get(i)[1].toString();
					map=new HashMap<String,Object>();
					map.put("name", Listsum.get(i)[1].toString());
					map.put("leave",0);
					map.put("work", 0);
					map.put("business", 0);
				}
				if(!useronly.equals(Listsum.get(i)[1].toString())){
					useronly=Listsum.get(i)[1].toString();
					map.put("total", total);
					list_person.add(map);
					map=new HashMap<String,Object>();
					total=0;
					map.put("name", Listsum.get(i)[1].toString());
					map.put("leave",0);
					map.put("work", 0);
					map.put("business", 0);
				}	
				int type=Integer.parseInt(Listsum.get(i)[3].toString());
				float time=Float.parseFloat(Listsum.get(i)[0].toString());
				time=GetDayByHover(time);
				switch (type) {
				case 1:
					map.put("leave", time);
					leaveCount+=time;
					break;
				case 2:
					map.put("work", time);
					workCount+=time;
					break;
				case 3:
					map.put("business", time);
					businessCount+=time;
					break;
				default:
					break;
				}
				total+=time;
				
			}
			if(map!=null){
			map.put("total", total);
			list_person.add(map);
			}
			map=new HashMap<String,Object>();
			map.put("name", departmentReport.getDeparmentName());
			map.put("leave",leaveCount);
			map.put("work",workCount);
			map.put("business", businessCount);
			totalCount=leaveCount+workCount+businessCount;
			map.put("total", totalCount);
			list_department.add(map);
		}	
			departmentReport.setList_person(list_person);
			departmentReport.setList_department(list_department);
		return departmentReport;
	}

	// 考勤管理图片
	public InputStream LeaveImageByDepartment(DepartmentReport departmentReport)
			throws Exception {
		DepartmentReport dp = ListOfLeaveReportByDepartment(departmentReport);
		InputStream oStream = LeaveAndOvertimeExcel.creatChartBar(dp.getList_department(),dp.getDeparmentName());
		return oStream;

	}

	// 部门考勤表
	public InputStream LeaveReportByDepartment(DepartmentReport departmentReport)
			throws Exception {
		DepartmentReport dp = ListOfLeaveReportByDepartment(departmentReport);
		InputStream	in=LeaveAndOvertimeExcel.ExportByDepartment(dp);
		return in;
	}
	//----------------------请假加班end----------------	
	 
	// ----------------------油耗数据Start----------------	
	// 油耗数据
		public List<?> DateOfDraftReport(ReportModel reportModel) throws Exception{

			List<ReportModel> list=ListOfDraftReport(reportModel);
			ArrayList<HashMap<String, Object>> list2=new ArrayList<HashMap<String, Object>>();
			NumberFormat nf = NumberFormat.getPercentInstance();
			nf.setMinimumFractionDigits(2); // 保留到小数点后几位
			int sum_total=0;
			for (int i = 0; i < list.size(); i++) {
				sum_total+=list.get(i).getAmount();
			}
			HashMap<String,Object> map1=new HashMap<String,Object>();
			map1.put("Sum",sum_total);			
			list2.add(map1);
			for (int i = 0; i < list.size(); i++) {
				HashMap<String,Object> map=new HashMap<String,Object>();	
				map.put("Name",list.get(i).getTwoName());	
				map.put("Count", list.get(i).getAmount());
				if(sum_total==0){
					map.put("Percentage",0);
				}
				else{
				map.put("Percentage",nf.format(list.get(i).getAmount() * 1.0 / sum_total * 1.0));
				}
				list2.add(map);
			}
			return list2;
			
		
		}
		//油耗数据List
		@SuppressWarnings("unchecked")
		public List<ReportModel> ListOfDraftReport(ReportModel reportModel) throws Exception {
			List<ReportModel> list =new ArrayList<ReportModel>();
			if(reportModel.getOneName()!=null&&!reportModel.getOneName().equals("")&&!reportModel.getOneName().equals("null")){
				List<?> Listsum=this.statisticDao.CountDraftDate(reportModel);
				float sumCount=0;
				if(Listsum!=null){
					if(Listsum.get(0)!=null)
						sumCount=Float.parseFloat(Listsum.get(0).toString());
				}
				ReportModel rm=new ReportModel();
				rm.setAmount(sumCount);
				rm.setOneName("油耗统计报告");
				rm.setTwoName(reportModel.getOneName());
				list.add(rm);
			}else{
				List<Object[]> Listsum=(List<Object[]>) this.statisticDao.CountDraftDate(reportModel);
				for(int j=0;j<Listsum.size();j++){
					ReportModel rm=new ReportModel();
					float sumCount=0;
					if(Listsum!=null){
						if(Listsum.get(j)[0]!=null)
							sumCount=Float.parseFloat(Listsum.get(j)[0].toString());
						rm.setTwoName(Listsum.get(j)[1].toString());
					}
					
					rm.setAmount(sumCount);
					rm.setOneName("油耗统计报告");
					list.add(rm);
				}
				
			}
				
			return list;
		}
		// 油耗的船舶列表
		public List<?> DraftByBoat() throws Exception{
			return this.statisticDao.DraftByBoat();
		}

		// 油耗excel
		public InputStream DraftReport(ReportModel reportModel) throws Exception{
			List<ReportModel> list=ListOfDraftReport(reportModel);
			DraftExcel.creatChartBar(list);
			InputStream	in=DraftExcel.ExportDataToExcleNew(list,reportModel);
			return in;
		}

		// 油耗图片
		public InputStream DraftImage(ReportModel reportModel) throws Exception{
			List<ReportModel> list=ListOfDraftReport(reportModel);
			InputStream oStream=DraftExcel.creatChartBar(list);
			return oStream;
		}
		//----------------------请假加班end----------------	
		 
}
