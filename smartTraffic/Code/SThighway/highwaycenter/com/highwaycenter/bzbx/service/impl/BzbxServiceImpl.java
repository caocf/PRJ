package com.highwaycenter.bzbx.service.impl;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.common.action.BaseResult;
import com.common.action.Constants;
import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;
import com.common.utils.FileUpload;
import com.common.utils.ValidateCode;
import com.highwaycenter.bzbx.dao.BzbxDao;
import com.highwaycenter.bzbx.dao.BzbxlxDao;
import com.highwaycenter.bzbx.model.HBzbxBzbx;
import com.highwaycenter.bzbx.service.BzbxService;
import com.highwaycenter.gljg.model.HJcGlbm;
import com.highwaycenter.lxjbxx.dao.LxjbxxDao;
@Service("bzbxservice")
public class BzbxServiceImpl extends BaseService implements BzbxService{

	@Resource(name="bzbxdao")
	private BzbxDao bzbxDao;
	@Resource(name="bzbxlxdao")
	private BzbxlxDao bzbxlxDao;
	@Resource(name="lxjbxxdao")
	private LxjbxxDao lxjbxxDao;
	@Override
	public BaseResult selectBzbxGkList(int page, int rows, Integer xzqhdm,
			Integer bzbxlxbh, String selectValue,String selectId) {
		
		String tuanpath = null;
		BaseQueryRecords records = this.bzbxDao.selectGkList(page, rows, xzqhdm, bzbxlxbh, selectValue,selectId);
		List<HBzbxBzbx> list = (List<HBzbxBzbx>) records.getData();
		String format1 = "yyyy.mm";
		String format3 = "yyyy、mm";
		String format2 = "yyyy-mm";
		SimpleDateFormat sdf1 = new SimpleDateFormat(format1);
		SimpleDateFormat sdf2 = new SimpleDateFormat(format2);
		SimpleDateFormat sdf3 = new SimpleDateFormat(format3);
		Date d = null;
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				HBzbxBzbx bzbx  = list.get(i);
				//设置路线名称
				String xlmc = bzbx.getXlmc();
				if(xlmc!=null&&!xlmc.trim().equals("")){
					String lxjc = this.lxjbxxDao.selectLxjc(xlmc);
					if(lxjc!=null&&!lxjc.trim().equals("")){
						xlmc +="("+lxjc+")";
						//fwq.setXlmc(xlmc);
					}
				}
				bzbx.setXlmc(xlmc);
				//设置图案路径
				String path = bzbx.getBmnrta();
				if(path==null||path.trim().equals("")){//标志标线没有图案
					bzbx.setBzbxpath("");
				  }else{
					  String morenPath =ServletActionContext.getServletContext().getRealPath("/")+Constants.IMG_BZBXPICTURE_PATH+path;  //无后缀名
					  //增加后缀名	
					         String[] extions ={".png"}; //只验证png格式   String[] extions = {".png",".jpg",".jpeg",".gif",".bmp"};
							   for(int q=0;q<extions.length;q++){
								  /* String newPath =  morenPath+extions[q];
								   System.out.println("遍历"+newPath);
								   File file = new File(newPath);
								  if(file.exists()){
									  System.out.println("找到了"+newPath);
									  break;
								  }*/
								  tuanpath = Constants.IMG_BZBXPICTURE_PATH+path+extions[q];  //考虑到只有png，直接写在for循环内部
								  bzbx.setBzbxpath(tuanpath);
							   } 
				  }
				
				String time1 = bzbx.getAzsj();
				if(time1!=null&&!time1.equals("")){
					if(time1.indexOf(".")!=-1){
				
					try{
					d=sdf1.parse(time1);
					}catch(Exception e){
						e.printStackTrace();
					}
					String time2 = sdf2.format(d);
					//bzbx.setAzsj(time2);
					}
				if(time1.indexOf("、")!=-1){
					
					try{
					d=sdf3.parse(time1);
					}catch(Exception e){
						e.printStackTrace();
					}
					String time2 = sdf2.format(d);
					//bzbx.setAzsj(time2);
					}
				list.set(i, bzbx);
			 }
			}
		}
		records.setData(list);
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		result.setObj(records);
		return result;
	}
	@Override
	public BaseResult selectBzbxlxList() {
		List list = this.bzbxlxDao.selectBzbxlxList();
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		result.setList(list);
		return result;
	}
	@Override
	public BaseResult saveBzbx(HBzbxBzbx bzbx,File uploadFile,String uploadFileFileName) {
		
		/*bzbx.setBmnrta(bzbxta);*/
		//时间格式修改
		String format1 = "yyyy-mm";
		String format2 = "yyyy.mm";
		SimpleDateFormat sdf1 = new SimpleDateFormat(format1);
		SimpleDateFormat sdf2 = new SimpleDateFormat(format2);
		Date d = null;	
		String oldtime = bzbx.getAzsj();
		 if(oldtime ==null && !oldtime.equals("")){
			  try {
				d=sdf1.parse(oldtime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
				String time2 = sdf2.format(d);
					bzbx.setAzsj(time2);
				}

		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		//是否有文件上传
		
		//3.验证是否有上传图片并且图片格式是否正确
		if(uploadFile==null||uploadFile.length()==0){//没有图像上传
			
			this.bzbxDao.save(bzbx);
		  
			return new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		}else{
			//生成图片名字
	     //   UUID uuid = UUID.randomUUID();
			//标志标线图案名字生成
			String bzbxta = ValidateCode.genValidateCode(8);/*uuid.toString();*/ // 使用验证码生成方式生成8位图片名字
		
			//验证图片格式
			System.out.println("文件大小"+uploadFile.length());
			BaseResult checkFlag =FileUpload.checkImageType(uploadFile,
					uploadFileFileName, Constants.HEADPC_COMMITSIZE);
			if(checkFlag.getResultcode()!=1){
				return checkFlag;
			}
			
			 String uploadpath = ServletActionContext.getServletContext().getRealPath("/")
					 +Constants.IMG_BZBXPICTURE_PATH;   
			 //上传并压缩图片
			 BaseResult uploadFlag =FileUpload.imgUpload
					 (bzbxta,uploadFile,uploadFileFileName,uploadpath);
			 if(uploadFlag.getResultcode()!=1){
				 return uploadFlag;
			 }
			 bzbx.setBmnrta(bzbxta);  //文件保存成功之后将图片名字放在数据库
			 this.bzbxDao.save(bzbx);
		}
			
		
		
		return result;
	}
	@Override
	public BaseResult updateBzbx(HBzbxBzbx bzbx,File uploadFile,String uploadFileFileName) {
		try{
			String taname = this.bzbxDao.selectTa(bzbx.getBzbxbh());
		//标志标线图案名字生成
		String bzbxta = "";
		/*bzbx.setBmnrta(bzbxta);*/
		//查询标志标线编号是否存在
		int countBh = this.bzbxDao.countBybh(bzbx.getBzbxbh());
		if(countBh==0){//标志标线不存在
			return new BaseResult(Constants.BZBX_BN_NOEXIT_CODE,Constants.BZBX_BN_NOEXIT_INFO);
		}
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		String format1 = "yyyy-mm";
		String format2 = "yyyy.mm";
		SimpleDateFormat sdf1 = new SimpleDateFormat(format1);
		SimpleDateFormat sdf2 = new SimpleDateFormat(format2);
		Date d = null;	
		String oldtime = bzbx.getAzsj();
		 if(oldtime ==null && !oldtime.equals("")){
		   try{
			  d=sdf1.parse(oldtime);
					}catch(ParseException  e){
						e.printStackTrace();
					}
				String time2 = sdf2.format(d);
					bzbx.setAzsj(time2);
				}
			//是否有文件上传
			
			//3.验证是否有上传图片并且图片格式是否正确
			if(uploadFile==null||uploadFile.length()==0){//没有标志标线图案上传
				bzbx.setBmnrta(taname);
				this.bzbxDao.update(bzbx);
			  
				return new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
			}else{
				String nrta = null;
				//查询原本的图案名字
			
				if(taname!=null&&!taname.trim().equals("")){
					nrta=taname;
				}else{
					//自动生成一个
					//生成图片名字
			        UUID uuid = UUID.randomUUID();
					//标志标线图案名字生成
			        nrta = uuid.toString();
					
				}
				System.out.println("lllllllllllllllllllllll+图片名字"+nrta);
				
				//验证图片格式
				System.out.println("文件大小"+uploadFile.length());
				BaseResult checkFlag =FileUpload.checkImageType(uploadFile,
						uploadFileFileName, Constants.HEADPC_COMMITSIZE);
				if(checkFlag.getResultcode()!=1){
					return checkFlag;
				}
				
				 String uploadpath = ServletActionContext.getServletContext().getRealPath("/")
						 +Constants.IMG_BZBXPICTURE_PATH;   
				 //上传图片
				 BaseResult uploadFlag =FileUpload.imgUpload
						 (nrta,uploadFile,uploadFileFileName,uploadpath);
				 if(uploadFlag.getResultcode()!=1){
					 return uploadFlag;
				 }
				 
				bzbx.setBmnrta(nrta);
				this.bzbxDao.update(bzbx);
				if(taname!=null&&!taname.trim().equals("")){
				//删除之前的图片格式
				   String[] extions = {".jpg",".jpeg",".png",".gif",".bmp"};
				   for(int i=0;i<extions.length;i++){
					   String newextion = uploadFileFileName.substring(uploadFileFileName.lastIndexOf("."));
					   System.out.println("扩展名"+newextion);
					  if(!newextion.equals(extions[i])){
						  FileUpload.deleteWebFile(uploadpath+taname+extions[i]);
					  }
				   }
				}
				
			}
			return result;
		}catch(java.lang.Exception e){
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public BaseResult deleteBzbx(Integer bzbxbh) {
		String taname = this.bzbxDao.selectTa(bzbxbh);
		this.bzbxDao.delete(bzbxbh);
		if(taname!=null&&!taname.trim().equals("")){
			//删除之前的图片格式
			 String uploadpath = ServletActionContext.getServletContext().getRealPath("/")
					 +Constants.IMG_BZBXPICTURE_PATH;   
			   String[] extions = {".jpg",".jpeg",".png",".gif",".bmp"};
			   for(int i=0;i<extions.length;i++){	
					  FileUpload.deleteWebFile(uploadpath+taname+extions[i]);
				  }
			   }
			
		return new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
	}
	@Override
	public BaseResult selectBzbxXQ(Integer bzbxbh) {
		HBzbxBzbx bzbx = this.bzbxDao.selectUnique(bzbxbh);
		//设置图案路径
		String path = bzbx.getBmnrta();
		if(path==null||path.trim().equals("")){//标志标线没有图案
			bzbx.setBzbxpath("");
		  }else{
			  String morenPath =ServletActionContext.getServletContext().getRealPath("/")+Constants.IMG_BZBXPICTURE_PATH+path;  //无后缀名
			  //增加后缀名	
			         String[] extions ={".png"}; //只验证png格式   String[] extions = {".png",".jpg",".jpeg",".gif",".bmp"};
					   for(int q=0;q<extions.length;q++){
						  /* String newPath =  morenPath+extions[q];
						   System.out.println("遍历"+newPath);
						   File file = new File(newPath);
						  if(file.exists()){
							  System.out.println("找到了"+newPath);
							  break;
						  }*/
						  String tuanpath = Constants.IMG_BZBXPICTURE_PATH+path+extions[q];  //考虑到只有png，直接写在for循环内部
						  bzbx.setBzbxpath(tuanpath);
					   } 
		  }
		String format1 = "yyyy.mm";
		String format3 = "yyyy、mm";
		String format2 = "yyyy-mm";
		SimpleDateFormat sdf1 = new SimpleDateFormat(format1);
		SimpleDateFormat sdf2 = new SimpleDateFormat(format2);
		SimpleDateFormat sdf3 = new SimpleDateFormat(format3);
		Date d = null;
		String time1 = bzbx.getAzsj();
		if(time1!=null&&!time1.equals("")){
			if(time1.indexOf(".")!=-1){
		
			try{
			d=sdf1.parse(time1);
			}catch(Exception e){
				e.printStackTrace();
			}
			String time2 = sdf2.format(d);
			//bzbx.setAzsj(time2);
			}
		if(time1.indexOf("、")!=-1){
			
			try{
			d=sdf3.parse(time1);
			}catch(Exception e){
				e.printStackTrace();
			}
			String time2 = sdf2.format(d);
			//bzbx.setAzsj(time2);
			}
		}
		String xlmc = bzbx.getXlmc();
		if(xlmc!=null&&!xlmc.trim().equals("")){
			String lxjc = this.lxjbxxDao.selectLxjc(xlmc);
			if(lxjc!=null&&!lxjc.trim().equals("")){
				xlmc +="("+lxjc+")";
				//fwq.setXlmc(xlmc);
			}
		}
		bzbx.setLxjc(xlmc);
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		result.setObj(bzbx);
		return result;
	}
	@Override
	public BaseResult selectChineseStandard() {
		String filePath =  ServletActionContext.getServletContext().getRealPath("/")+Constants.IMG_BZBXPICTURE_TYPE0;
		List<Object>  filelist = new ArrayList<Object>();
		File root = new File(filePath);
		File[] files = root.listFiles();
		for(File file:files){     
			if(file.isFile()){
				filelist.add(file.getAbsolutePath());
			}
		}
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		result.setList(filelist);
		return result;
	}

	
	
}
