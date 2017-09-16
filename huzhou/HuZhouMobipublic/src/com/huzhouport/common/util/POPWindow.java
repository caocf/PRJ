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
/*author �򵤵�
 * context xxx.this
 * idRelativeLayout �����򲼾�xml��
 * idLinearLayout   ���λ�ò���id
 * idButtons ��ť
 * itemsOnClick ��ť�ĵ��
 * idText  TextViewid
 * TextContent TextView����value
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
		 //����POPWindow��View
		this.setContentView(mMenuView);
		//����POPWindow��������Ŀ�
		this.setWidth(width-30);
		//����POPWindow��������ĸ�
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		//����POPWindow��������ɵ��
		this.setOutsideTouchable(true);
		//����POPWindow�������嶯��Ч��
		this.setAnimationStyle(R.style.PopupAnimation);
		//ʵ����һ��ColorDrawable��ɫΪ��͸�� 
		//ColorDrawable dw = new ColorDrawable(0x90000000);
		//����POPWindow��������ı���
		this.setBackgroundDrawable(context.getResources().getDrawable(R.color.white));
		
		//���ಿ����Ӱ
		WindowManager.LayoutParams lp = context.getWindow().getAttributes();
		lp.alpha = 0.5f;
		context.getWindow().setAttributes(lp);
		
		this.showAtLocation(context.findViewById(idLinearLayout), Gravity.CENTER,0,0);
		 //mMenuView���OnTouchListener�����жϻ�ȡ����λ�������ѡ������������ٵ�����
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
