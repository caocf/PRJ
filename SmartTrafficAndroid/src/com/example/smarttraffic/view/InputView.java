package com.example.smarttraffic.view;

import com.example.smarttraffic.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 自定义控件（已弃用）
 * @author Administrator zhou
 *
 */
public class InputView extends LinearLayout
{
	private TextView textInputView;
	private ImageView voiceInputView;
	private ImageView locationInputView;
	
	public InputView(Context context) {
		this(context,null);
			
	}
	
	public InputView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        
        LayoutInflater.from(context).inflate(R.layout.inputviewlayout, this, true);
		textInputView=(TextView)findViewById(R.id.textInput);
		voiceInputView=(ImageView)findViewById(R.id.voiceInput);
		locationInputView=(ImageView)findViewById(R.id.locationInput);	
		
		textInputView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getContext(), "text", Toast.LENGTH_LONG).show();
				
			}
		});
		
		voiceInputView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getContext(), "voice", Toast.LENGTH_LONG).show();
				
			}
		});

		locationInputView.setOnClickListener(new View.OnClickListener() {
	
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getContext(), "location", Toast.LENGTH_LONG).show();
		
			}
		});
    }  
	
	
}
