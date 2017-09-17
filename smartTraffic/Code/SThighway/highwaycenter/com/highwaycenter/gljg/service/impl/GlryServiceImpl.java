package com.highwaycenter.gljg.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.action.Constants;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.common.action.BaseResult;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.SQL;
import com.common.service.BaseService;
import com.common.utils.AesSecure;
import com.common.utils.InputFomartConversion;
import com.common.utils.PropertyLoader;
import com.common.utils.ValidateCode;
import com.highwaycenter.bean.GlryInfo;
import com.highwaycenter.common.SessionMap;
import com.highwaycenter.gljg.dao.DllpDao;
import com.highwaycenter.gljg.dao.GlbmDao;
import com.highwaycenter.gljg.dao.GlbmgxDao;
import com.highwaycenter.gljg.dao.GljgDao;
import com.highwaycenter.gljg.dao.GlryDao;
import com.highwaycenter.gljg.dao.RybmDao;
import com.highwaycenter.gljg.model.HJcDllp;
import com.highwaycenter.gljg.model.HJcGlbm;
import com.highwaycenter.gljg.model.HJcGljg;
import com.highwaycenter.gljg.model.HJcGlry;
import com.highwaycenter.gljg.model.HJcRybm;
import com.highwaycenter.gljg.model.HjcZw;
import com.highwaycenter.gljg.service.GlryService;
import com.highwaycenter.log.dao.CzrzDao;
import com.highwaycenter.role.dao.JbjsbDao;
import com.highwaycenter.role.dao.JsqxDao;
import com.highwaycenter.role.dao.QxzDao;
import com.highwaycenter.role.dao.QxzqxdyDao;
import com.highwaycenter.role.dao.RyjsDao;
import com.highwaycenter.role.model.HJcJbjsb;
import com.highwaycenter.role.model.HJcRyjs;
import com.highwaycenter.role.model.HJcRyjsId;
import com.opensymphony.xwork2.ActionContext;
import com.highwaycenter.bean.AuthoritySession;

@Service("glryservice")
public class GlryServiceImpl extends BaseService implements GlryService{

	@Resource(name ="glrydao")
	private GlryDao glryDao;
	@Resource(name="dllpdao")
	private DllpDao dllpDao;
	@Resource(name="czrzdao")
	private CzrzDao czrzDao;
	@Resource(name="glbmdao")
	private GlbmDao glbmDao;
	@Resource(name="glbmgxdao")
	private GlbmgxDao glbmgxDao;
	@Resource(name="gljgdao")
	private GljgDao gljgDao;
	@Resource(name="jbjsbdao")
	private JbjsbDao jbjsbDao;
	@Resource(name="ryjsdao")
	private RyjsDao ryjsDao;
	@Resource(name="rybmdao")
	private RybmDao rybmDao;
	

	@Resource(name="qxzdao")
	private QxzDao qxzDao;

	@Resource(name="qxzqxdydao")
	private QxzqxdyDao qxzqxdyDao;
	@Resource(name="jsqxdao")
	private JsqxDao jsqxDao;

	
	private Map<String,Object> findSession(){
	 ActionContext ac = ActionContext.getContext();
	 Map<String,Object> session = ac.getSession();
	 return session;
	}
	
	/**
	 * 用户登录service
	 * 
	 */
	@Override
	public BaseResult login(String username, String password,
			Timestamp dlsj, String dldz,Timestamp scczsj,String isRemember) {
		  
		 HJcGlry glry = this.glryDao.findByUsername(username);  
		 //HJcGlry glry = this.glryDao.findByName(username); 
			//加密
			String key = Constants.AESKEY;
			String rymm = AesSecure.encode(password, key);
			
		 if(glry==null){
			 //用户名不存在，查询失败，返回用户名错误
			 return new BaseResult(Constants.LOGIN_NAME_ERROR_CODE,Constants.LOGIN_NAME_ERROR_INFO);
		 }else if(!glry.getRymm().equals(rymm)){
			 //密码不匹配，查询失败，返回密码错误
			 return new BaseResult(Constants.LOGIN_PASS_ERROR_CODE,Constants.LOGIN_PASS_ERROR_INFO);
		 }
		 //用户名密码验证正确	 
	
		 //删除原来的令牌
		 this.dllpDao.deleteByRy(glry.getRybh());
		 //生成新的令牌
		 String dllp =  this.dllpSave(glry, dlsj, dldz, scczsj);	
	    //获取用户权限列表
	     Map<String, Object> session = findSession();
	     AuthoritySession ryqxSession = this.selectUserQx(glry.getRybh());		
	     if(ryqxSession!=null){//有权限则存入
	    	 //检测内存中是否有session
	    	     SessionMap.saveAuSession(ryqxSession);
	     }
	    /* String ryuser = this.getUserQx(glry.getRybh());		
		 session.put("authority",ryuser);//权限信息 没有权限则为null
*/	     session.put("token", dllp);
	     session.put("userid", glry.getRybh());
	    //保存cookie
	     this.saveCookie(username, password, isRemember, "user");
	  
	    BaseResult result =  new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
    	 /* cookie.setMaxAge(60*60*24*7); //cookie 保存7天
    	  * */ 
    	
	    result.setObj(dllp);
	    
		return result; 
	}

	/**
	 * 解码记住密码
	 */
	@Override
	public BaseResult decodePassword(){
		BaseResult result = new BaseResult();
		List list = this.getCookie("user");
		result.setList(list);
		return result;
	}
	/**
	 * 令牌生成service
	 * 
	 */
	@Override
	public String dllpSave(HJcGlry glry, Timestamp dlsj, String dldz,
			Timestamp scczsj) {
		//生成64位令牌  并保存  DLLP_LENGTH=8  测试为8位数字
		HJcDllp dllp = new HJcDllp(ValidateCode.genValidateCode(Constants.DLLP_LENGTH),glry.getRybh(),dlsj,dldz,scczsj);
		//保存令牌后返回令牌
		return dllpDao.saveAndReturn(dllp);
	}
     
	/**
	 * 令牌更新service
	 * 
	 */
	public String dllpUpdate(HJcGlry glry, Timestamp dlsj, String dldz,
			Timestamp scczsj){
		String newLp = ValidateCode.genValidateCode(Constants.DLLP_LENGTH);
		this.dllpDao.updateLp(newLp, dlsj, dldz, scczsj, glry.getRybh());
		return newLp;
		
	}
	/**
	 * 令牌查询service
	 */
	@SuppressWarnings("unused")
	@Override
	public int dllpCheck(String token) {
	    BaseResult result =  new BaseResult();
		if(token == null||token.trim().equals("")){
		    return Constants.DLLP_CHECK_FAIL_CODE; 
		  
		}
		HJcDllp dllp = this.dllpDao.findById(token);
		if(dllp==null){
		   this.logout(token);
			return Constants.DLLP_CHECK_FAIL_CODE;
		}else if(dllp.getRybh()==null||dllp.getRybh().equals("")){
			 this.logout(token);
			return Constants.DLLP_CHECK_FAIL_CODE;
		}else{
			Timestamp oldoperate = dllp.getScczsj();
			//令牌查询成功之后进行令牌的操作时间更新更新操作
			 Timestamp operateTime = new Timestamp(System.currentTimeMillis());
			 long ms = operateTime.getTime()-oldoperate.getTime();
			 System.out.println("令牌时间差"+ms+"  "+ms/(80*60*1000));
			 if(ms>80*60*1000){
				 //超过30分钟的令牌失效
				 this.logout(token);
				 return Constants.DLLP_CHECK_FAIL_CODE;
			 }else{
			 dllp.setScczsj(operateTime);
			 this.dllpDao.update(dllp);
			 //Integer dlry = dllp.getRybh();
			 }
		}
		
		return Constants.SUCCESS_CODE;
	}
	
	@Override
	public int authorityCheck(String actionName,String token){ 
		Integer rybh = this.dllpDao.rybhBylp(token);
		 String ryqxlist = SessionMap.checkAuSession(rybh);
		 if(ryqxlist.equals("checknull")){
			 return Constants.DLLP_CHECK_FAIL_CODE;
		 }
		 System.out.println("人员权限"+ryqxlist);
		 if(!ryqxlist.equals("false")){
		 Properties pro = PropertyLoader.getProperties("authority.properties");
		 String actionAut = pro.getProperty("action."+actionName).trim();
		 System.out.println("action权限："+actionAut);
		 if(actionAut!=null&&ryqxlist!=null){
			
			 if(ryqxlist.indexOf("'"+actionAut+"'")==-1){
				 return Constants.AUTHORITY_CHECK_FAIL_CODE;
			 }
		 }else if(actionAut!=null){
			 return Constants.AUTHORITY_CHECK_FAIL_CODE;
		 }
	         return Constants.SUCCESS_CODE;
		}else{
			 return Constants.DLLP_CHECK_FAIL_CODE;
		}
	}
	@Override
	public BaseResult glrySave(HJcGlry glry, String bmdm,Integer jsbh,String zwbh,String bgsdh) {
		try{
		BaseResult result =new BaseResult();
		HJcJbjsb js = new HJcJbjsb();
		HJcRyjsId ryjsId = new HJcRyjsId();
	
		//1.判断该手机长号是否唯一，手机长号作为登陆用户名
				if(glry.getSjch()==null||glry.getSjch().trim().equals("")){
					//手机号未填写
					return new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
				}
				//判断该手机长号是否唯一，手机长号作为登陆用户名

				int sjcount = this.glryDao.countPhoneName(glry.getSjch().trim(),glry.getSjch().trim());
				if(sjcount>0){
					//手机号已经存在
					return new BaseResult(Constants.BMRY_SJCHERROR_CODE,Constants.BMRY_SJCHERROR_INFO);
				}	
				
		   //2.判断用户名是否存在
				if(glry.getRyxm()==null||glry.getRyxm().trim().equals("")){
					//手机号未填写
					return new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
				}
				

				int xmcount = this.glryDao.countPhoneName(glry.getRyxm().trim(),glry.getRyxm().trim());
				if(xmcount>0l){
					//姓名已经存在
					return new BaseResult(Constants.BMRY_RYXMERROR_CODE,Constants.BMRY_RYXMERROR_INFO);
				}
				
			
		
		
				//3.角色判断
				if(jsbh!=null&&!jsbh.equals("")){
					//判断角色是否存在
					 js = this.jbjsbDao.findById(jsbh);
					if(js==null){
					    return new BaseResult(Constants.JSBH_ERROR_CODE,Constants.JSBH_ERROR_INFO);
				    }		
				}
				
				//4.部门是否重复判断
				if(bmdm!=null&&!bmdm.trim().equals("")){
				    String [] judge  = bmdm.split(";");
				    int length = judge.length;
				    if(length>1){
				    	Set<String> set = new HashSet<String>();
				    	for(String str:judge){
				    		set.add(str);
				    	}
				    	System.out.println("set长度"+set.size()+"| 数组长度"+length);
				    	if(set.size()<length){
				    		 return new BaseResult(49,"部门不能重复添加");
				    	}
				    }
				    
				}
				
				
		//加密
		String key = Constants.AESKEY;
		String rymm = AesSecure.encode(glry.getRymm(), key);
		glry.setRymm(rymm);
		//加密结束
		Integer rybh = this.glryDao.saveAndReturn(glry);//保存
		
		//保存人员角色关系
		if(jsbh!=null&&!jsbh.equals("")){
			ryjsId.setHJcGlry(new HJcGlry(rybh));
			ryjsId.setHJcJbjsb(js);
			this.ryjsDao.save(new HJcRyjs(ryjsId));
			
		}
		
		//3.管理部门设置
		List<HJcRybm> rybmlist =new ArrayList<HJcRybm>();
	    
		if(bmdm!=null&&!bmdm.trim().equals("")){
			//分解部门
			String[] bmdms = bmdm.split(";");
			int length = bmdms.length;
			String[] zwbhs = new String[length];
			String[] bgsdhs = new String[length];
			//3.1 职位电话设置
			if(zwbh!=null&&!"".equals(zwbh)){
				zwbhs = zwbh.split(";");
				System.out.println(zwbhs.length);
			}
            if(bgsdh!=null&&!"".equals(bgsdh)){
            	bgsdhs = bgsdh.split(";");
			} 
			for(int i=0;i<length;i++){
				HJcRybm rybmtemp = new HJcRybm();
				rybmtemp.setBmbh(bmdms[i]);
				rybmtemp.setRybh(rybh);
				if(zwbhs.length>i){
				   if(zwbhs[i]!=null&&!"".equals(zwbhs[i].trim())){
					   rybmtemp.setZwbh(zwbhs[i]);
				   }
				}
				if(bgsdhs.length>i){
				  if(bgsdhs[i]!=null&&!"".equals(bgsdhs[i].trim())){
					  rybmtemp.setBgsdh(bgsdhs[i]);
				   }
				}
				rybmlist.add(rybmtemp);
			}
			
		}
		
		//保存管理部门与人员的关系
				if(rybmlist!=null&&rybmlist.size()>0){
					for(int i=0;i<rybmlist.size();i++){
						this.rybmDao.save(rybmlist.get(i));
					}
				}

		
	
		
		result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		result.setObj(rybh);   //将保存好的人员编号保存到返回数据里面的obj
		
         
		return result;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public BaseResult glryUpdate(HJcGlry glry, String bmdm,Integer jsbh,String zwbh,String bgsdh) {
		try{
		BaseResult result =new BaseResult();
		HJcJbjsb js = new HJcJbjsb();
		HJcRyjsId ryjsId = new HJcRyjsId();
	
		//0.判断是否为超级管理员
		if(glry.getRybh()==Constants.SUPERMANAGER_RYBH){
			return new BaseResult(Constants.SUPERMANAGER_JS_UPDATE_CODE,Constants.SUPERMANAGER_JS_UPDATE_INFO);
		}
		
		//1.判断该手机长号是否唯一，手机长号作为登陆用户名
		if(glry.getSjch()==null||glry.getSjch().trim().equals("")){
			//手机号未填写
			return new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
		}
		int samesjch_ry = this.glryDao.countBhPhoneName(glry.getRybh(),glry.getSjch().trim(),glry.getSjch().trim());
		if(samesjch_ry>0){
			//手机号已经存在并且不是原本该人的手机
			return new BaseResult(Constants.BMRY_SJCHERROR_CODE,Constants.BMRY_SJCHERROR_INFO);
		}

			//2.判断姓名是否唯一，姓名作为登陆用户名
			if(glry.getRyxm()==null||glry.getRyxm().trim().equals("")){
				//手机号未填写
				return new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
			}
			int samexm_ry = this.glryDao.countBhPhoneName(glry.getRybh(),glry.getSjch().trim(),glry.getSjch().trim());
			if(samexm_ry>0){
				//姓名已经存在并且不是原本该人的姓名
				return new BaseResult(Constants.BMRY_RYXMERROR_CODE,Constants.BMRY_RYXMERROR_INFO);
			}
			//3.部门是否重复判断
			if(bmdm!=null&&!bmdm.trim().equals("")){
			    String [] judge  = bmdm.split(";");
			    int length = judge.length;
			    if(length>1){
			    	Set<String> set = new HashSet<String>();
			    	for(String str:judge){
			    		set.add(str);
			    	}
			    	System.out.println("set长度"+set.size()+"| 数组长度"+length);
			    	if(set.size()<length){
			    		 return new BaseResult(49,"部门不能重复添加");
			    	}
			    }
			    
			}

			//4.管理部门设置
			List<HJcRybm> rybmlist =new ArrayList<HJcRybm>();
		    
			if(bmdm!=null&&!bmdm.trim().equals("")){
				//分解部门
				String[] bmdms = bmdm.split(";");
				int length = bmdms.length;
				String[] zwbhs = new String[length];
				String[] bgsdhs = new String[length];
				//3.1 职位电话设置
				if(zwbh!=null&&!"".equals(zwbh)){
					zwbhs = zwbh.split(";");
				}
                if(bgsdh!=null&&!"".equals(bgsdh)){
                	bgsdhs = bgsdh.split(";");
				} 
                System.out.println(""+zwbhs.length);
				for(int i=0;i<length;i++){
					HJcRybm rybmtemp = new HJcRybm();
					rybmtemp.setBmbh(bmdms[i]);
					rybmtemp.setRybh(glry.getRybh());
					if(zwbhs.length>i){
						   if(zwbhs[i]!=null&&!"".equals(zwbhs[i].trim())){
							   rybmtemp.setZwbh(zwbhs[i]);
						   }
						}
						if(bgsdhs.length>i){
						  if(bgsdhs[i]!=null&&!"".equals(bgsdhs[i].trim())){
							  rybmtemp.setBgsdh(bgsdhs[i]);
						   }
						}
					rybmlist.add(rybmtemp);
				}
				
			}
		
	
		//5.角色判断
		if(jsbh!=null&&!jsbh.equals("")){
			//判断角色是否存在
			 js = this.jbjsbDao.findById(jsbh);
			if(js==null){
			    return new BaseResult(Constants.JSBH_ERROR_CODE,Constants.JSBH_ERROR_INFO);
		    }		
		}
		
		//从数据库取出密码
		String rymm = this.glryDao.selectRymm(glry.getRybh());
		glry.setRymm(rymm);
		 this.glryDao.update(glry);//更新人员表
		//删除人员部门关系
		 this.rybmDao.deleteByry(glry.getRybh());
		//保存管理部门与人员的关系
			if(rybmlist!=null&&rybmlist.size()>0){
				for(int i=0;i<rybmlist.size();i++){
					this.rybmDao.save(rybmlist.get(i));
				}
			}

	
		 if(jsbh!=null&&!jsbh.equals("")){   //更新角色表
		 
		
			HJcRyjs ryjs = this.ryjsDao.findByGlry(glry);	//在人员角色表中查询该人员是否已经存在原有角色		
			if(ryjs==null){
				//该人员以前并未添加过角色
				//在人员角色表新增人员角色关系
				System.out.println("第一次保存-----新建的人员角色关系   "+glry.getRybh()+" "+js.getJsbh());
				ryjsId.setHJcGlry(new HJcGlry(glry.getRybh()));
				ryjsId.setHJcJbjsb(js);
				this.ryjsDao.save(new HJcRyjs(ryjsId));	
				
			}else if(ryjs.getId().getHJcJbjsb().getJsbh()!=js.getJsbh()){
				//角色表已经存在该人员的角色并且与需要更新的数据不同，需删除后重新保存该数据
				this.ryjsDao.delete(ryjs);//删除以前的数据
				System.out.println("删除后保存-----新建的人员角色关系   "+glry.getRybh()+" "+js.getJsbh());
				ryjsId.setHJcGlry(new HJcGlry(glry.getRybh()));   //保存新的人员角色关系
				ryjsId.setHJcJbjsb(js);
			    ryjs.setId(ryjsId);
				 this.ryjsDao.save(ryjs);
				 //更新该人员的权限session
				 AuthoritySession ausession = this.selectUserQx(glry.getRybh());
				 SessionMap.updateAuSession(ausession);
			}else{
				//人员角色关系并未变化，无需更新
				
			}

		 }
		
		result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);   
		return result;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public BaseResult glryDelete(Integer rybh,String token) {
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		//0.判断是否为超级管理员 （超级管理员编号：0）
     
        if(rybh==Constants.SUPERMANAGER_RYBH){
        	return new BaseResult(Constants.SUPERMANAGER_JS_DELETE_CODE,Constants.SUPERMANAGER_JS_DELETE_INFO);
        }
		//根据id获取管理人员实例
		HJcGlry glry = this.glryDao.findById(rybh);
		if(glry==null){
			//该管理人员不存在
			return new BaseResult(Constants.BMRY_ERROR_CODE,Constants.BMRY_ERROR_INFO);
		}else{
			//1.判断是否是自身
			   //根据token获取人员编号
			HJcDllp tokenry = this.dllpDao.findById(token);
		    if(tokenry!=null&&tokenry.getRybh()!=null){
			   Integer token_rybh  = tokenry.getRybh();
			   if(token_rybh == glry.getRybh()){
			 		return new BaseResult(Constants.RYBH_DELETE_ERROR_CODE,Constants.RYBH_DELETE_ERROR_INFO);
					     }
					 }

			this.ryjsDao.deleteByRy(rybh);
			//删除人员的权限session内存信息
			SessionMap.deleteAuSession(rybh);
			//删除人员部门关系
			// this.ryzwgxDao.delete(glry.getRybh());
			this.rybmDao.deleteByry(rybh);
			//删除口令相关信息
			this.dllpDao.deleteByRy(rybh);
			this.czrzDao.deleteByRy(rybh);
			result.setObj(glry.getRyxm());
			
			this.glryDao.delete(glry);	
			
		}
		
		return result;
	}


	@Override
	public BaseResult jsToRy(Integer rybh, Integer jsbh) {
		//0.判断是否为超级管理员
				if(rybh==Constants.SUPERMANAGER_RYBH){
					return new BaseResult(Constants.SUPERMANAGER_JS_UPDATE_CODE,Constants.SUPERMANAGER_JS_UPDATE_INFO);
				}
		
		HJcRyjsId ryjsId = new HJcRyjsId();
		HJcRyjs ryjs = new HJcRyjs ();
		//查看人员是否存在
		HJcGlry glry = this.glryDao.findById(rybh);
		if(glry==null){
		    return new BaseResult(Constants.BMRY_ERROR_CODE,Constants.BMRY_ERROR_INFO);
		}
		
		HJcJbjsb js = this.jbjsbDao.findById(jsbh);
		if(js==null){
			 return new BaseResult(Constants.JSBH_ERROR_CODE,Constants.JSBH_ERROR_INFO);
		}
		
		//1.删除人员角色的相关数据
		this.ryjsDao.deleteByRy(rybh);
		//2.新增人员角色关系
		
		
		//查看该管理人员在人员角色表是否已经存在角色关系
		/*HJcRyjs ryjs = this.ryjsDao.findByGlry(glry);
		if(ryjs!=null){
			//存在人员角色关系
			this.ryjsDao.delete(ryjs);  //删除该关系
		}else{
			//
			ryjs = new HJcRyjs ();
		}*/
		ryjsId.setHJcGlry(glry);   //保存新的人员角色关系
		ryjsId.setHJcJbjsb(js);
		ryjs.setId(ryjsId);
		this.ryjsDao.save(ryjs);
		 //更新该人员的权限session
		 AuthoritySession ausession = this.selectUserQx(glry.getRybh());
		 SessionMap.updateAuSession(ausession);
		return new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
	}


	@Override
	public BaseResult selectGlryInfo(Integer rybh) {
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		
		GlryInfo ryinfo = this.glryDao.selectRyInfo(rybh);	
        
		if(ryinfo!=null){
			//查询部门
	
			 List<HJcRybm> rybmlist = this.rybmDao.selectRybmList(ryinfo.getRybh());
			   for(int j=0;j<rybmlist.size();j++){
				   HJcRybm rybm = rybmlist.get(j);
				   //查询上级机构
				   HJcGljg fjg = this.selectFjgObjBybm(rybm.getBmbh());
				   if(fjg!=null){
				        rybm.setSsjgbh(fjg.getGljgdm());
				        rybm.setSsjgmc(fjg.getGljgmc());
				   }
				   //查询职位
				   String zwbhs = rybm.getZwbh();
				   if(zwbhs!=null&&!"".equals(zwbhs)){
					   String zwmcs  = this.rybmDao.selectZwstr(zwbhs);
					   rybm.setZwmc(zwmcs);
					  
				   }
				   rybmlist.set(j, rybm);
			   }
			   ryinfo.setRybmlist(rybmlist);
			  
	
		  result.setObj(ryinfo);
		
		}else{
			return new BaseResult(Constants.BMRY_ERROR_CODE,Constants.BMBH_ERROR_INFO);
		}
		return result;
	}

	@Override
	public BaseResult passwordUpdate(String token, String oldpw, String newpw) {
		//加密
		String key = Constants.AESKEY;
		String oldPassword = AesSecure.encode(oldpw, key);
		//验证旧密码对不对
		Integer rybh = this.dllpDao.rybhBylp(token); 
		if(rybh==null||rybh.equals("")){
			return new BaseResult(Constants.DLLP_CHECK_FAIL_CODE,Constants.DLLP_CHECK_FAIL_INFO);
		}
		if(rybh==Constants.SUPERMANAGER_RYBH){
			return new BaseResult(Constants.SUPERMANAGER_JS_UPDATE_CODE,Constants.SUPERMANAGER_JS_UPDATE_INFO);
		}
		
		HJcGlry glry = this.glryDao.findById(rybh);
		
		if(glry!=null){
		    if(!glry.getRymm().equals(oldPassword)){
		    	return new BaseResult(Constants.BMRY_OLDPWERROR_CODE,Constants.BMRY_OLDPWERROR_INFO);
		    			
		    }else{
		    	//加密
			    key = Constants.AESKEY;
				String newpassword = AesSecure.encode(newpw, key);
				//加密结束
		    	//旧密码验证成功，更新
		    	glry.setRymm(newpassword);
		    	this.glryDao.update(glry);
		    	return new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);	
		    }
		}	
		return new BaseResult(Constants.BMRY_ERROR_CODE,Constants.BMRY_ERROR_INFO);
	}


	@Override
	public BaseResult glryListNobm(Integer page,Integer rows) {
		BaseQueryRecords baserecords = this.glryDao.selectGlryListNoBm(page, rows);
		BaseResult result= new BaseResult(1,"获取成功");
		result.setObj(baserecords);
		return result;
	}


	@Override
	public BaseResult glryListByProperty(Integer page, Integer rows, String xmpyszm) {
		BaseQueryRecords baserecords = new BaseQueryRecords();
		BaseResult result= new BaseResult(1,"获取成功");
		try{
		
		if(xmpyszm==null){
			baserecords = this.glryDao.selectGlryListNoJS(page, rows);
		}else{
		    baserecords = this.glryDao.selectGlryListByProterty(page,rows,"xmpyszm",xmpyszm);
		}
		
		result.setObj(baserecords);

	}catch(java.lang.Exception e){
		e.printStackTrace();
	}
		return result;
	}

	 /**
     * 获取用户所有基本权限
     */
	@SuppressWarnings("unchecked")

	public AuthoritySession selectUserQx(Integer rybh) {
		StringBuffer qxs = new StringBuffer("");
		String qxsStr;
		List<Integer> userqxs = new ArrayList<Integer>();
		//获取人员的权限列表
		SQL sql = new SQL("select qxbh from h_jc_ryjs as a , h_jc_jsqx "+
				          "as b where a.jsbh = b.jsbh and a.rybh = "+rybh);
		userqxs = (List<Integer>) this.jsqxDao.findBySql(sql).getData();
		System.out.println("userqxs.size()---"+userqxs.size());
        if(userqxs!=null&&userqxs.size()>0){
        	Iterator<Integer> qxIt = userqxs.iterator();
        	while(qxIt.hasNext()){
        		qxs.append("'").append(qxIt.next()).append("',");
        	}
        	qxsStr = qxs.substring(0, qxs.length()-1);
        	Integer jsbh = this.ryjsDao.findjsbhByRy(rybh);
        	AuthoritySession userAu = new AuthoritySession(rybh,jsbh,qxsStr);	
        	 return userAu;       
           }else{
        	   return null;
           }

	 }
	

	

	

	@Override
	public BaseResult pwChongzhi(Integer rybh, String newpw) {
		//0.判断是否为超级管理员 （超级管理员编号：0）
	     
        if(rybh==Constants.SUPERMANAGER_RYBH){
        	return new BaseResult(Constants.SUPERMANAGER_JS_UPDATE_CODE,Constants.SUPERMANAGER_JS_UPDATE_INFO);
        }
				HJcGlry glry = this.glryDao.findById(rybh);
				if(glry!=null){
					//加密
					String key = Constants.AESKEY;
					String newpassword = AesSecure.encode(newpw, key);
				    	glry.setRymm(newpassword);
				    	this.glryDao.update(glry);
				    	return new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);	
		
				}	
				return new BaseResult(Constants.BMRY_ERROR_CODE,Constants.BMRY_ERROR_INFO);
	}


	@Override
	public BaseResult logout(String token) {
		//根据token获取人员id，清空人员权限session
	//	HJcDllp dllp = this.dllpDao.findById(token);


		 //存取session权限
		    ActionContext ac = ActionContext.getContext();
	  	    Map<String,Object> session = ac.getSession();
		    if(session.get("token")!=null){
		 	    session.remove("token");
		     }
		   /* if(session.get("authority")!=null){
		 	    session.remove("authority");
		     }
		   */
		   //token 销毁
		    this.dllpDao.deleteByLp(token);
		
	
		return new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
	}


	@Override
	public BaseResult glryBasicUpdate(HJcGlry glry) {

		//0.判断是否为超级管理员 （超级管理员编号：0）
	     
        if(glry.getRybh()==Constants.SUPERMANAGER_RYBH){
        	return new BaseResult(Constants.SUPERMANAGER_JS_UPDATE_CODE,Constants.SUPERMANAGER_JS_UPDATE_INFO);
        }
		      //1.判断该手机长号是否唯一，手机长号作为登陆用户名
				if(glry.getSjch()==null||glry.getSjch().trim().equals("")){
					//手机号未填写
					return new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
				}
				
				int samesjch_ry = this.glryDao.countBhPhoneName(glry.getRybh(),glry.getSjch().trim(),glry.getSjch().trim());
				System.out.println("手机重复人数"+samesjch_ry);
				if(samesjch_ry>0){
					//手机号已经存在并且不是原本该人的手机
					return new BaseResult(Constants.BMRY_SJCHERROR_CODE,Constants.BMRY_SJCHERROR_INFO);
				}

					//2.判断姓名是否唯一，姓名作为登陆用户名
					if(glry.getRyxm()==null||glry.getRyxm().trim().equals("")){
						//手机号未填写
						return new BaseResult(Constants.WRITE_UNCOMPLETE_CODE,Constants.WRITE_UNCOMPLETE_INFO);
					}
					int samexm_ry = this.glryDao.countBhPhoneName(glry.getRybh(),glry.getRyxm().trim(),glry.getRyxm().trim());
					System.out.println("手机重复人数"+samexm_ry);
					if(samexm_ry>0){
						//姓名已经存在并且不是原本该人的姓名
						return new BaseResult(Constants.BMRY_RYXMERROR_CODE,Constants.BMRY_RYXMERROR_INFO);
					}
				
			//没有头像上传
				
				/*String bmdm = this.glryDao.selectBmByRybh(glry.getRybh());
				if(bmdm!=null&&!bmdm.equals(""))
				{
					glry.setHJcGlbm(new HJcGlbm(bmdm));
				}else{
					glry.setHJcGlbm(null);
				}*/
				//从数据库取出密码
				String rymm = this.glryDao.selectRymm(glry.getRybh());
				glry.setRymm(rymm);
				//5.更新人员信息
				this.glryDao.update(glry);
			  
				return new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);

	}


	@Override
	public BaseResult selectUserInfo(String token) {
		//根据token获取用户编号
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		Integer rybh = this.dllpDao.rybhBylp(token); 
		if(rybh==null||rybh.equals("")){
			return new BaseResult(Constants.DLLP_CHECK_FAIL_CODE,Constants.DLLP_CHECK_FAIL_INFO);
		}
		GlryInfo ryinfo = this.glryDao.selectRyInfo(rybh);
		 List<HJcRybm> rybmlist = this.rybmDao.selectRybmList(ryinfo.getRybh());
		   for(int j=0;j<rybmlist.size();j++){
			   HJcRybm rybm = rybmlist.get(j);
			   //查询上级机构
			   HJcGljg fjg = this.selectFjgObjBybm(rybm.getBmbh());
			   if(fjg!=null){
			        rybm.setSsjgbh(fjg.getGljgdm());
			        rybm.setSsjgmc(fjg.getGljgmc());
			   }
			   //查询职位
			   String zwbhs = rybm.getZwbh();
			   if(zwbhs!=null&&!"".equals(zwbhs)){
				   String zwmcs  = this.rybmDao.selectZwstr(zwbhs);
				   rybm.setZwmc(zwmcs);
				  
			   }
			   rybmlist.set(j, rybm);
		    }
		     ryinfo.setRybmlist(rybmlist);
	       	result.setObj(ryinfo);
			  return result;
				
			
			
	}

           public String selectFjgBybm(String bmdm){
        	   String jgdm = null;
        	   if(bmdm==null||bmdm.trim().equals("")){
        		   //1.该人员属于父机构，返回父机构
        		   HJcGljg fgljg = this.gljgDao.selectFjg();
        		   if(fgljg!=null){
        		        return fgljg.getGljgdm();
        		   }else {
        			   return null;
        		      }
        		   }
        	   //2.递归子部门，查询父部门
        	   String fbmdm = recursiveJgFunction(bmdm);
        	   //3.查询该父部门的 上级机构
        	   String gljg = this.glbmDao.selectSjjg(fbmdm);
        	   
        	   
        	   return gljg;
           }
           

           public HJcGljg selectFjgObjBybm(String bmdm){
        	  
        	   //1.递归子部门，查询父部门
        	   String fbmdm = recursiveJgFunction(bmdm);
        	   //2.查询该父部门的 上级机构
        	   HJcGljg gljg = this.glbmDao.selectSjjgObj(fbmdm);
        	   
        	   
        	   return gljg;
           }
           
           
           public String selectFjgMcBybm(String bmdm){
        	   String jgdm = null;
        	   if(bmdm==null||bmdm.trim().equals("")){
        		   //1.该人员属于父机构，返回父机构
        		   HJcGljg fgljg = this.gljgDao.selectFjg();
        		   if(fgljg!=null){
        		        return fgljg.getGljgdm();
        		   }else {
        			   return null;
        		      }
        		   }
        	   //2.递归子部门，查询父部门
        	   String fbmdm = recursiveJgFunction(bmdm);
        	   //3.查询该父部门的 上级机构
        	   String gljgmc = this.glbmDao.selectSjjgMc(fbmdm);
        	   
        	   
        	   return gljgmc;
           }
           
	//递归查询父机构
			public String recursiveJgFunction(String bmdm){
					 SQL sql = new SQL("select SJBMDM from h_jc_glbmgx where BMDM = '" + bmdm+"'");
					  String parent =  this.glbmgxDao.findUniqueBySql(sql);	   
							if(parent==null) { 	
				                   return bmdm;
				            }else{
				            	return this.recursiveJgFunction(parent);
					       }
					
				}

    //cookie操作
     protected void saveCookie(String username,String password,String isRemember,String cookieName)
     {
    	 try {
    	//密码加密
        password=AesSecure.encode(password, Constants.AESKEY_REMEMBER);
    	 HttpServletResponse response = ServletActionContext.getResponse();
    	   if(isRemember.equals("1")){
  	    	 //将用户名和密码保存到cookie
  	    	  Cookie cookie;
			
				cookie = new Cookie(cookieName,URLEncoder.encode(username, "UTF-8")+"-"+password);
			
  	    	  cookie.setPath("/");
  	    	  cookie.setMaxAge(60*60*24*7); //cookie 保存7天
  	    	  response.addCookie(cookie);
  	     }else{//只保存用户名
  	    	  //删除cookie
  	    	Cookie cookie = new Cookie(cookieName, null);
  	    	cookie.setPath("/");
  	    	cookie.setMaxAge(0);
  	    	 response.addCookie(cookie);
  	     }
    	} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     }

     public List<String> getCookie(String cookieName){
    try {
    	 HttpServletRequest request = ServletActionContext.getRequest(); 
         List<String> list = new ArrayList<String>();
    	 Cookie[] cookies=request.getCookies(); 
    	 if(cookies!=null){ 
    	  for(int i=0;i<cookies.length;i++){
    	   if(cookies[i].getName().equals(cookieName)){  
    	    String username;
		
				username = java.net.URLDecoder.decode(cookies[i].getValue().split("-")[0],"UTF-8");
			
    	    String password=cookies[i].getValue().split("-")[1];
    	    //解码password
    	    password = AesSecure.decode(password, Constants.AESKEY_REMEMBER);
    	    list.add(username);
    	    list.add(password);
    	   }
    	 }
    	}
    	 return list;
       } catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			} 
     }
     
     
  /*   protected void saveZwOrder(int rybh,String zw){
    	 if(zw!=null&&!zw.trim().equals("")){
    		List<String> zwmclist = new ArrayList<String>();
    		zwmclist=InputFomartConversion.springSplitStringlistTwoSymbol(zw);
    		if(zwmclist!=null&&zwmclist.size()>0){
    			for(int i=0;i<zwmclist.size();i++){
    			int zwbh = this.ryzwgxDao.selectZwbh(zwmclist.get(i));
    			this.ryzwgxDao.save(new HJcRyzwgx(rybh,zwbh));
    			}
    			
    		}
    		 
    	 }
    	 
     }*/

	@Override
	public BaseResult selectZwList(int page,int rows) {
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		if(page==-1||rows==-1){
		   List list = this.rybmDao.selectZwlistOrder( page, rows).getData();
		   result.setList(list);
		   return result;
		}else{
			BaseQueryRecords records = this.rybmDao.selectZwlistOrder( page, rows);
			result.setObj(records);
			return result;
		}
		
	}

	@Override
	public BaseResult savePost(HjcZw zw) {
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		int samename = this.rybmDao.countSameName(null, zw.getZwmc());
		if(samename>0){
			return new BaseResult(1009,"职位名称已经存在，请重新输入！");
		}
		this.rybmDao.savePost(zw.getZwmc());
		return result;
		
	}

	@Override
	public BaseResult updatePost(HjcZw zw) {
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		int samename = this.rybmDao.countSameName(zw.getZwbh(), zw.getZwmc());
		if(samename>0){
			return new BaseResult(1009,"职位名称已经存在，请重新输入！");
		}
		this.rybmDao.updatePost(zw.getZwbh(), zw.getZwmc());
		return result;
		
	}

	@Override
	public BaseResult deletePost(HjcZw zw) {
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		String ryzwgx = this.rybmDao.selectAllZw();
		System.out.println(ryzwgx);
		ryzwgx = ","+ryzwgx+",";
		if(ryzwgx.indexOf(zw.getZwbh().toString())>-1){
			return new BaseResult(1008,"该职务与人员相关联，不能删除");
		}
		String zwmmc = this.rybmDao.selectnameByBh("h_jc_zw", "zwmc", "zwbh", zw.getZwbh());
		result.setObj(zwmmc);
		this.rybmDao.deletePost(zw.getZwbh());
		return result;
		
	}

	@Override
	public BaseResult selectAllAu(String token) {
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		HJcDllp dllp = this.dllpDao.findById(token);
		if(dllp==null){
			return result;
		}
		if(dllp.getRybh()==null){
			return result;
		}
		AuthoritySession auss = this.selectUserQx(dllp.getRybh());
		result.setObj(auss);
		return result;
	}

}