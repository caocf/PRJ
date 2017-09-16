package com.huzhouport.upload;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.example.huzhouportpublic.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UploadActivity extends FragmentActivity {
	ImageView imageView1;
	ImageView imageView2;
	ImageView imageView3;
	ImageView imageView4;
	ImageView imageView5;

	TextView textView1;
	TextView textView2;

	Timer timer;

	private ViewPager mPager;
	private ArrayList<Fragment> fragmentsList;

	public static UploadFileTool tool=new UploadFileTool();

	private int selectedItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_upload);

		

		imageView1 = (ImageView) findViewById(R.id.imageView1);
		imageView2 = (ImageView) findViewById(R.id.imageView2);
		imageView4 = (ImageView) findViewById(R.id.imageView4);
		imageView5 = (ImageView) findViewById(R.id.imageView5);
		imageView3= (ImageView) findViewById(R.id.imageView3);
		textView1 = (TextView) findViewById(R.id.uploadFileNow);
		textView2 = (TextView) findViewById(R.id.uploadFileState);

		InitViewPager(0);
		textView1.setOnClickListener(new MyOnClickListener(0));
		textView2.setOnClickListener(new MyOnClickListener(1));
		selectedItem = 0;

		

		timer = new Timer();
		timer.schedule(task, 1000, 1000);

		imageView2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				tool.reUpload(false);
			}
		});

		imageView1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				switch (selectedItem) {
				case 0:
					tool.deleteFile(false);
					break;

				case 1:
					tool.deleteFaile(false);
					tool.deleteComplete(false);
					break;
				}
			}
		});
		imageView3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
	}
	

	final Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				ArrayList<UploadFileInfo> temp = new ArrayList<UploadFileInfo>();
				temp = (ArrayList<UploadFileInfo>) tool.getFiles().clone();
				FileUploadNowFragment.adapter.updata(temp);

				temp = null;
				temp = (ArrayList<UploadFileInfo>) tool.getSelectFile(false,
						true, true).clone();
				FileUploadFinishFragment.adapter.updata(temp);

				break;
			}
		}
	};

	public class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
			selectedItem = index;
		}
	};

	private void InitViewPager(int idx) {
		mPager = (ViewPager) findViewById(R.id.uploadFile);
		fragmentsList = new ArrayList<Fragment>();

		Fragment uploadNow = new FileUploadNowFragment();
		Fragment uploadFinish = new FileUploadFinishFragment();

		fragmentsList.add(uploadNow);
		fragmentsList.add(uploadFinish);

		mPager.setAdapter(new MyFragmentPagerAdapter(this
				.getSupportFragmentManager(), fragmentsList));
		mPager.setCurrentItem(idx);

		mPager.setOnPageChangeListener(new MyOnPageChangeListener());

		selectedItem = idx;
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageSelected(int arg0) {
			selectedItem = arg0;
			switch (arg0) {
			case 0:
				textView1.setTextColor(0xff0067ac);
				textView2.setTextColor(0xff666666);
				imageView4.setImageDrawable(getResources().getDrawable(
						R.drawable.fileupload_item_check));
				imageView5.setImageDrawable(null);
				break;
			case 1:
				textView2.setTextColor(0xff0067ac);
				textView1.setTextColor(0xff666666);
				imageView5.setImageDrawable(getResources().getDrawable(
						R.drawable.fileupload_item_check));
				imageView4.setImageDrawable(null);
				break;

			}

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}

	private TimerTask task = new TimerTask() {
		@Override
		public void run() {
			handler.sendEmptyMessage(1);
		}

	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	class myAdapter extends BaseAdapter {
		Context mContent = null;
		ArrayList<UploadFileInfo> data = null;

		public myAdapter(Context context, ArrayList<UploadFileInfo> arrayList) {
			this.mContent = context;
			this.data = arrayList;
			// isChecked=new ArrayList<Integer>();

		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;

			convertView = LayoutInflater.from(mContent).inflate(
					R.layout.upload_file_listview, null);
			holder = new ViewHolder();

			holder.filename = (TextView) convertView
					.findViewById(R.id.upload_filename);
			holder.filestatus = (TextView) convertView
					.findViewById(R.id.upload_filestatus);
			holder.checked = (ImageView) convertView
					.findViewById(R.id.upload_check);

			if (data.get(position).isChecked())
				holder.checked.setImageDrawable(getResources().getDrawable(
						R.drawable.fileupload_check));
			else {
				holder.checked.setImageDrawable(getResources().getDrawable(
						R.drawable.fileupload_uncheck));
			}

			holder.filename.setText(data.get(position).getName());

			switch (data.get(position).getStatus()) {
			case -1:
				holder.filestatus.setText("上传失败");
				holder.filestatus.setTextColor(0xffda251c);
				break;

			case 0:
				holder.filestatus
						.setText(data.get(position).getPercentString());
				break;

			case 1:
				holder.filestatus.setText("完成");
				break;
			}

			return convertView;
		}

		// ArrayList<Integer> isChecked;

		public void changeCheck(int position) {
			data.get(position).setChecked(!data.get(position).isChecked());
		}

		// public void addChecked(int id)
		// {
		// if(isChecked(id))
		// return;
		// else
		// isChecked.add(Integer.valueOf(id));
		//
		// }
		//
		// public void removeChecked(int id)
		// {
		// if(isChecked(id))
		// isChecked.remove(Integer.valueOf(id));
		// else
		// return;
		// }
		//
		// public boolean isChecked(int id)
		// {
		// Integer integer=Integer.valueOf(id);
		// return isChecked.contains(integer);
		// }

		public void updata(ArrayList<UploadFileInfo> d) {
			this.data.clear();
			this.data.addAll(d);
			notifyDataSetChanged();
		}

	}

	class ViewHolder {
		TextView filename;
		TextView filestatus;
		ImageView checked;
	}

}
