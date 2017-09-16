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
 * 自定义view
 * 
 * 
 * 
 */
public class MyViewGroup extends ViewGroup {
	private Scroller scroller;// 滑动
	private int distance;// 滑动距离
	private View viewMenu, viewContent;
	private int duration = 5;
	private int width = 0;
	//private int height = 0;
	private int lastMotionX=0;//记录上一次按下去的x位置
	public static boolean isMenuOpen = false;// 菜单是否打开
	
	private static int STATE_NULL=1,STATE_SCROLLING=2;//触摸下去的两种状态
	private int touchState=1;//当前的触摸状态

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
		//这个里面可以实现很多特效，今天小木桩就不多介绍了，以后再续
	      super.dispatchDraw(canvas);
	    }
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed) {
			viewMenu = getChildAt(0);// 获取滑动菜单的view
			viewContent = getChildAt(1);// 获得主页view
			measureView(viewContent);
			measureView(viewMenu);
			width = getWidth();
			//height = getHeight();
			// 四个参数分别为：左边的位置，上边的位置，右边的位置，底边的位置
			Log.e("onLayout", "w=" + getWidth());
			Log.e("onLayout", "h=" + getHeight());
			viewMenu.layout(-viewMenu.getMeasuredWidth(), 0, 0, getHeight());
			viewContent.layout(0, 0, getWidth(), getHeight());
		}
	}
	/**
	 * 当一个view的高度获取不到的时候，先调用这个方法测量一下，那么就可以获取到view的高度了
	 * 
	 * @param view
	 * @author 小木桩
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
	 * 这个方法改写之后一般就都是这么写的，比较传统的写法，不需要外加更多的代码，一般都是在动画运行的过程中不停的会调用这个方法 从而实现弹性的效果
	 * 
	 * 
	 * getScrollX()和scroller.getCurrX()的值是一样的，是相对最初始的情况下的绝对偏移量 >0
	 * 表示在最原始的基础上向左偏移了 <0 表示在最原始的基础上向右偏移了
	 * @author 小木桩
	 */
	@Override
	public void computeScroll() {
		if (scroller.computeScrollOffset()) {
			int curX = scroller.getCurrX();
			this.scrollTo(curX, scroller.getCurrY());
			invalidate();// 刷新
			Log.e("", "curX=" + curX);
			if (curX == -width && isMenuOpen) {
				scroller.abortAnimation();
				closeMenuPull02();
			}
		}
	}

	/**
	 * startScroll方法有五个参数 int startX 水平方向开始滚动的偏移值（相对于最原始的位置），>0表示向左偏移了，<0表示向右偏移了
	 * int startY, 同上，垂直方向。 >0表示向上偏移了 int dx, 水平方向移动的距离，>0向左偏移，<0向右偏移 int dy,
	 * 垂直方向移动的距离，>0向上偏移，<0向下偏移 int duration 移动动画持续的时间
	 * @author 小木桩
	 */
	public void showMenu() {
		if(touchState==STATE_SCROLLING){
			return;
		}
		isMenuOpen = true;
		scroller.startScroll(getScrollX(), 0, -distance, 0, duration);
		invalidate();// 刷新
	}

	/**
	 * 正常的关闭动画效果
	 * @author 小木桩
	 */
	public void closeMenu() {
		if(touchState==STATE_SCROLLING){
			return;
		}
		isMenuOpen = false;
		scroller.startScroll(getScrollX(), 0, distance, 0, duration);
		invalidate();// 刷新
	}

	/**
	 * closeMenuPull01和closeMenuPull02是一套另外的执行关闭菜单的方式，带有反弹的效果
	 * 先执行关闭01的动画，接着执行02的关闭动画
	 */
	public void closeMenuPull01() {
		if(touchState==STATE_SCROLLING){
			return;
		}
		scroller.startScroll(getScrollX(), 0, distance , 0,
				duration);
		invalidate();// 刷新
	}

	// 关闭菜单（执行自定义动画）
	private void closeMenuPull02() {
		if(touchState==STATE_SCROLLING){
			return;
		}
		isMenuOpen = false;
		scroller.startScroll(getScrollX(), 0, getWidth(), 0, duration);
		invalidate();// 刷新
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
				// 返回true表示执行自身的ontouch方法，若返回false则执行子view的onTouch方法进行处理
				return true;// 交给自身的touch的方法去处理
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
			scrollBy(moveX, 0);//从现在的位置，开始移动，移动的距离分别为X，Y放心的距离大小
			lastMotionX=tempX;
			touchState=STATE_SCROLLING;
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			// 没有超过半屏\
			int scrollX=getScrollX();
			if(scrollX>0){
				scrollTo(0, 0);
			}else if (scrollX < -width / 3) {
				scrollTo(-width / 3, 0);
				//scroller.startScroll(getScrollX(), 0, -getScrollX(), 0, duration);
				isMenuOpen = true;
			}
			// 超过半屏
			else if (scrollX >= -width / 3) {
//				scroller.startScroll(getScrollX(), 0, -(distance + getScrollX()),
//						0, duration);
				scrollTo(0, 0);
				isMenuOpen = false;
			}
	
			invalidate();// 刷新		
			touchState = STATE_NULL;
			break;
		}
		return super.onTouchEvent(event);
	}
}
