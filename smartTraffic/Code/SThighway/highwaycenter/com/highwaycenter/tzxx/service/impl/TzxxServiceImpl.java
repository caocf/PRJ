package com.highwaycenter.tzxx.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.common.action.BaseResult;
import com.common.dao.BaseQueryRecords;
import com.common.service.BaseService;
import com.highwaycenter.bean.selectListBean;
import com.highwaycenter.tzxx.dao.TzxxDao;
import com.highwaycenter.tzxx.model.HTzTzxxb;
import com.highwaycenter.tzxx.service.TzxxService;

@Service("tzxxservice")
public class TzxxServiceImpl extends BaseService implements TzxxService{
	@Resource(name="tzxxdao")
	private TzxxDao tzxxDao;

	@Override
	public BaseResult selectTzxxList(Integer page, Integer rows,
			String columnId, String selectvalue) {
		BaseQueryRecords records = this.tzxxDao.selectTzxxList(page, rows, columnId, selectvalue);
		List<HTzTzxxb> tzxxlist = (List<HTzTzxxb>) records.getData();
		if(tzxxlist!=null&&tzxxlist.size()>0){
		for(int i=0;i<tzxxlist.size();i++){
			HTzTzxxb tzxx = tzxxlist.get(i);
			String title = tzxx.getTitle();
			
			if(title.indexOf("】")>-1){
				title = title.substring(title.indexOf("】")+1, title.length());
			}
            if(title.indexOf("]")>-1){
            	title = title.substring(title.indexOf("]")+1, title.length());
			}
            
            tzxx.setTitle(title);
            tzxxlist.set(i, tzxx);
		   }
		records.setData(tzxxlist);
		
		}
		
		BaseResult result = new BaseResult(1,"通阻信息列表获取成功！");
		result.setObj(records);	
		return result;
	}

	@Override
	public BaseResult selectTzxxXq(String main_id) {
		HTzTzxxb tzxx = this.tzxxDao.selectTzxxXq(main_id);		
		BaseResult result = new BaseResult(1,"通阻信息获取成功！");
		String title = tzxx.getTitle();
		
		if(title.indexOf("】")>-1){
			title = title.substring(title.indexOf("】")+1, title.length());
		}
        if(title.indexOf("]")>-1){
        	title = title.substring(title.indexOf("]")+1, title.length());
		}
        
        tzxx.setTitle(title);
		
		result.setObj(tzxx);	
		return result;
	}

	@Override
	public BaseResult selectTzxxColumnList() {
		BaseResult result = new BaseResult(1,"通阻类别列表获取成功！");
		List list = new ArrayList();
		List<Object[]> objlist = this.tzxxDao.selectProperty("columnId", "columnName");
		if(objlist!=null&&objlist.size()>0){
			Iterator<Object[]> it = objlist.iterator();
			while(it.hasNext()){
				Object[] obj = it.next();
				selectListBean colunm = new selectListBean();
				colunm.setId_string((String)obj[0]);
				colunm.setName((String)obj[1]);
				list.add(colunm);
			}
			
		}
		result.setList(list);
		
		return result;
	}

}
