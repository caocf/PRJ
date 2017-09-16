package net.hxkg.patrolboat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import net.hxkg.book.TreeNode;
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

public class SearchBoatActivity extends Activity implements HttpRequest.onResult,TextWatcher,OnItemClickListener
{
	ArrayList<TreeNode> topNodes=new ArrayList<>();
	ArrayList<TreeNode> allNodes=new ArrayList<>();
	BoatAdapter treeViewAdapter;
	ListView treeview,list2;
	int mode;
	EditText editText;
	LinearLayout lay1;
	TextView t1,t2,t3;
	SimpleAdapter sdAdapter;
	ArrayList<Map<String, Object>> names=new ArrayList<>();
	
	public static final int RESULT=500;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patrolboat);
		mode=getIntent().getIntExtra("mode", 1);
		
		init() ;
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		treeview = (ListView) findViewById(R.id.tree_list);
		treeViewAdapter= new BoatAdapter(topNodes, allNodes, inflater);
		ItemClickListener treeViewItemClickListener = new ItemClickListener(treeViewAdapter,this,mode);
	    treeview.setAdapter(treeViewAdapter);
	    treeview.setOnItemClickListener(treeViewItemClickListener);
	    
	    editText=(EditText) findViewById(R.id.edit);
	    editText.addTextChangedListener(this);
	    lay1=(LinearLayout) findViewById(R.id.lay1);
	    t1=(TextView) findViewById(R.id.text);
	    t2=(TextView) findViewById(R.id.text1);
	    t3=(TextView) findViewById(R.id.text2);
	    list2=(ListView) findViewById(R.id.list2);
	    sdAdapter=new SimpleAdapter(this,names , R.layout.item_booknames,new String[]{"text"} , new int[]{R.id.text});
	    list2.setAdapter(sdAdapter);
	    list2.setOnItemClickListener(this);
	    
	}
	
	public void onClick(View v)
	{
		this.finish();
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
					JSONArray jsonArray=new JSONArray(result);
					for(int i=0;i<jsonArray.length();i++)
					{
						JSONObject object=(JSONObject) jsonArray.opt(i);
						String s1=object.getString("name");
						String s2=object.getString("id");
						String s3=object.getString("pid");
						String s4=object.getString("haschildren");
						String s5=object.getString("member");
						boolean haschildren="1".equals(s4)?true:false;
						
						
						TreeNode node1= new TreeNode(s1, TreeNode.TOP_LEVEL, Integer.valueOf(s2), 
																		TreeNode.NO_PARENT, haschildren, false);
						node1.setMember(s5);
						topNodes.add(node1);
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
		map.put("level", TreeNode.TOP_LEVEL);
		httpRequest.post(Constants.BaseURL+"DepByLevel", map);
        
    }
	
	public void onBack(View v)
	{
		this.finish();
	}
	
	public void onChoose(View v)
	{
		 if(mode==1)
         {
         	return;
         }
         Intent intent =new Intent();
         intent.putExtra("name", t2.getText());
         setResult(SearchBoatActivity.RESULT, intent);
         finish();
	}
	
	public void onSearch(String s)
	{
		TreeNode node=new TreeNode();
		node.setContentText(s);
		Intent intent=new Intent(SearchBoatActivity.this,BoatInfoActivity.class);
		intent.putExtra("treeNode", node);
		SearchBoatActivity.this.startActivity(intent);
	}

	@Override
	public void onSuccess(String result) 
	{
		JSONObject data;
		try 
		{
			data=new JSONObject(result);
			String s1=data.getString("resultdesc");
			Toast.makeText(SearchBoatActivity.this, s1, Toast.LENGTH_LONG).show();
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
					e.printStackTrace();
				}
				
				
			}

			@Override
			public void onError(int httpcode) {
				
			}
			
		});
		Map<String, Object> map=new HashMap<>();
		map.put("tip", s.toString());
		httpRequest.post(Constants.BaseURL+"ShipNamesByTip", map);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id)
	{
		onSearch((String)names.get(position).get("text"));
		
		
	}
}
