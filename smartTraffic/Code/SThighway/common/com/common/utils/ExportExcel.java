package com.common.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.apache.http.HttpRequest;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.tomcat.util.http.fileupload.util.mime.MimeUtility;

/**
 * 利用开源组件POI3.0.2动态导出EXCEL文档
 * 
 * @version v1.0
 * @param <T>
 *            应用泛型，代表任意一个符合javabean风格的类
 *            注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 *            byte[]表jpg格式的图片数据
 */
public class ExportExcel<T> {
	

	public void exportExcel(Collection<T> dataset, OutputStream out) {
		exportExcel("测试POI导出EXCEL文档", null, dataset, out, "yyyy-MM-dd");
	}

	public void exportExcel(String[] headers, Collection<T> dataset,
			OutputStream out) {
		exportExcel("测试POI导出EXCEL文档", headers, dataset, out, "yyyy-MM-dd");
	}

	public void exportExcel(String[] headers, Collection<T> dataset,
			OutputStream out, String pattern) {
		exportExcel("测试POI导出EXCEL文档", headers, dataset, out, pattern);
	}

	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 * 
	 * @param title
	 *            表格标题名
	 * @param headers
	 *            表格属性列名数组
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 *            javabean属性的数据类型有基本数据类型及String,Date,TimeStamp
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 */
	@SuppressWarnings("unchecked")
	public void exportExcel(String title, String[] headers,
			Collection<T> dataset, OutputStream out, String pattern) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 20);
		//设置第一行标题
		Row rowhead = sheet.createRow(0);// 创建一行（标题）
		rowhead.setHeightInPoints(35);
		
		HSSFCell cellhead = (HSSFCell) rowhead.createCell((short) 0);		
		 HSSFCellStyle titleMainStyle = hssFCellStyleType(workbook);
		  Region region = new Region(0, (short) 0, 0, (short) (headers.length-1));//设置合并的行列
		  setRegionStyle(sheet,region,titleMainStyle);//设置合并单元格的风格（加边框）
		  sheet.addMergedRegion(region);//将单元格合并
		  HSSFFont font = workbook.createFont();
		
		  font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
			font.setFontName("黑体");
			font.setFontHeightInPoints((short) 16);// 设置字体大小
			titleMainStyle.setFont(font);
			cellhead.setCellStyle(titleMainStyle);
		  cellhead.setCellValue("嘉兴公路综合数据交换平台操作日志");
		
		
		//设置副标题
		 rowhead = sheet.createRow(1);// 创建一行（副标题 时间）
		 rowhead.setHeightInPoints(20);
		 cellhead = (HSSFCell) rowhead.createCell((short) 0);
	      titleMainStyle = hssFCellStyleTypeFuTitle(workbook);
		  region = new Region(1, (short) 0, 1, (short)(headers.length-1));//设置合并的行列
		  setRegionStyle(sheet,region,titleMainStyle);//设置合并单元格的风格（加边框）setRegionStyle是一个我写的方法
		  sheet.addMergedRegion(region);//将单元格合并
		  HSSFFont font2 = workbook.createFont();
			font2.setColor(HSSFColor.BLACK.index);
			font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			titleMainStyle.setFont(font2);
		    cellhead.setCellStyle(titleMainStyle);
			cellhead.setCellValue("日志                                                                                                       制表时间:"+changeYMD(new Date()));
		 
	     HSSFCellStyle style = workbook.createCellStyle();
		 style = this.hssFCellStyleTypeHead(workbook);
		
		HSSFCellStyle style2 = workbook.createCellStyle();
        style2  = this.hssFCellStyleTypeConstent(workbook);
	
		
		HSSFRow row = sheet.createRow(2);
		row.setHeightInPoints(25);
		for (short i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}
		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		int index = 2;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			T t = (T) it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();
			for (short i = 0; i < fields.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style2);
				Field field = fields[i];
				String fieldName = field.getName();
				String getMethodName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				try {
					Class tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName,
							new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					
					if (value instanceof Boolean) {
						

					} else if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else if (value instanceof Timestamp) {
						Timestamp date = (Timestamp) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else {
						// 其它数据类型都当作字符串简单处理
						textValue = (String)value;
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							HSSFRichTextString richString = new HSSFRichTextString(
									textValue);
							HSSFFont font3 = workbook.createFont();
							font3.setColor(HSSFColor.BLACK.index);
							richString.applyFont(font3);
							cell.setCellValue(richString);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} finally {
					// 清理资源
				}
			}
		}
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}catch(java.lang.Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 时间转换
	 */
	public static String changeYMD(Date value) {
		SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return myFmt.format(value);

	}
	/**
	 * 设置标头格式
	 * 
	 * @param book
	 * @return
	 */
	public HSSFCellStyle hssFCellStyleType(HSSFWorkbook book) {
		HSSFCellStyle setBorder = book.createCellStyle();
		HSSFFont font = book.createFont();
		setBorder = this.getNewCenterStyle(book);

		font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 16);// 设置字体大小
		setBorder.setFont(font);
		return setBorder;
	}

	/**
	 * 设置副标头格式
	 * 
	 * @param book
	 * @return
	 */
	public HSSFCellStyle hssFCellStyleTypeFuTitle(HSSFWorkbook book) {
		HSSFCellStyle setBorder = book.createCellStyle();
		HSSFFont font = book.createFont();
		setBorder = this.getNewCenterStyle(book);
		//font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
		font.setFontHeightInPoints((short) 12);// 设置字体大小
		setBorder.setFont(font);
		return setBorder;
	}
	
	
	/**
	 * 设置分类格式
	 * 
	 * @param book
	 * @return
	 */
	public HSSFCellStyle hssFCellStyleTypeHead(HSSFWorkbook book) {
		HSSFCellStyle setBorder = book.createCellStyle();
	
		setBorder = this.getNewCenterStyle(book);
		HSSFFont font = book.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		setBorder.setFont(font);
		//font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
		
		setBorder.setFont(font);
		return setBorder;
	}
	
	
	/**
	 * 设置内容格式
	 * 
	 * @param book
	 * @return
	 */
	public HSSFCellStyle hssFCellStyleTypeConstent(HSSFWorkbook book) {
		HSSFCellStyle setBorder = book.createCellStyle();
		
		setBorder = this.getNewCenterStyle(book);
				HSSFFont font2 = book.createFont();
				font2.setColor(HSSFColor.BLACK.index);
				font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
				setBorder.setFont(font2);
		
		return setBorder;
	}
	
	/**
	 * 设置基本样式
	 * 
	 * @param book
	 * @return
	 */
	 private  HSSFCellStyle getNewCenterStyle(HSSFWorkbook workBook){
		  HSSFCellStyle style =  workBook.createCellStyle();
		  style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		  style.setFillForegroundColor(HSSFColor.WHITE.index);
		  style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		  style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		  style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		  style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		  style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
		  style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		  return style;
		 }
	 
	 
	 private void setRegionStyle(HSSFSheet sheet, Region region , HSSFCellStyle cs) {
		    
	        for (int i = region.getRowFrom(); i <= region.getRowTo(); i ++) {
	            HSSFRow row = sheet.getRow(i);
	            if(region.getColumnFrom()!=region.getColumnTo()){
	            for (int j = region.getColumnFrom(); j <= region.getColumnTo(); j++) {
	                HSSFCell cell = (HSSFCell) row.createCell((short)j);		
	                cell.setCellStyle(cs);
	            }
	            }
	        }
	 }
}

