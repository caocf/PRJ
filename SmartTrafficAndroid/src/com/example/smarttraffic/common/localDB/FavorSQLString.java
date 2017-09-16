package com.example.smarttraffic.common.localDB;

public class FavorSQLString 
{
	public static final String FAVOR_TABLE="favor";
	public static final String COLUM_ID="favor_id";
	public static final String COLUM_TYPE="favor_type";
	public static final String COLUM_CONTENT="favor_content";
	public static final String COLUM_LAN1="lan1";
	public static final String COLUM_LON1="lon1";
	public static final String COLUM_LAN2="lan2";
	public static final String COLUM_LON2="lon2";
	
	public static final String CREATE_FAVOR_DB="CREATE TABLE IF NOT EXISTS "+FAVOR_TABLE+" ("+COLUM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COLUM_TYPE+" INTEGER,"+ COLUM_CONTENT+" TEXT,"+COLUM_LAN1+" INTEGER,"+COLUM_LON1+" INTEGER,"+COLUM_LAN2+" INTEGER,"+COLUM_LON2+" INTEGER)";
	public static final String UPDATE_FAVOR_DB="";
	
	public static final String INSERT_FAVOR_STATEMENT="insert into "+FAVOR_TABLE+"("+COLUM_TYPE+","+COLUM_CONTENT+","+COLUM_LAN1+","+COLUM_LON1+","+COLUM_LAN2+","+COLUM_LON2+") values (?,?,?,?,?,?)";
	public static final String UPDATE_FAVOR_STATEMENT="update "+FAVOR_TABLE+" set "+COLUM_CONTENT+" =? where "+COLUM_ID+"=?";
	public static final String DELETE_FAVOR_STATEMENT="delete from "+FAVOR_TABLE+" where "+COLUM_ID+"=?";
	public static final String SELECT_FAVOR_STATEMENT="select * from "+FAVOR_TABLE+" where "+COLUM_TYPE+"=? ";
	public static final String IS_FAVOR_STATEMENT="select * from "+FAVOR_TABLE+" where "+COLUM_TYPE+"=? and "+COLUM_CONTENT+"=? ";
}
