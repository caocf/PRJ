//var host = "182.254.143.172";
var host="www.trafficeye.com.cn";
var port1 = "80";//server
var port2 = "80";//html

var scriptName = "wapi.js";
var r = new RegExp("(^|(.*?\\/))(" + scriptName + ")(\\?|$)"),
	s = document.getElementsByTagName('script'),
	src, m, l = "";
for(var i=0, len=s.length; i<len; i++) {
	src = s[i].getAttribute('src');
	if(src) {
	    var m = src.match(r);
	    if(m) {
	        l = m[1];
	        break;
	    }
	}
}
if (l.indexOf("10.66") != -1) {
	if (l.indexOf("10.66.116.253") >= 0) {
		host = "10.66.116.253";
		//port1 = port2 = "80";
	}
	else if (l.indexOf("182.254.143.172") >= 0) {
		host = "182.254.143.172";
		//port1 = port2 = "7001";
	}
}

/**配置默认基础地图地址*/
TILE_PIV_URL = "http://182.254.143.172/fmapimg_chi_day/",
/**配置默认基础地图地址*/
MARKER_PIV_URL = "http://mobile.trafficeye.com.cn/webt_anno/",
/**配置默认路况地图地址*/

/**配置默认历史路况地图地址*/
HIS_TRAF_URL = "http://116.228.55.134:81/dtmimg/history/",
/**配置卫星地图地址*/
SATELITE_PIV_URL = "http://sis.siweidg.com/",
/**配置卫星地图注记地址*/
SATELITE_MARKER_URL = "http://wts.siweisat.com/",
/**配置英文地图地址**/
ENG_PIV_URL = "http://182.254.143.172/fmapimg_eng_day/",
//地图最大等级
MAX_ZOOM = 17,
//地图最小等级
MIN_ZOOM = 10,
//默认城市码
DEFAULT_CITY_CODE = "330400",
//默认城市名
DEFAULT_CITY_NAME = "嘉兴",
//默认审图号
DEFAULT_COPY_RIGHT = "© 2013 CenNavi - GS(2013)6011 - 京ICP备09012116号 - Data ©",
//业务代码
BIZ_CODE = "TYMON",
//控制超链接显示
LINK_IS_SHOW = true,
//地图访问方式[0：直接访问，1：参数访问]
MAP_ACCESS_OPTION = 0;

var 
CURSOR_OPEN = "http://" + host + ":" + port2 + "/WebtAPI/img/openhand.cur",
CURSOR_CLOSE = "http://" + host + ":" + port2 + "/WebtAPI/img/closedhand.cur",
CURSOR_RULER = "http://" + host + ":" + port2 + "/WebtAPI/img/ruler.cur",
CURSOR_BIG = "http://" + host + ":" + port2 + "/WebtAPI/img/big.cur",
CURSOR_SMALL = "http://" + host + ":" + port2 + "/WebtAPI/img/small.cur",
DEFAULT_GIS_URL2 = "http://" + host + ":" + port1 + "/fkgis-gateway/",
DEFAULT_GIS_URL = "http://" + host + ":" + port1 + "/fkgis-gateway/ere/gis/";

document.write("<script type='text/javascript' charset='utf-8' src='http://" + host + ":" + port2 + "/WebtAPI/ol_map.js'></script>");
document.write("<script type='text/javascript' charset='utf-8' src='http://" + host + ":" + port2 + "/WebtAPI/ol_layers.js'></script>");
document.write("<script type='text/javascript' charset='utf-8' src='http://" + host + ":" + port2 + "/WebtAPI/ol_ctrls.js'></script>");
document.write("<script type='text/javascript' charset='utf-8' src='http://" + host + ":" + port2 + "/WebtAPI/webt_map.js'></script>");
document.write("<script type='text/javascript' charset='utf-8' src='http://" + host + ":" + port2 + "/WebtAPI/webt_ctrls.js'></script>");
document.write("<script type='text/javascript' charset='utf-8' src='http://" + host + ":" + port2 + "/WebtAPI/webt_search.js'></script>");