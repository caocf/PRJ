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
import com.huzhouport.illegal.model.IllegalReason;
import com.huzhouport.statistic.model.ReportModel;

@SuppressWarnings("deprecation")
public class IllegalExcel {
	public static InputStream creatChartBar(List<ReportModel> dataList) throws IOException {		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (int j = 0; j < dataList.size(); j++) {
			dataset.addValue(dataList.get(j).getAmount(), dataList.get(j)
					.getOneName(), dataList.get(j).getTwoName());
		}

		JFreeChart chart = ChartFactory.createBarChart("违章取证统计图", "违章案由",
				"统计数", dataset, PlotOrientation.VERTICAL, true, false, false);

		/*************** A start *********/
		// 设置标题字体样式
		TextTitle textTitle = chart.getTitle();
		textTitle.setFont(new Font("黑体", Font.PLAIN, 20));
		textTitle.setMargin(20, 10,10,10);
		// 设置柱状体颜色
		CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
		categoryplot.getRenderer().setSeriesPaint(0, GlobalVar.CategoryPlot_ONE_COLOR);
		categoryplot.getRenderer().setSeriesPaint(1, GlobalVar.CategoryPlot_TWO_COLOR);
		categoryplot.getRenderer().setSeriesPaint(2, GlobalVar.CategoryPlot_THREE_COLOR);
		categoryplot.getRenderer().setSeriesPaint(3, GlobalVar.CategoryPlot_FOUTH_COLOR);
		//区内背景
		categoryplot.setBackgroundPaint(Color.white);
		
		//虚线是否可见
		//categoryplot.setRangeGridlinesVisible(false);
		//虚线颜色
		categoryplot.setRangeGridlinePaint(GlobalVar.CPLINE);
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		CategoryAxis domainAxis = categoryplot.getDomainAxis();
		//轴线颜色
		numberaxis.setTickMarkPaint(GlobalVar.XYLINE);
		domainAxis.setTickMarkPaint(GlobalVar.XYLINE);
		// 设置X轴坐标上的字体样式
		domainAxis.setTickLabelFont(new Font("黑体", Font.PLAIN, 14));
		//domainAxis.setCategoryLabelPositions(AxisLocation.BOTTOM_OR_RIGHT);
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
				GlobalVar.REPORT_ILLEGAL_IMAGE);
		File oFile = new File(root);
		ChartUtilities.saveChartAsJPEG(oFile, chart, GlobalVar.REPORT_WIDTH, GlobalVar.REPORT_HEIGHT);			
		InputStream in = null;
		in = new FileInputStream(oFile);
		/*byte[] buf=ChartUtilities.encodeAsPNG(chart.createBufferedImage(GlobalVar.REPORT_WIDTH,GlobalVar.REPORT_HEIGHT));
		InputStream in=new ByteArrayInputStream(buf); */
		return in;
	}

	public static InputStream ExportDataToExcleNew(List<ReportModel> dataList,
			List<IllegalReason> resonList, ReportModel reportModel) throws IOException {
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
		HSSFSheet sheet = workbook.createSheet("违章取证统计表");
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0, 0, 0, 3);
		sheet.addMergedRegion(cellRangeAddress);
		sheet.setColumnWidth(0, 6000);
		sheet.setColumnWidth(1, 6000);
		sheet.setColumnWidth(2, 6000);
		sheet.setColumnWidth(3, 6000);

		int sum_total = 0;
		int sum_GZ = 0, sum_YZ = 0, sum_HS = 0, sum_HZ = 0;

		for (int i = 0; i < dataList.size(); i++) {
			sum_total += dataList.get(i).getAmount();
			if (dataList.get(i).getOneName().equals("港航"))
				sum_GZ += dataList.get(i).getAmount();
			if (dataList.get(i).getOneName().equals("运政"))
				sum_YZ += dataList.get(i).getAmount();
			if (dataList.get(i).getOneName().equals("航政"))
				sum_HZ += dataList.get(i).getAmount();
			if (dataList.get(i).getOneName().equals("海事"))
				sum_HS += dataList.get(i).getAmount();

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
		cell.setCellValue(new HSSFRichTextString("违章取证统计表"));
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
		row.createCell(1).setCellValue(
				reportModel.getBeginTime() + "至" + reportModel.getEndTime().split(" ")[0]);
		// 第四行
		row = sheet.createRow(3);
		row.createCell(0).setCellValue("统计内容范围：");
		if(reportModel.getCondition()==1)
		row.createCell(1).setCellValue("全部");
		if(reportModel.getCondition()==2)
			row.createCell(1).setCellValue("已审核通过");
		// 第五行
		row = sheet.createRow(4);
		cellRangeAddress = new CellRangeAddress(4, 20, 0, 3);
		sheet.addMergedRegion(cellRangeAddress);
		BufferedImage bufferImg = null;
		ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
		String imagePasth = ServletActionContext.getServletContext()
				.getRealPath(GlobalVar.REPORT_ILLEGAL_IMAGE);

		bufferImg = ImageIO.read(new File(imagePasth));
		int ImageWidth = bufferImg.getWidth();
		ImageIO.write(bufferImg, "jpg", byteArrayOut);
		/*System.out.println("-----无图片-----");
		ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
		InputStream inStream=creatChartBar(dataList);
		byte[] buff = new byte[1024];  
        int rc = 0;  
        while ((rc = inStream.read(buff, 0, buff.length)) > 0) {  
        	byteArrayOut.write(buff, 0, rc);  
        }  
    
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 510, 255,
				(short) 0, 4, (short) 3, 20);*/
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, ImageWidth, 255,
				(short) 0, 4, (short) 3, 20);
		patriarch.createPicture(anchor, workbook.addPicture(byteArrayOut
				.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
		System.out.println("-----图片end-----");
		NumberFormat nf = NumberFormat.getPercentInstance();
		nf.setMinimumFractionDigits(2); // 保留到小数点后几位

		row = sheet.createRow(21);
		row.createCell(0).setCellValue("统计总数：");
		row.createCell(1).setCellValue(sum_total);

		row = sheet.createRow(22);
		row.createCell(0).setCellValue("执法类别统计：");

		row = sheet.createRow(23);
		row.createCell(1).setCellValue("港航");
		row.createCell(2).setCellValue(sum_GZ);
		if(sum_total==0){
			row.createCell(3).setCellValue(0);
		}
		else{
			row.createCell(3).setCellValue(
					nf.format(sum_GZ * 1.0 / sum_total * 1.0));
		}
		
		row = sheet.createRow(24);
		row.createCell(1).setCellValue("运政");
		row.createCell(2).setCellValue(sum_YZ);
		if(sum_total==0){
			row.createCell(3).setCellValue(0);
		}
		else{
			row.createCell(3).setCellValue(
					nf.format(sum_YZ * 1.0 / sum_total * 1.0));
		}
		
		row = sheet.createRow(25);
		row.createCell(1).setCellValue("航政");
		row.createCell(2).setCellValue(sum_HZ);
		if(sum_total==0){
			row.createCell(3).setCellValue(0);
		}
		else{
			row.createCell(3).setCellValue(
					nf.format(sum_HZ * 1.0 / sum_total * 1.0));
		}
		
		row = sheet.createRow(26);
		row.createCell(1).setCellValue("海事");
		row.createCell(2).setCellValue(sum_HS);
		if(sum_total==0){
			row.createCell(3).setCellValue(0);
		}
		else{
			row.createCell(3).setCellValue(
					nf.format(sum_HS * 1.0 / sum_total * 1.0));
		}
		
		

		row = sheet.createRow(27);
		row.createCell(0).setCellValue("违章缘由统计：");
		for (int i = 0; i < resonList.size(); i++) {
			row = sheet.createRow(i + 28);
			row.createCell(1).setCellValue(resonList.get(i).getReasonName());
			int reasonNumber = 0;
			for (int j = 0; j < dataList.size(); j++) {
				if (dataList.get(j).getTwoName().equals(
						resonList.get(i).getReasonName()))
					reasonNumber += dataList.get(j).getAmount();
			}
			row.createCell(2).setCellValue(reasonNumber);
			if(sum_total==0){
				row.createCell(3).setCellValue(0);
			}
			else{
				row.createCell(3).setCellValue(
						nf.format(reasonNumber * 1.0 / sum_total * 1.0));
			}
			
			
		}

		String root = ServletActionContext.getServletContext().getRealPath(
				GlobalVar.REPORT_ILLEGAL_EXCEL);
		File file = new File(root);// 创建一个文件对象
		OutputStream os = new FileOutputStream(file);// 创建一个输出对象
		workbook.write(os);// 把建好的Excel放入输出流
		os.close();// 关闭输出流
		InputStream in = null;
		in = new FileInputStream(file);
		/*InputStream in = null;
		in = new ByteArrayInputStream(workbook.getBytes());
		System.out.println(in);
		System.out.println("-----end-----");*/
		return in;

	}

}
