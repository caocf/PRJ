// MFCActiveXControl5.cpp : CMFCActiveXControl5App �� DLL ע���ʵ�֡�

#include "stdafx.h"
#include "MFCActiveXControl5.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


CMFCActiveXControl5App theApp;

const GUID CDECL _tlid = { 0xA0AA506D, 0x8BF6, 0x4D3C, { 0x91, 0x78, 0xED, 0x0, 0x1F, 0xAE, 0xAD, 0xB } };
const WORD _wVerMajor = 1;
const WORD _wVerMinor = 0;



// CMFCActiveXControl5App::InitInstance - DLL ��ʼ��

BOOL CMFCActiveXControl5App::InitInstance()
{
	BOOL bInit = COleControlModule::InitInstance();

	if (bInit)
	{

		// TODO: �ڴ�������Լ���ģ���ʼ�����롣
	}

	return bInit;
}



// CMFCActiveXControl5App::ExitInstance - DLL ��ֹ

int CMFCActiveXControl5App::ExitInstance()
{
	// TODO: �ڴ�������Լ���ģ����ֹ���롣

	return COleControlModule::ExitInstance();
}



// DllRegisterServer - ������ӵ�ϵͳע���

STDAPI DllRegisterServer(void)
{
	AFX_MANAGE_STATE(_afxModuleAddrThis);

	if (!AfxOleRegisterTypeLib(AfxGetInstanceHandle(), _tlid))
		return ResultFromScode(SELFREG_E_TYPELIB);

	if (!COleObjectFactoryEx::UpdateRegistryAll(TRUE))
		return ResultFromScode(SELFREG_E_CLASS);

	return NOERROR;
}



// DllUnregisterServer - �����ϵͳע������Ƴ�

STDAPI DllUnregisterServer(void)
{
	AFX_MANAGE_STATE(_afxModuleAddrThis);

	if (!AfxOleUnregisterTypeLib(_tlid, _wVerMajor, _wVerMinor))
		return ResultFromScode(SELFREG_E_TYPELIB);

	if (!COleObjectFactoryEx::UpdateRegistryAll(FALSE))
		return ResultFromScode(SELFREG_E_CLASS);

	return NOERROR;
}
