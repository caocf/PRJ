//...定义配置文件
#import <UIKit/UIKit.h>


// WebT GIS服务接口地址 - 搜索用
#define HTTPURL_GATEWAY_GIS @"http://www.trafficeye.com.cn/fkgis-gateway/"

#define HTTPURL_Sysfkgis_gatewayNew @"http://www.trafficeye.com.cn/rpe/?"

#define HTTPURL_Sysfkgis_stepWalk @"http://www.trafficeye.com.cn/stepwalk/?"
//...中文瓦片地图地址
//#define HTTPURL_MAP_CN    @"http://mobile.trafficeye.com.cn/map_day/"
//#define HTTPURL_MAP_CN      @"http://www.trafficeye.com.cn/fmapimg_chi_day/"
#define HTTPURL_MAP_CN  @"http://115.231.73.253/fmapimg_chi_bigfont/"
//...英文瓦片地图地址
//#define HTTPURL_MAP_EN    @"http://mobile.trafficeye.com.cn/map_day/"
#define HTTPURL_MAP_EN      @"http://www.trafficeye.com.cn/fmapimg_eng_day/"
//...路况地图地址
#define HTTPURL_MAP_DTM     @"http://www.trafficeye.com.cn/dtmimg"
//#define HTTPURL_MAP_DTM     @"http://115.159.5.167:8090/dtmimg/"
//...轨迹接口地址



NSString *Sysfkgis_gateway = HTTPURL_GATEWAY_GIS; //...搜索
NSString *Sysfkgis_gatewayNew = HTTPURL_Sysfkgis_gatewayNew; //...驾车搜索
NSString *Sysfkgis_stepWalk = HTTPURL_Sysfkgis_stepWalk;   //...步行搜索
NSString *Sysfmappmg_cn = HTTPURL_MAP_CN;    //...中文瓦片地图
NSString *Sysfmappmg_en = HTTPURL_MAP_EN;    //...英文瓦片地图
NSString *Sysdtmimg = HTTPURL_MAP_DTM;        //...路况
NSString *SysPassable;
NSString *Systrack;         //...轨迹