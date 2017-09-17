package com.highwaycenter.common.service.impl;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Service;

import com.common.action.BaseResult;
import com.common.action.Constants;
import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;
import com.highwaycenter.bzbx.dao.BzbxDao;
import com.highwaycenter.bzbx.model.HBzbxBzbx;
import com.highwaycenter.common.dao.SearchDao;
import com.highwaycenter.common.service.SerchService;
import com.highwaycenter.lxjbxx.dao.LxjbxxDao;
import com.highwaycenter.qljbxx.dao.QljbxxDao;

@Service("searchservice")
public class SearchServiceImpl extends BaseService implements SerchService{
	
	@Resource(name="searchdao")
	private SearchDao searchDao;
	@Resource(name="lxjbxxdao")
	private LxjbxxDao lxjbxxDao;
	@Resource(name="qljbxxdao")
	private QljbxxDao qljbxxDao;
	@Resource(name="bzbxdao")
	private BzbxDao bzbxDao;
	/**
	 * 桥梁搜查字段属性
	 */
	private static String[] QL_PARAM_NAME= new String[]{  //去掉属性中的标志编码
		"路线代码","路线简称","行政等级","桥梁编号","桥梁名称","收费性质","跨越地物名称","技术状况评定",
		"监管单位名称","桥墩类型","墩台防撞设施类型",
		"设计荷载等级","抗震等级","通航等级","设计单位名称","建设单位名称",
		"施工单位名称","监理单位名称","修建年度","管理单位名称"
		};

	private static String[]  QL_PARAM_VALUE  = new String[]{
		"lxdm","lxjc","xzdj","qlbh","qlmc","sfxz","kydwmc","jszkpd","jgdwmc",
		"qdlx","dtfzsslx","sjhzdj","kzdj","thdj","sjdwmc","jsdwmc",
		"sgdwmc","jldwmc","xjnd","gldwmc"
		};

	
	/**
	 * 路线搜查字段属性
	 */
	private static String[] LX_PARAM_NAME= new String[]{ //去掉属性中的标志编码
		"路线代码","路段编号","路线简称","行政等级","路段起点名称","路段止点名称","技术等级","面层类型","车道分类",
		"是否断头路路段","是否收费路段","是否一幅高速","管理单位名称","管养单位名称","修建年度"
		
	};
	
	private static String[]  LX_PARAM_VALUE  = new String[]{
		"lxdm","ldbh","lxjc","xzdj","ldqdmc","ldzdmc","jsdj","mclx","cdfl",
		"sfdtlld","sfsfld","sfyfgs","gldwmc","gydwmc","xjnd"};
	
	/**
	 *标志标线搜查字段属性
	 */
	private static String[] BZBX_PARAM_NAME = new String[]{ //去掉属性中的服务区编号
		"线路名称","行政等级","标志标线类型","标志位置","版面尺寸","支撑形式及规格","制作安装单位","管理单位"
	};
	private static String[]  BZBX_PARAM_VALUE  = new String[]{
		"xlmc","xzdj","bzbxlxbh","bzwz","bmcc","zcxsjgg","zzazdw","gldw"};
	
	
	/**
	 * 高级搜索字段查询
	 * @param type   类型  1、路网  2、视频 3.1、服务区 3.2、收费站 4、桥梁 5、标志标线 6、公路站 8、交通量
	 */
	@Override
	public BaseResult advancedSearch(String type) {
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		Map<String,Object> map = new HashMap<String,Object>();
		 if(type.equals("4")){ //桥梁信息
			      /*map =this.creatMap(HJcQljbxx.class, 2,-1, this.QL_PARAM_NAME);
			      map.remove("xzqhdm");
			      map.remove("xzqh");*/
		         map = this.creatMapByAdd(QL_PARAM_NAME, QL_PARAM_VALUE);
		 }else if(type.equals("1")){//路网信息
		        /*  map =this.creatMap(HJcLxjbxx.class, 2, -1,this.LX_PARAM_NAME);
		          for(int i=0;i<lxremoves.length;i++){
		        	  map.remove(lxremoves[i]);
		          }*/
			 map = this.creatMapByAdd(LX_PARAM_NAME, LX_PARAM_VALUE);
		  //}else if(type.equals("3.1")){//服务区
		          //map =this.creatMap(HFwFwq.class,2,17,this.FWQ_PARAM_NAME);
		 // }else if(type.equals("3.2")){//收费站
		         //   map =this.creatMap(HFwSfz.class,2,17,this.SFZ_PARAM_NAME);
		         //   map.remove("sfzlxmc");
		  }else if(type.equals("5")){//标志标线 
		        	/*map =this.creatMap(HBzbxBzbx.class,2,17,this.BZBX_PARAM_NAME);
		        	map.remove("xzqhdm");*/
			  map= this.creatMapByAdd(BZBX_PARAM_NAME, BZBX_PARAM_VALUE);
		  //}else if(type.equals("6")){//公路站信息（基本信息+详情）
		        //	Map<String,Object> mapJbxx = new HashMap<String,Object>();
		        //	Map<String,Object> mapXxxx = new HashMap<String,Object>();
		        //	mapJbxx =this.creatMap(HGlzGlzJbxx.class,2,13,this.GLZJBXX_PARAM_NAME);
		        //	mapXxxx =this.creatMap(HGlzGlzXxxx.class,3,-1,this.GLZXXXX_PARAM_NAME);
		        //	map.putAll(mapJbxx);
		        //	map.putAll(mapXxxx);
		        	//map.remove("xzqhdm");
		  //}else if(type.equals("8")){//交调站基本信息
		        //	map =this.creatMap(HJdJxdczxx.class,2,42,Constants.JD_PARAM_NAME);	
		  }else{
		        	result =  new BaseResult(Constants.SUCCESS_CODE,"参数错误，查询失败");  
		        	return result;
		        }
		 result.setMap(map);
		 return result;
		 
	}

	
	/**
	 * 查询不同对象的字段下拉列表
	 */
	@Override
	public BaseResult searchSelect(String type, String key) {
		BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		Map<String,Object> map = new HashMap<String,Object>();
		List<Object> list = new ArrayList<Object>();
		 if(type.equals("4")){ //桥梁信息
			 if(key.equals("xzdj")){
					list = this.searchDao.selectKey("h_jc_xzdj", "djszm", "xzdjmc");
			 }else{
		       list = this.searchDao.selectSingleKey("h_jc_qljbxx", key);
		      
			 }
			 result.setList(list);
		 }else if(type.equals("1")){//路网信息
			 if(key.equals("xzdj")){
					list = this.searchDao.selectKey("h_jc_xzdj", "djszm", "xzdjmc");
			 }else{
			 list = this.searchDao.selectSingleKey("h_jc_lxjbxx", key);
			 }
			 result.setList(list);
		 /*       
		  }else if(type.equals("3.1")){//服务区
			  
		      
		  }else if(type.equals("3.2")){//收费站
		        */  
		  }else if(type.equals("5")){//标志标线 
			  if(key.equals("xzdj")){
					list = this.searchDao.selectKey("h_jc_xzdj", "djszm", "xzdjmc");
			 }else if(key.equals("bzbxlxbh")){
		        	list = this.searchDao.selectKey("h_jc_bzbxlx", "bzbxlxbh", "bzbxlxmc");
		        }else{
		       	     list = this.searchDao.selectSingleKey("h_bzbx_bzbx", key);
				   
		        }
		        result.setList(list);
	
		 }else{
			 return null;
		 }
		return result;
	}
	
	
	/**
	 * 通过反射获取类的属性名与中文名字对应，返回一个map
	 * @param clazz
	 * @param startIndex
	 * @param names
	 * @return
	 */
	@SuppressWarnings("unused")
	private<T> Map<String,Object> creatMap(Class<T> clazz,int startIndex,int endIndex,String[] names){
    try {
    	   
    	  Map<String,Object> map = new HashMap<String,Object>();
		  Field[] fields =clazz.getDeclaredFields();  
		  System.out.println(fields[0].getName());
		  Object obj = clazz.newInstance();	
		  if(endIndex<0||endIndex>fields.length){
			  endIndex = fields.length;
		  }
		  
		  for (int i = startIndex; i < endIndex; i++) {
			  int offset = startIndex;
			  String paramName = names[i-offset];
	          String paramValue =fields[i].getName();
	          map.put(paramValue, paramName);
	        }
		  return map;
		  } catch (Exception e) {
				e.printStackTrace();
			} 
    
		return null;
	}

	/**
	 * 通过反射获取类的属性名与中文名字对应，返回一个map
	 * @param clazz
	 * @param startIndex
	 * @param names
	 * @return
	 */
	@SuppressWarnings("unused")
	private  Map<String,Object> creatMapByAdd(String[] names,String[] values){
    try {
   	   
    	  Map<String,Object> map = new HashMap<String,Object>();
		  for (int i = 0; i < names.length; i++) {
			
	          map.put(values[i], names[i]);
	        }
		  return map;
		  } catch (Exception e) {
				e.printStackTrace();
			} 
    
		return null;
	}


	/**
	 * 根据条件进行高级查询
	 */
	@Override
	public BaseResult selectListByCondition(String type, String condition,int page,int rows) {
		 BaseResult result = new BaseResult(Constants.SUCCESS_CODE,Constants.SUCCESS_INFO);
		if(type.equals("1")){//路网
		String sqlcondition = this.creatSqlCondition(type, condition);
		 BaseQueryRecords records = this.lxjbxxDao.selectGkListByCondition(page, rows, sqlcondition);
		 result.setObj(records);
			return result;
		}else if(type.equals("4")){//桥梁
			String sqlcondition = this.creatSqlCondition(type, condition);
			
			BaseQueryRecords records = this.qljbxxDao.selectGkListByCondition(page, rows, sqlcondition);
			 result.setObj(records);
				return result;
			
		}else if(type.equals("5")){//标志标线
			String sqlcondition = this.creatSqlCondition(type, condition);
			BaseQueryRecords records = this.bzbxDao.selectGklisCondition(sqlcondition, page, rows);
			records = this.changeBzbxList(records);
			 result.setObj(records);
				return result;
		}
		return result;
	}
	
	

	private String creatSql(String type,String condition){
		String hql = null;
		if(type.equals("1")){//路网
			hql = "from HJcLxjbxx where 1=1  ";
		}else if(type.equals("4")){//桥梁
			hql = "from HJcQljbxx where 1=1 ";
		}else if(type.equals("5")){//标志标线
			hql = "from HBzbxBzbx where 1=1 ";
		}else{
			return hql;
		}
		
		//分割
		String[] sqlcondition = condition.split(";");
		for(int i=0;i<sqlcondition.length;i++){//格式  
			String[] keyname = sqlcondition[i].split(",");
			if(keyname.length==3){
				if(!keyname[0].equals("null")){
					if(keyname[0].equals("bzwz")){
						//模糊查询
						if(keyname[2].trim().equals("0")){
						   hql+=" and (bzwzl like '%"+keyname[1].trim()+"%' or bzwzm like '%"+keyname[1].trim()+"%' or bzwzr like '%"+keyname[1].trim()+"%') ";
						}else{
						 //精确查询
							 hql+=" and (bzwzl = '"+keyname[1].trim()+"' or bzwzm = '"+keyname[1].trim()+"' or bzwzr = '"+keyname[1].trim()+"') ";
						}
						 
					}else{
					   hql+=" and "+keyname[0].trim();
					   if(keyname[2].trim().equals("0")){
						  hql+=" like '%"+keyname[1].trim()+"%' ";
					  }else{
						hql+=" = '"+keyname[1].trim()+"' ";
					  }
					}
				}
		
		}
	}
		
		System.out.println(hql);
		return hql;
  }

	
	private BaseQueryRecords changeBzbxList(BaseQueryRecords bzbxrecords){
		String tuanpath = null;
		
		List<HBzbxBzbx> list = (List<HBzbxBzbx>) bzbxrecords.getData();
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
					bzbx.setAzsj(time2);
					}
				if(time1.indexOf("、")!=-1){
					
					try{
					d=sdf3.parse(time1);
					}catch(Exception e){
						e.printStackTrace();
					}
					String time2 = sdf2.format(d);
					bzbx.setAzsj(time2);
					}
				list.set(i, bzbx);
			 }
			}
		}
		bzbxrecords.setData(list);
		return bzbxrecords;
		
	}

	
	
	private String creatSqlCondition(String type,String condition){
		String sql = "";
		String[] sqlcondition = condition.split(";");
		for(int i=0;i<sqlcondition.length;i++){//格式  
			String[] keyname = sqlcondition[i].split(",");
			if(keyname.length==3){
				if(!keyname[0].equals("null")){
					if(keyname[0].equals("xzdj")){
						 //精确查询
						if(type.equals("5")){
							sql+=" and left(a.xlmc,1) = '"+keyname[1]+"'";
						}else{
							 sql+=" and left(a.lxdm,1) = '"+keyname[1]+"'";
						}
					}else if(keyname[0].equals("bzwz")){
						//模糊查询
						if(keyname[2].trim().equals("0")){
							sql+=" and (bzwzl like '%"+keyname[1].trim()+"%' or bzwzm like '%"+keyname[1].trim()+"%' or bzwzr like '%"+keyname[1].trim()+"%') ";
						}else{
						 //精确查询
							sql+=" and (bzwzl = '"+keyname[1].trim()+"' or bzwzm = '"+keyname[1].trim()+"' or bzwzr = '"+keyname[1].trim()+"') ";
						}
						 
					}else{
					   sql+=" and a."+keyname[0].trim();
					   if(keyname[2].trim().equals("0")){
						  sql+=" like '%"+keyname[1].trim()+"%' ";
					  }else{
						sql+=" = '"+keyname[1].trim()+"' ";
					  }
					}
					}
				}
		
		}
		if(sql.equals("")){
			return null;
		}else{
			System.out.println(sql);
			return sql;
		}
		
	}
   /**
    * 定位数据所在的位置
    * type  类型  1、路网  2、视频 3.1、服务区 3.2、收费站 4、桥梁 5、标志标线 6、公路站 8、交通量
    */
	@Override
	public BaseResult selectItemLocation(String type, Object id,int rows) {
		if(type.equals("1")){//路网
			
		}
		
		
		
		return null;
	}

}
