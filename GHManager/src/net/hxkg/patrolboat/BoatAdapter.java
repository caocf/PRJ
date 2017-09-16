package net.hxkg.patrolboat;

import java.util.ArrayList;

import net.hxkg.book.TreeNode;
import net.hxkg.ghmanager.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BoatAdapter extends BaseAdapter {
    /** 所有的数据集合 */
    private ArrayList<TreeNode> allNodes;
    /** 顶层元素结合*/
    private ArrayList<TreeNode> topNodes;
    /** LayoutInflater */
    private LayoutInflater inflater;
    /** item的行首缩进基数 */
    private int indentionBase;
     
    public BoatAdapter(ArrayList<TreeNode> topNodes, ArrayList<TreeNode> allNodes, LayoutInflater inflater) {
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
        
        TreeNode element = topNodes.get(position);
        int level =element.getLevel();
        convertView.setPadding(
                indentionBase * (level + 1),
                holder.leftImg.getPaddingTop(),
                holder.leftImg.getPaddingRight(),
                holder.leftImg.getPaddingBottom());
        
        holder.treeText.setText(element.getContentText()+"("+element.getMember()+"艘)");
        
        if (element.isHasChildren() && !element.isExpanded()) 
        {
            holder.rightImg.setImageResource(R.drawable.icon_right);
            
        } else if (element.isHasChildren() && element.isExpanded())
        {
            holder.rightImg.setImageResource(R.drawable.ic_dropdown);
        } else if (!element.isHasChildren()) 
        {
        	holder.treeText.setText(element.getContentText());
        	holder.rightImg.setVisibility(View.GONE);
            //holder.treeText1.setText("139554585");
            //holder.treeText1.setVisibility(View.VISIBLE);
            holder.leftImg.setVisibility(View.GONE);
        }
        return convertView;
    }
     
    static class ViewHolder{
    	ImageView leftImg;
    	ImageView rightImg;
        TextView treeText;
        TextView treeText1;
    }
}
