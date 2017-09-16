package com.huzhouport.addresslist;

import android.app.Activity;


public class AddresslistView extends Activity {/*
	private MySQLiteHelper dbHelper;
	private ProgressDialog progressDialog;
	*//** ����ͬ��֪ͨ����UI�߳�,��Ҫ���� ����ͬ��,֪ͨUI�̸߳��½��� *//*
	Handler SynHandler = new Handler() {
		public void handleMessage(Message msg) {
			progressDialog.dismiss();// �رս�����
		}
	};

	private ClearEditText searchtext; // ������
	private TextView tel;

	private List<Map<String, Object>> parentList;
	private ArrayList<ArrayList<HashMap<String, Object>>> allchildList;
	private Query query = new Query();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.addresslist_view);

		//
		SharedPreferences preferences = getSharedPreferences("count",
				MODE_WORLD_READABLE);
		int count = preferences.getInt("count", 0);
		System.out.println("count==" + count);

		// �жϳ�����ڼ������У�����ǵ�һ����������ת��ϵͳ����ͬ��
		if (count == 0) {

			progressDialog = new ProgressDialog(this);// ����һ��������
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setTitle("���Ե�");
			progressDialog.setMessage("����ͬ��ͨѶ¼����!");
			// System.out.println("����ͬ��ͨѶ¼����");
			progressDialog.show();
			Thread SynThread = new Thread(new SynDataHandler());
			SynThread.start();

		}
		Editor editor = preferences.edit();
		// ��������
		editor.putInt("count", ++count);
		// �ύ�޸�
		editor.commit();

		System.out.println("aaaa111");
		parentList = new ArrayList<Map<String, Object>>();
		allchildList = new ArrayList<ArrayList<HashMap<String, Object>>>();

		Intent intent = getIntent();
		String addressname = intent.getStringExtra("name");
		System.out.println("addressname=" + addressname);
		if (addressname == null) {

		} else {
			findViewById(R.id.addresslist_titeltable1).setVisibility(
					View.VISIBLE);
			ClearEditText c = (ClearEditText) findViewById(R.id.addresslist_searchtext);
			c.setText(addressname);
		}

		// ��Ա��ȡ
		try {
			GetUserListByDepartment(addressname);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExpandableListView expandlistview = (ExpandableListView) findViewById(R.id.addresslist_viewlist);
		MyAdapter adapter = new MyAdapter(this);
	    expandlistview.setAdapter(adapter);
        expandlistview.setGroupIndicator(null);
		
		expandlistview.setOnChildClickListener(new OnChildClickListener() {
		     
		    public boolean onChildClick(ExpandableListView parent, View v,
		            int groupPosition, int childPosition, long id) {
		    	Toast.makeText(AddresslistView.this, "���Child: " + ((TextView)v).getText(), Toast.LENGTH_SHORT).show();
		        return false;
		    }
		});

		
		// ����
		ImageButton back = (ImageButton) findViewById(R.id.addresslist_back);
		back.setOnClickListener(new Back());
		// ������ͼ��
		ImageButton search = (ImageButton) findViewById(R.id.addresslist_search);
		search.setOnClickListener(new Search());
		// ȡ��
		Button quxiao = (Button) findViewById(R.id.addresslist_search1);
		quxiao.setOnClickListener(new Quxiao());

		// �����̬ ��ʾȡ�� ������
		searchtext = (ClearEditText) findViewById(R.id.addresslist_searchtext);
		// searchtext.setOnClickListener(new Searchtext());
		searchtext.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String text = searchtext.getText().toString();
				System.out.println("searchtext==" + text);
				if (text == null) {
					// System.out.println("searchtext====2");
				}
				if (text.length() == 0) {
					// System.out.println("searchtext====1");

					findViewById(R.id.addresslist_search1).setVisibility(
							View.VISIBLE);
					findViewById(R.id.addresslist_search2).setVisibility(
							View.GONE);
				} else {
					findViewById(R.id.addresslist_search2).setVisibility(
							View.VISIBLE);
					findViewById(R.id.addresslist_search1).setVisibility(
							View.GONE);
				}
			}

		});

		// ������ť
		Button search1 = (Button) findViewById(R.id.addresslist_search2);
		search1.setOnClickListener(new SearchButton());

	}

	public void GetUserListByDepartment(String addressname) throws Exception {
		String result;
		if (addressname == null) {
			result = query.AllUserAndDepartment();
		} else {
			result = query.AllUserAndDepartmentTel(addressname);
		}
		// System.out.println("resultuser:  " + result);
		JSONTokener jsonParser_User = new JSONTokener(result);
		// System.out.println("jsonParser_User:  " + jsonParser_User);
		JSONObject data = (JSONObject) jsonParser_User.nextValue();
		// System.out.println("data:  " + data);
		JSONArray jsonArray = data.getJSONArray("list");
		System.out.println("jsonArray:  " + jsonArray);
		System.out.println("jsonArray.length()" + jsonArray.length());
		ArrayList<HashMap<String, Object>> childlist = null;

		if (jsonArray.length() == 0) {
			Toast.makeText(AddresslistView.this, R.string.addresslist_nofind,
					Toast.LENGTH_LONG).show();
		}
		{

			int dId = 0; // ����id

			int k = 0; // �ж��Ƿ���Ӳ��� 0���1�����
			int j = 0; // ����
			String total = null; // ��������
			for (int i = 0; i < jsonArray.length(); i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				// if(i == jsonArray.length()){
				// map.put("parent",total+"("+j+")");
				// parentList.add(map);
				// }else{
				JSONArray jsonArray2 = (JSONArray) jsonArray.getJSONArray(i);
				JSONObject jsonObject_Deaprtment = (JSONObject) jsonArray2
						.opt(0);
				JSONObject jsonObject_User = (JSONObject) jsonArray2.opt(1);
				// System.out.println("jsonObject_Deaprtment"+jsonObject_Deaprtment);
				// System.out.println("jsonObject_User"+jsonObject_User);
				HashMap<String, Object> childmap = new HashMap<String, Object>();
				// System.out.println("i:  "
				// + jsonObject_Deaprtment.getString("departmentName"));

				if (i == 0) {
					childlist = new ArrayList<HashMap<String, Object>>();
					dId = Integer.parseInt(jsonObject_Deaprtment
							.getString("departmentId"));

					// map.put("parent",jsonObject_Deaprtment.getString("departmentName"));
					// parentList.add(map);

					// userNameList[i] = jsonObject_User.getString("name");
					// userTelList[i] = jsonObject_User.getString("tel");
					childmap.put("child_tel", jsonObject_User.getString("tel"));
					childmap.put("child_name",
							jsonObject_User.getString("name"));
					childmap.put("child_userId",
							jsonObject_User.getString("userId"));
					childlist.add(childmap);
					System.out.println("childlist" + childlist);

					total = jsonObject_Deaprtment.getString("departmentName");
					j = 1;
				} else {
					if (Integer.parseInt(jsonObject_Deaprtment
							.getString("departmentId")) == dId) {
						// userNameList[i] = jsonObject_User.getString("name");
						// userTelList[i] = jsonObject_User.getString("tel");
						childmap.put("child_tel",
								jsonObject_User.getString("tel"));
						childmap.put("child_name",
								jsonObject_User.getString("name"));
						childmap.put("child_userId",
								jsonObject_User.getString("userId"));
						childlist.add(childmap);

						total = jsonObject_Deaprtment
								.getString("departmentName");
						j++;

					} else {
						// total+"("+j+")";
						// System.out.println("i+1+total=="+i+1+".."+total);
						map.put("parent", total + "(" + j + ")");
						parentList.add(map);
						j = 1;

						total = jsonObject_Deaprtment
								.getString("departmentName");
						// System.out.println("i+total"+i+".."+total);
						allchildList.add(childlist);
						childlist = new ArrayList<HashMap<String, Object>>();
						dId = Integer.parseInt(jsonObject_Deaprtment
								.getString("departmentId"));
						// map.put("parent",
						// jsonObject_Deaprtment.getString("departmentName"));
						// parentList.add(map);
						// userNameList[i] = jsonObject_User.getString("name");
						// userTelList[i] = jsonObject_User.getString("tel");
						childmap.put("child_tel",
								jsonObject_User.getString("tel"));
						childmap.put("child_name",
								jsonObject_User.getString("name"));
						childmap.put("child_userId",
								jsonObject_User.getString("userId"));
						childlist.add(childmap);

					}
				}
				// System.out.println("parentList"+i+"  "+parentList);
				if (i == (jsonArray.length() - 1)) {
					// System.out.println("total=="+total+j);
					map = new HashMap<String, Object>();
					map.put("parent", total + "(" + j + ")");
					parentList.add(map);
					allchildList.add(childlist);
				}

				// System.out.println("parentList"+i+"  "+parentList);
			}
			// }
			System.out.println("parentList" + parentList);
			System.out.println("allchildList" + allchildList);
		}
	}

	class MyAdapter extends BaseExpandableListAdapter {
		Context context;
		LayoutInflater mlayoutInflater;

		MyAdapter(Context context) {
			this.context = context;
			mlayoutInflater = LayoutInflater.from(context);
		}

		@Override
		// ȡ����ָ�����顢ָ������Ŀ����������
		public Object getChild(int groupPosition, int childPosition) {

			return allchildList.get(groupPosition).get(childPosition);
		}

		@Override
		// ȡ�ø��������и�������ͼ��ID�� ����ID������������Ψһ�ġ�
		public long getChildId(int groupPosition, int childPosition) {

			return childPosition;
		}

		@Override
		// ȡ����ʾ�������������λ�õ������õ���ͼ��
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			convertView = mlayoutInflater.inflate(R.layout.addresslist_item,
					null);

			TextView textView1 = (TextView) convertView
					.findViewById(R.id.addresslist_name);
			TextView textView2 = (TextView) convertView
					.findViewById(R.id.addresslist_tel);
			TextView textView3 = (TextView) convertView
					.findViewById(R.id.addresslist_id);
			textView1.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_name").toString());
			textView2.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_tel").toString());
			textView3.setText(allchildList.get(groupPosition)
					.get(childPosition).get("child_userId").toString());

			// ��绰
			// final TextView
			// textViewname=(TextView)convertView.findViewById(R.id.addresslist_name);
			final ImageButton imgButton = (ImageButton) convertView
					.findViewById(R.id.addresslist_tellphone);
			final String name=textView1.getText().toString();
			final String tel = textView2.getText().toString();// tel
			final String id = textView3.getText().toString();// id
			// ��绰
			imgButton.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {

					String address = tel;
					// System.out.println("address"+tel);
					Intent intent = new Intent(); // ����һ����ͼ
					intent.setAction("android.intent.action.CALL");
					// intent.addCategory("android.intent.category.DEFAULT");
					intent.setData(Uri.parse("tel:" + address));
					startActivity(intent); // �����ڲ����Զ�Ϊintent������
											// ��android.intent.category.DEFAULT".

				}
			});
			
			textView1.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					Intent intent = new Intent(AddresslistView.this,
						AddresslistShowID.class);
					intent.putExtra("id",id);
					intent.putExtra(
							"name",name);
					startActivity(intent);
					

				}
			}); 
			textView2.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
			
					Intent intent = new Intent(AddresslistView.this,
						AddresslistShowID.class);
					intent.putExtra("id",id);
					intent.putExtra(
							"name",name);
					startActivity(intent);
					

				}
			}); 
			
			
			

			return convertView;
		}

		@Override
		// ȡ��ָ���������Ԫ����
		public int getChildrenCount(int groupPosition) {

			return allchildList.get(groupPosition).size();
		}

		@Override
		// ȡ�������������������ݡ�
		public Object getGroup(int groupPosition) {

			return allchildList.get(groupPosition);
		}

		@Override
		// ȡ�÷�������
		public int getGroupCount() {

			return allchildList.size();
		}

		@Override
		// ȡ��ָ�������ID������ID������������Ψһ��
		public long getGroupId(int groupPosition) {

			return groupPosition;
		}

		@Override
		// ȡ��������ʾ�����������ͼ
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			convertView = mlayoutInflater.inflate(R.layout.addresslist_tree,
					null);

			TextView textParent = (TextView) convertView
					.findViewById(R.id.addresslist_tree);

			textParent.setText(parentList.get(groupPosition).get("parent")
					.toString());

			return convertView;
		}

		@Override
		// �Ƿ�ָ��������ͼ��������ͼ��ID��Ӧ�ĺ�̨���ݸı�Ҳ�ᱣ�ָ�ID��
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		// ָ��λ�õ�����ͼ�Ƿ��ѡ�� //����һ��
		public boolean isChildSelectable(int groupPosition, int childPosition) {
//			System.out.println("123456789");
//
//			Intent intent = new Intent(AddresslistView.this,
//					AddresslistShowID.class);
//			intent.putExtra(
//					"id",
//					allchildList.get(groupPosition).get(childPosition)
//							.get("child_userId").toString());
//			intent.putExtra(
//					"name",
//					allchildList.get(groupPosition).get(childPosition)
//							.get("child_name").toString());
//			startActivity(intent);
			
			return false;
		}
	}

	public class ButtonTel1 implements View.OnClickListener {
		public void onClick(View v) {
			String address = tel.getText().toString();
			System.out.println("address" + address);
		}
	}

	public class Back implements View.OnClickListener {
		public void onClick(View v) {
//			Intent intent = new Intent(AddresslistView.this, OfficOA.class);
//			startActivity(intent);
			finish();
		}
	}

	public class Search implements View.OnClickListener {
		public void onClick(View v) {
			// findViewById(R.id.addresslist_titeltable).setVisibility(View.GONE);
			findViewById(R.id.addresslist_titeltable1).setVisibility(
					View.VISIBLE);
		}
	}

	public class Quxiao implements View.OnClickListener {
		public void onClick(View v) {
			// findViewById(R.id.addresslist_titeltable).setVisibility(View.GONE);
			findViewById(R.id.addresslist_titeltable1).setVisibility(View.GONE);
		}
	}

	public class SearchButton implements View.OnClickListener {
		public void onClick(View v) {
			Intent intent = new Intent(AddresslistView.this,
					AddresslistView.class);
			intent.putExtra("name", searchtext.getText().toString());
			startActivity(intent);
			
		}
	}

	class SynDataHandler implements Runnable {
		@Override
		public void run() {

			SynData();

			Message message = new Message();
			Bundle bundle = new Bundle();
			// bundle.putBoolean("isNetError", isNetError);
			message.setData(bundle);
			SynHandler.sendMessage(message);

		}

	}

	public void SynData() { // ��������
		try {
			System.out.println("��������");
			String result = query.AllUserAndDepartment();
			JSONTokener jsonParser_User = new JSONTokener(result);
			// System.out.println("jsonParser_User:  " + jsonParser_User);
			JSONObject data = (JSONObject) jsonParser_User.nextValue();
			// System.out.println("data:  " + data);
			JSONArray jsonArray = data.getJSONArray("list");
			System.out.println("jsonArray:  " + jsonArray);
			System.out.println("jsonArray.length()" + jsonArray.length());

			// ����д�����ݿ�
			dbHelper = new MySQLiteHelper(this, "my.db", null, 2);
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			db.execSQL("delete  from  mm_user");

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONArray jsonArray2 = (JSONArray) jsonArray.getJSONArray(i);
				JSONObject jsonObject_Deaprtment = (JSONObject) jsonArray2
						.opt(0);
				JSONObject jsonObject_User = (JSONObject) jsonArray2.opt(1);
				String userId = jsonObject_User.getString("userId");
				String name = jsonObject_User.getString("name");
				String tel = jsonObject_User.getString("tel");
				String departmentName = jsonObject_Deaprtment
						.getString("departmentName");
				System.out.println("sql==" + userId + " " + name + " "
						+ departmentName);
				db.beginTransaction();
				db.execSQL("insert into mm_user values(NULL" + ", '" + userId
						+ "','" + name + "','" + tel + "','" + departmentName
						+ "')");
				db.setTransactionSuccessful();
				db.endTransaction();
			}

		} catch (Exception e) {
			Toast.makeText(this, "����ʧ��", 2000);// ���ֻ�����ʾ��ʾToast��2��
			Log.i("addresslistView", e.toString());// DDMS����ʾ��ʾ

		}
	}

*/}
