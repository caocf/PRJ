#pragma once

// MFCActiveXControl5PropPage.h : CMFCActiveXControl5PropPage 属性页类的声明。


// CMFCActiveXControl5PropPage : 有关实现的信息，请参阅 MFCActiveXControl5PropPage.cpp。

class CMFCActiveXControl5PropPage : public COlePropertyPage
{
	DECLARE_DYNCREATE(CMFCActiveXControl5PropPage)
	DECLARE_OLECREATE_EX(CMFCActiveXControl5PropPage)

// 构造函数
public:
	CMFCActiveXControl5PropPage();

// 对话框数据
	enum { IDD = IDD_PROPPAGE_MFCACTIVEXCONTROL5 };

// 实现
protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

// 消息映射
protected:
	DECLARE_MESSAGE_MAP()
};

