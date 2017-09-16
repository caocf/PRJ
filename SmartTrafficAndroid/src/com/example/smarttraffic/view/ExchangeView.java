package com.example.smarttraffic.view;

import com.example.smarttraffic.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

/**
 * 自定义图标切换控件
 * 
 * @author Administrator zhou
 * 
 */
public class ExchangeView extends LinearLayout
{
	BaseAdapter adapter; // 内容适配器
	OnItemClickListener listener; // 内容监听器

	LinearLayout exchangeLayout; // 交换提示布局
	ViewFlipper viewFlipper; // 交换内容
	int current; // 当前选择
	ImageView[] imageView; // 提示图片

	GestureDetector gestureDetector; // 手势

	public ExchangeView(Context context)
	{
		this(context, null);
	}

	public ExchangeView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		View root = LayoutInflater.from(context).inflate(
				R.layout.view_exchange, this, true);
		exchangeLayout = (LinearLayout) root
				.findViewById(R.id.view_exchange_image);
		viewFlipper = (ViewFlipper) root
				.findViewById(R.id.view_exchange_content);
		current = -1;

		gestureDetector = new GestureDetector(getContext(), new moveListener());
	}

	public void setAdapter(BaseAdapter adapter)
	{
		this.adapter = adapter;

		if (adapter != null && adapter.getCount() > 0)
		{
			for (int i = 0; i < adapter.getCount(); i++)
			{
				viewFlipper.addView(adapter.getView(i, null, null), i,
						new LayoutParams(LayoutParams.MATCH_PARENT,
								LayoutParams.MATCH_PARENT));
			}
		}
	}

	public void setCurrent(int current)
	{
		this.current = current;
	}

	public void setListener(OnItemClickListener listener)
	{
		this.listener = listener;
	}

	public void update(int id)
	{
		if (adapter == null || adapter.getCount() == 0)
			return;

		if (imageView == null)
			createImage();
		select(id);
	}

	private int select_image_id = R.drawable.icon_point_activity;
	private int unselect_image_id = R.drawable.icon_point_normal;

	public void createImage()
	{
		if (adapter == null || adapter.getCount() == 0)
			return;

		int count = adapter.getCount();

		imageView = new ImageView[count];

		for (int i = 0; i < count; i++)
		{
			imageView[i] = new ImageView(this.getContext());
			imageView[i].setBackgroundResource(unselect_image_id);

			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT, 0);
			lp.setMargins(10, 10, 10, 10);
			imageView[i].setLayoutParams(lp);

			exchangeLayout.addView(imageView[i]);
		}
	}

	public void select(int id)
	{
		if (current == -1)
		{
			imageView[0].setBackgroundResource(select_image_id);
			current = 0;
		} else
		{
			imageView[current].setBackgroundResource(unselect_image_id);
			imageView[id].setBackgroundResource(select_image_id);
			current = id;

			viewFlipper.setDisplayedChild(id);
		}
	}

	// public void showView(int id)
	// {
	// frameLayout.removeAllViews();
	// viewFlipper.addView(adapter.getView(id, null, null));
	// }

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		try
		{
			return gestureDetector.onTouchEvent(event);

		} catch (Exception e)
		{
		}

		return true;
	}

	private final float DEFAULT_DISTANCE = 200;

	class moveListener extends GestureDetector.SimpleOnGestureListener
	{

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY)
		{
			int t = 0;
			float d = e1.getX() - e2.getX();

			System.out.println("cccc:" + current);

			if (d > DEFAULT_DISTANCE)
			{
				if ((current + 1) < adapter.getCount())
					t = current + 1;
				else
					t = 0;

				Animation rInAnim = AnimationUtils.loadAnimation(getContext(),
						R.anim.map_left_in); // 向右滑动左侧进入的渐变效果（alpha 0.1 -> 1.0）
				Animation rOutAnim = AnimationUtils.loadAnimation(getContext(),
						R.anim.map_left_out); // 向右滑动右侧滑出的渐变效果（alpha 1.0 -> 0.1）

				viewFlipper.setInAnimation(rInAnim);
				viewFlipper.setOutAnimation(rOutAnim);
				viewFlipper.showPrevious();

			} else if (d < -DEFAULT_DISTANCE)
			{
				if (current > 0)
					t = current - 1;
				else
					t = adapter.getCount() - 1;

				Animation lInAnim = AnimationUtils.loadAnimation(getContext(),
						R.anim.map_right_in); // 向左滑动左侧进入的渐变效果（alpha 0.1 -> 1.0）
				Animation lOutAnim = AnimationUtils.loadAnimation(getContext(),
						R.anim.map_right_out); // 向左滑动右侧滑出的渐变效果（alpha 1.0 ->
												// 0.1）

				viewFlipper.setInAnimation(lInAnim);
				viewFlipper.setOutAnimation(lOutAnim);
				viewFlipper.showNext();
			} else
			{
				return true;
			}
			if (listener != null)
				listener.onItemClick(null, viewFlipper, t, t);

			update(t);

			return true;
		}
	}
}
