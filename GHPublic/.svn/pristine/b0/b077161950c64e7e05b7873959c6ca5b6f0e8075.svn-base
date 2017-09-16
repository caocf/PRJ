package com.hztuen.lvyou.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.kymjs.kjframe.http.HttpParams;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

public class Util {
	public static final String TAG = Util.class.getSimpleName();

	/***
	 * 鐢熸垚 绛惧悕
	 * @param attributes
	 * @param key
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String sign(List<String> attributes) throws UnsupportedEncodingException{
//		String key = Contact.key;
//		Object[] os = attributes.toArray();
//		Arrays.sort(os);
//		StringBuffer stringBuffer = new StringBuffer();
//		for(int i = 0; i < os.length; i++){
//			if(i > 0){
//				stringBuffer.append("&");
//			}
//			stringBuffer.append(os[i]);
//		}
//		//key 涓虹害瀹氬瓧
//
//		stringBuffer.append(key);
//		Log.i("key", key);
//		String content = stringBuffer.toString();
		String sign = "";
//		//md5鍔犲瘑
//		//			sign = DigestUtils.md5DigestAsHex(content.getBytes("UTF-8")).toString();
//		Log.i("sign", content);
//		sign= md5(content);
//		Log.i("sign", sign);
		return sign;
	}



	/**
	 * 涓篕J 鎻愪緵鍙傛暟
	 * HttpParams params
	 * 
	 * @param attributes
	 * @return
	 * @throws Exception
	 */
	public static HttpParams prepareKJparams(List<String> attributes) throws Exception{
		HttpParams params = new HttpParams();
		for (int i = 0; i < attributes.size(); i++) {
			String[] temp = attributes.get(i).split("=");
			if(temp.length==1)
			{
				params.put(temp[0],"");
			}else{
			params.put(temp[0], temp[1]);
			}
		}
//		params.put("sign", sign(attributes));
		return params;
	}

	/**
	 * 涓篈BTASK 鎻愪緵鍙傛暟
	 * 
	 * List<NameValuePair> params
	 * @param attributes
	 * @return
	 * @throws Exception
	 */
	public static List<NameValuePair> prepareABTASKparams(List<String> attributes) throws Exception{
		List<NameValuePair> params = new ArrayList<NameValuePair>(); 
		for (int i = 0; i < attributes.size(); i++) {
			String[] temp = attributes.get(i).split("=");
			params.add(new BasicNameValuePair(temp[0], temp[1])); 
		}
		params.add(new BasicNameValuePair("sign", sign(attributes))); 
		return params;
	}

	/**
	 * 涓簃AbHttpUtil鎻愪緵鍙傛暟
	 * 
	 * 	AbRequestParams params
	 * @param attributes
	 * @return
	 * @throws UnsupportedEncodingException
	 */
//	public static AbRequestParams preparemAbHttpUtilparams(List<String> attributes) throws UnsupportedEncodingException{
//		AbRequestParams params = new AbRequestParams();
//		for (int i = 0; i < attributes.size(); i++) {
//			String[] temp = attributes.get(i).split("=");
//			params.put(temp[0], temp[1]);
//		}
//		params.put("sign", sign(attributes));
//		return params;
//	}



	/**
	 * md5 鍔犲瘑
	 * @param string
	 * @return
	 */
	public static String md5(String string) {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Huh, UTF-8 should be supported?", e);
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10) hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}
		return hex.toString();
	}


	/**
	 * 缁欏嚭鎻愮ず
	 * 
	 * @param context
	 * @param userError
	 */
	public static void getTip(Context context,String userError){
		Toast.makeText(context, userError, Toast.LENGTH_SHORT).show();
	}



	/**
	 * java 鍒ゆ柇 瀛楃涓叉槸鍚︿负鏁板瓧1
	 * 鐢↗AVA鑷甫鐨勫嚱鏁?
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str){
		for (int i = 0; i < str.length(); i++){
			System.out.println(str.charAt(i));
			if (!Character.isDigit(str.charAt(i))){
				return false;
			}
		}
		return true;
	}



	/**
	 *  java 鍒ゆ柇 瀛楃涓叉槸鍚︿负鏁板瓧2
	 * 鐢ㄦ鍒欒〃杈惧紡
	 * 	棣栧厛瑕乮mport java.util.regex.Pattern 鍜?java.util.regex.Matcher
	 */
	public boolean isNumeric2(String str){ 
		Pattern pattern = Pattern.compile("[0-9]*"); 
		Matcher isNum = pattern.matcher(str);
		if( !isNum.matches() ){
			return false; 
		} 
		return true; 
	}


	/**
	 * 
	 * @return
	 */
	public static String remain2num(BigDecimal b){
		return new java.text.DecimalFormat("0.00").format(b);
		//		return new java.text.DecimalFormat("#.00").format(b);
	}


	/** 
	 * 鏍规嵁鎵嬫満鐨勫垎杈ㄧ巼浠?dp 鐨勫崟浣?杞垚涓?px(鍍忕礌) 
	 */  
	public static int dip2px(Context context, float dpValue) {  
		final float scale = context.getResources().getDisplayMetrics().density;  
		return (int) (dpValue * scale + 0.5f);  
	}  

	/** 
	 * 鏍规嵁鎵嬫満鐨勫垎杈ㄧ巼浠?px(鍍忕礌) 鐨勫崟浣?杞垚涓?dp 
	 */  
	public static int px2dip(Context context, float pxValue) {  
		final float scale = context.getResources().getDisplayMetrics().density;  
		return (int) (pxValue / scale + 0.5f);  
	}  


	/**
	 * 鍥剧墖璐ㄩ噺鍘嬬缉
	 * 
	 * @param image
	 * @return
	 */
	public static Bitmap compressImage(Bitmap image) {  
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//璐ㄩ噺鍘嬬缉鏂规硶锛岃繖閲?00琛ㄧず涓嶅帇缂╋紝鎶婂帇缂╁悗鐨勬暟鎹瓨鏀惧埌baos涓? 
		int options = 100;  
		while ( baos.toByteArray().length / 1024>500) {  //寰幆鍒ゆ柇濡傛灉鍘嬬缉鍚庡浘鐗囨槸鍚﹀ぇ浜?00kb,澶т簬缁х画鍘嬬缉         
			baos.reset();//閲嶇疆baos鍗虫竻绌篵aos  
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//杩欓噷鍘嬬缉options%锛屾妸鍘嬬缉鍚庣殑鏁版嵁瀛樻斁鍒癰aos涓? 
			options -= 10;//姣忔閮藉噺灏?0  
		}  
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//鎶婂帇缂╁悗鐨勬暟鎹産aos瀛樻斁鍒癇yteArrayInputStream涓? 
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//鎶夿yteArrayInputStream鏁版嵁鐢熸垚鍥剧墖  
		return bitmap;  
	}  



	/**
	 * 鍥剧墖鎸夋瘮渚嬪ぇ灏忓帇缂╂柟娉曪紙鏍规嵁璺緞鑾峰彇鍥剧墖骞跺帇缂╋級
	 * 
	 * @param srcPath
	 * @return
	 */
	public static Bitmap getimage(String srcPath) {  
		BitmapFactory.Options newOpts = new BitmapFactory.Options();  
		//寮?璇诲叆鍥剧墖锛屾鏃舵妸options.inJustDecodeBounds 璁惧洖true浜? 
		newOpts.inJustDecodeBounds = true;  
		Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//姝ゆ椂杩斿洖bm涓虹┖  

		newOpts.inJustDecodeBounds = false;  
		int w = newOpts.outWidth;  
		int h = newOpts.outHeight;  
		//鐜板湪涓绘祦鎵嬫満姣旇緝澶氭槸800*480鍒嗚鲸鐜囷紝鎵?互楂樺拰瀹芥垜浠缃负  
		float hh = 800f;//杩欓噷璁剧疆楂樺害涓?00f  
		float ww = 480f;//杩欓噷璁剧疆瀹藉害涓?80f  
		//缂╂斁姣斻?鐢变簬鏄浐瀹氭瘮渚嬬缉鏀撅紝鍙敤楂樻垨鑰呭鍏朵腑涓?釜鏁版嵁杩涜璁＄畻鍗冲彲  
		int be = 1;//be=1琛ㄧず涓嶇缉鏀? 
		if (w > h && w > ww) {//濡傛灉瀹藉害澶х殑璇濇牴鎹搴﹀浐瀹氬ぇ灏忕缉鏀? 
			be = (int) (newOpts.outWidth / ww);  
		} else if (w < h && h > hh) {//濡傛灉楂樺害楂樼殑璇濇牴鎹搴﹀浐瀹氬ぇ灏忕缉鏀? 
			be = (int) (newOpts.outHeight / hh);  
		}  
		if (be <= 0)  
			be = 1;  
		newOpts.inSampleSize = be;//璁剧疆缂╂斁姣斾緥  
		//閲嶆柊璇诲叆鍥剧墖锛屾敞鎰忔鏃跺凡缁忔妸options.inJustDecodeBounds 璁惧洖false浜? 
		bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
		return compressImage(bitmap);//鍘嬬缉濂芥瘮渚嬪ぇ灏忓悗鍐嶈繘琛岃川閲忓帇缂? 
	}  




	/**
	 * 鍥剧墖鎸夋瘮渚嬪ぇ灏忓帇缂╂柟娉曪紙鏍规嵁Bitmap鍥剧墖鍘嬬缉锛夛細
	 * 
	 * @param image
	 * @return
	 */
	public static Bitmap comp(Bitmap image) {  
		ByteArrayOutputStream baos = new ByteArrayOutputStream();         
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
		if( baos.toByteArray().length / 1024>1024) {//鍒ゆ柇濡傛灉鍥剧墖澶т簬1M,杩涜鍘嬬缉閬垮厤鍦ㄧ敓鎴愬浘鐗囷紙BitmapFactory.decodeStream锛夋椂婧㈠嚭    
			baos.reset();//閲嶇疆baos鍗虫竻绌篵aos  
			image.compress(Bitmap.CompressFormat.JPEG, 80, baos);//杩欓噷鍘嬬缉50%锛屾妸鍘嬬缉鍚庣殑鏁版嵁瀛樻斁鍒癰aos涓? 
		}  
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());  
		BitmapFactory.Options newOpts = new BitmapFactory.Options();  
		//寮?璇诲叆鍥剧墖锛屾鏃舵妸options.inJustDecodeBounds 璁惧洖true浜? 
		newOpts.inJustDecodeBounds = true;  
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
		newOpts.inJustDecodeBounds = false;  
		int w = newOpts.outWidth;  
		int h = newOpts.outHeight;  
		//鐜板湪涓绘祦鎵嬫満姣旇緝澶氭槸800*480鍒嗚鲸鐜囷紝鎵?互楂樺拰瀹芥垜浠缃负  
		float hh = 800f;//杩欓噷璁剧疆楂樺害涓?00f  
		float ww = 480f;//杩欓噷璁剧疆瀹藉害涓?80f  
		//缂╂斁姣斻?鐢变簬鏄浐瀹氭瘮渚嬬缉鏀撅紝鍙敤楂樻垨鑰呭鍏朵腑涓?釜鏁版嵁杩涜璁＄畻鍗冲彲  
		int be = 1;//be=1琛ㄧず涓嶇缉鏀? 
		if (w > h && w > ww) {//濡傛灉瀹藉害澶х殑璇濇牴鎹搴﹀浐瀹氬ぇ灏忕缉鏀? 
			be = (int) (newOpts.outWidth / ww);  
		} else if (w < h && h > hh) {//濡傛灉楂樺害楂樼殑璇濇牴鎹搴﹀浐瀹氬ぇ灏忕缉鏀? 
			be = (int) (newOpts.outHeight / hh);  
		}  
		if (be <= 0)  
			be = 1;  
		newOpts.inSampleSize = be;//璁剧疆缂╂斁姣斾緥  
		//閲嶆柊璇诲叆鍥剧墖锛屾敞鎰忔鏃跺凡缁忔妸options.inJustDecodeBounds 璁惧洖false浜? 
		isBm = new ByteArrayInputStream(baos.toByteArray());  
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
		return compressImage(bitmap);//鍘嬬缉濂芥瘮渚嬪ぇ灏忓悗鍐嶈繘琛岃川閲忓帇缂? 
	}  



	/**
	 * 灏嗗浘鐗囦繚瀛樺埌鏈湴鏂囦欢澶?
	 * 
	 * @param bm
	 * @param picName
	 */
	public static String saveBitmap(Bitmap bm,String picName) {
		Log.e(TAG, "淇濆瓨鍥剧墖");
		File sdDir1 = null;
		sdDir1 = Environment.getExternalStorageDirectory();//鑾峰彇璺熺洰褰?
		File file =null;
		Log.e(TAG, sdDir1.toString());
		if(null != sdDir1) {
			file = new File(sdDir1.toString()+"/com/hztuen/tushu/images/");
		}else {
			file = new File("/storage/sdcard0/com/hztuen/tushu/images/");
		}

		if(!file.exists()){
			//鐩綍鏄惁瀛樺湪锛屼笉瀛樺湪锛屽垯鍒涘缓鐩綍
			file.mkdirs();
		}

		File f = new File(sdDir1.toString()+"/com/hztuen/tushu/images/", picName+".png");
		if (f.exists()) {
			//鏂囦欢鏄惁瀛樺湪锛屽瓨鍦ㄥ嵆鍒犻櫎鍘熸潵鐨勬枃浠?
			f.delete();
		}

		try {
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.PNG, 100, out);
			out.flush();
			out.close();
			Log.i(TAG, "宸茬粡淇濆瓨");
			return sdDir1.toString()+"/com/hztuen/tushu/images/"+picName+".png";
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}


	/**
	 * 灏唋ist 杞瓧绗︿覆  浠ヤ究鍦ㄨ交閲忕骇淇濆瓨
	 * @param search_List
	 * @return
	 * @throws IOException
	 */
	public static String SceneList2String(List<?> search_List) throws IOException {
		// 瀹炰緥鍖栦竴涓狟yteArrayOutputStream瀵硅薄锛岀敤鏉ヨ杞藉帇缂╁悗鐨勫瓧鑺傛枃浠躲?
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		// 鐒跺悗灏嗗緱鍒扮殑瀛楃鏁版嵁瑁呰浇鍒癘bjectOutputStream
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				byteArrayOutputStream);
		// writeObject 鏂规硶璐熻矗鍐欏叆鐗瑰畾绫荤殑瀵硅薄鐨勭姸鎬侊紝浠ヤ究鐩稿簲鐨?readObject 鏂规硶鍙互杩樺師瀹?
		objectOutputStream.writeObject(search_List);
		// 鏈?悗锛岀敤Base64.encode灏嗗瓧鑺傛枃浠惰浆鎹㈡垚Base64缂栫爜淇濆瓨鍦⊿tring涓?
		String SceneListString = new String(Base64.encode(
				byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
		// 鍏抽棴objectOutputStream
		objectOutputStream.close();
		return SceneListString;
	}


	/**
	 * 
	 * @param SceneListString
	 * @return
	 * @throws StreamCorruptedException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("rawtypes")
	public static List String2SceneList(String SceneListString)
			throws StreamCorruptedException, IOException,
			ClassNotFoundException {
		byte[] mobileBytes = Base64.decode(SceneListString.getBytes(),
				Base64.DEFAULT);
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				mobileBytes);
		ObjectInputStream objectInputStream = new ObjectInputStream(
				byteArrayInputStream);
		List SceneList = (List) objectInputStream
				.readObject();
		objectInputStream.close();
		return SceneList;
	}
}
