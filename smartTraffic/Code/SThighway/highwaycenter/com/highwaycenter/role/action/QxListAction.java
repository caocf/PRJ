package com.highwaycenter.role.action;
//xiugai26
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import com.common.action.BaseAction;
import com.common.action.BaseResult;
import com.common.action.Constants;
import com.highwaycenter.gljg.service.GlryService;
import com.highwaycenter.log.service.CzrzService;
import com.highwaycenter.role.model.HJcJbjsb;
import com.highwaycenter.role.model.HJcJbqx;
import com.highwaycenter.role.model.HJcQxz;
import com.highwaycenter.role.service.QxListService;
import com.highwaycenter.role.service.QxzService;

@SuppressWarnings("all") //忽略该类中的所有警告
public class QxListAction extends BaseAction{
	
	static Logger log = Logger.getLogger(QxListAction.class);
	private static final long serialVersionUID = -488500161180296181L;
	@Resource(name="qxlistservice")
	private QxListService qxlistService;
	@Resource(name="qxzservice")
	private QxzService qxzService;
	@Resource(name="glryservice")
	private GlryService glryservice;
	@Resource(name="czrzservice")
	private CzrzService czrzService;
	private String token;
	private BaseResult result;
	private Integer page;
	private Integer rows;
	private HJcJbjsb js;
	private String qxlist;
	private String qxzlist;
	private HJcJbqx jbqx;
	private Integer qxzbh;
	private Integer jsbh;

	
	/**
	 * 版本2权限组列表接口1:获取权限组可选的基本权限
	 * 权限组编辑的基本权限列表
	 * @param  token
	 * @param qxzbh   编辑的时候需要传入qxzbh 新增的时候传入为空 
	 * @return   result.list
	 * 接口实例： http://localhost:8080/HighWayCenter/qxlistmanager/jbqxlist?token=?                
	 */
	public String jbqxList(){
		try{
			log.debug("获取基本权限start-------------------");	
			//验证令牌
	     	 int validate_code = this.glryservice.dllpCheck(token);
	    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	  		    return "json";
	  	     }
		     this.result=  this.qxlistService.findjbqxlist(qxzbh);
		    
	     }catch(java.lang.Exception e){
			e.printStackTrace();
		}
		return "json";	
	}
	
	
	/**版本2权限组列表接口2：获取所有权限组(基本权限)
	 * 
	 * @param  token
	 * @return   result.list
	 * 接口实例： http://localhost:8080/HighWayCenter/qxlistmanager/qxzlist?token                
	 */
	public String qxzList(){
			log.debug("获取权限组start-------------------");	
			System.out.println("获取权限组start-------------------");
			//验证令牌
	     	 int validate_code = this.glryservice.dllpCheck(token);
	    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	  		    return "json";
	  	     }
	    	//验证权限
	    	 validate_code = this.glryservice.authorityCheck("qxzlist",token);
	    	 if(validate_code != Constants.SUCCESS_CODE){
	    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
	   		    return "json";
	   	     }
	    	 if(page!=null||rows!=null){
	    		  this.result=  this.qxlistService.findQxzlist(4, page, rows);
			         return "json";	
	    	 }
	    	 if(jsbh==null||jsbh.equals("")){
		         this.result=  this.qxlistService.findQxzlist(1,-1);
		         return "json";	
	    	 }else{
	    		 this.result=  this.qxlistService.findQxzlist(2,jsbh);
	    		 return "json";	
	    	 }
		    
	 
		
	}
	
	

	/**版本2权限组列表接口2-1：获取所有权限组(无基本权限)
	 * 
	 * @param  token
	 * @return   result.list
	 * 接口实例： http://localhost:8080/HighWayCenter/qxlistmanager/qxzlistforselect?token                
	 */
	public String qxzListForselect(){
		try{
			//验证令牌
	     	 int validate_code = this.glryservice.dllpCheck(token);
	    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	  		    return "json";
	  	     }
		  
		     this.result=  this.qxlistService.findQxzlist(0,-1);
		    
	     }catch(java.lang.Exception e){
			e.printStackTrace();
		}
		return "json";	
	}
	
	
	
	/**角色模块9：根据角色编号，父权限组编号，获取一层子权限组的集合，加标志位
	 * 
	 * @param    js.jsbh
	 * @param    qxz.qxzbh
	 * @return   result.list  父子权限 json 数结构
	 * 接口实例：http://localhost:8080/HighWayCenter/qxlistmanager/zqxzlistjsqxz?js.jsbh=4&qxz.qxzbh=?&token=ZxblYG26              
	 */
	/*@Action(value = "zqxzlistjsqxz", results = { @Result(name = "json", type = "json")})*/
/*	public String zqxzlistJSQXZ(){
		try{ 
			log.debug("获取角色的全部权限组加标志位start-------------------");	
			System.out.println("获取角色的全部权限组start-------------------");
			//验证令牌
	   	 int validate_code = this.glryservice.dllpCheck(token);
	    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	  		    return "json";
	  	     }
	    	 //令牌验证结束
			if(js==null||js.getJsbh()==null||js.getJsbh().equals("")){
				this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
				return "json";
			}
			if(qxz==null||qxz.getQxzbh()==null||qxz.getQxzbh().equals("")){
				this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
				return "json";
			}
	 
           this.result=  this.qxlistService.zqxzlistJSFQXZ(js.getJsbh(),qxz.getQxzbh());   
	     }catch(java.lang.Exception e){
			e.printStackTrace();
		}
		log.debug("获取单个角色的一级权限组over-------------------");	
		System.out.println("获取单个角色的一级权限组start-------------------");
		return "json";	
	}
	*/

	/**角色模块8：获取角色的全部基本权限（获取全部权限，加标志位
	 * 获取单个角色的基本权限
	 * @param    js.jsbh
	 * @return   result.obj  父子权限 json 数结构
	 * 接口实例：http://localhost:8080/HighWayCenter/qxlistmanager/jsjbqxlist?js.jsbh=4&token=ZxblYG26              
	 */
	/*@Action(value = "jsjbqxlist", results = { @Result(name = "json", type = "json")})*/
/*	public String jsjbqxListByJs(){
		try{ 
			log.debug("获取单个角色的基本权限start-------------------");	
			System.out.println("获取单个角色的基本权限start-------------------");
			//验证令牌
	   	 int validate_code = this.glryservice.dllpCheck(token);
	    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	  		    return "json";
	  	     }
	    	 //令牌验证结束
			if(js==null||js.getJsbh()==null||js.getJsbh().equals("")){
				this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
				return "json";
			}
           this.result=  this.qxlistService.jsjbqxlistByjs(js.getJsbh());   
	     }catch(java.lang.Exception e){
			e.printStackTrace();
		}
		log.debug("获取单个角色的一级权限组over-------------------");	
		System.out.println("获取单个角色的一级权限组start-------------------");
		return "json";	
	}
	*/
	/**权限组接口2：获取所有一级权限组
	 * 角色模块新建角色接口2
	 * @param    js.jsbh
	 * @return   result.obj  父子权限 json 数结构
	 * 接口实例：http://localhost:8080/HighWayCenter/qxlistmanager/fqxzlist?token=ZxblYG26              
	 */
	/*@Action(value = "fqxzlist", results = { @Result(name = "json", type = "json")})*/
/*	public String fqxzList(){
		try{ 
			log.debug("获取所有一级权限组start-------------------");	
			System.out.println("获取所有一级权限组start-------------------");
			//验证令牌
	   	 int validate_code = this.glryservice.dllpCheck(token);
	    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	  		    return "json";
	  	     }
	    	 //令牌验证结束
			
           this.result=  this.qxlistService.fqxzlist();   
	     }catch(java.lang.Exception e){
			e.printStackTrace();
		}
		log.debug("获取所有一级权限组over-------------------");	
		System.out.println("获取所有一级权限组start-------------------");
		return "json";	
	}
	*/
	
	/**权限组接口3：获取某一父权限组的子权限组（单层）
	 * 获取某一父权限组的子权限组
	 * @param    qxz.qxzbh
	 * @return   result.list  父子权限 json 数结构
	 * 接口实例：http://localhost:8080/HighWayCenter/qxlistmanager/zqxzlist?qxz.qxzbh=?&token=ZxblYG26              
	 */
	/*@Action(value = "zqxzlist", results = { @Result(name = "json", type = "json")})*/
/*	public String zqxzListByFqxz(){
		try{ 
			log.debug("获取某一父权限组的子权限组（一层）------------------");	
			System.out.println("获取某一父权限组的子权限组（一层）start-------------------");
			//验证令牌
	   	 int validate_code = this.glryservice.dllpCheck(token);
	    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	  		    return "json";
	  	     }
	    	 //令牌验证结束
		if(qxz==null||qxz.getQxzbh()==null||qxz.getQxzbh().equals("")){
			this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
			return "json";
		}
 
           this.result=  this.qxlistService.zqxzListByFqxz(qxz.getQxzbh());   
	     }catch(java.lang.Exception e){
			e.printStackTrace();
		}
		log.debug("获取某一父权限组的子权限组（一层）over-------------------");	
		System.out.println("获取某一父权限组的子权限组（一层）over-------------------");
		return "json";	
	}
	*/
	/**  角色  （获取该父权限租的全部基本子权限）
	 * 获取某一父权限组的基本权限 
	 * @param    qxz.qxzbh
	 * @return   result.list  父子权限 json 数结构
	 * 接口实例：http://localhost:8080/HighWayCenter/qxlistmanager/zqxalllistbyfqxz?qxz.qxzbh=?&token=ZxblYG26              
	 */
/*	@Action(value = "zqxalllistbyfqxz", results = { @Result(name = "json", type = "json")})
	public String zqxAllListByFqxz(){
		try{ 
			log.debug("获取某一父权限组的基本权限------------------");	
			System.out.println("获取某一父权限组的基本权限start-------------------");
			//验证令牌
	   	 int validate_code = this.glryservice.dllpCheck(token);
	    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	  		    return "json";
	  	     }
	    	 //令牌验证结束
		if(qxz==null||qxz.getQxzbh()==null||qxz.getQxzbh().equals("")){
			this.result =  new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
			return "json";
		}
 
           this.result=  this.qxlistService.zqxzListByFqxz(qxz.getQxzbh());   
	     }catch(java.lang.Exception e){
			e.printStackTrace();
		}
		log.debug("获取某一父权限组的子权限组（一层）over-------------------");	
		System.out.println("获取某一父权限组的子权限组（一层）over-------------------");
		return "json";	
	}*/
	
	
	/**版本2权限组列表接口3：获取某一权限组相应的基本权限（基本权限是详情）
	 * @param    qxzbh    权限组编号
	 * @return   result.list  
	 * 接口实例：http://localhost:8080/HighWayCenter/qxlistmanager/zqxlistbyfqxz?qxz.qxzbh=?&token=ZxblYG26              
	 */
	public String zqxListByFqxz(){
		try{ 
			log.debug("获取某一父权限组的基本权限------------------");	
			//验证令牌
	   	 int validate_code = this.glryservice.dllpCheck(token);
	    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	  		    return "json";
	  	     }
	    	 //令牌验证结束
		if(qxzbh==null||qxzbh.equals("")){
			this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
			return "json";
		}
 
           this.result=  this.qxlistService.zqxListByFqxz(qxzbh);   
	     }catch(java.lang.Exception e){
			e.printStackTrace();
		}
		return "json";	
	}
	
	
	
	/**
	 * 根据父权限编号获取所有的子权限组与基本子权限
	 * @param  qxz.qxzbh    权限组编号
	 * @return   result.obj  父子权限 json 数结构
	 * 接口实例： http://localhost:8080/HighWayCenter/qxlistmanager/qxlistbyfbh?qxz.qxzbh=11&token                
	 */
	//
	/*@Action(value = "qxlistbyfbh", results = { @Result(name = "json", type = "json")})*/
	/*public String qxListByFbh(){
		try{
			log.debug("根据父权限编号获取所有的权限组与基本子权限start-------------------");	
			System.out.println("根据父权限编号获取所有的权限组与基本子权限start-------------------");
			//验证令牌
	     	 int validate_code = this.glryservice.dllpCheck(token);
	    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	  		    return "json";
	  	     }
		    if(qxz==null||qxz.getQxzbh()==null||qxz.getQxzbh().equals("")){
		    	this.result = new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
		    }else{
		     this.result=  this.qxlistService.getZqxByFqx(qxz.getQxzbh());
		    }
	     }catch(java.lang.Exception e){
			e.printStackTrace();
		}
		return "json";	
	}
	*/
	
	/**
	 * 获取所有的权限组与基本子权限
	 * @param page
	 * @param rows
	 * @return   result.obj  父子权限 json 数结构
	 * 接口实例：http://localhost:8080/HighWayCenter/qxlistmanager/qxlistall              
	 */
	/*@Action(value = "qxlistall", results = { @Result(name = "json", type = "json")})*/
/*	public String qxListAll(){
		try{
			long requestTime = System.currentTimeMillis();
			log.debug("获取所有的权限组与基本子权限start-------------------");	
			System.out.println("获取所有的权限组与基本子权限start-------------------");
			//验证令牌
	   	 int validate_code = this.glryservice.dllpCheck(token);
	    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	  		    return "json";
	  	     }
	    	 //令牌验证结束
			if(page==null){
				page = Constants.MOREN_PAGE;
			}
			if(rows==null){
				rows=Constants.MOREN_ROWS;
			}
		    
		     this.result=  this.qxlistService.getZqxAll();
		     long wTime = System.currentTimeMillis();
		     System.out.println("222    ---"+ (wTime-requestTime));
	     }catch(java.lang.Exception e){
			e.printStackTrace();
		}
		return "json";	
	}
*/	
	/**
	 * 获取所有角色的权限组与基本子权限
	 * @param page
	 * @param rows
	 * @return   result.obj  父子权限 json 数结构
	 * 接口实例：http://localhost:8080/HighWayCenter/qxlistmanager/jsqxlistall              
	 */
	/*@Action(value = "jsqxlistall", results = { @Result(name = "json", type = "json")})*/
/*	public String jsqxListAll(){
		long requestTime = System.currentTimeMillis();
		try{ 
		
			log.debug("获取所有角色的权限组与基本子权限start-------------------");	
			System.out.println("获取所有角色的权限组与基本子权限start-------------------");
			//验证令牌
	   	 int validate_code = this.glryservice.dllpCheck(token);
	    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	  		    return "json";
	  	     }
	    	 //令牌验证结束
			if(page==null||page.equals("")){
				page=Constants.MOREN_PAGE;
			}
			if(rows==null||rows.equals("")){
				rows=Constants.MOREN_ROWS;
			}
		     this.result=  this.qxlistService.getJsqxALL(page, rows);
		   
	     }catch(java.lang.Exception e){
			e.printStackTrace();
		}
		log.debug("获取所有角色的权限组与基本子权限start-------------------");	
		
		long endTime = System.currentTimeMillis();
		System.out.println("获取所有角色的权限组与基本子权限start-------------------"+(endTime-requestTime));
		return "json";	
	}
	*/
	/**
	 * 获取单个角色的权限组与基本子权限
	 * @param page
	 * @param rows
	 * @return   result.obj  父子权限 json 数结构
	 * 接口实例：http://localhost:8080/HighWayCenter/qxlistmanager/qxlisbyjs?js.jsbh=4&token=ZxblYG26              
	 */
	/*@Action(value = "qxlisbyjs", results = { @Result(name = "json", type = "json")})*/
/*	public String qxListByJs(){
		try{ 
			log.debug("获取单个角色的权限组与基本子权限start-------------------");	
			System.out.println("获取单个角色的权限组与基本子权限start-------------------");
			//验证令牌
	   	 int validate_code = this.glryservice.dllpCheck(token);
	    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	  		    return "json";
	  	     }
	    	 //令牌验证结束
			
         this.result=  this.qxlistService.getZqxByJs(js.getJsbh());   
	     }catch(java.lang.Exception e){
			e.printStackTrace();
		}
		log.debug("获取单个角色的权限组与基本子权限over-------------------");	
		System.out.println("获取单个角色的权限组与基本子权限start-------------------");
		return "json";	
	}*/
	
	
	//权限组权限级联---勾选基本权限
	/**角色模块5：
	 * 权限组权限级联---基本权限不勾选的时候响应
	 * @param 
	 * @param  nochoose_jbqx
	 * @return   result.list  返回所有权限组的id集合
	 * 接口实例：http://localhost:8080/HighWayCenter/qxlistmanager/qxnochooseqxz?jbqx.qxbh=&token=ZxblYG26              
	 */
	/*@Action(value = "qxnochooseqxz", results = { @Result(name = "json", type = "json")})*/
/*	public String qxNoChooseQxz(){
		long startTime=System.currentTimeMillis();   //获取开始时间 
		try{ 

			log.debug(" 权限组权限级联---基本权限不勾选的时候响应start-------------------");	
			System.out.println(" 权限组权限级联---基本权限不勾选的时候响应start------");
			//验证令牌
	   	    int validate_code = this.glryservice.dllpCheck(token);
	    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	  		    return "json";
	  	     }
	    	 //令牌验证结束
		  if(jbqx==null||jbqx.getQxbh()==null||jbqx.getQxbh().equals("")){
			  this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
	  		   return "json";
		  }
		  
         this.result=  this.qxlistService.qxNoChooseQxz(jbqx.getQxbh());   
	     }catch(java.lang.Exception e){
			e.printStackTrace();
		}
		log.debug("权限组权限级联---不勾选权限over-------------------");	
		System.out.println("权限组权限级联---不勾选权限start-------------------");
	    long endTime=System.currentTimeMillis(); //获取结束时间 
		System.out.println("程序运行时间： "+(endTime-startTime)+"ms"); 
		
		
		return "json";
		
	}
	*/
	
	
	//权限组权限级联---勾选权限组    QxzService selectZqxByFqxz
	/**版本2权限组列表接口4：
	 * 权限组权限级联---勾选权限组
	 * @param  qxzbh
	 * @param 
	 * @return   result.list  返回所有子权限的id集合
	 * 接口实例：http://localhost:8080/HighWayCenter/qxlistmanager/qxzchooseqx?qxz.qxzbh=4&token=ZxblYG26              
	 */
	public String qxzChooseQx(){
		long startTime=System.currentTimeMillis();   //获取开始时间 
		try{ 

			log.debug("权限组权限级联---勾选权限组start-------------------");	
			System.out.println("权限组权限级联---勾选权限组start-------------------");

		  if(qxzbh==null||qxzbh.equals("")){
			  this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
	  		   return "json";
		  }
		  
         this.result=  this.qxlistService.selectZqxByFqxz(qxzbh);   
	     }catch(java.lang.Exception e){
			e.printStackTrace();
		}

		return "json";	
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public BaseResult getResult() {
		return result;
	}


	public void setResult(BaseResult result) {
		this.result = result;
	}


	public Integer getPage() {
		return page;
	}


	public void setPage(Integer page) {
		this.page = page;
	}


	public Integer getRows() {
		return rows;
	}


	public void setRows(Integer rows) {
		this.rows = rows;
	}


	public HJcJbjsb getJs() {
		return js;
	}


	public void setJs(HJcJbjsb js) {
		this.js = js;
	}


	public String getQxlist() {
		return qxlist;
	}


	public void setQxlist(String qxlist) {
		this.qxlist = qxlist;
	}


	public String getQxzlist() {
		return qxzlist;
	}


	public void setQxzlist(String qxzlist) {
		this.qxzlist = qxzlist;
	}


	public HJcJbqx getJbqx() {
		return jbqx;
	}


	public void setJbqx(HJcJbqx jbqx) {
		this.jbqx = jbqx;
	}


	public Integer getQxzbh() {
		return qxzbh;
	}


	public void setQxzbh(Integer qxzbh) {
		this.qxzbh = qxzbh;
	}


	public Integer getJsbh() {
		return jsbh;
	}


	public void setJsbh(Integer jsbh) {
		this.jsbh = jsbh;
	}




}
