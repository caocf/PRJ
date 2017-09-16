package com.huzhouport.addresslist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;
import com.huzhouport.common.util.Query;

import android.app.Activity;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 联系人分章节显示、ListView快速滑动显示联系人首字母、附带字母表快速查找的例子
 * 查阅网络资源，实现方式都是比较复杂，尤其有些还实现了SectionIndex接口，很多人不怎么能理解，研究后发现此种类型的例子没必要也不应该那么实现
 * @author hiphonezhu@sina.com
 * 
 */
public class AddresslistMain extends Activity {
	//数据
	private Query query = new Query();
	
	//页面
	private BaseAdapter adapter;
	private ListView personList;
	private List<ContentValues> list;

	private QuickAlphabeticBar alpha;
	private static final String NAME = "name", NUMBER = "number",
			SORT_KEY = "sort_key";


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addresslist_main1);
		personList = (ListView) findViewById(R.id.addresslist_listView);
		alpha = (QuickAlphabeticBar) findViewById(R.id.addresslist_fast_scroller);

		//asyncQuery = new MyAsyncQueryHandler(getContentResolver());
		
		// 人员
					try {
						GetUserListByDepartment();
					} catch (Exception e) {
						e.printStackTrace();
					}
		
	}

	
//	protected void onResume() {
//		super.onResume();
//		Uri uri = Uri.parse("content://com.android.contacts/data/phones"); // 联系人的Uri
//		String[] projection = { "_id", "display_name", "data1", "sort_key" }; // 查询的列
//		asyncQuery.startQuery(0, null, uri, projection, null, null,
//				"sort_key COLLATE LOCALIZED asc"); // 按照sort_key升序查询
//	}

	/**
	 * 数据库异步查询类AsyncQueryHandler
	 * 
	 * @author administrator
	 * 
	 */
	private class MyAsyncQueryHandler extends AsyncQueryHandler {

		public MyAsyncQueryHandler(ContentResolver cr) {
			super(cr);

		}

		/**
		 * 查询结束的回调函数
		 */
		@Override
		protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
			if (cursor != null && cursor.getCount() > 0) {
				list = new ArrayList<ContentValues>();
				cursor.moveToFirst();
				for (int i = 0; i < cursor.getCount(); i++) {
					ContentValues cv = new ContentValues();
					cursor.moveToPosition(i);
					String name = cursor.getString(1);
					String number = cursor.getString(2);
					String sortKey = cursor.getString(3);

					if (number.startsWith("+86")) {// 去除多余的中国地区号码标志，对这个程序没有影响。
						cv.put(NAME, name);
						cv.put(NUMBER, number.substring(3));
						cv.put(SORT_KEY, sortKey);
					} else {
						cv.put(NAME, name);
						cv.put(NUMBER, number);
						cv.put(SORT_KEY, sortKey);
					}
					list.add(cv);
				}
				if (list.size() > 0) {
					setAdapter(list);
				}
			}
		}

	}

	private void setAdapter(List<ContentValues> list) {
		System.out.println("list===="+list);
		adapter = new ListAdapter(this, list);
		personList.setAdapter(adapter);
		alpha.init(AddresslistMain.this);
		alpha.setListView(personList);
		alpha.setHight(alpha.getHeight());
		alpha.setVisibility(View.VISIBLE);
	}

	private static class ViewHolder {
		TextView alpha;
		TextView name;
		TextView number;
	}

	/*
	 * 移植时只需要提供一个已排序的集合list即可
	 */
	private class ListAdapter extends BaseAdapter{
		private LayoutInflater inflater;
		private List<ContentValues> list;
		private HashMap<String, Integer> alphaIndexer;//保存每个索引在list中的位置【#-0，A-4，B-10】
		private String[] sections;//每个分组的索引表【A,B,C,F...】

		public ListAdapter(Context context, List<ContentValues> list) {
			this.inflater = LayoutInflater.from(context);
			this.list = list; // 该list是已经排序过的集合，有些项目中的数据必须要自己进行排序。
			System.out.println("list==="+list);
			  ComparatorUser comparator=new ComparatorUser();
			  Collections.sort(list, comparator);
			
			
			
			System.out.println("list1==="+list);
			this.alphaIndexer = new HashMap<String, Integer>();
			this.sections = new String[list.size()];

			for (int i =0; i <list.size(); i++) {
				String name = getAlpha(list.get(i).getAsString(SORT_KEY));
				System.out.println("name===="+name);
				if(!alphaIndexer.containsKey(name)){//只记录在list中首次出现的位置
					System.out.println("111");
					alphaIndexer.put(name, i);
				}
			}
			Set<String> sectionLetters = alphaIndexer.keySet();
			ArrayList<String> sectionList = new ArrayList<String>(
					sectionLetters);
			Collections.sort(sectionList);
			sections = new String[sectionList.size()];
			sectionList.toArray(sections);
			
			alpha.setAlphaIndexer(alphaIndexer);
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;

			if (convertView == null) {
				convertView = inflater.inflate(R.layout.addresslist_item1, null);
				holder = new ViewHolder();
				holder.alpha = (TextView) convertView.findViewById(R.id.addresslist_alpha);
				holder.name = (TextView) convertView.findViewById(R.id.addresslist_name1);
				holder.number = (TextView) convertView
						.findViewById(R.id.addresslist_number);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			ContentValues cv = list.get(position);
			String name = cv.getAsString(NAME);
			String number = cv.getAsString(NUMBER);
			holder.name.setText(name);
			holder.number.setText(number);

			// 当前联系人的sortKey
			String currentStr = getAlpha(list.get(position).getAsString(
					SORT_KEY));
			// 上一个联系人的sortKey
			String previewStr = (position - 1) >= 0 ? getAlpha(list.get(
					position - 1).getAsString(SORT_KEY)) : " ";
			/**
			 * 判断显示#、A-Z的TextView隐藏与可见
			 */
			if (!previewStr.equals(currentStr)) { // 当前联系人的sortKey！=上一个联系人的sortKey，说明当前联系人是新组。
				holder.alpha.setVisibility(View.VISIBLE);
				holder.alpha.setText(currentStr);
			} else {
				holder.alpha.setVisibility(View.GONE);
			}
			return convertView;
}
}

    public void GetUserListByDepartment() throws Exception {
		list = new ArrayList<ContentValues>();
		String result = query.AllUserAndDepartment();
		//System.out.println("resultuser:  " + result);
		JSONTokener jsonParser_User = new JSONTokener(result);
		//System.out.println("jsonParser_User:  " + jsonParser_User);
		JSONObject data = (JSONObject) jsonParser_User.nextValue();
		//System.out.println("data:  " + data);
		JSONArray jsonArray = data.getJSONArray("list");
		System.out.println("jsonArray:  " + jsonArray);
		System.out.println("jsonArray.length()"+jsonArray.length());
		//ArrayList<HashMap<String, Object>> childlist = null;
		//userNameList = new String[jsonArray.length()];
	   //userTelList = new String[jsonArray.length()];
	   
	    
	  
		for (int i = 0; i < jsonArray.length(); i++) {
			//HashMap<String, Object> map = new HashMap<String, Object>();

			JSONArray jsonArray2 = (JSONArray) jsonArray.getJSONArray(i);
			JSONObject jsonObject_Deaprtment = (JSONObject) jsonArray2.opt(0);
			JSONObject jsonObject_User = (JSONObject) jsonArray2.opt(1);
            //System.out.println("jsonObject_Deaprtment"+jsonObject_Deaprtment);
            //System.out.println("jsonObject_User"+jsonObject_User);
			//HashMap<String, Object> childmap = new HashMap<String, Object>();
			System.out.println("i:  "
					+ jsonObject_Deaprtment.getString("departmentName"));
			ContentValues cv = new ContentValues();
			cv.put("number",jsonObject_User.getString("tel"));
			cv.put("sort_key", String2Alpha(jsonObject_User.getString("name")));
			cv.put("name", jsonObject_User.getString("name"));
			list.add(cv);
			//setAdapter(list);
			}
		setAdapter(list);
		System.out.println("list======="+list);
		}


private char[] chartable =
         {
             '啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈', '哈',
             '击', '喀', '垃', '妈', '拿', '哦', '啪', '期', '然',
             '撒', '塌', '塌', '塌', '挖', '昔', '压', '匝', '座'
         };
 private char[] alphatable =
         {
             'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
             'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
         };

 private int[] table = new int[27];
 //初始化
 {
     for (int i = 0; i < 27; ++i) {
         table[i] = gbValue(chartable[i]);
     }
 }

 //主函数,输入字符,得到他的声母,
 //英文字母返回对应的大写字母
 //其他非简体汉字返回 '0'
 public char Char2Alpha(char ch) {
     if (ch >= 'a' && ch <= 'z')
         return (char) (ch - 'a' + 'A');
     if (ch >= 'A' && ch <= 'Z')
         return ch;

     int gb = gbValue(ch);
     if (gb < table[0])
         return '0';

     int i;
     for (i = 0; i < 26; ++i) {
         if (match(i, gb))
             break;
     }
     if (i >= 26)
         return '0';
     else
         return alphatable[i];
 }
 //根据一个包含汉字的字符串返回一个汉字拼音首字母的字符串
 public String String2Alpha(String SourceStr) {
     String Result = "";
     int StrLength = SourceStr.length();
     int i;
     try {
         for (i = 0; i < StrLength; i++) {
             Result += Char2Alpha(SourceStr.charAt(i));
         }
     } catch (Exception e) {
         Result = "";
     }
     return Result;
 }
 private boolean match(int i, int gb) {
     if (gb < table[i])
         return false;
     int j = i + 1;
     //字母Z使用了两个标签
     while (j < 26 && (table[j] == table[i]))
         ++j;
     if (j == 26)
         return gb <= table[j];
     else
         return gb < table[j];
 }
 //取出汉字的编码
 private int gbValue(char ch) {
     String str = new String();
     str += ch;
     try {
         byte[] bytes = str.getBytes("GB2312");
         if (bytes.length < 2)
             return 0;
         return (bytes[0] << 8 & 0xff00) + (bytes[1] &
                 0xff);
     } catch (Exception e) {
         return 0;
     }
 }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	/**
	 * 提取英文的首字母，非英文字母用#代替。
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		if (str == null) {
			return "#";
		}

		if (str.trim().length() == 0) {
			return "#";
		}

		char c = str.trim().substring(0, 1).charAt(0);
		// 正则表达式，判断首字母是否是英文字母
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		if (pattern.matcher(c + "").matches()) {
			return (c + "").toUpperCase(); // 大写输出
		} else {
			return "#";
		}
	}
}