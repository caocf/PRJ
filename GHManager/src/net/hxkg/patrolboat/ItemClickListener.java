package net.hxkg.patrolboat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.hxkg.book.TreeNode;
import net.hxkg.network.Constants;
import net.hxkg.network.HttpRequest;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ItemClickListener implements OnItemClickListener ,HttpRequest.onResult
{
    /** 定义的适配器 */
    private BoatAdapter treeViewAdapter;
    ArrayList<TreeNode> allNodes;
    ArrayList<TreeNode> topNodes;
    TreeNode treeNode; 
    int position;
    int mode;
    Activity activity;
    
    public ItemClickListener(BoatAdapter treeViewAdapter,Activity activity,int mode)
    {
        this.treeViewAdapter = treeViewAdapter;
        this.mode=mode;
        this.activity=activity;
    }
     
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,long id) 
    {
        //点击的item代表的元素
    	treeNode = (TreeNode) treeViewAdapter.getItem(position);
        //树中顶层的元素
        topNodes = treeViewAdapter.getTopNodes();
        //元素的数据源
        allNodes = treeViewAdapter.getAllNodes();
        this.position=position;
         
        //点击没有子项的item
        if (!treeNode.isHasChildren()) 
        {
            if(mode==2)
            {
            	Intent data=new Intent();
            	data.putExtra("name", treeNode.getContentText());
            	activity.setResult(200, data);
            	activity.finish();
            	return;
            }
            Intent intent =new Intent(activity,BoatInfoActivity.class);
            intent.putExtra("treeNode", treeNode);
            activity.startActivity(intent);
        }
         
        if (treeNode.isExpanded())//收起
        {
            treeNode.setExpanded(false);
            //删除节点内部对应子节点数据，包括子节点的子节点...
            ArrayList<TreeNode> elementsToDel = new ArrayList<TreeNode>();
            for (int i = position + 1; i < topNodes.size(); i++)
            {
                int l1=treeNode.getLevel();
                int l2=topNodes.get(i).getLevel();
                String string=topNodes.get(i).getContentText();
            	if ( l1>= l2)
                    break;
                elementsToDel.add(topNodes.get(i));
            }
            topNodes.removeAll(elementsToDel);
            allNodes.removeAll(elementsToDel);
            treeViewAdapter.notifyDataSetChanged();
        } else //展开
        {
            treeNode.setExpanded(true);
            HttpRequest httpRequest=new HttpRequest(this);
            Map<String, Object> map=new HashMap<>();
    		map.put("pid", treeNode.getId());
    		httpRequest.post(Constants.BaseURL+"DepByPID", map);
        }
    }

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
				boolean haschildren="1".equals(s4)?true:false;
				String s5=object.getString("member");
				String s6=object.getString("level");
				
				TreeNode node1= new TreeNode(s1, Integer.valueOf(s6), Integer.valueOf(s2), 
														Integer.valueOf(s3), haschildren, false);
				node1.setMember(s5);
				allNodes.add(node1);
			}
			//从数据源中提取子节点数据添加进树，注意这里只是添加了下一级子节点，为了简化逻辑
            int i = 1;//注意这里的计数器放在for外面才能保证计数有效
            for (TreeNode e : allNodes) {
                if (e.getParendId() == treeNode.getId()) {
                    e.setExpanded(false);
                    topNodes.add(position + i, e);
                    i ++;
                }
            }
			treeViewAdapter.setAllNodes(allNodes);
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
