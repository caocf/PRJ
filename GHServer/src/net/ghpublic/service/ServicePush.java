package net.ghpublic.service;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Admin on 2016/4/25.
 */
@Service("servicePush")
public class ServicePush//implements SmackInitializer
{
    private static ConnectionConfiguration config;
    private static XMPPConnection connection;
    private static MultiUserChat muc;

    @PostConstruct
    public void inite( ) throws Exception
    {
        config =new ConnectionConfiguration("192.168.1.104",5222,"192.168.1.104");
        config.setReconnectionAllowed(true);
        config.setSecurityMode(ConnectionConfiguration.SecurityMode.disabled);
        connection = new XMPPTCPConnection(config);
        //connection.connect();
        //connection.login("admin","123456");
        //muc=new MultiUserChat(connection,"public@conference.192.168.1.104");
        //muc.join("admin");
    }


    public void test(String msg)
    {
        try
        {
            /*ChatManager chatManager=ChatManager.getInstanceFor(connection);
            //connection.
            Chat chat=chatManager.createChat("test1@192.168.1.104",null );
            chat.sendMessage("Howdy!");*/

            muc.sendMessage(msg);

        }
        catch (Exception e)
        {
        }
    }

}
