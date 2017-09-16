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
 * ��ϵ�˷��½���ʾ��ListView���ٻ�����ʾ��ϵ������ĸ��������ĸ����ٲ��ҵ�����
 * ����������Դ��ʵ�ַ�ʽ���ǱȽϸ��ӣ�������Щ��ʵ����SectionIndex�ӿڣ��ܶ��˲���ô����⣬�о����ִ������͵�����û��ҪҲ��Ӧ����ôʵ��
 * @author hiphonezhu@sina.com
 * 
 */
public class AddresslistMain extends Activity {
	//����
	private Query query = new Query();
	
	//ҳ��
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
		
		// ��Ա
					try {
						GetUserListByDepartment();
					} catch (Exception e) {
						e.printStackTrace();
					}
		
	}

	
//	protected void onResume() {
//		super.onResume();
//		Uri uri = Uri.parse("content://com.android.contacts/data/phones"); // ��ϵ�˵�Uri
//		String[] projection = { "_id", "display_name", "data1", "sort_key" }; // ��ѯ����
//		asyncQuery.startQuery(0, null, uri, projection, null, null,
//				"sort_key COLLATE LOCALIZED asc"); // ����sort_key�����ѯ
//	}

	/**
	 * ���ݿ��첽��ѯ��AsyncQueryHandler
	 * 
	 * @author administrator
	 * 
	 */
	private class MyAsyncQueryHandler extends AsyncQueryHandler {

		public MyAsyncQueryHandler(ContentResolver cr) {
			super(cr);

		}

		/**
		 * ��ѯ�����Ļص�����
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

					if (number.startsWith("+86")) {// ȥ��������й����������־�����������û��Ӱ�졣
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
	 * ��ֲʱֻ��Ҫ�ṩһ��������ļ���list����
	 */
	private class ListAdapter extends BaseAdapter{
		private LayoutInflater inflater;
		private List<ContentValues> list;
		private HashMap<String, Integer> alphaIndexer;//����ÿ��������list�е�λ�á�#-0��A-4��B-10��
		private String[] sections;//ÿ�������������A,B,C,F...��

		public ListAdapter(Context context, List<ContentValues> list) {
			this.inflater = LayoutInflater.from(context);
			this.list = list; // ��list���Ѿ�������ļ��ϣ���Щ��Ŀ�е����ݱ���Ҫ�Լ���������
			System.out.println("list==="+list);
			  ComparatorUser comparator=new ComparatorUser();
			  Collections.sort(list, comparator);
			
			
			
			System.out.println("list1==="+list);
			this.alphaIndexer = new HashMap<String, Integer>();
			this.sections = new String[list.size()];

			for (int i =0; i <list.size(); i++) {
				String name = getAlpha(list.get(i).getAsString(SORT_KEY));
				System.out.println("name===="+name);
				if(!alphaIndexer.containsKey(name)){//ֻ��¼��list���״γ��ֵ�λ��
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

			// ��ǰ��ϵ�˵�sortKey
			String currentStr = getAlpha(list.get(position).getAsString(
					SORT_KEY));
			// ��һ����ϵ�˵�sortKey
			String previewStr = (position - 1) >= 0 ? getAlpha(list.get(
					position - 1).getAsString(SORT_KEY)) : " ";
			/**
			 * �ж���ʾ#��A-Z��TextView������ɼ�
			 */
			if (!previewStr.equals(currentStr)) { // ��ǰ��ϵ�˵�sortKey��=��һ����ϵ�˵�sortKey��˵����ǰ��ϵ�������顣
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
             '��', '��', '��', '��', '��', '��', '��', '��', '��',
             '��', '��', '��', '��', '��', 'Ŷ', 'ž', '��', 'Ȼ',
             '��', '��', '��', '��', '��', '��', 'ѹ', '��', '��'
         };
 private char[] alphatable =
         {
             'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
             'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
         };

 private int[] table = new int[27];
 //��ʼ��
 {
     for (int i = 0; i < 27; ++i) {
         table[i] = gbValue(chartable[i]);
     }
 }

 //������,�����ַ�,�õ�������ĸ,
 //Ӣ����ĸ���ض�Ӧ�Ĵ�д��ĸ
 //�����Ǽ��庺�ַ��� '0'
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
 //����һ���������ֵ��ַ�������һ������ƴ������ĸ���ַ���
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
     //��ĸZʹ����������ǩ
     while (j < 26 && (table[j] == table[i]))
         ++j;
     if (j == 26)
         return gb <= table[j];
     else
         return gb < table[j];
 }
 //ȡ�����ֵı���
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
	 * ��ȡӢ�ĵ�����ĸ����Ӣ����ĸ��#���档
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
		// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		if (pattern.matcher(c + "").matches()) {
			return (c + "").toUpperCase(); // ��д���
		} else {
			return "#";
		}
	}
}