package com.zjport.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	/**
	 *
	 * @param title  excel标题
	 * @param headers   表格属性列名数组
	 * @param dataset   数据集   需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的javabean属性的数据类型有基本数据类型及String,Date,TimeStamp
	 * @param pattern   如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 * @param cellValue  二级名称
	 * @return InputStream 输出流
	 */
	public InputStream exportExcel(String title, String[] headers,
								   Collection<T> dataset, String pattern,String cellValue) {

		HSSFWorkbook workbook = new HSSFWorkbook();  // 声明一个工作薄

		HSSFSheet sheet = workbook.createSheet(title); // 生成一个表格

		sheet.setDefaultColumnWidth(20);  // 设置表格默认列宽度为20个字节

		//设置第一行标题
		Row rowhead = sheet.createRow(0);// 创建一行（标题）
		rowhead.setHeightInPoints(35);
		HSSFCell cellhead = (HSSFCell) rowhead.createCell((short) 0);
		HSSFCellStyle titleMainStyle = hssFCellStyleType(workbook);

		setRegionStyle(sheet,new CellRangeAddress( 0,0,0,headers.length-1) ,titleMainStyle);//设置合并单元格的风格（加边框）
		sheet.addMergedRegion(new CellRangeAddress( 0,0,0,headers.length-1) );//将单元格合并

		HSSFFont font = workbook.createFont();  //设置字体样式
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 16);// 设置字体大小
		titleMainStyle.setFont(font);
		cellhead.setCellStyle(titleMainStyle);

		cellhead.setCellValue(title); //填充标题


		//设置副标题
		rowhead = sheet.createRow(1);// 创建一行（副标题 时间）
		rowhead.setHeightInPoints(20);
		cellhead = (HSSFCell) rowhead.createCell((short) 0);
		titleMainStyle = hssFCellStyleTypeFuTitle(workbook);
		setRegionStyle(sheet,new CellRangeAddress( 1,1,0,headers.length-1) ,titleMainStyle);//设置合并单元格的风格（加边框）
		sheet.addMergedRegion(new CellRangeAddress( 1,1,0,headers.length-1) );//将单元格合并

		//设置字体
		HSSFFont font2 = workbook.createFont();
		font2.setColor(HSSFColor.BLACK.index);
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		titleMainStyle.setFont(font2);
		cellhead.setCellStyle(titleMainStyle);
		cellhead.setCellValue(cellValue+"  制表时间:"+changeYMD(new Date()));  //填充二级标题

		HSSFCellStyle style = workbook.createCellStyle();
		style = this.hssFCellStyleTypeHead(workbook);
		HSSFCellStyle style2 = workbook.createCellStyle();
		style2  = this.hssFCellStyleTypeConstent(workbook);
		HSSFRow row = sheet.createRow(2);
		row.setHeightInPoints(25);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
		}


		Iterator<T> it = dataset.iterator();// 遍历集合数据，产生数据行
		int index = 2;

		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			T t =  it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] allFields = t.getClass().getDeclaredFields();
			List<Field> fields = new ArrayList<>();
			for (Field field: allFields) {
				String name = field.getName();
				int mod = field.getModifiers();
				if(Modifier.isPublic(mod) && Modifier.isStatic(mod) && Modifier.isFinal(mod)){
					continue;
				}
				if (name.equalsIgnoreCase("SerialVersionUID")) {
					continue;
				}
				fields.add(field);
			}
			for (int i = 0; i < fields.size(); i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(style2);
				if(i == 0)
				{
					cell.setCellValue(index-2);
					continue;
				}

				Field field = fields.get(i);
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
						textValue = ((Boolean)value).toString();
					} else if(value instanceof  Integer){
						textValue =  ((Integer)value).toString();
					} else if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else if (value instanceof Timestamp) {
						Timestamp date = (Timestamp) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else {
						textValue = (String)value;
					}

					if (textValue != null) {	// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
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
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
				}
			}
		}
		try {
			File file = new File("temp.xls");// 创建一个文件对象
			OutputStream os = new FileOutputStream(file);// 创建一个输出对象
			workbook.write(os);

			InputStream in = null;
			in = new FileInputStream(file);
			os.close();// 关闭输出流
			return in;
		}catch(Exception e){
			e.printStackTrace();
		}finally {

		}
		return null;
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
	 * @param workBook
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


	private static void setRegionStyle(  HSSFSheet sheet,CellRangeAddress region,CellStyle cs){

		for(int i=region.getFirstRow();i<=region.getLastRow();i++){

			Row row=sheet.getRow(i);
			if(row==null) row=sheet.createRow(i);
			for(int j=region.getFirstColumn();j<=region.getLastColumn();j++){
				Cell cell=row.getCell(j);
				if( cell==null){
					cell=row.createCell(j);
					cell.setCellValue("");
				}
				cell.setCellStyle(cs);
			}
		}
	}
}

