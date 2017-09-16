package com.huzhouport.map;

public interface BaseMapData
{
	// key
	public static final String	sKey					= "4d8SpCi5Lmgwhyuj0q1nTApC";

	// 默认参数
		public static final float	ZOOMSIZE_DEFAULT		= 12;							// 地图是否比例
		public static final boolean	ENABLECLICK_DEFAULT		= true;						// 地图是否可点击
		public static final boolean	SATeLLITE_DEFAULT		= false;						// 是否卫星图
		public static final boolean	TRAFFIC_DEFAULT			= false;						// 是否交通图
		public static final boolean	INNER_ZOOM_CONTROL		= true;						//

		public static final String	CITY_DEFAULT			= "嘉兴";						// 默认城市
		public static final String  POINT_DEFAULT           = "嘉兴市市政府";
		
		
		// 初始化错误提示
		public static final String	NO_CONTEXT				= "无上下文contex";
		public static final String	WRONG_INI_MANAGER		= "初始化地图管理器失败";
		public static final String	WRONG_NET_LINK			= "您的网络出错了";
		public static final String	WRONG_NET_DATA			= "请输入正确检索条件";
		public static final String	WRONG_KEY_PERMISSION	= "您的key值不正确";
		public static final String	RIGHT_KEY_PERMISSION	= "key值认证成功";

		// 搜索错误提示
		public static final String	NO_FIND_POI				= "未找到结果";
		public static final String	WRONG_FIND_POI			= "查找结果错误";

		public static final String	WRONG_FIND_ADDRESS		= "查找地址失败";

		public static final String	WRONG_FIND_SUGGESSION	= "查找建议失败";

		public static final String	WRONG_FIND_DRIVER		= "查找自驾信息失败";

		public static final String	WRONG_FIND_WALKING		= "查找步行信息失败";

		public static final String	WRONG_FIND_BUS			= "查找线路信息失败";

		public static final String	WRONG_FIND_TRANSIT		= "查找公交信息失败";

		// -----------------------------------------------------------
		// 定位默认参数

		// 发送参数
		public static final int		LOCATION_DEFAULT		= 1;							// 定位方式
		public static final int		COORTYPE_DEFAULT		= 1;							//
		public static final int		TIMESPAN_DEFAULT		= 0;							// 小于1000是一次定位
		public static final boolean	RETURN_ADD_DEFAULT		= true;						// 是否结果中包含地址信息
		public static final boolean	RETURN_DIRECT_DEFAULT	= true;						// 是否结果中包含方位

		// 提醒参数
		public static final float	DST_RADIUS_DEFAULT		= 5000;						// 提醒范围
		public static final String	DST_TYPE_DEFAULT		= "bd0911";					// 坐标系

		// --------------------------------------------------------------
		// 线路查询参数

		// 自驾策略 1、时间优先、2、距离优先、3、费用优先、4、避免拥堵
		public static final int		DRIVER_POLICY			= 1;

		// 交通策略：1、时间优先、 2、少换乘 、3、少步行、4、非地铁
		public static final int		BUS_POLICY				= 1;

		// -------------------------------------------------------------
		// 绘制图形默认参数
		public static final int		RECT_RED				= 0;
		public static final int		RECT_GREEN				= 255;
		public static final int		RECT_BLUE				= 0;
		public static final int		RECT_ALPHA				= 100;

		public static final int		RECT_BORDER_THICKNESS	= 3;
		public static final int		RECT_BORDER_RED			= 255;
		public static final int		RECT_BORDER_GREEN		= 255;
		public static final int		RECT_BORDER_BLUE		= 255;
		public static final int		RECT_BORDER_ALPHA		= 100;

		public static final int		RECT_IS_FILL			= 1;

		public static final int		POINT_RED				= 255;
		public static final int		POINT_GREEN				= 0;
		public static final int		POINT_BLUE				= 0;
		public static final int		POINT_ALPHA				= 100;
		public static final int		POINT_THICKNESS			= 10;

		public static final int		LINE_RED				= 0;
		public static final int		LINE_GREEN				= 255;
		public static final int		LINE_BLUE				= 0;
		public static final int		LINE_ALPHA				= 100;
		public static final int		LINE_WIDTH				= 3;

		// ----------------------------------------------------------------
		// 文字图层
		public static final int		TEXT_ALPHA				= 100;
		public static final int		TEXT_RED				= 0;
		public static final int		TEXT_GREEN				= 0;
		public static final int		TEXT_BLUE				= 0;

		public static final int		TEXT_BACK_ALPHA			= 100;
		public static final int		TEXT_BACK_RED			= 0;
		public static final int		TEXT_BACK_GREEN			= 255;
		public static final int		TEXT_BACK_BLUE			= 255;

		public static final int		TEXT_FONT_SIZE			= 30;

	}
