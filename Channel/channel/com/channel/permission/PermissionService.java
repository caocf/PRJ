package com.channel.permission;

import com.channel.model.perm.Permission;
import com.channel.model.perm.Permission_Relation;
import com.channel.model.user.CXtUser;
import com.common.action.result.BaseResult;
import com.common.action.result.BaseResultFailed;
import com.common.action.result.BaseResultOK;
import com.common.utils.tree.TreeService;
import com.common.utils.tree.model.Tree;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 25019 on 2015/12/9.
 */
public interface PermissionService extends TreeService<Permission, Permission_Relation> {
    //添加权限分组
    public BaseResult addPermGroup(String groupname, int groupid);

    //删除权限分组
    public BaseResult delPermGroup(int groupid);

    //更新权限分组
    public BaseResult updatePermGroup(int groupid, String groupname);

    //查询权限分组
    public BaseResult queryPermGroups(int groupid);

    //查询所有权限分组
    public List<Permission> queryPermGroups();

    //查询权限组下的所有权限列表
    public BaseResult queryPermGroupPerms(int groupid);

    //查询权限组及下属权限组下的的所有权限列表
    public BaseResult queryPermGroupPerms_r(int groupid);

    //查询用户的权限列表，以组的方式查询
    public List<Tree<Permission>> queryUserGroupPerms(int userid);

    //在权限组下添加权限
    public BaseResult addPerm(int groupid, String permcode, String permdesc);

    //删除权限
    public BaseResult delPerm(int permid);

    public BaseResult delPerms(List<Integer> permids);

    //修改权限
    public BaseResult updatePerm(int permid, String permcode,
                                 String permdesc);

    //添加角色
    public BaseResult addRole(String rolename, int xzqhid);

    //更新角色
    public BaseResult updateRole(int roleid, String rolename, int xzqhid);

    //删除角色
    public BaseResult delRole(int roleid);

    //查询角色列表
    public BaseResult queryRoles();

    //查询角色绑定的权限
    public BaseResult queryRolePerms(int roleid);

    //为角色绑定权限
    public BaseResult bindRolePerm(int roleid, int permid);

    //为角色绑定权限
    public BaseResult bindRolePerms(int roleid, List<Integer> permids);

    //为角色解绑权限
    public BaseResult delBindRolePerms(int roleid, List<Integer> permids);

    //为角色解绑权限
    public BaseResult delBindRolePerm(int roleid, int permid);

    //查找角色绑定的用户
    public BaseResult queryRoleUsers(int roleid);

    //查询用户绑定的角色
    public List<Permission> queryUserRoles(int userid);

    //查询角色绑定的用户信息列表
    public BaseResult queryRoleUserInfos(int roleid, int page, int rows);

    //为角色绑定用户
    public BaseResult bindRoleUsers(int roleid, List<Integer> userids);

    //为角色绑定用户
    public BaseResult bindRoleUser(int roleid, int userid);

    //为角色解绑用户
    public BaseResult delBindRoleUser(int roleid, int userid);

    //为角色解绑用户
    public BaseResult delBindRoleUsers(int roleid, List<Integer> userids);

    //为用户解绑所有角色
    public BaseResult delBindUserRoles(int userid);

    //查找用户拥有的所有权限
    public BaseResult queryUserPerms(int userid);

    //为用户创建权限映象
    public BaseResult addUserRefect(int userid);

    //删除用户映象
    public BaseResult delUserRefect(int userid);

    //为系统生成所有用户映象
    public void initUserRefect();

    public BaseResult queryRolesByXzqh(int xzqhid);
}
