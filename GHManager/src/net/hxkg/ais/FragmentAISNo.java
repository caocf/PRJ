package net.hxkg.ais;
 

import net.hxkg.ghmanager.R;
import android.app.Fragment; 
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;   
import android.widget.TextView;

public class FragmentAISNo extends Fragment 
{ 	 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View rootView = inflater.inflate(R.layout.fragment_aisno,container,false);
		 
		
		AISCheckActivity ins=(AISCheckActivity) (this.getActivity());
		 
		
		return rootView;
	}
	 	
}
