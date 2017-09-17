package com.channel.permission;

import javax.servlet.http.HttpSessionListener;

import com.channel.model.user.CXtUser;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Created by 25019 on 2015/12/23.
 */
public class PermissionSessionListener implements HttpSessionListener {
    private static ConcurrentHashMap<String, HttpSession> sessions = new ConcurrentHashMap<>();//保存上下文
    public static final String INVALID = "INVALIDPERM";

    public static void invalidPermsAll() {
        Iterator<Map.Entry<String, HttpSession>> it = sessions.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, HttpSession> entry = it.next();

            HttpSession session = entry.getValue();
            if (session != null) {
                session.setAttribute(INVALID, true);

                CXtUser user = (CXtUser) session.getAttribute("user");
                if (user != null) {
                    System.out.println("因权限更改，设置用户" + user.getId() + "session中的权限失败");
                }
            }
        }
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        String sessionid = session.getId();
        sessions.put(sessionid, session);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        String sessionid = session.getId();
        sessions.remove(sessionid);
    }
}
