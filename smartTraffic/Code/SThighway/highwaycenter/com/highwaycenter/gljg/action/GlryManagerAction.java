package com.highwaycenter.gljg.action;

import javax.annotation.Resource;

import com.common.action.BaseAction;
import com.common.action.BaseResult;
import com.common.action.Constants;
import com.highwaycenter.gljg.model.HJcGlry;
import com.highwaycenter.gljg.model.HjcZw;
import com.highwaycenter.gljg.service.GlryService;
import com.highwaycenter.log.service.CzrzService;
import com.highwaycenter.role.model.HJcJbjsb;

public class GlryManagerAction extends BaseAction{

	private static final long serialVersionUID = 2311000482975342582L;
	
	@Resource(name="glryservice")
	private GlryService glryservice;
	@Resource(name="czrzservice")
	private CzrzService czrzService;
	private HJcGlry glry;
	private HJcJbjsb jbjsb;  // 基本角色表
	private String bmdm;    //部门编号
	private Integer jsbh;   //基本角色
	private BaseResult result;
	/*private String uploadpath;  //图片上传路径
*/	private String token;
	private String oldpassword;
	private String newpassword;
	private Integer rows;
	private Integer page;
	private String bgsdh;   //;;
	private String zwbh;   //,,;,,;
	private HjcZw zw;
  public String getBgsdh() {
		return bgsdh;
	}



	public void setBgsdh(String bgsdh) {
		this.bgsdh = bgsdh;
	}



	public String getZwbh() {
		return zwbh;
	}



	public void setZwbh(String zwbh) {
		this.zwbh = zwbh;
	}



	/*  private File uploadFile;
    private String uploadFileContentType;// 得到文件的类型
    private String uploadFileFileName;// 得到文件的名称
*/	
	/**
	 * 编辑管理人员   
	 * token                令牌
	 * @param glry.rybh     人员编号
	 * @param bmdm          所属管理部门代码
	 * @param glry.ryxm     人员姓名
	 * @param glry.xmqp     姓名全拼
	 * @param glry.xmpyszm  姓名拼音首字母
	 * @param glry.sjch     手机长号
	 * @param glry.sjdh     手机短号
	 * @param glry.zw       职位
	 * @param glry.bgsdh    办公室电话
	 * @param jsbh          角色编号                
	 * @return  人员新增结果  保存人员编号
	 * 接口实例：http://localhost:8080/HighWayCenter/rymanager/glryupdate?glry.rybh=3&bmdm=402881fb4c2b8994014c2b89ae340000&glry.ryxm=张三&jsbh=1&
     *         glry.rymm=123456&glry.xmqp=zhs&glry.xmpyszm=z&glry.sjch=15261667036&glry.sjdh=50934&glry.zw=科员&glry.bgsdh=0573-87889766&token=?                         
	 */
	/*@Action(value = "glryupdate", results = { @Result(name = "json", type = "json")})*/
	public String glryUpdate(){
		System.out.println(" 编辑管理人员   ");

    	//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
     	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
   		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
   		    return "json";
   	     }
     	 //令牌验证结束
     	//验证权限
    	 validate_code = this.glryservice.authorityCheck("glryupdate",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
		
		//判断glry的姓名,手机长号,密码是否为空
     	 if(glry==null||glry.getRybh()==null||glry.getRybh().equals("")||glry.getRyxm()==null||glry.getRyxm().trim().equals("")||
     			 glry.getSjch()==null||glry.getSjch().trim().equals("")){
     		this.result = new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
     	 }else{
			this.result = glryservice.glryUpdate(glry, bmdm,jsbh,zwbh,bgsdh);		
     	 }
     	if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
      //记录日志信息
    	this.czrzService.saveCzrz(token,"编辑管理人员","编辑管理人员——“"+glry.getRyxm()+"”");//基本权限 11.编辑管理人员
     	}
		return "json";		
	}
	
	
	
	/**
	 * 新增管理人员   
	 * @param bmdm          所属管理部门代码
	 * @param glry.ryxm     人员姓名
	 * @param glry.rymm     人员密码
	 * @param glry.xmqp     姓名全拼
	 * @param glry.xmpyszm  姓名拼音首字母
	 * @param glry.sjch     手机长号
	 * @param glry.sjdh     手机短号
	 * @param glry.zw       职位
	 * @param glry.bgsdh    办公室电话
	 * @param jsbh    角色编号                
	 * @return  人员新增结果  保存人员编号
	 * 接口实例：http://localhost:8080/HighWayCenter/rymanager/glrysave?bmdm=402881fb4c2b8994014c2b89ae340000&glry.ryxm=张三&jsbh=1&
     *         glry.rymm=123456&glry.xmqp=zhs&glry.xmpyszm=z&glry.sjch=15261667036&glry.sjdh=50934&glry.zw=科员&glry.bgsdh=0573-87889766                         
	 */
	/*@Action(value = "glrysave", results = { @Result(name = "json", type = "json")})*/
	public String glrySave(){
		//验证令牌
   	 int validate_code = (Integer) this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束
    	 validate_code = this.glryservice.authorityCheck("glrysave",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
		//判断glry的姓名是否为空
     	 if(glry==null||glry.getRyxm()==null||glry.getRyxm().trim().equals("")||
     			 glry.getSjch()==null||glry.getSjch().trim().equals("")){
     		this.result = new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
     	 }else{
			this.result = glryservice.glrySave(glry, bmdm,jsbh,zwbh,bgsdh);
     		
		}
         //记录日志信息
     	if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
     	this.czrzService.saveCzrz(token,"新增管理人员","新增管理人员——“"+glry.getRyxm()+"”");//基本权限 10.新增管理人员
     	}
		return "json";		
	}

	/**
	 * 删除管理人员 (一并删除人员的人员角色关系)
	 * @param glry.rybh    人员编号
	 * @return  人员删除结果  
	 * 接口实例：http://localhost:8080/HighWayCenter/rymanager/glrydelete?glry.rybh=?                     
	 */
	/*@Action(value = "glrydelete", results = { @Result(name = "json", type = "json")})*/
	public String glryDelete(){
		System.out.println("删除人员");
		//验证令牌
   	 int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束
    	 validate_code = this.glryservice.authorityCheck("glrydelete",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
    	 //令牌验证结束
		this.result = glryservice.glryDelete(glry.getRybh(),token);		
        //记录日志信息
		if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
    	this.czrzService.saveCzrz(token,"删除管理人员","删除管理人员——“"+(String)result.getObj()+"”");//基本权限 12.删除管理人员	
		}
		return "json";		
	}
	

	/**
	 * 给人员分配角色
	 * @param glry.rybh    人员编号
	 * @param jbjsb.jsbh   角色编号
	 * @return  分配结果  
	 * 接口实例：http://localhost:8080/HighWayCenter/rymanager/jstory?glry.rybh=1&jbjsb.jsbh=6                   
	 */
/*	@Action(value = "jstory", results = { @Result(name = "json", type = "json")})*/
	public String jsToRy(){
		System.out.println("角色给人员");
		//验证令牌
		int validate_code = this.glryservice.dllpCheck(token);
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		    return "json";
  	     }
    	 //令牌验证结束
			
			if(glry==null||glry.getRybh()==null||glry.getRybh().equals("")){
				//人员编号输入为空
				 System.out.println("人员 ：null");
			    this.result =  new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);	
			}else if(jbjsb==null||jbjsb.getJsbh()==null||jbjsb.getJsbh().equals("")){
				//角色编号输入为空
				 System.out.println("角色 ：null");
				this.result =  new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);	
			}else{
			    System.out.println("人员 ："+glry.getRybh()+" 角色："+jbjsb.getJsbh());
			    this.result = glryservice.jsToRy(glry.getRybh(), jbjsb.getJsbh());
			}
	  System.out.println("分配角色结束---------------");
		return "json";		
	}
	
	/**
	 * 获取用户列表
	 * @param  token   令牌
	 *  @param page    所请求的页数
	 *  @param rows    所请求的行数
	 * @return  result.objs
	 * 接口实例：http://localhost:8080/HighWayCenter/rymanager/glrylistall?page=1&rows=1                  
	 */
	/*@Action(value = "glrylistall", results = { @Result(name = "json", type = "json")})*/
	public String glryListAll(){
		System.out.println("获取用户列表");
		if(page==null){
			//默认系统页数1
			page = 1;
		}
		if(rows == null){
			//默认系统行数2
			rows = -1;
		}
		this.result = this.glryservice.glryListNobm(page, rows);
		return "json";
		
	}
	
	
	/**
	 * 根据属性获取用户列表
	 *  @param glry.xmpyszm  // 拼音首字母
	 *  @param page    所请求的页数
	 *  @param rows    所请求的行数
	 * @return  result.objs
	 * 接口实例：http://localhost:8080/HighWayCenter/rymanager/glrylistbyproperty?page=1&rows=1&glry.xmpyszm=?                  
	 */
	/*@Action(value = "glrylistbyproperty", results = { @Result(name = "json", type = "json")})*/
	public String glryListByProperty(){
		System.out.println("根据属性获取用户列表");
		if(page==null){
			//默认系统页数1
			page = 1;
		}
		if(rows == null){
			//默认系统行数2
			rows = 2;
		}
		if(glry==null){
			glry = new HJcGlry();
		}
		this.result = this.glryservice.glryListByProperty(page, rows,glry.getXmpyszm());
		return "json";
		
	}
	
	/**
	 * 获取用户信息
	 * @param glry.rybh   人员编号
	 * @param token    
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/rymanager/findryInfo?token=?&glry.rybh=?            
	 */
	/*@Action(value = "findryInfo", results = { @Result(name = "json", type = "json")})*/
	public String findRyInfo(){
		System.out.println("获取用户信息");
/*		//验证令牌
		int validate_code = this.glryservice.dllpCheck(token);
	    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	  		    return "json";
	    	 }
	    	 
	    	 validate_code = this.glryservice.authorityCheck("findryInfo",token);
	    	 if(validate_code != Constants.SUCCESS_CODE){
	    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
	   		    return "json";
	   	     }*/
        if(glry==null||glry.getRybh()==null||glry.getRybh().equals("")){
         this.result = new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
         return "json";
        }
		this.result = this.glryservice.selectGlryInfo(glry.getRybh());
        //记录日志信息
    	/*this.czrzService.saveCzrz(token,"查看管理人员");//基本权限 3.查看管理人员
*/		return "json";
		
	}
	
	
	/**
	 * 获取个人信息  
	 * @param token    
	 * @return  result.obj
	 * 接口实例：http://localhost:8080/HighWayCenter/rymanager/findryInfo?token=?&glry.rybh=?            
	 */
	/*@Action(value = "finduserInfo", results = { @Result(name = "json", type = "json")})*/
	public String findUserInfo(){
		System.out.println("获取用户信息");
		//验证令牌
		int validate_code = this.glryservice.dllpCheck(token);
	    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
	  		    this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
	  		    return "json";
	    	 }
	    	 validate_code = this.glryservice.authorityCheck("finduserInfo",token);
	    	 if(validate_code != Constants.SUCCESS_CODE){
	    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
	   		    return "json";
	   	     }
		this.result = this.glryservice.selectUserInfo(token);
        //记录日志信息
    	/*this.czrzService.saveCzrz(token,"查看个人信息");//基本权限 34.查看个人信息
*/		return "json";
		
	}
	
	/**
	 * 用户上传头像
	 * @param uploadpath    上传路径
	 * @param glry.rybh     人员编号  
	 * @return  result.objs
	 * 接口实例：http://localhost:8080/HighWayCenter/rymanager/imgupload?uploadpath=?&glry.rybh=?            
	 */
	/*@Action(value = "imgupload", results = { @Result(name = "json", type = "json")})*/
	public void imgUpload(){
		/*String jsonResult = null;*/
		/* try{
        	 System.out.println(uploadFileFileName+"token "+token);
        	 String realpath = ServletActionContext.getServletContext().getRealPath("/")+Constants.IMG_HEADPICTURE_PATH;        
             System.out.println("上传到服务器的地址realpath: "+realpath);   
          
             String uploadPath = realpath;
		     BaseResult flag = FileUpload.imgUploadAndCompress(glry.getRybh().toString(),uploadFile,uploadFileFileName,realpath);
		   
		   if(flag.getResultcode()!=1){
			   jsonResult = "{'resultcode':'"+flag.getResultcode()+"','resultdesc':'"+flag.getResultdesc()+"'}";
		   }else{
			   //删除以前的原头像
			   String[] extions = {".jpg",".jpeg",".png",".gif",".bmp"};
			   for(int i=0;i<extions.length;i++){
				   String newextion = uploadFileFileName.substring(uploadFileFileName.lastIndexOf("."));
				   System.out.println("扩展名"+newextion);
				  if(!newextion.equals(extions[i])){
					  FileUpload.deleteWebFile(uploadPath+glry.getRybh()+extions[i]);
				  }
			   }

			  
			   //保存人员信息 
			   
			   jsonResult = "{'resultcode':1,'resultdesc':'上传成功'}";*/
		/*   }*/
	
		  /* String a1=FileUpload.findFiles(uploadPath+"1_small"+".jpg");
		   String a2 = FileUpload.findFiles(uploadPath+"18_small"+".jpg");
           System.out.println("a1:"+a1+" a2:"+a2);*/
        
/*         HttpServletResponse response = ServletActionContext.getResponse();
         response.setContentType("text/html");
		 response.getWriter().write(jsonResult);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(java.lang.Exception e){
			e.printStackTrace();
		}
         */
		
	}
	
	
	/**
	 * 用户修改密码
	 * @param token          令牌
	 * @param glry.rybh
	 * @param oldpassword    旧密码
	 * @param newpassword    新密码
	 * @return  
	 * 接口实例：http://localhost:8080/HighWayCenter/rymanager/passwordupdate?oldpassword=123456&newpassword=ZOUMY&token=ZxrJibcz            
	 */
	/*@Action(value = "passwordupdate", results = { @Result(name = "json", type = "json")})*/
	public String passwordUpdate(){		
		System.out.println("用户修改密码");
		//验证令牌
		
		int validate_code = this.glryservice.dllpCheck(token);
		
   	
		 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
			  this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		      return "json";
			}
		 validate_code = this.glryservice.authorityCheck("lxqx",token);
		 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
		//Integer rybh = (Integer) .getObj();
		if(oldpassword==null||oldpassword.trim().equals("")||newpassword==null||newpassword.trim().equals("")){
			this.result = new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
		}else{
		     this.result =this.glryservice.passwordUpdate(token,oldpassword,newpassword);	
		}
        //记录日志信息
		if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
    	this.czrzService.saveCzrz(token,"修改密码","修改密码");//基本权限 13.编辑个人设置
		}
		return "json";
		
	}

	/**
	 * 重置密码
	 * @param token          令牌
	 * @param newpassword    新密码
	 * @return  
	 * 接口实例：http://localhost:8080/HighWayCenter/rymanager/pwchongzhi?newpassword=ZOUMY&token=ZxrJibcz&glry.rybh=?           
	 */
/*	@Action(value = "pwchongzhi", results = { @Result(name = "json", type = "json")})*/
	public String pwChongzhi(){		
		System.out.println("用户重置密码");
		//验证令牌
		
		int validate_code = this.glryservice.dllpCheck(token);
		 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
			  this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
		      return "json";
		 }
		 validate_code = this.glryservice.authorityCheck("pwchongzhi",token);
		 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
		System.out.println("人员编号"+glry.getRybh());
		if(glry==null||glry.getRybh()==null||glry.getRybh().equals("")||newpassword==null||newpassword.trim().equals("")){
			this.result = new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
		}else{
		     this.result =this.glryservice.pwChongzhi(glry.getRybh(),newpassword);	
		}
        //记录日志信息
		if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
    	this.czrzService.saveCzrz(token,"重置密码","重置密码");//基本权限 11.编辑管理人员
		}
		return "json";	
	}

	
	/**
	 * 用户修改个人信息
	 * @param token          令牌
	 * @param       人员信息
	 * @return  
	 * 接口实例：http://localhost:8080/HighWayCenter/rymanager/basicinfoupdate?token=
	 */

	public String basicInfoUpdate(){		
		System.out.println("用户修改个人信息");
		
       
    	//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
	   	
     	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
   		   this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
   		   return  "json";
   		 
     	 }
        	validate_code= this.glryservice.authorityCheck("basicinfoupdate",token);
        	 if(validate_code != Constants.SUCCESS_CODE){
        		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
       		    return "json";
       	     }
     	 if(glry==null||glry.getRybh()==null||glry.getRybh().equals("")||glry.getRyxm()==null||glry.getRyxm().trim().equals("")||
     			 glry.getSjch()==null||glry.getSjch().trim().equals("")){
     	
		    this.result = new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
   	        return "json";
     		
     	 } 
     		 this.result = glryservice.glryBasicUpdate(glry);	
			  	
     	    
     	   //记录日志信息
     		if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
     	     this.czrzService.saveCzrz(token,"编辑个人设置","编辑个人设置");//基本权限 13.编辑个人设置
     		}
	        return "json";
			 
        }
	         
     /**
      * 获取职位下拉列表（按照职位顺序）
      * @param token  令牌
      * @result 
      * 接口实例：http://localhost:8080/HighWayCenter/rymanager/postlist?token=
	 */

	public String selectPostlist(){		
		System.out.println("职位列表");
    	//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
	   	
     	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
   		   this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
   		   return  "json";
   		 
     	 }
     		 this.result = glryservice.selectZwList(-1,-1);	
	        return "json";
			 
        }
	
	/**
     * 获取职位下拉列表（分页）
     * @param token  令牌
     * @param page
     * @param rows
     * @result 
     * 接口实例：http://localhost:8080/HighWayCenter/rymanager/postlistpage
	 */

	public String selectPostlistPage(){		
		System.out.println("维护职位的分页列表");
		 int validate_code = this.glryservice.dllpCheck(token);
		   	
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		   this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		   return  "json";		 
    	 }
    	 validate_code= this.glryservice.authorityCheck("whzw",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
    	 if(page==null||page.equals("")){
				page = Constants.MOREN_PAGE;
			}
		 if(rows==null||rows.equals("")){
				rows=Constants.MOREN_ROWS;
		   }
		 this.result = glryservice.selectZwList(page, rows);	
	        return "json";
			 
       }
	/**
     * 新增职位
     * @param token  令牌
     * @param zw.zwmc 职位名称
     * @result 
     * 接口实例：http://localhost:8080/HighWayCenter/rymanager/savepost
	 */

	public String savePost(){		
   	//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
	   	
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		   this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		   return  "json";		 
    	 }
    	 validate_code= this.glryservice.authorityCheck("whzw",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
    	 if(zw==null||zw.getZwmc()==null||"".equals(zw.getZwmc())){
    		 this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
    		 return "json";
    	 }
    		 this.result = glryservice.savePost(zw);
    		 
    		//记录日志信息
    		if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
     	     this.czrzService.saveCzrz(token,"新增职位","新增职位——“"+zw.getZwmc()+"”");//基本权限 .编辑个人设置
    		 }
	        return "json";
	    
			 
       }
	
	
	/**
     *编辑职位
     * @param token  令牌
     * @param zw.zwbh 职位编号
     * @param zw.zwmc 职位名称
     * @result 
     * 接口实例：http://localhost:8080/HighWayCenter/rymanager/updatepost
	 */

	public String updatePost(){		
   	//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
	   	
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		   this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		   return  "json";		 
    	 }
    	 validate_code= this.glryservice.authorityCheck("whzw",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
    	 if(zw==null||zw.getZwbh()==null||zw.getZwbh().equals("")||zw.getZwmc()==null||"".equals(zw.getZwmc())){
    		 this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
    		 return "json";
    	 }
    		 this.result = glryservice.updatePost(zw);
    			//记录日志信息
    		if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
     	     this.czrzService.saveCzrz(token,"更新职位","更新职位——“"+zw.getZwmc()+"”");//基本权限 .编辑个人设置
    		 }
	        return "json";
			 
       }
	
	
	/**
     * 删除职位
     * @param token  令牌
     * @param zw.zwbh 职位编号
     * @result 
     * 接口实例：http://localhost:8080/HighWayCenter/rymanager/deletepost
	 */

	public String deletePost(){		
   	//验证令牌
		 int validate_code = this.glryservice.dllpCheck(token);
	   	
    	 if(validate_code == Constants.DLLP_CHECK_FAIL_CODE){
  		   this.result =  new BaseResult(validate_code,Constants.DLLP_CHECK_FAIL_INFO);
  		   return  "json";		 
    	 }
    	 validate_code= this.glryservice.authorityCheck("whzw",token);
    	 if(validate_code != Constants.SUCCESS_CODE){
    		 this.result =  new BaseResult(validate_code,Constants.AUTHORITY_CHECK_FAIL_INFO);
   		    return "json";
   	     }
    	 if(zw==null||zw.getZwbh()==null||zw.getZwbh().equals("")){
    		 this.result =  new BaseResult(Constants.REQUEST_PARAM_NULL_CODE,Constants.REQUEST_PARAM_NULL_INFO);
    		 return "json";
    	 }
    		 this.result = glryservice.deletePost(zw);
    		if(result!=null&&result.getResultcode()==Constants.SUCCESS_CODE){
    		 this.czrzService.saveCzrz(token,"删除职位","删除职位——“"+(String)result.getObj()+"”");//基本权限 .编辑个人设置
    		 }
 	        return "json";
			 
       }
	public HJcJbjsb getJbjsb() {
		return jbjsb;
	}



	public void setJbjsb(HJcJbjsb jbjsb) {
		this.jbjsb = jbjsb;
	}



	public HJcGlry getGlry() {
		return glry;
	}

	public void setGlry(HJcGlry glry) {
		this.glry = glry;
	}

	public String getBmdm() {
		return bmdm;
	}

	public void setBmdm(String bmdm) {
		this.bmdm = bmdm;
	}

	public BaseResult getResult() {
		return result;
	}

	public void setResult(BaseResult result) {
		this.result = result;
	}




	public Integer getJsbh() {
		return jsbh;
	}



	public void setJsbh(Integer jsbh) {
		this.jsbh = jsbh;
	}



	public String getToken() {
		return token;
	}



	public void setToken(String token) {
		this.token = token;
	}




	public String getOldpassword() {
		return oldpassword;
	}



	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}



	public String getNewpassword() {
		return newpassword;
	}



	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}



	public Integer getRows() {
		return rows;
	}



	public void setRows(Integer rows) {
		this.rows = rows;
	}



	public Integer getPage() {
		return page;
	}



	public void setPage(Integer page) {
		this.page = page;
	}



	public HjcZw getZw() {
		return zw;
	}



	public void setZw(HjcZw zw) {
		this.zw = zw;
	}











}
