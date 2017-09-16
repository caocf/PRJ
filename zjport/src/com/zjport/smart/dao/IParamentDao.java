package com.zjport.smart.dao;

import com.common.base.dao.BaseDao;
import com.zjport.model.Parament;

import java.util.List;

/**
 * Created by Will on 2016/10/12 12:01.
 */
public interface IParamentDao extends BaseDao {

    List<Parament> queryParamentByIds(Integer[] ids);
}
