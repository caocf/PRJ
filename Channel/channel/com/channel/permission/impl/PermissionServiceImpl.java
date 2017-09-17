package com.channel.permission.impl;

import com.channel.bean.Constants;
import com.channel.dic.dao.impl.XzqhdmDaoImpl;
import com.channel.dic.service.XzqhdmService;
import com.channel.model.perm.Permission;
import com.channel.model.perm.Permission_Relation;
import com.channel.model.user.CXtDpt;
import com.channel.model.user.CXtUser;
import com.channel.model.user.CZdXzqhdm;
import com.channel.permission.PermissionDao;
import com.channel.permission.PermissionService;
import com.channel.permission.PermissionSessionListener;
import com.channel.user.dao.UserDao;
import com.channel.user.dao.impl.DptDaoImpl;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.action.result.BaseResultOK;
import com.common.dao.impl.StringExpression;
import com.common.utils.tree.TreeDao;
import com.common.utils.tree.impl.TreeServiceImpl;
import com.common.utils.tree.model.Tree;
import org.junit.Test;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by 25019 on 2015/12/9.
 */
@Service("permissionService")
public class PermissionServiceImpl extends TreeServiceImpl<Permission, Permission_Relation> implements PermissionService {
    public static final int PERM_GROUP_TYPE = 1;//权限分组
    public static final int PERM_USER_TYPE = 2;//用户映象
    public static final int PERM_PERM_TYPE = 3;//权限
    public static final int PERM_ROLE_TYPE = 4;//角色

    @Resource(name = "permissionDao")
    private PermissionDao permissionDao;
    @Resource(name = "xzqhdmservice")
    private XzqhdmService xzqhdmService;
    @Resource(name = "xzqhdmdao")
    private XzqhdmDaoImpl xzqhdmdao;
    @Resource(name = "userdao")
    private UserDao userDao;
    @Resource(name = "dptdao")
    private DptDaoImpl dptdao;

    @Override
    public TreeDao<Permission, Permission_Relation> getTreeDao() {
        return permissionDao;
    }

    //添加权限分组
    public BaseResult addPermGroup(String groupname, int groupid) {
        Permission pgroup = this.findNode(new Permission(groupid), PERM_GROUP_TYPE);
        //查看该组下是否有权限，如果有，不能添加
        if (pgroup != null) {
            List<Permission> perms = this.findChildrenNodes_r(pgroup, PERM_PERM_TYPE);
            if (perms != null && perms.size() > 0) {
                return new BaseResultFailed("该父分组下存在已绑定的权限，请先删除父分组下的权限再进行添加");
            }
        }

        Permission group = new Permission();
        group.setType(PERM_GROUP_TYPE);
        group.setName(groupname);
        group.setDesc(groupname);

        this.addNode(group);

        if (pgroup != null) {
            this.bindChildrenNode(pgroup, group);
        }

        return new BaseResultOK(group);
    }


    //删除权限分组
    public BaseResult delPermGroup(int groupid) {
        Permission group = this.findNode(new Permission(groupid), PERM_GROUP_TYPE);
        if (group == null)
            return new BaseResultFailed("权限组不存在");

        List<Permission> groups = this.findChildrenNodes_r(group, PERM_GROUP_TYPE);
        if (groups != null && groups.size() > 0) {
            return new BaseResultFailed("该权限组下存在下一级分组，请先删除下一级分组后再删除当前分组");
        }

        //判断权限分组下是否有权限
        List<Permission> perms = this.findChildrenNodes_r(group, PERM_PERM_TYPE);
        if (perms == null || perms.size() == 0) {
            this.delNode(group);
            return new BaseResultOK();
        } else
            return new BaseResultFailed("权限组下有权限，请先删除权限后再删除分组");
    }

    //更新权限分组
    public BaseResult updatePermGroup(int groupid, String groupname) {
        Permission group = this.findNode(new Permission(groupid), PERM_GROUP_TYPE);
        if (group == null)
            return new BaseResultFailed("权限组不存在");
        group.setName(groupname);
        group.setDesc(groupname);
        this.modifyNode(group);
        return new BaseResultOK();
    }

    //查询所有权限分组
    @Override
    public List<Permission> queryPermGroups() {
        List<Permission> groups = this.findNodes(PERM_GROUP_TYPE).getData();
        return groups;
    }

    //查询权限分组
    public BaseResult queryPermGroups(int groupid) {
        List<Permission> groups = null;

        if (groupid == -1 || groupid == 0)
            groups = this.findRootNodes(PERM_GROUP_TYPE).getData();
        else {
            groups = this.findChildrenNodes(new Permission(groupid), PERM_GROUP_TYPE).getData();
        }
        List<Integer> isleafList = new ArrayList<>();
        for (Permission group : groups) {
            isleafList.add(ifPermGroupLeafGroup(group.getId()) ? 1 : 0);
        }
        BaseResult result = new BaseResultOK(groups);
        result.addToMap("isleaf", isleafList);
        return result;
    }

    //判断权限分组是否是叶子分组
    private boolean ifPermGroupLeafGroup(int groupid) {
        List<Permission> groups = this.findChildrenNodes(new Permission(groupid), PERM_GROUP_TYPE).getData();
        if (groups != null && groups.size() > 0)
            return false;
        return true;
    }

    //查询权限组下的所有权限列表
    public BaseResult queryPermGroupPerms(int groupid) {
        Permission group = this.findNode(new Permission(groupid), PERM_GROUP_TYPE);
        if (group == null)
            return new BaseResultFailed("权限组不存在");
        List<Permission> perms = this.findChildrenNodes(group, PERM_PERM_TYPE).getData();
        return new BaseResultOK(perms);
    }

    //查询权限组及下属权限组下的的所有权限列表
    public BaseResult queryPermGroupPerms_r(int groupid) {
        Permission group = this.findNode(new Permission(groupid), PERM_GROUP_TYPE);
        if (group == null)
            return new BaseResultFailed("权限组不存在");
        List<Permission> perms = this.findChildrenNodes(group, PERM_PERM_TYPE).getData();
        //查询出所有的权限组，包括所有下属权限组
        List<Permission> groups = this.findChildrenNodes_r(group, PERM_GROUP_TYPE);
        for (Permission gp : groups) {
            List<Permission> gpperms = this.findChildrenNodes(gp, PERM_PERM_TYPE).getData();
            perms.addAll(gpperms);
        }
        return new BaseResultOK(perms);
    }

    //查询用户的权限列表，以组的方式查询
    public List<Tree<Permission>> queryUserGroupPerms(int userid) {
        //查询出该用户的权限列表
        BaseResult userpermsresult = this.queryUserPerms(userid);
        if (!userpermsresult.ifResultOK())
            return null;

        //该用户的权限列表
        List<Permission> userperms = (List<Permission>) userpermsresult.getObj();

        //获取系统的根权限分组
        BaseResult rootgroupret = queryPermGroups(-1);
        if (!rootgroupret.ifResultOK())
            return null;

        List<Tree<Permission>> ret = new ArrayList<>();

        //查询出所有的权限组及对应的权限
        List<Permission> rootgroups = (List<Permission>) rootgroupret.getObj();
        for (Permission rootgroup : rootgroups) {
            Tree<Permission> tree = this.findNodeTree(rootgroup, -1);
            domark(tree, userperms);
            ret.add(tree);
        }
        return ret;
    }

    private void domark(Tree<Permission> groupperm, List<Permission> perms) {
        //看自己的子节点有没有权限，如果有，则过滤
        List<Tree<Permission>> children = groupperm.getChildren();
        Iterator<Tree<Permission>> it = children.iterator();
        while (it.hasNext()) {
            Tree<Permission> child = it.next();
            if (child.getNode().getType() == PERM_PERM_TYPE) {
                boolean flag = false;
                for (Permission perm : perms) {
                    if (child.getNode().getId() == perm.getId())
                        flag = true;
                }
                if (flag == false) {
                    it.remove();
                }
            }
        }

        //再看子节点有没有，如果有，则过滤
        for (Tree<Permission> child : children) {
            if (child.getNode().getType() == PERM_GROUP_TYPE) {
                domark(child, perms);
            }
        }
    }

    private boolean checkTreeHasPerm(Tree<Permission> tree, Permission perm) {
        //检查自己的孩子节点是否有该权限
        List<Tree<Permission>> children = tree.getChildren();
        for (Tree<Permission> child : children) {
            if (child.getNode().getType() == PERM_PERM_TYPE && child.getNode().getId() == perm.getId())
                return true;
        }
        for (Tree<Permission> child : children) {
            if (child.getNode().getType() == PERM_GROUP_TYPE) {
                boolean hasperm = checkTreeHasPerm(child, perm);
                if (hasperm)
                    return true;
            }
        }
        return false;
    }

    //在权限组下添加权限
    public BaseResult addPerm(int groupid, String permcode, String permdesc) {
        Permission group = this.findNode(new Permission(groupid), PERM_GROUP_TYPE);
        if (group == null)
            return new BaseResultFailed("权限组不存在");

        List<Permission> groups = this.findChildrenNodes_r(group, PERM_GROUP_TYPE);
        if (groups != null && groups.size() > 0)
            return new BaseResultFailed("该分组下存在下一级分组，无法在该组下添加权限");

        Permission p = this.permissionDao.findPerm(PERM_PERM_TYPE, permcode);
        if (p != null) {
            return new BaseResultFailed(new StringExpression("代码为?的权限已存在", permcode).toString());
        }

        Permission perm = new Permission();
        perm.setType(PERM_PERM_TYPE);
        perm.setName(permcode);
        perm.setDesc(permdesc);

        this.addBindChildrenNode(group, perm);
        return new BaseResultOK(perm);
    }

    //删除权限
    public BaseResult delPerm(int permid) {
        Permission perm = this.findNode(new Permission(permid), PERM_PERM_TYPE);
        if (perm == null)
            return new BaseResultFailed("权限不存在");

        this.delNode(perm);

        PermissionSessionListener.invalidPermsAll();

        return new BaseResultOK();
    }

    //删除权限
    public BaseResult delPerms(List<Integer> permids) {
        for (Integer permid : permids) {
            Permission perm = this.findNode(new Permission(permid), PERM_PERM_TYPE);
            if (perm != null)
                this.delNode(perm);
        }
        PermissionSessionListener.invalidPermsAll();
        return new BaseResultOK();
    }

    //修改权限
    public BaseResult updatePerm(int permid, String permcode, String permdesc) {
        Permission perm = this.findNode(new Permission(permid), PERM_PERM_TYPE);
        if (perm == null)
            return new BaseResultFailed("权限不存在");
        perm.setName(permcode);
        perm.setDesc(permdesc);
        this.modifyNode(perm);
        PermissionSessionListener.invalidPermsAll();
        return new BaseResultOK();
    }

    //添加角色
    public BaseResult addRole(String rolename, int xzqhid) {
        Permission role = new Permission();
        role.setType(PERM_ROLE_TYPE);
        role.setName(rolename);
        role.setDesc(rolename);
        role.setXzqh(xzqhid);
        this.addNode(role);
        return new BaseResultOK();
    }

    //更新角色
    public BaseResult updateRole(int roleid, String rolename, int xzqhid) {
        Permission role = this.findNode(new Permission(roleid), PERM_ROLE_TYPE);
        if (role == null)
            return new BaseResultFailed("角色不存在");
        role.setDesc(rolename);
        role.setName(rolename);
        role.setXzqh(xzqhid);
        this.modifyNode(role);
        return new BaseResultOK();
    }

    //删除角色
    public BaseResult delRole(int roleid) {
        Permission role = this.findNode(new Permission(roleid), PERM_ROLE_TYPE);
        if (role == null)
            return new BaseResultFailed("角色不存在");

        //查检权限是否有关联的用户，如果有，则不能删除
        List<Permission> users = this.findParentNodes_r(role, PERM_USER_TYPE);
        if (users == null || users.size() == 0) {
            this.delNode(role);
            return new BaseResultOK();
        } else
            return new BaseResultFailed("请先删除角色下关联的用户");
    }

    @Override
    public BaseResult queryRoles() {
        List<Permission> roles = new ArrayList<>();
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        CZdXzqhdm xzqh = (CZdXzqhdm) getSession().getAttribute("dptxzqh");
        if (xzqh == null && "admin".equals(user.getUsername().trim())) {
            roles = this.findNodes(PERM_ROLE_TYPE).getData();
        } else {
            List<CZdXzqhdm> list = this.xzqhdmService.findChildrenNodes_r(xzqh);
            list.add(xzqh);
            roles = this.permissionDao.findPermByXzqh(PERM_ROLE_TYPE, list).getData();
        }
        BaseResultOK ok = new BaseResultOK(roles);
        ok.addToMap("xzqhid", getSession().getAttribute("dptshixzqh"));
        return ok;
    }

    @Override
    public BaseResult queryRolesByXzqh(int xzqhid) {
        List<Permission> roles = new ArrayList<>();
        CXtUser user = (CXtUser) getSession().getAttribute("user");
        CZdXzqhdm xzqh = this.xzqhdmdao.queryXzqhdmById(xzqhid);
        if (xzqh.getId() == 1 && "admin".equals(user.getName())) {
            roles = this.findNodes(PERM_ROLE_TYPE).getData();
        } else {
/*          List<CZdXzqhdm> list = this.xzqhdmService.findChildrenNodes_r(xzqh);
            list.add(xzqh);
            roles = this.permissionDao.findPermByXzqh(PERM_ROLE_TYPE, list).getData();*/
            //处到市的
            if (xzqh != null) {
                int type = xzqh.getType();
                if (type == Constants.XZQH_QUXIAN) {
                    CZdXzqhdm dptshixzqh = this.xzqhdmService.queryShiXzqh(xzqh.getId());
                    List<CZdXzqhdm> list = this.xzqhdmService.findChildrenNodes_r(dptshixzqh);
                    list.add(dptshixzqh);
                    roles = this.permissionDao.findPermByXzqh(PERM_ROLE_TYPE, list).getData();
                } else {
                    List<CZdXzqhdm> list = this.xzqhdmService.findChildrenNodes_r(xzqh);
                    list.add(xzqh);
                    roles = this.permissionDao.findPermByXzqh(PERM_ROLE_TYPE, list).getData();
                }
            }
        }
        BaseResultOK ok = new BaseResultOK(roles);
        return ok;
    }

    //查询角色绑定的权限
    public BaseResult queryRolePerms(int roleid) {
        Permission role = this.findNode(new Permission(roleid), PERM_ROLE_TYPE);
        if (role == null)
            return new BaseResultFailed("角色不存在");

        List<Permission> perms = this.findChildrenNodes_r(role, PERM_PERM_TYPE);
        return new BaseResultOK(perms);
    }

    //为角色绑定权限
    public BaseResult bindRolePerm(int roleid, int permid) {
        List<Integer> permids = new ArrayList<>();
        permids.add(permid);
        PermissionSessionListener.invalidPermsAll();
        return bindRolePerms(roleid, permids);
    }

    //为角色绑定权限
    public BaseResult bindRolePerms(int roleid, List<Integer> permids) {
        Permission role = this.findNode(new Permission(roleid), PERM_ROLE_TYPE);
        if (role == null)
            return new BaseResultFailed("角色不存在");

        for (Integer permid : permids) {
            Permission perm = this.findNode(new Permission(permid), PERM_PERM_TYPE);
            if (perm != null) {
                this.bindChildrenNode(role, perm);
            }
        }
        PermissionSessionListener.invalidPermsAll();
        return new BaseResultOK();
    }

    //为角色解绑权限
    public BaseResult delBindRolePerms(int roleid, List<Integer> permids) {
        Permission role = this.findNode(new Permission(roleid), PERM_ROLE_TYPE);
        if (role == null)
            return new BaseResultFailed("角色不存在");

        for (Integer permid : permids) {
            Permission perm = this.findNode(new Permission(permid), PERM_PERM_TYPE);
            if (perm != null) {
                this.delBindChildrenNode(role, perm);
            }
        }
        PermissionSessionListener.invalidPermsAll();
        return new BaseResultOK();
    }

    //为角色解绑权限
    public BaseResult delBindRolePerm(int roleid, int permid) {
        List<Integer> permids = new ArrayList<>();
        permids.add(permid);
        PermissionSessionListener.invalidPermsAll();
        return delBindRolePerms(roleid, permids);
    }

    //查找角色绑定的用户
    public BaseResult queryRoleUsers(int roleid) {
        Permission role = this.findNode(new Permission(roleid), PERM_ROLE_TYPE);
        if (role == null)
            return new BaseResultFailed("角色不存在");

        List<Permission> users = this.findParentNodes_r(role, PERM_USER_TYPE);
        return new BaseResultOK(users);
    }

    //查找角色绑定的用户
    public List<Permission> queryUserRoles(int userid) {
        Permission user = this.permissionDao.findUser(PERM_USER_TYPE, userid);
        if (user == null)
            return new ArrayList<>();
        List<Permission> perms = this.findChildrenNodes_r(user, PERM_ROLE_TYPE);
        return perms;
    }

    @Override
    public BaseResult queryRoleUserInfos(int roleid, int page, int rows) {
        Permission role = this.findNode(new Permission(roleid), PERM_ROLE_TYPE);
        if (role == null)
            return new BaseResultFailed("角色不存在");

        List<Permission> users = this.findParentNodes_r(role, PERM_USER_TYPE);
        List<Integer> userids = new ArrayList<>();
        for (Permission user : users) {
            userids.add(Integer.parseInt(user.getName()));
        }
        return new BaseResultOK(this.userDao.queryUsers(userids, page, rows));
    }

    //为角色绑定用户
    public BaseResult bindRoleUsers(int roleid, List<Integer> userids) {
        Permission role = this.findNode(new Permission(roleid), PERM_ROLE_TYPE);
        if (role == null)
            return new BaseResultFailed("角色不存在");
        for (Integer userid : userids) {
            BaseResult addUserRet = addUserRefect(userid);
            if (addUserRet.ifResultOK()) {
                Permission user = (Permission) addUserRet.getObj();
                this.bindChildrenNode(user, role);
            }
        }
        PermissionSessionListener.invalidPermsAll();
        return new BaseResultOK();
    }

    //为角色绑定用户
    public BaseResult bindRoleUser(int roleid, int userid) {
        List<Integer> userids = new ArrayList<>();
        userids.add(userid);
        PermissionSessionListener.invalidPermsAll();
        return bindRoleUsers(roleid, userids);
    }

    //为角色解绑解雇
    public BaseResult delBindRoleUser(int roleid, int userid) {
        List<Integer> userids = new ArrayList<>();
        userids.add(userid);
        PermissionSessionListener.invalidPermsAll();
        return delBindRoleUsers(roleid, userids);
    }

    //为角色解绑解雇
    public BaseResult delBindRoleUsers(int roleid, List<Integer> userids) {
        Permission role = this.findNode(new Permission(roleid), PERM_ROLE_TYPE);
        if (role == null)
            return new BaseResultFailed("角色不存在");
        for (Integer userid : userids) {
            Permission user = this.permissionDao.findUser(PERM_USER_TYPE, userid);
            if (user != null)
                this.delBindChildrenNode(user, role);
        }
        PermissionSessionListener.invalidPermsAll();
        return new BaseResultOK();
    }

    //查找用户拥有的所有权限
    public BaseResult queryUserPerms(int userid) {
        Permission user = this.permissionDao.findUser(PERM_USER_TYPE, userid);
        if (user == null)
            return new BaseResultFailed("用户不存在");
        List<Permission> perms = this.findChildrenNodes_r(user, PERM_PERM_TYPE);
        return new BaseResultOK(perms);
    }

    @Override
    public BaseResult delBindUserRoles(int userid) {
        Permission user = this.permissionDao.findUser(PERM_USER_TYPE, userid);
        if (user != null) {
            //用户只跟角色绑定
            this.delBindChildrenNodes(user);
        }
        return new BaseResultOK();
    }

    //为用户创建权限映象
    public BaseResult addUserRefect(int userid) {
        Permission user = this.permissionDao.findUser(PERM_USER_TYPE, userid);
        if (user == null) {
            CXtUser xtuser = this.userDao.queryUserById(userid);
            if (xtuser == null)
                return new BaseResultFailed("系统不存在该用户");

            user = new Permission();
            user.setType(PERM_USER_TYPE);
            user.setName("" + userid);
            user.setDesc("" + userid);
            this.addNode(user);
        }
        return new BaseResultOK(user);
    }

    //删除用户映象
    public BaseResult delUserRefect(int userid) {
        Permission user = this.permissionDao.findUser(PERM_USER_TYPE, userid);
        if (user != null) {
            this.delNode(user);
        }
        return new BaseResultOK(user);
    }

    //为系统生成所有用户映象
    @Test
    @Rollback(false)
    public void initUserRefect() {
        try {
            List<CXtUser> users = this.userDao.queryUsers().getData();
            for (CXtUser user : users) {
                this.addUserRefect(user.getId());
            }
        } catch (Exception e) {
        }
    }
}
