package net.hxkg.network;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import android.os.Handler;

public class HttpRequest 
{
	onResult onresult;
	Handler handler=new Handler();
	
	public HttpRequest(onResult onresult)
	{
		this.onresult=onresult;
		
	}
	
	public interface onResult
	{
		public void onSuccess(String result);
		public void onError(int httpcode);
	}
	
	
	
	public void post(final String actionUrl, final Map<String, Object> params)
	{
		new Thread(new Runnable() 
		{			
			@Override
			public void run() 
			{
				try 
				{
					String sb2 = "";
					String BOUNDARY = java.util.UUID.randomUUID().toString();
					String PREFIX = "--", LINEND = "\r\n";
					String MULTIPART_FROM_DATA = "multipart/form-data";
					String CHARSET = "UTF-8";

					URL uri = new URL(actionUrl);
					HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
					
					conn.setConnectTimeout(15 * 1000);
					if(Constants.COOKIE!=null)
					{
						conn.setRequestProperty("cookie", Constants.COOKIE);
					}					
					conn.setReadTimeout(15 * 1000);
					conn.setDoInput(true);
					conn.setDoOutput(true);
					conn.setUseCaches(false); 
					conn.setRequestMethod("POST");
					conn.setRequestProperty("connection", "keep-alive");
					conn.setRequestProperty("Charsert", "UTF-8");
					conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA+ ";boundary=" + BOUNDARY);

					StringBuilder sb = new StringBuilder();
					for (Map.Entry<String, Object> entry : params.entrySet()) 
					{
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
					//String ss=sb.toString();
					DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
					outStream.write(sb.toString().getBytes());
					InputStream in = null;
					byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
					outStream.write(end_data);
					outStream.flush();

					int res = conn.getResponseCode();
					if (res == 200) 
					{
						String cookieval = conn.getHeaderField("set-cookie");
						
						if(cookieval != null) 
						{ 
							Constants.COOKIE = cookieval.substring(0, cookieval.indexOf(";")); 
						}
			            
						
						in = conn.getInputStream();
						BufferedReader br = new BufferedReader(new InputStreamReader(in));
						String line = "";
						for (line = br.readLine(); line != null; line = br.readLine()) 
						{
							sb2 = sb2 + line;
						}
						final String resultString=sb2;
						handler.post(new Runnable() 
						{
							
							@Override
							public void run() 
							{
								onresult.onSuccess(resultString);
								
							}
						});
					}
					/*else if(res==301||res==302)
					{
						URL s=conn.getURL();
						String ssString=s.toString();
						post(ssString,params);
						System.out.print(123);
					}*/
					else 
					{
						onresult.onError(res);
					}
					outStream.close();
					conn.disconnect();	
				} 
				catch (Exception e)
				{
					onresult.onError(-1);
				}							
			}
		}).start();				
	}
	public void post(final String actionUrl, final Map<String, Object> params, final Map<String, File> files, final String uploadClassName) throws IOException 
	{
		new Thread(new Runnable() 
		{			
			@Override
			public void run() 
			{
				try 
				{
					String sb2 = "";
					String BOUNDARY = java.util.UUID.randomUUID().toString();
					String PREFIX = "--", LINEND = "\r\n";
					String MULTIPART_FROM_DATA = "multipart/form-data";
					String CHARSET = "UTF-8";

					URL uri = new URL(actionUrl);
					HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
					conn.setConnectTimeout(5 * 1000);
					conn.setReadTimeout(10 * 1000); 
					conn.setDoInput(true);
					conn.setDoOutput(true);
					conn.setUseCaches(false); 
					conn.setRequestMethod("POST");
					conn.setRequestProperty("connection", "keep-alive");
					conn.setRequestProperty("Charsert", "UTF-8");
					conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
							+ ";boundary=" + BOUNDARY);
					if(Constants.COOKIE!=null)
					{
						conn.setRequestProperty("cookie", Constants.COOKIE);
					}

					DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
					InputStream in = null;
					if(params!=null)
					{
						StringBuilder sb = new StringBuilder();
						for (Map.Entry<String, Object> entry : params.entrySet()) 
						{
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
						outStream.write(sb.toString().getBytes());
					}					
					
					if (files != null) 
					{
						for (Map.Entry<String, File> file : files.entrySet()) 
						{
							if(file.getValue()==null)
							{
								continue;
							}
							StringBuilder sb1 = new StringBuilder();
							sb1.append(PREFIX);
							sb1.append(BOUNDARY);
							sb1.append(LINEND);
							sb1.append("Content-Disposition: form-data; name=\""
									+ uploadClassName + "\"; filename=\"" + file.getKey()
									+ "\"" + LINEND);
							sb1.append("Content-Type: application/octet-stream; charset="
									+ CHARSET + LINEND);
							sb1.append(LINEND);
							outStream.write(sb1.toString().getBytes());

							InputStream is = new FileInputStream(file.getValue());
							byte[] buffer = new byte[4 * 1024];
							int len = 0;
							while ((len = is.read(buffer)) != -1) {
								outStream.write(buffer, 0, len);
							}

							is.close();
							outStream.write(LINEND.getBytes());
						}

						byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
						outStream.write(end_data);
						outStream.flush();

						int res = conn.getResponseCode();//System.out.println(res);
						if (res == 200) 
						{
							in = conn.getInputStream();
							BufferedReader br = new BufferedReader(new InputStreamReader(in));
							String line = "";
							for (line = br.readLine(); line != null; line = br.readLine()) {
								sb2 = sb2 + line;
							}
							
							final String resultString=sb2;
							handler.post(new Runnable() 
							{					
								@Override
								public void run() 
								{
									onresult.onSuccess(resultString);						
								}
							});
						}
						else 
						{
							onresult.onError(res);
						}
						outStream.close();
						conn.disconnect();
					}
				}
				catch (Exception e) 
				{
					onresult.onError(-1);
				}
				
				
			}
		}).start();		
	}
	
}
