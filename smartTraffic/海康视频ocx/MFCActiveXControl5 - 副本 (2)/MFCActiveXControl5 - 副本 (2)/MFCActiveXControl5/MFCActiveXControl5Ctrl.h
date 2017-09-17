#pragma once

// MFCActiveXControl5Ctrl.h : CMFCActiveXControl5Ctrl ActiveX 控件类的声明。


// CMFCActiveXControl5Ctrl : 有关实现的信息，请参阅 MFCActiveXControl5Ctrl.cpp。

class CMFCActiveXControl5Ctrl : public COleControl
{
	DECLARE_DYNCREATE(CMFCActiveXControl5Ctrl)

// 构造函数
public:
	CMFCActiveXControl5Ctrl();

// 重写
public:
	virtual void OnDraw(CDC* pdc, const CRect& rcBounds, const CRect& rcInvalid);
	virtual void DoPropExchange(CPropExchange* pPX);
	virtual void OnResetState();

// 实现
protected:
	~CMFCActiveXControl5Ctrl();

	DECLARE_OLECREATE_EX(CMFCActiveXControl5Ctrl)    // 类工厂和 guid
	DECLARE_OLETYPELIB(CMFCActiveXControl5Ctrl)      // GetTypeInfo
	DECLARE_PROPPAGEIDS(CMFCActiveXControl5Ctrl)     // 属性页 ID
	DECLARE_OLECTLTYPE(CMFCActiveXControl5Ctrl)		// 类型名称和杂项状态

// 消息映射
	DECLARE_MESSAGE_MAP()

// 调度映射
	DECLARE_DISPATCH_MAP()

	afx_msg void AboutBox();

// 事件映射
	DECLARE_EVENT_MAP()

// 调度和事件 ID
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

