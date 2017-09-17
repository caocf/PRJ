package com.highwaycenter.role.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.common.action.BaseResult;
import com.common.action.Constants;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.SQL;
import com.common.service.BaseService;
import com.highwaycenter.bean.JsBean;
import com.highwaycenter.role.service.JsqxService;
import com.highwaycenter.role.dao.JbjsbDao;
import com.highwaycenter.role.dao.JbqxDao;
import com.highwaycenter.role.dao.JsqxDao;
import com.highwaycenter.role.dao.QxzDao;
import com.highwaycenter.role.dao.QxzqxdyDao;
import com.highwaycenter.role.dao.RyjsDao;
import com.highwaycenter.role.model.HJcJbjsb;
import com.highwaycenter.role.model.HJcJbqx;
import com.highwaycenter.role.model.HJcJsqx;
import com.highwaycenter.bean.AuthoritySession;
import com.highwaycenter.common.SessionMap;
@Service("jsqxservice")
public class JsqxServiceImpl extends BaseService implements JsqxService{
	
	@Resource(name="jbjsbdao")
	private JbjsbDao jbjsbDao;
	@Resource(name="jsqxdao")
	private JsqxDao jsqxDao;

	@Resource(name="jbqxdao")
	private JbqxDao jbqxDao;
	@Resource(name="qxzdao")
	private QxzDao qxzDao;

	@Resource(name="qxzqxdydao")
	private QxzqxdyDao qxzqxdyDao;
	@Resource(name="ryjsdao")
	private RyjsDao ryjsDao;

	/*版本2角色接口1：新增角色
	 * 
	 */
	@Override
	public BaseResult jsSave(HJcJbjsb js,List<Integer> qxjh) {
		try{
		//1.验证是否有同名
		int countName = this.jbjsbDao.countSameName(js.getJsmc(),null);
		if(countName>0)
			return new BaseResult(Constants.JSMC_RENAME_CODE,Constants.JSMC_RENAME_INFO);
		//2.保存角色
	    Integer jsbh = this.jbjsbDao.saveAndReturn(js);
 
	    //3.保存角色权限关系
	    this.saveJsQx(jsbh, qxjh);
		/*	 if(qxjh!=null&&qxjh.size()>0){
			 Iterator<Integer> it = qxjh.iterator();
			 while(it.hasNext()){
				 Integer choseqx = it.next();
				 //判断该基本权限编号是否存在,存在就保存
				 HJcJbqx qx = this.jbqxDao.findById(choseqx);
				 if(qx!=null){
					HJcJsqx jsqx = new HJcJsqx(qx,new HJcJbjsb(jsbh));
					this.jsqxDao.save(jsqx);
				 }
				 }
			   }*/
	    BaseResult result =new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
	    result.setObj(jsbh);   //返回角色编号
		return result;
		}catch(java.lang.Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public void saveJsQx(Integer jsbh,List<Integer> qxjh){
		 //3.保存角色权限关系
		 if(qxjh!=null&&qxjh.size()>0){
		 Iterator<Integer> it = qxjh.iterator();
		 while(it.hasNext()){
			 Integer choseqx = it.next();
			 //判断该基本权限编号是否存在,存在就保存
			 HJcJbqx qx = this.jbqxDao.findById(choseqx);
			 if(qx!=null){
				HJcJsqx jsqx = new HJcJsqx(qx,new HJcJbjsb(jsbh));
				this.jsqxDao.save(jsqx);
			 }
			 }
		   }
	}
	/*版本2角色接口2：编辑角色
	 * 
	 */
	@Override
	public BaseResult jsUpdate(HJcJbjsb js,List<Integer> qxjh) {
		//检查是否为超级管理员
		if(js.getJsbh()==Constants.SUPERMANAGER_JSBH){
			return new BaseResult(Constants.SUPERMANAGER_JS_UPDATE_CODE,Constants.SUPERMANAGER_JS_UPDATE_INFO);
		}
		//验证是否有同名
		int countName = this.jbjsbDao.countSameName(js.getJsmc(), js.getJsbh());
		if(countName>0){
			return new BaseResult(Constants.JSMC_RENAME_CODE,Constants.JSMC_RENAME_INFO);
		}
		//更新角色
		this.jbjsbDao.update(js);
		//删除角色的权限组权限关系
		this.jsqxDao.deleteByJs(js.getJsbh());
		//保存新的 角色权限组权限关系
		this.saveJsQx(js.getJsbh(), qxjh);
		//批量修改用户的权限session内存
				List<Integer> rybhlist = this.ryjsDao.findRylistByJs(js.getJsbh()) ;
				List<Integer> exitrybh = SessionMap.selectExitRybh();
				
				List<AuthoritySession> aulist = new ArrayList<AuthoritySession>();
				if(rybhlist!=null&&rybhlist.size()>0){
					for(int i=0;i<rybhlist.size();i++){
						if(exitrybh.contains(rybhlist)){
				          AuthoritySession ausession = this.getUserQx(rybhlist.get(i));
				          aulist.add(ausession);
						}
					}
				}
		SessionMap.updateAuSessionList(aulist);
		return new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
	}

	/*版本2角色接口3：删除角色
	 * 
	 */
	@Override
	public BaseResult jsDelete(int jsbh) {
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		//检查是否为超级管理员
				if(jsbh==Constants.SUPERMANAGER_JSBH){
					return new BaseResult(Constants.SUPERMANAGER_JS_UPDATE_CODE,Constants.SUPERMANAGER_JS_UPDATE_INFO);
				}
		//判断是否有管理人员对应这个角色
		int ryjsCount = this.ryjsDao.selectCountByJsbh(jsbh);
		if(ryjsCount>0){
			return new BaseResult(Constants.RY_EXIT_ERROR_CODE,Constants.RY_EXIT_ERROR_INFO);
		}		
		// 删除该角色在角色权限关系表的全部记录
		//h_jc_jsqx 基础_角色权限表
		//获得角色名字
		String jsmc = this.jbjsbDao.selectnameByBh("h_jc_jbjsb", "jsmc", "jsbh", jsbh);
		result.setObj(jsmc);
		this.jsqxDao.deleteByJs(jsbh);
		this.jbjsbDao.delete(new HJcJbjsb(jsbh));
		return result;
		
	}

	/*版本2角色接口4：角色列表
	 * 
	 */
	@Override
	public BaseResult jsListAll(int page, int rows) {
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		BaseQueryRecords records = this.jbjsbDao.jsListAll(page, rows);
		List<Object> jsbeans = new ArrayList<Object>();
		
		if(records.getData()!=null){
		@SuppressWarnings("unchecked")
		List<HJcJbjsb> list = (List<HJcJbjsb>) records.getData();
		
		//遍历角色
		if(list!=null&&list.size()>0){
			
			Iterator<HJcJbjsb> it= list.iterator();
			while(it.hasNext()){
				HJcJbjsb js = it.next();
				int jsbh = js.getJsbh();
				//查找权限list
				List<HJcJbqx> jbqxlist = this.jsqxDao.findQxmcsByJs(jsbh);
				String qxmc1 = null;
				StringBuffer qxmc = new StringBuffer();
				if(jbqxlist!=null&&jbqxlist.size()>0){
					
					Iterator<HJcJbqx> qxit = jbqxlist.iterator();
					while(qxit.hasNext()){
						qxmc.append(qxit.next().getQxmc()).append("，");
					}
					System.out.println("长度"+qxmc.lastIndexOf("，"));
					qxmc1 = qxmc.substring(0, qxmc.lastIndexOf("，"));
				}
				JsBean jsbean = new JsBean();
				jsbean.setJsbh(jsbh);
				jsbean.setJsmc(js.getJsmc());
				jsbean.setQxlist(jbqxlist);
				jsbean.setQxs(qxmc1);
				jsbeans.add(jsbean);
			}
		
		}
		}
		records.setData(jsbeans);
		result.setObj(records);
		return result;
	}

	
	/*人员接口1：获取角色列表
	 * 
	 * 
	 */
	@Override
	public BaseResult jslist() {
		List list =  this.jbjsbDao.jsListAll(-1, -1).getData();
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		result.setList(list);
		return result;
		
	}

	@Override
	public BaseResult jsAllDelete(String jss) {
		// TODO Auto-generated method stub
		return null;
	}
	 /**
     * 获取用户所有基本权限
     */
	@SuppressWarnings("unchecked")

	public AuthoritySession getUserQx(Integer rybh) {
		StringBuffer qxs = new StringBuffer("");
		String qxsStr;
		List<Integer> userqxs = new ArrayList<Integer>();
		//获取人员的权限列表
		SQL sql = new SQL("select qxbh from h_jc_ryjs as a , h_jc_jsqx "+
		          "as b where a.jsbh = b.jsbh and a.rybh = "+rybh);
         userqxs = (List<Integer>) this.jsqxDao.findBySql(sql).getData();
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
	      Integer jsbh = this.ryjsDao.findjsbhByRy(rybh);
	      AuthoritySession userAu = new AuthoritySession(rybh,jsbh,"");	
	      return userAu;
     }
	 }
	
	
	/**
	 * 线程修改内存
	 */
/*	public class updateQxThread extends Thread {
		private 
	}*/


}
