package com.channel.permission;

import com.channel.permission.impl.PermissionServiceImpl;
import com.common.action.BaseAction;
import com.common.action.result.BaseResult;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 25019 on 2015/12/9.
 */
public class PermissionAction extends BaseAction {
    @Resource(name = "permissionService")
    private PermissionService permissionService;
    private BaseResult result;

    private String groupname;
    private String permcode;
    private String permdesc;
    private String rolename;
    private int permid;
    private int groupid;
    private int roleid;
    private int xzqhid;
    private List<Integer> permids;
    private List<Integer> userids;
    private int userid;

    private String sEcho;
    private int page;
    private int rows;

    public String toPermManager() {
        return SUCCESS;
    }

    public String addPermGroup() {
        result = this.permissionService.addPermGroup(groupname, groupid);
        return SUCCESS;
    }

    public String delPermGroup() {
        result = this.permissionService.delPermGroup(groupid);
        return SUCCESS;
    }

    public String updatePermGroup() {
        result = this.permissionService.updatePermGroup(groupid, groupname);
        return SUCCESS;
    }

    public String queryPermGroups() {
        result = this.permissionService.queryPermGroups(groupid);
        return SUCCESS;
    }

    public String queryPermGroupPerms() {
        result = this.permissionService.queryPermGroupPerms(groupid);
        return SUCCESS;
    }

    public String queryPermGroupPerms_r() {
        result = this.permissionService.queryPermGroupPerms_r(groupid);
        return SUCCESS;
    }

    public String addPerm() {
        result = this.permissionService.addPerm(groupid, permcode, permdesc);
        return SUCCESS;
    }

    public String delPerms() {
        if (permids != null && permids.size() != 0)
            result = this.permissionService.delPerms(permids);
        else
            result = this.permissionService.delPerm(permid);
        return SUCCESS;
    }

    public String updatePerm() {
        result = this.permissionService.updatePerm(permid, permcode, permdesc);
        return SUCCESS;
    }

    public String addRole() {
        result = this.permissionService.addRole(rolename,xzqhid);
        return SUCCESS;
    }

    public String updateRole() {
        result = this.permissionService.updateRole(roleid, rolename,xzqhid);
        return SUCCESS;
    }

    public String delRole() {
        result = this.permissionService.delRole(roleid);
        return SUCCESS;
    }

    public String queryRoles() {
        result = this.permissionService.queryRoles();
        return SUCCESS;
    }

    public String queryRolesByXzqh() {
        result = this.permissionService.queryRolesByXzqh(xzqhid);
        return SUCCESS;
    }

    public String queryRolePerms() {
        result = this.permissionService.queryRolePerms(roleid);
        return SUCCESS;
    }


    public String bindRolePerms() {
        if (permids != null && permids.size() != 0)
            result = this.permissionService.bindRolePerms(roleid, permids);
        else
            result = this.permissionService.bindRolePerm(roleid, permid);
        return SUCCESS;
    }

    public String delBindRolePerms() {
        if (permids != null && permids.size() != 0)
            result = this.permissionService.delBindRolePerms(roleid, permids);
        else
            result = this.permissionService.delBindRolePerm(roleid, permid);
        return SUCCESS;
    }

    public String queryRoleUsers() {
        result = this.permissionService.queryRoleUsers(roleid);
        return SUCCESS;
    }

    public String queryRoleUserInfos() {
        result = this.permissionService.queryRoleUserInfos(roleid, page, rows);
        result.addToMap("sEcho", sEcho);
        return SUCCESS;
    }

    public String bindRoleUsers() {
        if (userids != null && userids.size() != 0)
            result = this.permissionService.bindRoleUsers(roleid, userids);
        else
            result = this.permissionService.bindRoleUser(roleid, userid);
        return SUCCESS;
    }

    public String delBindRoleUsers() {
        if (userids != null && userids.size() != 0)
            result = this.permissionService.delBindRoleUsers(roleid, userids);
        else
            result = this.permissionService.delBindRoleUser(roleid, userid);
        return SUCCESS;
    }

    public String queryUserPerms() {
        result = this.permissionService.queryUserPerms(userid);
        return SUCCESS;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getPermcode() {
        return permcode;
    }

    public void setPermcode(String permcode) {
        this.permcode = permcode;
    }

    public String getPermdesc() {
        return permdesc;
    }

    public void setPermdesc(String permdesc) {
        this.permdesc = permdesc;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getSEcho() {
        return sEcho;
    }

    public void setSEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPermid() {
        return permid;
    }

    public void setPermid(int permid) {
        this.permid = permid;
    }

    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }

    public List<Integer> getPermids() {
        return permids;
    }

    public void setPermids(List<Integer> permids) {
        this.permids = permids;
    }

    public List<Integer> getUserids() {
        return userids;
    }

    public void setUserids(List<Integer> userids) {
        this.userids = userids;
    }

    public int getXzqhid() {
        return xzqhid;
    }

    public void setXzqhid(int xzqhid) {
        this.xzqhid = xzqhid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public BaseResult getResult() {
        return result;
    }

    public void setResult(BaseResult result) {
        this.result = result;
    }
}
