package com.highwaycenter.log.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.JOptionPane;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.common.action.BaseResult;
import com.common.action.Constants;
import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;
import com.common.utils.ExportExcel;
import com.highwaycenter.bean.CzrzInfo;
import com.highwaycenter.gljg.dao.DllpDao;
import com.highwaycenter.gljg.dao.GlbmDao;
import com.highwaycenter.gljg.dao.GljgDao;
import com.highwaycenter.gljg.dao.GlryDao;
import com.highwaycenter.gljg.model.HJcGlry;
import com.highwaycenter.log.dao.CzrzDao;
import com.highwaycenter.log.model.HJcCzrz;
import com.highwaycenter.log.service.CzrzService;


@Service("czrzservice")
public class CzrzServiceImpl extends BaseService implements  CzrzService{

	@Resource(name="dllpdao")
	private DllpDao dllpDao;
	@Resource(name="czrzdao")
	private CzrzDao czrzDao;

	@Override
	public BaseResult saveCzrz(HJcCzrz czrz,String token) {
		HJcGlry ry = new HJcGlry();
		//获取系统时间
		if(czrz.getCzsj()==null||czrz.getCzsj().equals("")){
		Timestamp curenttime = new Timestamp(System.currentTimeMillis());
		czrz.setCzsj(curenttime);
		}
		if(czrz.getRzbh()==null||czrz.getRzbh().equals("")){//根据token查询人员
			 ry = this.dllpDao.findRyBylp(token);
			 czrz.setCzr(ry.getRybh());
		}
		  this.czrzDao.saveCzrz(czrz);
		  return new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
	}

	@Override
	public BaseResult deleteCzrz(int rzbh) {
		this.czrzDao.deleteCzrz(new HJcCzrz(rzbh));
		return new BaseResult(1,"日志新增成功");
	}

	@Override
	public BaseResult selectCzrzListAll(int page, int rows) {
		BaseResult result = new BaseResult();
		//result.setObj(this.czrzDao.findAll());
		return result;
	}

	@Override
	public BaseResult selectCzrzInfo(int rzbh) {
		BaseResult result = new BaseResult();
		result.setObj(this.czrzDao.findInfoByRzbh(rzbh));
		return result;
	}

	@Override
	public BaseResult saveCzrz(String token, String operateTitle,String operateContent) {
		HJcGlry ry = new HJcGlry();
		//获取系统时间
		Timestamp curenttime = new Timestamp(System.currentTimeMillis());
		//根据token查询人员
		 ry = this.dllpDao.findRyBylp(token);
		HJcCzrz czrz = new HJcCzrz();
		czrz.setRzbt(operateContent);
		czrz.setRzmc(operateContent);
		czrz.setCzr(ry.getRybh());
		czrz.setCzsj(curenttime); 
		this.czrzDao.saveCzrz(czrz); //undo
		return new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		
	}

	@Override
	public BaseResult selectCzrzList(int page, int rows, String startTime,
			String endTime, String selectvalue) {
		if(endTime!=null&&!endTime.trim().equals("")){
		     endTime +=" 23:59:59";
		}
		BaseQueryRecords records  =  this.czrzDao.selectCzrzList(page, rows, startTime, endTime, selectvalue);
	    
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		result.setObj(records);
		return result;
	}

	@Override
	public BaseResult deleteCzrzList(String rzbhlist) {
		this.czrzDao.deletList(rzbhlist);
		return new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
	}

	@Override
	public BaseResult excelCreat(String startTime,String endTime, String selectvalue) {
		if(endTime!=null&&!endTime.trim().equals("")){
		     endTime +=" 23:59:59";
		}
		//获取全部记录
		List<CzrzInfo> dataset  = new ArrayList<CzrzInfo>();
		dataset =  this.czrzDao.excelInputCzrz(startTime,endTime,selectvalue);
		
		ExportExcel<CzrzInfo> ex = new ExportExcel<CzrzInfo>();
		String[] headers = {"操作人姓名","日志标题", "日志内容","操作时间","日志类型名称"};
		
		try {
			String download = ServletActionContext.getServletContext().getRealPath("/")
			 +Constants.EXCEL_DOWNLOAD_PATH;   
			String filepath = download+"操作日志.xls";
		    File file = new File(download); 
		    if(!file.exists()){
				file.mkdir();
			}
		   
			OutputStream out = new FileOutputStream(filepath);
			System.out.println("abc"+dataset.size());
			ex.exportExcel("嘉兴公路综合数据交换平台操作日志", headers,dataset, out, "yyyy-MM-dd hh:mm:ss");
			
			out.close();
			System.out.println("excel生成成功！");
		    BaseResult result =new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		    result.setObj(filepath);
			return result ;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch(java.lang.Exception e){
			e.printStackTrace();
		}
	
		return new BaseResult(Constants.EXCEL_DOWNLOAD_FAIL_CODE,Constants.EXCEL_DOWNLOAD_FAIL_INFO);
	}

}
