package com.channel.permission;

import com.channel.model.perm.Permission;
import com.channel.model.perm.Permission_Relation;
import com.channel.model.user.CZdXzqhdm;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.HQL;
import com.common.utils.tree.TreeDao;

import java.util.List;

/**
 * Created by 25019 on 2015/12/9.
 */
public interface PermissionDao extends TreeDao<Permission, Permission_Relation> {
    public Permission findUser(int type, int userid);

    public Permission findPerm(int type, String permcode);

    public BaseQueryRecords findPermByXzqh(int type, List<CZdXzqhdm> xzqh);
}
