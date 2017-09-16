package com.huzhouport.map;

public interface BaseMapData
{
	// key
	public static final String	sKey					= "4d8SpCi5Lmgwhyuj0q1nTApC";

	// Ĭ�ϲ���
		public static final float	ZOOMSIZE_DEFAULT		= 12;							// ��ͼ�Ƿ����
		public static final boolean	ENABLECLICK_DEFAULT		= true;						// ��ͼ�Ƿ�ɵ��
		public static final boolean	SATeLLITE_DEFAULT		= false;						// �Ƿ�����ͼ
		public static final boolean	TRAFFIC_DEFAULT			= false;						// �Ƿ�ͨͼ
		public static final boolean	INNER_ZOOM_CONTROL		= true;						//

		public static final String	CITY_DEFAULT			= "����";						// Ĭ�ϳ���
		public static final String  POINT_DEFAULT           = "������������";
		
		
		// ��ʼ��������ʾ
		public static final String	NO_CONTEXT				= "��������contex";
		public static final String	WRONG_INI_MANAGER		= "��ʼ����ͼ������ʧ��";
		public static final String	WRONG_NET_LINK			= "�������������";
		public static final String	WRONG_NET_DATA			= "��������ȷ��������";
		public static final String	WRONG_KEY_PERMISSION	= "����keyֵ����ȷ";
		public static final String	RIGHT_KEY_PERMISSION	= "keyֵ��֤�ɹ�";

		// ����������ʾ
		public static final String	NO_FIND_POI				= "δ�ҵ����";
		public static final String	WRONG_FIND_POI			= "���ҽ������";

		public static final String	WRONG_FIND_ADDRESS		= "���ҵ�ַʧ��";

		public static final String	WRONG_FIND_SUGGESSION	= "���ҽ���ʧ��";

		public static final String	WRONG_FIND_DRIVER		= "�����Լ���Ϣʧ��";

		public static final String	WRONG_FIND_WALKING		= "���Ҳ�����Ϣʧ��";

		public static final String	WRONG_FIND_BUS			= "������·��Ϣʧ��";

		public static final String	WRONG_FIND_TRANSIT		= "���ҹ�����Ϣʧ��";

		// -----------------------------------------------------------
		// ��λĬ�ϲ���

		// ���Ͳ���
		public static final int		LOCATION_DEFAULT		= 1;							// ��λ��ʽ
		public static final int		COORTYPE_DEFAULT		= 1;							//
		public static final int		TIMESPAN_DEFAULT		= 0;							// С��1000��һ�ζ�λ
		public static final boolean	RETURN_ADD_DEFAULT		= true;						// �Ƿ����а�����ַ��Ϣ
		public static final boolean	RETURN_DIRECT_DEFAULT	= true;						// �Ƿ����а�����λ

		// ���Ѳ���
		public static final float	DST_RADIUS_DEFAULT		= 5000;						// ���ѷ�Χ
		public static final String	DST_TYPE_DEFAULT		= "bd0911";					// ����ϵ

		// --------------------------------------------------------------
		// ��·��ѯ����

		// �Լݲ��� 1��ʱ�����ȡ�2���������ȡ�3���������ȡ�4������ӵ��
		public static final int		DRIVER_POLICY			= 1;

		// ��ͨ���ԣ�1��ʱ�����ȡ� 2���ٻ��� ��3���ٲ��С�4���ǵ���
		public static final int		BUS_POLICY				= 1;

		// -------------------------------------------------------------
		// ����ͼ��Ĭ�ϲ���
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
		// ����ͼ��
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
