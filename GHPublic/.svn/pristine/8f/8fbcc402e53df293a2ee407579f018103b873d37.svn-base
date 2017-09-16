package com.hztuen.gh.activity.Adapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.gh.modol.RegistImage;
import com.hxkg.ghpublic.R;
import com.hztuen.lvyou.utils.ImageUtil;

public class RegistAddImagesAdapter extends BaseAdapter{
	public Context context;
	private List<RegistImage> registImage;
	
	public RegistAddImagesAdapter(Context context,List<RegistImage> registImage){
		this.context = context;
		this.registImage = registImage;
	}
	@Override
	public int getCount() {
		return registImage.size();
		// TODO Auto-generated method stub
//		return ;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return registImage.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView = LayoutInflater.from(context).inflate(R.layout.regist_gridvie_item, null);
		}
		ImageView imageadded = (ImageView) convertView.findViewById(R.id.imageView1);
		ImageView imageView2 = (ImageView) convertView.findViewById(R.id.imageView2);
		
		if(position == registImage.size()-1){
			//添加图片 button
			imageView2.setVisibility(View.GONE);
			imageadded.setVisibility(View.VISIBLE);
		}else{
			//
			String imageUrl = registImage.get(position+1).getImageFolder();
			if(null != imageUrl && !"".equals(imageUrl)){
				Bitmap mBitmap = ImageUtil.getSmallImageBitmap(context, imageUrl, 80, 80, false);
				if(mBitmap != null){
					imageView2.setImageBitmap(mBitmap);
					imageView2.setVisibility(View.VISIBLE);
					imageadded.setVisibility(View.GONE);
				}
			}else {
				imageView2.setVisibility(View.VISIBLE);
				imageadded.setVisibility(View.GONE);
			}
		}
//		iamgeadded.setBackgroundResource(R.drawable.appicon);
//		imageadded.setImageDrawable(null);
//		imageadded.setImageResource(R.drawable.appicon);
		return convertView;
	}
	public void pre(List<RegistImage> registImage){
		this.registImage =registImage;
		notifyDataSetChanged();
	}
	
	/**
	 * 加载本地图片
	 *
	 */
	public static Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
