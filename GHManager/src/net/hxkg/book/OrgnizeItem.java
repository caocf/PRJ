package net.hxkg.book;

public class OrgnizeItem extends TreeNode
{
	public  String idString;
	public  String zzjgmc;
	public  String zzjglb;
	public boolean hasChild=true;	
	
	public int getType()
	{
		return Integer.valueOf(zzjglb);
	}
	
	public String telString;
	public String bm;
	public String dw;
}
