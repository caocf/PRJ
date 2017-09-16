package com.example.smarttraffic.smartBus.parse;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.smartBus.bean.QRCode;
import com.example.smarttraffic.util.DoString;

/**
 * QR数据解析类
 * @author Administrator zwc
 *
 */
public class QRSearch extends BaseSearch
{
	@Override
	public Object parse(String data)
	{
		QRCode result=new QRCode();
		
		result=JSON.parseObject(DoString.parseThreeNetString(data), QRCode.class);
		
		return result;
	}
}
