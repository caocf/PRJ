package com.huzhouport.upload;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;


public class UploadFileTool
{
	ArrayList<UploadFileInfo> files;                  
	ArrayList<UploadFileInfo> faileUpFile;            
	ArrayList<UploadFileInfo> finishedFile;          
	
//	public static final int SEND_ONE_GROUP_FILE_SUCCESS=8000;
//	public static final int SEND_OND_FILE_WRONG=8001;
//	public static final int SEND_ALL_FILE=8002;
//	public static final int SEND_PROGRESS=8003;
//	public static final int SEND_AGAIN=8004;
//	public static final int SEND_NET_FAILE=8005;
//	public static final int SEND_ONE_FILE_SUCCESS=8006;
	
	
	int ResendTimes;     
	
	public static ResultHandler handler=new ResultHandler();
	
	
	public int getResendTimes()
	{
		return ResendTimes;
	}

	public void setResendTimes(int resendTimes)
	{
		ResendTimes = resendTimes;
	}

	Context context;
	public void setContext(Context context)
	{
		this.context = context;
	}
	public UploadFileTool()
	{
		files=new ArrayList<UploadFileInfo>();
		faileUpFile=new ArrayList<UploadFileInfo>();
		finishedFile=new ArrayList<UploadFileInfo>();
		ResendTimes=1;
		handler.setListener(new ResultListener()
		{
			
			@Override
			public void sendListener(int i)
			{
				if(context==null)
					return;
				if(i==ResultHandler.SEND_FALSE)
					Toast.makeText(context, "上传失败", Toast.LENGTH_LONG).show();
			}
			
			@Override
			public void reloadListener()
			{
				
			}
			
			@Override
			public void deleteListener()
			{
				
			}
			
			@Override
			public void defaultListener()
			{
				
			}
		});
		
		uploadThread.start();
	}
	
	public void addFile(String name,String actionUrl, Map<String, Object> params,Map<String, File> files, String uploadClassName)
	{
		if(this.files==null)
			this.files=new ArrayList<UploadFileInfo>();
		this.files.add(new UploadFileInfo(name,actionUrl,params,files,uploadClassName));
		
//		if(!uploadThread.isAlive())
//		{
//			uploadThread=new UploadThread(this);
//			uploadThread.start();
//		}
	}
	
	public void addFile(String name,String actionUrl,String param)
	{
		if(this.files==null)
			this.files=new ArrayList<UploadFileInfo>();
		this.files.add(new UploadFileInfo(name,actionUrl,param));
		
	}
	
	public ArrayList<UploadFileInfo> getFiles()
	{
		return files;
	}

	public void setFiles(ArrayList<UploadFileInfo> files)
	{
		this.files = files;
	}

	public ArrayList<UploadFileInfo> getFaileUpFile()
	{
		return faileUpFile;
	}

	public void setFaileUpFile(ArrayList<UploadFileInfo> faileUpFile)
	{
		this.faileUpFile = faileUpFile;
	}


	public ArrayList<UploadFileInfo> getFinishedFile()
	{
		return finishedFile;
	}

	public void setFinishedFile(ArrayList<UploadFileInfo> finishedFile)
	{
		this.finishedFile = finishedFile;
	}

	public boolean isCompeleted()
	{
		return uploadThread.isAlive();
	}
	
	public ArrayList<UploadFileInfo> getSelectFile(boolean getUploadFiles,boolean getSuccessFiles,boolean getFalseFiles)
	{
		ArrayList<UploadFileInfo> result=new ArrayList<UploadFileInfo>();
		
		if(getUploadFiles)
		{
			for(int i=0;i<files.size();i++)
				result.add(files.get(i));
		}
		if(getFalseFiles)
		{
			for(int i=0;i<faileUpFile.size();i++)
				result.add(faileUpFile.get(i));
		}
		
		if(getSuccessFiles)
		{
			for(int i=0;i<finishedFile.size();i++)
				result.add(finishedFile.get(i));
		}
		return result;
	}
	
	public ArrayList<UploadFileInfo> getAllFile()
	{
		ArrayList<UploadFileInfo> result=new ArrayList<UploadFileInfo>();
		for(int i=0;i<files.size();i++)
			result.add(files.get(i));
		
		for(int i=0;i<faileUpFile.size();i++)
			result.add(faileUpFile.get(i));
		
		for(int i=0;i<finishedFile.size();i++)
			result.add(finishedFile.get(i));
		
		return result;
	}

	public void reUpload(boolean sendAll)
	{		
		if(faileUpFile==null)
			return;
		if(faileUpFile.isEmpty())
			return;
		
		if(sendAll)
		{
			for(int j=0;j<faileUpFile.size();j++)
			{
				UploadFileInfo data=faileUpFile.get(j);
				this.addFile(data.getName(), data.getActionUrl(), data.getParams(), data.getFiles(), data.getUploadClassName());
			}
			faileUpFile.clear();
		}
		else
		{
			for(int j=0;j<faileUpFile.size();j++)
			{
				UploadFileInfo data=faileUpFile.get(j);
				if(data.isChecked())
				{
					data.setChecked(false);
					this.addFile(data.getName(), data.getActionUrl(), data.getParams(), data.getFiles(), data.getUploadClassName());			
					this.faileUpFile.remove(j);	
					j--;
				}
			}
		}
		
	}
	
	public void deleteFile(boolean deleteAll)
	{
		if(deleteAll)
			this.files=new ArrayList<UploadFileInfo>();
		else
		{
			for(int i=0;i<files.size();i++)
			{
				UploadFileInfo data=files.get(i);
				if(data.isChecked())
				{
					data.setChecked(false);
					files.remove(data);
					i--;
				}
			}
		}
	} 
	
	public void deleteFaile(boolean deleteAll)
	{
		if(deleteAll)
			this.faileUpFile=new ArrayList<UploadFileInfo>();
		else
		{
			for(int i=0;i<faileUpFile.size();i++)
			{
				UploadFileInfo data=faileUpFile.get(i);
				if(data.isChecked())
				{
					data.setChecked(false);
					faileUpFile.remove(data);
					i--;
				}
			}
		}
	}
	
	public void deleteComplete(boolean deleteAll)
	{
		if(deleteAll)
			this.finishedFile=new ArrayList<UploadFileInfo>();
		else
		{
			for(int i=0;i<finishedFile.size();i++)
			{
				UploadFileInfo data=finishedFile.get(i);
				if(data.isChecked())
				{
					data.setChecked(false);
					finishedFile.remove(data);
					i--;
				}
			}
		}
	}
	
	public Thread uploadThread=new Thread()
	{
		@Override
		public void run()
		{
			while(true)
			{
				for(int i=0;i<ResendTimes;i++)
				{
					while(!files.isEmpty())
					{
						UploadFileInfo data=files.get(0);
						boolean sendStatus;
						if(data.isGet())
						{
							sendStatus=post(data.getActionUrl(), data.getGetParams());
						}
						else
						{
							sendStatus=post(data);
						}
						if(sendStatus)
						{
							if(finishedFile==null)
								finishedFile=new ArrayList<UploadFileInfo>();
							data.setStatus(1);
							finishedFile.add(data);
						}
						else 
						{
							if(faileUpFile==null)
								faileUpFile=new ArrayList<UploadFileInfo>();
							data.setStatus(-1);
							faileUpFile.add(data);
							
							handler.sendEmptyMessage(ResultHandler.SEND_FALSE);
							//Toast.makeText(context, "上传失败", Toast.LENGTH_LONG).show();
						}
						files.remove(0);
						
					}
				}
				
				try
				{
					sleep(3000);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}	
		}
	};
	
	public boolean post(UploadFileInfo data){
		
		String actionUrl=data.getActionUrl();
		Map<String, Object> params=data.getParams();
		Map<String, File> files=data.getFiles();
		String uploadClassName=data.getUploadClassName();
			
		String sb2 = "";
		String BOUNDARY = java.util.UUID.randomUUID().toString();
		String PREFIX = "--", LINEND = "\r\n";
		String MULTIPART_FROM_DATA = "multipart/form-data";
		String CHARSET = "UTF-8";
		
		try
		{
			URL uri = new URL(actionUrl);
			HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
			conn.setReadTimeout(10 * 1000); 
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false); 
			conn.setRequestMethod("POST");
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Charsert", "UTF-8");
			conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
					+ ";boundary=" + BOUNDARY);
	
			StringBuilder sb = new StringBuilder();
			if(params!=null)
			{
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
			}
			DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
			outStream.write(sb.toString().getBytes());
			InputStream in = null;

			int count=0;
			if (files != null) {// scheduleAttachment.af
				for (Map.Entry<String, File> file : files.entrySet()) {
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
						count+=len;
						data.setHasSendLength(count);
					}
	
					is.close();
					outStream.write(LINEND.getBytes());
				}
	

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
			}
		}
		catch(IOException e)
		{
			return false;
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
		
	}

	
	public boolean post(String url, String param) {
		try {
			URL httpurl = new URL(url);
			HttpURLConnection httpConn = (HttpURLConnection) httpurl
					.openConnection();
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			PrintWriter out = new PrintWriter(httpConn.getOutputStream());
			out.print(param);
			out.flush();
			out.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					httpConn.getInputStream()));
			in.close();
		} catch (Exception e) {

			return false;

		}
		return true;
	}
	
	public static final String SAVE_PATH=Environment.getExternalStorageDirectory().getPath()+"/failsFile/";
	
	public void saveFile(UploadFileInfo data)
	{
		File file=new File(SAVE_PATH);
		int count=file.list().length;
		
		String filePathString;

		while(true)
		{
			count++;
			filePathString=SAVE_PATH+count;
			file=new File(filePathString);
			if(!file.exists())
			{
				file.mkdir();
				break;
			}
		}
		
		if(data.isGet())
		{
			
		}
		else
		{
			if(data.getFiles()!=null)
			{
				String temp=filePathString+"/File";
				File tempFile=new File(temp);
				if(!tempFile.exists())
				{
					tempFile.mkdir();
				}
				
				for (Map.Entry<String, File> entry : data.getFiles().entrySet()) 
				{
					temp+=entry.getKey();
					WriteFile(temp, entry.getValue());
				}
			}
		}
		
	}
	
	public void WriteFile(String path,File file)
	{
		try
		{
			InputStream is = new FileInputStream(file);
			OutputStream out=new FileOutputStream(path);
			
			byte[] buffer = new byte[4 * 1024];
			int len = 0;
			
			while ((len = is.read(buffer)) != -1) {
				out.write(buffer, 0, len);
		
			}
	
			is.close();
			out.close();
		}
		catch(Exception e)
		{
			
		}
	}
}

class UploadThread extends Thread
{
	public UploadThread(UploadFileTool u)
	{
		tool=u;
	}
	
	public UploadFileTool tool;
	
	@Override
	public void run()
	{
			for(int i=0;i<tool.ResendTimes;i++)
			{
				while(!tool.files.isEmpty())
				{
					UploadFileInfo data=tool.files.get(0);
					if(tool.post(data))
					{
						if(tool.finishedFile==null)
							tool.finishedFile=new ArrayList<UploadFileInfo>();
						data.setStatus(1);
						tool.finishedFile.add(data);
					}
					else 
					{
						if(tool.faileUpFile==null)
							tool.faileUpFile=new ArrayList<UploadFileInfo>();
						data.setStatus(-1);
						tool.faileUpFile.add(data);
					}
					tool.files.remove(0);
					
				}
				
			}
	}	
}
