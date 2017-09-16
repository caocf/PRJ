package com.zjport.video;

import java.util.List;

import com.common.base.dao.impl.querycondition.ObjectQuery;
import com.zjport.video.model.CZdXzqhRela;
import com.zjport.video.model.CZdXzqhdm;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import com.common.utils.tree.impl.TreeDaoImpl;

@Repository("xzqhdmdao")
public class XzqhdmDaoImpl extends TreeDaoImpl<CZdXzqhdm, CZdXzqhRela> {

    @Override
    public Class<?> getEntryClass() {
        // TODO Auto-generated method stub
        return CZdXzqhdm.class;
    }

    @Override
    public Class<?> getEntryRelationClass() {
        // TODO Auto-generated method stub
        return CZdXzqhRela.class;
    }

    public CZdXzqhdm findXzqhById(Integer xzqh) {
        return (CZdXzqhdm) super.findUnique(new ObjectQuery(CZdXzqhdm.class, "id", xzqh));
    }
}
