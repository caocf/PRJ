/***
 * echarts color configuration--------------different color, please modify this
 */
var echarts_color_1="#0000f4";
var echarts_color_2="#30fcfb";
var echarts_color_3="#09fe65";
var echarts_color_4="#980609";
var echarts_color_5="#9f01f6";


/**
 * request model configuration-------------different model ,please modify this
 */
var areaPlan=3;       //region request model
var adminPlan=3;	  //admin request model
var hotPlan=1;			//hot request model
var roadPlan=1;			//road request model
var passPlan=1;			//pass request model
var roadPeriodPlan=1;		//roadPeriod request model

/**
 * web-t traffic eye address------------------different server,please modify this
 */
//var localaddress="http://10.22.113.166:8080/jxtpi/";
//var localaddress="http://61.153.49.246:8090/jxtpi/";
var localaddress="http://115.231.73.253:80/";
/**配置默认路况地图地址*/
var TRAFFIC_PIV_URL = localaddress+"tpimap/";

/**
 * basePath url------------------different server,please modify this
 */
//var path="http://221.181.65.46:7001/";
//var path="http://61.153.49.246:8090/jxtpi/";
//var path="http://115.159.64.244:8080/jxtpi/";//生产环境
var path="http://localhost:8080/jxtpi/";//开发环境
//var path="http://192.168.1.112:8080/jxtpi/";//测试环境
//var path="http://115.159.64.244:8080/jxtpi/";//腾云测试环境
//var path="http://61.153.49.246:8080/";
//var path="http://115.231.73.253:8091/";