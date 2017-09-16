package net.hxkg.network;
import java.net.URI;
import java.net.URISyntaxException;
import org.java_websocket.drafts.Draft_17;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MService extends Service
{	
	Client client=null;
	@Override  
    public void onCreate() 
	{  
        super.onCreate();
        
        
        
        URI uri=null;
		try {
			uri = new URI(Constants.WSURL);
		} catch (URISyntaxException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(uri!=null)		
        {
			client=new Client(uri,new Draft_17(),this);
			///
			try {
				client.connect();
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
		// TODO Auto-generated method stub
		return null;
	}

}
