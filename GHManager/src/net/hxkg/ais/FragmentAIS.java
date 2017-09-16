package net.hxkg.ais;
 

import net.hxkg.ghmanager.R;
import android.app.Fragment; 
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;   
import android.widget.TextView;

public class FragmentAIS extends Fragment 
{ 	
	TextView ais,shipname;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.fragment_ais,container,false);
		ais=(TextView) rootView.findViewById(R.id.ais);
		shipname=(TextView) rootView.findViewById(R.id.shipname);
		
		AISCheckActivity ins=(AISCheckActivity) (this.getActivity());
		ais.setText(ins.ais);
		shipname.setText(ins.shipname);
		
		return rootView;
	}
	 	
}
