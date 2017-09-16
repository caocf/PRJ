package com.huzhouport.electric.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.transaction.annotation.Transactional;

import com.huzhouport.common.service.impl.BaseServiceImpl;
import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.common.util.QueryConditionSentence;
import com.huzhouport.electric.dao.ChannelDao;
import com.huzhouport.electric.model.Channel;
import com.huzhouport.electric.service.ChannelService;

public class ChannelServiceImpl extends BaseServiceImpl<Channel> implements
		ChannelService {

	private QueryConditionSentence qcs = new QueryConditionSentence();// 封装条件
	private ChannelDao channelDao;

	public void setChannelDao(ChannelDao channelDao) {
		this.channelDao = channelDao;
	}

	public Map<String, Integer> countPageChannelInfo(Channel channel,
			int pageSize) throws Exception {
		Map<String, Integer> map = new HashMap<String, Integer>();
		int total = this.channelDao.countPageChannelInfo(channel, qcs
				.QCS(channel.getQueryCondition()));
		map.put("allTotal", total);
		int pages = calculateTotalPage(total, pageSize); // 获取计算分页页数
		map.put("pages", pages);
		return map;
	}

	public List<?> searchChannelInfo(Channel channel, int pageNo, int pageSize)
			throws Exception {
		int beginIndex = (pageNo - 1) * pageSize;
		return this.channelDao.searchChannelInfo(channel, qcs.QCS(channel
				.getQueryCondition()), qcs
				.Sequence(channel.getQueryCondition()), beginIndex, pageSize);
	}
//	@Transactional
	public String inputChannel(Channel channel, File excelFile,
			String excelFileFileName) throws Exception {
		Workbook book = createWorkBook(new FileInputStream(
				excelFile), excelFileFileName);
		Sheet sheet = book.getSheetAt(0);// 获得工作簿对象
		Row ros = null;// 获得表头行	
		Channel cl=null;
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			ros = sheet.getRow(i);
			cl=new Channel();
			cl.setRiversCode(String.valueOf(inputDataType(ros,0)));
			cl.setRiverName(String.valueOf(inputDataType(ros,1)));
			cl.setChannelCoding(String.valueOf(inputDataType(ros,2)));
			cl.setChannelName(String.valueOf(inputDataType(ros,3)));
			cl.setReportUnit(String.valueOf(inputDataType(ros,4)));
			cl.setAdministrativeArea(String.valueOf(inputDataType(ros,5)));
			cl.setStartingName(String.valueOf(inputDataType(ros,6)));
			cl.setStartingMileage(String.valueOf(inputDataType(ros,7)));
			cl.setStartingPointmain(String.valueOf(inputDataType(ros,8)));
			cl.setStartingPointoff(String.valueOf(inputDataType(ros,9)));
			cl.setStartingPointtype(String.valueOf(inputDataType(ros,10)));
			cl.setEndingName(String.valueOf(inputDataType(ros,11)));
			cl.setEndingMileage(String.valueOf(inputDataType(ros,12)));
			cl.setEndingPointmain(String.valueOf(inputDataType(ros,13)));
			cl.setEndingPointoff(String.valueOf(inputDataType(ros,14)));
			cl.setEndingPointotype(String.valueOf(inputDataType(ros,15)));
			cl.setWhetherSegment(String.valueOf(inputDataType(ros,16)));
			cl.setSegmentCategories(String.valueOf(inputDataType(ros,17)));
			cl.setCountriesName(String.valueOf(inputDataType(ros,18)));
			cl.setAdjacentAdministrative(String.valueOf(inputDataType(ros,19)));
			cl.setOrganizationName(String.valueOf(inputDataType(ros,20)));
			cl.setManagementAdministrative(String.valueOf(inputDataType(ros,21)));
			cl.setOrganizationNamed(String.valueOf(inputDataType(ros,22)));
			cl.setManagementAdministratived(String.valueOf(inputDataType(ros,23)));
			cl.setOrganizationNamef(String.valueOf(inputDataType(ros,24)));
			cl.setManagementAdministrativef(String.valueOf(inputDataType(ros,25)));
			cl.setAuxiliarySegment(String.valueOf(inputDataType(ros,26)));
			cl.setBottleneckSection(String.valueOf(inputDataType(ros,27)));
			cl.setMajorShoals(String.valueOf(inputDataType(ros,28)));
			cl.setRepeatSegment(String.valueOf(inputDataType(ros,29)));
			cl.setSegmentmileage(String.valueOf(inputDataType(ros,30)));
			cl.setChannelDepth(String.valueOf(inputDataType(ros,31)));
			cl.setChannelWidth(String.valueOf(inputDataType(ros,32)));
			cl.setChannelRadius(String.valueOf(inputDataType(ros,33)));
			cl.setSegmentTechnology(String.valueOf(inputDataType(ros,34)));
			cl.setSegmentClassification(String.valueOf(inputDataType(ros,35)));
			cl.setSegmentAttributes(String.valueOf(inputDataType(ros,36)));
			cl.setChannelOrganization(String.valueOf(inputDataType(ros,37)));
			cl.setInstitutionsAdministrativearea(String.valueOf(inputDataType(ros,38)));
			cl.setSegmentNavigation(String.valueOf(inputDataType(ros,39)));
			cl.setWaterAssurance(String.valueOf(inputDataType(ros,40)));
			cl.setChannelSpecial(String.valueOf(inputDataType(ros,41)));
			cl.setChannelSeasonal(String.valueOf(inputDataType(ros,42)));
			cl.setSegmentType(String.valueOf(inputDataType(ros,43)));
			cl.setCategoryCloth(String.valueOf(inputDataType(ros,44)));
			cl.setSegmentVessel(String.valueOf(inputDataType(ros,45)));
			cl.setCoordinates(String.valueOf(inputDataType(ros,46)));
			cl.setCoding(String.valueOf(inputDataType(ros,47)));
			cl.setChangeReason(String.valueOf(inputDataType(ros,48)));
			cl.setPicture(String.valueOf(inputDataType(ros,49)));
			cl.setScope(String.valueOf(inputDataType(ros,50)));
			this.channelDao.save(cl);
		}
		return null;
	}
	protected Object inputDataType(Row ros,int i){
		Object o="";
		/*System.out.println(ros.getCell(i).getCellType());*/
		if(null == ros.getCell(i)){
			return "";
		}else if (ros.getCell(i).getCellType()==1){
			return ros.getCell(i).getStringCellValue();
		}else if(ros.getCell(i).getCellType()==3){
			return "";			
		}else if(ros.getCell(i).getCellType()==0){
			return String.valueOf(ros.getCell(i).getNumericCellValue());
		}
		return o;
	}
		// 判断文件类型
		public Workbook createWorkBook(InputStream is, String excelFileFileName)
				throws Exception {
			if (excelFileFileName.toLowerCase().endsWith("xls")) {
				return new HSSFWorkbook(is);
			}else if (excelFileFileName.toLowerCase().endsWith("xlsx")) {
				return new XSSFWorkbook(is);
			}else{
				throw new Exception("请传入exl文件");
			}
//			return null;
		}
	public InputStream getChannelModelInputStream() throws Exception {
		String toOneExcelDst = ServletActionContext.getServletContext()
				.getRealPath(GlobalVar.EXCELTEMPLETEPATH + "\\channel.xls");
		return new FileInputStream(new File(toOneExcelDst));
	}

}
