package com.channel.bean;

/**
 * 常量类 记得同步js
 *
 * @author Administrator
 */
public class Constants {

    public static final int ATTACH_IMG = 1; //图片
    public static final int ATTACH_AUDIO = 2; //音频
    public static final int ATTACH_VIDEO = 3;//视频
    public static final int ATTACH_FILE = 4;//文件
    // 部门类别
    public static final int DPT_SHENJU = 0; //省局
    public static final int DPT_SHIJU = 1; //市局
    public static final int DPT_CHU = 2; //处、地方局
    public static final int DPT_ZHAN = 3;//站

    // 行政区划类别
    public static final int XZQH_SHEN = 1;//省
    public static final int XZQH_SHI = 2;//市
    public static final int XZQH_QUXIAN = 3;//区县

    // 1全局
    public static final String DEFAULT_TIME = "1970-01-01 00:00:00";// 缆线种类
    //统计
    public static final int STATISTIC_MONTH = 1;// 月统计
    public static final int STATISTIC_SEASON = 2;// 季度统计
    // 用户状态
    public static final int USTATUS_ENABLE = 1;// 正常
    public static final int USTATUS_DISABLE = 2;// 禁用
    public static final int USTATUS_DELETE = 3;// 删除
    // 操作状态
    public static final int OP_ADD = 1;// 添加
    public static final int OP_UPDATE = 2;// 更新
    public static final int OP_DELETE = 3;// 刪除
    public static final int OP_QUERY = 4;// 更新
    //观测站分类
    public static final int GCZ_JG = 1;// 激光
    public static final int GCZ_RG = 2;// 人工

    // 行政区划分类
    public static final int XZQH_KINDS = 1;// 分类行政区划
    // 航道
    public static final int APP_HDAO = 7;// 航道
    // 航段
    public static final int APP_HDUAN = 8;// 航段
    // 附属物
    public static final int APP_NAVIGATIONMARK = 9;// 航标
    public static final int APP_BRIDGE = 10;// 桥梁
    public static final int APP_AQUEDUCT = 11;// 渡槽
    public static final int APP_CABLE = 12;// 缆线
    public static final int APP_PIPELINE = 13;// 管道
    public static final int APP_TUNNEL = 14;// 隧道
    public static final int APP_KYDOCK = 15;// 客运码头
    public static final int APP_SHIPYARD = 16;// 船厂
    public static final int APP_TAKEOUTFALL = 17;// 取排水口
    public static final int APP_HYDROLOGICALSTATION = 18;// 水文站
    public static final int APP_HYDOCK = 19;// 货运运码头
    public static final int APP_MANAGEMENTSTATION = 20;// 管理站
    public static final int APP_SERVICEAREA = 21;// 服务区
    public static final int APP_MOORINGAREA = 22;// 锚泊区
    public static final int APP_HUB = 23;// 枢纽
    public static final int APP_DAM = 24;// 坝
    public static final int APP_REGULATIONREVEMENT = 28;// 整治护岸
    public static final int APP_LASEROBSERVATION = 25;// 激光流量观测点
    public static final int APP_VIDEOOBSERVATION = 26;// 视频观测点
    public static final int APP_BOLLARD = 27;// 系缆桩
    public static final int APP_MANUALOBSERVATION = 29;// 人工流量观测点
    public static final int APP_GWDOCK = 30;// 公务码头
    //项目类型
    public static final int XZXK_QL = 1;//项目类型 桥梁
    public static final int XZXK_LX = 2;//项目类型 缆线
    public static final int XZXK_GX = 3;//项目类型 管线
    public static final int XZXK_SD = 4;//项目类型 隧道
    public static final int XZXK_QPSK = 5;//项目类型 取排水口
    public static final int XZXK_ZB = 6;//项目类型 闸坝
    // 字典 字段类型
    // 去排水口
    public static final String QPSKLX = "qpsklx";// 水口类型
    // 码头
    public static final String MTJGLX = "mtjglx";// 结构类型
    // 全局
    public static final String SSSJBH = "sssjbh";// 所属市级编号
    public static final String JSDJ = "jsdj";// 技术等级
    // 航道航段
    public static final String FJDLB = "fjdlb";// 分界点类别
    // 航段
    public static final String HDSX = "hdsx";// 航段属性
    public static final String HDDJ = "hddj";// 航段等级
    public static final String HDSYGLLX = "hdsygllx";// 水域管理类型
    public static final String HDFJHDLX = "hdfjhdlx";// 分界航段类型
    // 航标
    public static final String HBLX = "hblx";// 航标类型
    public static final String HBJG = "hbjg";// 航标结构
    public static final String HBDYLB = "hbdylb";// 电源类别
    public static final String HBZCFS = "hbzcfs";// 支撑方式
    // 坝
    public static final String BLX = "blx";// 坝类型
    // 枢纽
    public static final String SNXS = "snxs";// 型式
    public static final String SNTHLX = "snthlx";// 通航类型
    public static final String SNGCSSWZ = "sngcsswz";// 过船设施位置
    // 缆线
    public static final String LXZL = "lxzl";// 缆线种类
    // 专项工程
    public static final String ZXGCZT = "zxgczt";// 专项工程状态

    // 2返回值错误代码
    // 用戶
    public static final int RESULT_USER_NOTEXIST = 1;// 用户不存在
    public static final int RESULT_USER_DISABLE = 2;// 用户已禁用
    public static final int RESULT_USER_DELETE = 3;// 用户已删除
    public static final int RESULT_USER_PASSERROR = 4;// 密码错误
    // 用戶信息
    public static final int RESULT_USERINFO_OLDPASSERROR = 5;// 原密码错误
    public static final int RESULT_USERINFO_USEREXISTED = 6;// 用户已存在
    public static final int RESULT_USER_NOTDPT = 7;// 用户管理机构不存在
    // 机构
    public static final int RESULT_DPT_OTHERDPT = 8;// 该管理机构下存在未删除机构
    public static final int RESULT_DPT_OTHERUSER = 9;// 该管理机构下存在未删除用户
    public static final int RESULT_USER_NOTXZQH = 10;// 用户行政区划不存在
    public static final int RESULT_USER_NOTDPTRELA = 11;// 机构关联不存在
    public static final int RESULT_USER_CHILDXZQH = 12;//存在子行政区划
    // 航段
    public static final int RESULT_HDUAN_NOTEXIST = 12;// 航段不存在
    public static final int RESULT_HDUAN_EXISTED = 13;// 航段已存在
    public static final int RESULT_HDAO_NOTEXIST = 14;// 航道不存在
    public static final int RESULT_HDAO_EXISTED = 15;// 航道已存在
    // 附属物
    public static final int RESULT_APP_EXISTBH = 16;// 编号已存在
    public static final int RESULT_APP_NOTEXIST = 17;// 附属物不存在
    // 附件
    public static final int RESULT_ATTACH_NOTEXIST = 18;// 附件不存在
    // 专项工程
    public static final int RESULT_PROJ_NOTEXIST = 19;// 专项工程不存在
    // 骨干台账
    public static final int RESULT_MAIN_NOTEXIST = 20;// 骨干台账不存在
    // 支线台账
    public static final int RESULT_BRANCH_NOTEXIST = 21;// 支线台账不存在
    //流量
    public static final int RESULT_TRAFFIC_NOTEXIST = 22;//流量监测不存在
    //航政
    public static final int RESULT_XZXK_NOTEXIST = 23;//行政许可不存在
    public static final int RESULT_THLZ_NOTEXIST = 24;//通航论证不存在

    /**
     * 验证码 change by 庄佳彬 at 2017-03-28
     */
    public static final String VERIFY_CODE = "verifyCode";
    public static final int VERIFY_CODE_ERROR = 25;



    // 记得同步js
}
