package com.example.smarttraffic.smartBus.fragment;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.smarttraffic.ChangeHead;
import com.example.smarttraffic.R;
import com.example.smarttraffic.common.localDB.FavorDBOperate;
import com.example.smarttraffic.favor.TwoItemsAdapter;
import com.example.smarttraffic.favor.TwoItemsData;
import com.example.smarttraffic.network.BaseRequest;
import com.example.smarttraffic.network.BaseSearch;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.HttpUrlRequestAddress;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.smartBus.SelectPointActivity;
import com.example.smarttraffic.smartBus.SmartBusLineDetailActivity;
import com.example.smarttraffic.smartBus.SmartBusStationDetailActivity;
import com.example.smarttraffic.smartBus.SmartBusTransferListActivity;
import com.example.smarttraffic.smartBus.adapter.StationAdapter;
import com.example.smarttraffic.smartBus.adapter.LineAdapter;
import com.example.smarttraffic.smartBus.bean.LineInfo;
import com.example.smarttraffic.smartBus.bean.StationInfo;
import com.example.smarttraffic.user.Message;
import com.example.smarttraffic.user.UserControl;
import com.example.smarttraffic.util.StartIntent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SmartBusFavorFragment extends Fragment
{
	int type;
	boolean delete;

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public boolean isDelete()
	{
		return delete;
	}

	public void setDelete(boolean delete)
	{
		this.delete = delete;
	}

	ChangeHead changeHead;

	public void setChangeHead(ChangeHead changeHead)
	{
		this.changeHead = changeHead;
	}

	ImageView deleteImageView;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.fragment_smart_bus_favor,
				container, false);

		listView = (ListView) root.findViewById(R.id.smart_bus_favor_list);

		deleteImageView = (ImageView) root
				.findViewById(R.id.smart_bus_favor_delete);

		if (delete)
		{
			deleteImageView.setVisibility(View.VISIBLE);
			deleteImageView.setOnClickListener(new View.OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					if (type == 0)
					{
						deleteTransfer();
						// FavorDBOperate dbOperate = new FavorDBOperate(
						// SmartBusFavorFragment.this.getActivity());
						// for (TwoItemsData t : transferAdapter.getData())
						// {
						// if (t.isSelect())
						// dbOperate.delete(t.getId());
						// }
						// dbOperate.CloseDB();
						//transferAdapter.update();

					} else if (type == 1)
					{
						deleteLine();

						// FavorDBOperate dbOperate = new FavorDBOperate(
						// SmartBusFavorFragment.this.getActivity());
						// for (LineInfo l : lineAdapter.getData())
						// {
						// if (l.isSelect())
						// dbOperate.delete(l.getFavorID());
						// }
						// dbOperate.CloseDB();
						//lineAdapter.update();
					} else if (type == 2)
					{
						deleteStation();

						// FavorDBOperate dbOperate = new FavorDBOperate(
						// SmartBusFavorFragment.this.getActivity());
						// for (StationInfo s : favorAdapter.getData())
						// {
						// if (s.isSelect())
						// dbOperate.delete(s.getFavorID());
						// }
						// dbOperate.CloseDB();
						//favorAdapter.update();
					}
					changeHead.changeHead(0);
					//initList(type, delete);
				}
			});
		}

		return root;
	}

	private void deleteLine()
	{
		String lines = "";

		for (LineInfo l : lineAdapter.getData())
		{
			if (l.isSelect())
			{
				if (lines.equals(""))
					lines += l.getFavorID();
				else
					lines += "," + l.getFavorID();
			}
		}

		final String finalLine = lines;

		if(finalLine.equals(""))
		{
			Toast.makeText(getActivity(),
					"请选择删除项",
					Toast.LENGTH_SHORT).show();
			return;
		}
		
		System.out.println(finalLine);

		new HttpThread(new BaseSearch()
		{
			@Override
			public Object parse(String data)
			{
				return JSON.parseObject(
						JSON.parseObject(data).getJSONObject("message")
								.toJSONString(), Message.class);
			}
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{

				return HttpUrlRequestAddress.FAVOR_DELETE_LINE_URL + "?userid="
						+ UserControl.getUser().getUserid() + "&lineIds="
						+ finalLine;
			}
		}, new UpdateView()
		{

			@Override
			public void update(Object data)
			{
				Message message = (Message) data;

				Toast.makeText(getActivity(),
						message.getStatus() == 1 ? "删除成功" : "删除失败",
						Toast.LENGTH_SHORT).show();
				initList(type, delete);
			}
		}, "删除收藏线路失败").start();
	}

	private void deleteStation()
	{
		String stations = "";

		for (StationInfo l : favorAdapter.getData())
		{
			if (l.isSelect())
			{
				if (stations.equals(""))
					stations += l.getFavorID();
				else
					stations += "," + l.getFavorID();
			}
		}

		final String finalStations = stations;
		
		if(finalStations.equals(""))
		{
			Toast.makeText(getActivity(),
					"请选择删除项",
					Toast.LENGTH_SHORT).show();
			return;
		}

		System.out.println(finalStations);

		new HttpThread(new BaseSearch()
		{
			@Override
			public Object parse(String data)
			{
				return JSON.parseObject(
						JSON.parseObject(data).getJSONObject("message")
								.toJSONString(), Message.class);
			}
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{

				return HttpUrlRequestAddress.FAVOR_DELETE_STATION_URL
						+ "?userid=" + UserControl.getUser().getUserid()
						+ "&stationIds=" + finalStations;
			}
		}, new UpdateView()
		{

			@Override
			public void update(Object data)
			{
				Message message = (Message) data;

				Toast.makeText(getActivity(),
						message.getStatus() == 1 ? "删除成功" : "删除失败",
						Toast.LENGTH_SHORT).show();
				initList(type, delete);
			}
		}, "删除收藏线路失败").start();
	}

	private void deleteTransfer()
	{
		String transfer = "";

		for (TwoItemsData l : transferAdapter.getData())
		{
			if (l.isSelect())
			{
				if (transfer.equals(""))
					transfer += l.getId();
				else
					transfer += "," + l.getId();
			}
		}

		final String finalTransfer = transfer;

		if(finalTransfer.equals(""))
		{
			Toast.makeText(getActivity(),
					"请选择删除项",
					Toast.LENGTH_SHORT).show();
			return;
		}
		
		System.out.println(finalTransfer);

		new HttpThread(new BaseSearch()
		{
			@Override
			public Object parse(String data)
			{
				return JSON.parseObject(
						JSON.parseObject(data).getJSONObject("message")
								.toJSONString(), Message.class);
			}
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{

				return HttpUrlRequestAddress.FAVOR_DELETE_TRANSFER_URL
						+ "?userid=" + UserControl.getUser().getUserid()
						+ "&transferIds=" + finalTransfer;
			}
		}, new UpdateView()
		{

			@Override
			public void update(Object data)
			{
				Message message = (Message) data;

				Toast.makeText(getActivity(),
						message.getStatus() == 1 ? "删除成功" : "删除失败",
						Toast.LENGTH_SHORT).show();
				initList(type, delete);
			}
		}, "删除收藏线路失败").start();
	}

	ListView listView;
	TwoItemsAdapter transferAdapter;
	LineAdapter lineAdapter;
	StationAdapter favorAdapter;

	public void selectAll(int type, boolean isSelect)
	{
		switch (type)
		{
		case 0:
			transferAdapter.selectALL(isSelect);
			if (changeHead != null)
				changeHead.changeHead(transferAdapter.countSelect());
			break;

		case 1:
			lineAdapter.selectALL(isSelect);
			if (changeHead != null)
				changeHead.changeHead(lineAdapter.countSelect());
			break;

		case 2:
			favorAdapter.selectALL(isSelect);
			if (changeHead != null)
				changeHead.changeHead(favorAdapter.countSelect());
			break;
		}
	}

	private void loadLine()
	{
		if (UserControl.getUser() == null)
		{
			Toast.makeText(getActivity(), "您尚未登录", Toast.LENGTH_SHORT).show();
			return;
		}

		new HttpThread(new BaseSearch()
		{
			public Object parse(String data)
			{
				System.out.println(data);

				JSONObject object = JSON.parseObject(data);

				Message message = JSON.parseObject(
						object.getJSONObject("message").toJSONString(),
						Message.class);

				if (message.getStatus() == 1)
				{
					List<LineInfo> infos = new ArrayList<LineInfo>();

					JSONArray array = object.getJSONArray("result");

					for (int i = 0; i < array.size(); i++)
					{
						JSONObject jsonObject = array.getJSONObject(i);

						LineInfo temp = new LineInfo();
						temp.setEnd(jsonObject.getString("endname"));
						temp.setId(jsonObject.getIntValue("lineID"));
						temp.setFavorID(jsonObject.getIntValue("id"));
						temp.setName(jsonObject.getString("linename"));
						temp.setStart(jsonObject.getString("startname"));
						temp.setDirect(jsonObject.getIntValue("direct"));
						temp.setUserid(jsonObject.getIntValue("userID"));

						infos.add(temp);
					}

					return infos;
				}

				return message;
			}
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{
				return HttpUrlRequestAddress.FAVOR_FIND_LINE_URL + "?userid="
						+ UserControl.getUser().getUserid();
			}
		}, new UpdateView()
		{

			@SuppressWarnings("unchecked")
			@Override
			public void update(Object data)
			{
				if (data instanceof Message)
				{
					Toast.makeText(getActivity(), "查找收藏记录失败",
							Toast.LENGTH_SHORT).show();
					return;
				} else if (data instanceof List<?>)
				{
					List<LineInfo> result = (List<LineInfo>) data;

					lineAdapter = new LineAdapter(getActivity(), result, delete);
					listView.setAdapter(lineAdapter);

					if (!delete)
						listView.setOnItemClickListener(new OnItemClickListener()
						{

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id)
							{
								Bundle bundle = new Bundle();
								bundle.putInt(
										SmartBusLineDetailActivity.SMART_BUS_LINE_ID,
										((LineAdapter) parent.getAdapter())
												.getData().get(position)
												.getId());
								bundle.putInt(
										SmartBusLineDetailActivity.SMART_BUS_LINE_DIRECT,
										((LineAdapter) parent.getAdapter())
												.getData().get(position)
												.getDirect());
								
								bundle.putString(SmartBusLineDetailActivity.SMART_BUS_LINE_NAME, ((LineAdapter) parent.getAdapter())
												.getData().get(position).getName());

								StartIntent.startIntentWithParam(
										SmartBusFavorFragment.this
												.getActivity(),
										SmartBusLineDetailActivity.class,
										bundle);
							}
						});
					else
						listView.setOnItemClickListener(new OnItemClickListener()
						{

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id)
							{
								lineAdapter.changeCheck(position);
								if (changeHead != null)
									changeHead.changeHead(lineAdapter
											.countSelect());
							}
						});
				}

			}
		}, "查找收藏线路失败").start();
	}

	private void loadStation()
	{
		if (UserControl.getUser() == null)
		{
			Toast.makeText(getActivity(), "您尚未登录", Toast.LENGTH_SHORT).show();
			return;
		}

		new HttpThread(new BaseSearch()
		{
			public Object parse(String data)
			{
				System.out.println(data);

				JSONObject object = JSON.parseObject(data);

				Message message = JSON.parseObject(
						object.getJSONObject("message").toJSONString(),
						Message.class);

				if (message.getStatus() == 1)
				{
					List<StationInfo> infos = new ArrayList<StationInfo>();

					JSONArray array = object.getJSONArray("result");

					for (int i = 0; i < array.size(); i++)
					{
						JSONObject jsonObject = array.getJSONObject(i);

						StationInfo temp = new StationInfo();
						temp.setFavorID(jsonObject.getIntValue("id"));
						temp.setId(jsonObject.getIntValue("stationID"));
						temp.setName(jsonObject.getString("stationName"));
						try
						{
							temp.setLines(jsonObject.getString("lines").split(
									","));
						} catch (Exception e)
						{
						}
						infos.add(temp);
					}

					return infos;
				}

				return message;
			}
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{
				return HttpUrlRequestAddress.FAVOR_FIND_STATION_URL
						+ "?userid=" + UserControl.getUser().getUserid();
			}
		}, new UpdateView()
		{

			@SuppressWarnings("unchecked")
			@Override
			public void update(Object data)
			{
				if (data instanceof Message)
				{
					Toast.makeText(getActivity(), "查找收藏记录失败",
							Toast.LENGTH_SHORT).show();
					return;
				} else if (data instanceof List<?>)
				{

					favorAdapter = new StationAdapter(getActivity(),
							(List<StationInfo>) data, delete);
					listView.setAdapter(favorAdapter);
					if (!delete)
						listView.setOnItemClickListener(new OnItemClickListener()
						{

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id)
							{
								Bundle bundle = new Bundle();
								bundle.putInt(
										SmartBusStationDetailActivity.SMART_BUS_STATTON_ID,
										((StationAdapter) parent.getAdapter())
												.getData().get(position)
												.getId());
								StartIntent.startIntentWithParam(
										SmartBusFavorFragment.this
												.getActivity(),
										SmartBusStationDetailActivity.class,
										bundle);
							}
						});
					else
						listView.setOnItemClickListener(new OnItemClickListener()
						{

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id)
							{
								favorAdapter.changeCheck(position);
								if (changeHead != null)
									changeHead.changeHead(favorAdapter
											.countSelect());
							}
						});
				}

			}
		}, "查找收藏站点失败").start();

	}

	private void loadTransfer()
	{
		if (UserControl.getUser() == null)
		{
			Toast.makeText(getActivity(), "您尚未登录", Toast.LENGTH_SHORT).show();
			return;
		}

		new HttpThread(new BaseSearch()
		{
			public Object parse(String data)
			{
				System.out.println(data);

				JSONObject object = JSON.parseObject(data);

				Message message = JSON.parseObject(
						object.getJSONObject("message").toJSONString(),
						Message.class);

				if (message.getStatus() == 1)
				{
					List<TwoItemsData> infos = new ArrayList<TwoItemsData>();

					JSONArray array = object.getJSONArray("result");

					for (int i = 0; i < array.size(); i++)
					{
						JSONObject jsonObject = array.getJSONObject(i);

						TwoItemsData temp = new TwoItemsData();
						temp.setId(jsonObject.getIntValue("id"));
						temp.setEnd(jsonObject.getString("endName"));
						temp.setLan1((int) (1e6 * jsonObject
								.getDoubleValue("startLantitude")));
						temp.setLan2((int) (1e6 * jsonObject
								.getDoubleValue("endLantitude")));
						temp.setLon1((int) (1e6 * jsonObject
								.getDoubleValue("startLongtitude")));
						temp.setLon2((int) (1e6 * jsonObject
								.getDoubleValue("endLongtitude")));
						temp.setStart(jsonObject.getString("startName"));

						infos.add(temp);
					}

					return infos;
				}

				return message;
			}
		}, new BaseRequest()
		{
			@Override
			public String CreateUrl()
			{
				return HttpUrlRequestAddress.FAVOR_FIND_TRANSFER_URL
						+ "?userid=" + UserControl.getUser().getUserid();
			}
		}, new UpdateView()
		{

			@SuppressWarnings("unchecked")
			@Override
			public void update(Object data)
			{
				if (data instanceof Message)
				{
					Toast.makeText(getActivity(), "查找收藏记录失败",
							Toast.LENGTH_SHORT).show();
					return;
				} else if (data instanceof List<?>)
				{
					transferAdapter = new TwoItemsAdapter(getActivity(),
							(List<TwoItemsData>) data, delete);
					listView.setAdapter(transferAdapter);

					if (!delete)
						listView.setOnItemClickListener(new OnItemClickListener()
						{

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id)
							{
								TwoItemsData temp = transferAdapter.getData()
										.get(position);
								Bundle bundle = new Bundle();
								bundle.putString(
										SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_NAME,
										temp.getStart());
								bundle.putString(
										SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_NAME,
										temp.getEnd());
								StartIntent.startIntentWithParam(
										SmartBusFavorFragment.this
												.getActivity(),
												SelectPointActivity.class,
										bundle);
							}
						});
					else
						listView.setOnItemClickListener(new OnItemClickListener()
						{

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id)
							{
								transferAdapter.changeCheck(position);
								if (changeHead != null)
									changeHead.changeHead(transferAdapter
											.countSelect());
							}
						});
				}

			}
		}, "查找收藏换乘失败").start();
	}

	private void initList(int type, boolean isDelete)
	{
		// int[] contentType = new int[] { ContentType.SMART_BUS_TRANSFER_FAVOR,
		// ContentType.SMART_BUS_LINE_FAVOR,
		// ContentType.SMART_BUS_STATION_FAVOR };
		// FavorDBOperate dbOperate = new FavorDBOperate(getActivity());

		if (type == 0)
		{
			loadTransfer();

			// transferAdapter = new TwoItemsAdapter(getActivity(),
			// dbOperate.selectForTwoItem(contentType[type]), delete);
			// listView.setAdapter(transferAdapter);
			//
			// if (!delete)
			// listView.setOnItemClickListener(new OnItemClickListener()
			// {
			//
			// @Override
			// public void onItemClick(AdapterView<?> parent, View view,
			// int position, long id)
			// {
			// TwoItemsData temp = transferAdapter.getData().get(
			// position);
			// Bundle bundle = new Bundle();
			// bundle.putString(
			// SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_NAME,
			// temp.getStart());
			// bundle.putString(
			// SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_NAME,
			// temp.getEnd());
			//
			// bundle.putDouble(
			// SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_GEO_lAN,
			// ((double) temp.getLan1()) / 1e6);
			// bundle.putDouble(
			// SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_START_GEO_lON,
			// ((double) temp.getLon1()) / 1e6);
			//
			// bundle.putDouble(
			// SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_GEO_LAN,
			// ((double) temp.getLan2()) / 1e6);
			// bundle.putDouble(
			// SmartBusTransferListActivity.SMART_BUS_TRANSFER_LIST_END_GEO_LON,
			// ((double) temp.getLon2()) / 1e6);
			//
			// StartIntent.startIntentWithParam(
			// SmartBusFavorFragment.this.getActivity(),
			// SmartBusTransferListActivity.class, bundle);
			// }
			// });
			// else
			// listView.setOnItemClickListener(new OnItemClickListener()
			// {
			//
			// @Override
			// public void onItemClick(AdapterView<?> parent, View view,
			// int position, long id)
			// {
			// transferAdapter.changeCheck(position);
			// if (changeHead != null)
			// changeHead.changeHead(transferAdapter.countSelect());
			// }
			// });
		} else if (type == 1)
		{
			loadLine();

			// lineAdapter = new LineAdapter(getActivity(),
			// dbOperate.selectForLineInfo(contentType[type]), delete);
			// listView.setAdapter(lineAdapter);
			//
			// if (!delete)
			// listView.setOnItemClickListener(new OnItemClickListener()
			// {
			//
			// @Override
			// public void onItemClick(AdapterView<?> parent, View view,
			// int position, long id)
			// {
			// Bundle bundle = new Bundle();
			// bundle.putInt(
			// SmartBusLineDetailActivity.SMART_BUS_LINE_ID,
			// ((LineAdapter) parent.getAdapter()).getData()
			// .get(position).getId());
			// StartIntent.startIntentWithParam(
			// SmartBusFavorFragment.this.getActivity(),
			// SmartBusLineDetailActivity.class, bundle);
			// }
			// });
			// else
			// listView.setOnItemClickListener(new OnItemClickListener()
			// {
			//
			// @Override
			// public void onItemClick(AdapterView<?> parent, View view,
			// int position, long id)
			// {
			// lineAdapter.changeCheck(position);
			// if (changeHead != null)
			// changeHead.changeHead(lineAdapter.countSelect());
			// }
			// });
		} else if (type == 2)
		{
			loadStation();
			// favorAdapter = new StationAdapter(getActivity(),
			// dbOperate.selectForStationInfo(contentType[type]), delete);
			// listView.setAdapter(favorAdapter);
			// if (!delete)
			// listView.setOnItemClickListener(new OnItemClickListener()
			// {
			//
			// @Override
			// public void onItemClick(AdapterView<?> parent, View view,
			// int position, long id)
			// {
			// Bundle bundle = new Bundle();
			// bundle.putInt(
			// SmartBusStationDetailActivity.SMART_BUS_STATTON_ID,
			// ((StationAdapter) parent.getAdapter())
			// .getData().get(position).getId());
			// StartIntent.startIntentWithParam(
			// SmartBusFavorFragment.this.getActivity(),
			// SmartBusStationDetailActivity.class, bundle);
			// }
			// });
			// else
			// listView.setOnItemClickListener(new OnItemClickListener()
			// {
			//
			// @Override
			// public void onItemClick(AdapterView<?> parent, View view,
			// int position, long id)
			// {
			// favorAdapter.changeCheck(position);
			// if (changeHead != null)
			// changeHead.changeHead(favorAdapter.countSelect());
			// }
			// });
		}

		// dbOperate.CloseDB();
	}

	@Override
	public void onResume()
	{
		initList(type, delete);
		super.onResume();
	}

}
