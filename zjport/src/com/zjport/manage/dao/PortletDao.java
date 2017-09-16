package com.zjport.manage.dao;

import com.common.base.BaseRecords;
import com.common.base.dao.impl.BaseDaoDB;
import com.common.base.dao.impl.querycondition.HQL;
import com.common.base.dao.impl.querycondition.ObjectQuery;
import com.common.base.dao.impl.querycondition.SQL;
import com.zjport.model.Portlet;
import com.zjport.model.TPortlet;
import com.zjport.model.TPortletLib;
import com.zjport.model.TPortletPart;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TWQ on 2016/12/1.
 */
@Repository("portletDao")
public class PortletDao extends BaseDaoDB{

    public TPortlet getPortlet(int userId) {
        return (TPortlet)super.findUnique(new ObjectQuery(TPortlet.class,"stUserId",userId));
    }

    public List<Portlet> getPortletList(String position, int userId) {

        String sql = "select a.ST_PORTLET_ID,b.ST_POSITION,b.ST_ORDER,b.ST_DISPLAY_FORM,c.ST_MODULE_NAME,c.ST_MODULE_ID,c.ST_ICON,b.ST_COLOR,c.ST_URL,c.ST_DESCRIBE,c.ST_PORTLET_LIB_ID " +
                "from t_portlet a LEFT JOIN t_portlet_part b on a.ST_PORTLET_PART_MIDDLE_ID=b.ST_PORTLET_PART_MIDDLE_ID " +
                "LEFT JOIN t_portlet_lib c on b.ST_PORTLET_LIB_ID=c.ST_PORTLET_LIB_ID ";

        Map<String,Object> paramMap = new HashMap<>();

        sql += " where b.ST_POSITION=:position";
        paramMap.put("position", position);
        sql += " and a.ST_USER_ID=:userId";
        paramMap.put("userId", userId);

        sql +=" order by b.ST_ORDER ";
        SQL Sql = new SQL(sql, paramMap);

        BaseRecords records = super.find(Sql);

        List<Portlet> list = new ArrayList<Portlet>();
        for(int i = 0; i<records.getData().size(); i++) {
            Portlet portlet = new Portlet();
            Object[] ob = (Object[])records.getData().get(i);
            portlet.setStPortletId(Integer.parseInt(String.valueOf(ob[0])));
            portlet.setStPosition(String.valueOf(ob[1]));
            portlet.setStOrder(String.valueOf(ob[2]));
            portlet.setStDisplayForm(String.valueOf(ob[3]));
            portlet.setStModuleName(String.valueOf(ob[4]));
            portlet.setStModuleId(String.valueOf(ob[5]));
            portlet.setStIcon(String.valueOf(ob[6]));
            portlet.setStColor(String.valueOf(ob[7]));
            portlet.setStURL(String.valueOf(ob[8]));
            portlet.setStDescribe(String.valueOf(ob[9]));
            portlet.setStPortletLibId(String.valueOf(ob[10]));
            list.add(portlet);
        }

        return list;
    }

    public List<TPortletPart> getpartListByMiddleId(String middleId) {
        return (List<TPortletPart>)super.find(new ObjectQuery(TPortletPart.class,"stPortletPartMiddleId",middleId)).getData();
    }

    public BaseRecords selectPortletLibList(List<Integer> out) {
        String hql = "from TPortletLib where stPortletLibId NOT IN (?)";

        hql +=" order by stOrder";
        HQL Hql = new HQL(hql, out);
        BaseRecords records = super.find(Hql);
        return records;
        //return super.find(new ObjectQuery(TPortletLib.class,"stOrder",false));
    }

    public BaseRecords selectPortletList(String search, int rows, int page) {
        return super.find(new ObjectQuery(TPortletLib.class, page, rows));
    }

    public TPortletLib getPortletLibById(int libId) {
        return (TPortletLib)super.findUnique(new ObjectQuery(TPortletLib.class,"stPortletLibId",libId));
    }

    public TPortletLib getPortletLibByModuleId(String moduleId) {
        return (TPortletLib)super.findUnique(new ObjectQuery(TPortletLib.class,"stModuleId",moduleId));
    }

    public BaseRecords getPortletLibInfoById(int libId) {
        return super.find(new ObjectQuery(TPortletLib.class,"stPortletLibId",libId));
    }
}
