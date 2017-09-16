package com.huzhouport.common.util;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/***
 * �Զ���view
 * 
 * 
 * 
 */
public class MyViewGroup extends ViewGroup {
	private Scroller scroller;// ����
	private int distance;// ��������
	private View viewMenu, viewContent;
	private int duration = 5;
	private int width = 0;
	//private int height = 0;
	private int lastMotionX=0;//��¼��һ�ΰ���ȥ��xλ��
	public static boolean isMenuOpen = false;// �˵��Ƿ��
	
	private static int STATE_NULL=1,STATE_SCROLLING=2;//������ȥ������״̬
	private int touchState=1;//��ǰ�Ĵ���״̬

	public MyViewGroup(Context context) {
		super(context, null);
	}


	public MyViewGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
		// this.context = context;
		scroller = new Scroller(context);
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
	 protected void dispatchDraw(Canvas canvas) {
	      Log.i("dispatchDraw", "dispatchDraw");
		//����������ʵ�ֺܶ���Ч������Сľ׮�Ͳ�������ˣ��Ժ�����
	      super.dispatchDraw(canvas);
	    }
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed) {
			viewMenu = getChildAt(0);// ��ȡ�����˵���view
			viewContent = getChildAt(1);// �����ҳview
			measureView(viewContent);
			measureView(viewMenu);
			width = getWidth();
			//height = getHeight();
			// �ĸ������ֱ�Ϊ����ߵ�λ�ã��ϱߵ�λ�ã��ұߵ�λ�ã��ױߵ�λ��
			Log.e("onLayout", "w=" + getWidth());
			Log.e("onLayout", "h=" + getHeight());
			viewMenu.layout(-viewMenu.getMeasuredWidth(), 0, 0, getHeight());
			viewContent.layout(0, 0, getWidth(), getHeight());
		}
	}
	/**
	 * ��һ��view�ĸ߶Ȼ�ȡ������ʱ���ȵ��������������һ�£���ô�Ϳ��Ի�ȡ��view�ĸ߶���
	 * 
	 * @param view
	 * @author Сľ׮
	 */
	private void measureView(View view) {
		ViewGroup.LayoutParams p = view.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		view.measure(childWidthSpec, childHeightSpec);
	}

	/**
	 * 
	 * ���������д֮��һ��Ͷ�����ôд�ģ��Ƚϴ�ͳ��д��������Ҫ��Ӹ���Ĵ��룬һ�㶼���ڶ������еĹ����в�ͣ�Ļ����������� �Ӷ�ʵ�ֵ��Ե�Ч��
	 * 
	 * 
	 * getScrollX()��scroller.getCurrX()��ֵ��һ���ģ���������ʼ������µľ���ƫ���� >0
	 * ��ʾ����ԭʼ�Ļ���������ƫ���� <0 ��ʾ����ԭʼ�Ļ���������ƫ����
	 * @author Сľ׮
	 */
	@Override
	public void computeScroll() {
		if (scroller.computeScrollOffset()) {
			int curX = scroller.getCurrX();
			this.scrollTo(curX, scroller.getCurrY());
			invalidate();// ˢ��
			Log.e("", "curX=" + curX);
			if (curX == -width && isMenuOpen) {
				scroller.abortAnimation();
				closeMenuPull02();
			}
		}
	}

	/**
	 * startScroll������������� int startX ˮƽ����ʼ������ƫ��ֵ���������ԭʼ��λ�ã���>0��ʾ����ƫ���ˣ�<0��ʾ����ƫ����
	 * int startY, ͬ�ϣ���ֱ���� >0��ʾ����ƫ���� int dx, ˮƽ�����ƶ��ľ��룬>0����ƫ�ƣ�<0����ƫ�� int dy,
	 * ��ֱ�����ƶ��ľ��룬>0����ƫ�ƣ�<0����ƫ�� int duration �ƶ�����������ʱ��
	 * @author Сľ׮
	 */
	public void showMenu() {
		if(touchState==STATE_SCROLLING){
			return;
		}
		isMenuOpen = true;
		scroller.startScroll(getScrollX(), 0, -distance, 0, duration);
		invalidate();// ˢ��
	}

	/**
	 * �����Ĺرն���Ч��
	 * @author Сľ׮
	 */
	public void closeMenu() {
		if(touchState==STATE_SCROLLING){
			return;
		}
		isMenuOpen = false;
		scroller.startScroll(getScrollX(), 0, distance, 0, duration);
		invalidate();// ˢ��
	}

	/**
	 * closeMenuPull01��closeMenuPull02��һ�������ִ�йرղ˵��ķ�ʽ�����з�����Ч��
	 * ��ִ�йر�01�Ķ���������ִ��02�Ĺرն���
	 */
	public void closeMenuPull01() {
		if(touchState==STATE_SCROLLING){
			return;
		}
		scroller.startScroll(getScrollX(), 0, distance , 0,
				duration);
		invalidate();// ˢ��
	}

	// �رղ˵���ִ���Զ��嶯����
	private void closeMenuPull02() {
		if(touchState==STATE_SCROLLING){
			return;
		}
		isMenuOpen = false;
		scroller.startScroll(getScrollX(), 0, getWidth(), 0, duration);
		invalidate();// ˢ��
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent e) {
		final int action = e.getAction();
		if ((action == MotionEvent.ACTION_MOVE)
				&& (touchState == STATE_SCROLLING)) {
			return true;
		} 
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			lastMotionX = (int) e.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			int tempX=(int) e.getX();
			int moveX=lastMotionX-tempX;
			if ((moveX < -6) ||( moveX > 6)) {
				// ����true��ʾִ�������ontouch������������false��ִ����view��onTouch�������д���
				return true;// ���������touch�ķ���ȥ����
			}
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			touchState = STATE_NULL;
			break;
		}
		return super.onInterceptTouchEvent(e);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastMotionX = (int) event.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			int tempX=(int) event.getX();
			int moveX=lastMotionX-tempX;
			scrollBy(moveX, 0);//�����ڵ�λ�ã���ʼ�ƶ����ƶ��ľ���ֱ�ΪX��Y���ĵľ����С
			lastMotionX=tempX;
			touchState=STATE_SCROLLING;
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			// û�г�������\
			int scrollX=getScrollX();
			if(scrollX>0){
				scrollTo(0, 0);
			}else if (scrollX < -width / 3) {
				scrollTo(-width / 3, 0);
				//scroller.startScroll(getScrollX(), 0, -getScrollX(), 0, duration);
				isMenuOpen = true;
			}
			// ��������
			else if (scrollX >= -width / 3) {
//				scroller.startScroll(getScrollX(), 0, -(distance + getScrollX()),
//						0, duration);
				scrollTo(0, 0);
				isMenuOpen = false;
			}
	
			invalidate();// ˢ��		
			touchState = STATE_NULL;
			break;
		}
		return super.onTouchEvent(event);
	}
}
