package com.highwaycenter.gljg.service.impl;

//zoumy xiugai26
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.common.action.BaseResult;
import com.common.action.Constants;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.SQL;
import com.common.service.BaseService;
import com.highwaycenter.bean.GlryInfo;
import com.highwaycenter.bean.TreeNodes;
import com.highwaycenter.gljg.dao.GlbmDao;
import com.highwaycenter.gljg.dao.GlbmgxDao;
import com.highwaycenter.gljg.dao.GljgDao;
import com.highwaycenter.gljg.dao.GlryDao;
import com.highwaycenter.gljg.dao.RybmDao;
import com.highwaycenter.gljg.service.GljgListService;
import com.highwaycenter.gljg.model.HJcGlbm;
import com.highwaycenter.gljg.model.HJcGljg;


import com.highwaycenter.gljg.model.HJcRybm;
import com.highwaycenter.role.dao.RyjsDao;


@Service("gljglistservice")
public class GljgListServiceImpl extends BaseService implements GljgListService{
	
	@Resource(name="glbmdao")
	private GlbmDao glbmDao;
	@Resource(name="gljgdao")
	private GljgDao gljgDao;
	@Resource(name="glrydao")
	private GlryDao glryDao;
	@Resource(name="ryjsdao")
	private RyjsDao ryjsDao;
	@Resource(name="glbmgxdao")
	private GlbmgxDao glbmgxDao;
	/*@Resource(name="ryzwgxdao")
	private RyzwgxDao ryzwgxDao;*/
	@Resource(name="rybmdao")
	private RybmDao rybmDao;

	
	@Override
	public BaseResult selectFgljgAll() {
		List gljgs = (List<HJcGljg>) this.gljgDao.findByKey("sjgljgdm", null, 1, -1).getData();
		BaseResult result = new BaseResult();
		result.setList(gljgs);
		return result;
	}
	
	@Override
	public BaseResult selectjgAll(String sfxzgljg) {
		List<Object> others ;
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		 TreeNodes fnode = new TreeNodes();
		//获取唯一的父机构
	    HJcGljg fgljg = this.gljgDao.selectFjg();
	    if(fgljg!=null){
	    	fnode.setFzgxId(-1);
	    	fnode.setNodeDesc(fgljg.getGljgmc());
	    	fnode.setParientNodeId(-1);
	    	fnode.setNodeId(fgljg.getGljgdm());
	    	others =  new ArrayList<Object>();
	    	others.add(fgljg.getJd());
	    	others.add(fgljg.getWd());
	    	fnode.setOtherAttributes(others);
	    }
       // List<HJcGljg> zgljg = (List<HJcGljg>) this.gljgDao.findByKey("sjgljgdm", fgljg.getGljgdm()).getData();
	    List<HJcGljg> zgljg = (List<HJcGljg>) this.gljgDao.findByssgljgandbz(fgljg.getGljgdm(),sfxzgljg).getData();
        List<TreeNodes> zgljglist = new ArrayList<TreeNodes>();
		if(zgljg!=null&&zgljg.size()>0){
		    for(HJcGljg zjg:zgljg){
		   	 TreeNodes Node = new TreeNodes(zjg.getGljgdm(),-1,zjg.getGljgmc(),zjg.getSjgljgdm());
		   	 others =  new ArrayList<Object>();
	    	others.add(fgljg.getJd());
	    	others.add(fgljg.getWd());
	    	Node.setOtherAttributes(others);
		   	zgljglist.add(Node);
        	}
		}
		fnode.setChildrenNodes(zgljglist);
		result.setObj(fnode);
	
		return result;
	}
	
	@Override
	public BaseResult selectZgljgByFjg(String  fjgbh) {
		
	        HJcGljg fjg = this.gljgDao.findById(fjgbh);
			TreeNodes fnode = new TreeNodes(fjg.getGljgdm(),-1,fjg.getGljgmc(),-1);
			diguiFjg(fjgbh,fnode);
		BaseResult result = new BaseResult(1,"返回所有父机构及其子机构成功");
		result.setObj(fnode);
		return result;
	}
	
	@Override
	public BaseResult selectBmByJg(String fjgbh) {
		try{
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		HJcGljg gljg = this.gljgDao.findById(fjgbh);
		List<HJcGlbm> bms = this.glbmDao.selectGlbmBySsjgOrder(gljg.getGljgdm(), "ordercolumn", true);
		
		List<Object> bmtrees = new ArrayList<Object>();
		
		if(bms!=null){
			Iterator it =  bms.iterator();
			while(it.hasNext()){
				HJcGlbm sjbm = (HJcGlbm) it.next();
				TreeNodes sjbmnode = new TreeNodes(sjbm.getBmdm(),sjbm.getBmmc(),-1);
				this.recursiveFunction(sjbm.getBmdm(),sjbmnode);
				bmtrees.add(sjbmnode);
			}
			
		}
		result.setList(bmtrees); //result.list 存放树部门
		result.setObj(gljg);//result.obj 存放机构
		return result;
		}catch(java.lang.Exception e){
			e.printStackTrace();
		}
		
		return null;
		
		
	}

	@Override
	public BaseResult selectJgInfo(String fjgbh) {
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		List<Object> bmmcjh= new ArrayList<Object>();
		try{
		HJcGljg gljg = this.gljgDao.findById(fjgbh) ;
		if(gljg!=null){		
		//根据机构获取所有部门
		List<HJcGlbm> bms =  this.glbmDao.selectGlbmBySsjgOrder(gljg.getGljgdm(), "ordercolumn", true);
	
		if(bms!=null&&bms.size()>0){
			for(int i=0;i<bms.size();i++){
				bmmcjh.add(bms.get(i).getBmmc());
			}
		}
		   result.setObj(gljg);
		    result.setList(bmmcjh);
		}
		
		}catch(java.lang.Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	public void diguiFjg(String fjbdm, TreeNodes fjgNode) {
		
		//递归查询子机构
		 List childList = this.gljgDao.findByKey("sjgljgdm",fjbdm ).getData();	 
		 int size = childList.size();
		 List<TreeNodes> childrenList = new ArrayList<TreeNodes>();
	        if(size>0){
	            for (int i = 0; i < size; i++) {	
	            	HJcGljg xjgljg = (HJcGljg)childList.get(i);
	                System.out.println("下级名称"+xjgljg.getGljgmc());
	                TreeNodes xjjgNode = new TreeNodes(xjgljg.getGljgdm(),-1,xjgljg.getGljgmc(),xjgljg.getSjgljgdm());
	                childrenList.add(xjjgNode);
	            	//  System.out.println("父权限组："+qxzdy.getHJcQxzByFqxzbh().getQxzbh());
	                 this.diguiFjg(xjgljg.getGljgdm(), xjjgNode);//递归查询节点的子节点 
		       }
	            fjgNode.setChildrenNodes(childrenList);
	        }
	}

     //根据部门获取所有人员列表,可以根据拼音首字母做检索
	@Override
	public BaseResult selectRyByBm(String bmdm, String gljgdm,int page, int rows,String xmpyszm,String selectvalue) {
		try{
		BaseQueryRecords baserecords = new BaseQueryRecords();
		BaseResult result= new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		List<String> glbmlist  = new ArrayList<String>();
		List<String> gljglist = new ArrayList<String>();
		BaseQueryRecords records = new BaseQueryRecords();
		int fjgFlag = 0;
		
		
		   //判断传进来的是机构还是部门
			if(bmdm!=null&&!bmdm.trim().equals("")){
				//对部门进行递归检索
				glbmlist.add(bmdm);
				recursiveListFunction(bmdm,glbmlist);
			
			}
			if(gljgdm!=null&&!gljgdm.trim().equals("")){
				//对机构进行检索
				  //1.判断机构是否为父机构，如果是父机构，直接遍历它第一层子机构
				  HJcGljg sjgljg = this.gljgDao.findById(gljgdm) ;
				  if(sjgljg!=null){
					  List<String> fbmjh = new ArrayList<String>();
				/*	  if(sjgljg.getSjgljgdm()==null||sjgljg.getSjgljgdm()==""){
						  //是父机构
                          //检索没有上级部门的人员list
						  fjgFlag = 1;
						  
						  String sql = "select b.bmdm from h_jc_glbm as b where b.ssgljgdm in "+
						  "(select a.gljgdm from h_jc_gljg as a where a.gljgdm ='"+sjgljg.getGljgdm()+
						  "' or  a.sjgljgdm = '"+sjgljg.getGljgdm()+"')";
						  	
						  fbmjh = (List<String>) this.gljgDao.findBySql(new SQL(sql)).getData();
						  glbmlist  = new ArrayList<String>();
						  glbmlist.addAll(fbmjh);	  
						  
					  }else{*/
						  //2.不是父机构，则直接检索它的部门
						  String sql = "select bmdm from h_jc_glbm  where ssgljgdm = '"+ sjgljg.getGljgdm()+"'";
						  fbmjh =  (List<String>) this.glbmDao.findBySql(new SQL(sql)).getData();
						  glbmlist  = new ArrayList<String>();
						  glbmlist.addAll(fbmjh);		  
					 /* }*/
					  //3.递归父部门
					  if(fbmjh!=null&&fbmjh.size()>0){
					    Iterator<String> it = fbmjh.iterator();
					    while(it.hasNext()){
					       recursiveListFunction(it.next(),glbmlist);
					    }
					  }
					  
				  }	
			}
	   String sqltest = null;
		//人员检索 1.拼接部门 in的
		if(glbmlist!=null&&glbmlist.size()>0)	{
		   /*   String[] str = glbmlist.toArray(new String[0]);*/
			//将list拼接成sql语句的in
			StringBuffer qxzString = new StringBuffer(); 
			
				Iterator<String> it = glbmlist.iterator();
				while(it.hasNext()) { 	
				     qxzString.append("'").append(it.next().toString()).append("',"); 
			    } 
				 System.out.println(qxzString+"长度"+qxzString.toString().length());
			
				 sqltest = qxzString.substring(0, qxzString.length()-1);   
			
		}
		 records = this.glryDao.selectGlryList(page, rows, sqltest, xmpyszm,selectvalue,fjgFlag);
		 
		 //查询人员的部门与职位信息
		 List<GlryInfo> glryinfolist = (List<GlryInfo>) records.getData();
		 if(glryinfolist!=null&&glryinfolist.size()>0){
			 for(int i=0;i<glryinfolist.size();i++){
				
				 GlryInfo info = glryinfolist.get(i);
				 List<HJcRybm> rybmlist = this.rybmDao.selectRybmList(info.getRybh());
				   for(int j=0;j<rybmlist.size();j++){
					   HJcRybm rybm = rybmlist.get(j);
					   String zwbhs = rybm.getZwbh();
					   if(zwbhs!=null&&!"".equals(zwbhs)){
						   String zwmcs  = this.rybmDao.selectZwstr(zwbhs);
						   rybm.setZwmc(zwmcs);
						   rybmlist.set(j, rybm);
					   }
				   }
				   info.setRybmlist(rybmlist);
				   glryinfolist.set(i, info);
				 /*List<String> bmlist = this.rybmDao.selectBmByry(info.getRybh());
				 String bms = "";
				 if(bmlist!=null&&bmlist.size()>0){
					 for(int j=0;j<bmlist.size();j++){
						 bms+=bmlist.get(j)+",";
					 }
					 bms = bms.substring(0,bms.length()-1);
					 info.setBmmc(bms);
					 glryinfolist.set(i, info);
				 }*/
				 
				 
			 }		 
		 }
		 records.setData(glryinfolist);
		 result.setObj(records);

		 
		 //第一次运行的时候将用户的职位与职位排序表比对，插入关系数据
	/*	 List<GlryInfo> list = (List<GlryInfo>) records.getData();
		 for(int i=0;i<list.size();i++){
			 //查询人员职位
			 int rybh = list.get(i).getRybh();
			 String zwmc = list.get(i).getZw();
			 String zwbhs = null;
			 String bgsdh = null;
			 if(zwmc!=null&&!zwmc.equals("")){
				 zwmc="'"+zwmc+"'";
				 System.out.println(zwmc);
				 if(zwmc.indexOf(",")>-1){
					 zwmc = zwmc.replace(",", "','");
				 }
				 if(zwmc.indexOf("、")>-1){
					zwmc = zwmc.replace("、", "','");
					
				 } if(zwmc.indexOf("兼")>-1){
					 zwmc = zwmc.replace("兼", "','");
				 }
				 System.out.println("shhhh"+zwmc);
		    	 zwbhs = this.rybmDao.selectzwbhs(zwmc);
		    	 System.out.println(zwbhs);
				
			 }
			 if(list.get(i).getBgsdh()!=null&&!"".equals(list.get(i).getBgsdh())){
				 bgsdh = list.get(i).getBgsdh();
			 }
			 if(bgsdh!=null||zwbhs!=null){
			 this.rybmDao.updateRybmzw(rybh, bgsdh,zwbhs);
			 }
		 }*/
		 //第一次运行的时候将用户的部门插入到部门人员表
		 
		 
		return result;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	//递归进行部门树的构建
	public void recursiveFunction(String sjbmdm, TreeNodes sjbmNodes){
		
		//递归查询子部门
		List<TreeNodes> childrenList = new ArrayList<TreeNodes>();
		List<TreeNodes> childList = this.glbmgxDao.findxjbmNode(sjbmdm);
		
	        if(childList!=null){
	           Iterator it = childList.iterator();
	           while(it.hasNext()){
	        	   TreeNodes xjbm = (TreeNodes) it.next();
	                childrenList.add(xjbm);
	            	
	                 this.recursiveFunction(xjbm.getNodeId().toString(),xjbm );//递归查询节点的子节点 
		       }
	           sjbmNodes.setChildrenNodes(childrenList);
	        }
	}
	
	//递归返回部门list
		public void recursiveListFunction(String sjbmdm,List<String> zbmlist ){
				 SQL sql = new SQL("select BMDM from h_jc_glbmgx where SJBMDM = '" + sjbmdm+"'");
				  List<String> childList =  (List<String>) this.glbmgxDao.findBySql(sql).getData();
					
			        if(childList!=null){
			        	zbmlist.addAll(childList);
			        	Iterator<String> it = childList.iterator();   
						while(it.hasNext()) { 	
			                    this.recursiveListFunction(it.next(), zbmlist);//递归查询节点的子节点 
			            	}
				       }
				
			}

		@Override
		public BaseResult selectOrderList(String bmdm,String jgdm) {
			BaseResult result= new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
			List list = new ArrayList();
			if(bmdm!=null&&!bmdm.equals("")){
			  list = this.glbmgxDao.selectbmlist(bmdm);
			}
			if(jgdm!=null&&!jgdm.equals("")){
			  list = this.glbmDao.selectGlbmBySsjgOrder(jgdm, "ordercolumn", true);
			}
			result.setList(list);
			return result;
		}

		@Override
		public BaseResult updateOrderList(List<String> orderlist) {
			BaseResult result= new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
			if(orderlist!=null&&orderlist.size()>0){
				for(int i=0;i<orderlist.size();i++){
					int order = orderlist.size()-i;
					this.glbmDao.updateOrderColumn(orderlist.get(i), order);
				}				
			}
			return result;
		}

		@Override
		public BaseResult selectBmXq(String bmdm) {
			BaseResult result= new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
			HJcGlbm glbm = this.glbmDao.findById(bmdm);
			result.setObj(glbm);
			return result;
		}

		@Override
		public BaseResult selectGljgList() {
			List gljgs = (List<HJcGljg>) this.gljgDao.listall();
			BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
			result.setList(gljgs);
			return result;
		}

		@Override
		public BaseResult selectRyOrderList(String bmdm) {
			BaseResult result= new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
			List list = new ArrayList();
			if(bmdm!=null&&!bmdm.equals("")){
			  list = this.rybmDao.selectGlryBySsjgOrder(bmdm, "ryordercolumn", true);
			}
			
			result.setList(list);
			return result;
		}

		@Override
		public BaseResult updateRyOrderList(List<String> orderlist) {
			BaseResult result= new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
			if(orderlist!=null&&orderlist.size()>0){
				for(int i=0;i<orderlist.size();i++){
					int order = orderlist.size()-i;
					this.rybmDao.updateRybmorder(Integer.valueOf(orderlist.get(i)), order);
				}				
			}
			return result;
		}

		@Override
		public BaseResult selectXzcfGljg() {
			List gljgs = (List<HJcGljg>) this.gljgDao.xzcflistall();
			if(gljgs!=null&&gljgs.size()>0){
			   for(int i=0;i<gljgs.size();i++){
				   HJcGljg jg = (HJcGljg) gljgs.get(i);
				  
				   if(jg.getOrgname()==null||jg.getOrgname().equals("")){
					   jg.setOrgname(jg.getGljgmc());
				   }
				   gljgs.set(i, jg);
				   if(jg.getOrgcode()==null||jg.getOrgcode().equals("")){
					   gljgs.remove(i);
				   }
			   }
			}
			BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
			result.setList(gljgs);
			return result;
		}
        
		
	
}
