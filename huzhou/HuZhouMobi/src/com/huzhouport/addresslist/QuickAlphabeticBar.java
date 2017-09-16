package com.huzhouport.addresslist;

import java.util.HashMap;

import com.example.huzhouport.R;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
/**
 * ��ĸ��
 * @author hiphonezhu@sina.com
 *
 */
public class QuickAlphabeticBar extends ImageButton {
	private TextView mDialogText;
	private Handler mHandler;
	private ListView mList;
	private float mHight;
	private String[] letters = new String[] { "#", "A", "B", "C", "D", "E",
			"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
			"S", "T", "U", "V", "W", "X", "Y", "Z" };
	private HashMap<String, Integer> alphaIndexer;

	public QuickAlphabeticBar(Context context) {
		super(context);
	}

	public QuickAlphabeticBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public QuickAlphabeticBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void init(Activity ctx) {
		mDialogText = (TextView) ctx.findViewById(R.id.addresslist_fast_position);
		mDialogText.setVisibility(View.INVISIBLE);
		mHandler = new Handler();
	}

	public void setListView(ListView mList) {
		this.mList = mList;
	}

	public void setAlphaIndexer(HashMap<String, Integer> alphaIndexer) {
		this.alphaIndexer = alphaIndexer;
	}
	
	public void setHight(float mHight) {
		this.mHight = mHight;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int act = event.getAction();
		float y = event.getY();
		System.out.println("act==="+act);
		System.out.println("y==="+y);
		System.out.println("mHight==="+mHight);
		mHight=(float) 602.0;
		// ������ָλ�ã��ҵ���Ӧ�ĶΣ���mList�ƶ��ο�ͷ��λ����
		int selectIndex = (int) (y / (mHight / 27));
		System.out.println("selectIndex==="+selectIndex);
		if (selectIndex < 27) {// ��ֹԽ��
			String key = letters[selectIndex];
			System.out.println("key==="+key);
			System.out.println("alphaIndexer==="+alphaIndexer);
			if (alphaIndexer.containsKey(key)) {
				int pos = alphaIndexer.get(key);
				if (mList.getHeaderViewsCount() > 0) {// ��ֹListView�б�������������û�С�
					this.mList.setSelectionFromTop(
							pos + mList.getHeaderViewsCount(), 0);
				} else {
					this.mList.setSelectionFromTop(pos, 0);
				}
				mDialogText.setText(letters[selectIndex]);
			}
		}
		if (act == MotionEvent.ACTION_DOWN) {
			if (mHandler != null) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						if (mDialogText != null
								&& mDialogText.getVisibility() == View.INVISIBLE) {
							mDialogText.setVisibility(VISIBLE);
						}
					}
				});
			}
		} else if (act == MotionEvent.ACTION_UP) {
			if (mHandler != null) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						if (mDialogText != null
								&& mDialogText.getVisibility() == View.VISIBLE) {
							mDialogText.setVisibility(INVISIBLE);
						}
					}
				});
			}
		}
		return super.onTouchEvent(event);
	}
}
