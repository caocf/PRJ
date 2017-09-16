package com.huzhouport.integratedquery;

/*@author 沈丹丹
 * */
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import com.example.huzhouport.R;
import com.huzhouport.common.util.HttpFileUpTool;
import com.huzhouport.common.util.HttpUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BS_CBJBXX extends Fragment
{
	private TextView	content01 , content02 , content03 , content04 ,
			content05 , content06 , content07 , content08 , content09;
	private TextView	content10 , content11 , content12 , content13 ,
			content14 , content15 , content16 , content17 , content18;
	private TextView	content19 , content20 , content21;
	private String		getResult , title;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getResult = getArguments().getString("getResult");
		title = getArguments().getString("title");

	}

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,
			Bundle savedInstanceState)
	{
		// TODO 自动生成的方法存根
		View view = inflater.inflate(R.layout.bs_cbjbxx, container, false);
		content01 = (TextView) view.findViewById(R.id.bs_cbjbxx_txt012);
		content02 = (TextView) view.findViewById(R.id.bs_cbjbxx_txt022);
		content03 = (TextView) view.findViewById(R.id.bs_cbjbxx_txt032);
		content04 = (TextView) view.findViewById(R.id.bs_cbjbxx_txt042);
		content05 = (TextView) view.findViewById(R.id.bs_cbjbxx_txt052);
		content06 = (TextView) view.findViewById(R.id.bs_cbjbxx_txt062);
		content07 = (TextView) view.findViewById(R.id.bs_cbjbxx_txt072);
		content08 = (TextView) view.findViewById(R.id.bs_cbjbxx_txt082);
		content09 = (TextView) view.findViewById(R.id.bs_cbjbxx_txt092);
		content10 = (TextView) view.findViewById(R.id.bs_cbjbxx_txt102);
		content11 = (TextView) view.findViewById(R.id.bs_cbjbxx_txt112);
		content12 = (TextView) view.findViewById(R.id.bs_cbjbxx_txt122);
		content13 = (TextView) view.findViewById(R.id.bs_cbjbxx_txt132);
		content14 = (TextView) view.findViewById(R.id.bs_cbjbxx_txt142);
		content15 = (TextView) view.findViewById(R.id.bs_cbjbxx_txt152);
		content16 = (TextView) view.findViewById(R.id.bs_cbjbxx_txt162);
		content17 = (TextView) view.findViewById(R.id.bs_cbjbxx_txt172);
		content18 = (TextView) view.findViewById(R.id.bs_cbjbxx_txt182);
		content19 = (TextView) view.findViewById(R.id.bs_cbjbxx_txt192);
		content20 = (TextView) view.findViewById(R.id.bs_cbjbxx_txt202);
		content21 = (TextView) view.findViewById(R.id.bs_cbjbxx_txt212);
		if (getResult.length() == 0)
		{
			new GetDate().execute();
		}
		else
		{
			iniDate();
		}

		return view;
	}

	static BS_CBJBXX newInstance(String getResult,String title)
	{
		BS_CBJBXX newFragment = new BS_CBJBXX();
		Bundle obundle = new Bundle();
		obundle.putString("getResult", getResult);
		obundle.putString("title", title);
		newFragment.setArguments(obundle);
		return newFragment;

	}

	class GetDate extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params)
		{
			Map<String, Object> paramsDate = new HashMap<String, Object>();
			paramsDate.put("cbname", title);
			paramsDate.put("method", 1);
			HttpFileUpTool hfu = new HttpFileUpTool();
			String actionUrl = HttpUtil.BASE_URL + "GetAndPostDate";
			try
			{
				 getResult =hfu.post(actionUrl, paramsDate);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			iniDate();
			super.onPostExecute(result);
		}

	}

	private void iniDate()
	{
		try
		{
			JSONTokener jsonParser = new JSONTokener(getResult);
			JSONObject data = (JSONObject) jsonParser.nextValue();
			// 接下来的就是JSON对象的操作了
			JSONArray jsonArray = data.getJSONArray("list");
			JSONObject jsonObject2 = (JSONObject) jsonArray.opt(0);
			content01.setText(jsonObject2.getString("ZWCM"));
			content02.setText(jsonObject2.getString("CJDJH"));
			content03.setText(jsonObject2.getString("CBDJH"));
			content04.setText(jsonObject2.getString("CJG"));
			content05.setText(jsonObject2.getString("CJGDM"));
			content06.setText(jsonObject2.getString("CBLX"));
			content07.setText(jsonObject2.getString("CBLXDM"));
			content08.setText(jsonObject2.getString("ZJZGL"));
			content09.setText(jsonObject2.getString("ZDW"));
			content10.setText(jsonObject2.getString("JDW"));
			content11.setText(jsonObject2.getString("ZC"));
			content12.setText(jsonObject2.getString("CC"));
			content13.setText(jsonObject2.getString("LDJBC"));
			content14.setText(jsonObject2.getString("XK"));
			content15.setText(jsonObject2.getString("XS"));
			content16.setText(jsonObject2.getString("CKZHL"));
			content17.setText(jsonObject2.getString("CSKZ"));
			content18.setText(jsonObject2.getString("CSMZ"));
			content19.setText(jsonObject2.getString("SYR"));
			content20.setText(jsonObject2.getString("JYR"));
			content21.setText(jsonObject2.getString("JYRDH"));

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
