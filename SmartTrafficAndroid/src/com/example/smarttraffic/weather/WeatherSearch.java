package com.example.smarttraffic.weather;

import com.alibaba.fastjson.JSON;
import com.example.smarttraffic.network.BaseSearch;

/**
 * 天气信息获取、解析
 * @author Administrator zhou
 *
 */
public class WeatherSearch extends BaseSearch
{
	@Override
	public Object parse(String data)
	{
		return JSON.parseObject(JSON.parseObject(data).getJSONObject("retData").toJSONString(), Weather2.class);
	}

//	public static Bitmap getHttpBitmap(String url){
//        URL myFileURL;
//        Bitmap bitmap=null;
//        try{
//            myFileURL = new URL(url);
//            HttpURLConnection conn=(HttpURLConnection)myFileURL.openConnection();
//            conn.setConnectTimeout(6000);
//            conn.setDoInput(true);
//            conn.setUseCaches(false);
//            InputStream is = conn.getInputStream();
//            bitmap = BitmapFactory.decodeStream(is);
//            is.close();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//         
//        return bitmap;
//    }
}
