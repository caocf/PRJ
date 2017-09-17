

/* this ALWAYS GENERATED file contains the definitions for the interfaces */


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


/* verify that the <rpcndr.h> version is high enough to compile this file*/
#ifndef __REQUIRED_RPCNDR_H_VERSION__
#define __REQUIRED_RPCNDR_H_VERSION__ 475
#endif

#include "rpc.h"
#include "rpcndr.h"

#ifndef __RPCNDR_H_VERSION__
#error this stub requires an updated version of <rpcndr.h>
#endif // __RPCNDR_H_VERSION__


#ifndef __MFCActiveXControl5idl_h__
#define __MFCActiveXControl5idl_h__

#if defined(_MSC_VER) && (_MSC_VER >= 1020)
#pragma once
#endif

/* Forward Declarations */ 

#ifndef ___DMFCActiveXControl5_FWD_DEFINED__
#define ___DMFCActiveXControl5_FWD_DEFINED__
typedef interface _DMFCActiveXControl5 _DMFCActiveXControl5;

#endif 	/* ___DMFCActiveXControl5_FWD_DEFINED__ */


#ifndef ___DMFCActiveXControl5Events_FWD_DEFINED__
#define ___DMFCActiveXControl5Events_FWD_DEFINED__
typedef interface _DMFCActiveXControl5Events _DMFCActiveXControl5Events;

#endif 	/* ___DMFCActiveXControl5Events_FWD_DEFINED__ */


#ifndef __MFCActiveXControl5_FWD_DEFINED__
#define __MFCActiveXControl5_FWD_DEFINED__

#ifdef __cplusplus
typedef class MFCActiveXControl5 MFCActiveXControl5;
#else
typedef struct MFCActiveXControl5 MFCActiveXControl5;
#endif /* __cplusplus */

#endif 	/* __MFCActiveXControl5_FWD_DEFINED__ */


#ifdef __cplusplus
extern "C"{
#endif 


/* interface __MIDL_itf_MFCActiveXControl5_0000_0000 */
/* [local] */ 

#pragma once
#pragma region Desktop Family
#pragma endregion


extern RPC_IF_HANDLE __MIDL_itf_MFCActiveXControl5_0000_0000_v0_0_c_ifspec;
extern RPC_IF_HANDLE __MIDL_itf_MFCActiveXControl5_0000_0000_v0_0_s_ifspec;


#ifndef __MFCActiveXControl5Lib_LIBRARY_DEFINED__
#define __MFCActiveXControl5Lib_LIBRARY_DEFINED__

/* library MFCActiveXControl5Lib */
/* [control][version][uuid] */ 


EXTERN_C const IID LIBID_MFCActiveXControl5Lib;

#ifndef ___DMFCActiveXControl5_DISPINTERFACE_DEFINED__
#define ___DMFCActiveXControl5_DISPINTERFACE_DEFINED__

/* dispinterface _DMFCActiveXControl5 */
/* [uuid] */ 


EXTERN_C const IID DIID__DMFCActiveXControl5;

#if defined(__cplusplus) && !defined(CINTERFACE)

    MIDL_INTERFACE("A90C30BE-25CC-4E7F-A3A5-BD7F4978A9CA")
    _DMFCActiveXControl5 : public IDispatch
    {
    };
    
#else 	/* C style interface */

    typedef struct _DMFCActiveXControl5Vtbl
    {
        BEGIN_INTERFACE
        
        HRESULT ( STDMETHODCALLTYPE *QueryInterface )( 
            _DMFCActiveXControl5 * This,
            /* [in] */ REFIID riid,
            /* [annotation][iid_is][out] */ 
            _COM_Outptr_  void **ppvObject);
        
        ULONG ( STDMETHODCALLTYPE *AddRef )( 
            _DMFCActiveXControl5 * This);
        
        ULONG ( STDMETHODCALLTYPE *Release )( 
            _DMFCActiveXControl5 * This);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfoCount )( 
            _DMFCActiveXControl5 * This,
            /* [out] */ UINT *pctinfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfo )( 
            _DMFCActiveXControl5 * This,
            /* [in] */ UINT iTInfo,
            /* [in] */ LCID lcid,
            /* [out] */ ITypeInfo **ppTInfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetIDsOfNames )( 
            _DMFCActiveXControl5 * This,
            /* [in] */ REFIID riid,
            /* [size_is][in] */ LPOLESTR *rgszNames,
            /* [range][in] */ UINT cNames,
            /* [in] */ LCID lcid,
            /* [size_is][out] */ DISPID *rgDispId);
        
        /* [local] */ HRESULT ( STDMETHODCALLTYPE *Invoke )( 
            _DMFCActiveXControl5 * This,
            /* [annotation][in] */ 
            _In_  DISPID dispIdMember,
            /* [annotation][in] */ 
            _In_  REFIID riid,
            /* [annotation][in] */ 
            _In_  LCID lcid,
            /* [annotation][in] */ 
            _In_  WORD wFlags,
            /* [annotation][out][in] */ 
            _In_  DISPPARAMS *pDispParams,
            /* [annotation][out] */ 
            _Out_opt_  VARIANT *pVarResult,
            /* [annotation][out] */ 
            _Out_opt_  EXCEPINFO *pExcepInfo,
            /* [annotation][out] */ 
            _Out_opt_  UINT *puArgErr);
        
        END_INTERFACE
    } _DMFCActiveXControl5Vtbl;

    interface _DMFCActiveXControl5
    {
        CONST_VTBL struct _DMFCActiveXControl5Vtbl *lpVtbl;
    };

    

#ifdef COBJMACROS


#define _DMFCActiveXControl5_QueryInterface(This,riid,ppvObject)	\
    ( (This)->lpVtbl -> QueryInterface(This,riid,ppvObject) ) 

#define _DMFCActiveXControl5_AddRef(This)	\
    ( (This)->lpVtbl -> AddRef(This) ) 

#define _DMFCActiveXControl5_Release(This)	\
    ( (This)->lpVtbl -> Release(This) ) 


#define _DMFCActiveXControl5_GetTypeInfoCount(This,pctinfo)	\
    ( (This)->lpVtbl -> GetTypeInfoCount(This,pctinfo) ) 

#define _DMFCActiveXControl5_GetTypeInfo(This,iTInfo,lcid,ppTInfo)	\
    ( (This)->lpVtbl -> GetTypeInfo(This,iTInfo,lcid,ppTInfo) ) 

#define _DMFCActiveXControl5_GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId)	\
    ( (This)->lpVtbl -> GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId) ) 

#define _DMFCActiveXControl5_Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr)	\
    ( (This)->lpVtbl -> Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr) ) 

#endif /* COBJMACROS */


#endif 	/* C style interface */


#endif 	/* ___DMFCActiveXControl5_DISPINTERFACE_DEFINED__ */


#ifndef ___DMFCActiveXControl5Events_DISPINTERFACE_DEFINED__
#define ___DMFCActiveXControl5Events_DISPINTERFACE_DEFINED__

/* dispinterface _DMFCActiveXControl5Events */
/* [uuid] */ 


EXTERN_C const IID DIID__DMFCActiveXControl5Events;

#if defined(__cplusplus) && !defined(CINTERFACE)

    MIDL_INTERFACE("BC9E9507-297B-4538-9288-73D249E70C15")
    _DMFCActiveXControl5Events : public IDispatch
    {
    };
    
#else 	/* C style interface */

    typedef struct _DMFCActiveXControl5EventsVtbl
    {
        BEGIN_INTERFACE
        
        HRESULT ( STDMETHODCALLTYPE *QueryInterface )( 
            _DMFCActiveXControl5Events * This,
            /* [in] */ REFIID riid,
            /* [annotation][iid_is][out] */ 
            _COM_Outptr_  void **ppvObject);
        
        ULONG ( STDMETHODCALLTYPE *AddRef )( 
            _DMFCActiveXControl5Events * This);
        
        ULONG ( STDMETHODCALLTYPE *Release )( 
            _DMFCActiveXControl5Events * This);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfoCount )( 
            _DMFCActiveXControl5Events * This,
            /* [out] */ UINT *pctinfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetTypeInfo )( 
            _DMFCActiveXControl5Events * This,
            /* [in] */ UINT iTInfo,
            /* [in] */ LCID lcid,
            /* [out] */ ITypeInfo **ppTInfo);
        
        HRESULT ( STDMETHODCALLTYPE *GetIDsOfNames )( 
            _DMFCActiveXControl5Events * This,
            /* [in] */ REFIID riid,
            /* [size_is][in] */ LPOLESTR *rgszNames,
            /* [range][in] */ UINT cNames,
            /* [in] */ LCID lcid,
            /* [size_is][out] */ DISPID *rgDispId);
        
        /* [local] */ HRESULT ( STDMETHODCALLTYPE *Invoke )( 
            _DMFCActiveXControl5Events * This,
            /* [annotation][in] */ 
            _In_  DISPID dispIdMember,
            /* [annotation][in] */ 
            _In_  REFIID riid,
            /* [annotation][in] */ 
            _In_  LCID lcid,
            /* [annotation][in] */ 
            _In_  WORD wFlags,
            /* [annotation][out][in] */ 
            _In_  DISPPARAMS *pDispParams,
            /* [annotation][out] */ 
            _Out_opt_  VARIANT *pVarResult,
            /* [annotation][out] */ 
            _Out_opt_  EXCEPINFO *pExcepInfo,
            /* [annotation][out] */ 
            _Out_opt_  UINT *puArgErr);
        
        END_INTERFACE
    } _DMFCActiveXControl5EventsVtbl;

    interface _DMFCActiveXControl5Events
    {
        CONST_VTBL struct _DMFCActiveXControl5EventsVtbl *lpVtbl;
    };

    

#ifdef COBJMACROS


#define _DMFCActiveXControl5Events_QueryInterface(This,riid,ppvObject)	\
    ( (This)->lpVtbl -> QueryInterface(This,riid,ppvObject) ) 

#define _DMFCActiveXControl5Events_AddRef(This)	\
    ( (This)->lpVtbl -> AddRef(This) ) 

#define _DMFCActiveXControl5Events_Release(This)	\
    ( (This)->lpVtbl -> Release(This) ) 


#define _DMFCActiveXControl5Events_GetTypeInfoCount(This,pctinfo)	\
    ( (This)->lpVtbl -> GetTypeInfoCount(This,pctinfo) ) 

#define _DMFCActiveXControl5Events_GetTypeInfo(This,iTInfo,lcid,ppTInfo)	\
    ( (This)->lpVtbl -> GetTypeInfo(This,iTInfo,lcid,ppTInfo) ) 

#define _DMFCActiveXControl5Events_GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId)	\
    ( (This)->lpVtbl -> GetIDsOfNames(This,riid,rgszNames,cNames,lcid,rgDispId) ) 

#define _DMFCActiveXControl5Events_Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr)	\
    ( (This)->lpVtbl -> Invoke(This,dispIdMember,riid,lcid,wFlags,pDispParams,pVarResult,pExcepInfo,puArgErr) ) 

#endif /* COBJMACROS */


#endif 	/* C style interface */


#endif 	/* ___DMFCActiveXControl5Events_DISPINTERFACE_DEFINED__ */


EXTERN_C const CLSID CLSID_MFCActiveXControl5;

#ifdef __cplusplus

class DECLSPEC_UUID("CEAA8AEA-3D39-4CCE-A9D9-E948F6455D33")
MFCActiveXControl5;
#endif
#endif /* __MFCActiveXControl5Lib_LIBRARY_DEFINED__ */

/* Additional Prototypes for ALL interfaces */

/* end of Additional Prototypes */

#ifdef __cplusplus
}
#endif

#endif


