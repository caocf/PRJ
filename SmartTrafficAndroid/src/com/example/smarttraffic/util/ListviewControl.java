package com.example.smarttraffic.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * listview 布局控制
 * @author Administrator zhou
 *
 */
public class ListviewControl
{
	
	/**
	 * 根据子元素计算设置listview高度
	 * @param listView 目标listview
	 */
    public static void setListViewHeightBasedOnChildren(ListView listView) {  
        ListAdapter listAdapter = listView.getAdapter();   
        if (listAdapter == null) {  
            return;  
        }  
  
        int totalHeight = 0;  
        for (int i = 0; i < listAdapter.getCount(); i++) {  
            View listItem = listAdapter.getView(i, null, listView);  
            listItem.measure(0, 0);  
            totalHeight += listItem.getMeasuredHeight();  
        }  
  
        ViewGroup.LayoutParams params = listView.getLayoutParams();  
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));  
        listView.setLayoutParams(params);  
    } 
    
    public static void setGridViewHeightBasedOnChildren(GridView gridView,int rowNum,int extra) {  
        ListAdapter listAdapter = gridView.getAdapter();   
        if (listAdapter == null) {  
            return;  
        }  
  
        int totalHeight = 0;   
        
        View listItem = listAdapter.getView(0, null, gridView);  
        listItem.measure(0, 0);  
        totalHeight = listItem.getMeasuredHeight()*rowNum;  
        
  
        ViewGroup.LayoutParams params = gridView.getLayoutParams();  
        params.height = totalHeight+extra;  
        gridView.setLayoutParams(params);  
    }
    
    
    /**
     * 获取视图的实际高度
     * @param v 视图
     * @return 高度
     */
    public static int getViewHeight(View v)
    {
    	v.measure(0, 0);
    	return v.getMeasuredHeight();
    }
    
    /**
     * 按指定高度将gridview撑满
     * @param gridView 目标视图
     * @param colum 列数
     * @param height 高度
     */
    public static void setGridViewFull(GridView gridView,int colum,int height)
    {
    	
    	View view=gridView.getAdapter().getView(0, null, gridView);
    	
    	view.measure(0, 0);
    	int innerHeight=view.getMeasuredHeight();
    	
    	int count=gridView.getAdapter().getCount();
    	
    	int row=count/colum+(count%colum==0?0:1);
    	
    	int padding=(height-innerHeight*row)/row;
    	
    	gridView.setVerticalSpacing(padding);
    }
}
