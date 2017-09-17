package com.channel.dic.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.channel.dic.dao.DicDao;
import com.channel.model.hd.CZdAppattribute;
import com.channel.model.user.CZdUserjstatus;
import com.common.dao.BaseQueryRecords;
import com.common.dao.impl.BaseDaoDB;
import com.common.dao.impl.HQL;

@Repository("dicdao")
public class DicDaoImpl extends BaseDaoDB implements DicDao {
    @Override
    public BaseQueryRecords<CZdAppattribute> queryAllTitle() {
        // TODO Auto-generated method stub
        String hql = "from  CZdAppattribute where id>0 and valid = 1 and name='yhzw'";
        return (BaseQueryRecords<CZdAppattribute>) super.find(new HQL(hql));
    }

    @Override
    public BaseQueryRecords<CZdUserjstatus> queryAllJStatus() {
        // TODO Auto-generated method stub
        String hql = "from CZdUserjstatus where id>0 and valid=1";
        return (BaseQueryRecords<CZdUserjstatus>) super.find(new HQL(hql));
    }

    @Override
    public BaseQueryRecords<CZdAppattribute> queryDicAttr(String name) {
        // TODO Auto-generated method stub
        String hql = "from CZdAppattribute where name='?' and valid=1";
        return (BaseQueryRecords<CZdAppattribute>) super
                .find(new HQL(hql, name));
    }

    @Override
    public String queryAttrDesc(Integer id) {
        // TODO Auto-generated method stub
        String attrdesc = "";
        if (id != null) {
            String hql = "from CZdAppattribute where id=? and valid=1";
            List<CZdAppattribute> appattr = (List<CZdAppattribute>) super.find(
                    new HQL(hql, id)).getData();
            if (appattr.size() != 0 && appattr != null) {
                attrdesc = appattr.get(0).getAttrdesc();
            }
        }
        return attrdesc;
    }

    @Override
    public List queryNameDesc() {
        // TODO Auto-generated method stub
        String hql = "select distinct namedesc,name from CZdAppattribute where valid=1 and editable=1 order by name";
        return super.find(new HQL(hql)).getData();
    }

    @Override
    public void addDic(CZdAppattribute dic) {
        // TODO Auto-generated method stub
        super.save(dic);
    }

    @Override
    public CZdAppattribute queryDicById(int id) {
        // TODO Auto-generated method stub
        return (CZdAppattribute) super.findUnique(new CZdAppattribute(), "id",
                id);
    }

    @Override
    public void updateDic(CZdAppattribute dic) {
        // TODO Auto-generated method stub
        super.update(dic);
    }

    @Override
    public void addUserJstatus(CZdUserjstatus jstatus) {
        super.save(jstatus);
    }

    @Override
    public CZdUserjstatus queryJstatusById(int id) {
        return (CZdUserjstatus) super
                .findUnique(new CZdUserjstatus(), "id", id);
    }

    @Override
    public void updateJstatus(CZdUserjstatus jstatus) {
        super.update(jstatus);
    }

    @Override
    public List queryAllName() {
        String hql = "select distinct name from  CZdAppattribute where valid=1 and editable=1 order by name";
        return super.find(new HQL(hql)).getData();
    }

    @Override
    public int queryIdByNameDesc(String name, String desc) {
        int dicid = 0;
        String hql = "from  CZdAppattribute where valid=1 and name='?' and attrdesc='?'";
        CZdAppattribute dic = (CZdAppattribute) super.findUnique(new HQL(hql, name, desc));
        if (dic != null) {
            dicid = dic.getId();
        }
        return dicid;
    }

}
