package com.huzhouport.common;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.example.huzhouportpublic.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * SD卡文件管理器
 * @author Administrator
 *
 */
public class GetSDTreeActivity extends Activity
{
	public static final String RESULT_PATH="result_path";
	public static final String RESULT_NAME="result_name";
	public static final int RESULT_CODE=1000;
	
	File sdRoot;

	File[] datas;

	File nowFile;

	ListView listView;
	int lastPoint = 0;
	boolean isBack = false;
	MyAdapater adapater;

	String[] fileTypes = new String[] { "apk", "avi", "bat", "bin", "bmp",
			"chm", "css", "dat", "dll", "doc", "docx", "dos", "dvd", "gif",
			"html", "ifo", "inf", "iso", "java", "jpeg", "jpg", "log", "m4a",
			"mid", "mov", "movie", "mp2", "mp2v", "mp3", "mp4", "mpe", "mpeg",
			"mpg", "pdf", "php", "png", "ppt", "pptx", "psd", "rar", "tif",
			"ttf", "txt", "wav", "wma", "wmv", "xls", "xlsx", "xml", "xsl",
			"zip" };

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.get_sd_main);
		listView = (ListView) findViewById(R.id.list);

		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3)
			{
				File clickFile = datas[arg2];
				if (clickFile.isDirectory())
				{
					lastPoint = arg2;
					loadFiles(clickFile);
				} else
				{
					selectFile(clickFile);
				}
			}
		});

		String root = Environment.getExternalStorageDirectory().getPath();
		sdRoot = new File(root);
		if (sdRoot.exists())
		{
			loadFiles(sdRoot);
		}

	}

	void loadFiles(File directory)
	{
		nowFile = directory;
		setTitle(nowFile.getPath());
		// 分类并排序
		File[] temp = directory.listFiles();
		ArrayList<File> tempFolder = new ArrayList<File>();
		ArrayList<File> tempFile = new ArrayList<File>();
		if(temp==null) {
			Toast.makeText(this, "该目录下是系统文件，不能打开", Toast.LENGTH_SHORT).show();
			return ;
		}
		for (int i = 0; i < temp.length; i++)
		{
			File file = temp[i];
			if (file.isDirectory())
			{
				tempFolder.add(file);
			} else if (file.isFile())
			{
				tempFile.add(file);
			}
		}
		// 对List进行排序
		Comparator<File> comparator = new MyComparator();
		Collections.sort(tempFolder, comparator);
		Collections.sort(tempFile, comparator);

		datas = new File[tempFolder.size() + tempFile.size()];
		System.arraycopy(tempFolder.toArray(), 0, datas, 0, tempFolder.size());
		System.arraycopy(tempFile.toArray(), 0, datas, tempFolder.size(),
				tempFile.size());

		// 数据处理结束
		adapater = new MyAdapater(GetSDTreeActivity.this);
		listView.setAdapter(adapater);
		if (isBack)
		{
			listView.smoothScrollToPosition(lastPoint);
			lastPoint = 0;
			isBack = false;
		}
	}

	// 自定义比较器
	class MyComparator implements Comparator<File>
	{
		@Override
		public int compare(File lhs, File rhs)
		{
			return lhs.getName().compareTo(rhs.getName());
		}

	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		switch (keyCode)
		{
		case KeyEvent.KEYCODE_BACK:
			String parent = nowFile.getParent();
			if (parent.equals("/"))
			{
				this.finish();
				return true;
			}
			isBack = true;
			loadFiles(new File(parent));
			return true;
		default:
			break;
		}
		return super.onKeyUp(keyCode, event);
	}

	private void selectFile(File f)
	{
		String path=f.getAbsolutePath();
		String fileName=f.getName();
		//String s=getExtensionName(fileName);
		
		if((getExtensionName(fileName).equals("png")||getExtensionName(fileName).equals("jpg")
				||getExtensionName(fileName).equals("bmp")))
		{
		
		Intent intent = new Intent();
        intent.putExtra(RESULT_PATH, path);
        intent.putExtra(RESULT_NAME, fileName);
        this.setResult(RESULT_CODE, intent);
        this.finish();
		}
		else
		{
			Toast.makeText(this, "不是有效文件！", Toast.LENGTH_SHORT).show();
		}
	}
	
	 public static String getExtensionName(String filename) 
	 {    
	        if ((filename != null) && (filename.length() > 0)) {    
	            int dot = filename.lastIndexOf('.');    
	            if ((dot >-1) && (dot < (filename.length() - 1))) {    
	                return filename.substring(dot + 1);    
	            }    
	        }    
	     return filename;    
	 } 

	class ViewHolder
	{
		ImageView typeView;
		TextView nameView;
	}

	private class MyAdapater extends BaseAdapter
	{
		LayoutInflater mInflater;

		public MyAdapater(Context context)
		{
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder;
			if (convertView == null)
			{
				convertView = mInflater.inflate(R.layout.get_sd_item, null);
				holder = new ViewHolder();
				holder.typeView = (ImageView) convertView
						.findViewById(R.id.image_type);
				holder.nameView = (TextView) convertView
						.findViewById(R.id.text_name);
				convertView.setTag(holder);
			} else
			{
				holder = (ViewHolder) convertView.getTag();
			}

			File file = datas[position];

			if (file.isDirectory())
			{
				holder.typeView.setImageResource(R.drawable.get_sd_folder);
			} else
			{
				holder.typeView.setImageResource(R.drawable.get_sd_file);
				String name = file.getName();
				int pointIndex = name.lastIndexOf(".");
				if (pointIndex != -1)
				{
					String type = name.substring(pointIndex + 1).toLowerCase();
					for (int i = 0; i < fileTypes.length; i++)
					{
						if (type.equals(fileTypes[i]))
						{
							try
							{
								int resId = getResources().getIdentifier("get_sd_"+type,
										"drawable", getPackageName());
								holder.typeView.setImageResource(resId);
							} catch (Exception e)
							{
								e.printStackTrace();
							}

						}
					}
				}

			}
			holder.nameView.setText(file.getName());
			return convertView;
		}

		@Override
		public int getCount()
		{
			return datas.length;
		}

		@Override
		public Object getItem(int position)
		{
			return datas[position];
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}
	}
}