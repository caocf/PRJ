package com.channel.dic.dao;

import java.util.List;

import com.channel.model.hd.CZdAppattribute;
import com.channel.model.user.CZdUserjstatus;
import com.common.dao.BaseQueryRecords;

public interface DicDao {

	public BaseQueryRecords<CZdAppattribute> queryAllTitle();

	public BaseQueryRecords<CZdUserjstatus> queryAllJStatus();

	public BaseQueryRecords<CZdAppattribute> queryDicAttr(String name);

	public String queryAttrDesc(Integer id);

	public List queryNameDesc();

	public void addDic(CZdAppattribute dic);

	public void addUserJstatus(CZdUserjstatus jstatus);

	public CZdAppattribute queryDicById(int id);

	public CZdUserjstatus queryJstatusById(int id);

	public void updateDic(CZdAppattribute dic);
	
	public void updateJstatus(CZdUserjstatus jstatus);

	public List queryAllName();

	public int queryIdByNameDesc(String name, String desc);
}
