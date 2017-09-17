package com.highwaycenter.gljg.action;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import com.common.action.BaseAction;
import com.common.action.Constants;
import com.common.action.BaseResult;
import com.common.utils.PropertyLoader;
import com.highwaycenter.gljg.model.HJcGlry;
import com.highwaycenter.gljg.service.GlryService;
import com.highwaycenter.log.service.CzrzService;
//41xiugai
/*@Controller
@ParentPackage("cement-interceptor")
@Namespace("/login")*/    //登录登出路径
@SuppressWarnings("all") //忽略该类中的所有警告
public class LoginAction extends BaseAction{

	private static final long serialVersionUID = -5571314115272855312L;
	
	@Resource(name="glryservice")
	private GlryService glryservice;
	@Resource(name="czrzservice")
	private CzrzService czrzService;
	private BaseResult result;
	private HJcGlry glry;
	private String isRemember;   //是否记住密码 1记住，2不记住
	//private String dldz;         //页面端获取登录地点
	private String token;
	private String username;   //用户名

	/**
	 * 登陆接口 
	 * 
	 * @param  username    手机长号或者姓名作为登陆名
	 * @param  glry.rymm    密码
	 * @return  新增权限组的结果  权限组编号  
	 * 接口实例：http://localhost:8080/HighWayCenter/login/userlogin?glry.sjch=?&glry.rymm=?                 
	 */
	/*@Action(value = "userlogin", results = { @Result(name = "json", type = "json")})*/
	public String userLogin(){
        if(glry.equals(null)||username==null||username.trim().equals("")){
        	//验证传入数据是否为空
        	//用户名为空
        	this.result = new BaseResult(Constants.LOGIN_NAME_NULL_CODE,Constants.LOGIN_NAME_NULL_INFO);
        	
        }else{
        	//获取用户登陆ip
            HttpServletRequest request = ServletActionContext.getRequest();
        	String dldz =  request.getRemoteAddr();
        	//获取用户登陆时间
        	Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        	this.result = this.glryservice.login(username, glry.getRymm(),currentTime,
        			dldz,currentTime,isRemember);
            
        }
        //记录日志信息
       
         if(this.result.getResultcode()==Constants.SUCCESS_CODE){
        	this.czrzService.saveCzrz((String)this.result.getObj(),"登陆系统","登陆系统");//  登陆
         }
       
      
		return "json";
	}
	
	/**
	 * 退出系统接口
	 *  删除用户存在内存的权限信息，删除口令
	 * @param token
	 * @return  新增权限组的结果  权限组编号  
	 * 接口实例：http://localhost:8080/HighWayCenter/login/userlogout?token=?                
	 */
	/*@Action(value = "userlogout", results = { @Result(name = "json", type = "json")})*/
	public String userLogout(){
		   this.czrzService.saveCzrz(token,"退出系统","退出系统");//  退出系统	
        	this.result = this.glryservice.logout(token);
		return "json";
	}
	
	/**
	 * 读版本号
	 * @return
	 *http://localhost:8080/HighWayCenter/login/versionCode
	/*@Action(value = "versionCode", results = { @Result(name = "json", type = "json")})*/
	public String selectVersionCode(){

		Properties  p = PropertyLoader.getProperties("productinfo.properties");
		String name = (String) p.get("product.versionName");
        this.result =new BaseResult();
        result.setObj(name);	
		return "json";
	}
	
	/**
	 * 解码记住密码
	 * @return
	 * http://localhost:8080/HighWayCenter/login/rememberName
	 */
	public String decodePassword(){
		this.result = this.glryservice.decodePassword();
		return "json";
	}
	
	/**
	 * 下载字体安装包
	 * @return
	 * http://localhost:8080/HighWayCenter/login/frontdownload
	 */
    public void frontExeDownload(){
    	try{
        	//获取下载路径
    		String downloadpath = ServletActionContext.getServletContext().getRealPath("/")
    				 +"/exedownload/VistaFont_CHS.EXE";
    		File file = new File(downloadpath);
    		// 取得文件名。
    		String filename = file.getName();
    		
    		// HttpServletRequest request = (HttpServletRequest)ActionContext.getContext().get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
    		
           //英文名直接getbytes
            String encodeName = "filename="+ new String(filename.getBytes());
            if(encodeName==null){
            	encodeName = "filename="+(URLEncoder.encode(filename,"UTF-8")).getBytes();
            }

    		InputStream fis = new BufferedInputStream(new FileInputStream(downloadpath));
    		byte[] buffer = new byte[fis.available()];
    		fis.read(buffer);
    		fis.close();
    		// 清空response
    		HttpServletResponse response = ServletActionContext.getResponse();
    		response.reset();
    		// 设置response的Header
    		response.addHeader("Content-Disposition", "attachment;filename="
    				+ new String(filename.getBytes()));
    		response.addHeader("Content-Length", "" + file.length());
    		OutputStream toClient = new BufferedOutputStream(
    				response.getOutputStream());
    		response.setContentType("application/octet-stream bin");  //下载exe格式
    		response.setHeader("Content-disposition","attachment;"+encodeName);
    		
    		toClient.write(buffer);
    		toClient.flush();
    		toClient.close();
        	
           }catch(Exception  e){
        		e.printStackTrace();
        	}
    }
	
	
	
	
	
	
	public BaseResult getResult() {
		return result;
	}

	public void setResult(BaseResult result) {
		this.result = result;
	}

	public HJcGlry getGlry() {
		return glry;
	}

	public void setGlry(HJcGlry glry) {
		this.glry = glry;
	}

	public String getIsRemember() {
		return isRemember;
	}

	public void setIsRemember(String isRemember) {
		this.isRemember = isRemember;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	
	

}
