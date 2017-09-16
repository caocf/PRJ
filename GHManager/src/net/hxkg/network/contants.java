package net.hxkg.network;



public class contants {
	
	public static final String baseUrl = "http://120.55.193.1:8080/GH/";
	
	public static final String login_url =baseUrl+ "/login";
	
	public static final String newslist_url =baseUrl+ "/newslist";
	
	public static final String waterlist_url = baseUrl +"/waterinfolist";
	
	public static final String idea_url = baseUrl +"/sendadvice";
	
	public static final String sendcomment = baseUrl +"/comment";
	//注册获取验证码
	public static final String getmessage = baseUrl +"/getmessage";
	//注册验证信息
	public static final String nextcheck = baseUrl +"/validate";
	
	
	//许可信息列表
	public static final String getpermissionlist = baseUrl +"/permissionlist";
	
	//危货进港船名
	public static final String getshipnamelist = baseUrl +"/myshiplist";
	
	//提交危货
	public static final String sendrecord = baseUrl +"/dangerreport";
	
	//按船名获取危货进港列表
	public static final String dangerlist = baseUrl +"/dangerlist";
	
	//获取最新发布列表
	public static final String latest = baseUrl +"/latest";
	
	//查询船舶出租列表
	public static final String rentlist = baseUrl +"/rentlist";
	
	//查询船舶出售列表
	public static final String salelist = baseUrl +"/salelist";

	//注册提交
	public static final String regist_submit = baseUrl +"/register";
	
	//按条件查询船源列表		
	public static final String shipslist = baseUrl +"/shipslist";
	
	//按条件查询货源列表	
	public static final String goodslist = baseUrl +"/goodslist";
	
	//发布租船信息
	public static final String postshiprent = baseUrl +"/postshiprent";
	
	//发布货源信息
	public static final String postgoods = baseUrl +"/postgoods";
	
	//评论列表
	public static final String comment = baseUrl +"/commentlist";
	
	//发布船源信息
	public static final String postships = baseUrl +"/postships";
	
	//发布船舶出售信息
	public static final String postshipsale = baseUrl +"/postshipsale";
	
	//获取我的发布列表
	public static final String mypost = baseUrl +"/mypost";
	
	//按ID查找货源
	public static final String goodsunique = baseUrl +"/goodsunique";
	
	//按ID查找船源
	public static final String shipsourceunique = baseUrl +"/shipsourceunique";
	
	//按ID查找租船
	public static final String shiprentunique = baseUrl +"/shiprentunique";
	
	//按ID查找售船
	public static final String shipsaleunique = baseUrl +"/shipsaleunique";
	
	//按ID删除货源
	public static final String removegoods = baseUrl +"/removegoods";
	
	//按ID删除船源
	public static final String removeshipsource = baseUrl +"/removeshipsource";
		
	//按ID删除租船
	public static final String removeshiprent = baseUrl +"/removeshiprent";
	
	//按ID删除售船
	public static final String removeshipsale = baseUrl +"/removeshipsale";
	
	//按提示文字获取船名列表
	public static final String shipnames = baseUrl +"/shipnames";
	
	//按船名获取船舶基本信息
	public static final String baseinfo = baseUrl +"/baseinfo";
	
	//按船名获取船舶证书信息
	public static final String certinfo = baseUrl +"/certinfo";
		
	//按船名获取船舶违章信息
	public static final String illegallist = baseUrl +"/illegallist";
	
	//按船名获取船舶信用信息
	public static final String creditlist = baseUrl +"/creditlist";
	
	//按用户名获取船舶列表
	public static final String myshiplist = baseUrl +"/myshiplist";
	
	//按用户名添加船舶
	public static final String addmyship = baseUrl +"/addmyship";
	

	//船舶检查器
	public static final String shipcheck = baseUrl +"/shipcheck";
	
	//检测当前APP版本
	public static final String versioncheck = baseUrl +"/versioncheck";
	
	//按提示文字获取码头名列表
	public static final String wharfnames = baseUrl +"/wharfnames";
	
	//按地域获取码头列表
	public static final String wharflist = baseUrl +"/wharflist";
	

	
}
