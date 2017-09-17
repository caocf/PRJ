package com.sts.common.util;

import org.codehaus.xfire.MessageContext;
import org.jdom.Element;
import org.codehaus.xfire.handler.AbstractHandler;

public class ClientAuthenticationHandler extends AbstractHandler {
	private String username = null; 

    private String password = null; 

    public ClientAuthenticationHandler() { 

    } 

    public ClientAuthenticationHandler(String username,String password) { 

        this.username = username; 

        this.password = password; 
    } 

    public void setUsername(String username) { 

        this.username = username; 

    } 

    public void setPassword(String password) { 

        this.password = password; 

    } 

    public void invoke(MessageContext context) throws Exception { 
        Element el = new Element("Header"); 
        context.getOutMessage().setHeader(el); 
        Element username_el = new Element("Username"); 
        username_el.addContent(username); 
        Element password_el = new Element("Password"); 
        password_el.addContent(password); 
        el.addContent(username_el); 
        el.addContent(password_el); 
    } 
}
