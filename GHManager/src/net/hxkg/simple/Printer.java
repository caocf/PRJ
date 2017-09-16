package net.hxkg.simple;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.Charset;

public class Printer 
{
	 public Socket socket;

	    public int POS_OPEN_NETPORT = 9100;// 0X238c
	    public boolean IFOpen = false;
	    public int Net_SendTimeout = 1000;
	    public int Net_ReceiveTimeout = 1500;
	    public String command = ""; //打印命令字符串
	    public byte[] outbytes; //传输的命令集

	    PrinterCMD pcmd = new PrinterCMD();
	
	 public boolean Open(String ipaddress, int netport)
	    {
	        if (socket == null)
	        {
	            try
	            {
	                SocketAddress ipe = new InetSocketAddress(ipaddress,netport);
	                socket = new Socket();  //Socket(ipaddress, netport,true);
	                socket.connect(ipe);
	                socket.setSoTimeout(Net_ReceiveTimeout);
	                //socket.SendTimeout = Net_SendTimeout;
	                IFOpen = true;
	                //System.out.print("连接成功");
	            }
	            catch(Exception e)
	            {
	                //MessageBox.Show("连接不成功");
	                e.printStackTrace();
	                IFOpen = false;
	            }
	        }
	        else
	        {
	            try
	            {
	                socket.close(); 
	                SocketAddress ipe = new InetSocketAddress(ipaddress,netport);
	                socket = new Socket();  //Socket(ipaddress, netport,true);
	                socket.connect(ipe);
	                socket.setSoTimeout(Net_ReceiveTimeout);
	                //socket.SendTimeout = Net_SendTimeout;
	                IFOpen = true;
	            }
	            catch(Exception e)
	            {
	                e.printStackTrace();
	                IFOpen = false;
	            }
	        }
	        return IFOpen;
	    }
	 
	  public void Close()
	    {
	        try
	        {
	            socket.close();
	            socket = null;
	        }
	        catch(Exception e)
	        {
	            e.printStackTrace();
	        }
	    }
	  
	  public void Set()
	    {
	        try
	        {
	            command = pcmd.CMD_SetPos();
	            OutputStream stream = socket.getOutputStream();
	            outbytes =  command.getBytes(Charset.forName("ASCII")); 
	            stream.write(outbytes);
	        }
	        catch (IOException e)
	        {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	    }
	  
	  public void PrintText(String pszString, int nFontAlign, int nFontSize,int ifzhenda)
	    {
	        try
	        {
	            OutputStream stream = socket.getOutputStream();
	            command = pcmd.CMD_TextAlign(nFontAlign);            
	            outbytes =  command.getBytes(Charset.forName("ASCII")); 
	            stream.write(outbytes);

	            if (ifzhenda == 1)
	            {
	                command = pcmd.CMD_FontSize_BTP_M280(nFontSize);
	                outbytes =  command.getBytes(Charset.forName("ASCII")); 
	                stream.write(outbytes);

	                command = pcmd.CMD_FontSize_BTP_M2801(nFontSize);
	                outbytes =  command.getBytes(Charset.forName("ASCII")); 
	                stream.write(outbytes);
	            }
	            else
	            {
	                command = pcmd.CMD_FontSize(nFontSize);
	                outbytes =  command.getBytes(Charset.forName("ASCII")); 
	                stream.write(outbytes);
	            }

	            command = pszString;// +CMD_Enter();
	            outbytes =  command.getBytes(Charset.forName("GB2312")); //Charset.defaultCharset()); //forName("UTF-8")
	            stream.write(outbytes);
	        }
	        catch (IOException e)
	        {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            return;
	        }        

	    }
}
