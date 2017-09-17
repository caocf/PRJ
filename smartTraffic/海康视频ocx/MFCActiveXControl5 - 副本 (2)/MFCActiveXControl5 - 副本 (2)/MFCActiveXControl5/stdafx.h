#pragma once

// stdafx.h : 标准系统包含文件的包含文件，
// 或是经常使用但不常更改的
// 特定于项目的包含文件

#ifndef VC_EXTRALEAN
#define VC_EXTRALEAN            //  从 Windows 头文件中排除极少使用的信息
#endif

#include "targetver.h"


#define _ATL_CSTRING_EXPLICIT_CONSTRUCTORS      // 某些 CString 构造函数将是显式的
#define _ATL_NO_AUTOMATIC_NAMESPACE             // 避免类名称冲突

#include <afxctl.h>         // ActiveX 控件的 MFC 支持
#include <afxext.h>         // MFC 扩展
#ifndef _AFX_NO_OLE_SUPPORT
#include <afxdtctl.h>           // MFC 对 Internet Explorer 4 公共控件的支持
#endif
#ifndef _AFX_NO_AFXCMN_SUPPORT
#include <afxcmn.h>                     // MFC 对 Windows 公共控件的支持
#endif // _AFX_NO_AFXCMN_SUPPORT

// 如果不希望使用 MFC 数据库类，
//  请删除下面的两个包含文件
#ifndef _WIN64

#ifndef _AFX_NO_DB_SUPPORT
#include <afxdb.h>                      // MFC ODBC 数据库类
#endif // _AFX_NO_DB_SUPPORT

#ifndef _AFX_NO_DAO_SUPPORT
#include <afxdao.h>                     // MFC DAO 数据库类
#endif // _AFX_NO_DAO_SUPPORT



#include "../dll/tmcp_interface_sdk.h"
#include <afxcontrolbars.h>

#include <map>
using namespace std;

#define WM_PTZ				WM_USER + 100
#define WM_STOPPLAY			WM_USER + 101
#define WM_FOCUS			WM_USER + 102
#define WM_CAPPIC			WM_USER + 103
#define WM_STARTPLAY		WM_USER + 104
#define WM_QUERYRECORD		WM_USER + 105
#define WM_PLAYRECORD		WM_USER + 106
#define WM_RECORDMENU		WM_USER + 107
#define PLAY_START			10001 //开始播放 
#define PLAY_PAUSE			10002 //暂停播放
#define PLAY_FAST			10003 //加快播放速度
#define PLAY_SLOW			10004 //减慢播放速度
#define PLAY_OFFSET			10005 //播放定位


#define ALARM_CODE_VIDEO_LOSE				10000
#define ALARM_CODE_VIDEO_MOVE				10002
#define ALARM_CODE_VIDEO_COVER				10001
#define ALARM_CODE_ACROSS_LINE				203
#define ALARM_CODE_ENTER_REGION				204
#define ALARM_CODE_EXIT_REGION				205
#define ALARM_CODE_ALARM_INTRUTION			206
#define ALARM_CODE_TAKE_LEAVE				207
#define ALARM_CODE_LOITER					208
#define ALARM_CODE_PARKING					209
#define ALARM_CODE_SPEED_DETECT				210
#define ALARM_CODE_AGGREGATION				211
#define ALARM_CODE_ASSAULT					212
#define ALARM_CODE_PEOPLE_NUMBER			213
#define ALARM_CODE_BLOCK					214
#define ALARM_CODE_APPROACH_PARKING			215
#define ALARM_CODE_REGRESS					216
#define ALARM_CODE_PASSER					217
#define ALARM_CODE_THROW					218
#define ALARM_CODE_SMOKE					219
#define ALARM_CODE_OPTICAL_FIBER			220
#define ALARM_CODE_SENSOR_ALARM				400
#define ALARM_CODE_BOX_ALARM				600
#define ALARM_CODE_VRM						222


using namespace Platform;


#ifdef _DEBUG
#pragma comment(lib, "../dll/debug/tmcp_interface_sdk.lib")
#else
#pragma comment(lib, "../dll/release/tmcp_interface_sdk.lib")
#endif // _DEBUG


#ifdef _UNICODE
#if defined _M_IX86
#pragma comment(linker,"/manifestdependency:\"type='win32' name='Microsoft.Windows.Common-Controls' version='6.0.0.0' processorArchitecture='x86' publicKeyToken='6595b64144ccf1df' language='*'\"")
#elif defined _M_IA64
#pragma comment(linker,"/manifestdependency:\"type='win32' name='Microsoft.Windows.Common-Controls' version='6.0.0.0' processorArchitecture='ia64' publicKeyToken='6595b64144ccf1df' language='*'\"")
#elif defined _M_X64
#pragma comment(linker,"/manifestdependency:\"type='win32' name='Microsoft.Windows.Common-Controls' version='6.0.0.0' processorArchitecture='amd64' publicKeyToken='6595b64144ccf1df' language='*'\"")
#else
#pragma comment(linker,"/manifestdependency:\"type='win32' name='Microsoft.Windows.Common-Controls' version='6.0.0.0' processorArchitecture='*' publicKeyToken='6595b64144ccf1df' language='*'\"")
#endif
#endif

#endif // _WIN64

