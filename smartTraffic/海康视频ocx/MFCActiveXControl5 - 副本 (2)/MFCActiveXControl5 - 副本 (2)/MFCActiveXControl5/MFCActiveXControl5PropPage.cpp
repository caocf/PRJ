// MFCActiveXControl5PropPage.cpp : CMFCActiveXControl5PropPage ����ҳ���ʵ�֡�

#include "stdafx.h"
#include "MFCActiveXControl5.h"
#include "MFCActiveXControl5PropPage.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif

IMPLEMENT_DYNCREATE(CMFCActiveXControl5PropPage, COlePropertyPage)

// ��Ϣӳ��

BEGIN_MESSAGE_MAP(CMFCActiveXControl5PropPage, COlePropertyPage)
END_MESSAGE_MAP()

// ��ʼ���๤���� guid

IMPLEMENT_OLECREATE_EX(CMFCActiveXControl5PropPage, "MFCACTIVEXCONT.MFCActiveXContPropPage.1",
	0x2024bdeb, 0x8e27, 0x4567, 0x8e, 0x2d, 0x7f, 0xd7, 0xc0, 0x5e, 0x5a, 0x99)

// CMFCActiveXControl5PropPage::CMFCActiveXControl5PropPageFactory::UpdateRegistry -
// ��ӻ��Ƴ� CMFCActiveXControl5PropPage ��ϵͳע�����

BOOL CMFCActiveXControl5PropPage::CMFCActiveXControl5PropPageFactory::UpdateRegistry(BOOL bRegister)
{
	if (bRegister)
		return AfxOleRegisterPropertyPageClass(AfxGetInstanceHandle(),
			m_clsid, IDS_MFCACTIVEXCONTROL5_PPG);
	else
		return AfxOleUnregisterClass(m_clsid, NULL);
}

// CMFCActiveXControl5PropPage::CMFCActiveXControl5PropPage - ���캯��

CMFCActiveXControl5PropPage::CMFCActiveXControl5PropPage() :
	COlePropertyPage(IDD, IDS_MFCACTIVEXCONTROL5_PPG_CAPTION)
{
}

// CMFCActiveXControl5PropPage::DoDataExchange - ��ҳ�����Լ��ƶ�����

void CMFCActiveXControl5PropPage::DoDataExchange(CDataExchange* pDX)
{
	DDP_PostProcessing(pDX);
}

// CMFCActiveXControl5PropPage ��Ϣ�������
