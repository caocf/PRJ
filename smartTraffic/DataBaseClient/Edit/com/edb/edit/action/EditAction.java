package com.edb.edit.action;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.edb.edit.model.KeyValue;
import com.edb.edit.model.Table;
import com.edb.edit.service.EditService;
import com.opensymphony.xwork2.ActionContext;

public class EditAction {

	List<Table> tables;

	public List<Table> getTables() {
		return tables;
	}

	List<?> columns;
	List<?> content;
	String tableName;
	String rowid;
	String result;
	int page;
	int num;
	int count;
	String username;
	String password;
	
	boolean connect;
	public boolean isConnect() {
		return connect;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getCount() {
		return count;
	}

	public String getTableName() {
		return tableName;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<?> getColumns() {
		return columns;
	}

	public List<?> getContent() {
		return content;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}

	public String getResult() {
		return result;
	}

	EditService editService=new EditService();


	// 所有表信息
	public String queryAllTable() {
		this.tables = editService.queryAllTables();

		return "success";
	}

	// 表列信息
	public String queryColumnByTableName() {
		try{
			if (!tableName.equals("")){
				this.columns = this.editService.queryColumnByTableName(tableName);
				Map<String,Object> session=ActionContext.getContext().getSession();
				this.content = this.editService.queryUserTablePrivsByTabeName((String) session.get("username"),tableName);
			}
				
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	// 表内容信息
	public String queryContetnByTableName() {
		if (page == 0 || num == 0) {
			page = 1;
			num = 20;
		}

		if (!tableName.equals("")) {
			this.content = this.editService.queryContentByTableName(tableName,
					page, num);
			this.count = this.editService.count(tableName);
		}

		return "success";
	}

	// 查询一条数据
	public String queryOneRow() {
		if (!tableName.equals("") && !rowid.equals(""))
			this.content = this.editService.queryOneRow(tableName, rowid);

		return "success";
	}

	// 删除一行
	public String deleteByTableAndID() {
		result = this.editService.delete(tableName, rowid);

		return "success";
	}

	public List<KeyValue> paramsMap;

	public void setParamsMap(List<KeyValue> paramsMap) {
		this.paramsMap = paramsMap;
	}

	// 添加一行
	public String addContent() {
		if (paramsMap != null && tableName != null && tableName.length() > 0)
			result = this.editService.addContent(tableName, paramsMap);

		return "success";
	}

	// 更新一行
	public String updateContent() {
		if (paramsMap != null && tableName != null && tableName.length() > 0
				&& rowid != null && rowid.length() > 0)
			result = this.editService
					.updateContent(tableName, paramsMap, rowid);

		return "success";
	}

	InputStream inputExcel;

	public InputStream getInputExcel() {
		return inputExcel;
	}

	// 下载excel表格模板
	public String downloadExcel() {
		inputExcel = this.editService.downloadExcel(tableName);

		return "success";
	}

	File excelFile;

	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

	// 上传保存
	public String uploadExcel() {

		try {
			if (excelFile == null) {
				throw new Exception("请添加导入文件");
			} else {
				result = this.editService.inputChannel(excelFile, excelFile.getName().substring(0, excelFile.getName().lastIndexOf("."))+".xls", tableName);
			}

		} catch (Exception e) {
			result = e.toString();
		}
		
		result="success";
		return "success";
	}

	public String initDB() {
		addSession(username, password);
		if(editService.queryAllTables()!=null && editService.queryAllTables().size()>0)
		{
			connect=true;
		}
		else
		{
			connect=false;
			ActionContext actionContext=ActionContext.getContext();
			Map<String,Object> session=actionContext.getSession();
			session.remove("username");		
			session.remove("password");
		}
		
		return "success";
	}
	
	public void addSession(String name,String pass)
	{
		ActionContext actionContext=ActionContext.getContext();
		Map<String,Object> session=actionContext.getSession();
		session.put("username", name);		
		session.put("password", pass);
	}
}
