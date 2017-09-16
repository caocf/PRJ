package com.huzhouport.car;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class CarGridView extends GridView
{
	public CarGridView(Context context) {
        super(context);
    }

    public CarGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CarGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CarGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }*/

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    //  AT_MOST参数表示控件可以自由调整大小，最大不超过Integer.MAX_VALUE/4
        int height=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2,MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
    }
}
