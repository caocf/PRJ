package com.hztuen.gh.activity.Adapter;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.gh.modol.CardImagesModel;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.ImagePagerActivity;
import com.hztuen.lvyou.utils.BitmapCache;

public class HomeAdapter extends BaseAdapter implements OnClickListener
{
	public static final String TAG = HomeAdapter.class.getSimpleName();

	private RequestQueue queue;
	private Context context;
	private ImageLoader imageLoader;
	private List<CardImagesModel> imageslist;
	private ArrayList<String> arrayUrl=new ArrayList<String>();
	// public final static String SER_KEY = "com.intent.activity.ser";
	//
	// /**
	// * 记录每个子项的高度。
	// */
	private int mItemHeight = 0;

	//
	//
	public HomeAdapter(List<CardImagesModel> imageslist) {
		this.imageslist = imageslist;
		notifyDataSetChanged();
	}

	public HomeAdapter(Context context, List<CardImagesModel> imageslist) {
		super();
		this.context = context;
		this.imageslist = imageslist;
		queue = Volley.newRequestQueue(context);
		imageLoader = new ImageLoader(queue, new BitmapCache());
		
		for(int i=0;i<imageslist.size();i++)
		{
			arrayUrl.add(imageslist.get(i).getimgUrl());
		}
	}

	@Override
	public int getCount() {
		System.out.println("ListView Adapter size : " + imageslist.size());
		return imageslist.size();
	}

	@Override
	public CardImagesModel getItem(int position) {
		return imageslist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(final int position, View view, ViewGroup parent) {
		ViewHolder holder = null;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.gird_item,
					null);
		}

		holder = new ViewHolder();
		holder.img = (NetworkImageView) view.findViewById(R.id.image);
		view.setTag(holder);
		
		//
	//	if (holder.img.getLayoutParams().height != mItemHeight) {
			// 通过activity中计算出的结果，在这里设置networkImageview的高度（得到的是正方形）
	//		holder.img.getLayoutParams().height = mItemHeight;
	//	}

		if (imageslist.get(position).getimgUrl() != null
				&& !imageslist.get(position).getimgUrl().equals("")) {
		//	holder.img.setDefaultImageResId(R.drawable.ic_alarm);
		//	holder.img.setErrorImageResId(R.drawable.ic_alarm);
			holder.img.setImageUrl(imageslist.get(position).getimgUrl(),
					imageLoader);
		} else {
			holder.img.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_alarm));
		}
		
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				imageBrower(position,arrayUrl);
				
			}
		});

		return view;
	}

	/**
	 * 设置item子项的高度。
	 */
	public void setItemHeight(int height) {
		if (height == mItemHeight) {
			return;
		}
		mItemHeight = height;
		notifyDataSetChanged();
	}

	class ViewHolder {
		NetworkImageView img;
	}

	public void pre(List<CardImagesModel> imageslist) {
		this.imageslist = imageslist;
		notifyDataSetChanged();
	}
	
	
	
	/**
	 * 打开图片查看器
	 * 
	 * @param position
	 * @param urls2
	 */
	protected void imageBrower(int position, ArrayList<String> urls2) {
		Intent intent = new Intent(context, ImagePagerActivity.class);
		// 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


}
