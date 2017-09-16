package com.huzhouport.statistic.action;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
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
import com.huzhouport.statistic.model.ReportModel;

@SuppressWarnings("deprecation")
public class FixExcel {

	public static InputStream creatChartBar(List<ReportModel> dataList) throws IOException {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int j = 0; j < dataList.size(); j++) {
			dataset.addValue(dataList.get(j).getAmount(), dataList.get(j)
					.getOneName(), dataList.get(j).getTwoName());
		}

		JFreeChart chart = ChartFactory.createBarChart("定期签证航次报告统计图", "时间段",
				"统计数", dataset, PlotOrientation.VERTICAL, true, false, false);

		/*************** A start *********/
		// 设置标题字体样式
		TextTitle textTitle = chart.getTitle();
		textTitle.setFont(new Font("黑体", Font.PLAIN, 20));
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
		}
		/*************** A end *********/
		String root = ServletActionContext.getServletContext().getRealPath(GlobalVar.REPORT_FIX_IMAGE);
		File oFile = new File(root);
		ChartUtilities.saveChartAsJPEG(oFile, chart, GlobalVar.REPORT_WIDTH, GlobalVar.REPORT_HEIGHT);
		InputStream in = null;
		in = new FileInputStream(oFile);
		return in;
	}

	public static InputStream ExportDataToExcleNew(List<ReportModel> dataList,ReportModel reportModel) throws IOException {
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
		HSSFSheet sheet = workbook.createSheet("定期签证航次报告统计表");
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 3);
		sheet.addMergedRegion(cellRangeAddress);
		sheet.setColumnWidth(0, 6000);
		sheet.setColumnWidth(1, 6000);
		sheet.setColumnWidth(2, 6000);
		sheet.setColumnWidth(3, 6000);

		int sum_total = 0;
		for (int i = 0; i < dataList.size(); i++) {
			sum_total += dataList.get(i).getAmount();
		}

		// 第一行
		// 在索引0的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow(0);
		// 在索引0的位置创建单元格（左上端）
		HSSFCell cell = row.createCell(0);
		// 定义单元格为字符串类型
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setCellStyle(cellStyle);
		// 在单元格中输入一些内容
		cell.setCellValue(new HSSFRichTextString("定期签证航次报告统计表"));
		// 第二行
		row = sheet.createRow(1);
		row.createCell(0).setCellValue("报表生成时间：");
		cellRangeAddress = new CellRangeAddress(1, 1, 1, 3);
		sheet.addMergedRegion(cellRangeAddress);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		row.createCell(1).setCellValue(sdf.format(now));
		// 第三行
		row = sheet.createRow(2);
		row.createCell(0).setCellValue("统计时间段：");
		cellRangeAddress = new CellRangeAddress(2, 2, 1, 3);
		sheet.addMergedRegion(cellRangeAddress);
		row.createCell(1).setCellValue(reportModel.getBeginTime());
		// 第四行
		row = sheet.createRow(3);
		row.createCell(0).setCellValue("统计内容：");
		row.createCell(1).setCellValue("定期签证航次报告数据统计");
		// 第五行
		row = sheet.createRow(4);
		cellRangeAddress = new CellRangeAddress(4, 20, 0, 3);
		sheet.addMergedRegion(cellRangeAddress);
		BufferedImage bufferImg = null;
		ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
		String imagePasth = ServletActionContext.getServletContext()
				.getRealPath(GlobalVar.REPORT_FIX_IMAGE);

		bufferImg = ImageIO.read(new File(imagePasth));
		int ImageWidth = bufferImg.getWidth();
		ImageIO.write(bufferImg, "jpg", byteArrayOut);
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, ImageWidth, 255,
				(short) 0, 4, (short) 3, 20);
		patriarch.createPicture(anchor, workbook.addPicture(byteArrayOut
				.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));

		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(2); // 保留到小数点后几位

		row = sheet.createRow(21);
		row.createCell(0).setCellValue("统计总数：");
		row.createCell(1).setCellValue(sum_total);

		row = sheet.createRow(22);
		row.createCell(0).setCellValue("统计原则：");
		if(reportModel.getEndTime().equals("1"))
			row.createCell(1).setCellValue("按季统计");
		if(reportModel.getEndTime().equals("2"))
			row.createCell(1).setCellValue("按月统计");
		
		for (int i = 0; i < dataList.size(); i++) {
			row = sheet.createRow(i + 23);
			float countNumber=dataList.get(i).getAmount();
			row.createCell(1).setCellValue(dataList.get(i).getTwoName());
			row.createCell(2).setCellValue(countNumber);
			if(sum_total==0){
				row.createCell(3).setCellValue(0);
			}
			else{
				row.createCell(3).setCellValue(
						nf.format(countNumber * 1.0 / sum_total * 1.0));
			}
			
		}

		String root = ServletActionContext.getServletContext().getRealPath(GlobalVar.REPORT_FIX_EXCEL);
		File file = new File(root);// 创建一个文件对象
		OutputStream os = new FileOutputStream(file);// 创建一个输出对象
		workbook.write(os);// 把建好的Excel放入输出流
		os.close();// 关闭输出流
		InputStream in = null;
		in = new FileInputStream(file);
		return in;

	}



}
