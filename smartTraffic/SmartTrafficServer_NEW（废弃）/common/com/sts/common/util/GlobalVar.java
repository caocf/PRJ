package com.sts.common.util;

public final class GlobalVar {
	public static final int SAERCH_PAGESIZE=10;//分页
	
	public static final String MAP_SERVICE_URL="http://192.168.170.3:8090/zhjtapi/";//地图请求的路径
	
	public static final String PARKING_ACTION=MAP_SERVICE_URL+"ajax/queryparkingplacecoordinate";//停车诱导的请求路径

	public static final String BACKGROUND_ACTION=MAP_SERVICE_URL+"ajax/searchsurround";//周边搜索的请求路径
	
	public static final String POI_KEY_ACTION=MAP_SERVICE_URL+"ajax/searchpoibykey";//关键字搜索地址 

	public static final String POI_CAT_ACTION=MAP_SERVICE_URL+"ajax/searchpoibycat";//POI分类搜索地址 
	
	public static final String QUERY_BUS_ROUTE_ACTION=MAP_SERVICE_URL+"bus/queryBusRoute";//公交线路查询接口
	
	public static final String QUERY_BUS_STATION_ACTION=MAP_SERVICE_URL+"bus/queryBusStation";//公交站点查询接口-name
	
	public static final String QUERY_BUS_TRANSFER_ACTION=MAP_SERVICE_URL+"bus/queryBusTransfer";//公交换乘接口
	
	public static final String QUERY_SURROUND_BUS_STATION_ACTION=MAP_SERVICE_URL+"bus/querySurroundBusStation";//周边公交站点查询
	
	public static final String QUERY_BUS_STATION_ID_ACTION=MAP_SERVICE_URL+"bus/queryBusRouteByStationId";//公交站点查询接口-id
	
	public static final String QUERY_BUS_LINE_TRACE_URL=MAP_SERVICE_URL+"bus/queryBusRouteCoordinate";	//公交坐标轨迹
	
	public static final String QUERY_ROAD_ACTION=MAP_SERVICE_URL+"ajax/queryroad";//道线路路
	
	public static final String[] POT_CODE_LIST={"0102","0103","0104","0105","0106","0107","0108","0109","0101",
		                                         "0202","0203","0204","0205","0206","0207","0209","0210","0211","0212","0213","0214","0215","0216","0217","0201",
		                                         "0302","0303","0304","0305","0301",
		                                         "0402","0403","0404","0405","0406","0401",
		                                         "0502","0503","0504","0505","0506","0507","0508","0509","0510","0501",
		                                         "0602","0603","0604","0601",
		                                         "0702","0703","0704","0705","0701",
		                                         "0802","0803","0804","0805","0806","0807","0808","0809","0810","0811","0812","0813","0801",
		                                         "0902","0903","0904","0901",
		                                         "1002","1003","1004","1005","1006","1001",
		                                         "1102","1103","1104","1105","1106","1107","1108","1109","1110","1111","1112","1113","1114","1115","1001",
		                                         "1202","1203","1204","1205","1206","1207","1201",
		                                         "1302","1303","1304","1305","1306","1307","1308","1309","1310","1311","1312","1313","1301"};
		                                         
	public static final String[] POT_NAME_LIST={"省市县政府","乡镇街道","部门管理","办事机构","行业组织","市政公用","企事业单位","村级自治机构","其他",
		                                       "火车汽车站","机场","货运","仓储","票务","港口码头","服务区","收费站","加油站","汽车租赁","汽车销售","汽车服务","公路桥","铁路桥","街道","其他",
		                                       "银行","自动取款","保险公司","投资信托","其他",
		                                       "住宅小区","大楼大厦","房产物业","房产公司","房产中介","其他",
		                                       "超市商场","综合市场","家政维修","花艺摄影","水电气","快递通信","律师咨询","公厕","烟酒日杂","其他",
		                                       "星级饭店","一般旅馆","非星级度假村、疗养院","其他",
		                                       "快餐","西餐馆","中餐馆","地方口味与名店","其他",
		                                       "酒吧茶楼","影剧院","书店音像","网吧游艺","公园游乐场","体育场馆","健身活动","娱乐城","洗浴足道","动物园","高尔夫球场","滑冰场","其他",
		                                       "旅游景点","宗教庙宇","旅行社","其他",
		                                       "医院","卫生院","药房保健品","救护车","康复中心","其他",
		                                       "幼儿园","小学","中学","高等院校","职业院校","综合培训","驾校","新闻传媒","研究所","图书馆","文工团","出版社","电台","会议中心","其他",
		                                       "自然村","泉","瀑布","水井","山洞溶洞","山峰","其他",
		                                       "装饰纺织品","五金化工","文体办公","通讯电器","造纸建材","食品加工","建设工程","贸易","技术服务","进出口","农林","印刷","其他"};

	
	public static final String SMART_BUS_QUERY_LINE_BY_LINE_NAME_URL="http://phoneapp.jx96520.com.cn:10100/bus/line/?application=jiaotongju";
	public static final String SMART_BUS_QUERY_NEARBY_BIKE_LOCATION_URL="http://phoneapp.jx96520.com.cn:10100/bike/station/nearby/?application=jiaotongju";
	public static final String SMART_BUS_QUERY_NEARBY_BUS_LOCATION_URL="http://phoneapp.jx96520.com.cn:10100/bus/station/nearby/?application=jiaotongju";
	public static final String SMART_BUS_QUERY_BIKE_STATION_BY_ID_URL="http://phoneapp.jx96520.com.cn:10100/bike/station/";
	public static final String SMART_BUS_QUERY_BUS_ARRIVE_URL="http://phoneapp.jx96520.com.cn:10100/bus/arrive/";
	
	public static final String SMART_BUS_QUERY_BUS_STATION_BY_STATION_NAME="http://phoneapp.jx96520.com.cn:10100/bus/station/";
	public static final String SMART_BUS_QUERY_BUS_STATION_BY_LINE_ID="http://phoneapp.jx96520.com.cn:10100/bus/line/";
	
	public static final String SMART_BUS_QUERY_BUS_STATION_BY_STATION_ID="http://phoneapp.jx96520.com.cn:10100/bus/station/";
	
	public static final String SMART_BUS_QUERY_BIKE_INFO="http://phoneapp.jx96520.com.cn:10100/bike/parkingplacecount/";
	public static final String SMART_BUS_QUERY_BUS_LINE="http://phoneapp.jx96520.com.cn:10100/bus/line/";
	public static final String SMART_BUS_QUERY_BUS_STATION="http://phoneapp.jx96520.com.cn:10100/bus/station/";
	
	public static final String SMART_BUS_QUERY_BUS_TRANSFER="http://phoneapp.jx96520.com.cn:10100/traffictransfer/assembled/new/?application=jiaotongju";

	public static final String USER_NAME="sunland";
	public static final String PASSWORD = "jxcg_parkingfee";
	public static final String NAME_SPACE = "http://service.sunland.com";
	public static final String CHENGGUAN_URL="http://11.0.11.1:7879/Zhjt_GC_TCC_dpl_tcc_proxy/services";
	
	public static final String WSDL_URI = CHENGGUAN_URL+"/IParkingfeeService?wsdl";
	public static final String SERVICE = CHENGGUAN_URL+"/IParkingfeeService";
	
}  
