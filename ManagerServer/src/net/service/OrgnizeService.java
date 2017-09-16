package net.service;

import common.BaseResult;
import net.modol.JcxxYhEN;
import net.modol.JcxxZzjgEN;
import net.modol.LawBaseEN;
import net.modol.LeaveOrOt;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2016/9/9.
 */
@Service
public class OrgnizeService extends BaseService
{
    //查找上级是该id的项
    public List<JcxxZzjgEN> ItemsByPid(String pid)
    {
        Session session=sessionFactory.openSession();
        List<JcxxZzjgEN> user=session.createCriteria(JcxxZzjgEN.class)
                .add(Restrictions.eq("sjzzjg",pid))
                .addOrder(Order.asc("wzh"))
                .list();
        session.close();
        return user;
    }
    //按部门ID查询员工列表
    public List<JcxxYhEN> CrewsByDepID(String id)
    {
        Session session=sessionFactory.openSession();
        List<JcxxYhEN> crew=session.createCriteria(JcxxYhEN.class)
                .add(Restrictions.eq("bm",id))
                .list();
        session.close();
        return crew;
    }
    //按ID查询组织
    public JcxxZzjgEN DepartmentByID(String id)
    {
        Session session=sessionFactory.openSession();
        JcxxZzjgEN dep=(JcxxZzjgEN)session.createCriteria(JcxxZzjgEN.class)
                .add(Restrictions.eq("id",id))
                .uniqueResult();
        session.close();
        return dep;
    }
    //按人员ID查询人员
    public JcxxYhEN CrewByCrewID(String id)
    {
        Session session=sessionFactory.openSession();
        JcxxYhEN crew=(JcxxYhEN)session.createCriteria(JcxxYhEN.class)
                .add(Restrictions.eq("id",id)).uniqueResult();
        session.close();
        return crew;
    }
    //按提示查询人员名字
    public List<String> UserNamesByTip(String tip)
    {
        Session session=sessionFactory.openSession();
        List<String> names=session.createCriteria(JcxxYhEN.class)
                .add(Restrictions.like("xm","%"+tip+"%"))
                .setProjection(Projections.property("xm"))
                .list();
        session.close();
        return names;
    }
    //按名字查询人员信息
    public List<?> UserByName(String name)
    {
        Session session=sessionFactory.openSession();
        List<JcxxYhEN> users=session.createCriteria(JcxxYhEN.class)
                .add(Restrictions.eq("xm",name))
                .list();

        List<Map<String,Object>> data=new ArrayList<>();
        for(JcxxYhEN user:users)
        {
            JcxxZzjgEN dw=DepartmentByID(user.getDw());
            JcxxZzjgEN bm=DepartmentByID(user.getBm());
            Map<String,Object> map=new HashMap<>();
            map.put("person",user);


            if(bm==null)
            {
                map.put("bm","");
            }else
            {
                map.put("bm",bm.getZzjgmc());
            }
            if(dw==null)
            {
                map.put("dw","");
            }else
            {
                map.put("dw",dw.getZzjgmc());
            }

            data.add(map);
        }

        session.close();
        return data;
    }

    //获取组织树
    public void OrgTree(String nodeid,Map<String,Object> map )
    {
        Session session=sessionFactory.openSession();
        OrgTree(nodeid,map,session);
        session.close();
    }
    //获取组织树
    public void OrgTree(String nodeid,Map<String,Object> map,Session session)
    {
        JcxxZzjgEN jcxxZzjgEN=(JcxxZzjgEN)session.createCriteria(JcxxZzjgEN.class)
                .add(Restrictions.eq("id",nodeid))
                .uniqueResult();


        map.put("ID",jcxxZzjgEN.getId());
        map.put("ZZJGMC",jcxxZzjgEN.getZzjgmc());
        map.put("ZZJGBM",jcxxZzjgEN.getZzjgbm());
        map.put("ZZJGDM",jcxxZzjgEN.getZzjgdm());
        map.put("ZZJGLB",jcxxZzjgEN.getZzjglb());
        map.put("ZZYM",jcxxZzjgEN.getZzym());
        map.put("SJZZJG",jcxxZzjgEN.getSjzzjg());
        map.put("SJZZJGIDLB",jcxxZzjgEN.getSjzzjgidlb());
        map.put("WZH",jcxxZzjgEN.getWzh());
        map.put("SFCK",jcxxZzjgEN.getSfck());
        map.put("SFJD",jcxxZzjgEN.getSfjd());
        map.put("ZZJGJC",jcxxZzjgEN.getZzjgjc());
        map.put("SSQY",jcxxZzjgEN.getSsqy());
        map.put("WSDWBH",jcxxZzjgEN.getWsdwbh());
        map.put("adcode",jcxxZzjgEN.getAdcode());

        List<JcxxZzjgEN> dept=session.createCriteria(JcxxZzjgEN.class)
                .add(Restrictions.eq("sjzzjg",nodeid))
                .list();
        if(dept==null||"".equals(dept))
        {
            map.put("dept","");
            return;
        }
        else
        {
            List<Map<String,Object>> depts=new ArrayList<>();
            for(JcxxZzjgEN zzjgEN:dept)
            {
                Map<String,Object> childmap=new HashMap<>();
                OrgTree(zzjgEN.getId(),childmap);
                depts.add(childmap);
            }

            map.put("dept",depts);
        }
    }

}
