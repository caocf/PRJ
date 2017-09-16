package com.example.smarttraffic.view;

import com.example.smarttraffic.R;

import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * 自定义下拉框控件
 * @author Administrator zhou
 *
 */
public class InputListView extends LinearLayout
{
	private EditText inputTextView;
	private ImageView downListImageView;
	private ListView inputListView;
	
	InputSelectAdapter selectAdapter;				//下拉框内容适配器
	
	String[] dataStrings;							//下拉框内容文本
	int[] dataImages;								//下拉框内容文本对于图标
	InputListViewListener[] listeners;				//下拉框内容点击事件
		
	public void setDataStrings(String[] dataStrings)
	{
		this.dataStrings = dataStrings;
	}
	
	public void setDataImages(int[] images)
	{
		this.dataImages=images;
	}
	
	/**
	 * 设置下拉框点击事件
	 * @param listener 监听事件
	 * @param i 对应位置
	 */
	public void setListeners(InputListViewListener listener,int i)
	{
		if(listeners==null)
        {
    	    listeners=new InputListViewListener[dataStrings.length];
        }	
		this.listeners[i] = listener;
	}
	
	/**
	 * 更新视图和点击事件
	 * @param names 内容
	 * @param images 对应图标
	 * @param listeners 监听事件
	 */
	public void updateView(String[] names,int[] images,InputListViewListener[] listeners)
	{
		setDataStrings(names);
		setDataImages(images);
		for(int i=0;i<listeners.length;i++)
		{
			setListeners(listeners[i], i);
		}
		
		selectAdapter.update(names, images);
		
	}

	public InputListView(Context context) {
		this(context,null);
			
	}
	
	/**
	 * 设置视图，如无设置下拉框内容，则使用默认内容
	 * @param context
	 * @param attrs
	 */
	public InputListView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        
       View root=  LayoutInflater.from(context).inflate(R.layout.input_listview_layout, this, true);
       inputTextView=(EditText)root.findViewById(R.id.input_list_edit);
       downListImageView=(ImageView)root.findViewById(R.id.input_down_List_image);
       inputListView=(ListView)root.findViewById(R.id.input_choice_list);
       
       if(dataStrings==null)
       {
    	   dataStrings=context.getResources().getStringArray(R.array.array_other_inputview_default_name);
    	   dataImages=new int[]{R.drawable.input_speech_icon,R.drawable.input_favorites_icon,R.drawable.input_mylocation_icon,R.drawable.input_icon_maplocation};
       }
         
       selectAdapter=new InputSelectAdapter(getContext(), dataStrings, dataImages);
       
//       ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(), R.layout.left_item_listvalue_layout,dataStrings);
       inputListView.setAdapter(selectAdapter);
       inputListView.setOnItemClickListener(new onListClick());
       
       downListImageView.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			
			switch (inputListView.getVisibility()) {
				case VISIBLE:
					showList(false);
					break;
	
				case GONE:
					showList(true);
					break;				
			}
		}
       });
    }  
	
	/**
	 * 下拉框内容点击事件
	 *
	 */
	class onListClick implements android.widget.AdapterView.OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id)
		{
			if(listeners!=null && listeners[position]!=null)
			{
				listeners[position].inputSelectListener();	
			}
			
			showList(false);
		}
	}
	
	
	/**
	 * 下拉框是否显示
	 * @param show 是否显示
	 */
	public void showList(boolean show)
	{
		if(show)
		{
			inputListView.setVisibility(VISIBLE);
			downListImageView.setImageResource(R.drawable.selector_input_up);
		}
		else
		{
			inputListView.setVisibility(GONE);
			downListImageView.setImageResource(R.drawable.selector_input_dowm);
		}
	}
	
	/**
	 * 设置提示信息
	 * @param str
	 */
	public void setHint(String str)
	{
		inputTextView.setHint(str);
	}
	
	/**
	 * 获取内容
	 * @return 内容
	 */
	public String getText()
	{
		return inputTextView.getText().toString();
	}
	
	/**
	 * 设置内容
	 * @param str 内容
	 */
	public void setText(String str)
	{
		inputTextView.setText(str);
	}
	
	public void setTextColor(int color)
	{
		inputTextView.setTextColor(color);
	}

	public void setTextWatcher(TextWatcher textWatcher)
	{
		inputTextView.addTextChangedListener(textWatcher);
	}
	
}
