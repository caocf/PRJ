#pragma once

// MFCActiveXControl5PropPage.h : CMFCActiveXControl5PropPage ����ҳ���������


// CMFCActiveXControl5PropPage : �й�ʵ�ֵ���Ϣ������� MFCActiveXControl5PropPage.cpp��

class CMFCActiveXControl5PropPage : public COlePropertyPage
{
	DECLARE_DYNCREATE(CMFCActiveXControl5PropPage)
	DECLARE_OLECREATE_EX(CMFCActiveXControl5PropPage)

// ���캯��
public:
	CMFCActiveXControl5PropPage();

// �Ի�������
	enum { IDD = IDD_PROPPAGE_MFCACTIVEXCONTROL5 };

// ʵ��
protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV ֧��

// ��Ϣӳ��
protected:
	DECLARE_MESSAGE_MAP()
};

