package com.huzhouport.common.util;

import java.util.ArrayList;
import java.util.List;

import com.huzhouport.common.model.KeyValuePair;
import com.huzhouport.common.model.QueryCondition;

/**
 * 封装查询语句
 * 
 * 
 */
public class QueryConditionSentence {

	/**
	 * 获取查询条件，封装并返回string
	 * 
	 * @param queryCondition
	 *            查询对象
	 * @return
	 * @throws Exception
	 */
	public String QCS(QueryCondition queryCondition) throws Exception {

		List<String> list = new ArrayList<String>();// 暂时载入所有查询条件
		String returnQCS = "";// 返回的查询语句
		//CommomValidation cv = new CommomValidation();
		// 判断是否传入查询条件
		if (queryCondition.getListKeyValuePair().size() != 0) {		
			list = Condition(queryCondition.getListKeyValuePair());
			int listLength = list.size();
			for (int i = 0; i < listLength; i++) {

				if (i == listLength - 1) {
					returnQCS += list.get(i);
				} else {
					returnQCS += list.get(i)
							+ " "
							+ queryCondition.getListKeyValuePair().get(i)
									.getConnector() + " ";
				}
			}
		}

		return returnQCS;
	}

	/**
	 * 按照什么条件排序
	 * 
	 * @param queryCondition
	 * @return
	 */
	public String Sequence(QueryCondition queryCondition) {
		String returnQCS = "";
		if (null != queryCondition.getOrderByFielName()) {
			returnQCS = (" order by " + queryCondition.getOrderByFielName()
					+ " " + queryCondition.getSequence());
		}
		return returnQCS;
	}

	/**
	 * 封装所有条件语句（以键值对的格式封装，如：userName='zhangsan'）
	 * 
	 * @param list
	 *            所有的查询条件
	 * @return
	 * @throws Exception
	 */
	protected List<String> Condition(List<KeyValuePair> list) throws Exception {
		List<String> conditionSentence = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getKey().equals("")
					|| list.get(i).getPair().equals("")
					|| list.get(i).getValue().equals("")) {
				continue;
			} else {
				conditionSentence.add(JudgeKVP(list.get(i)));
			}
		}
		return conditionSentence;
	}

	/**
	 * 封装某个条件语句
	 * 
	 * @param keyValuePair
	 * @return
	 * @throws Exception
	 */
	protected String JudgeKVP(KeyValuePair keyValuePair) throws Exception {

		// 判断是否是时间值
		String[] value = keyValuePair.getKey().split("_");
		if (value[0].equals("date")) {
			// 判断时间的连接符是否是“小于”，“大于”，“等于”，“不等于”，“大于等于”，“小于等于”
			String dateKey = value[1];
			if (keyValuePair.getPair().equals(GlobalVar.PAIRCONTAIN)
					|| keyValuePair.getPair().equals(GlobalVar.PAIRNOCONTAIN)
					|| keyValuePair.getPair().equals(GlobalVar.PAIRSTART)
					|| keyValuePair.getPair().equals(GlobalVar.PAIREND)) {
				throw new Exception(
						"时间类型的值只能选择“小于”，“大于”，“等于”，“不等于”，“大于等于”，“小于等于”");
			}
			/*
			 * else if (new CommomValidation().isYMD(keyValuePair.getValue())) {
			 * //dateKey = "to_char(" + dateKey + ",'yyyy-mm-dd')"; }
			 */
			return dateKey + keyValuePair.getPair() + "'"
					+ keyValuePair.getValue() + "'";
		}

		if (value[0].equals("int")) {
			return value[1] + keyValuePair.getPair() + keyValuePair.getValue();
		}

		if (value[0].equals("in")) {
			return value[1] + keyValuePair.getPair() + keyValuePair.getValue();
		}

		if (value[0].equals("datemin")) {
			return value[1]
					+ keyValuePair.getPair()
					+ ChangeType.StringToDate(keyValuePair.getValue())
							.getTime() / 1000;
		}

		if (value[0].equals("datemax")) {
			return value[1]
					+ keyValuePair.getPair()
					+ ChangeType
							.StringToDate(
									ChangeType.TomorrowByValue(keyValuePair
											.getValue())).getTime() / 1000;
		}

		// 判断连接符是否是包含关系
		if (keyValuePair.getPair().equals(GlobalVar.PAIRCONTAIN)||keyValuePair.getPair().equals("like")) {
			return keyValuePair.getKey() + " like '%" + keyValuePair.getValue()
					+ "%'";
		}

		// 判断连接符是否是包含多個条件关系
		if (keyValuePair.getPair().equals(GlobalVar.PAIRCONTAINMANY)) {
			return keyValuePair.getKey() + " in ('" + keyValuePair.getValue()
					+ "')";
		}

		// 判断连接符是否是不包含关系
		if (keyValuePair.getPair().equals(GlobalVar.PAIRNOCONTAIN)) {
			return keyValuePair.getKey() + " not like '%"
					+ keyValuePair.getValue() + "%'";
		}

		// 判断连接符是否是以.....开头关系
		if (keyValuePair.getPair().equals(GlobalVar.PAIRSTART)) {
			return keyValuePair.getKey() + " like '%" + keyValuePair.getValue()
					+ "'";
		}

		// 判断连接符是否是以.....结束关系
		if (keyValuePair.getPair().equals(GlobalVar.PAIREND)) {
			return keyValuePair.getKey() + " like '" + keyValuePair.getValue()
					+ "%'";
		}

		// 等于关系
		return keyValuePair.getKey() + keyValuePair.getPair() + "'"
				+ keyValuePair.getValue() + "'";

	}
}
