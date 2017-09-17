package com.channel.permission.impl;

import com.channel.model.perm.Permission;
import com.channel.model.perm.Permission_Relation;
import com.channel.model.user.CZdXzqhdm;
import com.channel.permission.PermissionDao;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.HQL;
import com.common.utils.tree.impl.TreeDaoImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 25019 on 2015/12/9.
 */
@Service("permissionDao")
public class PermissionDaoImpl extends TreeDaoImpl<Permission, Permission_Relation> implements PermissionDao {

    @Override
    public Class<?> getEntryClass() {
        return Permission.class;
    }

    @Override
    public Class<?> getEntryRelationClass() {
        return Permission_Relation.class;
    }

    public Permission findUser(int type, int userid) {
        return (Permission) super.findUnique(new HQL("select a from Permission a where a.type=? and a.name='?'", type, userid));
    }

    @Override
    public Permission findPerm(int type, String permcode) {
        return (Permission) super.findUnique(new HQL("select a from Permission a where a.type=? and a.name='?'", type, permcode));
    }

    @Override
    public BaseQueryRecords findPermByXzqh(int type, List<CZdXzqhdm> xzqhs) {
        String hql = "from Permission where type=?";
        if (xzqhs != null) {
            hql += " and xzqh in(";
            for (int i = 0; i < xzqhs.size(); i++) {
                hql += xzqhs.get(i).getId();
                if (i != xzqhs.size() - 1)
                    hql += ",";
            }
            hql += ")";
        }
        return super.find(new HQL(hql, type));
    }
}
