package com.example.smarttraffic.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

/**
 * post请求类
 * @author Administrator
 *
 */
public class PostClient
{
	/**
	 * 获取post请求结果值
	 * @param actionUrl 请求地址
	 * @param params 请求参数
	 * @return 请求结果字符串
	 */
	public static String post(String actionUrl, Map<String, Object> params){
		String sb2 = "";
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";
		try
		{
			URL uri = new URL(actionUrl);
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setConnectTimeout(5 * 1000); 
			conn.setReadTimeout(5 * 1000); 
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false); 
			conn.setRequestMethod("POST");
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
					+ ";boundary=" + BOUNDARY);
	
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINEND);
				sb.append("Content-Disposition: form-data; name=\""
						+ entry.getKey() + "\"" + LINEND);
				sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
				sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
				sb.append(LINEND);
				sb.append(entry.getValue());
				sb.append(LINEND);
			}
	
			DataOutputStream outStream = new DataOutputStream(
					conn.getOutputStream());
			outStream.write(sb.toString().getBytes());
			InputStream in = null;
	
			byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
			outStream.write(end_data);
			outStream.flush();
	
			int res = conn.getResponseCode();
			if (res == 200) {
				in = conn.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String line = "";
				for (line = br.readLine(); line != null; line = br.readLine()) {
					sb2 = sb2 + line;
				}
			}
			outStream.close();
			conn.disconnect();
			return sb2;
		}
		catch (ClientProtocolException e) {
			e.printStackTrace();
			return "网络异常！";
		} catch (IOException e) {
			e.printStackTrace();
			return "网络异常！";
		}
		catch(Exception e)
		{
			return "";
		}
	}
}
