

/* this ALWAYS GENERATED file contains the IIDs and CLSIDs */

/* link this file in with the server and any clients */


 /* File created by MIDL compiler version 8.00.0595 */
/* at Mon Aug 10 16:38:33 2015
 */
/* Compiler settings for MFCActiveXControl5.idl:
    Oicf, W1, Zp8, env=Win32 (32b run), target_arch=X86 8.00.0595 
    protocol : dce , ms_ext, c_ext, robust
    error checks: allocation ref bounds_check enum stub_data 
    VC __declspec() decoration level: 
         __declspec(uuid()), __declspec(selectany), __declspec(novtable)
         DECLSPEC_UUID(), MIDL_INTERFACE()
*/
/* @@MIDL_FILE_HEADING(  ) */

#pragma warning( disable: 4049 )  /* more than 64k source lines */


#ifdef __cplusplus
extern "C"{
#endif 


#include <rpc.h>
#include <rpcndr.h>

#ifdef _MIDL_USE_GUIDDEF_

#ifndef INITGUID
#define INITGUID
#include <guiddef.h>
#undef INITGUID
#else
#include <guiddef.h>
#endif

#define MIDL_DEFINE_GUID(type,name,l,w1,w2,b1,b2,b3,b4,b5,b6,b7,b8) \
        DEFINE_GUID(name,l,w1,w2,b1,b2,b3,b4,b5,b6,b7,b8)

#else // !_MIDL_USE_GUIDDEF_

#ifndef __IID_DEFINED__
#define __IID_DEFINED__

typedef struct _IID
{
    unsigned long x;
    unsigned short s1;
    unsigned short s2;
    unsigned char  c[8];
} IID;

#endif // __IID_DEFINED__

#ifndef CLSID_DEFINED
#define CLSID_DEFINED
typedef IID CLSID;
#endif // CLSID_DEFINED

#define MIDL_DEFINE_GUID(type,name,l,w1,w2,b1,b2,b3,b4,b5,b6,b7,b8) \
        const type name = {l,w1,w2,{b1,b2,b3,b4,b5,b6,b7,b8}}

#endif !_MIDL_USE_GUIDDEF_

MIDL_DEFINE_GUID(IID, LIBID_MFCActiveXControl5Lib,0xA0AA506D,0x8BF6,0x4D3C,0x91,0x78,0xED,0x00,0x1F,0xAE,0xAD,0x0B);


MIDL_DEFINE_GUID(IID, DIID__DMFCActiveXControl5,0xA90C30BE,0x25CC,0x4E7F,0xA3,0xA5,0xBD,0x7F,0x49,0x78,0xA9,0xCA);


MIDL_DEFINE_GUID(IID, DIID__DMFCActiveXControl5Events,0xBC9E9507,0x297B,0x4538,0x92,0x88,0x73,0xD2,0x49,0xE7,0x0C,0x15);


MIDL_DEFINE_GUID(CLSID, CLSID_MFCActiveXControl5,0xCEAA8AEA,0x3D39,0x4CCE,0xA9,0xD9,0xE9,0x48,0xF6,0x45,0x5D,0x33);

#undef MIDL_DEFINE_GUID

#ifdef __cplusplus
}
#endif



