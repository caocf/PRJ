package com.zjport.smart.dao.impl;

import com.common.base.dao.impl.BaseDaoDB;
import com.common.base.dao.impl.querycondition.HQL;
import com.zjport.model.Parament;
import com.zjport.smart.dao.IParamentDao;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Will on 2016/10/12 12:01.
 */
@Repository("paramentDao")
public class ParamentDaoImpl extends BaseDaoDB implements IParamentDao {

    @Override
    public List<Parament> queryParamentByIds(Integer[] ids) {
        String hqlStr = "from Parament as p where p.cameraId in (?) order by p.cameraId";
        List<Integer> param = new ArrayList<>();
        for (int i : ids) {
            param.add(i);
        }
        HQL hql = new HQL(hqlStr, param);
        return (List<Parament>)this.find(hql).getData();
    }
}
