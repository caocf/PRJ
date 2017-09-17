// MFCActiveXControl5PropPage.cpp : CMFCActiveXControl5PropPage 属性页类的实现。

#include "stdafx.h"
#include "MFCActiveXControl5.h"
#include "MFCActiveXControl5PropPage.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif

IMPLEMENT_DYNCREATE(CMFCActiveXControl5PropPage, COlePropertyPage)

// 消息映射

BEGIN_MESSAGE_MAP(CMFCActiveXControl5PropPage, COlePropertyPage)
END_MESSAGE_MAP()

// 初始化类工厂和 guid

IMPLEMENT_OLECREATE_EX(CMFCActiveXControl5PropPage, "MFCACTIVEXCONT.MFCActiveXContPropPage.1",
	0x2024bdeb, 0x8e27, 0x4567, 0x8e, 0x2d, 0x7f, 0xd7, 0xc0, 0x5e, 0x5a, 0x99)

// CMFCActiveXControl5PropPage::CMFCActiveXControl5PropPageFactory::UpdateRegistry -
// 添加或移除 CMFCActiveXControl5PropPage 的系统注册表项

BOOL CMFCActiveXControl5PropPage::CMFCActiveXControl5PropPageFactory::UpdateRegistry(BOOL bRegister)
{
	if (bRegister)
		return AfxOleRegisterPropertyPageClass(AfxGetInstanceHandle(),
			m_clsid, IDS_MFCACTIVEXCONTROL5_PPG);
	else
		return AfxOleUnregisterClass(m_clsid, NULL);
}

// CMFCActiveXControl5PropPage::CMFCActiveXControl5PropPage - 构造函数

CMFCActiveXControl5PropPage::CMFCActiveXControl5PropPage() :
	COlePropertyPage(IDD, IDS_MFCACTIVEXCONTROL5_PPG_CAPTION)
{
}

// CMFCActiveXControl5PropPage::DoDataExchange - 在页和属性间移动数据

void CMFCActiveXControl5PropPage::DoDataExchange(CDataExchange* pDX)
{
	DDP_PostProcessing(pDX);
}

// CMFCActiveXControl5PropPage 消息处理程序
