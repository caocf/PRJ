package com.zjport.dataquery;

import com.common.base.BaseRecords;
import com.common.base.BaseResult;
import com.common.base.dao.impl.BaseDaoDB;
import com.common.base.dao.impl.querycondition.HQL;
import com.common.base.dao.impl.querycondition.ObjectQuery;
import com.common.base.dao.impl.querycondition.SQL;
import com.zjport.dataquery.model.Condition;
import com.zjport.dataquery.model.TCxlx;
import com.zjport.dataquery.model.TCxtj;
import com.zjport.dataquery.model.TCxzs;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("dataQueryDao")
public class DataQueryDao extends BaseDaoDB
{

	public BaseResult querySorts()
	{
		return new BaseResult(BaseResult.RESULT_OK, "", super.find(new ObjectQuery(TCxlx.class, "id", false)));
	}

	public BaseResult querySingle(int id, String code, String value, Integer page, Integer rows)
	{
		TCxlx tCxlx = (TCxlx) super.findUnique(new ObjectQuery(TCxlx.class, "id", id));
		if (tCxlx == null)
		{
			return BaseResult.newResultFailed("参数错误");
		}

		// 根节点
		if (tCxlx.getPid() == -1)
		{
			List<TCxlx> tCxlxList = super.find(new ObjectQuery(TCxlx.class, "pid", id, "id", false)).getData();
			id = tCxlxList.get(0).getId();
		}

		// 查询条件
		List<TCxtj> tCxtjList = super.find(new ObjectQuery(TCxtj.class, "lxid", id, "id", false)).getData();

		// 展示字段
		HQL hql = new HQL("from TCxzs where lxid =:lxid and zs=1 and zj=0");
		Map<String, Object> tj = new HashMap<>();
		tj.put("lxid", id);
		hql.setParamMap(tj);
		List<TCxzs> tCxzsList = (List<TCxzs>) super.find(hql).getData();

		BaseRecords records = getData(id, code, value, page, rows);

		Map<String, Object> map = new HashMap<>();
		map.put("cxtj", tCxtjList);
		map.put("cxzs", tCxzsList);

		BaseResult result = BaseResult.newResultOK(records);
		result.setMap(map);

		return result;
	}

	private BaseRecords getData(int id, String code, String value, Integer page, Integer rows)
	{
		if (page == null)
		{
			page = 1;
		}

		if (rows == null)
		{
			rows = 10;
		}

		BaseRecords records;
		Map<String, Object> paramMap = new HashMap<>();
		List<TCxzs> cxzsList = super.find(new ObjectQuery(TCxzs.class, "lxid", id, "id", false)).getData();
		TCxlx cxlx = (TCxlx)super.findUnique(new ObjectQuery(TCxlx.class, "id", id));
		String table = cxlx.getTable();

		String column = "";
		for(TCxzs zs : cxzsList) {
			if(zs.getZs() == 1) {
				column += zs.getCode()+",";
			}
		}
		if(!"".equals(column)) {
			column = column.substring(0, column.length()-1);
		}

		String sql = "select " + column + " from " + table;

		if (StringUtils.isNotEmpty(value))
		{
			sql += " where " + code + " like '%" + value.trim() + "%'";
		}
		SQL Sql = new SQL(sql, paramMap);
		Sql.setPaging(page, rows);
		records = super.find(Sql);
		return records;
	}

	public BaseResult queryDetail(int id, String key)
	{
		// colums
		List<TCxzs> tCxzsList = super.find(new ObjectQuery(TCxzs.class, "lxid", id, "id", false)).getData();

		Map<String, Object> map = new HashMap<>();
		map.put("column", tCxzsList);
		map.put("data", getXq(id, key));

		BaseResult result = BaseResult.newResultOK();
		result.setMap(map);

		return result;
	}

	private Object getXq(int id, String key)
	{
		Object o = null;

		Map<String, Object> paramMap = new HashMap<>();
		List<TCxzs> cxzsList = super.find(new ObjectQuery(TCxzs.class, "lxid", id, "id", false)).getData();
		TCxlx cxlx = (TCxlx)super.findUnique(new ObjectQuery(TCxlx.class, "id", id));
		String table = cxlx.getTable();


		HQL hql = new HQL("from TCxzs where lxid = :lxid and zj=1");
		Map<String, Object> paramMapZj = new HashMap<>();
		paramMapZj.put("lxid", id);
		hql.setParamMap(paramMapZj);

		TCxzs zsZj = (TCxzs)super.find(hql).getData().get(0);
		String zj = zsZj.getCode();


		String column = "";
		for(TCxzs zs : cxzsList) {
			if(zs.getZj() != 1) {
				column += zs.getCode()+",";
			}
		}
		if(!"".equals(column)) {
			column = column.substring(0, column.length()-1);
		}

		String sql = "select " + column + " from " + table + " where "+zj+" = :zj";
		paramMap.put("zj", key);
		SQL Sql = new SQL(sql, paramMap);
		o = super.findUnique(Sql);
		return o;
	}

}
