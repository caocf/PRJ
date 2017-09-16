package com.huzhouport.statistic.action;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.struts2.ServletActionContext;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

import com.huzhouport.common.util.GlobalVar;
import com.huzhouport.statistic.model.DepartmentReport;
import com.huzhouport.statistic.model.ReportModel;

@SuppressWarnings("deprecation")
public class LeaveAndOvertimeExcel {

	public static InputStream creatChartBar(List<HashMap<String,Object>> dataList,String Name) throws IOException {		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue((Float) dataList.get(0).get("leave"),dataList.get(0).get("name").toString() , "请假");
		dataset.addValue((Float)dataList.get(0).get("work"),dataList.get(0).get("name").toString() , "加班");
		dataset.addValue((Float)dataList.get(0).get("business"),dataList.get(0).get("name").toString(), "出差");
		
		JFreeChart chart = ChartFactory.createBarChart(Name+"考勤统计图", null,
				"统计天数", dataset, PlotOrientation.VERTICAL, true, false, false);

		/*************** A start *********/
		// 设置标题字体样式
		TextTitle textTitle = chart.getTitle();
		textTitle.setFont(new Font("黑体", Font.PLAIN, 20));
		textTitle.setMargin(20, 10,10,10);
		// 设置柱状体颜色
		CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
		categoryplot.getRenderer().setSeriesPaint(0, GlobalVar.CategoryPlot_ONE_COLOR);
		//区内背景
		categoryplot.setBackgroundPaint(Color.white);
		//虚线颜色
		categoryplot.setRangeGridlinePaint(GlobalVar.CPLINE);
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		CategoryAxis domainAxis = categoryplot.getDomainAxis();
		//轴线颜色
		numberaxis.setTickMarkPaint(GlobalVar.XYLINE);
		domainAxis.setTickMarkPaint(GlobalVar.XYLINE);
		// 设置X轴坐标上的字体样式
		domainAxis.setTickLabelFont(new Font("黑体", Font.PLAIN, 14));
		// 设置X轴的标题字体样式
		domainAxis.setLabelFont(new Font("黑体", Font.PLAIN, 14));
		// 设置Y轴坐标上的字体样式
		numberaxis.setTickLabelFont(new Font("黑体", Font.PLAIN, 14));
		// 设置Y轴的标题字体样式
		numberaxis.setLabelFont(new Font("黑体", Font.PLAIN, 14));
		// 设置图片最底部字体样式
		if (chart.getLegend() != null) {
			chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 14));
			chart.getLegend().setBorder(1,1,1,1);
			chart.getLegend().setMargin(10, 10,20,10);		
		}
		/*************** A end *********/
		String root = ServletActionContext.getServletContext().getRealPath(
				GlobalVar.REPORT_LEAVE_IMAGE);
		File oFile = new File(root);
		ChartUtilities.saveChartAsJPEG(oFile, chart, GlobalVar.REPORT_WIDTH, GlobalVar.REPORT_HEIGHT);			
		InputStream in = null;
		in = new FileInputStream(oFile);
		return in;
	}

	public static InputStream ExportDataToExcleNew(List<HashMap<String,Object>> dataList,ReportModel reportModel, String userName) throws IOException {
		// 创建新的Excel 工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 设置字体
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 14);
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font2.setFontHeightInPoints((short) 12);

		// 设置样式
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		// 设置其他样式
		HSSFCellStyle cell_name_Style = workbook.createCellStyle();
		cell_name_Style.setFont(font2);
		cell_name_Style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		// 在Excel工作簿中建一工作表，其名为缺省值
		HSSFSheet sheet = workbook.createSheet("考勤表");
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 4);
		sheet.addMergedRegion(cellRangeAddress);
		sheet.setColumnWidth(0, 6000);
		sheet.setColumnWidth(1, 6000);
		sheet.setColumnWidth(2, 6000);
		sheet.setColumnWidth(3, 6000);
		sheet.setColumnWidth(4, 6000);

		// 第一行
		// 在索引0的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow(0);
		// 在索引0的位置创建单元格（左上端）
		HSSFCell cell = row.createCell(0);
		// 定义单元格为字符串类型
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		// 在单元格中输入一些内容
		cell.setCellValue(new HSSFRichTextString(userName+"个人考勤表"));
		// 第二行
		row = sheet.createRow(1);
		row.createCell(0).setCellValue("导出时间：");
		cellRangeAddress = new CellRangeAddress(1, 1, 1, 4);
		sheet.addMergedRegion(cellRangeAddress);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		row.createCell(1).setCellValue(sdf.format(now));
		// 第三行
		row = sheet.createRow(2);
		row.createCell(0).setCellValue("统计时间段：");
		cellRangeAddress = new CellRangeAddress(2, 2, 1, 3);
		sheet.addMergedRegion(cellRangeAddress);
		row.createCell(1).setCellValue(reportModel.getBeginTime()+ "至" + reportModel.getEndTime().split(" ")[0]);
		// 第四行
		row = sheet.createRow(3);
		cell=row.createCell(0);cell.setCellValue("姓名");cell.setCellStyle(cell_name_Style);
		cell=row.createCell(1);cell.setCellValue("请假(天)");cell.setCellStyle(cell_name_Style);
		cell=row.createCell(2);cell.setCellValue("加班(天)");cell.setCellStyle(cell_name_Style);
		cell=row.createCell(3);cell.setCellValue("出差(天)");cell.setCellStyle(cell_name_Style);
		cell=row.createCell(4);cell.setCellValue("合计(天)");cell.setCellStyle(cell_name_Style);
		for (int i = 0; i < dataList.size(); i++) {
			row = sheet.createRow(i + 4);
			row.createCell(0).setCellValue(dataList.get(i).get("name").toString());
			row.createCell(1).setCellValue(dataList.get(i).get("leave").toString());
			row.createCell(2).setCellValue(dataList.get(i).get("work").toString());
			row.createCell(3).setCellValue(dataList.get(i).get("business").toString());
			row.createCell(4).setCellValue(dataList.get(i).get("total").toString());
		}

		String root = ServletActionContext.getServletContext().getRealPath(GlobalVar.REPORT_LEAVE_EXCEL);
		File file = new File(root);// 创建一个文件对象
		OutputStream os = new FileOutputStream(file);// 创建一个输出对象
		workbook.write(os);// 把建好的Excel放入输出流
		os.close();// 关闭输出流
		InputStream in = null;
		in = new FileInputStream(file);
		return in;

	}
	public static InputStream ExportByDepartment(DepartmentReport departmentReport) throws IOException {
		// 创建新的Excel 工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();

		// 设置字体
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 14);
		HSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font2.setFontHeightInPoints((short) 12);

		// 设置标题样式
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		
		// 设置其他样式
		HSSFCellStyle cell_name_Style = workbook.createCellStyle();
		cell_name_Style.setFont(font2);
		cell_name_Style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		// 在Excel工作簿中建一工作表，其名为缺省值
		HSSFSheet sheet = workbook.createSheet("考勤表");
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 4);
		sheet.addMergedRegion(cellRangeAddress);
		sheet.setColumnWidth(0, 6000);
		sheet.setColumnWidth(1, 6000);
		sheet.setColumnWidth(2, 6000);
		sheet.setColumnWidth(3, 6000);
		sheet.setColumnWidth(4, 6000);

		// 第一行
		// 在索引0的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow(0);
		// 在索引0的位置创建单元格（左上端）
		HSSFCell cell = row.createCell(0);
		// 定义单元格为字符串类型
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		// 在单元格中输入一些内容
		cell.setCellValue(new HSSFRichTextString(departmentReport.getDeparmentName()+"考勤表"));
		// 第二行
		row = sheet.createRow(1);
		row.createCell(0).setCellValue("导出时间：");
		cellRangeAddress = new CellRangeAddress(1, 1, 1, 4);
		sheet.addMergedRegion(cellRangeAddress);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		row.createCell(1).setCellValue(sdf.format(now));
		// 第三行
		row = sheet.createRow(2);
		row.createCell(0).setCellValue("统计时间段：");
		cellRangeAddress = new CellRangeAddress(2, 2, 1, 4);
		sheet.addMergedRegion(cellRangeAddress);
		row.createCell(1).setCellValue(departmentReport.getStartTime()+ "至" + departmentReport.getToTime().split(" ")[0]);
		// 第四行
		row = sheet.createRow(3);
		cell=row.createCell(0);cell.setCellValue("姓名");cell.setCellStyle(cell_name_Style);
		cell=row.createCell(1);cell.setCellValue("请假(天)");cell.setCellStyle(cell_name_Style);
		cell=row.createCell(2);cell.setCellValue("加班(天)");cell.setCellStyle(cell_name_Style);
		cell=row.createCell(3);cell.setCellValue("出差(天)");cell.setCellStyle(cell_name_Style);
		cell=row.createCell(4);cell.setCellValue("合计(天)");cell.setCellStyle(cell_name_Style);
		// 第五行

		for (int i = 0; i < departmentReport.getList_person().size(); i++) {
			row = sheet.createRow(i + 4);
			row.createCell(0).setCellValue(departmentReport.getList_person().get(i).get("name").toString());
			row.createCell(1).setCellValue(departmentReport.getList_person().get(i).get("leave").toString());
			row.createCell(2).setCellValue(departmentReport.getList_person().get(i).get("work").toString());
			row.createCell(3).setCellValue(departmentReport.getList_person().get(i).get("business").toString());
			row.createCell(4).setCellValue(departmentReport.getList_person().get(i).get("total").toString());
		}
		row = sheet.createRow(4+departmentReport.getList_person().size());
		row.createCell(0).setCellValue("合计");
		row.createCell(1).setCellValue(departmentReport.getList_department().get(0).get("leave").toString());
		row.createCell(2).setCellValue(departmentReport.getList_department().get(0).get("work").toString());
		row.createCell(3).setCellValue(departmentReport.getList_department().get(0).get("business").toString());
		row.createCell(4).setCellValue(departmentReport.getList_department().get(0).get("total").toString());
		
		String root = ServletActionContext.getServletContext().getRealPath(GlobalVar.REPORT_LEAVE_EXCEL);
		File file = new File(root);// 创建一个文件对象
		OutputStream os = new FileOutputStream(file);// 创建一个输出对象
		workbook.write(os);// 把建好的Excel放入输出流
		os.close();// 关闭输出流
		InputStream in = null;
		in = new FileInputStream(file);
		return in;

	}

	public static float GetDayByHOVER(float lastDate) {
		float dlong = 0;

		if (lastDate >= 8) {
			int d =(int) (lastDate / 8); // 去商
			int l = (int)(lastDate % 8); // 去余
			if (l == 0) {
				dlong = d;
			} else {
				dlong = (float) (d+0.5);
			}
		} else {
			dlong =(float) 0.5;
		}
		return dlong;
	}
	public String GetDayByHOVER2(float lastDate) {
		String dlong = "";

		if (lastDate >= 8) {
			int d =(int) (lastDate / 8); // 去商
			int l = (int)(lastDate % 8); // 去余
			if (l == 0) {
				dlong = d + "天";
			} else {
				dlong = d + "天半";
			}
		} else {
			dlong = "半天";
		}
		return dlong;
	}

}
