#pragma once

// MFCActiveXControl5.h : MFCActiveXControl5.DLL ����ͷ�ļ�

#if !defined( __AFXCTL_H__ )
#error "�ڰ������ļ�֮ǰ������afxctl.h��"
#endif

#include "resource.h"       // ������


// CMFCActiveXControl5App : �й�ʵ�ֵ���Ϣ������� MFCActiveXControl5.cpp��

class CMFCActiveXControl5App : public COleControlModule
{
public:
	BOOL InitInstance();
	int ExitInstance();
};

extern const GUID CDECL _tlid;
extern const WORD _wVerMajor;
extern const WORD _wVerMinor;

