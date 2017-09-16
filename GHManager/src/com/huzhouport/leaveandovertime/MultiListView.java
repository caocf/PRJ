package com.huzhouport.leaveandovertime;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 解决listview与scrollview嵌套冲突问题
 * @author Administrator
 *
 */
public class MultiListView extends ListView {  
    public MultiListView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    public MultiListView(Context context) {  
        super(context);  
    }  
  
    public MultiListView(Context context, AttributeSet attrs, int defStyle) {  
        super(context, attrs, defStyle);  
    }  
  
    @Override  
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
  
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
                MeasureSpec.AT_MOST);  
        super.onMeasure(widthMeasureSpec, expandSpec);  
    }  
  
}  