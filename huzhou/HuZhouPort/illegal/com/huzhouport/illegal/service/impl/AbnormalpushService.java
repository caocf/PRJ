package com.huzhouport.illegal.service.impl;
import java.io.IOException;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.muc.MultiUserChat;

public class AbnormalpushService
{
	public static ConnectionConfiguration config;
	public static XMPPConnection connection;
	public static MultiUserChat muc;
	
	public AbnormalpushService()
	{
		 config =new ConnectionConfiguration("120.55.100.184",5222,"120.55.100.184");
	     config.setReconnectionAllowed(true);
	     config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
	     connection = new XMPPTCPConnection(config);
	     try 
	     {
			connection.connect();
			connection.login("倪立","123456");
			muc=new MultiUserChat(connection,"hzgh@conference.120.55.100.184");
	        muc.join("admin");
		} catch (SmackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        //
	        //
		//ystem.out.println("11");
	}
	
	public void  pushAbnormal(String content)
	{
		 try {
			muc.sendMessage(content);
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(abinfo);
	}
}
