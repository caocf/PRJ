package com.huzhouport.dangerousgoodsportln;





import com.example.huzhouport.R;
import com.huzhouport.cruiselog.CruiselogList;
import com.huzhouport.dangerousgoodsjob.DangerousgoodsjobAddList;
import com.huzhouport.dangerousgoodsjob.DangerousgoodsjobList;
import com.huzhouport.knowledge.KnowledgeView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Dangerousgoodsportln extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dangerousgoodsportln_text);
	Button button=(Button)findViewById(R.id.dangerousgoodsportln_text1);
     button.setOnClickListener(new DangerousgoodsportlnButton());
     Button button2=(Button)findViewById(R.id.dangerousgoodsportln_text2);
     button2.setOnClickListener(new DangerousgoodsportlnButton1());
     
     Button button1=(Button)findViewById(R.id.dangerousgoodsjob_text1);
     button1.setOnClickListener(new Dangerousgoodsjob());	
     Button button3=(Button)findViewById(R.id.dangerousgoodsjob_text2);
     button3.setOnClickListener(new Dangerousgoodsjob1());
     
     Button button4=(Button)findViewById(R.id.cruiselog_text);
     button4.setOnClickListener(new Cruiselog());
     
	}
	

    
	class  DangerousgoodsportlnButton implements OnClickListener{

		@Override
		public void onClick(View v) {
			System.out.println("adc");
			//Intent intent=new Intent(Addresslist.this, AddresslistMenu.class); 
			Intent intent=new Intent(Dangerousgoodsportln.this, DangerousgoodsportlnList.class); 
			startActivity(intent);
		
			
		}
	
	}
	class  DangerousgoodsportlnButton1 implements OnClickListener{

		@Override
		public void onClick(View v) {
			System.out.println("adc");
			//Intent intent=new Intent(Addresslist.this, AddresslistMenu.class); 
			Intent intent=new Intent(Dangerousgoodsportln.this, DangerousgoodsportlnAddList.class); 
			startActivity(intent);
		
			
		}
	
	}
	class  Dangerousgoodsjob implements OnClickListener{

		@Override
		public void onClick(View v) {
			System.out.println("adc");
			//Intent intent=new Intent(Addresslist.this, AddresslistMenu.class); 
			Intent intent=new Intent(Dangerousgoodsportln.this, DangerousgoodsjobList.class); 
			
			startActivity(intent);
		
			
		}
	
	}
	
	class  Dangerousgoodsjob1 implements OnClickListener{

		@Override
		public void onClick(View v) {
			System.out.println("adc");
			//Intent intent=new Intent(Addresslist.this, AddresslistMenu.class); 
			Intent intent=new Intent(Dangerousgoodsportln.this, DangerousgoodsjobAddList.class); 
			
			startActivity(intent);
		
			
		}
	
	}
	
	class  Cruiselog implements OnClickListener{

		@Override
		public void onClick(View v) {
	
			//Intent intent=new Intent(Addresslist.this, AddresslistMenu.class); 
			Intent intent=new Intent(Dangerousgoodsportln.this, CruiselogList.class); 
			
			startActivity(intent);
		
			
		}
	
	}
	
	

}
