//PACKAGE COM.HZTUEN.LVYOU.UTILS;
//
//IMPORT COM.GOOGLE.GSON.GSON;
//IMPORT COM.GOOGLE.GSON.REFLECT.TYPETOKEN;
//
//IMPORT ANDROID.OS.ENVIRONMENT;
//IMPORT ANDROID.TEXT.TEXTUTILS;
//IMPORT ANDROID.UTIL.LOG;
//
///**
// * 璋冭瘯鐢?
// * 
// * @AUTHOR HUANGLB
// * 
// */
//PUBLIC CLASS LOGUTIL {
//    // 鍏抽棴DEBUG
//    PUBLIC STATIC FINAL BOOLEAN DEBUBABLE = TRUE;
//
//    PRIVATE STATIC FINAL STRING TAG = "BIRDSLOVE";
//
//    /**
//     * 淇濆瓨鏃ュ織鍒版枃浠朵腑
//     */
//    PRIVATE FINAL BOOLEAN ISSAVETOFILE = FALSE;
//    
//    /**
//     * 鏃ュ織淇濆瓨璺緞锛宻DCARD/(UMS_APP_NAME璁剧疆鐨勫悕绉?/LOG.TXT
//     */
//    PRIVATE STATIC FINAL STRING LOG_DIR = ENVIRONMENT.GETEXTERNALSTORAGEDIRECTORY().GETABSOLUTEPATH() + "/BIRDSLOVE/";
//    PRIVATE STATIC FINAL STRING LOG_PATH = LOG_DIR + "LOG.TXT";
//    
//    PRIVATE STATIC LOGUTIL INSTANCE = NEW LOGUTIL();
//
//    PUBLIC STATIC LOGUTIL GETLOGGER() {
//        RETURN INSTANCE;
//    }
//
//    /**
//     * LOG.I
//     */
//    PUBLIC VOID INFO(STRING MSG) {
//        IF (DEBUBABLE) {
//            FINAL STRING MESSAGE = CREATEMESSAGE(MSG);
//            LOG.I(TAG, MESSAGE);
//        }
//    }
//
//    PUBLIC STATIC VOID I(STRING MSG) {
//        INSTANCE.INFO(MSG);
//    }
//    /**
//     * @AUTHOR HUANGLB
//     * @PARAM MSG
//     */
//    PUBLIC STATIC VOID I(STRING TAG,STRING MSG) {
//        INSTANCE.INFO(TAG,MSG);
//    }
//
//    PUBLIC STATIC VOID I(STRING TAG, OBJECT MSGOBJ) {
//        IF (MSGOBJ != NULL) {
//            TRY {
//                FINAL GSON GSON = NEW GSON();   
//                FINAL STRING OBJLOG = GSON.TOJSON(MSGOBJ, NEW TYPETOKEN<OBJECT>() {}.GETTYPE());
//                INSTANCE.INFO(TAG, OBJLOG);
//            } CATCH (EXCEPTION E) {
//                E.PRINTSTACKTRACE();
//            }
//        }
//    }
//    
//    PUBLIC VOID INFO(STRING TAG,STRING MSG) {
//        IF (DEBUBABLE) {
//            FINAL STRING MESSAGE = CREATEMESSAGE(MSG);
//            LOG.I(TAG, MESSAGE);
//        }
//    }
//    
//    PUBLIC STATIC VOID I(EXCEPTION E) {
//        INSTANCE.INFO(E != NULL ? E.TOSTRING() : "NULL");
//    }
//
//    /**
//     * LOG.V
//     */
//    PUBLIC VOID VERBOSE(STRING MSG) {
//        IF (DEBUBABLE) {
//            FINAL STRING MESSAGE = CREATEMESSAGE(MSG);
//            LOG.V(TAG, MESSAGE);
//        }
//    }
//    
//    PUBLIC VOID VERBOSE(STRING TAG, STRING MSG) {
//        IF (DEBUBABLE) {
//            FINAL STRING MESSAGE = CREATEMESSAGE(MSG);
//            LOG.V(TAG, MESSAGE);
//        }
//    }
//
//    PUBLIC STATIC VOID V(STRING MSG) {
//        INSTANCE.VERBOSE(MSG);
//    }
//    
//    PUBLIC STATIC VOID V(STRING TAG, STRING MSG) {
//        INSTANCE.VERBOSE(TAG, MSG);
//    }
//    
//    PUBLIC STATIC VOID V(STRING TAG, OBJECT MSGOBJ) {
//        IF (MSGOBJ != NULL) {
//            TRY {
//                FINAL GSON GSON = NEW GSON();   
//                FINAL STRING OBJLOG = GSON.TOJSON(MSGOBJ, NEW TYPETOKEN<OBJECT>() {}.GETTYPE());
//                INSTANCE.VERBOSE(TAG, OBJLOG);
//            } CATCH (EXCEPTION E) {
//                E.PRINTSTACKTRACE();
//            }
//        }
//    }
//    
//    PUBLIC STATIC VOID V(EXCEPTION E) {
//        INSTANCE.VERBOSE(E != NULL ? E.TOSTRING() : "NULL");
//    }
//
//    /**
//     * LOG.D
//     */
//    PUBLIC VOID DEBUG(STRING MSG) {
//        IF (DEBUBABLE) {
//            FINAL STRING MESSAGE = CREATEMESSAGE(MSG);
//            LOG.D(TAG, MESSAGE);
//        }
//    }
//    
//    PUBLIC VOID DEBUG(STRING TAG, STRING MSG) {
//        IF (DEBUBABLE) {
//            FINAL STRING MESSAGE = CREATEMESSAGE(MSG);
//            LOG.D(TAG, MESSAGE);
//        }
//    }
//
//    PUBLIC STATIC VOID D(STRING MSG) {
//        INSTANCE.DEBUG(MSG);
//    }
//    
//    PUBLIC STATIC VOID D(STRING TAG, STRING MSG) {
//        INSTANCE.DEBUG(TAG, MSG);
//    }
//    
//    PUBLIC STATIC VOID D(STRING TAG, OBJECT MSGOBJ) {
//        IF (MSGOBJ != NULL) {
//            TRY {
//                FINAL GSON GSON = NEW GSON();   
//                FINAL STRING OBJLOG = GSON.TOJSON(MSGOBJ, NEW TYPETOKEN<OBJECT>() {}.GETTYPE());
//                INSTANCE.DEBUG(TAG, OBJLOG);
//            } CATCH (EXCEPTION E) {
//                E.PRINTSTACKTRACE();
//            }
//        }
//    }
//
//    PUBLIC STATIC VOID D(EXCEPTION E) {
//        INSTANCE.DEBUG(E != NULL ? E.TOSTRING() : "NULL");
//    }
//
//    /**
//     * LOG.WARN
//     * 
//     * @PARAM MSG
//     */
//    PUBLIC VOID WARN(STRING MSG) {
//        IF (DEBUBABLE) {
//            FINAL STRING MESSAGE = CREATEMESSAGE(MSG);
//            LOG.W(TAG, MESSAGE);
//        }
//    }
//
//    PUBLIC STATIC VOID W(STRING MSG) {
//        INSTANCE.WARN(MSG);
//    }
//
//    PUBLIC STATIC VOID W(EXCEPTION E) {
//        INSTANCE.WARN(E != NULL ? E.TOSTRING() : "NULL");
//    }
//    
//    /**
//     * LOG.E
//     * 
//     * @PARAM MSG
//     */
//    PUBLIC VOID ERROR(STRING MSG) {
//        IF (DEBUBABLE) {
//            FINAL STRING MESSAGE = CREATEMESSAGE(MSG);
//            LOG.E(TAG, MESSAGE);
//        }
//    }
//    
//    PUBLIC VOID ERROR(STRING TAG, STRING MSG) {
//        IF (DEBUBABLE) {
//            FINAL STRING MESSAGE = CREATEMESSAGE(MSG);
//            LOG.E(TAG, MESSAGE);
//        }
//    }
//    
//    PUBLIC STATIC VOID E(STRING MSG) {
//        INSTANCE.ERROR(MSG);
//    }
//    
//    PUBLIC STATIC VOID E(STRING TAG, STRING MSG) {
//        INSTANCE.ERROR(TAG, MSG);
//    }
//    
//    PUBLIC STATIC VOID E(STRING TAG, OBJECT MSGOBJ) {
//        IF (MSGOBJ != NULL) {
//            TRY {
//                FINAL GSON GSON = NEW GSON();   
//                FINAL STRING OBJLOG = GSON.TOJSON(MSGOBJ, NEW TYPETOKEN<OBJECT>() {}.GETTYPE());
//                INSTANCE.ERROR(TAG, OBJLOG);
//            } CATCH (EXCEPTION E) {
//                E.PRINTSTACKTRACE();
//            }
//        }
//    }
//    
//    PUBLIC STATIC VOID E(EXCEPTION E) {
//        INSTANCE.ERROR(E);
//    }
//
//    /**
//     * LOG.ERROR
//     * 
//     * @PARAM E
//     */
//    PUBLIC VOID ERROR(EXCEPTION E) {
//        IF (DEBUBABLE) {
//            FINAL STRINGBUFFER SB = NEW STRINGBUFFER();
//            FINAL STRING NAME = GETFUNCTIONNAME();
//            FINAL STACKTRACEELEMENT[] STS = E.GETSTACKTRACE();
//
//            IF (NAME != NULL) {
//                SB.APPEND(NAME + " - " + E + "\R\N");
//            } ELSE {
//                SB.APPEND(E + "\R\N");
//            }
//            IF (STS != NULL && STS.LENGTH > 0) {
//                FOR (FINAL STACKTRACEELEMENT ST : STS) {
//                    IF (ST != NULL) {
//                        SB.APPEND("[ " + ST.GETFILENAME() + ":" + ST.GETLINENUMBER() + " ]\R\N");
//                    }
//                }
//            }
//            LOG.E(TAG, SB.TOSTRING());
//        }
//    }
//    
//    /**
//     * 鑾峰彇鍑芥暟鍚嶇О
//     */
//    PRIVATE STRING GETFUNCTIONNAME() {
//        FINAL STACKTRACEELEMENT[] STS = THREAD.CURRENTTHREAD().GETSTACKTRACE();
//
//        IF (STS == NULL) {
//            RETURN NULL;
//        }
//
//        FOR (FINAL STACKTRACEELEMENT ST : STS) {
//            IF (ST.ISNATIVEMETHOD()) {
//                CONTINUE;
//            }
//
//            IF (ST.GETCLASSNAME().EQUALS(THREAD.CLASS.GETNAME())) {
//                CONTINUE;
//            }
//
//            IF (ST.GETCLASSNAME().EQUALS(THIS.GETCLASS().GETNAME())) {
//                CONTINUE;
//            }
//
//            RETURN "[" + THREAD.CURRENTTHREAD().GETNAME() + "("
//                    + THREAD.CURRENTTHREAD().GETID() + "): " + ST.GETFILENAME()
//                    + ":" + ST.GETLINENUMBER() + "]";
//        }
//
//        RETURN NULL;
//    }
//
//    PRIVATE STRING CREATEMESSAGE(STRING MSG) {
//        IF (TEXTUTILS.ISEMPTY(MSG)) {
//            RETURN NULL;
//        }
//        
//        FINAL STRING FUNCTIONNAME = GETFUNCTIONNAME();
//        FINAL STRING MESSAGE = (FUNCTIONNAME == NULL ? MSG : (FUNCTIONNAME + " - " + MSG));
//        
//        IF (ISSAVETOFILE) {
//            FILEUTILS.WRITESTRINGTOFILE(MSG + "\R\N", LOG_DIR, LOG_PATH, TRUE);
//        }
//        
//        RETURN MESSAGE;
//    }
//    
//    /**
//     * 淇濆瓨淇℃伅鍒版枃浠?
//     * 
//     * @PARAM MSG
//     */
//    PUBLIC STATIC VOID SAVEMESSAGE(STRING MSG) {
//        IF (DEBUBABLE) {
//            FILEUTILS.WRITESTRINGTOFILE(MSG + "\R\N", LOG_PATH, TRUE);
//        }
//    }
//
//}
