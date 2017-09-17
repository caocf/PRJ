package com.highwaycenter.role.service.impl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.common.action.BaseResult;
import com.common.action.Constants;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.common.service.BaseService;
import com.highwaycenter.bean.Fqxz;
import com.highwaycenter.bean.JsBean;
import com.highwaycenter.bean.JsQxNodes;
import com.highwaycenter.bean.QxNodes;
import com.highwaycenter.bean.TreeNodes;
import com.highwaycenter.role.dao.JbjsbDao;
import com.highwaycenter.role.dao.JbqxDao;
import com.highwaycenter.role.dao.JsqxDao;
import com.highwaycenter.role.dao.QxzDao;
import com.highwaycenter.role.dao.QxzqxdyDao;
import com.highwaycenter.role.model.HJcJbjsb;
import com.highwaycenter.role.model.HJcJbqx;
import com.highwaycenter.role.model.HJcJsqx;
import com.highwaycenter.role.model.HJcQxz;
import com.highwaycenter.role.model.HJcQxzqxdy;
import com.highwaycenter.role.service.QxListService;

@Service("qxlistservice")
public class QxListServiceImpl extends BaseService implements QxListService{

	@Resource(name="jbqxdao")
	private JbqxDao jbqxDao;
	@Resource(name="qxzdao")
	private QxzDao qxzDao;
	@Resource(name="qxzqxdydao")
	private QxzqxdyDao qxzqxdyDao;
	@Resource(name="jsqxdao")
	private JsqxDao jsqxDao;

	/**
	 * 可供权限组选择的基本权限
	 */
	@Override
	public BaseResult findjbqxlist(Integer qxzbh) {
		List<Object> list = new ArrayList<Object>();
		//可供被选择的权限
		List <HJcJbqx>jbqxs = this.jbqxDao.selectQxNoChosed();
		
		//已经被该权限组选中的权限
		if(qxzbh!=null&&!qxzbh.equals("")){
			List<HJcJbqx> hasChooseQx = this.qxzqxdyDao.findByQxz(qxzbh);
			if(hasChooseQx!=null&&hasChooseQx.size()>0){
				Iterator<HJcJbqx> it = hasChooseQx.iterator();
				while(it.hasNext()){
					HJcJbqx qx = it.next();
					qx.setIsQxzChoose(1);
					list.add(qx);
				}
			}
			
			
		}
		list.addAll(jbqxs);
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		result.setList(list);
		return result;
	}

	/**
	 * 权限组列表（基本权限）
	 * int hasjbqx ：1需要带基本权限，0不需要带基本权限，2查询某个角色下的基础权限
	 */
	@Override
	public BaseResult findQxzlist(int hasjbqx,int jsbh) {
		
		//查询某个角色下的基本权限
		List<Object> qxzlist = new ArrayList<Object>();
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
	
		//1.查询权限组列表
		List qxzs = (List<HJcQxz>) this.qxzDao.findAll().getData();
		if(hasjbqx==1){
		
		//2.查询基本权限
		if(qxzs!=null&&qxzs.size()>0){
			Iterator<HJcQxz> qxzIt = qxzs.iterator();
			while(qxzIt.hasNext()){
				HJcQxz qxz = qxzIt.next();
				List<HJcJbqx> jbqxs=this.qxzqxdyDao.findByQxz(qxz.getQxzbh());
				Fqxz fqxz = new Fqxz(qxz.getQxzbh(),qxz.getQxzmc(),jbqxs);
				qxzlist.add(fqxz);
			}
		}
		
		//3.设定一个其他权限，未被权限组包括的所有权限
		List<HJcJbqx> otherqxzs=this.qxzqxdyDao.findOtherQxz();
		Fqxz otherqxz = new Fqxz(-1,"其他权限组");
		otherqxz.setJbqxlist(otherqxzs);
		qxzlist.add(otherqxz);
		result.setList(qxzlist);
 		return result;
		}
		if(hasjbqx==0){
			result.setList(qxzs);
			return result;
		}
		if(hasjbqx==2){
			//查询角色的权限
			List<Integer> jsqxs = this.jsqxDao.selectQxbhListByRole(jsbh);
			HashSet<Integer> jsqxset = new HashSet<Integer>();
			jsqxset.addAll(jsqxs);
			
			//2.查询基本权限
			if(qxzs!=null&&qxzs.size()>0){
				Iterator<HJcQxz> qxzIt = qxzs.iterator();
				while(qxzIt.hasNext()){
					HJcQxz qxz = qxzIt.next();
					List<HJcJbqx> jbqxs=this.qxzqxdyDao.findByQxz(qxz.getQxzbh());
					this.setJsChoose(jbqxs, jsqxset);
					Fqxz fqxz = new Fqxz(qxz.getQxzbh(),qxz.getQxzmc(),jbqxs);
					qxzlist.add(fqxz);
				}
			}
			
			//3.设定一个其他权限，未被权限组包括的所有权限
			List<HJcJbqx> otherqxzs=this.qxzqxdyDao.findOtherQxz();
			this.setJsChoose(otherqxzs, jsqxset);
			Fqxz otherqxz = new Fqxz(-1,"其他权限组");
			otherqxz.setJbqxlist(otherqxzs);
			qxzlist.add(otherqxz);
			result.setList(qxzlist);
			return result;
		}

		return result;

		
	}

	@Override
	public BaseResult zqxListByFqxz(Integer qxzbh) {
		List<HJcJbqx> jbqxs=this.qxzqxdyDao.findByQxz(qxzbh);
		Fqxz fqxz = new Fqxz(qxzbh,null,jbqxs);
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		result.setObj(fqxz);
 		return result;
	}

	@Override
	public BaseResult selectZqxByFqxz(int qxzbh) {
		List list = this.qxzqxdyDao.findqxbhByQxz(qxzbh);
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		result.setList(list);
 		return result;
	}

	

	protected void setJsChoose(List<HJcJbqx> qxs,HashSet<Integer> jsqxs){
		if(qxs!=null&&qxs.size()>0){
			for(int i=0;i<qxs.size();i++){
				HJcJbqx jbqx = qxs.get(i);
				if(jsqxs.contains(jbqx.getQxbh())){
					
					jbqx.setIsJsChoose(1);
				}else{
					jbqx.setIsJsChoose(0);
				}
				qxs.set(i, jbqx);
			}
		}
	}

	@Override
	public BaseResult findQxzlist(int hasjbqx, int page, int rows) {
	
		List<Object> qxzlist = new ArrayList<Object>();
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		//显示权限组的分页信息加基本权限描述
			BaseQueryRecords records = this.qxzDao.findAllPage(page,rows);
			List<HJcQxz>qxzs = (List<HJcQxz>) records.getData();
			//2.查询基本权限
			if(qxzs!=null&&qxzs.size()>0){
				Iterator<HJcQxz> qxzIt = qxzs.iterator();
				while(qxzIt.hasNext()){
					HJcQxz qxz = qxzIt.next();
					
					//查找权限list
					List<HJcJbqx> jbqxlist=this.qxzqxdyDao.findByQxz(qxz.getQxzbh());
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
					qxz.setJbqxdesc(qxmc1);
					
					qxzlist.add(qxz);
				}
			}
			records.setData(qxzlist);
			result.setObj(records);
			return result;
		}
	
	
	

}
