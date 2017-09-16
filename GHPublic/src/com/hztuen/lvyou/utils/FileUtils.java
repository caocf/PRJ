//package com.hztuen.lvyou.utils;
//
//import java.io.BufferedReader;
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.List;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.Bitmap.CompressFormat;
//import android.os.Environment;
//import android.os.StatFs;
//import android.text.TextUtils;
//
///**
// * 
// ******************************************
// * @鏂囦欢鍚嶇О	:  FileUtils.java
// * @鍒涘缓鏃堕棿	: 2015/11/19
// * @鏂囦欢鎻忚堪	: 鏂囦欢宸ュ叿绫� ******************************************
// */
//public class FileUtils {
//	private final static String TAG = "FileUtils";
//	
//	/**
//	 * 璇诲彇琛ㄦ儏閰嶇疆鏂囦欢
//	 * 
//	 * @param context
//	 * @return
//	 */
//	public static List<String> getEmojiFile(Context context) {
//		try {
//			List<String> list = new ArrayList<String>();
//			InputStream in = context.getResources().getAssets().open("emoji"); 
//			BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
//			String str = null;
//			while ((str = br.readLine()) != null) {
//				list.add(str);
//			}
//
//			return list;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
////	/**
////	 * 璇诲彇绀肩墿閰嶇疆鏂囦欢
////	 * 
////	 * @param context
////	 * @return
////	 */
////	public static List<ChatGift> getGiftFile(Context context) {
////		 
////			String resture = Util.getFromAssets(context,"gift.json");
////			Gson gson = new Gson();
////		    ChatGiftInfo mPersonInfo = gson.fromJson(resture, ChatGiftInfo.class);
////			return mPersonInfo.getList();
////		 
////	}
//	
//	/**
//	 * 妫�煡SD鍗℃槸鍚﹀瓨鍦�	 * 
//	 * @return
//	 */
//	public static boolean checkSDCard() {
//		final String status = Environment.getExternalStorageState();
//		if (Environment.MEDIA_MOUNTED.equals(status)) {
//			return true;
//		}
//		return false;
//	}
//
//	
//	/**
//	 * 鑾峰彇鍙互浣跨敤鐨勭紦瀛樼洰褰�榛樿鐩綍鍚�yyApp/)
//	 * @param context
//	 * @return
//	 */
//	public static File getDiskCacheDir(Context context) {
//		final String cachePath = checkSDCard() ? getExternalCacheDir(context).getPath() : getAppCacheDir(context);
//
//        File cacheDirFile = new File(cachePath);
//        if (!cacheDirFile.exists()) {
//            cacheDirFile.mkdirs();
//        }
//
//        return cacheDirFile;
//	}
//	 
//	
//    /**
//     * 鑾峰彇绋嬪簭澶栭儴鐨勭紦瀛樼洰褰�     * @param context
//     * @return
//     */
//     public static File getExternalCacheDir(Context context) {
//     	// 杩欎釜sd鍗′腑鏂囦欢璺緞涓嬬殑鍐呭浼氶殢鐫�紝绋嬪簭鍗歌浇鎴栬�璁剧疆涓竻闄ょ紦瀛樺悗涓�捣娓呯┖
//         final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
//         return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
//     }
//     
// 	/**
// 	 * 鑾峰彇瀹夎鍦ㄧ敤鎴锋墜鏈轰笂鐨刢om.youyuan.yyapp涓嬬殑cache鐩綍
// 	 * 
// 	 * @return cache path
// 	 */
// 	public static String getAppCacheDir(Context context) {
// 		return context.getCacheDir().getPath();
// 	}
// 	
//    /**
//     * 鑾峰彇鏂囦欢鍚�     * 
//     * @param path
//     *            鍏ㄨ矾寰�     * @return
//     */
//    public static String getFileName(String path) {
//    	if (!TextUtils.isEmpty(path)) {
//    		return path.substring(path.lastIndexOf(File.separator) + 1);
//    	}
//    	return "";
//    }
//    
//    /**
//     * 鑾峰彇鏂囦欢璺緞绌洪棿澶у皬
//     * @param path
//     * @return
//     */
//    public static long getUsableSpace(File path) {
//    	try{
//    		 final StatFs stats = new StatFs(path.getPath());
//    	     return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
//    	}catch (Exception e) {
//			e.printStackTrace();
//			return -1;
//		}
//       
//    }
//
//    /**
//	 * 绌洪棿澶у皬鍗曚綅鏍煎紡鍖�	 * @param size
//	 * @return
//	 */
//	public static String formatSize(long size) {
//        String suffix = null;
//        float fSize=0;
//
//        if (size >= 1024) {
//            suffix = "KB";
//            fSize=size / 1024;
//            if (fSize >= 1024) {
//                suffix = "MB";
//                fSize /= 1024;
//            }
//            if (fSize >= 1024) {
//                suffix = "GB";
//                fSize /= 1024;
//            }
//        } else {
//            fSize = size;
//        }
//        
//        java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");
//        StringBuilder resultBuffer = new StringBuilder(df.format(fSize));
//        if (suffix != null)
//            resultBuffer.append(suffix);
//        return resultBuffer.toString();
//    }
// 	
// 	/**
//     * 鑾峰彇鏂囦欢鎵�湪鐨勬枃浠惰矾寰�     * 
//     * @param path
//     * @return
//     */
//    public static String getFilePath(String path) {
//        return path.substring(0, path.lastIndexOf(File.separator) + 1);
//    }
//    
//    /**
//	 * 鍒犻櫎鏂囦欢
//	 * 
//	 * @param filePath
//	 * @return
//	 */
//	public static boolean deleteFile(String filePath) {
//	    if (LogUtil.DEBUBABLE) {
//	        LogUtil.e(TAG,"deleteFile path " + filePath);
//	    }
//		
//		if (!TextUtils.isEmpty(filePath)) {
//			final File file = new File(filePath);
//			if (file.exists()) {
//				return file.delete();
//			}
//		}
//		return false;
//	}
//	
//	public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
//		ByteArrayOutputStream output = new ByteArrayOutputStream();
//		bmp.compress(CompressFormat.JPEG, 100, output);
//		if (needRecycle) {
//			bmp.recycle();
//		}
//		
//		byte[] result = output.toByteArray();
//		try {
//			output.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return result;
//	}
//	
//	
//	/**
//     * 淇濆瓨鏂囦欢
//     * 
//     * @param sToSave
//     * @param sFileName
//     * @param isAppend
//     * @return
//     */
//    public static boolean writeStringToFile(String content, String fileName, boolean isAppend) {
//        return writeStringToFile(content, "", fileName, isAppend);
//    }
//
//    
//    public static boolean writeStringToFile(String content,
//            String directoryPath, String fileName, boolean isAppend) {
//        if (!TextUtils.isEmpty(content)) {
//            if (!TextUtils.isEmpty(directoryPath)) {// 鏄惁闇�鍒涘缓鏂扮殑鐩綍
//                final File threadListFile = new File(directoryPath);
//                if (!threadListFile.exists()) {
//                    threadListFile.mkdirs();
//                }
//            }
//            boolean bFlag = false;
//            final int iLen = content.length();
//            final File file = new File(fileName);
//            try {
//                if (!file.exists()) {
//                    file.createNewFile();
//                }
//                final FileOutputStream fos = new FileOutputStream(file,
//                        isAppend);
//                byte[] buffer = new byte[iLen];
//                try {
//                    buffer = content.getBytes();
//                    fos.write(buffer);
//                    if (isAppend) {
//                        fos.write("||".getBytes());
//                    }
//                    fos.flush();
//                    bFlag = true;
//                } catch (IOException ioex) {
//                    if (LogUtil.DEBUBABLE) {
//                        ioex.printStackTrace();
//                    }
//                } finally {
//                    fos.close();
//                    buffer = null;
//                }
//            } catch (Exception ex) {
//                if (LogUtil.DEBUBABLE) {
//                    ex.printStackTrace();
//                }
//            } catch (OutOfMemoryError o) {
//                if (LogUtil.DEBUBABLE) {
//                    o.printStackTrace();
//                }
//            }
//            return bFlag;
//        }
//        return false;
//    }
//}
