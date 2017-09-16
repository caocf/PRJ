package com.example.smarttraffic.common.localDB;

public class DetailFavorSQLString
{
	public static final String DETAIL_FAVOR_TABLE="detailFavor";
	public static final String COLUM_ID="df_id";
	public static final String COLUM_TYPE="df_type";
	public static final String COLUM_NAME="df_name";
	public static final String COLUM_ADDRESS="df_address";
	public static final String COLUM_PHONE="df_phone";
	public static final String COLUM_LEVEL="df_level";
	public static final String COLUM_LANTITUDE="df_lantitude";
	public static final String COLUM_LONGTITUDE="df_lontitude";
	
	public static final String CREATE_FAVOR_DB="CREATE TABLE IF NOT EXISTS "+DETAIL_FAVOR_TABLE+" ("+COLUM_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COLUM_TYPE+" INTEGER,"+ COLUM_NAME+" TEXT,"+COLUM_ADDRESS+" TEXT,"+COLUM_PHONE+" TEXT,"+COLUM_LEVEL+" INTEGER,"+COLUM_LANTITUDE+" INTEGER,"+COLUM_LONGTITUDE+" INTEGER"+")";
	public static final String UPDATE_FAVOR_DB="";
	
	public static final String INSERT_FAVOR_STATEMENT="insert into "+DETAIL_FAVOR_TABLE+"("+COLUM_TYPE+","+COLUM_NAME+","+COLUM_ADDRESS+","+COLUM_PHONE+","+COLUM_LEVEL+","+COLUM_LANTITUDE+","+COLUM_LONGTITUDE+") values (?,?,?,?,?,?,?)";
//	public static final String UPDATE_FAVOR_STATEMENT="update "+DETAIL_FAVOR_TABLE+" set "+COLUM_ADDRESS+" =? where "+COLUM_ID+"=?";
	public static final String DELETE_FAVOR_STATEMENT="delete from "+DETAIL_FAVOR_TABLE+" where "+COLUM_ID+"=?";
	public static final String SELECT_FAVOR_STATEMENT="select * from "+DETAIL_FAVOR_TABLE+" where "+COLUM_TYPE+"=? ";
	public static final String IS_FAVOR_STATEMENT="select * from "+DETAIL_FAVOR_TABLE+" where "+COLUM_TYPE+"=? and "+COLUM_NAME+"=? ";
}
