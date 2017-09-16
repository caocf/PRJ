package net.hxkg.book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;
import net.hxkg.network.HttpRequest.onResult;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class TreeItemClickListener implements OnItemClickListener ,HttpRequest.onResult
{
    /** 定义的适配器 */
    private TreeAdapter treeViewAdapter;
    ArrayList<TreeNode> allNodes;
    ArrayList<TreeNode> topNodes;
    OrgnizeItem treeNode; 
    int position;
    int mode;
    Activity activity;  
    
    public TreeItemClickListener(TreeAdapter treeViewAdapter,Activity activity,int mode )
    {
        this.treeViewAdapter = treeViewAdapter;
        this.mode=mode;
        this.activity=activity; ;
    }
     
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
    {
        //点击的item代表的元素
    	treeNode = (OrgnizeItem) (treeViewAdapter.getItem(position));
        //树中顶层的元素
        topNodes = treeViewAdapter.getTopNodes();
        //元素的数据源
        //allNodes = treeViewAdapter.getAllNodes();
        this.position=position;
         
        //点击没有子项的item直接返回
        /*if (!treeNode.isHasChildren()) 
        {
            
        }*/
        
        switch (treeNode.getType()) 
        {
		case 1:
		{
			if (treeNode.isExpanded())//已展开，点击收起
            {
                treeNode.setExpanded(false);
                //删除节点内部对应子节点数据，包括子节点的子节点...
                ArrayList<TreeNode> elementsToDel = new ArrayList<TreeNode>();
                for (int i = position + 1; i < topNodes.size(); i++)
                {
                    int l1=treeNode.getLevel();
                    int l2=topNodes.get(i).getLevel();
                	if ( l1>= l2)
                        break;
                    elementsToDel.add(topNodes.get(i));
                }
                topNodes.removeAll(elementsToDel);
                //allNodes.removeAll(elementsToDel);
                treeViewAdapter.notifyDataSetChanged();
            } else //点击展开
            {
                treeNode.setExpanded(true);
                HttpRequest httpRequest=new HttpRequest(this);
                Map<String, Object> map=new HashMap<>();
        		map.put("pid", treeNode.idString);
        		//httpRequest.post(Constants.BaseURL+"UsersByPID", map);
        		httpRequest.post(Constants.BaseURL+"ItemsByPid", map);
            }
			break;
		}
		case 2:
		{
			if (treeNode.isExpanded())//已展开，点击收起
            {
                treeNode.setExpanded(false);
                //删除节点内部对应子节点数据，包括子节点的子节点...
                ArrayList<TreeNode> elementsToDel = new ArrayList<TreeNode>();
                for (int i = position + 1; i < topNodes.size(); i++)
                {
                    int l1=treeNode.getLevel();
                    int l2=topNodes.get(i).getLevel();
                	if ( l1>= l2)
                        break;
                    elementsToDel.add(topNodes.get(i));
                }
                topNodes.removeAll(elementsToDel);
                //allNodes.removeAll(elementsToDel);
                treeViewAdapter.notifyDataSetChanged();
            } else //点击展开
            {
                treeNode.setExpanded(true);
                HttpRequest httpRequest=new HttpRequest(new onResult()
                {

					@Override
					public void onSuccess(String result) 
					{
						List<OrgnizeItem> children=new ArrayList<>();
						try 
						{
							JSONObject baseresult=new JSONObject(result);
							JSONArray jsonArray=baseresult.getJSONArray("obj");
							for(int i=0;i<jsonArray.length();i++)
							{
								JSONObject object=(JSONObject) jsonArray.opt(i);
												
								String s2=object.getString("id");
								String s3=object.getString("xm");
								//String s4=object.getString("zzjglb");								
								
								OrgnizeItem orgnizeItem=new OrgnizeItem();
								orgnizeItem.setExpanded(false);
								orgnizeItem.idString=s2;
								orgnizeItem.zzjgmc=s3;
								orgnizeItem.zzjglb="3";
								orgnizeItem.bm=object.getString("bmmc");
								orgnizeItem.dw=object.getString("dw"); 
								orgnizeItem.telString=object.getString("sjhm"); 
								orgnizeItem.setLevel(treeNode.getLevel()+1);
								children.add(orgnizeItem);
								
								
							}
							//从数据源中提取子节点数据添加进树，注意这里只是添加了下一级子节点，为了简化逻辑
				            int i = 1;//注意这里的计数器放在for外面才能保证计数有效
				            for (OrgnizeItem e : children)
				            {                
				                    e.setExpanded(false);
				                    topNodes.add(TreeItemClickListener.this.position + i, e);
				                    i ++;                
				            }
							//treeViewAdapter.setAllNodes(allNodes);
							treeViewAdapter.notifyDataSetChanged();
							
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
                String s=treeNode.idString;
        		map.put("pid", treeNode.idString);
        		//httpRequest.post(Constants.BaseURL+"UsersByPID", map);
        		httpRequest.post(Constants.BaseURL+"CrewsByDepID", map);
            }
			break;
		}
		case 3:
		{
			if(mode==1)//查看模式
            {
				Intent intent =new Intent(activity,BookInfoActivity.class);
				YHModel yhModel=new YHModel();
				yhModel.bmmc=treeNode.bm;
				yhModel.nameString=treeNode.zzjgmc;
				yhModel.dwid=treeNode.dw;
				yhModel.tel1=treeNode.telString;
				intent.putExtra("YHModel", yhModel);
				activity.startActivity(intent);
				return;
            }
            Intent intent =new Intent();
            intent.putExtra("name", treeNode.zzjgmc);
            activity.setResult(BookActivity.RESULT, intent);
            activity.finish();
            break;
		}		

		default:
			break;
		}   
        
        
    }

	@Override
	public void onSuccess(String result) 
	{
		List<OrgnizeItem> children=new ArrayList<>();
		try 
		{
			JSONObject baseresult=new JSONObject(result);
			JSONArray jsonArray=baseresult.getJSONArray("obj");
			for(int i=0;i<jsonArray.length();i++)
			{
				JSONObject object=(JSONObject) jsonArray.opt(i);
								
				String s2=object.getString("id");
				String s3=object.getString("zzjgmc");
				String s4=object.getString("zzjglb");
				/*boolean haschildren="1".equals(s4)?true:false;
				String s5=object.getString("expand");
				boolean expand="1".equals(s5)?true:false;
				String s6=object.getString("level");
				
				OrgnizeItem node1= new TreeNode(s1, Integer.valueOf(s6), Integer.valueOf(s2), 
														Integer.valueOf(s3), haschildren, false);
				//allNodes.add(node1);*/
				
				OrgnizeItem orgnizeItem=new OrgnizeItem();
				orgnizeItem.setExpanded(false);
				orgnizeItem.idString=s2;
				orgnizeItem.zzjgmc=s3;
				orgnizeItem.zzjglb=s4;
				orgnizeItem.setLevel(treeNode.getLevel()+1);
				children.add(orgnizeItem);
			}
			//从数据源中提取子节点数据添加进树，注意这里只是添加了下一级子节点，为了简化逻辑
            int i = 1;//注意这里的计数器放在for外面才能保证计数有效
            for (OrgnizeItem e : children)
            {                
                    e.setExpanded(false);
                    topNodes.add(position + i, e);
                    i ++;                
            }
			//treeViewAdapter.setAllNodes(allNodes);
			treeViewAdapter.notifyDataSetChanged();
			
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
 
}
