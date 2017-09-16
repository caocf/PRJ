package net.hxkg.crew;
import java.util.List;
import net.hxkg.ghmanager.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CrewSearchAdapter extends BaseAdapter
{
	private final static String TAG = CrewSearchAdapter.class.getSimpleName();
	private Context context;
	private List<CrewBaseEN> cne;
	public CrewSearchAdapter(Context context,List<CrewBaseEN> cne){
		this.context = context;
		this.cne = cne;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cne.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return cne.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.companysearch_list_item, null);
		}
		TextView companyName = (TextView) convertView.findViewById(R.id.companyName);
		companyName.setText(cne.get(position).getName());
		return convertView;
	}

}
