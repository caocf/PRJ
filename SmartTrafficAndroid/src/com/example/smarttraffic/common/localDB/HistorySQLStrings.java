package com.example.smarttraffic.common.localDB;

public class HistorySQLStrings 
{
	public static final String HISTORY_TABLE="history";
	public static final String COLUM_ID="history_id";
	public static final String COLUM_TYPE="history_type";
	public static final String COLUM_CONTENT="history_content";
	
	public static final String CREATE_HISTORY_DB="CREATE TABLE IF NOT EXISTS "+HISTORY_TABLE+" ("+COLUM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COLUM_TYPE+" INTEGER,"+ COLUM_CONTENT+" TEXT)";
	public static final String UPDATE_HISTORY_DB="";
	
	public static final String INSERT_HISTORY_STATEMENT="insert into "+HISTORY_TABLE+"("+COLUM_TYPE+","+COLUM_CONTENT+") values (?,?)";
	public static final String UPDATE_HISTORY_STATEMENT="update "+HISTORY_TABLE+" set "+COLUM_CONTENT+" =? where "+COLUM_ID+"=?";
	public static final String DELETE_HISTORY_STATEMENT="delete from "+HISTORY_TABLE+" where "+COLUM_ID+"=?";
	public static final String DELETE_ALL_HISTORY_STATEMENT="delete from "+HISTORY_TABLE+" where "+COLUM_TYPE+"=?";
	public static final String SELECT_HISTORY_STATEMENT="select * from "+HISTORY_TABLE+" where "+COLUM_TYPE+"=? order by "+COLUM_ID+" DESC";
	public static final String SELECT_HISTORY_STATEMENT_ASC="select * from "+HISTORY_TABLE+" where "+COLUM_TYPE+"=?";
	public static final String IS_HISTORY_STATEMENT="select * from "+HISTORY_TABLE+" where "+COLUM_TYPE+"=? and "+COLUM_CONTENT+"=? ";
}
