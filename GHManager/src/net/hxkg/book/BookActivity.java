package net.hxkg.book;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import net.hxkg.ghmanager.R;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import android.app.Activity;
import android.content.Context; 
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View; 
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class BookActivity extends Activity implements HttpRequest.onResult,TextWatcher,OnItemClickListener
{
	ArrayList<TreeNode> topNodes=new ArrayList<>();
	ArrayList<TreeNode> allNodes=new ArrayList<>();
	TreeAdapter treeViewAdapter;
	ListView treeview,list2;
	int mode;
	EditText editText;
	LinearLayout lay1;
	TextView t1,t2;
	SimpleAdapter sdAdapter;
	ArrayList<Map<String, Object>> names=new ArrayList<>();
	
	YHModel yhModel=new YHModel();
	
	public static final int RESULT=500; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_book);
		mode=getIntent().getIntExtra("mode", 1);//1 为查看模式，其他为选择名字模式
		
		init() ;
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		treeview = (ListView) findViewById(R.id.tree_list);
		treeViewAdapter= new TreeAdapter(topNodes, allNodes, inflater);
		TreeItemClickListener treeViewItemClickListener = new TreeItemClickListener(treeViewAdapter,this,mode);
	    treeview.setAdapter(treeViewAdapter);
	    treeview.setOnItemClickListener(treeViewItemClickListener);
	    
	    editText=(EditText) findViewById(R.id.edit);
	    editText.addTextChangedListener(this);
	    lay1=(LinearLayout) findViewById(R.id.lay1);
	    lay1.setOnClickListener(new ClickListener());
	    t1=(TextView) findViewById(R.id.text);
	    t2=(TextView) findViewById(R.id.text1); 
	    list2=(ListView) findViewById(R.id.list2);
	    sdAdapter=new SimpleAdapter(this,names , R.layout.item_booknames,new String[]{"text"} , new int[]{R.id.text});
	    list2.setAdapter(sdAdapter);
	    list2.setOnItemClickListener(this);
	    
	}
	
	public void onClick(View v)
	{
		this.finish();
	}
	
	public class ClickListener implements View.OnClickListener
	{
		@Override
		public void onClick(View v) 
		{
			if(mode==1)//查看模式
            {				
				Intent intent =new Intent(BookActivity.this,BookInfoActivity.class);
				BookActivity.this.startActivity(intent);
				
				return;
            }
            Intent intent =new Intent();
            intent.putExtra("name", editText.getText().toString());
            BookActivity.this.setResult(BookActivity.RESULT, intent);
            BookActivity.this.finish();
			
		}		
		
	}
	
	private void init() 
	{        
        HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult()
        {

			@Override
			public void onSuccess(String result) 
			{
				try 
				{
					JSONObject baseresult=new JSONObject(result);
					JSONArray jsonArray=baseresult.getJSONArray("obj");
					for(int i=0;i<jsonArray.length();i++)
					{
						JSONObject object=(JSONObject) jsonArray.opt(i);
						String s1=object.getString("id");
						String s2=object.getString("zzjgmc");
						String s3=object.getString("zzjglb");
						/*String s3=object.getString("pid");
						String s4=object.getString("haschildren");
						boolean haschildren="1".equals(s4)?true:false;
						String s5=object.getString("expand");
						boolean expand="1".equals(s5)?true:false;
						
						TreeNode node1= new TreeNode(s1, TreeNode.TOP_LEVEL, Integer.valueOf(s2), 
																		TreeNode.NO_PARENT, haschildren, false);*/
						
						OrgnizeItem orgnizeItem=new OrgnizeItem();
						orgnizeItem.idString=s1;
						orgnizeItem.zzjgmc=s2;//System.out.println(s2);
						orgnizeItem.zzjglb=s3;	
						orgnizeItem.setExpanded(false);
						orgnizeItem.setLevel(0);
						topNodes.add(orgnizeItem);
						
						
					}
					
					treeViewAdapter.notifyDataSetChanged();
					
					//treeview.performItemClick(null, 0, 0);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			@Override
			public void onError(int httpcode) 
			{
				// TODO Auto-generated method stub
				
			}
        	
        });
		Map<String, Object> map=new HashMap<>();
		map.put("pid", "B4EB412FAFF44223A53456711CA3D5EA");//省港航局ID
		//httpRequest.post(Constants.BaseURL+"UsersByLevel", map);
		httpRequest.post(Constants.BaseURL+"ItemsByPid", map);
         
    }
	
	public void onBack(View v)
	{
		this.finish();
	}
	
	public void onChoose(View v)
	{
		if(mode==1)//查看模式
        {				
			Intent intent =new Intent(BookActivity.this,BookInfoActivity.class);
			intent.putExtra("YHModel", yhModel);
			BookActivity.this.startActivity(intent);
			
			return;
        }
        Intent intent =new Intent();
        intent.putExtra("name", editText.getText().toString());
        BookActivity.this.setResult(BookActivity.RESULT, intent);
        BookActivity.this.finish();
	}
	
	public void onClear(View v)
	{
		editText.setText("");
	}
	
	public void onSearch(String s)
	{
		HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult()
		{
			@Override
			public void onSuccess(String result) 
			{				
				try 
				{
					JSONArray arr=new JSONArray(result.trim());
					for(int i=0;i<1;i++)
					{
						JSONObject object=arr.getJSONObject(i);
						String nameString=object.getJSONObject("person").getString("xm");
						String tel=object.getJSONObject("person").getString("sjhm");
						String dwString=object.getString("dw");
						String bmString=object.getString("bm");
						String st1="";
						st1=dwString+">>"+bmString;
						t1.setText(st1);
						t2.setText(nameString); 
						
						yhModel.nameString=nameString;
						yhModel.dw=dwString;
						yhModel.bmmc=object.getJSONObject("person").getString("bmmc");
						yhModel.tel1=tel;
						//yhModel.nameString=nameString;
					}				
					
					treeview.setVisibility(View.GONE);
					list2.setVisibility(View.GONE);
					lay1.setVisibility(View.VISIBLE);
					names.clear();
					sdAdapter.notifyDataSetChanged();
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			@Override
			public void onError(int httpcode) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		Map<String, Object> map=new HashMap<>();
		map.put("name", s);
		editText.setText(s);
		httpRequest.post(Constants.BaseURL+"UserByName", map);
	}

	@Override
	public void onSuccess(String result) 
	{
		JSONObject data;
		try 
		{
			data=new JSONObject(result);
			String s1=data.getString("resultdesc");
			Toast.makeText(BookActivity.this, s1, Toast.LENGTH_LONG).show();
			String s2=data.getString("resultcode");
			if("1".equals(s2))
			{
				this.finish();
			}
		}
		catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onError(int httpcode) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterTextChanged(Editable s)
	{
		if(s.toString().equals(""))
		{
			treeview.setVisibility(View.VISIBLE);
			lay1.setVisibility(View.GONE);
			list2.setVisibility(View.GONE);
			names.clear();
			sdAdapter.notifyDataSetChanged();
			return;
		}
		
		HttpRequest httpRequest=new HttpRequest(new HttpRequest.onResult()
		{
			@Override
			public void onSuccess(String result)
			{
				try {
					JSONArray array=new JSONArray(result);
					names.clear();
					for(int i=0;i<array.length();i++)
					{						
						Map<String, Object> map=new HashMap<>();
						map.put("text", array.getString(i));
						names.add(map);
					}
					treeview.setVisibility(View.GONE);
					lay1.setVisibility(View.GONE);
					list2.setVisibility(View.VISIBLE);
					
					sdAdapter.notifyDataSetChanged();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}

			@Override
			public void onError(int httpcode) {
				// TODO Auto-generated method stub
				
			}
			
		});
		Map<String, Object> map=new HashMap<>();
		map.put("tip", s.toString());
		httpRequest.post(Constants.BaseURL+"UserNamesByTip", map);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id)
	{
		
		onSearch((String)names.get(position).get("text"));		
		
	}
}
