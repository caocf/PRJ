// 1全局
var DEFAULT_TIME="1970-01-01 00:00:00";//缆线种类
//统计
var STATISTIC_MONTH = 1;// 月统计
var STATISTIC_SEASON = 2;// 季度统计
var ATTACH_IMG = 1; //图片
var ATTACH_AUDIO = 2; //音频
var  ATTACH_VIDEO = 3;//视频
var ATTACH_FILE = 4;//文件
// 用户状态
var USTATUS_ENABLE = 1;// 正常
var USTATUS_DISABLE = 2;// 禁用
var USTATUS_DELETE = 3;// 删除
// 操作状态
var OP_ADD = 1;// 添加
var OP_UPDATE = 2;// 更新
var OP_DELETE = 3;// 刪除
var OP_QUERY = 4;// 更新
//观测站分类
var GCZ_JG = 1;// 激光
var GCZ_RG = 2;// 人工

//行政区划
var XZQH_KINDS = 1;//分类行政区划
// 航道
var APP_HDAO = 7;// 航道
// 航段
var APP_HDUAN = 8;// 航段
//附属物
var APP_NAVIGATIONMARK = 9;// 航标
var APP_BRIDGE = 10;// 桥梁
var APP_AQUEDUCT = 11;// 渡槽
var APP_CABLE = 12;// 缆线
var APP_PIPELINE = 13;// 管道
var APP_TUNNEL = 14;// 隧道
var APP_KYDOCK = 15;// 客运码头
var APP_SHIPYARD = 16;// 船厂
var APP_TAKEOUTFALL = 17;// 取排水口
var APP_HYDROLOGICALSTATION = 18;// 水文站
var APP_HYDOCK = 19;// 货运运码头
var APP_MANAGEMENTSTATION = 20;// 管理站
var APP_SERVICEAREA = 21;// 服务区
var APP_MOORINGAREA = 22;// 锚泊区
var APP_HUB = 23;// 枢纽
var APP_DAM = 24;// 坝
var APP_REGULATIONREVEMENT = 28;// 整治护岸
var APP_LASEROBSERVATION = 25;// 激光流量观测点
var APP_VIDEOOBSERVATION = 26;// 视频观测点
var APP_BOLLARD = 27;// 系缆桩
var APP_MANUALOBSERVATION = 29;// 人工流量观测点
var APP_GWDOCK = 30;// 公务码头
//项目类型
var XZXK_QL =1 ;//项目类型 桥梁
var XZXK_LX =2 ;//项目类型 缆线
var XZXK_GX =3 ;//项目类型 管线
var XZXK_SD =4 ;//项目类型 隧道
var XZXK_QPSK =5 ;//项目类型 取排水口
var XZXK_ZB =6 ;//项目类型 闸坝

//字典 字段类型
//去排水口
var QPSKLX="qpsklx";//水口类型
//码头
var MTJGLX="mtjglx";//结构类型
//全局
var SSSJBH="sssjbh";//所属市级编号
var JSDJ="jsdj";//技术等级
//航道航段
var FJDLB="fjdlb";//分界点类别
//航段
var HDSX="hdsx";//航段属性
var HDSYGLLX="hdsygllx";//水域管理类型
var HDFJHDLX="hdfjhdlx";//分界航段类型
//航标
var HBLX="hblx";//航标类型
var HBJG="hbjg";//航标结构
var HBDYLB="hbdylb";//电源类别
var HBZCFS="hbzcfs";//支撑方式
//坝
var BLX="blx";//坝类型
//枢纽
var SNXS="snxs";//型式
var SNTHLX="snthlx";//通航类型
var SNGCSSWZ="sngcsswz";//过船设施位置

//缆线
var LXZL="lxzl";//缆线种类
//专项工程
var ZXGCZT="zxgczt";//专项工程状态

// 2返回值错误代码
// 用戶
var RESULT_USER_NOTEXIST = 1;// 用户不存在
var RESULT_USER_DISABLE = 2;// 用户已禁用
var RESULT_USER_DELETE = 3;// 用户已删除
var RESULT_USER_PASSERROR = 4;// 密码错误
// 用戶信息
var RESULT_USERINFO_OLDPASSERROR = 5;// 原密码错误
var RESULT_USERINFO_USEREXISTED = 6;// 用户已存在
var RESULT_USER_NOTDPT = 7;// 用户管理机构不存在

// 机构
var RESULT_DPT_OTHERDPT = 8;// 该管理机构下存在未删除机构
var RESULT_DPT_OTHERUSER = 9;// 该管理机构下存在未删除用户

var RESULT_USER_NOTXZQH = 10;//用户行政区划不存在
var RESULT_USER_NOTDPTRELA = 11;//机构关联不存在
var RESULT_USER_CHILDXZQH = 12;//存在子行政区划

//航段
var RESULT_HDUAN_NOTEXIST = 12;// 航段不存在
var RESULT_HDUAN_EXISTED = 13;//航段已存在
var RESULT_HDAO_NOTEXIST = 14;// 航道不存在
var RESULT_HDAO_EXISTED = 15;// 航道已存在
//附属物
var RESULT_APP_EXISTBH = 16;//编号已存在
var RESULT_APP_NOTEXIST = 17;//附属物不存在
//附件
var RESULT_ATTACH_NOTEXIST = 18;//附件不存在
//专项工程
var RESULT_PROJ_NOTEXIST = 19;//专项工程不存在
//骨干台账
var RESULT_MAIN_NOTEXIST = 20;//骨干台账不存在
//支线台账
var RESULT_BRANCH_NOTEXIST = 21;//支线台账不存在
//流量
var RESULT_TRAFFIC_NOTEXIST = 22;//流量监测不存在
//航政
var RESULT_XZXK_NOTEXIST = 23;//行政许可不存在
var RESULT_THLZ_NOTEXIST = 24;//通航论证不存在


/**
 * 验证码 change by 庄佳彬 at 2017-03-28
 */
var VERIFY_CODE = "verifyCode";
var VERIFY_CODE_ERROR = 25;