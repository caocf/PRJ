package com.huzhouport.map;

import com.baidu.mapapi.map.Symbol;
import com.baidu.mapapi.map.Symbol.Color;
import com.baidu.mapapi.map.Symbol.Stroke;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class MapConfig
{
	public MapConfig()
	{
		iniDefault();
	}

	/**
	 * 初始化默认参数
	 */
	private void iniDefault()
	{
		setfZoomSize(BaseMapData.ZOOMSIZE_DEFAULT);
		setbEnableClick(BaseMapData.ENABLECLICK_DEFAULT);
		setbSatellite(BaseMapData.SATeLLITE_DEFAULT);
		setbTraffic(BaseMapData.TRAFFIC_DEFAULT);
		setbZoomControl(BaseMapData.INNER_ZOOM_CONTROL);

		setiDriverPolicy(BaseMapData.DRIVER_POLICY);
		setiBusPolicy(BaseMapData.BUS_POLICY);

		iniLineDefault();
		iniPointDefault();
		iniRectDefault();

		iniTextDefault();
	}

	/**
	 * key是否正确
	 */
	private boolean	bKeyRight;

	public void setbKeyRight(boolean b)
	{
		bKeyRight = b;
	}

	public boolean getbKeyRight()
	{
		return bKeyRight;
	}

	// --------------------------------------------------

	/**
	 * 基本参数
	 */

	private float		fZoomSize;			// 地图缩放比例
	private GeoPoint	GeoCurrentPoint;	// 当前坐标点
	private GeoPoint	GeoCenterPoint;	// 中心坐标点
	private boolean		bEnableClick;		// 地图是否可点击
	private boolean		bZoomControl;		// 是否内置缩放控件

	public boolean getbEnableClick()
	{
		return bEnableClick;
	}

	public void setbEnableClick(boolean click)
	{
		bEnableClick = click;
	}

	public float getfZoomSize()
	{
		return fZoomSize;
	}

	public void setfZoomSize(float size)
	{
		fZoomSize = size;
	}

	public GeoPoint getGeoCurrentPoint()
	{
		return GeoCurrentPoint;
	}

	public void setGeoCurrentPoint(GeoPoint point)
	{
		GeoCurrentPoint = point;
	}

	public GeoPoint getGeoCenterPoint()
	{
		return GeoCenterPoint;
	}

	public void setGeoCenterPoint(GeoPoint point)
	{
		GeoCenterPoint = point;
	}

	public boolean getbZoomControl()
	{
		return bZoomControl;
	}

	public void setbZoomControl(boolean b)
	{
		bZoomControl = b;
	}

	// -------------------------------------

	/**
	 * 背景图层
	 */
	private boolean	bSatellite; // 卫星图
	private boolean	bTraffic;	// 交通图

	public boolean getbSatellite()
	{
		return bSatellite;
	}

	public void setbSatellite(boolean b)
	{
		bSatellite = b;
	}

	public boolean getbTraffic()
	{
		return bTraffic;
	}

	public void setbTraffic(boolean b)
	{
		bTraffic = b;
	}

	// ---------------------------------------
	/**
	 * 搜索项
	 */

	private int	iDriverPolicy;	// 自驾策略

	public void setiDriverPolicy(int i)
	{
		iDriverPolicy = i;
	}

	public int getiDriverPolicy()
	{
		switch (iDriverPolicy)
		{
			case 1:
				return MKSearch.ECAR_TIME_FIRST;
			case 2:
				return MKSearch.ECAR_DIS_FIRST;
			case 3:
				return MKSearch.ECAR_FEE_FIRST;
			case 4:
				return MKSearch.ECAR_AVOID_JAM;

		}
		return -1;
	}

	private int	iBusPolicy; // 公交策略

	public void setiBusPolicy(int i)
	{
		iBusPolicy = i;
	}

	public int getiBusPolicy()
	{
		switch (iBusPolicy)
		{
			case 1:
				return MKSearch.EBUS_TIME_FIRST;
			case 2:
				return MKSearch.EBUS_TRANSFER_FIRST;
			case 3:
				return MKSearch.EBUS_WALK_FIRST;
			case 4:
				return MKSearch.EBUS_NO_SUBWAY;

		}
		return -1;
	}

	// ------------------------------------------------------
	// 默认样式

	public void iniRectDefault()
	{
		Symbol symbol = new Symbol();

		Color color = symbol.new Color(BaseMapData.RECT_ALPHA,
				BaseMapData.RECT_RED, BaseMapData.RECT_GREEN,
				BaseMapData.RECT_BLUE);
		Color lineColor = symbol.new Color(BaseMapData.RECT_BORDER_ALPHA,
				BaseMapData.RECT_BORDER_RED, BaseMapData.RECT_BORDER_GREEN,
				BaseMapData.RECT_BORDER_BLUE);
		Stroke stroke = new Stroke(BaseMapData.RECT_BORDER_THICKNESS, lineColor);
		symbol.setSurface(color, BaseMapData.RECT_IS_FILL,
				BaseMapData.RECT_BORDER_THICKNESS, stroke);

		setrectSymbol(symbol);
	}

	public void iniLineDefault()
	{
		Symbol symbol = new Symbol();
		Symbol.Color color = symbol.new Color(BaseMapData.LINE_ALPHA,
				BaseMapData.LINE_RED, BaseMapData.LINE_GREEN,
				BaseMapData.LINE_BLUE);
		symbol.setLineSymbol(color, BaseMapData.LINE_WIDTH);

		setlineSymbol(symbol);
	}

	public void iniPointDefault()
	{
		Symbol symbol = new Symbol();
		Symbol.Color color = symbol.new Color(BaseMapData.POINT_ALPHA,
				BaseMapData.POINT_RED, BaseMapData.POINT_GREEN,
				BaseMapData.POINT_BLUE);
		symbol.setPointSymbol(color);

		setpointSymbol(symbol);

		setiPointThickness(BaseMapData.POINT_THICKNESS);
	}

	private Symbol	rectSymbol;

	public Symbol getrectSymbol()
	{
		return rectSymbol;
	}

	public void setrectSymbol(Symbol symbol)
	{
		rectSymbol = symbol;
	}

	private Symbol	lineSymbol;

	public Symbol getlineSymbol()
	{
		return lineSymbol;
	}

	public void setlineSymbol(Symbol symbol)
	{
		lineSymbol = symbol;
	}

	private Symbol	pointSymbol;

	public Symbol getpointSymbol()
	{
		return pointSymbol;
	}

	public void setpointSymbol(Symbol symbol)
	{
		pointSymbol = symbol;
	}

	private int	iPointThickness;

	public int getiPointThickness()
	{
		return iPointThickness;
	}

	public void setiPointThickness(int thick)
	{
		iPointThickness = thick;
	}

	// ----------------------------------------------------------
	// 文本图层
	private Symbol.Color	textSymbol;
	private Symbol.Color	textBackSymbol;
	private int				textFontSize;

	public void iniTextDefault()
	{
		Symbol symbol = new Symbol();
		Symbol.Color color = symbol.new Color(BaseMapData.TEXT_BACK_ALPHA,
				BaseMapData.TEXT_BACK_RED, BaseMapData.TEXT_BACK_GREEN,
				BaseMapData.TEXT_BACK_BLUE);
		settextBackSymbol(color);

		color = symbol.new Color(BaseMapData.TEXT_ALPHA, BaseMapData.TEXT_RED,
				BaseMapData.TEXT_GREEN, BaseMapData.TEXT_BLUE);
		settextSymbol(color);
		settextFontSize(BaseMapData.TEXT_FONT_SIZE);
	}

	public void settextSymbol(Symbol.Color s)
	{
		textSymbol = s;
	}

	public Symbol.Color gettextSymbol()
	{
		return textSymbol;
	}

	public void settextBackSymbol(Symbol.Color s)
	{
		textBackSymbol = s;
	}

	public Symbol.Color gettextBackSymbol()
	{
		return textBackSymbol;
	}

	public void settextFontSize(int i)
	{
		textFontSize = i;
	}

	public int gettextFontSize()
	{
		return textFontSize;
	}
}
