package com.highwaycenter.role.dao;
//xiugai
import java.util.List;

import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.HQL;
import com.common.dao.impl.SQL;
import com.highwaycenter.role.model.HJcJbqx;
import com.highwaycenter.role.model.HJcJsqx;


public interface JsqxDao {
	public void save(HJcJsqx jsqx);
	//public void update(HJcJsqx jsqx);
	public void delete(HJcJsqx jsqx);
	public HJcJsqx findById(int jsqx_id);
	public BaseQueryRecords findByRole(int jsbh);
	public void sqlOperate(String sql);
	public int deleteByJs(int jsbh);
	public BaseQueryRecords findByHql(HQL hql);
	public List<Integer> selectQxbhListByRole(Integer jsbh);
	public BaseQueryRecords findBySql(SQL sql);
	public List<HJcJbqx> findQxmcsByJs(int jsbh);

}
