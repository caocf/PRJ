package com.huzhouport.slidemenu;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.huzhouport.R;
import com.huzhouport.ais.AISMain;
import com.huzhouport.integratedquery.ShipSearch;
import com.huzhouport.integratedquery.ShipSearchNinecode;
import com.huzhouport.main.User;
import com.huzhouport.wharfwork.WharfWorkList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class IntegratedQueryFragment extends Fragment
{
	GridView			_gridView1;
	ImageButton			titleButton;
	private User		user;
	private Integer[]	images	= { // �Ź���ͼƬ������
								R.drawable.mm_shipbaseinfor,
			R.drawable.mm_shipvisainfor, R.drawable.mm_shipillegalinfor,
			R.drawable.mm_shipvisainfor, R.drawable.mm_shippayment,
			R.drawable.mm_shipsurvey, R.drawable.mm_wharfjob,
			R.drawable.mm_waterageenterprise,R.drawable.ninecode,/*R.drawable.mm_signinandsignout*/};
	private String[]	texts	= { // �Ź���ͼƬ�·����ֵ�����
								"����������Ϣ", "����֤����Ϣ", "����Υ����Ϣ", "����ǩ֤��Ϣ",
			"�����ɷ���Ϣ", "����������Ϣ", "��ͷ��ҵ��Ϣ", "������ҵ��Ϣ","��λ��˲�",/*"������"*/ };

	public IntegratedQueryFragment()
	{
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{

		View rootView = inflater.inflate(R.layout.activity_main, container,
				false);
		_gridView1 = (GridView) rootView.findViewById(R.id.gridView1);

		ArrayList<HashMap<String, Object>> lst = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 9; i++)
		{
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", images[i]);
			map.put("itemText", texts[i]);

			lst.add(map);
		}

		SimpleAdapter adpter = new SimpleAdapter(getActivity(), lst,
				R.layout.menulist, new String[] { "itemImage", "itemText" },
				new int[] { R.id.imageView_ItemImage, R.id.textView_ItemText });

		_gridView1.setAdapter(adpter);
		_gridView1.setOnItemClickListener(new gridView1OnClickListener());
		Bundle bundle = getArguments();
		if (bundle != null)
		{
			user = (User) bundle.getSerializable("User");
		}

		return rootView;
	}

	class gridView1OnClickListener implements OnItemClickListener
	{

		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3)
		{

			if (arg2 == 8)
			{
				/*Toast.makeText(getActivity(), "�ýӿ�δ��ͨ", Toast.LENGTH_SHORT)
						.show();*/
				Intent intent = new Intent(getActivity(), ShipSearchNinecode.class);
				intent.putExtra("searchType", "0");
				intent.putExtra("User", user);
				startActivity(intent);
			}
			else if(arg2==9)
			{
				Intent intent = new Intent(getActivity(), AISMain.class);
				intent.putExtra("User", user);
				startActivity(intent);
			}
			else if (arg2 == 6)
			{
				Intent intent = new Intent(getActivity(), WharfWorkList.class);
				intent.putExtra("User", user);
				startActivity(intent);
			}
			else
			{
				Intent intent = null;
				String searchType = String.valueOf(arg2);
				intent = new Intent(getActivity(), ShipSearch.class);
				intent.putExtra("searchType", searchType);
				intent.putExtra("User", user);
				startActivity(intent);	
			}
/*			else if (CheckPermission(arg2 + 1))
			{
				Intent intent = null;
				String searchType = String.valueOf(arg2);
				intent = new Intent(getActivity(), ShipSearch.class);
				intent.putExtra("searchType", searchType);
				startActivity(intent);
			}
			else
			{
				Toast.makeText(getActivity(), "����Ȩ�޲鿴" + texts[arg2],
						Toast.LENGTH_SHORT).show();
			}*/
		}
	}

	public Boolean CheckPermission(int pID)
	{
		if (pID == 1)
		{
			for (int i = 0; i < user.getRpList().size(); i++)
			{
				if (user.getRpList().get(i).getPermissionId() == 25)
				{
					if (user.getRpList().get(i).getStatus() == 1)
						return true;
					else
						return false;
				}
			}

		}
		else if (pID == 2)
		{
			for (int i = 0; i < user.getRpList().size(); i++)
			{
				if (user.getRpList().get(i).getPermissionId() == 26)
				{
					if (user.getRpList().get(i).getStatus() == 1)
						return true;
					else
						return false;
				}
			}

		}
		else if (pID == 3)
		{
			for (int i = 0; i < user.getRpList().size(); i++)
			{
				if (user.getRpList().get(i).getPermissionId() == 27)
				{
					if (user.getRpList().get(i).getStatus() == 1)
						return true;
					else
						return false;
				}
			}

		}
		else if (pID == 4)
		{
			for (int i = 0; i < user.getRpList().size(); i++)
			{
				if (user.getRpList().get(i).getPermissionId() == 28)
				{
					if (user.getRpList().get(i).getStatus() == 1)
						return true;
					else
						return false;
				}
			}

		}
		else if (pID == 5)
		{
			for (int i = 0; i < user.getRpList().size(); i++)
			{
				if (user.getRpList().get(i).getPermissionId() == 29)
				{
					if (user.getRpList().get(i).getStatus() == 1)
						return true;
					else
						return false;
				}
			}

		}
		else if (pID == 6)
		{
			for (int i = 0; i < user.getRpList().size(); i++)
			{
				if (user.getRpList().get(i).getPermissionId() == 30)
				{
					if (user.getRpList().get(i).getStatus() == 1)
						return true;
					else
						return false;
				}
			}

		}
		else if (pID == 8)
		{
			for (int i = 0; i < user.getRpList().size(); i++)
			{
				if (user.getRpList().get(i).getPermissionId() == 31)
				{
					if (user.getRpList().get(i).getStatus() == 1)
						return true;
					else
						return false;
				}
			}

		}
		return true;
	}

}
