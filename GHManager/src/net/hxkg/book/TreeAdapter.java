package net.hxkg.book;

import java.util.ArrayList;

import net.hxkg.ghmanager.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TreeAdapter extends BaseAdapter {
    /** 所有的数据集合 */
    private ArrayList<TreeNode> allNodes;
    /** 顶层元素结合*/
    private ArrayList<TreeNode> topNodes;
    /** LayoutInflater */
    private LayoutInflater inflater;
    /** item的行首缩进基数 */
    private int indentionBase;
     
    public TreeAdapter(ArrayList<TreeNode> topNodes, ArrayList<TreeNode> allNodes, LayoutInflater inflater) {
        this.topNodes = topNodes;
        this.allNodes = allNodes;
        this.inflater = inflater;
        indentionBase =40;
    }
    public ArrayList<TreeNode> getTopNodes() {
        return topNodes;
    }
     
    public ArrayList<TreeNode> getAllNodes() {
        return allNodes;
    }
    public void setAllNodes(ArrayList<TreeNode> allNodes) {
        this.allNodes=allNodes;
    }
     
    @Override
    public int getCount() {
        return topNodes.size();
    }
 
    @Override
    public Object getItem(int position) {
        return topNodes.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null) 
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_book, null);
            holder.treeText = (TextView) convertView.findViewById(R.id.text);
            holder.treeText1 = (TextView) convertView.findViewById(R.id.text1);
            holder.leftImg = (ImageView) convertView.findViewById(R.id.leftImg);
            holder.rightImg = (ImageView) convertView.findViewById(R.id.rightImg);
            convertView.setTag(holder);            
        } 
        else 
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.treeText1.setVisibility(View.GONE);
        holder.leftImg.setVisibility(View.VISIBLE);
        holder.rightImg.setVisibility(View.VISIBLE);
        
        OrgnizeItem element = (OrgnizeItem) topNodes.get(position);
        int level =element.getLevel();
        convertView.setPadding(
                indentionBase * (level + 1),
                holder.leftImg.getPaddingTop(),
                holder.leftImg.getPaddingRight(),
                holder.leftImg.getPaddingBottom());
        
        holder.treeText.setText(element.zzjgmc);
        switch (element.getType()) 
        {
		case 1:
			holder.leftImg.setImageResource(R.drawable.ic_daily_closed);
			if(element.isExpanded())//单位
			{
				holder.rightImg.setImageResource(R.drawable.ic_dropdown);
			}
			else 
			{
				holder.rightImg.setImageResource(R.drawable.icon_right);
			}
			
			break;
		case 2://部门
			holder.leftImg.setImageResource(R.drawable.ic_daily_open);
			if(element.isExpanded())
			{
				holder.rightImg.setImageResource(R.drawable.ic_dropdown);
			}
			else 
			{
				holder.rightImg.setImageResource(R.drawable.icon_right);
			}
			break;
		case 3://人员
			holder.leftImg.setImageResource(R.drawable.ic_avatar);
			holder.rightImg.setVisibility(View.GONE);
			break;

		default:
			break;
		}
        
        //holder.treeText.setText(element.getContentText());
        /*holder.treeText.setText(element.zzjgmc);
        
        if (element.isHasChildren() && !element.isExpanded()) 
        {
            holder.rightImg.setImageResource(R.drawable.icon_right);
        } else if (element.isHasChildren() && element.isExpanded())
        {
            holder.rightImg.setImageResource(R.drawable.ic_dropdown);
        } else if (!element.isHasChildren()) 
        {
            /*holder.rightImg.setImageResource(R.drawable.ic_phone2);
            holder.treeText1.setText("139554585");
            holder.treeText1.setVisibility(View.VISIBLE);
            holder.leftImg.setVisibility(View.GONE);
            holder.rightImg.setVisibility(View.GONE);
        }*/
        
        return convertView;
    }
     
    static class ViewHolder{
    	ImageView leftImg;
    	ImageView rightImg;
        TextView treeText;
        TextView treeText1;
    }
}
