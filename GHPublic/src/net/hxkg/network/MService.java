package net.hxkg.network;
import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.drafts.Draft_17;

import com.hztuen.gh.contact.contants;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MService extends Service{	
	public static final String TAG = MService.class.getSimpleName();
	
	Client client=null;
	@Override  
    public void onCreate() 
	{  
        super.onCreate();
        URI uri=null;
		try {
			uri = new URI(contants.pubWSURL);
		} catch (URISyntaxException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(uri!=null)		
        {
			client=new Client(uri,new Draft_17(),this);
			Log.i(TAG, "MService___________client");
			//
			try {
				client.connectBlocking();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) 
	{	 
		 super.onStartCommand(intent, Service.START_REDELIVER_INTENT, startId);
		 return START_REDELIVER_INTENT;
	}
	
	 
	
	@Override
	 public void onDestroy() 
	 {
		if(client!=null)
			client.close();
	 }
	
	@Override
	public IBinder onBind(Intent intent) 
	{
		return null;
	}

}
