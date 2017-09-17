package com.channel.user.dao.impl;

import org.springframework.stereotype.Repository;

import com.channel.model.user.CXtDpt;
import com.channel.model.user.CXtDptRela;
import com.common.dao.impl.HQL;
import com.common.utils.tree.impl.TreeDaoImpl;

@Repository("dptdao")
public class DptDaoImpl extends TreeDaoImpl<CXtDpt, CXtDptRela> {

    @Override
    public Class<?> getEntryClass() {
        // TODO Auto-generated method stub
        return CXtDpt.class;
    }

    @Override
    public Class<?> getEntryRelationClass() {
        // TODO Auto-generated method stub
        return CXtDptRela.class;
    }

    public CXtDpt queryDptById(int id) {
        CXtDpt dpt = (CXtDpt) super.findUnique(new CXtDpt(), "id", id);
        return dpt;
    }

    public CXtDptRela queryDptRelaBySid(int sid) {
        CXtDptRela dptRela = (CXtDptRela) super.findUnique(new CXtDptRela(), "sid", sid);
        return dptRela;
    }

    public void delDptRelaByid(int id) {
        String hql = "delete from CXtDptRela where pid =? or sid=?";
        super.delete(new HQL(hql, id, id));
    }

    public void addDpt(CXtDpt cXtDpt) {
        super.save(cXtDpt);
    }

    public void addDptRela(CXtDptRela dptRela) {
        // TODO Auto-generated method stub
        super.save(dptRela);
    }

    public CXtDpt queryDptInfo(Integer gljgbh) {
        // TODO Auto-generated method stub
        return (CXtDpt) super.findUnique(new CXtDpt(), "id", gljgbh);
    }

    public CXtDpt queryDptByName(String name) {
        return (CXtDpt) super.findUnique(new CXtDpt(), "name", name);
    }

    public CXtDpt queryDptByXzqh(int xzqh, int type) {
        String hql = "from CXtDpt where xzqh=? and type=?";
        return (CXtDpt) super.findUnique(new HQL(hql, xzqh, type));
    }
}
