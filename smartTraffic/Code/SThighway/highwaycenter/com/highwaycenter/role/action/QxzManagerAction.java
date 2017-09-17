package com.highwaycenter.role.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import com.common.action.BaseAction;
import com.common.action.BaseResult;
import com.common.action.Constants;
import com.common.utils.InputFomartConversion;
import com.highwaycenter.gljg.service.GlryService;
import com.highwaycenter.log.service.CzrzService;
import com.highwaycenter.role.model.HJcQxz;
import com.highwaycenter.role.model.HJcQxzqxdy;
import com.highwaycenter.role.service.QxListService;
import com.highwaycenter.role.service.QxzService;

/*@Controller
@ParentPackage("cement-interceptor")
@Namespace("/qxzmanager") */ // 角色权限管理
@SuppressWarnings("all") //忽略该类中的所有警告
public class QxzManagerAction extends BaseAction{

	private static final long serialVersionUID = -5569588668164920415L;
	@Resource(name="qxzservice")
	private QxzService qxzService;
	@Resource(name="glryservice")
	private GlryService glryservice;
	@Resource(name="qxlistservice")
	private QxListService qxlistService;
	@Resource(name="czrzservice")
	private CzrzService czrzService;
	private String token;
	private BaseResult result;
	private String qxlist;          //权限编号的集合  每个权限编号以","分割
	private HJcQxz qxz;
	
	
	
	/**版本2权限组接口1:新增权限组 (分配基本权限) 
	 * @param  token
	 * @param  qxz.qxzmc    权限名称
	 * @param  qxlist     基本权限集合
	 * @return  新增权限组的结果  权限组编号  
	 * 接口实例：http://localhost:8080/HighWayCenter/qxzmanager/qxzsave?qxz.qxzmc=?&token=?&qxlist=?                 
	 */
	public String qxzSave(){	
		//验证令牌
   	 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 
    	//验证权限
    	 validate_code = this.glryservice.authorityCheck("qxzsave",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
    	 //令牌验证结束
		if(qxz==null||qxz.getQxzmc()==null||qxz.getQxzmc().trim().equals("")){
			//权限名称为空
			this.result =  new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);		
			return "json";
		}
		qxz.setQxzmc(trimAll(qxz.getQxzmc()));
		qxlist = trimAll(qxlist);
		List<Integer> qxbhList =new ArrayList<Integer>();	 
	   if(qxlist!=null&&!qxlist.equals("")){
	   	    qxbhList = InputFomartConversion.springSplitIntegerlist(qxlist);
		 }
		    this.result= this.qxzService.qxzSave(qxz,qxbhList); 		
		  //记录日志信息
		    if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
			this.czrzService.saveCzrz(token,"新增权限组","新增权限组——“"+qxz.getQxzmc()+"”");//基本权限 19.新增权限组
		    }
	    	return "json";
	}
	

	
	
	/**版本2权限组接口2：编辑权限组
	 * 编辑权限组 
	 * @param  token  
	 * @param  qxz.qxzmc  权限组名称
	 * @param  qxz.qxzbh  权限组编号
	 * @param  qxlist     基本权限集合
	 * @return  权限组分配权限的结果  
	 * 接口实例：http://localhost:8080/HighWayCenter/qxzmanager/qxtoqxz?qxz.qxzbh=?&qxlist=?&token=?&qxz.qxzmc=?                    
	 */
	public String qxToQxz(){	
		
		//验证令牌
   	     int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }//令牌验证结束
    	//验证权限
    	 validate_code = this.glryservice.authorityCheck("qxtoqxz",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
		  if(qxz==null||qxz.getQxzbh()==null||qxz.getQxzbh().equals("")||
				  qxz.getQxzmc()==null||qxz.getQxzmc().trim().equals("")){
			  this.result =  new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);		
			  return "json";
		  }
		  qxz.setQxzmc(trimAll(qxz.getQxzmc()));
		  qxlist=trimAll(qxlist);
		  List<Integer> qxbhList =new ArrayList<Integer>();
			
	      if(qxlist!=null&&!qxlist.equals("")){
	   	    qxbhList = InputFomartConversion.springSplitIntegerlist(qxlist);
	      }
	   	  this.result = this.qxzService.qxToQxzSave(qxz, qxbhList); 
	   	if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
	   	  this.czrzService.saveCzrz(token,"编辑权限组","编辑权限组——“"+qxz.getQxzmc()+"”");//基本权限 20.编辑权限组
	   	}

		return "json";
	}

	
	/**
	 * 版本2权限组接口3：删除权限组
	 * @param   token
	 * @param   qxz.qxzbh    权限组编号
	 * @return  权限组删除结果
	 * 接口实例：http://localhost:8080/HighWayCenter/qxzmanager/qxzdelete?qxz.qxzbh=11&token=ZxblYG26               
	 */
	
	/*@Action(value = "qxzdelete", results = { @Result(name = "json", type = "json")})*/
	public String qxzDelete(){
		//验证令牌
   	 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	//验证权限
    	 validate_code = this.glryservice.authorityCheck("qxzdelete",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
    	 //令牌验证结束
		if(qxz==null||qxz.getQxzbh()==null||qxz.getQxzbh().equals("")){
			//权限组编号为空
			this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);	
	    	return "json";  
		}
		    this.result= this.qxzService.qxzDelete(qxz.getQxzbh()); 
	 if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
	   	  this.czrzService.saveCzrz(token,"删除权限组","删除权限组——“"+(String)result.getObj()+"”");//基本权限 21.删除权限组
		 }
		return "json";
	}
	
	


	public BaseResult getResult() {
		return result;
	}
	public void setResult(BaseResult result) {
		this.result = result;
	}
	public String getQxlist() {
		return qxlist;
	}
	public void setQxlist(String qxlist) {
		this.qxlist = qxlist;
	}

	public HJcQxz getQxz() {
		return qxz;
	}
	public void setQxz(HJcQxz qxz) {
		this.qxz = qxz;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}

	
	protected String trimAll(String trimstring){
		 trimstring = (trimstring==null)?null:trimstring.trim();
		 return  trimstring;
	}
	
}
