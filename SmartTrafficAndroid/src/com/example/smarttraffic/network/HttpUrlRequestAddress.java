package com.example.smarttraffic.network;


/**
 * 统一url地址管理类
 * @author Administrator zhou
 *
 */
public class HttpUrlRequestAddress 
{
//	public static final String BASE_URL="http://192.168.1.251/SmartTraffic";
	//public static final String BASE_URL="http://115.231.73.254/SmartTraffic";
	//public static final String BASE_URL="http://www.jxjtj.gov.cn:8888/SmartTraffic";
	public static final String BASE_URL="http://zhcx.jxjtj.gov.cn/SmartTraffic";
	
//	public static final String BASE_URL="http://112.11.131.238/SmartTraffic";
//	public static final String BASE_URL="http://192.168.1.108:8080/SmartTraffic";
//	public static final String BASE_URL="http://192.168.1.200:8080/SmartTrafficServer";
	
	public static final String BUS_REQUEST_TICKET_URL= BASE_URL+"/GetBusTickets";
	public static final String BUS_REQUEST_NO_URL=BASE_URL+"/GetBusCodeByBusCode";
	public static final String BUS_REQUEST_NO_WITHOUT_REAL_INFO_URL=BASE_URL+"/GetBusCodeByBusCode";
	public static final String BUS_REQUEST_STATION_URL=BASE_URL+"/xml/station.xml";
	
	public static final String TRAIN_REQUEST_STATION_TICKET_URL=BASE_URL+"/xml/trainTickets.xml";
	public static final String TRAIN_REQUEST_NO_URL=BASE_URL+"/GetTrainCode";
	public static final String TRAIN_REQUEST_NO_WITHOUT_REAL_INFO_URL=BASE_URL+"/GetTrainCode";
	public static final String TRAIN_REQUEST_STATION_URL=BASE_URL+"/GetTrainSchedule";
	
	public static final String AIR_REQUEST_STATION_TICKET_URL=BASE_URL+"/GetAirSchedule";
	public static final String AIR_REQUEST_STATION_SUGGESTION_URL=BASE_URL+"/GetAirCity";


	
	public static final String NEWS_REQUES_URL=BASE_URL+"/GetListNews";
	public static final String NEWS_DETAIL_REQUES_URL=BASE_URL+"/GetDetailNews";
	
	public static final String DRIVING_SCHOOL_REQUES_URL=BASE_URL+"/GetDrivingSchool";
	public static final String DRIVING_SCHOOL_REQUES_BY_NAMR_URL=BASE_URL+"/GetDrivingSchoolByName";
	
	
	public static final String CAR_REPAIR_REQUES_URL=BASE_URL+"/GetCarRepair";
	public static final String CAR_REPAIR_REQUES_NAME_URL=BASE_URL+"/GetCarRepairByName";
	
	
	public static final String UPDATE_GET_VERSION=BASE_URL+"/queryLastVersion";
	public static final String UPDATE_DOWNLOAD_VERSION=BASE_URL+"/downLastApp";
	
	public static final String SUGGESTION_SUBMIT_URL=BASE_URL+"/SaveSuggestion";
	public static final String COMPLAIN_SUBMIT_URL=BASE_URL+"/SaveComplain";
	
	public static final String DEBUG_SUBMIT_URL=BASE_URL+"/SaveDebug";
	
	public static final String TRAFFIC_VALUE_URL="http://115.231.73.253/jxtpi/cservice/queryAreaTPIByAreaName?plan=3&areaName=%E5%85%A8%E8%B7%AF%E7%BD%91";
	public static final String WEATHER_URL=BASE_URL+"/TodayWeather";
	
	public static final String WEATHER_BAIDU_URL="http://apistore.baidu.com/microservice/weather?citypinyin=jiaxing&qq-pf-to=pcqq.c2c";
	
	public static final String USER_LOGIN_URL=BASE_URL+"/appLogin";
	public static final String USER_REGISTER_URL=BASE_URL+"/appPhoneIsExisted";
	public static final String USER_REGISTER_VERIFY_URL=BASE_URL+"/appSendMessage";
	public static final String USER_SAVE_USER_INFO_URL=BASE_URL+"/appAddUser";
	public static final String USER_EDIT_USER_INFO_URL=BASE_URL+"/appEditUserInfo";
	
	public static final String USER_FORGET_PASSWORD_1=BASE_URL+"/forgetPassword";
	public static final String USER_FORGET_PASSWORD_2=BASE_URL+"/appResetPass";
	
	public static final String USER_EDIT_PASSWORD=BASE_URL+"/appEditPassword";
	
	public static final String AIR_SEARCH_NO_URL=BASE_URL+"/GetAirByAirno";
	public static final String AIR_SEARCH_NO_REAL_URL=BASE_URL+"/GetRealAirByAirno";
	
	public static final String AIR_SEARCH_NO_STATION_URL=BASE_URL+"/GetStationInfoByAirno";
	
	public static final String TRAIN_SEARCH_NO_STATION_URL=BASE_URL+"/GetStationInfoByTrainno";
	
	public static final String BUS_SEARCH_NO_STATION_URL=BASE_URL+"/GetStationInfoByBusNo";
	
	public static final String TRAIN_SEARCH_NO_TICKETS_URL=BASE_URL+"/GetTicketsByTrainNoAndStartEnd";
	
	public static final String BIKE_SEARCH_URL=BASE_URL+"/GetBikes";
	
	public static final String SMART_BUS_LINE_SUUGESTION_URL=BASE_URL+"/QueryOriginBusLine";
	public static final String SMART_BUS_STATION_SUUGESTION_URL=BASE_URL+"/QueryOriginBusStation";
	public static final String SMART_BUS_CIRCLE_URL=BASE_URL+"/SearchCircleByID";
	public static final String SMART_BUS_CIRCLE_BY_NAME_URL=BASE_URL+"/SearchPointByName";
	public static final String SMART_BUS_STATION_BY_ID_URL=BASE_URL+"/QueryOriginBusStationByID";
	public static final String SMART_BUS_LINE_BY_ID_URL=BASE_URL+"/QueryOriginBusLineByID";
	
	public static final String SMART_BUS_STATION_NEARBY_STATION_URL=BASE_URL+"/QueryOriginNearByBusStation";
	public static final String SMART_BUS_STATION_NEARBY_BIKE_URL=BASE_URL+"/QueryOriginNearByBikeStation";
	
	public static final String SMART_BUS_STATION_AVERAGE_BIKE_URL=BASE_URL+"/QueryBikeAverage";
	
	public static final String SMART_BIKE_STATION_BY_ID_URL=BASE_URL+"/QueryOriginBikeStationByID";
	public static final String SMART_BIKE_STATION_BY_NAME_URL=BASE_URL+"/QueryOriginBikeStation";
	public static final String SMART_BIKE_STATION_REAL_BY_ID_URL=BASE_URL+"/QueryOriginBikeParkingCount";
	
	public static final String SMART_BUS_STATION_REAL_INFO_URL=BASE_URL+"/QueryOriginBusArrive";
	
	public static final String SMART_BUS_NEWS_LIST_URL=BASE_URL+"/GetBusListNews";
	public static final String SMART_BUS_NEWS_DETAIL_URL=BASE_URL+"/GetBusDetailNews";
	
	//public static final String SMART_BUS_QUERY_ORIGN_LINE_TRACK_URL=BASE_URL+"/QueryBaseLineTrace";
	public static final String SMART_BUS_QUERY_ORIGN_LINE_TRACK_URL=BASE_URL+"/QueryOriginLineTrack";
	
	public static final String SMART_BUS_QUERY_BUS_STATION_TICKETES_BY_STATION_URL=BASE_URL+"/GetBusTicketsByStationName";

	public static final String SMART_BUS_QR_URL=BASE_URL+"/QueryStationIDByQRCodeFromDB";
	
	public static final String CALL_HELP_URL=BASE_URL+"/callHelp";
	
	public static final String SMART_BUS_TRANSFER=BASE_URL+"/QueryOriginTrafficTrasfer";
	
	public static final String NEWS_LIST_URL=BASE_URL+"/QueryTzxx";
	
	public static final String NEWS_JTGZ_LIST_URL=BASE_URL+"/QueryJTGZList";
	
	public static final String PARKING_LOCATION_URL=BASE_URL+"/searchParkingByLocationCircle";
	
	public static final String FAVOR_ADD_LINE_URL=BASE_URL+"/APPSaveLineFavor";
	public static final String FAVOR_ADD_STATION_URL=BASE_URL+"/APPSaveStationFavor";
	public static final String FAVOR_ADD_TRANSFER_URL=BASE_URL+"/APPSaveTransferFavor";
	public static final String FAVOR_FIND_LINE_URL=BASE_URL+"/APPQueryAllLineFavor";
	public static final String FAVOR_FIND_STATION_URL=BASE_URL+"/APPQueryAllStationFavor";
	public static final String FAVOR_FIND_TRANSFER_URL=BASE_URL+"/APPQueryAllTransferFavor";
	public static final String FAVOR_DELETE_LINE_URL=BASE_URL+"/APPDeleteForLine";
	public static final String FAVOR_DELETE_TRANSFER_URL=BASE_URL+"/APPDeleteForTransfer";
	public static final String FAVOR_DELETE_STATION_URL=BASE_URL+"/APPDeleteForStation";
	
	public static final String FAVOR_ADD_BIKE_STATION_URL=BASE_URL+"/APPSaveBikeStationFavor";
	public static final String FAVOR_FINE_BIKE_STATION_URL=BASE_URL+"/APPQueryAllBikeStationFavor";
	public static final String FAVOR_DELETE_BIKE_STATION_URL=BASE_URL+"/APPDeleteBikeForStation";
	
	public static final String STATEMENT_URL=BASE_URL+"/QueryStatement";
	public static final String SUGGESTION_ADD_URL=BASE_URL+"/addAppSuggestion";		
	
	public static final String LIVENESS_ADD_URL=BASE_URL+"/autoAddUserLiveness";
	
	
	public static final String TAXI_NEARBY_URL=BASE_URL+"/searchTaxtByLocationCircle";
	
	public static final String TAXI_DETAIL_URL=BASE_URL+"/searchTaxiDetailByName";
	public static final String TAXI_PHONE_URL=BASE_URL+"/queryTaxiPhoneById";
}
