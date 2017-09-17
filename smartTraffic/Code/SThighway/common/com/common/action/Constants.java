package com.common.action;
//zoumy xiugai
//自定义常量类
public class Constants {
	
	/**
	 * 桥梁搜查字段属性
	 *//*
	public static String[] QL_PARAM_NAME= new String[]{  //去掉属性中的标志编码
	 "桥梁代码", "路线代码", "路线简称", "桥梁编号","行政区划", "行政区划","桥梁名称","桥梁中心桩号","管理单位名称", "桥梁经度", "桥梁纬度", "桥梁性质", "收费性质", "桥梁所在地名","跨越地物类型", "跨越地物名称",
	 "桥梁用途分类", "按使用年限分", "桥梁跨径分类", "技术状况评定", "评定日期", "最近定期检查日期","已采取交通管制措施","立交桥类别","是否公铁立交","立交桥跨越各路线","立交桥连通各路线","管养单位名称","管养单位所属行业类别",
	 "监管单位名称","主要病害位置", "主要病害描述","桥梁全长（米）","多跨径总长（米）","单孔最大跨径（米）", "桥梁跨径组合（孔*米）", "桥梁全宽（米）","桥面净宽（米）","桥梁孔数（孔）","主桥孔数（孔）", "主桥主跨（米）","主桥边跨（米）",
	"前引桥长（米）","后引桥长（米）","行车道宽（米）","人行道宽（米）","桥梁高度（米）","桥面标高（米）","桥下净空（米）", "桥下净高（米）", "匝道（平米）","匝道（公里）", "上部结构类型","上部结构材料","下部结构形式","下部材料类型",
	 "桥墩类型","桥台类型","墩台防撞设施类型", "基础结构型式", "引桥结构型式","支座类型", "伸缩缝类型", "拱桥矢跨比", "桥面纵坡","桥面线型","桥面全宽","弯坡斜特征","桥位地形","桥面铺装类型", "主桥截面型式",
	"引桥截面型式", "设计荷载等级", "桥梁验算荷载","抗震等级", "河床地质及纵坡", "河床最低标高（米）", "设计洪水频率（年）", "总造价（万元）","设计洪水位", "常年水位","历史最高洪水位", "通航等级", "设计单位名称", "建设单位名称",
	 "施工单位名称","监理单位名称", "通车日期", "开工日期","竣工日期","修建年度（纳入统计年度）","备注","改建变更日期", "改造完工日期", "改建变更原因", "变更原因说明"	};

	*//**
	 * 路线搜查字段属性
	 *//*
	public static String[] LX_PARAM_NAME= new String[]{ //去掉属性中的标志编码
		"路线代码",  "路段编号",  "管理单位名称", "路线简称",  "路线地方名称","路段起点桩号",   "路段止点桩号", "里程",  "路段起点名称",  "路段止点名称",
		"起点是否为分界点", "起点分界点类别","止点是否为分界点","止点分界点类别",   "行政区划",
		 "行政区划","断链类型","技术等级", "面层类型","路基宽度（米）","路面宽度（米）","车道分类", "设计车速","设计小时交通量","地貌","涵洞（道）","涵洞（米）","是否断头路路段","是否晴雨通车",
		 "是否收费路段","是否城管路段", "是否一幅高速","备注","重复路段管理单位名称","重复路线代码","重复路段起点桩号","重复路段止点桩号","管养单位名称","管养单位所属行业类别","养护类型（按时间分）","养护类型（按资金来源分）",
		 "养护类型（按养护人员分）","养护里程（公里）",  "可绿化里程（公里）","已绿化里程（公里）","已实施GBM里程（公里）","已实施文明样板路的里程（公里）","开工日期","竣工日期","验收日期",
		 "通车日期","修建年度（纳入统计年度）","改建变更日期","改建变更原因","变更原因说明" 
		
	};
	
	*//**
	 * 服务区搜查字段属性
	 *//*
	public static String[] FWQ_PARAM_NAME= new String[]{ //去掉属性中的服务区编号
		"服务区名称","所属公司","线路名称","上行进口桩号","下行进口桩号","上行出口桩号","下行出口桩号","加油设施","餐饮设施"
		,"住宿设施","购物设施","车辆维修设施","管理中队桩号","治超站桩号","管理交警桩号"
	};
	
	*//**
	 *收费站搜查字段属性
	 *//*
	public static String[] SFZ_PARAM_NAME= new String[]{ //去掉属性中的服务区编号
		"收费站名称","收费站票据站名","所属公司","线路名称","收费站类型名称","收费站类型名称","进口etc车道数量","进口人工车道数量","主线上行进口桩号","主线下行进口桩号"
		,"出口etc车道数量","出口人工车道数量","主线上行出口桩号","主线下行出口桩号","出口指向"
	};
	
	*//**
	 *标志标线搜查字段属性
	 *//*
	public static String[] BZBX_PARAM_NAME= new String[]{ //去掉属性中的服务区编号
		"行政区划","线路名称","标志标线类型","公路等级","标志位置(左)","标志位置(中)","标志位置(右)","版面内容图案","版面内容数量","版面尺寸"
		,"支撑形式及规格","安装时间","制作安装单位","管理单位","备注"
	};
	
	*//**
	 *公路站基本信息搜查字段属性
	 *//*
	public static String[] GLZJBXX_PARAM_NAME= new String[]{ //去掉属性中的服务区编号
		 "组织机构",  "编码","名称", "备注", "单位地址", "联系电话", "传真","负责人","email","修建日期", "照片id"
	};
	
	*//**
	 *公路站详细信息搜查字段属性
	 *//*
	public static String[] GLZXXXX_PARAM_NAME= new String[]{ //去掉属性中的服务区编号
		 "年份","共养护里程","桥梁座数","桥梁长度", "隧道座数", "隧道长度", "绿化里程", "绿化率","国道", "省道",
		 "县乡道", "高速","一级","二级","三级","四级", "等外", "沥青路","水泥路","砂石路", "干线优良指标",
		  "干线好路率", "支线优良指标","支线好路率", "站内共有机具","机具原值", "拖拉机","压路机","小翻斗车","沥青洒布车","装载机",
		  "洒水车", "清扫车", "除草机","清缝机","粒料拌和机","灌缝机","巡查车","站内共有职工","定额","固定工", "临时工","男职工", "女职工","35岁以下",
		 "35至45岁","45岁以上", "大中专","高中", "初中", "党员", "团员","本站全年养护经费", "平均年公里成本","备注","管养示意图片Id","绿化面积", "生产用房(场地)",
		 "生活用房","占地面积", "下达优良里程指标", "下达优良里程优良率", "力争优良里程指标",
		 "力争优良里程优良率", "国省道优良里程","县乡道优良里程", "一季度指标", "二季度指标","三季度指标", "四季度指标"
	};
	
	*//**
	 *交调站信息搜查字段属性
	 *//*
	public static String[] JD_PARAM_NAME= new String[]{ //去掉属性中的服务区编号
		"调查站名称", "年份","管理机构", "行政区划", "路线","调查站类型编号","建站时间","调查方法编号", "停测月份", "桩号(km)",
		"起点桩号(km)","止点桩号(km)","起点名称", "止点名称","上行名称","下行名称","代表里程(km)","技术等级编号","技术等级唯一", "一级里程(km)",
		 "二级里程(km)", "三级里程(km)","四级里程(km)","高速里程(km)", "等外里程(km)", "车道特征编号","路面类型编号","路面宽度(米)","设计时速(km/h)","地貌", 
		 "适应交通量","供电方式编号",
		 "通讯方式编号", "公路功能编号", "调查人员数量","备注","调查站状态", "比重起点桩号(km)", "比重止点桩号(km)","比重代表里程(km)"
	};*/
	
	/**
	 * 行政处罚部门编号与名称常量
	 */
	
	public static String LEGAL_OUGUID="001008004008089";
	public static String LEGAL_OUNAME="公路管理局";
	public static String LEGAL_REGIONNAME="嘉兴";
	public static int XZCFLY_ZC = 1;
	/**
	 * 行政处罚状态枚举
	 * @author admin
	 *
	 */
	public enum LegalState {
	       INSERT (1), UPDATE (2), DELETE (3),SUCCESS(0); 
	       private int state ;
	       private LegalState( int _state) {
	           this . state = _state;
	       }
	       @Override
	       public String toString() {
	           return String.valueOf ( this.state );
	       }
	       public Integer toInt() {
	           return  this.state;
	       }
	       
	    }
	
	/**
	 * 行政处罚远程状态枚举
	 * @author admin
	 *
	 */
	public enum LegalAccessState {
	       INSERT (1), UPDATE (2), DELETE (2),SUCCESS(0),FAIL(-1); 
	       private int accessstate ;
	       private LegalAccessState( int _state) {
	           this.accessstate = _state;
	       }
	       @Override
	       public String toString() {
	           return String.valueOf ( this.accessstate );
	       }
	       public Integer toInt() {
	           return  this.accessstate;
	       }
	    }
	/**
	 * 同步数据模块简称
	 */
	/**
	 * 公路同步模块简称：gl
	 */
	public static String MODULE_GONGLU = "gl";
	/**
	 * 通阻信息同步模块简称：tz
	 */
	public static String MODULE_TZXX = "tz";
	/**
	 * 交调信息同步模块简称：jd
	 */
	public static String MODULE_JDXX = "jd";
	
	/**
	 * 行政执法同步模块简称：xz
	 */
	public static String MODULE_XZZF = "xz";
	
	/**
	 * 行政执法同步模块简称：zc
	 */
	public static String MODULE_ZCCF = "zc";
	
	/**
	 * 智慧交通同步模块简称：jt
	 */
	public static String MODULE_ZHJT = "jt";
	
	/**
	 * 数据同步：转换步骤新增的标志位：1
	 */
	public static String TRANS_INSERT = "1";
	/**
	 * 数据同步：转换步骤插入更新的标志位：2
	 */
	public static String TRANS_UPDATE = "2";
	
	//系统常量配置信息
	/**
	 * AES数据库加密密钥
	 */
	public static String AESKEY = "gongludata";
	/**
	 * AES记住密码的加密密钥
	 */
	public static String AESKEY_REMEMBER = "shujuzhongxin";
	 /**
	  * 令牌编码长度定义
	  */
	 public static int DLLP_LENGTH = 64;
	 
	 /**
	  * 硬盘图片路径
	  */
	/* public static String IMG_STORE_PATH = "F:/sstext/";  //上传到硬盘*/	 
	 /**
	  * 项目虚拟头像图片路径"HeadPicture/"
	  */
	 public static String IMG_HEADPICTURE_PATH="HeadPicture/";//项目下头像文件保存路径
	 /**
	  * 项目虚拟标志标线图片路径:"BzbxPicture/all_symbol"
	  */
	 public static String IMG_BZBXPICTURE_PATH="BzbxPicture/all_symbol/";//项目下标志标线图片路径
	 
	 /**
	  * 标志标线图片虚拟路径（分类） 命名规则："IMG_BZBXPICTURE_TYPE"+bzbxlxbh(除了5,6种类型)
	  */
	 /**
	  * IMG_BZBXPICTURE_TYPE+1:旅游图片路径
	  */
	 public static String IMG_BZBXPICTURE_TYPE1="BzbxPicture/travel_symbol";
	 /**
	  * IMG_BZBXPICTURE_TYPE+2:指路图片路径
	  */
	 public static String IMG_BZBXPICTURE_TYPE2="BzbxPicture/road_symbol";
	 /**
	  * IMG_BZBXPICTURE_TYPE+3:厂名牌图片路径
	  */
	 public static String IMG_BZBXPICTURE_TYPE3="BzbxPicture/company_symbol";
	 /**
	  * IMG_BZBXPICTURE_TYPE+4:图片路径
	  */
	 public static String IMG_BZBXPICTURE_TYPE4="BzbxPicture/warn_symbol";
	 /**
	  * IMG_BZBXPICTURE_TYPE+5:桥梁限载牌图片路径（国标路径）
	  */
	 public static String IMG_BZBXPICTURE_TYPE5="BzbxPicture/chinesestandard_symbol";
	 /**
	  * IMG_BZBXPICTURE_TYPE+6:停车让行图片路径（国标路径）
	  */
	 public static String IMG_BZBXPICTURE_TYPE6="BzbxPicture/chinesestandard_symbol";
	 /**
	  * IMG_BZBXPICTURE_TYPE+0:国标图片路径(5,6，其他路径不存在的图片也存在国标里面)
	  */
	 public static String IMG_BZBXPICTURE_TYPE0="BzbxPicture/chinesestandard_symbol";
	 
	 /**
	  * IMG_BZBXPICTURE_TYPE+7:其他图片路径
	  */
	 public static String IMG_BZBXPICTURE_TYPE7="BzbxPicture/other_symbol";
	 /**
	  * 项目虚拟默认头像图片路径:"image/login/userimage.png"
	  */
	 public static String MOREN_HEADPICTURE_NAME="image/login/userimage_big.png";
	 /**
	  * 项目虚拟默认头像小图路径:"image/login/userimage.png"
	  */
	 public static String MOREN_HEADSMALL = "image/login/userimage.png";
	 /**
	  * 项目下导出excel的存储路径:"exceldownload/"
	  */
	 public static String EXCEL_DOWNLOAD_PATH = "exceldownload/";
	 
	 /**
	  * excel生成失败
	  */ 
	 public static int EXCEL_DOWNLOAD_FAIL_CODE = 90;
	 
	 public static String EXCEL_DOWNLOAD_FAIL_INFO = "EXCEL生成失败！";
	 /**
	  * 图片压缩后的 扩展名
	  */
	 public static String HEAD_SMALL_ENDNAME = "_small";
	 /**
	  * 头像高度
	  */
	 public static int SMALL_HEAD_HEIGHT = 120;
	 
	 /**
	  * 头像宽度
	  */
		
	 public static int SMALL_HEAD_WIDTH = 120;
	 
	 /**
	  * 头像限制大小:2M
	  */
	/* public static int HEADPC_COMMITSIZE = 2048;*/
	 public static int HEADPC_COMMITSIZE = 2048;
		//分页信息
		/**
		 * 默认当前页数 :1
		 */
		public static int MOREN_PAGE =1;
		/**
		 * 默认行数:10
		 */
		public static int MOREN_ROWS = 10;
	 
		/**
		 * 列表分割符号","
		 */
		public static String SPLITSYMBOL = ",";
		
		/**
		 * 其他权限页面展示的名字：其他权限
		 */
		public static String OTHER_PERMISSION = "其他权限";
		
		
		/**
		 * 交调数据查询公网地址
		 */
	     public static String TRANSPORTDATA_PUBLIC_URL = "http://218.108.13.69:8888";
		
		/**
		 * 交调数据查询交通业务网接口
		 */
	     public static String TRANSPORTDATA_INNER_URL ="http://10.100.20.40:8888/jd";
	     
	     /**
	      * 交通量报表数据查询接口
	      */
	     public static String TRANSPORTDATA_ACTION_NEWREPORT ="/action/newreport!getSJQBJTLReport.action";
	     
	     /**
	      * 交通量报表数据接口参数2：xzqbs="33040020110903140636263000261480"
	      */
	     public static String TRANSPORTDATA_ACTION_NEWREPORT_PARAM2="33040020110903140636263000261480";
	     
	     /**
	      * 交调--实时拥挤度查询
	      */
	     public static String TRANSPORTDATA_ACTION_TIMECS ="/sum/test!getTimeCSByXzqhbs.action";
	     
	     /**
	      * 交调--实时拥挤度参数1名字
	      */
	     public static String TRANSPORTDATA_ACTION_TIMECS_PARAM1_NAME ="xzqhbs";
	     
	     /**
	      * 交调--实时拥挤度参数1值
	      */
	     public static String TRANSPORTDATA_ACTION_TIMECS_PARAM1_VALUE ="33040020110903140636263000261480";
	     
	     /**
	      * 监控视频接口数据
	      */

	     /**
	      * 监控视频接口：webservice请求接口url:"http://172.20.8.50/services/CmsService?wsdl"
	      */
	     public static String VIDEO_WEBSERVICE_URL="http://172.20.8.50/services/CmsService?wsdl";
	     
	    /**
	     * 监控视频请求参数5-平台url:"http://172.20.11.4"
	     */
	     public static String VIDEO_FLAT_URL="http://172.20.11.4";
	     
	     /**
	      *  监控视频请求参数1-用户名:admin
	      */
	     public static String VIDEO_WS_USERNAME="admin";
	     
	     /**
	      *  监控视频请求参数2-密码:12345
	      */
	     public static String VIDEO_WS_PASSWORD="12345";
	     
	     /**
	      * webservice命名空间："http://webservice.cms.hikvision.com"
	      */
	     public static String VIDEO_WS_TARGETNAME ="http://webservice.cms.hikvision.com";
	     
	//系统通用操作返回信息
    /**
     * 操作成功代码:1:成功
     */
	 public static int  SUCCESS_CODE = 1;
	 /**
	  * 操作成功描述:1:成功
	  */
	 public static String SUCCESS_INFO = "成功";
	 
	 /**
      * 令牌验证错误描述:-1:令牌验证失败,请重新登陆
	  */
	 public static String DLLP_CHECK_FAIL_INFO  = "令牌验证失败，请重新登陆";
	 
	 /**
      * 令牌验证错误代码:-1:令牌验证失败,请重新登陆
	  */
	 public static int DLLP_CHECK_FAIL_CODE  =  -1;   //令牌失效返回码
	 
	 /**
      * 权限验证错误描述:-2:无此权限
	  */
	 public static String AUTHORITY_CHECK_FAIL_INFO  = "对不起，此权限未开放";
	 
	 /**
      * 口令验证错误代码:-2:无此权限,请重新登陆 ，暂定为-2
	  */
	 public static int AUTHORITY_CHECK_FAIL_CODE  =  -2;   //无权限返回码
	 /**
	  * 请求action的参数错误代码:2:请求参数不完整或者为空
	  */
	 public static int REQUEST_PARAM_NULL_CODE = 2;
	 
	 /**
	  * 请求action的参数错误描述:2:请求参数不完整或者为空
	  */	 
	 public static String  REQUEST_PARAM_NULL_INFO = "请求参数不完整或者为空";
	 
	 
	 /**
	  * 必填信息为空错误代码:3.信息填写不完整
	  */
	 public static int WRITE_UNCOMPLETE_CODE = 3;	
	 /**
	  * 必填信息为空错误代码:3.信息填写不完整
	  */
     public static String   WRITE_UNCOMPLETE_INFO = "信息填写不完整";
     
     /**
      * 超级管理员角色编号：1
      */
     public static Integer SUPERMANAGER_JSBH = 1;
     /**
      * 超级管理员人员编号：0
      */
     public static Integer SUPERMANAGER_RYBH = 1;
     /**
      * 超级管理员姓名
      */
     public static String SUPERMANAGER_RYNAME = "admin%";
     /**
      * 超级管理员角色名字
      */
     public static String SUPERMANAGER_JSNAME = "超级管理员";
	 
	 public static int NAME_REPEAT_CODE = 5;
	 public static String NAME_REPEAT_INFO="名字重复";
	 
     

	 //登陆信息接口返回值与返回信息
        /**
         * 登陆错误信息代码:4.用户名不存在
         */
		 public static int  LOGIN_NAME_ERROR_CODE = 4;
	     /**
	      * 登陆错误信息描述:4.用户名不存在
	      */
		 public static String LOGIN_NAME_ERROR_INFO = "用户名不存在";
		 /**
		  * 登陆错误信息代码:5.密码错误
		  */
		 public static int  LOGIN_PASS_ERROR_CODE = 5;
		 /**
		  * 登陆错误信息描述:5.密码错误
		  */
		 public static String LOGIN_PASS_ERROR_INFO = "密码错误";
		 /**
		  * 登陆错误信息代码:6.用户名不能为空
		  */
		 public static int  LOGIN_NAME_NULL_CODE = 6; 
		 /**
		  * 登陆错误信息描述:6.用户名不能为空
		  */
		 public static String LOGIN_NAME_NULL_INFO = "用户名不能为空";
		 
		 
		 
	
			
   /**
	 * 管理人员信息 10+
	 */	
		 /**
		  * 管理人员错误信息代码:11:人员不存在
		  */
		 public static int BMRY_ERROR_CODE = 11;
		 /**
		  * 管理人员错误信息描述:11:人员不存在
		  */
		 public static String  BMRY_ERROR_INFO = "人员不存在";
		 
		
	/**
	 * 编辑管理人员返回信息
	 */		
			 public static int  BMRY_SJCHERROR_CODE = 12;
			 public static String  BMRY_SJCHERROR_INFO = "手机长号重复";
			 
			 public static int BMRY_OLDPWERROR_CODE = 13;
			 public static String  BMRY_OLDPWERROR_INFO = "旧密码错误";
			 
			 
			 public static int RYBH_NULL_CODE =15;
			 public static String  RYBH_NULL_INFO = "人员不存在";
			 
			 public static int RYBH_DELETE_ERROR_CODE = 16;
			 public static String RYBH_DELETE_ERROR_INFO = "不能删除自己";
			 
			
			 
			 public static int  BMRY_RYXMERROR_CODE = 18;
			 public static String  BMRY_RYXMERROR_INFO = "人员姓名重复";
			 
	  /**
       *  机构信息 20+
	   */	
			 public static int  JGBH_NULL_CODE = 21;
			 public static String  JGBH_NULL_INFO = "请求机构参数为空";
			 public static int  JGBH_ERROR_CODE = 22;
			 public static String  JGBH_ERROR_INFO = "机构不存在";
			 public static int  XJJGBH_ERROR_CODE = 23;
			 public static String  XJJGBH_ERROR_INFO = "下级机构存在，不能删除";
			 public static int  XJBMBH_ERROR_CODE = 24;
			 public static String  XJBMBH_ERROR_INFO = "下级部门存在，不能删除";
			 public static int JGMC_RENAME_CODE = 25;
			 public static String JGMC_RENAME_INFO ="机构名已存在，,请重新输入";
			 
    /**
     * 部门信息  30+
     */
			 public static int  BMBH_ERROR_CODE = 31;
			 public static String  BMBH_ERROR_INFO = "部门不存在";
			 public static int RY_EXIT_CODE = 32;
			 public static String RY_EXIT_INFO = "有人员关联，不能删除";
			 public static int BMMCC_RENAME_CODE = 30;
			 public static String BMMCC_RENAME_INFO ="部门名称已存在,请重新输入";
			 
	/**
	 * 角色信息  40+
	 */
			 public static int JSMC_RENAME_CODE = 40;
			 public static String JSMC_RENAME_INFO ="角色名称已存在,请重新输入";
			 
			 public static int  JSBH_ERROR_CODE = 41;
			 public static String  JSBH_ERROR_INFO = "角色不存在";
			 public static int RY_EXIT_ERROR_CODE = 42;
			 public static String  RY_EXIT_ERROR_INFO = "有人员相关联，不能删除";
			 
			 public static int SUPERMANAGER_JS_UPDATE_CODE = 43;
			 public static String SUPERMANAGER_JS_UPDATE_INFO = "超级管理员不能编辑";
			 
			 public static int SUPERMANAGER_JS_DELETE_CODE = 44;
			 public static String SUPERMANAGER_JS_DELETE_INFO = "超级管理员不能删除";
			 

			 public static int  JSQXZ_DELETE_CODE=45;
			 public static String  JSQXZ_DELETE_INFO =  "权限或权限组不能都为空，请分配一个权限";
	/**
	 * 权限组信息50+		 
	 */		
			 public static int QXZMC_RENAME_CODE = 40;
	         public static String QXZMC_RENAME_INFO ="权限组名称已存在,请重新输入";
			 public static int  QXZBH_ERROR_CODE = 51;
			 public static String  QXZBH_ERROR_INFO = "权限组不存在";
			 public static int   QXZ_OPERATEDELETE_JSEXIT_CODE = 52;
			 public static int   QXZ_OPERATEDELETE_ZQXERROR_CODE = 53;
			 public static String   QXZ_OPERATEDELETE_JSEXIT_INFO ="该权限组与角色关联，不能删除";
			 public static String   QXZ_OPERATEDELETE_ZQXERROR_INFO = "该权限组有下级权限组，不能删除";
		     
	/**
	 * 标志标线信息60+		 
	 */		 /**
			  * 错误代码：标志标线不存在
			  */
			 public static Integer BZBX_BN_NOEXIT_CODE = 60;
			 /**
			  * 错误信息：标志标线不存在
			  */
			public static String BZBX_BN_NOEXIT_INFO="标志标线不存在"; 
			 

	/**
	 * 操作日志信息 70+
	 */
			/**
			 * 错误代码：70:请求参数不完整，操作日志编号为空
			 */
			public static int CZRZ_BHNONE_CODE =70;
			/**
			 * 错误信息：70:请求参数不完整，操作日志编号为空
			 */
			public static String CZRZ_BHNONE_INFO = "请求参数不完整，操作日志编号为空";
	
	 //删除角色权限信息


	 /**
	  * 服务区信息80+
	  */
			 /**
			  * 错误代码：80:服务区名字重命名，请重新输入
			  */
			public static Integer FWQ_NAME_REPEAT_CODE=80;
			/**
			  * 错误信息：80:服务区名字重命名，请重新输入
			  */
			public static String FWQ_NAME_REPEAT_INFO="服务设施名字已存在，请重新输入";
			
			
	/**
	 * 远程调用视频webservice返回信息
	 */
			public static Integer  VIDEOWS_LOGIN_ERROR_CODE =101;
			public static String VIDEOWS_LOGIN_ERROR_INFO = "远程连接登陆失败";
			
			
}
