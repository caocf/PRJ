package com.edb.edit.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;


import com.edb.edit.dao.EditDao;
import com.edb.edit.model.ColumnComment;
import com.edb.edit.model.KeyValue;
import com.edb.edit.model.Table;


public class EditService {
	EditDao editDao=new EditDao();



	public List<Table> queryAllTables() {
		return this.editDao.queryAllTables();
	}

	public List<?> queryColumnByTableName(String name) {
		return this.editDao.queryColumnByTableName(name);
	}

	public List<?> queryContentByTableName(String name, int page, int num) {
		return this.editDao.queryContentByTabeName(name, page, num);
	}

	public List<?> queryOneRow(String name,String rowid)
	{
		return this.editDao.queryOneRow(name, rowid);
	}
	
	public String addContent(String table,List<KeyValue> d) {
		return this.editDao.addContent(table, d);
	}

	public String delete(String table, String rowid) {
		return this.editDao.delete(table, rowid);
	}

	public String updateContent(String table, List<KeyValue> colum,
			String rowid) {
		return this.editDao.updateContent(table, colum, rowid);
	}
	
	public int count(String tableName)
	{
		return this.editDao.count(tableName);
	}

	public InputStream downloadExcel(String tableName) {
		List<?> r = queryColumnByTableName(tableName);

		if (r == null)
			return null;

		List<ColumnComment> data = new ArrayList<ColumnComment>();

		for (Object[] o : (List<Object[]>) r) {
			data.add((ColumnComment) o[1]);
		}

		try {
			return ExportDataToExcleNew(data, tableName);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return null;
	}

	public InputStream ExportDataToExcleNew(List<ColumnComment> dataList,
			String tableName) throws IOException {
		// 创建新的Excel 工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 设置字体
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 14);

		// 设置样式
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		// 在Excel工作簿中建一工作表，其名为缺省值
		HSSFSheet sheet = workbook.createSheet(tableName + "表数据模板");

		// 第一行
		// 在索引0的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow(0);

		for (int i = 0; i < dataList.size(); i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(dataList.get(i).getColumnName());
		}

		HSSFRow row2 = sheet.createRow(1);

		for (int i = 0; i < dataList.size(); i++) {
			HSSFCell cell = row2.createCell(i);
			cell.setCellValue(dataList.get(i).getColumnComment());
		}

		String root = ServletActionContext.getServletContext().getRealPath(
				"Report/" + tableName + ".xls");
		File file = new File(root);// 创建一个文件对象
		OutputStream os = new FileOutputStream(file);// 创建一个输出对象
		workbook.write(os);// 把建好的Excel放入输出流
		os.close();// 关闭输出流
		InputStream in = null;
		in = new FileInputStream(file);

		return in;
	}

	public Workbook createWorkBook(InputStream is, String excelFileFileName) throws Exception {
		if (excelFileFileName.toLowerCase().endsWith("xls")) {
			return new HSSFWorkbook(is);
		} else if (excelFileFileName.toLowerCase().endsWith("xlsx")) {
			return new XSSFWorkbook(is);
		} else {
			throw new Exception("请传入exl文件");
		}
	}
	protected String inputDataType(Row ros,int i){
		String o="";
		if(null == ros.getCell(i)){
			return "";
		}else if (ros.getCell(i).getCellType()==1){
			return ros.getCell(i).getStringCellValue();
		}else if(ros.getCell(i).getCellType()==3){
			return "";			
		}else if(ros.getCell(i).getCellType()==0){
			return String.valueOf((int)(ros.getCell(i).getNumericCellValue()));
		}
		return o;
	}

	
	public String inputChannel(File excelFile,String excelFileFileName,String tableName) throws Exception {

		String result="";
		List<?> r = queryColumnByTableName(tableName);

		if (r == null)
			return null;

		List<ColumnComment> data = new ArrayList<ColumnComment>();

		for (Object[] o : (List<Object[]>) r) {
			data.add((ColumnComment) o[1]);
		}
		
		
		Workbook book = createWorkBook(new FileInputStream(excelFile), excelFileFileName);
		
		Sheet sheet = book.getSheetAt(0);// 获得工作簿对象
		Row ros =sheet.getRow(0);
		
		for (int i = 2; i <= sheet.getLastRowNum(); i++) {
			ros = sheet.getRow(i);
			
			List<KeyValue> param=new ArrayList<KeyValue>();
			
			for(int j=0;j<data.size();j++)
			{
				KeyValue temp=new KeyValue();
				temp.setKey(data.get(j).getColumnName());
				temp.setValue(inputDataType(ros, j).toString());
				
				param.add(temp);
			}
			
			result=this.editDao.addContent(tableName, param);
		}
		return result;
	}
	
	
	public List<?> queryUserTablePrivsByTabeName(String loginName,String name) throws Exception {
		return this.editDao.queryUserTablePrivsByTabeName(loginName.toUpperCase(),name);
	}
	
}
