// MFCActiveXControl5Ctrl.cpp : CMFCActiveXControl5Ctrl ActiveX 控件类的实现。

#include "stdafx.h"
#include "MFCActiveXControl5.h"
#include "MFCActiveXControl5Ctrl.h"
#include "MFCActiveXControl5PropPage.h"
#include "afxdialogex.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif

IMPLEMENT_DYNCREATE(CMFCActiveXControl5Ctrl, COleControl)

// 消息映射

BEGIN_MESSAGE_MAP(CMFCActiveXControl5Ctrl, COleControl)
	ON_OLEVERB(AFX_IDS_VERB_PROPERTIES, OnProperties)
	ON_WM_CREATE()
END_MESSAGE_MAP()

// 调度映射

BEGIN_DISPATCH_MAP(CMFCActiveXControl5Ctrl, COleControl)
	DISP_FUNCTION_ID(CMFCActiveXControl5Ctrl, "AboutBox", DISPID_ABOUTBOX, AboutBox, VT_EMPTY, VTS_NONE)
	DISP_FUNCTION_ID(CMFCActiveXControl5Ctrl, "InitConnect", dispidInitConnect, InitConnect, VT_I2, VTS_NONE)
	DISP_FUNCTION_ID(CMFCActiveXControl5Ctrl, "QueryAllCamera", dispidQueryAllCamera, QueryAllCamera, VT_BSTR, VTS_NONE)
	DISP_FUNCTION_ID(CMFCActiveXControl5Ctrl, "QueryAllCamera2", dispidQueryAllCamera2, QueryAllCamera2, VT_I1, VTS_NONE)
	DISP_FUNCTION_ID(CMFCActiveXControl5Ctrl, "PlayCamera", dispidPlayCamera, PlayCamera, VT_I2, VTS_I2)
	DISP_FUNCTION_ID(CMFCActiveXControl5Ctrl, "InitConnect2", dispidInitConnect2, InitConnect2, VT_I2, VTS_PI1 VTS_PI1 VTS_PI1 VTS_PI1)
	DISP_FUNCTION_ID(CMFCActiveXControl5Ctrl, "InitConnect3", dispidInitConnect3, InitConnect3, VT_I2, VTS_BSTR VTS_BSTR VTS_BSTR VTS_BSTR)
END_DISPATCH_MAP()

// 事件映射

BEGIN_EVENT_MAP(CMFCActiveXControl5Ctrl, COleControl)
END_EVENT_MAP()

// 属性页

// TODO: 按需要添加更多属性页。请记住增加计数!
BEGIN_PROPPAGEIDS(CMFCActiveXControl5Ctrl, 1)
	PROPPAGEID(CMFCActiveXControl5PropPage::guid)
END_PROPPAGEIDS(CMFCActiveXControl5Ctrl)

// 初始化类工厂和 guid

IMPLEMENT_OLECREATE_EX(CMFCActiveXControl5Ctrl, "MFCACTIVEXCONTRO.MFCActiveXControCtrl.1",
	0xceaa8aea, 0x3d39, 0x4cce, 0xa9, 0xd9, 0xe9, 0x48, 0xf6, 0x45, 0x5d, 0x33)

// 键入库 ID 和版本

IMPLEMENT_OLETYPELIB(CMFCActiveXControl5Ctrl, _tlid, _wVerMajor, _wVerMinor)

// 接口 ID

const IID IID_DMFCActiveXControl5 = { 0xA90C30BE, 0x25CC, 0x4E7F, { 0xA3, 0xA5, 0xBD, 0x7F, 0x49, 0x78, 0xA9, 0xCA } };
const IID IID_DMFCActiveXControl5Events = { 0xBC9E9507, 0x297B, 0x4538, { 0x92, 0x88, 0x73, 0xD2, 0x49, 0xE7, 0xC, 0x15 } };

// 控件类型信息

static const DWORD _dwMFCActiveXControl5OleMisc =
	OLEMISC_ACTIVATEWHENVISIBLE |
	OLEMISC_SETCLIENTSITEFIRST |
	OLEMISC_INSIDEOUT |
	OLEMISC_CANTLINKINSIDE |
	OLEMISC_RECOMPOSEONRESIZE;

IMPLEMENT_OLECTLTYPE(CMFCActiveXControl5Ctrl, IDS_MFCACTIVEXCONTROL5, _dwMFCActiveXControl5OleMisc)

// CMFCActiveXControl5Ctrl::CMFCActiveXControl5CtrlFactory::UpdateRegistry -
// 添加或移除 CMFCActiveXControl5Ctrl 的系统注册表项

BOOL CMFCActiveXControl5Ctrl::CMFCActiveXControl5CtrlFactory::UpdateRegistry(BOOL bRegister)
{
	// TODO: 验证您的控件是否符合单元模型线程处理规则。
	// 有关更多信息，请参考 MFC 技术说明 64。
	// 如果您的控件不符合单元模型规则，则
	// 必须修改如下代码，将第六个参数从
	// afxRegApartmentThreading 改为 0。

	if (bRegister)
		return AfxOleRegisterControlClass(
			AfxGetInstanceHandle(),
			m_clsid,
			m_lpszProgID,
			IDS_MFCACTIVEXCONTROL5,
			IDB_MFCACTIVEXCONTROL5,
			afxRegApartmentThreading,
			_dwMFCActiveXControl5OleMisc,
			_tlid,
			_wVerMajor,
			_wVerMinor);
	else
		return AfxOleUnregisterClass(m_clsid, m_lpszProgID);
}


// CMFCActiveXControl5Ctrl::CMFCActiveXControl5Ctrl - 构造函数

CMFCActiveXControl5Ctrl::CMFCActiveXControl5Ctrl()
{
	InitializeIIDs(&IID_DMFCActiveXControl5, &IID_DMFCActiveXControl5Events);
	// TODO: 在此初始化控件的实例数据。



}

// CMFCActiveXControl5Ctrl::~CMFCActiveXControl5Ctrl - 析构函数

CMFCActiveXControl5Ctrl::~CMFCActiveXControl5Ctrl()
{
	// TODO: 在此清理控件的实例数据。
}

// CMFCActiveXControl5Ctrl::OnDraw - 绘图函数

HWND g_hwnd[4];
int handle=-1;
char cId[20];
int g_iCurWnd=0;

map <int,int>	g_streamhandle_hwnd_map;

void CMFCActiveXControl5Ctrl::OnDraw(
			CDC* pdc, const CRect& rcBounds, const CRect& rcInvalid)
{
	if (!pdc)
		return;

	pdc->FillRect(rcBounds, CBrush::FromHandle((HBRUSH)GetStockObject(WHITE_BRUSH)));
	pdc->Rectangle(rcBounds);


	CRect rc=rcBounds;

	g_iCurWnd=0;

	::GetWindowRect(g_hwnd[g_iCurWnd],&rc);
//	ScreenToClient(&rc); 
//	InvalidateRect(rc);



}

// CMFCActiveXControl5Ctrl::DoPropExchange - 持久性支持

void CMFCActiveXControl5Ctrl::DoPropExchange(CPropExchange* pPX)
{
	ExchangeVersion(pPX, MAKELONG(_wVerMinor, _wVerMajor));
	COleControl::DoPropExchange(pPX);

	// TODO: 为每个持久的自定义属性调用 PX_ 函数。
}


// CMFCActiveXControl5Ctrl::OnResetState - 将控件重置为默认状态

void CMFCActiveXControl5Ctrl::OnResetState()
{
	COleControl::OnResetState();  // 重置 DoPropExchange 中找到的默认值

	// TODO: 在此重置任意其他控件状态。
}


// CMFCActiveXControl5Ctrl::AboutBox - 向用户显示“关于”框

void CMFCActiveXControl5Ctrl::AboutBox()
{
	CDialogEx dlgAbout(IDD_ABOUTBOX_MFCACTIVEXCONTROL5);
	dlgAbout.DoModal();
}


// CMFCActiveXControl5Ctrl 消息处理程序


int CMFCActiveXControl5Ctrl::OnCreate(LPCREATESTRUCT lpCreateStruct)
{
	if (COleControl::OnCreate(lpCreateStruct) == -1)
		return -1;
	
		return 0;
}


//连接

SHORT CMFCActiveXControl5Ctrl::InitConnect(void)
{
	AFX_MANAGE_STATE(AfxGetStaticModuleState());

	// TODO: 在此添加调度处理程序代码
	Plat_Init();

	handle=Plat_LoginCMS("172.20.44.194","admin","12345","8011");

	return handle;
}


BSTR CMFCActiveXControl5Ctrl::QueryAllCamera(void)
{
	AFX_MANAGE_STATE(AfxGetStaticModuleState());

	CString strResult=_T("");

	if (Plat_QueryResource(3,handle) == 0)
	{
		do
		{
			int Id = atoi(Plat_GetValueStr(Camera::camera_id, handle));
			CString Name = (CString)Plat_GetValueStr(Camera::device_name, handle);
			int high = atoi(Plat_GetValueStr(Camera::region_id, handle));
			
			if (Id == 0)
			{
				continue;
			}

			CString sId;
			sId.Format(TEXT("%d"),Id);

			CString shigh;
			shigh.Format(TEXT("%d"),high);

			strResult+=sId;
			strResult+=" ";
			strResult+=Name;
			strResult+=" ";
			strResult+=shigh;
			strResult+=";";
			

		}while (Plat_MoveNext(handle) != -1);
	}
	

	return strResult.AllocSysString();
}


CHAR CMFCActiveXControl5Ctrl::QueryAllCamera2(void)
{
	AFX_MANAGE_STATE(AfxGetStaticModuleState());

	return 0;
}


SHORT CMFCActiveXControl5Ctrl::PlayCamera(SHORT id)
{
	AFX_MANAGE_STATE(AfxGetStaticModuleState());


	int iId = id;//(WPARAM)id;

	int streamhandle;
	_itoa_s(iId, cId, 10);

	map<int ,int>::iterator ptr = g_streamhandle_hwnd_map.find(g_iCurWnd);
	if (ptr != g_streamhandle_hwnd_map.end())
	{
		int hstream = ptr->second;
		if (hstream >= 0)	//关闭正在播放的
		{
			if (Plat_StopVideo(hstream) == 0)
			{
				hstream = -1;
				g_streamhandle_hwnd_map.erase(g_iCurWnd);
			}
			else
			{
				return -1;
			}
		}
	}
	Invalidate();

	//预览实时视频
	const char* url = Plat_QueryRealStreamURL(cId, handle);


	if (-10302 == Plat_GetLastError())
	{
		return -1;
	}
    
    if(url == "")
    {
    }

	streamhandle = Plat_PlayVideo(url, (long)g_hwnd[g_iCurWnd], handle);
	
	if(streamhandle >= 0)
	{
		g_streamhandle_hwnd_map.insert(pair<int ,int>(g_iCurWnd, streamhandle));

	}
	else
	{
	}

	return 0;
}


SHORT CMFCActiveXControl5Ctrl::InitConnect2(CHAR* IP, CHAR* name, CHAR* pass, CHAR* port)
{
	
	AFX_MANAGE_STATE(AfxGetStaticModuleState());

	Plat_Init();

	handle=Plat_LoginCMS(IP,name,pass,port);

	return handle;
}


SHORT CMFCActiveXControl5Ctrl::InitConnect3(LPCTSTR ip, LPCTSTR name, LPCTSTR pass, LPCTSTR port)
{
	AFX_MANAGE_STATE(AfxGetStaticModuleState());

	return 0;
}
