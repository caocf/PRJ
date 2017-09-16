package net.hxkg.ship;



import java.util.List;

import net.hxkg.ghmanager.R;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PopShipNameAdapter extends BaseAdapter implements OnClickListener {

	private Context context;
	private List<String> shipNameList;
	private int type;

	public PopShipNameAdapter(Context context, List<String> shipNameList,
			int type) {
		this.context = context;
		this.shipNameList = shipNameList;
		this.type = type;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return shipNameList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return shipNameList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		holder = new ViewHolder();

		switch (type) {
		case 1:

			convertView = LinearLayout.inflate(context,
					R.layout.pop_ship_name_item, null);

			holder.ship_item_name = (TextView) convertView
					.findViewById(R.id.item_name);

			String name = shipNameList.get(position);
			holder.ship_item_name.setText(name);

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					DangersLetInActivity.instance.ship_name
							.setText(holder.ship_item_name.getText().toString());
					DangersLetInActivity.instance.popupWindowArea.dismiss();
					DangersLetInActivity.instance.ship_name_lists.clear();
				}
			});

			break;

		case 2:

			convertView = LinearLayout.inflate(context,
					R.layout.pop_ship_name_item, null);

			holder.ship_item_name = (TextView) convertView
					.findViewById(R.id.item_name);

			String goods_type = shipNameList.get(position);
			holder.ship_item_name.setText(goods_type);

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					DangersLetInActivity.instance.goods
							.setText(holder.ship_item_name.getText().toString());
					DangersLetInActivity.instance.popupWindowArea.dismiss();
					DangersLetInActivity.instance.goodsname_lists.clear();
				}
			});

			break;

		case 3:

			convertView = LinearLayout.inflate(context,
					R.layout.pop_ship_name_item, null);

			holder.ship_item_name = (TextView) convertView
					.findViewById(R.id.item_name);

			String goods_units = shipNameList.get(position);
			holder.ship_item_name.setText(goods_units);

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					DangersLetInActivity.instance.unit
							.setText(holder.ship_item_name.getText().toString());
					DangersLetInActivity.instance.popupWindowArea.dismiss();
					DangersLetInActivity.instance.unit_lists.clear();
				}
			});

			break;

		case 4:

			convertView = LinearLayout.inflate(context,
					R.layout.pop_ship_name_item, null);

			holder.ship_item_name = (TextView) convertView
					.findViewById(R.id.item_name);

			String dangers_good = shipNameList.get(position);
			holder.ship_item_name.setText(dangers_good);

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					DangersLetInActivity.instance.rank
							.setText(holder.ship_item_name.getText().toString());
					DangersLetInActivity.instance.popupWindowArea.dismiss();
					DangersLetInActivity.instance.dangersgoodstype_lists
							.clear();
				}
			});

			break;

		case 5:

			convertView = LinearLayout.inflate(context,
					R.layout.pop_ship_name_item, null);

			holder.ship_item_name = (TextView) convertView
					.findViewById(R.id.item_name);

			String from = shipNameList.get(position);
			holder.ship_item_name.setText(from);

			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (DangersLetInActivity.instance.poptpye == 6) {
						DangersLetInActivity.instance.from
								.setText(holder.ship_item_name.getText()
										.toString());
					} else if (DangersLetInActivity.instance.poptpye == 7) {
						DangersLetInActivity.instance.to
								.setText(holder.ship_item_name.getText()
										.toString());
					}
					DangersLetInActivity.instance.popupWindowArea.dismiss();
					DangersLetInActivity.instance.from_lists.clear();
				}
			});

			break;

		case 6:

			convertView = LinearLayout.inflate(context,
					R.layout.pop_ship_name_item, null);
			
			holder.ship_item_name = (TextView) convertView
					.findViewById(R.id.item_name);

			String froms = shipNameList.get(position);
			holder.ship_item_name.setText(froms);
			
			break;
		}

		return convertView;
	}

	class ViewHolder {
		TextView ship_item_name;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
