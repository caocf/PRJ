#pragma once

// MFCActiveXControl5Ctrl.h : CMFCActiveXControl5Ctrl ActiveX �ؼ����������


// CMFCActiveXControl5Ctrl : �й�ʵ�ֵ���Ϣ������� MFCActiveXControl5Ctrl.cpp��

class CMFCActiveXControl5Ctrl : public COleControl
{
	DECLARE_DYNCREATE(CMFCActiveXControl5Ctrl)

// ���캯��
public:
	CMFCActiveXControl5Ctrl();

// ��д
public:
	virtual void OnDraw(CDC* pdc, const CRect& rcBounds, const CRect& rcInvalid);
	virtual void DoPropExchange(CPropExchange* pPX);
	virtual void OnResetState();

// ʵ��
protected:
	~CMFCActiveXControl5Ctrl();

	DECLARE_OLECREATE_EX(CMFCActiveXControl5Ctrl)    // �๤���� guid
	DECLARE_OLETYPELIB(CMFCActiveXControl5Ctrl)      // GetTypeInfo
	DECLARE_PROPPAGEIDS(CMFCActiveXControl5Ctrl)     // ����ҳ ID
	DECLARE_OLECTLTYPE(CMFCActiveXControl5Ctrl)		// �������ƺ�����״̬

// ��Ϣӳ��
	DECLARE_MESSAGE_MAP()

// ����ӳ��
	DECLARE_DISPATCH_MAP()

	afx_msg void AboutBox();

// �¼�ӳ��
	DECLARE_EVENT_MAP()

// ���Ⱥ��¼� ID
public:
	enum {
		dispidInitConnect3 = 6L,
		dispidInitConnect2 = 5L,
		dispidPlayCamera = 4L,
		dispidQueryAllCamera2 = 3L,
		dispidQueryAllCamera = 2L,
		dispidInitConnect = 1L
	};
	afx_msg int OnCreate(LPCREATESTRUCT lpCreateStruct);
protected:
	SHORT InitConnect(void);
	BSTR QueryAllCamera(void);
	CHAR QueryAllCamera2(void);
	SHORT PlayCamera(SHORT id);
	SHORT InitConnect2(CHAR* IP, CHAR* name, CHAR* pass, CHAR* port);
	SHORT InitConnect3(LPCTSTR ip, LPCTSTR name, LPCTSTR pass, LPCTSTR port);
};

