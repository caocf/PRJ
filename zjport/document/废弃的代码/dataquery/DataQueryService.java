package com.zjport.dataquery;

import com.common.base.BaseResult;
import com.common.base.service.BaseService;
import com.zjport.dataquery.model.Condition;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("dataQueryService")
public class DataQueryService extends BaseService
{

	@Resource(name="dataQueryDao")
	DataQueryDao dataQueryDao;

	public BaseResult querySorts()
	{
		return this.dataQueryDao.querySorts();
	}

	public BaseResult querySingle(int id, String code, String value, Integer page, Integer rows)
	{
		return this.dataQueryDao.querySingle(id, code, value, page, rows);
	}

	public BaseResult queryDetail(int id, String key)
	{
		return this.dataQueryDao.queryDetail(id, key);
	}
}
