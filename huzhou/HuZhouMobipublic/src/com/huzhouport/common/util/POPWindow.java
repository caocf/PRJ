package com.huzhouport.common.util;

import com.example.huzhouportpublic.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.text.Html;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

public class POPWindow extends PopupWindow {
	private View mMenuView;
/*author 沈丹丹
 * context xxx.this
 * idRelativeLayout 弹出框布局xml名
 * idLinearLayout   相关位置布局id
 * idButtons 按钮
 * itemsOnClick 按钮的点击
 * idText  TextViewid
 * TextContent TextView——value
 * */
	@SuppressLint("NewApi")
	public POPWindow(final Activity context, final int idRelativeLayout,
			final int idLinearLayout, int idButtons,
			OnClickListener itemsOnClick,int[] idText, String[] TextContent) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(idRelativeLayout, null);
		
		Button buttons= (Button) mMenuView.findViewById(idButtons);
		buttons.setOnClickListener(itemsOnClick);

		for (int i=0;i<idText.length;i++) {
			( (TextView) mMenuView
				.findViewById(idText[i])).setText(Html.fromHtml(TextContent[i]));
		}
		Display display = context.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		 //设置POPWindow的View
		this.setContentView(mMenuView);
		//设置POPWindow弹出窗体的宽
		this.setWidth(width-30);
		//设置POPWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		//设置POPWindow弹出窗体可点击
		this.setOutsideTouchable(true);
		//设置POPWindow弹出窗体动画效果
		this.setAnimationStyle(R.style.PopupAnimation);
		//实例化一个ColorDrawable颜色为半透明 
		//ColorDrawable dw = new ColorDrawable(0x90000000);
		//设置POPWindow弹出窗体的背景
		this.setBackgroundDrawable(context.getResources().getDrawable(R.color.white));
		
		//其余部分阴影
		WindowManager.LayoutParams lp = context.getWindow().getAttributes();
		lp.alpha = 0.5f;
		context.getWindow().setAttributes(lp);
		
		this.showAtLocation(context.findViewById(idLinearLayout), Gravity.CENTER,0,0);
		 //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				int height = v.getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});

	}

}
