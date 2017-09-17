package com.highwaycenter.glz.service.impl;
//46update
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.common.action.BaseResult;
import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;
import com.highwaycenter.bean.GlxcldBean;
import com.highwaycenter.bean.HdjcxjcBean;
import com.highwaycenter.bean.QljcxjcBean;
import com.highwaycenter.bean.XcglgkBean;
import com.highwaycenter.bean.XzqhBean;
import com.highwaycenter.bean.selectListBean;
import com.highwaycenter.glz.dao.DbYscqktjxxDao;
import com.highwaycenter.glz.dao.DwryDao;
import com.highwaycenter.glz.dao.GlzHdgkDao;
import com.highwaycenter.glz.dao.GlzQlgkDao;
import com.highwaycenter.glz.dao.GlzjbxxDao;
import com.highwaycenter.glz.dao.GlzxxxxDao;
import com.highwaycenter.glz.dao.GyldDao;
import com.highwaycenter.glz.dao.XcGlxcjlDao;
import com.highwaycenter.glz.dao.XcHdjcxjcDao;
import com.highwaycenter.glz.dao.XcQljcxjcDao;
import com.highwaycenter.glz.dao.impl.XcGlxcjlDaoImpl;
import com.highwaycenter.glz.model.HGlzDbYscqktjxx;
import com.highwaycenter.glz.model.HGlzGlzGyld;
import com.highwaycenter.glz.model.HGlzGlzJbxx;
import com.highwaycenter.glz.model.HGlzGlzXxxx;
import com.highwaycenter.glz.model.HGlzHdgk;
import com.highwaycenter.glz.model.HGlzJxDwry;
import com.highwaycenter.glz.model.HGlzQlgk;
import com.highwaycenter.glz.model.HGlzXcGlxcjl;
import com.highwaycenter.glz.service.GlzxxService;

@Service("glzxxservice")
public class GlzxxServiceImpl extends BaseService implements GlzxxService{

	@Resource(name="glzxxxxdao")
	private GlzxxxxDao glzxxxDao;	
	@Resource(name="glzjbxxdao")
	private GlzjbxxDao glzjbxxDao;	
	@Resource(name="gylddao")
	private GyldDao gyldDao;
	@Resource(name="dwrydao")
	private DwryDao dwryDao;
	@Resource(name="glzqlgkdao")
	private GlzQlgkDao glzqlgkDao;
	@Resource(name="glzhdgkdao")
	private GlzHdgkDao glzhdgkDao;
	@Resource(name="dbyscqktjxxdao")
	private DbYscqktjxxDao yscqktjxxDao;
	@Resource(name="xcglxcjldao")
	private XcGlxcjlDao glxcjlDao;
	@Resource(name="qljcxjcdao")
	private XcQljcxjcDao qljcxjcDao;
	@Resource(name="hdjcxjcdao")
	private XcHdjcxjcDao hdjcxjcDao;
	
	@Override
	public BaseResult selectGlzXq(String id) {
		//公路站详情
		HGlzGlzXxxx glzxq = this.glzxxxDao.selectGlzqx(id);
		//公路站概况
		HGlzGlzJbxx glzjbxx = this.glzjbxxDao.selectUnique(glzxq.getStationId());
		List<Object> glzxx = new ArrayList<Object>();
		
		BaseResult result = new BaseResult(1,"获取公路站详情成功");
		String gljgmc = this.glzjbxxDao.selectGljgmc(glzxq.getStationId());
		glzjbxx.setGljgmc(gljgmc);
		glzxx.add(glzjbxx);
		glzxx.add(glzxq);
		result.setList(glzxx);
		return result;
	}
	
	@Override
	public BaseResult selectGlzgklist(Integer page, Integer rows,
			String gljgdm, String selectvalue,String selectId) {
		try{
		BaseResult result = new BaseResult(1,"获取公路站概况列表成功");
		System.out.println("service层+++++++++++++++++"+selectvalue);
        BaseQueryRecords records = this.glzxxxDao.selectGlzgkListNew(page, rows, gljgdm, selectvalue,selectId);
        result.setObj(records);
		return result;
		}catch (java.lang.Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BaseResult selectProperteList(String key1, String key2) {
		List<Object []> list = this.glzxxxDao.selectPropertyList(key1, key2);
		List resultlist = new ArrayList();
		if(list!=null&&list.size()>0){
		  Iterator<Object[]> it = list.iterator();
		  while(it.hasNext()){
			  Object[] obj = it.next();
			 //?  
		  }
		}
		BaseResult result = new BaseResult(1,"成功");
		result.setList(resultlist);		
		return result;

	}

	@Override
	public BaseResult selectPropertyListByJbxx(String key1, String key2) {
		List<Object []> list = this.glzjbxxDao.selectPropertyList("id", "name");
		List resultlist = new ArrayList();
		if(list!=null&&list.size()>0){
		  Iterator<Object[]> it = list.iterator();
		  while(it.hasNext()){
			  Object[] obj = it.next();
			  HGlzGlzJbxx glzjbxx = new HGlzGlzJbxx();
			  glzjbxx.setId((String)obj[0]);
			  glzjbxx.setName((String)obj[1]);
			  resultlist.add(glzjbxx);
		  }
		}
		BaseResult result = new BaseResult(1,"成功");
		result.setList(resultlist);		
		return result;

	}

	@Override
	public BaseResult selectGyldgkList(Integer page, Integer rows,String stationId, String selectvalue) {
		BaseResult result = new BaseResult(1,"管养路段概况列表获取成功");
        BaseQueryRecords records = this.gyldDao.selectGyldgkList(page, rows, stationId, selectvalue);
        result.setObj(records);
		return result;
	}

	@Override
	public BaseResult selectGyldXq(String id) {
		BaseResult result = new BaseResult(1,"管养路段详情获取成功");
		HGlzGlzGyld gyld = this.gyldDao.selectGyldxq(id);
		result.setObj(gyld);
		return result;
	}

	@Override
	public BaseResult selectDwrygkList(Integer page, Integer rows,
			String stationId, String selectvalue) {
		BaseResult result = new BaseResult(1,"单位人员况列表获取成功");
        BaseQueryRecords records = this.dwryDao.selectDwrygkList(page, rows, stationId, selectvalue);
        String glzname = this.glzjbxxDao.selectGlzName(stationId);
        String[] objs= new String[1];
        objs[0]=glzname;
        result.setObjs(objs);
        result.setObj(records);
		return result;
	}

	@Override
	public BaseResult selectDwryXq(String id) {
		BaseResult result = new BaseResult(1,"单位人员详情获取成功");
		HGlzJxDwry gyld = this.dwryDao.selectDwryXq(id);
		result.setObj(gyld);
		return result;
	}

	@Override
	public BaseResult selectGlzQlgkList(Short removeMake,Integer page, Integer rows,
			String stationId, String selectvalue) {
		BaseResult result = new BaseResult(1,"公路站桥梁概况列表获取成功");
		//short removeMake = 0;   //给他的标记
        BaseQueryRecords records = this.glzqlgkDao.selectGlzQlgkList(removeMake,page, rows, stationId, selectvalue);
        result.setObj(records);
		return result;
		
	}

	@Override
	public BaseResult selectGlzQlgkQx(String id) {
		BaseResult result = new BaseResult(1,"公路站桥梁详情获取成功");
		HGlzQlgk qlgk = this.glzqlgkDao.selectGlzQlgkXq(id);
        result.setObj(qlgk);
		return result;
	}

	@Override
	public BaseResult selectGlzHdgkList(Short removeMake, Integer page,
			Integer rows, String stationId, String selectvalue) {
		BaseResult result = new BaseResult(1,"公路站涵洞概况列表获取成功");
        BaseQueryRecords records = this.glzhdgkDao.selectGlzHdgkList(removeMake,page, rows, stationId, selectvalue);
        result.setObj(records);
		return result;
	}

	@Override
	public BaseResult selectGlzHdgkQx(String id) {
		BaseResult result = new BaseResult(1,"公路站涵洞详情获取成功");
		HGlzHdgk hdgk = this.glzhdgkDao.selectGlzHdgkXq(id);
        result.setObj(hdgk);
		return result;
	}

	@Override
	public BaseResult selectWorkDateList(){
		List list = this.yscqktjxxDao.selectWorkDateList();
		BaseResult result = new BaseResult(1,"年份列表获取成功");
		result.setList(list);
		return result;
	}
	
	
	
	@Override
	public BaseResult selectDbYscqktjxx(int page, int rows, String stationId,
			String workDate) {
		BaseResult result = new BaseResult(1,"月生产统计信息获取成功");
        BaseQueryRecords records = this.yscqktjxxDao.selectYsctjxxList(page, rows, stationId, workDate);
       /* List<HGlzDbYscqktjxx> tjxxs=(List<HGlzDbYscqktjxx>) records.getData();*/
       /* if(tjxxs!=null&&tjxxs.size()>0){
        	Iterator<HGlzDbYscqktjxx> it = tjxxs.iterator();
        	while(it.hasNext()){
        		HGlzDbYscqktjxx tjxx = it.next();
        		Timestamp workdate = tjxx.getWorkDate();	
        		if(workdate!=null){
                String tsStr = "";   
                DateFormat sdf = new SimpleDateFormat("yyyy年MM月");   
                tsStr = sdf.format(workdate);  
                tjxx.setWorkdateString(tsStr);
        		}
          }
        }*/
        String glzname = this.glzjbxxDao.selectGlzName(stationId);
        String[] objs= new String[1];
        objs[0]=glzname;
        result.setObjs(objs);
        result.setObj(records);
		return result;	
		
	}

	@Override
	public BaseResult selectXcjlList(int page, int rows, String stationId,
			String workDate) {
		BaseResult result = new BaseResult(1,"巡查记录获取成功");
        BaseQueryRecords records = this.glxcjlDao.selectXcGlxcjl(page, rows, stationId, workDate);
        result.setObj(records);
        String glzname = this.glzjbxxDao.selectGlzName(stationId);
        String[] objs= new String[1];
        objs[0]=glzname;
        result.setObjs(objs);
		return result;	
	}

	@Override
	public BaseResult selectInspectDateList(){
		List list = this.glxcjlDao.selectInspectDateList();
		BaseResult result = new BaseResult(1,"巡查年份列表获取成功");
		result.setList(list);
		return result;
	}
	
	@Override
	public BaseResult selectXcjlXq(String id) {
		BaseResult result = new BaseResult(1,"查看巡查记录详情成功");
		HGlzXcGlxcjl xcjl = this.glxcjlDao.selectXcGlxcjlXq(id);
		List<GlxcldBean> xcld = this.glxcjlDao.selectXcGlxcldXq(id);
		xcjl.setGyldlist(xcld);
		result.setObj(xcjl);
		return result;
	}

	@Override
	public BaseResult selectQljcxjcList(Integer page, Integer rows,
			String stationId, String bridgeId, String selectvalue) {
		BaseResult result = new BaseResult(1,"桥梁经常性检查记录获取成功");
	    BaseQueryRecords records = this.qljcxjcDao.selectQljcxjcList(page, rows, stationId, bridgeId, selectvalue);
		result.setObj(records);	
		return result;
	}

	@Override
	public BaseResult selectQljcxjxMx(String id) {
		BaseResult result = new BaseResult(1,"桥梁经常性检查明细获取成功");
		QljcxjcBean qlxcjcQx = this.qljcxjcDao.selectQljcxjcXq(id);
		List list = this.qljcxjcDao.selecQljcxjcmxXq(qlxcjcQx.getId());
		result.setObj(qlxcjcQx);	
		result.setList(list);
		return result;
	}

	@Override
	public BaseResult selectHdjcxjcList(Integer page, Integer rows,
			String stationId, String culverId, String selectvalue) {
		BaseResult result = new BaseResult(1,"涵洞经常性检查记录获取成功");
	    BaseQueryRecords records = this.hdjcxjcDao.selectHdjcxjcList(page, rows, stationId, culverId, selectvalue);
		result.setObj(records);	
		return result;
	}

	@Override
	public BaseResult selectHdjcxjxMx(String id) {
		BaseResult result = new BaseResult(1,"桥梁经常性检查明细获取成功");
		HdjcxjcBean hdxcjcQx = this.hdjcxjcDao.selectHdjcxjcXq(id);
		List list = this.hdjcxjcDao.selecHdjcxjcmxXq(hdxcjcQx.getId());
		result.setObj(hdxcjcQx);	
		result.setList(list);
		return result;
	}

	@Override
	public BaseResult selectQlNameList() {
		BaseResult result = new BaseResult(1,"桥梁名称列表获取成功");
		List list = this.qljcxjcDao.selectQlNameList();
		result.setList(list);
		return result;
	}

	@Override
	public BaseResult selectHdCodeList() {
		BaseResult result = new BaseResult(1,"涵洞名称列表获取成功");
		List list = this.hdjcxjcDao.selectHdCodeList();
		result.setList(list);
		return result;
	}

	
	
	
}
