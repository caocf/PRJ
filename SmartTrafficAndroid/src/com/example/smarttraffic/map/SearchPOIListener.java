package com.example.smarttraffic.map;

import cennavi.cenmapsdk.android.search.CNMKPoiResult;

public class SearchPOIListener implements SearchListener
{
	@Override
	public void searchListener(Object oResult, int id,boolean error, String errorContent)
	{
		CNMKPoiResult result=(CNMKPoiResult)oResult;
		
		if ( error || oResult == null || null==result.getPois()) 
		{
			return;
		}	

		try 
		{
			
		}
		catch ( Exception e )
		{
		}
	}
}
