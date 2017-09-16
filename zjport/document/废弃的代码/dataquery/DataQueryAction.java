package com.zjport.dataquery;

import com.common.base.BaseResult;
import com.zjport.dataquery.model.Condition;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("/dataquery")
@Controller
public class DataQueryAction
{

	@Resource(name="dataQueryService")
	DataQueryService dataQueryService;

	BaseResult result;

	@RequestMapping("/sorts")
	@ResponseBody
	public BaseResult queryLxs()
	{
		this.result = this.dataQueryService.querySorts();
		return result;
	}

	@RequestMapping("/single")
	@ResponseBody
	public BaseResult querySingle(int id, String code, String value, Integer page, Integer rows)
	{
		this.result = this.dataQueryService.querySingle(id, code, value, page, rows);
		return result;
	}

	@RequestMapping("/detail")
	@ResponseBody
	public BaseResult queryDetail(int id, String key)
	{
		this.result = this.dataQueryService.queryDetail(id, key);
		return result;
	}

}
