package com.channel.permission;

import com.channel.model.perm.Permission;
import com.channel.model.user.CXtUser;
import com.channel.permission.impl.PermissionServiceImpl;
import com.common.utils.tree.model.Tree;
import com.mchange.v2.beans.swing.TestBean;
import com.opensymphony.xwork2.ActionContext;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 25019 on 2015/12/15.
 */
@Component("permissionSession")
public class UI implements ApplicationContextAware {
    private static ApplicationContext applicationContext; // Spring应用上下文环境
    private static ConcurrentHashMap<String, Map<String, Object>> permsession = new ConcurrentHashMap<>();//保存上下文
    private static PermissionService permissionService = null;

    private List<Permission> _queryHasPermGroups(Tree<Permission> group) {
        List<Permission> groups = new ArrayList<>();
        //查看当前组的子节点有没有权限
        for (Tree<Permission> child : group.getChildren()) {
            if (child.getNode().getType() == PermissionServiceImpl.PERM_PERM_TYPE) {
                groups.add(group.getNode());
                return groups;
            }
        }
        boolean subgrouphasperms = false;
        //查看子节点为组有没有权限
        for (Tree<Permission> child : group.getChildren()) {
            if (child.getNode().getType() == PermissionServiceImpl.PERM_GROUP_TYPE) {
                List<Permission> subgroups = _queryHasPermGroups(child);
                groups.addAll(subgroups);
                if (subgroups != null && subgroups.size() > 0)
                    subgrouphasperms = true;
            }
        }
        if (subgrouphasperms)
            groups.add(group.getNode());
        return groups;
    }

    private Map<String, Object> _getSessionMap() {
        ActionContext actionContext = ActionContext.getContext();
        Map<String, Object> session = actionContext.getSession();
        CXtUser user = (CXtUser) session.get("user");
        if (user != null) {
            Boolean invalid = (Boolean) session.get(PermissionSessionListener.INVALID);
            if (invalid == null || invalid == true || session.get("groups") == null) {
                //获得组权限列表
                List<Tree<Permission>> groupperms = UI.permissionService.queryUserGroupPerms(user.getId());
                List<Permission> groups = new ArrayList<>();
                for (Tree<Permission> group : groupperms) {
                    groups.addAll(_queryHasPermGroups(group));
                }
                session.put("groups", groups);
            }
            if (invalid == null || invalid == true || session.get("perms") == null) {
                session.put("perms", (List<Permission>) UI.permissionService.queryUserPerms(user.getId()).getObj());
            }
            session.put(PermissionSessionListener.INVALID, false);
        }
        return session;
    }

    // 获得用户的权限列表
    public List<Permission> getPerms() {
        return (List<Permission>) _getSessionMap().get("perms");
    }

    // 获得有权限的组
    public List<Permission> getGroups() {
        return (List<Permission>) _getSessionMap().get("groups");
    }

    //判断用户是否有某个权限
    public boolean hasPerm(String permcode) {
        List<Permission> perms = getPerms();
        if (perms != null) {
            for (Permission perm : perms) {
                if (perm.getName() != null && perm.getName().equals(permcode))
                    return true;
            }
        }
        return false;
    }

    //判断用户是否有某个组内的权限
    public boolean groupHasPerms(String groupname) {
        List<Permission> groups = getGroups();
        if (groups != null) {
            for (Permission group : groups) {
                if (group.getName() != null && group.getName().equals(groupname))
                    return true;
            }
        }
        return false;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        UI.applicationContext = applicationContext;
        permissionService = (PermissionService) UI.applicationContext.getBean("permissionService");
    }
}
