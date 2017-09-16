package com.huzhouport.common.util;
/*
 * Êú×´TabWigdet
 * */
import com.example.huzhouport.R;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TabWidget;

public class VerticalTabWigdet extends TabWidget {
   Resources res;
	
	public VerticalTabWigdet(Context context, AttributeSet attrs) {
		super(context, attrs);
	
		res=context.getResources();
		setOrientation(LinearLayout.VERTICAL);
	}

	@Override
	public void addView(View child) {
		// TODO Auto-generated method stub
		LinearLayout.LayoutParams lp = new LayoutParams(
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f);
		lp.setMargins(0, 0, 0, 0);
		child.setLayoutParams(lp);
		super.addView(child);
		child.setBackgroundResource(R.drawable.tab_selector);
	}

}
